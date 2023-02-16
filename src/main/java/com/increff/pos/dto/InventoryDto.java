package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.*;
import javafx.util.Pair;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InventoryDto {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;
    @Autowired
    CsvFileGenerator csvFileGenerator;

    public void add(List<InventoryForm> inventoryForms) throws ApiException{
        JSONArray errorList=new JSONArray();
        Integer errorCount=0;
        listEmptyCheck(inventoryForms);
        for(InventoryForm form: inventoryForms) {
            JSONObject error= initialiseBrandErrorObject(form);
            try{
                ValidateUtil.validateForms(form);
                NormaliseUtil.normalise(form);
                ProductPojo productPojo= productService.getCheck(form.getBarCode());    // TODO inquerry bulk fetch product
                inventoryService.checkAlreadyExist(productPojo.getId(),form.getBarCode());
            }
            catch (Exception e) {
                errorCount++;
                error.put("message",e.getMessage());
            }
            errorList.put(error);
        }
        if(errorCount>0){
            throw new ApiException(errorList.toString());
        }
        bulkAdd(inventoryForms);
    }

    public InventoryData getInventory(Integer id) throws ApiException {
        InventoryPojo inventoryPojo= inventoryService.getCheck(id);
        ProductPojo productPojo= productService.getCheck(id);
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());
    }

    public InventoryData getInventory(String barCode) throws ApiException {
        ProductPojo productPojo= productService.getCheck(barCode);
        InventoryPojo inventoryPojo= inventoryService.getCheck(productPojo.getId());
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,barCode,productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());

    }

    public List<InventoryData> getAll() throws ApiException {   //Product wise inventory
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : list) {
            ProductPojo productPojo= productService.getCheck(inventoryPojo.getId());
            BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
            list2.add(ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory()));
        }
        return list2;
    }

    public void update(Integer id, InventoryForm f) throws ApiException {
        ValidateUtil.validateForms(f);          //TODO check barcode and id belong to same product
        ProductPojo productPojo = productService.getCheck(f.getBarCode());
        InventoryPojo inventoryPojo= ConvertorUtil.convert(f,productPojo.getId());
        inventoryService.update(id,inventoryPojo);
    }

    public void generateCsv(HttpServletResponse response) throws IOException, ApiException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"InventoryReport.csv\"");
        csvFileGenerator.writeInventoryToCsv(getAllItem(), response.getWriter());
    }

    public List<InventoryReportData> getAllItem() throws ApiException {   //Brand Category wise inventory
        List<InventoryData> inventoryDataList = getAll();
        List<InventoryReportData> inventoryItemList = new ArrayList<>();
        Map<Pair<String, String>, Integer> map = new HashMap<>();
        for (InventoryData inventoryData : inventoryDataList) {
            BrandPojo brandPojo = brandService.getCheck(productService.getCheck(inventoryData.getId()).getBrandCategory());
            Pair<String, String> pair = new Pair<>(brandPojo.getBrand(), brandPojo.getCategory());
            map.merge(pair, inventoryData.getQuantity(), Integer::sum);
        }
        for (Map.Entry<Pair<String, String>, Integer> entry : map.entrySet()) {
            inventoryItemList.add(ConvertorUtil.convertMapToItem(entry));
        }
        return inventoryItemList;
    }

    @Transactional(rollbackOn = ApiException.class)
    private void bulkAdd(List<InventoryForm> forms) throws ApiException {
        for(InventoryForm form: forms) {
            InventoryPojo inventoryPojo = ConvertorUtil.convert(form, productService.getCheck(form.getBarCode()).getId());// TODO pass getcheck in bulk add parameter
            inventoryService.add(inventoryPojo);
        }
    }
    JSONObject initialiseBrandErrorObject(InventoryForm inventoryForm){     //TODO Create class for inventory error
        JSONObject error=new JSONObject();
        error.put("barCode",inventoryForm.getBarCode());
        error.put("quantity",inventoryForm.getQuantity());
        error.put("message","");
        return error;
    }
    private void listEmptyCheck(List<InventoryForm> inventoryFormList) throws ApiException {
        if(inventoryFormList.isEmpty())
            throw new ApiException("Inventory List is Empty!");
    }

}

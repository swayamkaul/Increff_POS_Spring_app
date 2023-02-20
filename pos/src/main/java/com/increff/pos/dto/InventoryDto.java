package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional(rollbackFor = ApiException.class)
    public void addInventoryList(List<InventoryForm> forms) throws ApiException, JsonProcessingException {
        listEmptyCheck(forms);
        List<InventoryErrorData> inventoryErrorDataList = new ArrayList<>();
        List<String> barCodeList = ConvertorUtil.convertInventoryFormListToBarCodeList(forms);
        HashMap<String,ProductPojo> productPojoHashMap = productService.getProuctMapByBarcodeList(barCodeList);
        Integer errorSize = 0;
        for (InventoryForm form: forms) {
            InventoryErrorData inventoryErrorData = ConvertorUtil.convertToErrorData(form);
            try {
                ValidateUtil.validateForms(form);
                NormaliseUtil.normalise(form);
                if(!productPojoHashMap.containsKey(form.getBarCode())){
                    throw new ApiException("Product with given Barcode does not exist. Barcode: "+ form.getBarCode());
                }
            }
            catch (Exception e) {
                inventoryErrorData.setMessage(e.getMessage());
                errorSize++;
            }
            inventoryErrorDataList.add(inventoryErrorData);
        }
        if(errorSize > 0) {
            ErrorUtil.throwErrors(inventoryErrorDataList);
        }

        bulkAdd(forms,productPojoHashMap);
    }

    public InventoryData getInventory(Integer id) throws ApiException {
        InventoryPojo inventoryPojo = inventoryService.getCheck(id);
        ProductPojo productPojo = productService.getCheck(id);
        BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());
    }

    public InventoryData getInventory(String barCode) throws ApiException {
        ProductPojo productPojo = productService.getCheck(barCode);
        InventoryPojo inventoryPojo = inventoryService.getCheck(productPojo.getId());
        BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,barCode,productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());

    }

    public List<InventoryData> getAllInventories() throws ApiException {   //Product wise inventory
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : list) {
            ProductPojo productPojo = productService.getCheck(inventoryPojo.getId());
            BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
            list2.add(ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory()));
        }
        return list2;
    }

    public void updateInventory(Integer id, InventoryForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        NormaliseUtil.normalise(f);
        InventoryData inventoryData = getInventory(id);
        if((!inventoryData.getBarCode().equals(f.getBarCode()))){
            throw new ApiException("Barcode cannot be changed.");
        }

        inventoryService.updateInventoryQuantity(id, f.getQuantity());
    }

    public void getInventoryReportCsv(HttpServletResponse response) throws IOException, ApiException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"InventoryReport.csv\"");
        csvFileGenerator.writeInventoryToCsv(getAllItem(), response.getWriter());
    }

    public List<InventoryReportData> getAllItem() throws ApiException {   //Brand Category wise inventory
        List<InventoryData> inventoryDataList = getAllInventories();
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

    private void bulkAdd(List<InventoryForm> forms,HashMap<String,ProductPojo> productPojoHashMap) throws ApiException {
        for(InventoryForm form: forms) {
            InventoryPojo inventoryPojo = ConvertorUtil.convert(form, productPojoHashMap.get(form.getBarCode()).getId());
            inventoryService.add(inventoryPojo);
        }
    }

    private void listEmptyCheck(List<InventoryForm> inventoryFormList) throws ApiException {
        if(inventoryFormList.isEmpty())
            throw new ApiException("Inventory List is Empty!");
    }

}

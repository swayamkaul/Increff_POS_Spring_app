package com.increff.pos.dto;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.InventoryReportData;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.ValidateUtil;
import com.increff.pos.util.ValidationUtil;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
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

    public void add(InventoryForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        ProductPojo productPojo = productService.getCheck(f.getBarCode());
        InventoryPojo inventoryPojo=ConvertorUtil.convert(f,productPojo.getId());
        try{
            inventoryService.getCheck(inventoryPojo.getId());
        }
        catch(ApiException e){
            inventoryService.add(inventoryPojo);
        }
    }

    public void delete(int id) {
        inventoryService.delete(id);
    }

    public InventoryData get(int id) throws ApiException {
        InventoryPojo inventoryPojo= inventoryService.getCheck(id);
        ProductPojo productPojo= productService.getCheck(id);
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());
    }

    public InventoryData get(String barCode) throws ApiException {
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

    public void update(int id, InventoryForm f) throws ApiException {
        ProductPojo productPojo = productService.getCheck(f.getBarCode());
        InventoryPojo inventoryPojo= ConvertorUtil.convert(f,productPojo.getId());
        ValidationUtil.validate(inventoryPojo);
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

}

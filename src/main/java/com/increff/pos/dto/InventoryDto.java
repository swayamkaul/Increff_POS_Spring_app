package com.increff.pos.dto;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.ValidateUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class InventoryDto {
    @Autowired
    InventoryService inventoryService;
    @Autowired
    ProductService productService;
    @Autowired
    BrandService brandService;
    public void add(InventoryForm f) throws ApiException {
        ValidateUtil.validateForms(f);
        ProductPojo productPojo = productService.get(f.getBarCode());
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
        ProductPojo productPojo= productService.get(id);
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());
    }

    public InventoryData get(String barCode) throws ApiException {
        ProductPojo productPojo= productService.get(barCode);
        InventoryPojo inventoryPojo= inventoryService.getCheck(productPojo.getId());
        BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
        return ConvertorUtil.convert(inventoryPojo,barCode,productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory());

    }

    public List<InventoryData> getAll() throws ApiException {
        List<InventoryPojo> list = inventoryService.getAll();
        List<InventoryData> list2 = new ArrayList<InventoryData>();
        for (InventoryPojo inventoryPojo : list) {
            ProductPojo productPojo= productService.get(inventoryPojo.getId());
            BrandPojo brandPojo=brandService.getCheck(productPojo.getBrandCategory());
            list2.add(ConvertorUtil.convert(inventoryPojo,productPojo.getBarCode(),productPojo.getName(),brandPojo.getBrand(),brandPojo.getCategory()));
        }
        return list2;
    }

    public void update(int id, InventoryForm f) throws ApiException {
        ProductPojo productPojo = productService.get(f.getBarCode());
        InventoryPojo inventoryPojo= ConvertorUtil.convert(f,productPojo.getId());
        ValidationUtil.validate(inventoryPojo);
        inventoryService.update(id,inventoryPojo);
    }

}

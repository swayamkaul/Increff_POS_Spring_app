package com.increff.pos.util;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;

public class ValidationUtil {
    public static void validate(BrandPojo p)throws ApiException{
        if(StringUtil.isEmpty(p.getBrand())||StringUtil.isEmpty(p.getCategory())) {
            throw new ApiException("Brand name or Category cannot be empty.");
        }
    }
    public static void validate(ProductPojo p)throws ApiException{
        if(StringUtil.isEmpty(p.getName())||StringUtil.isEmpty(p.getBarCode())) {
            throw new ApiException("Product name or Barcode cannot be empty.");
        }
        if(p.getMrp()<0){
            throw new ApiException("MRP cannot be negative.");
        }
    }

    public static void validate(InventoryPojo inventoryPojo)throws ApiException{
        if(inventoryPojo.getQuantity()<0){
            throw new ApiException("Quantity cannot be negative.");
        }
    }

}

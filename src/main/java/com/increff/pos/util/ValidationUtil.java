package com.increff.pos.util;

import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;

import java.util.List;

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
    public static void validate(List<OrderItemForm> orderItemFormList)throws ApiException{
        for(OrderItemForm orderItemForm: orderItemFormList){
            if((orderItemForm.getQuantity() == null)
                || (orderItemForm.getBarCode() == null)){
                throw new ApiException("All Fields are mandatory!");
            }
            if(orderItemForm.getQuantity()<1){
                throw new ApiException("Quantity should be greater than 1.");
            }
        }
    }

    public static void validate(OrderItemForm orderItemForm,List<String> errorList)throws ApiException{
        if((orderItemForm.getQuantity() == null) ||
           (orderItemForm.getBarCode() == null) ){
                errorList.add(errorList.size()+" All Fields are mandatory!");
            }
            if(orderItemForm.getQuantity()<1){
                errorList.add("Quantity should be greater than 1.");
            }
    }

}

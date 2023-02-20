package com.increff.pos.util;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

import java.util.List;

public class NormaliseUtil {
    public static void normalise(BrandForm brandForm){
        brandForm.setBrand(StringUtil.toLowerCase(brandForm.getBrand()));
        brandForm.setCategory(StringUtil.toLowerCase(brandForm.getCategory()));
    }
    public static void normalise(ProductForm p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    public static void normalise(List<OrderItemForm> orderItemFormList){
        for (OrderItemForm f : orderItemFormList) {
            f.setBarCode(StringUtil.doTrim(f.getBarCode()));
        }

    }
    public static void normalise(OrderItemForm orderItemForm) {
        orderItemForm.setBarCode(StringUtil.toLowerCase(orderItemForm.getBarCode()).trim());
    }
    public static void normalise(InventoryForm inventoryForm) {
        inventoryForm.setBarCode(StringUtil.toLowerCase(inventoryForm.getBarCode()).trim());
    }
    public static void normalise(UserForm userForm) {
        userForm.setEmail(StringUtil.toLowerCase(userForm.getEmail()).trim());
    }

    public static void normalise(SalesReportForm salesReportForm) {
        salesReportForm.setBrand(StringUtil.toLowerCase(salesReportForm.getBrand()).trim());
        salesReportForm.setCategory(StringUtil.toLowerCase(salesReportForm.getCategory()).trim());
    }


}

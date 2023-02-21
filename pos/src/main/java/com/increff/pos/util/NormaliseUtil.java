package com.increff.pos.util;

import com.increff.pos.model.*;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

import java.util.List;

public class NormaliseUtil {
    public static void normalise(BrandForm brandForm){
        brandForm.setBrand(StringUtil.toLowerCase(brandForm.getBrand()).trim());
        brandForm.setCategory(StringUtil.toLowerCase(brandForm.getCategory()).trim());
    }
    public static void normalise(ProductForm productForm) {
        productForm.setName(StringUtil.toLowerCase(productForm.getName()).trim());
        productForm.setBarCode(StringUtil.toLowerCase(productForm.getBarCode()).trim());
        productForm.setBrand(StringUtil.toLowerCase(productForm.getBrand()).trim());
        productForm.setCategory(StringUtil.toLowerCase(productForm.getCategory()).trim());
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

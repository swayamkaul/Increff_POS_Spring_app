package com.increff.pos.helper;

import com.increff.pos.model.*;

public class FormHelper {

    public static BrandForm createBrand(String brand, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }

    public static ProductForm createProduct(String productBarcode, String productName, String brandName, String category, double productMrp) {
        ProductForm productForm = new ProductForm();
        productForm.setBrand(brandName);
        productForm.setCategory(category);
        productForm.setName(productName);
        productForm.setBarCode(productBarcode);
        productForm.setMrp(productMrp);
        return productForm;
    }
    public static InventoryForm createInventory(String productBarcode, Integer quantity) {
        InventoryForm inventoryForm = new InventoryForm();
        inventoryForm.setBarCode(productBarcode);
        inventoryForm.setQuantity(quantity);
        return inventoryForm;
    }

    public static UserForm createUser(String email, String password, String role) {
        UserForm userForm = new UserForm();
        userForm.setEmail(email);
        userForm.setPassword(password);
        userForm.setRole(role);
        return userForm;
    }

    public static OrderItemForm createOrderItem(String barCode, Integer quantity, double sellingPrice) {
        OrderItemForm orderItemForm = new OrderItemForm();
        orderItemForm.setQuantity(quantity);
        orderItemForm.setBarCode(barCode);
        orderItemForm.setSellingPrice(sellingPrice);
        return orderItemForm;
    }
}

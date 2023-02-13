package com.increff.pos.helper;

import com.increff.pos.model.BrandForm;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.model.ProductForm;
import com.increff.pos.model.UserForm;

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
    public static InventoryForm createInventory(String productBarcode, int quantity) {
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
}

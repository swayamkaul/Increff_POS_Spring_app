package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.FormHelper;
import com.increff.pos.model.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class InventoryDtoTest extends AbstractUnitTest {
    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    InventoryService inventoryService;

    @Autowired
    ProductService productService;

    @Test(expected = ApiException.class)
    public void testEmptyListInventoryAddition() throws ApiException {
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        inventoryDto.add(inventoryFormList);
    }

    @Test
    public void testAddInventory() throws ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);
        List<ProductPojo> productPojoList = productService.getAll();
        assertEquals(1, productPojoList.size());
        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 3);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);
        List<InventoryPojo> inventoryPojoList = inventoryService.getAll();



        String expectedBrandName = "brand";
        String expectedCategoryName = "category";
        String expectedName = "name";
        String expectedBarCode = "12345678";
        Integer expectedQuantity = 3;

        InventoryData data = inventoryDto.get("12345678");
        assertEquals(expectedBarCode, data.getBarCode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
        assertEquals(expectedQuantity, data.getQuantity());
    }

    @Test
    public void getAllTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name", "brand", "category", 23.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 3);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 3);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        List<InventoryData> list = inventoryDto.getAll();

        assertEquals(2, list.size());
    }

    @Test
    public void updateTest() throws ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 3);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        InventoryForm inventoryForm1 = FormHelper.createInventory("12345678", 6);


        String expectedBarCode = "12345678";
        Integer expectedQuantity = 6;

        InventoryData inventoryData = inventoryDto.get(inventoryDto.get(expectedBarCode).getId());
        inventoryDto.update(inventoryData.getId(), inventoryForm1);

        InventoryPojo pojo = inventoryService.getCheck(inventoryData.getId());
        assertEquals(expectedQuantity, pojo.getQuantity());

    }

    @Test
    public void testCSV() throws IOException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name", "brand", "category", 23.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 3);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 3);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);
        MockHttpServletResponse response = new MockHttpServletResponse();
        inventoryDto.generateCsv(response);
        assertEquals("text/csv", response.getContentType());

        List<InventoryReportData> list= inventoryDto.getAllItem();
        assertEquals(1, list.size());


    }

    @Test(expected = ApiException.class)
    public void addIllegal() throws ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 3);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 3);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

    }

    @Test
    public void testGetByBarCode() throws ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 3);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        String expectedBarCode = "12345678";
        InventoryData inventoryData = inventoryDto.get(inventoryDto.get(expectedBarCode).getId());
        assertEquals(expectedBarCode, inventoryData.getBarCode());
    }


}

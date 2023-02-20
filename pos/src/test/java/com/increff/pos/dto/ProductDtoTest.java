package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.FormHelper;

import com.increff.pos.model.BrandForm;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductDtoTest extends AbstractUnitTest {
    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    ProductService productService;

    @Test(expected = ApiException.class)
    public void testEmptyListProductAddition() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        productDto.addProductList(productFormList);
    }

    @Test
    public void testAddProduct() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 11.00);
        productFormList.add(productForm);

        productDto.addProductList(productFormList);

        String expectedBrandName = "brand";
        String expectedCategoryName = "category";
        String expectedName = "name";
        Double expectedMrp = 11.00;
        String expectedBarcode = "12345678";

        ProductData data = productDto.getProduct(productService.getCheck(expectedBarcode).getId());
        assertEquals(expectedBarcode, data.getBarCode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
        assertEquals(expectedMrp, data.getMrp(), 0.001);
    }

    @Test
    public void testGetAllProduct() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "first product", "brand", "category", 11.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("123456789","second product","brand","category",88.0);
        productFormList.add(productForm1);

        productDto.addProductList(productFormList);

        List<ProductData> list = productDto.getAllProducts();
        assertEquals(2, list.size());
    }

    @Test
    public void testGetByBarCodeProduct() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "first product", "brand", "category", 11.00);
        productFormList.add(productForm);

        productDto.addProductList(productFormList);

        ProductData productData = productDto.getProduct("12345678");
        assertEquals(productData.getName(),productForm.getName());
        assertEquals(productData.getBrand(),productForm.getBrand());
        assertEquals(productData.getCategory(),productForm.getCategory());
        assertEquals(productData.getBarCode(),productForm.getBarCode());

    }
    @Test
    public void testUpdateProduct() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        productDto.addProductList(productFormList);

        ProductForm productForm1 = FormHelper.createProduct("12345678", "name", "brand", "category", 27.00);

        String expectedBrandName = "brand";
        String expectedCategoryName = "category";
        String expectedName = "name";
        Double expectedMrp = 27.00;
        String expectedBarcode = "12345678";

        productDto.updateProduct(productService.getCheck(expectedBarcode).getId(), productForm1);

        ProductData data = productDto.getProduct(productService.getCheck(expectedBarcode).getId());
        assertEquals(expectedBarcode, data.getBarCode());
        assertEquals(expectedName, data.getName());
        assertEquals(expectedBrandName, data.getBrand());
        assertEquals(expectedCategoryName, data.getCategory());
        assertEquals(expectedMrp, data.getMrp(), 0.001);

    }

    @Test(expected = ApiException.class)
    public void testAddDuplicate() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);


        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        productDto.addProductList(productFormList);
        productDto.addProductList(productFormList);
    }

    @Test(expected = ApiException.class)
    public void testIllegalAdd() throws ApiException, JsonProcessingException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);


        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand2", "category3", 23.00);
        productFormList.add(productForm);

        productDto.addProductList(productFormList);
    }
}

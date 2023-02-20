package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.FormHelper;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SalesReportDtoTest extends AbstractUnitTest {
    @Autowired
    OrderDto orderDto;

    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;

    @Autowired
    DailySalesDto salesDto;

    @Autowired
    SalesReportDto salesReportDto;

    @Test
    public void getFilterTest() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.addProductList(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.addInventoryList(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderDto.createOrder(orderItemFormList);

        OrderItemForm orderItemForm1 = FormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.createOrder(orderItemFormList);

        SalesReportForm salesReportForm = new SalesReportForm();
        LocalDate date = LocalDate.now();
        salesReportForm.setStartDate(date.toString());
        salesReportForm.setEndDate(date.toString());
        salesReportForm.setCategory("all");
        salesReportForm.setBrand("all");
        List<SalesReportData> list = salesReportDto.getFilteredData(salesReportForm);
        assertEquals(1, list.size());

    }



    @Test
    public void generateCSV() throws ApiException, IOException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.addBrandList(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.addProductList(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.addInventoryList(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderDto.createOrder(orderItemFormList);

        OrderItemForm orderItemForm1 = FormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.createOrder(orderItemFormList);

        SalesReportForm salesReportForm = new SalesReportForm();
        LocalDate date = LocalDate.now();
        salesReportForm.setStartDate(date.toString());
        salesReportForm.setEndDate(date.toString());
        salesReportForm.setCategory("all");
        salesReportForm.setBrand("all");
        List<SalesReportData> list = salesReportDto.getFilteredData(salesReportForm);
        assertEquals(1, list.size());

        MockHttpServletResponse response = new MockHttpServletResponse();
        salesReportDto.getSalesReportCsv(response);


    }
}

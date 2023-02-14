package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.AbstractUnitTest;
import com.increff.pos.helper.FormHelper;
import com.increff.pos.model.*;
import com.increff.pos.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class OrderDtoTest extends AbstractUnitTest {
    @Autowired
    OrderDto orderDto;

    @Autowired
    InventoryDto inventoryDto;

    @Autowired
    ProductDto productDto;

    @Autowired
    BrandDto brandDto;


    @Test(expected = ApiException.class)
    public void testEmptyListInventoryAddition() throws ApiException, JsonProcessingException {
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        orderDto.addOrderItem(orderItemFormList);
    }

    @Test
    public void testOrderAddition() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "first product", "brand", "category", 11.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "second product", "brand", "category", 88.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm =FormHelper.createOrderItem("12345678", 2, 2.00);
        orderItemFormList.add(orderItemForm);
        orderDto.addOrderItem(orderItemFormList);

        OrderItemForm orderItemForm1 = FormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.addOrderItem(orderItemFormList);

        List<OrderData> list = orderDto.getAll();
        assertEquals(2, list.size());
        List<InventoryData> inventoryDataList = inventoryDto.getAll();
        assertEquals(2, inventoryDataList.size());
        assertEquals(3, (int)inventoryDataList.get(0).getQuantity());
        assertEquals(5, (int)inventoryDataList.get(1).getQuantity());
    }

    @Test(expected = ApiException.class)
    public void testDuplicatesCheck() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderItemFormList.add(orderItemForm);
        orderDto.addOrderItem(orderItemFormList);

    }

    @Test(expected = ApiException.class)
    public void testInsufficientInventory() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 8, 23.00);
        orderItemFormList.add(orderItemForm);

        orderDto.addOrderItem(orderItemFormList);

    }
    @Test(expected = ApiException.class)
    public void testSelligPriceMoreThanMrp() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 8, 230.00);
        orderItemFormList.add(orderItemForm);

        orderDto.addOrderItem(orderItemFormList);

    }
    @Test
    public void testGetOrderItemListByOrderID() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderDto.addOrderItem(orderItemFormList);

        OrderItemForm orderItemForm1 =FormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.addOrderItem(orderItemFormList);

        List<OrderData> list = orderDto.getAll();
        List<OrderItemData> list1 = orderDto.getItemByOrderId(list.get(0).getId());
        List<OrderItemData> list2 = orderDto.getItemByOrderId(list.get(1).getId());

        assertEquals(2, list.size());
        assertEquals(2, list1.size());
        assertEquals(1, list2.size());

    }
    @Test
    public void testGetPDF() throws Exception {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm =  FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        InventoryForm form = FormHelper.createInventory("12345679", 8);
        inventoryFormList.add(form);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 2, 23.00);
        orderItemFormList.add(orderItemForm);
        orderDto.addOrderItem(orderItemFormList);

        OrderItemForm orderItemForm1 = FormHelper.createOrderItem("12345679", 3, 28.00);
        orderItemFormList.add(orderItemForm1);

        orderDto.addOrderItem(orderItemFormList);
        List<OrderData> list = orderDto.getAll();
        assertEquals(2,list.size());

        ResponseEntity<byte[]> response= orderDto.getPDF(list.get(0).getId());
        assertNotEquals(null, response);
    }

    @Test(expected = ApiException.class)
    public void testThrowErrors() throws JsonProcessingException, ApiException {
        List<ProductForm> productFormList = new ArrayList<>();
        List<BrandForm> brandFormList = new ArrayList<>();
        List<InventoryForm> inventoryFormList = new ArrayList<>();
        List<OrderItemForm> orderItemFormList = new ArrayList<>();
        BrandForm brandForm = FormHelper.createBrand("Brand", "CateGory");
        brandFormList.add(brandForm);

        brandDto.add(brandFormList);

        ProductForm productForm = FormHelper.createProduct("12345678", "name", "brand", "category", 23.00);
        productFormList.add(productForm);

        ProductForm productForm1 = FormHelper.createProduct("12345679", "name1", "brand", "category", 28.00);
        productFormList.add(productForm1);

        productDto.add(productFormList);

        InventoryForm inventoryForm = FormHelper.createInventory("12345678", 7);
        inventoryFormList.add(inventoryForm);

        inventoryDto.add(inventoryFormList);

        OrderItemForm orderItemForm = FormHelper.createOrderItem("12345678", 8, 23.00);
        orderItemFormList.add(orderItemForm);

        orderDto.addOrderItem(orderItemFormList);

    }

}

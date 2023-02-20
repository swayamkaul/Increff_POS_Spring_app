package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderServiceTest extends AbstractUnitTest {
    @Autowired
    OrderService orderService;
    @Autowired
    InventoryService inventoryService;

    @Test
    public void testCheckOrder() throws ApiException {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setId(5);
        inventoryPojo.setQuantity(500);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo1=new InventoryPojo();
        inventoryPojo1.setId(6);
        inventoryPojo1.setQuantity(500);
        inventoryService.add(inventoryPojo1);

        OrderPojo orderPojo1 = new OrderPojo();
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setOrderId(orderPojo1.getId());
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);
        List<OrderItemPojo> list=new ArrayList<>();
        list.add(orderItemPojo);
        orderService.addOrderItemListToOrder(list);

        OrderPojo orderPojo2 = new OrderPojo();
        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setOrderId(orderPojo2.getId());
        orderItemPojo1.setQuantity(2);
        orderItemPojo1.setSellingPrice(23.00);
        orderItemPojo1.setProductId(5);

        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setOrderId(orderPojo2.getId());
        orderItemPojo2.setQuantity(2);
        orderItemPojo2.setSellingPrice(23.00);
        orderItemPojo2.setProductId(6);

        List<OrderItemPojo> list1=new ArrayList<>();
        list1.add(orderItemPojo1);
        list1.add(orderItemPojo2);


        OrderPojo orderPojo3= orderService.addOrderItemListToOrder(list1);
        List<OrderItemPojo> orderItemsInOrder1=orderService.selectByOrderId(orderPojo3.getId());
        assertEquals(2,orderItemsInOrder1.size());

        List<OrderPojo> orderPojoList = orderService.getAll();
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        for(OrderPojo orderPojo: orderPojoList){
            List<OrderItemPojo> orderItemsInOrder=orderService.selectByOrderId(orderPojo.getId());
            for(OrderItemPojo orderItemPojo3: orderItemsInOrder) {
                orderItemPojoList.add(orderItemPojo3);
            }
        }



        assertEquals(3,orderItemPojoList.size());

        assertEquals(2,orderPojoList.size());

    }

    @Test(expected = ApiException.class)
    public void testGetNonExistingOrder() throws ApiException {
        orderService.getCheck(1);
    }

    @Test
    public void testGetOrder() throws ApiException {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setId(5);
        inventoryPojo.setQuantity(500);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo1=new InventoryPojo();
        inventoryPojo1.setId(6);
        inventoryPojo1.setQuantity(500);
        inventoryService.add(inventoryPojo1);

        OrderItemPojo orderItemPojo = new OrderItemPojo();

        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);

        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setQuantity(2);
        orderItemPojo1.setSellingPrice(23.00);
        orderItemPojo1.setProductId(6);


        List<OrderItemPojo> list1=new ArrayList<>();
        list1.add(orderItemPojo);
        list1.add(orderItemPojo1);
        OrderPojo orderPojo1 = orderService.addOrderItemListToOrder(list1);


        OrderPojo orderPojo = orderService.get(orderPojo1.getId());
        assertEquals(orderPojo1.getId(), orderPojo.getId());

    }

    @Test
    public void testDateFilter() throws ApiException {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setId(5);
        inventoryPojo.setQuantity(500);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo1=new InventoryPojo();
        inventoryPojo1.setId(6);
        inventoryPojo1.setQuantity(500);
        inventoryService.add(inventoryPojo1);

        InventoryPojo inventoryPojo2=new InventoryPojo();
        inventoryPojo2.setId(7);
        inventoryPojo2.setQuantity(500);
        inventoryService.add(inventoryPojo2);

        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);

        OrderItemPojo orderItemPojo1 = new OrderItemPojo();
        orderItemPojo1.setQuantity(2);
        orderItemPojo1.setSellingPrice(23.00);
        orderItemPojo1.setProductId(6);

        OrderItemPojo orderItemPojo2 = new OrderItemPojo();
        orderItemPojo2.setQuantity(2);
        orderItemPojo2.setSellingPrice(23.00);
        orderItemPojo2.setProductId(7);

        List<OrderItemPojo> list=new ArrayList<>();

        list.add(orderItemPojo);
        list.add(orderItemPojo1);
        list.add(orderItemPojo2);


        orderService.addOrderItemListToOrder(list);
        orderService.addOrderItemListToOrder(list);
        LocalDate date = LocalDate.now();
        LocalDateTime startDate = date.atStartOfDay();

        LocalDateTime endDate =  LocalDateTime.of(date, LocalTime.MAX);

        List<OrderPojo> orderPojoList = orderService.getOrderByDateFilter(startDate,endDate);
        assertEquals(2, orderPojoList.size());

    }

    @Test
    public void testSelectByOrderId() throws ApiException {
        InventoryPojo inventoryPojo=new InventoryPojo();
        inventoryPojo.setId(5);
        inventoryPojo.setQuantity(500);
        inventoryService.add(inventoryPojo);

        InventoryPojo inventoryPojo1=new InventoryPojo();
        inventoryPojo1.setId(6);
        inventoryPojo1.setQuantity(500);
        inventoryService.add(inventoryPojo1);


        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setQuantity(2);
        orderItemPojo.setSellingPrice(23.00);
        orderItemPojo.setProductId(5);
        List<OrderItemPojo> list=new ArrayList<>();
        list.add(orderItemPojo);

        OrderPojo orderPojo=orderService.addOrderItemListToOrder(list);
        orderService.selectByOrderId(orderPojo.getId());
    }
}

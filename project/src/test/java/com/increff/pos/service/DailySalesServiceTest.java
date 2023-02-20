package com.increff.pos.service;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.pojo.SalesPojo;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class DailySalesServiceTest extends AbstractUnitTest {
    @Autowired
    SalesService salesService;

    @Test
    public void addTest() {
        SalesPojo salesPojo = new SalesPojo();
        LocalDate date = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        salesPojo.setDate(date);
        salesPojo.setLastRun(now);
        salesPojo.setInvoicedItemsCount(5);
        salesPojo.setTotalRevenue(345.00);
        salesPojo.setInvoicedOrderCount(3);
        salesService.add(salesPojo);

        Integer expectedItems = 5;
        Integer expectedOrders = 3;
        Double expectedRevenue = 345.00;
        SalesPojo salesPojo1 = salesService.getByDate(date);
        Integer item = salesPojo1.getInvoicedItemsCount();
        Integer order = salesPojo1.getInvoicedOrderCount();
        Double revenue = salesPojo1.getTotalRevenue();
        assertEquals(expectedItems, item);
        assertEquals(expectedOrders,order);
        assertEquals(expectedRevenue, revenue,0.001);
        List<SalesPojo> list = salesService.getALL();
        assertEquals(1,list.size());

        List<SalesPojo> list2 = salesService.getAllByDate(date,date);
        assertEquals(1,list2.size());

    }

    @Test
    public void updateTest() {
        SalesPojo salesPojo = new SalesPojo();
        LocalDate date = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();
        salesPojo.setDate(date);
        salesPojo.setLastRun(now);
        salesPojo.setInvoicedItemsCount(5);
        salesPojo.setTotalRevenue(345.00);
        salesPojo.setInvoicedOrderCount(3);
        salesService.add(salesPojo);

        SalesPojo salesPojo1 = new SalesPojo();
        salesPojo1.setDate(date);
        salesPojo1.setLastRun(now);
        salesPojo1.setInvoicedItemsCount(6);
        salesPojo1.setTotalRevenue(1235.00);
        salesPojo1.setInvoicedOrderCount(7);

        salesService.update(date, salesPojo1);

        Integer expectedItems = 6;
        Integer expectedOrders = 7;
        Double expectedRevenue = 1235.00;
        SalesPojo pojo = salesService.getByDate(date);
        Integer item = pojo.getInvoicedItemsCount();
        Integer order = pojo.getInvoicedOrderCount();
        Double revenue = pojo.getTotalRevenue();
        assertEquals(expectedItems, item);
        assertEquals(expectedOrders,order);
        assertEquals(expectedRevenue, revenue,0.001);
    }
}

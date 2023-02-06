package com.increff.pos.dto;

import com.increff.pos.model.SalesForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.*;
import com.increff.pos.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Component
public class DailySalesDto {
    @Autowired
    SalesService salesService;
    @Autowired
    OrderService orderService;

    public void createReport() throws ApiException {
        SalesPojo salesPojo = new SalesPojo();

        LocalDate date = LocalDate.now();
        int totalItems = 0;
        double totalRevenue = 0.0;
        LocalDateTime startDate = date.atStartOfDay();

        LocalDateTime endDate =  LocalDateTime.of(date, LocalTime.MAX);

        List<OrderPojo> orderPojoList = orderService.getOrderByDateFilter(startDate,endDate);

        Integer totalOrders = orderPojoList.size();

        for (OrderPojo orderPojo : orderPojoList) {
            Integer id = orderPojo.getId();
            List<OrderItemPojo> orderItemPojoList = orderService.selectByOrderId(id);
            for (OrderItemPojo orderItemPojo: orderItemPojoList) {
                totalItems += orderItemPojo.getQuantity();
                totalRevenue += (orderItemPojo.getQuantity() * orderItemPojo.getSellingPrice());
            }
        }
        LocalDateTime now = LocalDateTime.now();
        salesPojo.setDate(date);
        salesPojo.setLastRun(now);
        salesPojo.setInvoicedItemsCount(totalItems);
        salesPojo.setTotalRevenue(totalRevenue);
        salesPojo.setInvoicedOrderCount(totalOrders);
        SalesPojo pojo = salesService.getByDate(date);
        if(Objects.isNull(pojo)){
            salesService.add(salesPojo);
        }
        else {
            salesService.update(date,salesPojo);
        }

    }

    public List<SalesPojo> getAll() {
        return salesService.getALL();
    }

    public List<SalesPojo> getAllByDate(SalesForm salesForm){
        ValidateUtil.validateForms(salesForm);
        LocalDate startDate = LocalDate.parse(salesForm.getStartDate());
        LocalDate endDate = LocalDate.parse(salesForm.getEndDate());
        return  salesService.getAllByDate(startDate, endDate);
    }


}

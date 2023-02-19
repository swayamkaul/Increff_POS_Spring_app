package com.increff.pos.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "/api/orders")
public class OrderApiController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "Gets All orders")
    @RequestMapping(method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return dto.getAllOrders();
    }

    //OrderItem APIs

    @ApiOperation(value="Adds Order Items")
    @RequestMapping( method = RequestMethod.POST)
    public OrderData createOrder(@RequestBody List<OrderItemForm> orderItemFormList) throws ApiException, JsonProcessingException {
        return dto.createOrder(orderItemFormList);
    }


    @ApiOperation(value="Get Order Items by order id")
    @RequestMapping(path="/{id}/items", method = RequestMethod.GET)
    public List<OrderItemData> getItemByOrderId(@PathVariable Integer id) throws ApiException{
        return dto.getItemByOrderId(id);
    }

    @ApiOperation(value = "Download Invoice")
    @GetMapping(path = "/invoice/{id}")
    public ResponseEntity<byte[]> getPDF(@PathVariable Integer id) throws Exception{
        return dto.getPDF(id);
    }
}
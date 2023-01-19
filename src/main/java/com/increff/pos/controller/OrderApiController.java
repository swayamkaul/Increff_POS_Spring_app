package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "/api/orders")
public class OrderApiController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "Creates order")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public OrderData add(@RequestBody OrderForm form) throws ApiException {
        return dto.add(form);
    }


    @ApiOperation(value = "Gets Order by id")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable Integer id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets All orders")
    @RequestMapping(path = "/orders", method = RequestMethod.GET)
    public List<OrderData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates Order by id")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public OrderData put(@PathVariable Integer id, @RequestBody OrderForm form) throws ApiException {
        return dto.update(id, form);
    }

    @ApiOperation(value = "Sets order non editable")
    @RequestMapping(path = "/{id}/finalise", method = RequestMethod.PUT)
    public void finaliseOrder(@PathVariable Integer id) throws ApiException {
        dto.finaliseOrder(id);
    }

    //OrderItem APIs

    @ApiOperation(value="Adds Order Items")
    @RequestMapping(path="/items", method = RequestMethod.POST)
    public OrderData add1(@RequestBody List<OrderItemForm> form) throws ApiException{
        return dto.add1(form);
    }

    @ApiOperation(value="Adds Order Items to exisiting order")
    @RequestMapping(path="/{id}/items", method = RequestMethod.PUT)
    public void add1(@PathVariable Integer id,@RequestBody OrderItemForm form) throws ApiException{
        dto.addToExisitingOrder1(id,form);
    }

    @ApiOperation(value="Get Order Items")
    @RequestMapping(path="/items/{id}", method = RequestMethod.GET)
    public OrderItemData get1(@PathVariable Integer id) throws ApiException{
        return dto.getById1(id);
    }

    @ApiOperation(value="Get Order Items by order id")
    @RequestMapping(path="/{id}/items", method = RequestMethod.GET)
    public List<OrderItemData> getByOrderId1(@PathVariable Integer id) throws ApiException{
        return dto.getByOrderId1(id);
    }

    @ApiOperation(value="Updates order item by item id")
    @RequestMapping(path="/items/{id}", method = RequestMethod.PUT)
    public void update1(@PathVariable Integer id, @RequestBody OrderItemForm f) throws ApiException{
        dto.update1(id, f);
    }
}
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
//
//    @ApiOperation(value = "Sets order non editable")
//    @RequestMapping(path = "/{id}/finalise", method = RequestMethod.PUT)
//    public void finaliseOrder(@PathVariable Integer id) throws ApiException {
//        dto.finaliseOrder(id);
//    }

    //OrderItem APIs

    @ApiOperation(value="Adds Order Items")
    @RequestMapping(path="/items", method = RequestMethod.POST)
    public OrderData addOrderItem(@RequestBody List<OrderItemForm> orderItemFormList) throws ApiException{
        return dto.addOrderItem(orderItemFormList);
    }

    @ApiOperation(value="Adds Order Items to exisiting order")
    @RequestMapping(path="/{id}/items", method = RequestMethod.PUT)
    public void addItemToExisitingOrder(@PathVariable Integer id,@RequestBody OrderItemForm orderItemForm) throws ApiException{
        dto.addItemToExisitingOrder(id,orderItemForm);
    }

    @ApiOperation(value="Get Order Items")
    @RequestMapping(path="/items/{id}", method = RequestMethod.GET)
    public OrderItemData getItemById(@PathVariable Integer id) throws ApiException{
        return dto.getItemById(id);
    }

    @ApiOperation(value="Get Order Items by order id")
    @RequestMapping(path="/{id}/items", method = RequestMethod.GET)
    public List<OrderItemData> getItemByOrderId(@PathVariable Integer id) throws ApiException{
        return dto.getItemByOrderId(id);
    }

    @ApiOperation(value="Updates order item by item id")
    @RequestMapping(path="/items/{id}", method = RequestMethod.PUT)
    public void updateOrderItem(@PathVariable Integer id, @RequestBody OrderItemForm f) throws ApiException{
        dto.updateOrderItem(id, f);
    }
}
package com.increff.pos.controller;


import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.ProductData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.ProductForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "/api/products")
public class ProductApiController {

    @Autowired
    private ProductDto dto;

    @ApiOperation(value = "Adds a Product")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody List<ProductForm> form) throws ApiException, JsonProcessingException {
        dto.add(form);
    }
    @ApiOperation(value = "Gets a Product by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ProductData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }
    @ApiOperation(value = "Gets a Product by Barcode")
    @RequestMapping(path = "/b/{barCode}", method = RequestMethod.GET)
    public ProductData get(@PathVariable String barCode) throws ApiException {
        return dto.get(barCode);
    }
    @ApiOperation(value = "Gets list of all Product")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ProductData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates an Product")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody ProductForm f) throws ApiException {
        dto.update(id, f);
    }


}

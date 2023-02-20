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

    @ApiOperation(value = "Adds Product List")
    @RequestMapping( method = RequestMethod.POST)
    public void addProductList(@RequestBody List<ProductForm> form) throws ApiException, JsonProcessingException {
        dto.addProductList(form);
    }
    @ApiOperation(value = "Gets a Product by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable Integer id) throws ApiException {
        return dto.getProduct(id);
    }
    @ApiOperation(value = "Gets a Product by Barcode")
    @RequestMapping(path = "/barcode/{barCode}", method = RequestMethod.GET)
    public ProductData getProduct(@PathVariable String barCode) throws ApiException {
        return dto.getProduct(barCode);
    }
    @ApiOperation(value = "Gets list of all Product")
    @RequestMapping(method = RequestMethod.GET)
    public List<ProductData> getAllProducts() throws ApiException {
        return dto.getAllProducts();
    }

    @ApiOperation(value = "Updates a Product")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateProduct(@PathVariable Integer id, @RequestBody ProductForm f) throws ApiException {
        dto.updateProduct(id, f);
    }


}

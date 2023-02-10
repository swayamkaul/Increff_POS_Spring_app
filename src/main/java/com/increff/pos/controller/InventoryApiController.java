package com.increff.pos.controller;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.InventoryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.InventoryForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;

@Api
@RestController
@RequestMapping(path = "/api/inventory")
public class InventoryApiController {

    @Autowired
    private InventoryDto dto;

    @ApiOperation(value = "Adds a Inventory")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public void add(@RequestBody List<InventoryForm> form) throws ApiException, JsonProcessingException {
        dto.add(form);
    }
    @ApiOperation(value = "Gets a Inventory by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }
    @ApiOperation(value = "Gets a Inventory by Barcode")
    @RequestMapping(path = "/b/{barCode}", method = RequestMethod.GET)
    public InventoryData get(@PathVariable String barCode) throws ApiException {
        return dto.get(barCode);
    }
    @ApiOperation(value = "Gets list of all Inventory")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<InventoryData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates an Inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventoryForm f) throws ApiException {
        dto.update(id, f);
    }

    @ApiOperation(value = "Export Product Report to CSV")
    @RequestMapping(path = "/exportcsv", method = RequestMethod.GET)
    public void exportToCSV(HttpServletResponse response) throws IOException, ApiException {
        dto.generateCsv(response);
    }


}

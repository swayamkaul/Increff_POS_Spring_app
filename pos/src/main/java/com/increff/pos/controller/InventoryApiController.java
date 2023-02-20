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
    private InventoryDto inventoryDto;

    @ApiOperation(value = "Adds Inventory List")
    @RequestMapping(method = RequestMethod.POST)
    public void addInventoryLsit(@RequestBody List<InventoryForm> form) throws ApiException, JsonProcessingException {
        inventoryDto.addInventoryList(form);
    }
    @ApiOperation(value = "Gets a Inventory by ID")
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public InventoryData getInventory(@PathVariable Integer id) throws ApiException {
        return inventoryDto.getInventory(id);
    }
    @ApiOperation(value = "Gets a Inventory by Barcode")
    @RequestMapping(path = "/barcode/{barCode}", method = RequestMethod.GET)
    public InventoryData getInventory(@PathVariable String barCode) throws ApiException {
        return inventoryDto.getInventory(barCode);
    }
    @ApiOperation(value = "Gets list of all Inventories")
    @RequestMapping(method = RequestMethod.GET)
    public List<InventoryData> getAllInventories() throws ApiException {
        return inventoryDto.getAllInventories();
    }

    @ApiOperation(value = "Updates an Inventory")
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public void updateInventory(@PathVariable Integer id, @RequestBody InventoryForm f) throws ApiException {
        inventoryDto.updateInventory(id, f);
    }

    @ApiOperation(value = "Export Inventory Report to CSV")
    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public void getInventoryReport(HttpServletResponse response) throws IOException, ApiException {
        inventoryDto.getInventoryReportCsv(response);
    }


}

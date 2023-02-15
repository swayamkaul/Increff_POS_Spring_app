package com.increff.pos.controller;

import com.increff.pos.dto.DailySalesDto;
import com.increff.pos.model.SalesForm;
import com.increff.pos.pojo.SalesPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/daily-sales")
public class DailySalesApiController {

    @Autowired
    private DailySalesDto dailySalesDto;

    @ApiOperation(value = "Gets all the Sales data")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<SalesPojo> getAll() throws ApiException {
        return dailySalesDto.getAll();
    }

    @ApiOperation(value = "get all sales date between 2 dates")
    @RequestMapping(path = "/filter", method = RequestMethod.POST)
    public List<SalesPojo> getAllByDate(@RequestBody SalesForm salesForm) throws ApiException {
        return dailySalesDto.getAllByDate(salesForm);
    }

    @ApiOperation(value = "Runs the scheduler")
    @RequestMapping(path = "/scheduler", method = RequestMethod.GET)
    public void runScheduler() throws ApiException {
        dailySalesDto.generateReport();
    }
}

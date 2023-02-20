package com.increff.pos.controller;

import com.increff.pos.dto.SalesReportDto;
import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/sales-report")
public class SalesReportApiController {
    @Autowired
    private SalesReportDto salesReportDto;

    @Autowired
    private OrderService orderService;

    @ApiOperation(value = "get data within filter")
    @RequestMapping(path = "/filter", method = RequestMethod.POST)
    public List<SalesReportData> getFilteredData(@RequestBody SalesReportForm salesReportForm) throws ApiException {
        return salesReportDto.getFilteredData(salesReportForm);
    }

    @ApiOperation(value = "Export Sales Report to CSV")
    @RequestMapping(path = "/report", method = RequestMethod.GET)
    public void getSalesReportInCsv(HttpServletResponse response) throws IOException {
        salesReportDto.getSalesReportCsv(response);
    }
}

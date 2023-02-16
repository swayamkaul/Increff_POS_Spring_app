package com.increff.pos.controller;


import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.BrandForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletResponse;

@Api
@RestController
@RequestMapping(path = "/api/brands")
public class BrandApiController {

	@Autowired
	private BrandDto brandDto;

	@ApiOperation(value = "Adds a Brand")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void add(@RequestBody List<BrandForm> form) throws ApiException, JsonProcessingException {
		brandDto.add(form);
	}
	@ApiOperation(value = "Gets a brand by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public BrandData get(@PathVariable Integer id) throws ApiException {
		return brandDto.get(id);
	}
	@ApiOperation(value = "Gets a brand by brand and category")
	@RequestMapping(path = "/{brand}/{category}", method = RequestMethod.GET)
	public BrandData get(@PathVariable String brand, @PathVariable String category) throws ApiException {
		return brandDto.get(brand,category);
	}
	@ApiOperation(value = "Gets list of all brand")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<BrandData> getAll() {
		return brandDto.getAll();
	}

	@ApiOperation(value = "Updates an brand")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable Integer id, @RequestBody BrandForm f) throws ApiException {
		brandDto.update(id, f);
	}

	@ApiOperation(value = "Exports to CSV")
	@RequestMapping(path = "/report", method = RequestMethod.GET)
	public void exportToCSV(HttpServletResponse response) throws IOException {
		brandDto.generateCsv(response);
	}


}

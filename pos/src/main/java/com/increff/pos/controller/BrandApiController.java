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
	@RequestMapping(method = RequestMethod.POST)
	public void addBrandList(@RequestBody List<BrandForm> form) throws ApiException, JsonProcessingException {
		brandDto.addBrandList(form);
	}
	@ApiOperation(value = "Gets a brand by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public BrandData getBrand(@PathVariable Integer id) throws ApiException {
		return brandDto.getBrand(id);
	}
	@ApiOperation(value = "Gets a brand by brand and category")
	@RequestMapping(path = "/{brand}/{category}", method = RequestMethod.GET)
	public BrandData getBrand(@PathVariable String brand, @PathVariable String category) throws ApiException {
		return brandDto.getBrand(brand,category);
	}
	@ApiOperation(value = "Gets list of all brand")
	@RequestMapping(method = RequestMethod.GET)
	public List<BrandData> getAllBrands() {
		return brandDto.getAllBrands();
	}

	@ApiOperation(value = "Updates an brand")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public void updateBrand(@PathVariable Integer id, @RequestBody BrandForm f) throws ApiException {
		brandDto.updateBrand(id, f);
	}

	@ApiOperation(value = "Gets report in CSV")
	@RequestMapping(path = "/report", method = RequestMethod.GET)
	public void getCsvReport(HttpServletResponse response) throws IOException {
		brandDto.getCsvReport(response);
	}


}

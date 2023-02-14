package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import com.increff.pos.AbstractUnitTest;
import com.increff.pos.model.BrandForm;
import com.increff.pos.util.NormaliseUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;

import java.util.List;

public class BrandServiceTest extends AbstractUnitTest {

	@Autowired
	BrandService brandService;

	@Test
	public void testBrandAddition() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand");
		brandPojo.setCategory("category");
		brandService.add(brandPojo);


		String expectedBrand = "brand";
		String expectedCategory = "category";

		BrandPojo brandPojo1 = brandService.getCheck(expectedBrand, expectedCategory);
		assertEquals(expectedBrand, brandPojo1.getBrand());
		assertEquals(expectedCategory, brandPojo1.getCategory());
	}

	@Test
	public void testGetAll() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand");
		brandPojo.setCategory("category");
		brandService.add(brandPojo);


		BrandPojo brandPojo1 = new BrandPojo();
		brandPojo1.setBrand("brand1");
		brandPojo1.setCategory("category1");
		brandService.add(brandPojo1);


		List<BrandPojo> list = brandService.getAll();
		assertEquals(2, list.size());
	}

	@Test(expected = ApiException.class)
	public void testCheckIllegalId() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand");
		brandPojo.setCategory("category");
		brandService.add(brandPojo);

		brandService.getCheck("brand", "category");
		brandService.getCheck(3);
	}

	@Test(expected = ApiException.class)
	public void testUpdate() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand");
		brandPojo.setCategory("category");
		brandService.add(brandPojo);

		BrandPojo brandPojo1 = new BrandPojo();
		brandPojo1.setBrand("brand1");
		brandPojo1.setCategory("category1");

		String expectedBrand = "brand1";
		String expectedCategory = "category1";

		BrandPojo pojo = brandService.getCheck("brand", "category");
		brandService.update(pojo.getId(), brandPojo1);
		BrandPojo pojo1 = brandService.getCheck(pojo.getId());
		assertEquals(expectedBrand, pojo1.getBrand());
		assertEquals(expectedCategory, pojo1.getCategory());

		brandService.checkAlreadyExist("brand1", "category1");
	}

	@Test(expected = ApiException.class)
	public void testCheckIllegal() throws ApiException {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand");
		brandPojo.setCategory("category");
		brandService.add(brandPojo);

		brandService.getCheck("brand", "category");

		brandService.getCheck("brand1", "category1");
	}

	@Test(expected = Exception.class)
	public void testCheckDuplicateBrandExists() throws Exception {
		BrandPojo brandPojo = new BrandPojo();
		brandPojo.setBrand("brand");
		brandPojo.setCategory("category");
		brandService.add(brandPojo);

		BrandPojo brandPojo1 = new BrandPojo();
		brandPojo1.setBrand("brand");
		brandPojo1.setCategory("category");
		brandService.add(brandPojo1);
	}


}

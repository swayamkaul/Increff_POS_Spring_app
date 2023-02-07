package com.increff.pos.service;

import static org.junit.Assert.assertEquals;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandForm;
import com.increff.pos.util.NormaliseUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.increff.pos.pojo.BrandPojo;

public class BrandServiceTest extends AbstractUnitTest {

	@Autowired
	private BrandService service;

	@Test
	public void testAdd() throws ApiException {
		BrandPojo p = new BrandPojo();
		p.setBrand(" Swayam Kaul ");
		p.setCategory(" Gadgets ");
		service.add(p);
	}

	@Test
	public void testNormalize() {
		BrandForm p = new BrandForm();
		p.setBrand(" Swayam Kaul ");
		NormaliseUtil.normalise(p);
		assertEquals("swayam kaul", p.getBrand());
	}

}

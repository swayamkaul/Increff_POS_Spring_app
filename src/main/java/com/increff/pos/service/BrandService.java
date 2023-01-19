package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.pojo.BrandPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class BrandService {

	@Autowired
	private BrandDao dao;

	public void add(BrandPojo p) throws ApiException {
		dao.insert(p);
	}


	public void delete(int id) {
		dao.delete(id);
	}


	public BrandPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	public BrandPojo get(String brand,String category) throws ApiException {
		return getCheck(brand,category);
	}

	@Transactional
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, BrandPojo p) throws ApiException {
		BrandPojo ex = getCheck(id);
		ex.setCategory(p.getCategory());
		ex.setBrand(p.getBrand());
		dao.update(ex);
	}

	@Transactional
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		if (p == null) {
			throw new ApiException("Brand with given ID does not exist, id: " + id);
		}
		return p;
	}
	@Transactional
	public BrandPojo getCheck(String brand,String category) throws ApiException {
		BrandPojo p = dao.select(brand,category);
		if (p == null) {
			throw new ApiException("Given Brand and Category does not exist, Brand: " + brand+", Category: "+category);
		}
		return p;
	}


}

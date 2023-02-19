package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.BrandPojo;

@Repository
@Transactional
public class BrandDao extends AbstractDao {

	private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
	private static final String SELECT_ALL = "select p from BrandPojo p";
	private static final String SELECT_BRAND_CATEGORY = "select p from BrandPojo p where brand=:brand and category=:category";


	public BrandPojo select(Integer id) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ID, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public BrandPojo select(String brand,String category){
		TypedQuery<BrandPojo> query = getQuery(SELECT_BRAND_CATEGORY, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category",category);
		return getSingle(query);
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
		return query.getResultList();
	}
	public void update(BrandPojo p) {
	}

}

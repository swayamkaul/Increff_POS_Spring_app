package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.BrandPojo;

@Repository
@Transactional
public class BrandDao extends AbstractDao {

	private static String select_id = "select p from BrandPojo p where id=:id";
	private static String select_all = "select p from BrandPojo p";
	private static String select_brand_category = "select p from BrandPojo p where brand=:brand and category=:category";

	@PersistenceContext
	private EntityManager em;


	public void insert(BrandPojo p) {
		em.persist(p);
	}

	public BrandPojo select(Integer id) {
		TypedQuery<BrandPojo> query = getQuery(select_id, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public BrandPojo select(String brand,String category){
		TypedQuery<BrandPojo> query = getQuery(select_brand_category, BrandPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category",category);
		return getSingle(query);
	}

	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(select_all, BrandPojo.class);
		return query.getResultList();
	}
	public void update(BrandPojo p) {
	}

}

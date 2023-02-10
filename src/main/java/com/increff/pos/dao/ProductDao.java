package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.ProductPojo;

@Repository
@Transactional
public class ProductDao extends AbstractDao {
    private static String select_id = "select p from ProductPojo p where id=:id";
    private static String select_bar_code = "select p from ProductPojo p where barCode=:barCode";
    private static String select_all = "select p from ProductPojo p";
    private final String SELECT_IN_BARCODE = "select p from ProductPojo p where barCode in (:barCode)";

    @PersistenceContext
    private EntityManager em;


    public void insert(ProductPojo p) {
        em.persist(p);
    }

    public ProductPojo select(int id) {
        TypedQuery<ProductPojo> query = getQuery(select_id, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    public ProductPojo select(String barCode) {
        TypedQuery<ProductPojo> query = getQuery(select_bar_code, ProductPojo.class);
        query.setParameter("barCode", barCode);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(select_all, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
    }
    public List<ProductPojo> selectInBarcode(List<String> barCode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_IN_BARCODE, ProductPojo.class);
        query.setParameter("barCode", barCode);
        return query.getResultList();
    }

}

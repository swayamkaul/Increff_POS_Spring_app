package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class ProductDao extends AbstractDao {
    private static final String SELECT_ID = "select p from ProductPojo p where id=:id";
    private static final String SELECT_BAR_CODE = "select p from ProductPojo p where barCode=:barCode";
    private static final String SELECT_ALL = "select p from ProductPojo p";
    private static final String SELECT_BY_BARCODES = "select p from ProductPojo p where barCode in (:barCodes)";

    public ProductPojo select(Integer id) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }
    public ProductPojo select(String barCode) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BAR_CODE, ProductPojo.class);
        query.setParameter("barCode", barCode);
        return getSingle(query);
    }

    public List<ProductPojo> selectAll() {
        TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
        return query.getResultList();
    }

    public void update(ProductPojo p) {
    }
    public List<ProductPojo> selectByBarcodeList(List<String> barCodes) {
        TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODES, ProductPojo.class);
        query.setParameter("barCodes", barCodes);
        return query.getResultList();
    }

}

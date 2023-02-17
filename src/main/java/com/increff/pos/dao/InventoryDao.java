package com.increff.pos.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.InventoryPojo;

@Repository
@Transactional
public class InventoryDao extends AbstractDao {
    private static String select_id = "select p from InventoryPojo p where id=:id";
    private static String select_all = "select p from InventoryPojo p";
    private static String select_in_ids = "select p from InventoryPojo p where id in (:id)";

    @PersistenceContext
    private EntityManager em;

    public void insert(InventoryPojo p) {
        em.persist(p);
    }

    public InventoryPojo select(Integer id) {
        TypedQuery<InventoryPojo> query = getQuery(select_id, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(select_all, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {
    }

    public List<InventoryPojo> selectInIds(List<Integer> idList) {
        TypedQuery<InventoryPojo> query = getQuery(select_in_ids, InventoryPojo.class);
        query.setParameter("id", idList);
        return query.getResultList();
    }
}

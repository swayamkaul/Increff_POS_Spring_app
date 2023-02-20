package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class InventoryDao extends AbstractDao {
    private static final String SELECT_ID = "select p from InventoryPojo p where id=:id";
    private static final String SELECT_ALL = "select p from InventoryPojo p";
    private static final String SELECT_IN_IDS = "select p from InventoryPojo p where id in (:id)";


    public InventoryPojo select(Integer id) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ID, InventoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<InventoryPojo> selectAll() {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
        return query.getResultList();
    }

    public void update(InventoryPojo p) {
    }

    public List<InventoryPojo> selectByIdList(List<Integer> idList) {
        TypedQuery<InventoryPojo> query = getQuery(SELECT_IN_IDS, InventoryPojo.class);
        query.setParameter("id", idList);
        return query.getResultList();
    }
}

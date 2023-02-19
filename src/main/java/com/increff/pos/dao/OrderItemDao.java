package com.increff.pos.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderItemPojo;

@Repository
@Transactional
public class OrderItemDao extends AbstractDao {

    private static final String SELECT_ID = "select p from OrderItemPojo p where id=:id";
    private static final String SELECT_OID = "select p from OrderItemPojo p where orderId=:orderId";
    private static final String SELECT_ALL = "select p from OrderItemPojo p";


    public OrderItemPojo select(Integer id) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderItemPojo> selectByOrderId(Integer orderId) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_OID, OrderItemPojo.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
        return query.getResultList();
    }


    public void update(OrderItemPojo p) {
    }



}

package com.increff.pos.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderPojo;

@Repository

@Transactional
public class OrderDao extends AbstractDao {

    private static final String SELECT_ID = "select p from OrderPojo p where id=:id";
    private static final String SELECT_ALL = "select p from OrderPojo p order by createdAt desc";
    private static final String SELECT_ALL_BY_DATE_FILTER = "select p from OrderPojo p where createdAt>=:startDate and createdAt<=:endDate";

    @PersistenceContext
    private EntityManager em;


    public Integer insert(OrderPojo p) {
        em.persist(p);
        return p.getId();
    }

    public OrderPojo select(Integer id) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ID, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll(Class<OrderPojo> orderPojoClass) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
        return query.getResultList();
    }

    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<OrderPojo> query = getQuery(SELECT_ALL_BY_DATE_FILTER, OrderPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }


    public void update(OrderPojo p) {
    }

}

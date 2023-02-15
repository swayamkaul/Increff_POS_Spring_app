package com.increff.pos.dao;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.pos.pojo.OrderPojo;

@Repository

@Transactional
public class OrderDao extends AbstractDao {

    private static String delete_id = "delete from OrderPojo p where id=:id";
    private static String select_id = "select p from OrderPojo p where id=:id";
    private static String select_all = "select p from OrderPojo p order by createdAt desc";

    private final String select_all_by_date_filter = "select p from OrderPojo p where createdAt>=:startDate and createdAt<=:endDate";


    @PersistenceContext
    private EntityManager em;


    public Integer insert(OrderPojo p) {
        em.persist(p);
        return p.getId();
    }

    public Integer delete(Integer id) {
        Query query = em.createQuery(delete_id);
        query.setParameter("id", id);
        return query.executeUpdate();
    }

    public OrderPojo select(Integer id) {
        TypedQuery<OrderPojo> query = getQuery(select_id, OrderPojo.class);
        query.setParameter("id", id);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll(Class<OrderPojo> orderPojoClass) {
        TypedQuery<OrderPojo> query = getQuery(select_all, OrderPojo.class);
        return query.getResultList();
    }

    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate) {
        TypedQuery<OrderPojo> query = getQuery(select_all_by_date_filter, OrderPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }


    public void update(OrderPojo p) {
    }

}

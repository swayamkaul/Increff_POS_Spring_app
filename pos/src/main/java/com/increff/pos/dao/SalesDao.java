package com.increff.pos.dao;

import com.increff.pos.pojo.SalesPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class SalesDao extends AbstractDao{

    private static final String SELECT_BY_DATE = "select p from SalesPojo p where date=:date";
    private static final String SELECT_ALL_BY_DATE = "select p from SalesPojo p where date>=:startDate and date<=:endDate";
    private static final String SELECT_ALL = "select p from SalesPojo p order by date desc";


    public SalesPojo selectByDate(LocalDate date) {
        TypedQuery<SalesPojo> query = getQuery(SELECT_BY_DATE, SalesPojo.class);
        query.setParameter("date", date);
        return query.getResultStream().findFirst().orElse(null);
    }

    public List<SalesPojo> getAllByDate(LocalDate startDate, LocalDate endDate) {
        TypedQuery<SalesPojo> query = getQuery(SELECT_ALL_BY_DATE, SalesPojo.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    public List<SalesPojo> selectAllDesc() {
        TypedQuery<SalesPojo> query = getQuery(SELECT_ALL, SalesPojo.class);
        return query.getResultList();
    }
}

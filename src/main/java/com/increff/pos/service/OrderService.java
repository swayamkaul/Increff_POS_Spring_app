package com.increff.pos.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;

@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderService {

    @Autowired
    private OrderDao dao;


    public OrderPojo add(OrderPojo p){
        p.setEditable(true);
        p.setId(dao.insert(p));
        return p;
    }


    public OrderPojo get(Integer id) throws ApiException{
        return getCheck(id);
    }


    public OrderPojo getCheck(Integer id) throws ApiException{
        OrderPojo p =dao.select(id);
        if(p == null){
            throw new ApiException("Order does not exists");
        }
        return p;
    }


    public List<OrderPojo> getAll(){
        return dao.selectAll(OrderPojo.class);
    }

    public OrderPojo update(Integer id,OrderPojo p) throws ApiException{
        return getCheck(id);
    }

    public void finaliseOrder(Integer id) throws ApiException{
        OrderPojo p1 = getCheck(id);
        p1.setEditable(false);
    }

    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate)  {
        return dao.getOrderByDateFilter(startDate,endDate);
    }
}

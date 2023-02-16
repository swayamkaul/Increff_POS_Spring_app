package com.increff.pos.service;

import java.time.LocalDateTime;
import java.util.List;



import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderDao;
import com.increff.pos.pojo.OrderPojo;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = ApiException.class)        //TODO Check transactionals in every file should be imported through spring
public class OrderService {

    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;
    @Autowired
    private InventoryService inventoryService;


    public OrderPojo add(OrderPojo p){
        p.setId(orderDao.insert(p));
        return p;
    }


    public OrderPojo get(Integer id) throws ApiException{
        return getCheck(id);
    }


    public OrderPojo getCheck(Integer id) throws ApiException{
        OrderPojo p = orderDao.select(id);
        if(p == null){
            throw new ApiException("Order does not exists");
        }
        return p;
    }


    public List<OrderPojo> getAll(){
        return orderDao.selectAll(OrderPojo.class);
    }

    public List<OrderPojo> getOrderByDateFilter(LocalDateTime startDate, LocalDateTime endDate)  {
        return orderDao.getOrderByDateFilter(startDate,endDate);
    }

    public OrderPojo addOrderItemListToOrder(List<OrderItemPojo> list) throws ApiException {
        OrderPojo order = add(new OrderPojo());
        for (OrderItemPojo p : list) {
            inventoryService.reduceQuantity(p.getProductId(), p.getQuantity());

            p.setOrderId(order.getId());
            orderItemDao.insert(p);
        }
        return order;
    }
    public List<OrderItemPojo> selectByOrderId(Integer orderId) {
        return orderItemDao.selectByOrderId(orderId);
    }
}

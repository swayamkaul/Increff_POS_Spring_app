package com.increff.pos.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;


@Service
@Transactional(rollbackOn = ApiException.class)
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private OrderService orderService;


    public OrderPojo add(List<OrderItemPojo> list) throws ApiException {
        OrderPojo order = orderService.add(new OrderPojo());
        int totalQuantity = 0;
        for (OrderItemPojo p : list) {
            totalQuantity += p.getQuantity();
            inventoryService.reduceQuantity(p.getProductId(), p.getQuantity());
            p.setOrderId(order.getId());
            dao.insert(p);
        }
        return order;
    }

    public void add(OrderItemPojo item) {
        dao.insert(item);
    }


    public OrderItemPojo selectById(Integer id) {
        return dao.select(id);
    }


    public List<OrderItemPojo> selectByOrderId(int orderId) {
        return dao.selectByOrderId(orderId);
    }


    public void update(Integer id, OrderItemPojo p) throws ApiException {


        OrderItemPojo p1 = selectById(id);

        if (p1.getProductId() != p.getProductId()) {
            throw new ApiException("Mismatch in product barcode provided");
        }

        OrderPojo op = orderService.update(p1.getOrderId(), null);

        if (!op.isEditable()) {
            throw new ApiException("Order is no longer editable");
        }

        if (p1.getQuantity() > p.getQuantity()) {
            inventoryService.increaseQuantity(p1.getProductId(), p1.getQuantity() - p.getQuantity());
        } else if (p1.getQuantity() < p.getQuantity()) {
            inventoryService.reduceQuantity(p1.getProductId(), p.getQuantity() - p1.getQuantity());
        }

        p1.setQuantity(p.getQuantity());
        p1.setSellingPrice(p.getSellingPrice());
    }
}
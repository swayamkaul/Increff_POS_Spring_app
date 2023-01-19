package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderItemData;
import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
public class OrderDto {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private ProductService productService;

    public OrderData add(OrderForm f) {
        OrderPojo p = new OrderPojo();
        OrderData data = ConvertorUtil.convert(orderService.add(p));
        return data;
    }

    public OrderData get(int id) throws ApiException {
        OrderPojo p = orderService.get(id);
        OrderData data = ConvertorUtil.convert(p);
        return data;
    }
    public List<OrderData> getAll() {
        List<OrderData> list = new ArrayList<OrderData>();
        List<OrderPojo> list1 = orderService.getAll();
        for (OrderPojo p : list1) {
            OrderData data = ConvertorUtil.convert(p);
            list.add(data);
        }
        return list;
    }


    public OrderData update(int id, OrderForm f) throws ApiException {
        OrderPojo p = orderService.update(id, null);
        OrderData data = ConvertorUtil.convert(p);
        return data;
    }


    public void finaliseOrder(int id) throws ApiException {
        orderService.finaliseOrder(id);
    }

    //Order Item DTO

    public OrderData add1(List<OrderItemForm> list) throws ApiException {
        List<OrderItemPojo> list1 = new ArrayList<OrderItemPojo>();

        Set<String> barcodeSet = new HashSet<String>();

        for (OrderItemForm f : list) {

            if(barcodeSet.contains(f.getBarCode())){
                throw new ApiException("Multiple entries of same Product in the order.");
            }
            barcodeSet.add(f.getBarCode());
            ProductPojo product = new ProductPojo();
            product = productService.get(f.getBarCode());
            OrderItemPojo p = ConvertorUtil.convert(f,product.getId());
            list1.add(p);
        }

        OrderPojo order = orderItemService.add(list1);

        OrderData data = ConvertorUtil.convert(order);


        return data;

    }

    public void addToExisitingOrder1(int orderId, OrderItemForm form) throws ApiException {
        orderService.getCheck(orderId);
        ProductPojo product = productService.get(form.getBarCode());
        OrderItemPojo item = ConvertorUtil.convert(form,product.getId(),orderId);
        orderItemService.add(item);

    }

    public OrderItemData getById1(Integer id) throws ApiException {
        OrderItemPojo p = orderItemService.selectById(id);
        ProductPojo product = productService.get(p.getProductId());
        OrderItemData data = ConvertorUtil.convert(p, product.getBarCode());
        data.setBarCode(product.getBarCode());
        return data;
    }

    public List<OrderItemData> getByOrderId1(Integer orderId) throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemService.selectByOrderId(orderId);

        for (OrderItemPojo p : list1) {

            ProductPojo product = productService.get(p.getProductId());
            OrderItemData data = ConvertorUtil.convert(p, product.getBarCode());
            data.setBarCode(product.getBarCode());

            list.add(data);
        }

        return list;

    }

    public void update1(Integer id, OrderItemForm f) throws ApiException {
        ProductPojo product = productService.get(f.getBarCode());
        OrderItemPojo p = ConvertorUtil.convert(f,product.getId(), orderItemService.selectById(id).getOrderId());
        orderItemService.update(id, p);
    }
}
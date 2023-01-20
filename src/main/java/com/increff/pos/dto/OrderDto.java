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
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;



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

    public OrderData addOrderItem(List<OrderItemForm> orderItemFormList) throws ApiException {
        List<OrderItemPojo> list1 = new ArrayList<OrderItemPojo>();

        HashSet<String> barcodeSet = new HashSet<String>();
        NormaliseUtil.normalise(orderItemFormList);
        ValidationUtil.validate(orderItemFormList);

        for (OrderItemForm f : orderItemFormList) {

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

    public void addItemToExisitingOrder(int orderId, OrderItemForm orderItemForm) throws ApiException {
        orderService.getCheck(orderId);
        NormaliseUtil.normalise(orderItemForm);
        ValidationUtil.validate(orderItemForm);
        ProductPojo product = productService.get(orderItemForm.getBarCode());
        List<OrderItemData> list1 = getItemByOrderId(orderId);
        for(OrderItemData orderItemData: list1){
            if(orderItemData.getBarCode().equals(orderItemForm.getBarCode())){
                throw new ApiException("Product already exists in the order! Barcode: "+orderItemForm.getBarCode());
            }
        }
        OrderItemPojo item = ConvertorUtil.convert(orderItemForm,product.getId(),orderId);

        orderItemService.add(item);

    }

    public OrderItemData getItemById(int id) throws ApiException {
        OrderItemPojo p = orderItemService.selectById(id);
        ProductPojo product = productService.get(p.getProductId());
        OrderItemData data = ConvertorUtil.convert(p, product.getBarCode());
        data.setBarCode(product.getBarCode());
        return data;
    }

    public List<OrderItemData> getItemByOrderId(int orderId) throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderItemService.selectByOrderId(orderId);

        for (OrderItemPojo p : list1) {
            ProductPojo product = productService.get(p.getProductId());
            OrderItemData data = ConvertorUtil.convert(p, product.getBarCode());
            list.add(data);
        }

        return list;

    }

    public void updateOrderItem(int id, OrderItemForm orderItemForm) throws ApiException {
        ValidationUtil.validate(orderItemForm);
        ProductPojo product = productService.get(orderItemForm.getBarCode());
        OrderItemPojo p = ConvertorUtil.convert(orderItemForm,product.getId(), orderItemService.selectById(id).getOrderId());
        orderItemService.update(id, p);
    }
}
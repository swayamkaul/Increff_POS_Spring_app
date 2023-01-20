package com.increff.pos.util;

import com.increff.pos.model.OrderItemForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

import java.util.List;

public class NormaliseUtil {
    public static void normalise(BrandPojo p){
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }
    public static void normalise(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

    public static void normalise(List<OrderItemForm> orderItemFormList){
        for (OrderItemForm f : orderItemFormList) {
            f.setBarCode(StringUtil.doTrim(f.getBarCode()));
        }

    }
    public static void normalise(OrderItemForm orderItemForm){
        orderItemForm.setBarCode(StringUtil.doTrim(orderItemForm.getBarCode()));
    }

}

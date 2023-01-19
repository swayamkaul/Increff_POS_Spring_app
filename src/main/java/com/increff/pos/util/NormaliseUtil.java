package com.increff.pos.util;

import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.ProductPojo;

public class NormaliseUtil {
    public static void normalise(BrandPojo p){
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));
        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }
    public static void normalise(ProductPojo p) {
        p.setName(StringUtil.toLowerCase(p.getName()));
    }

}

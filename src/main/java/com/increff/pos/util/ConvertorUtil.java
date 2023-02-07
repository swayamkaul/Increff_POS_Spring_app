package com.increff.pos.util;

import com.increff.pos.model.*;
import com.increff.pos.pojo.*;
import javafx.util.Pair;

import java.util.Map;

public class ConvertorUtil {
    public static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setCategory(p.getCategory());
        d.setBrand(p.getBrand());
        d.setId(p.getId());
        return d;
    }
    public static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setCategory(f.getCategory());
        p.setBrand(f.getBrand());
        return p;
    }
    public static ProductData convert(ProductPojo p,String brand,String category)  {
        ProductData d = new ProductData();
        d.setId(p.getId());
        d.setBarCode(p.getBarCode());
        d.setBrand(brand);
        d.setCategory(category);
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        return d;
    }

    public static  ProductPojo convert(ProductForm f, int brandCategory)  {
        ProductPojo p = new ProductPojo();
        p.setBrandCategory(brandCategory);
        p.setName(f.getName());
        p.setMrp((f.getMrp()));
        p.setBarCode(f.getBarCode());
        return p;
    }

    public static InventoryData convert(InventoryPojo p,String barCode,String name,String brand, String category)  {
        InventoryData d = new InventoryData();
        d.setBarCode(barCode);
        d.setName(name);
        d.setBrand(brand);
        d.setCategory(category);
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());
        return d;
    }
    public static  InventoryPojo convert(InventoryForm f, int id)  {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        p.setId(id);
        return p;
    }

    public static OrderItemPojo convert(OrderItemForm f, int productId)  {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setQuantity(f.getQuantity());
        orderItemPojo.setProductId(productId);
        orderItemPojo.setSellingPrice(f.getSellingPrice());
        return orderItemPojo;
    }

    public static OrderItemData convert(OrderItemPojo p,String barCode)  {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(p.getId());
        orderItemData.setOrderId(p.getOrderId());
        orderItemData.setBarCode(barCode);
        orderItemData.setQuantity(p.getQuantity());
        orderItemData.setSellingPrice(p.getSellingPrice());
        return orderItemData;
    }

    public static OrderData convert(OrderPojo p)  {
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        orderData.setUpdated(p.getUpdatedAt());
        return orderData;
    }

    public static InventoryReportData convertMapToItem(Map.Entry<Pair<String,String>, Integer> mapElement) {
        Pair<String, String> p = mapElement.getKey();
        InventoryReportData inventoryItem = new InventoryReportData();
        inventoryItem.setBrand(p.getKey());
        inventoryItem.setCategory(p.getValue());
        inventoryItem.setQuantity(mapElement.getValue());
        return inventoryItem;
    }
}

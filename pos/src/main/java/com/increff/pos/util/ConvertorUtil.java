package com.increff.pos.util;

import com.increff.pos.model.*;
import com.increff.pos.pojo.*;
import javafx.util.Pair;

import java.util.*;

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

    public static  ProductPojo convert(ProductForm f, Integer brandCategory)  {
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
    public static  InventoryPojo convert(InventoryForm f, Integer id)  {
        InventoryPojo p = new InventoryPojo();
        p.setQuantity(f.getQuantity());
        p.setId(id);
        return p;
    }

    public static OrderItemPojo convert(OrderItemForm f, Integer productId)  {
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
    public static UserData convert(UserPojo p) {
        UserData d = new UserData();
        d.setEmail(p.getEmail());
        d.setRole(p.getRole());
        d.setId(p.getId());
        return d;
    }

    public static UserPojo convert(UserForm f) {
        UserPojo p = new UserPojo();
        p.setEmail(f.getEmail());
        p.setRole(f.getRole());
        p.setPassword(f.getPassword());
        return p;
    }

    public static List<SalesReportData> convert(HashMap<Integer, SalesReportData> map, HashMap<Integer, BrandPojo> brandMap) {
        List<SalesReportData> salesReportDataList = new ArrayList<>();
        for(Map.Entry<Integer, SalesReportData> entry: map.entrySet()) {
            BrandPojo bp = brandMap.get(entry.getKey());
            SalesReportData d = entry.getValue();
            d.setBrand(bp.getBrand());
            d.setCategory(bp.getCategory());
            salesReportDataList.add(d);
        }
        return salesReportDataList;
    }

    public static BrandErrorData convertToErrorData(BrandForm f) {
        BrandErrorData brandErrorData = new BrandErrorData();
        brandErrorData.setCategory(f.getCategory());
        brandErrorData.setBrand(f.getBrand());
        brandErrorData.setMessage("");
        return brandErrorData;
    }

    public static  ProductErrorData convertToErrorData(ProductForm f)  {
        ProductErrorData productErrorData = new ProductErrorData();
        productErrorData.setName(f.getName());
        productErrorData.setMrp((f.getMrp()));
        productErrorData.setBrand((f.getBrand()));
        productErrorData.setCategory(f.getCategory());
        productErrorData.setBarCode(f.getBarCode());
        productErrorData.setMessage("");
        return productErrorData;
    }
    public static  InventoryErrorData convertToErrorData(InventoryForm f)  {
        InventoryErrorData inventoryErrorData = new InventoryErrorData();
        inventoryErrorData.setBarCode(f.getBarCode());
        inventoryErrorData.setQuantity((f.getQuantity()));
        inventoryErrorData.setMessage("");
        return inventoryErrorData;
    }

    public static List<String> convertInventoryFormListToBarCodeList(List<InventoryForm> inventoryFormList) {
        List<String> barCodeList=new ArrayList<>();
        for(InventoryForm inventoryForm:inventoryFormList){
            barCodeList.add(inventoryForm.getBarCode());
        }
        return barCodeList;
    }
    public static List<String> convertOrderItemFormListToBarCodeList(List<OrderItemForm> orderItemFormList) {
        List<String> barCodeList=new ArrayList<>();
        for(OrderItemForm orderItemForm: orderItemFormList){
            barCodeList.add(orderItemForm.getBarCode());
        }
        return barCodeList;
    }

    public static List<Integer> convertProductPojoHashMapToInventoryIdList(HashMap<String, ProductPojo> productPojoHashMap) {
        Collection<ProductPojo> productPojoList=  productPojoHashMap.values();
        List<Integer> idList = new ArrayList<>();
        for(ProductPojo productPojo: productPojoList){
            idList.add(productPojo.getId());
        }
        return idList;
    }
}

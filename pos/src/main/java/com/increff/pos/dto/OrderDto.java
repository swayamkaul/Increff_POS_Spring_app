package com.increff.pos.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.model.*;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.ErrorUtil;
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Component
public class OrderDto {

    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventoryService inventoryService;

    @Value("${invoice.url}")
    private String url;

    public List<OrderData> getAllOrders() {
        List<OrderData> list = new ArrayList<OrderData>();
        List<OrderPojo> list1 = orderService.getAll();
        for (OrderPojo p : list1) {
            OrderData data = ConvertorUtil.convert(p);
            list.add(data);
        }
        return list;
    }
    public OrderData createOrder(List<OrderItemForm> orderItemFormList) throws ApiException, JsonProcessingException {
        List<String> errorList = new ArrayList<String>();
        checkConstraintsAndDuplicates(orderItemFormList);
        List<OrderItemPojo> orderItemPojoList = checkSellingPriceAndInventory(orderItemFormList,errorList);
        OrderPojo orderPojo = orderService.addOrderItemListToOrder(orderItemPojoList);
        OrderData orderData = ConvertorUtil.convert(orderPojo);
        return orderData;
    }
    public List<OrderItemData> getItemByOrderId(Integer orderId) throws ApiException {
        List<OrderItemData> list = new ArrayList<OrderItemData>();
        List<OrderItemPojo> list1 = orderService.selectByOrderId(orderId);
        for (OrderItemPojo p : list1) {
            ProductPojo product = productService.getCheck(p.getProductId());
            OrderItemData data = ConvertorUtil.convert(p, product.getBarCode());
            list.add(data);
        }
        return list;
    }

    private void checkConstraintsAndDuplicates(List<OrderItemForm> forms) throws ApiException {
        if(forms.isEmpty()){
            throw new ApiException("Cart Empty!");
        }
        Set<String> set = new HashSet<>();
        for(OrderItemForm f : forms) {
            ValidateUtil.validateForms(f);
            NormaliseUtil.normalise(f);
            if(set.contains(f.getBarCode())) {
                throw new ApiException("Duplicate Barcode Detected, Barcode: "+f.getBarCode());
            }
            set.add(f.getBarCode());
        }
    }

    private List<OrderItemPojo>  checkSellingPriceAndInventory(List<OrderItemForm> forms,List<String> errorList) throws JsonProcessingException, ApiException {
        List<OrderItemPojo> orderItemPojoList=new ArrayList<>();
        List<String> barCodeList=ConvertorUtil.convertOrderItemFormListToBarCodeList(forms);
        HashMap<String,ProductPojo> productPojoHashMap = productService.getProuctMapByBarcodeList(barCodeList);
        List<Integer> idList=ConvertorUtil.convertProductPojoHashMapToInventoryIdList(productPojoHashMap);
        HashMap<Integer,InventoryPojo> inventoryPojoHashMap = inventoryService.getInventoryMapByIdList(idList);
        for(OrderItemForm orderItemForm : forms) {
            try {
                if(!productPojoHashMap.containsKey(orderItemForm.getBarCode())){
                    errorList.add("Product with given Barcode does not exist. Barcode: "+ orderItemForm.getBarCode());
                }
                ProductPojo productPojo = productPojoHashMap.get(orderItemForm.getBarCode());
                InventoryPojo inventoryPojo = inventoryPojoHashMap.get(productPojo.getId());
                if(productPojo.getMrp() < orderItemForm.getSellingPrice()){
                    errorList.add("Selling Price more than MRP for Barcode: "+orderItemForm.getBarCode());
                }
                if(Objects.isNull(inventoryPojo) || inventoryPojo.getQuantity() < orderItemForm.getQuantity()){
                    errorList.add("Insufficient Inventory for Barcode: "+orderItemForm.getBarCode());
                }
                OrderItemPojo p = ConvertorUtil.convert(orderItemForm, productPojo.getId());
                orderItemPojoList.add(p);
            }catch (Exception e){
                errorList.add(orderItemForm.getBarCode()+": "+e.getMessage());
            }
        }
        throwErrorsIfAny(errorList);
        return orderItemPojoList;
    }
    private void throwErrorsIfAny(List<String> errorlist) throws ApiException, JsonProcessingException {
        if(!errorlist.isEmpty()){
            ErrorUtil.throwErrors(errorlist);
        }
    }

    public ResponseEntity<byte[]> getInvoicePDF(Integer id) throws Exception {
        InvoiceForm invoiceForm = generateInvoiceForOrder(id);
        RestTemplate restTemplate = new RestTemplate();
        byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }
    public InvoiceForm generateInvoiceForOrder(Integer orderId) throws ApiException
    {
        InvoiceForm invoiceForm = new InvoiceForm();
        OrderPojo orderPojo = orderService.get(orderId);
        invoiceForm.setOrderId(orderPojo.getId());
        invoiceForm.setPlacedDate(orderPojo.getCreatedAt().toString());
        List<OrderItemPojo> orderItemPojoList = orderService.selectByOrderId(orderPojo.getId());
        List<OrderItem> orderItemList = new ArrayList<>();
        for(OrderItemPojo p: orderItemPojoList) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(p.getId());
            String productName = productService.getCheck(p.getProductId()).getName();
            orderItem.setProductName(productName);
            orderItem.setQuantity(p.getQuantity());
            orderItem.setSellingPrice(p.getSellingPrice());
            orderItemList.add(orderItem);
        }
        invoiceForm.setOrderItemList(orderItemList);
        return invoiceForm;
    }
}
package com.increff.pos.dto;

import com.increff.pos.model.*;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderItemService;
import com.increff.pos.service.OrderService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.ConvertorUtil;
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.ValidateUtil;
import com.increff.pos.util.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
        for (OrderItemForm f : orderItemFormList) {
            ValidateUtil.validateForms(f);
            NormaliseUtil.normalise(orderItemFormList);
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

    public ResponseEntity<byte[]> getPDF(int id) throws Exception {
        InvoiceForm invoiceForm = generateInvoiceForOrder(id);

        RestTemplate restTemplate = new RestTemplate();

        //TODO this url in properties file
        String url = "http://localhost:8085/fop/api/invoice";

        byte[] contents = Base64.getDecoder().decode(restTemplate.postForEntity(url, invoiceForm, byte[].class).getBody());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);

        String filename = "invoice.pdf";
        headers.setContentDispositionFormData(filename, filename);

        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
        return response;
    }

    public InvoiceForm generateInvoiceForOrder(int orderId) throws ApiException
    {
        InvoiceForm invoiceForm = new InvoiceForm();
        OrderPojo orderPojo = orderService.get(orderId);

        invoiceForm.setOrderId(orderPojo.getId());
        invoiceForm.setPlaceDate(orderPojo.getCreatedAt().toString());

        List<OrderItemPojo> orderItemPojoList = orderItemService.selectByOrderId(orderPojo.getId());
        List<OrderItem> orderItemList = new ArrayList<>();

        for(OrderItemPojo p: orderItemPojoList)
        {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderItemId(p.getId());
            String productName = productService.get(p.getProductId()).getName();
            orderItem.setProductName(productName);
            orderItem.setQuantity(p.getQuantity());
            orderItem.setSellingPrice(p.getSellingPrice());
            orderItemList.add(orderItem);
        }

        invoiceForm.setOrderItemList(orderItemList);

        return invoiceForm;
    }
}
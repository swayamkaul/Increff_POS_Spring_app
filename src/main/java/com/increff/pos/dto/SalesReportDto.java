package com.increff.pos.dto;

import com.increff.pos.model.SalesReportData;
import com.increff.pos.model.SalesReportForm;
import com.increff.pos.pojo.BrandPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.OrderPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.*;
import com.increff.pos.util.CsvFileGenerator;
import com.increff.pos.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class SalesReportDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;

    @Autowired
    private BrandService brandService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CsvFileGenerator csvGenerator;

    protected List<SalesReportData> salesList = new ArrayList<>();

    public List<SalesReportData> getAll() throws ApiException {
        List<OrderPojo> list = orderService.getAll();
        return getFilterSalesReport(list, "all", "all");
    }

    public List<SalesReportData> getFilteredData(SalesReportForm salesReportForm) throws ApiException {
        String startDate = salesReportForm.getStartDate() + " 00:00:00";
        String endDate = salesReportForm.getEndDate() + " 23:59:59";
        ValidateUtil.validateForms(salesReportForm);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime sDate = LocalDateTime.parse(startDate, formatter);
        LocalDateTime eDate = LocalDateTime.parse(endDate, formatter);
        if(sDate.isAfter(eDate)){
            throw new ApiException("Start Date cannot be after End Date");
        }
        List<OrderPojo> list = orderService.getOrderByDateFilter(sDate,eDate);
        return getFilterSalesReport(list,salesReportForm.getBrand(), salesReportForm.getCategory());
    }

    public List<SalesReportData> getFilterSalesReport(List<OrderPojo> list, String brand, String category) throws ApiException {
        HashMap<Integer, SalesReportData> map = new HashMap<Integer, SalesReportData>();
        for(OrderPojo orderPojo: list){
            List<OrderItemPojo> orderItemPojoList = orderItemService.selectByOrderId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo: orderItemPojoList) {
                ProductPojo productPojo = productService.getCheck(orderItemPojo.getProductId());
                BrandPojo brandPojo = brandService.getCheck(productPojo.getBrandCategory());
                if(!map.containsKey(brandPojo.getId())) {
                    map.put(brandPojo.getId(), new SalesReportData());
                }
                SalesReportData salesReportData = map.get(brandPojo.getId());
                salesReportData.setQuantity(salesReportData.getQuantity() + orderItemPojo.getQuantity() );
                salesReportData.setRevenue(salesReportData.getRevenue() + (orderItemPojo.getQuantity() * orderItemPojo.getSellingPrice()));

            }
        }

        List<SalesReportData> salesReportDataList = new ArrayList<>();

        for(Map.Entry<Integer, SalesReportData> entry: map.entrySet()) {
            BrandPojo bp = brandService.getCheck(entry.getKey());
            if((Objects.equals(brand,bp.getBrand()) || Objects.equals(brand,"all")) && (Objects.equals(category,bp.getCategory()) || Objects.equals(category,"all"))){
                SalesReportData d = entry.getValue();
                d.setBrand(bp.getBrand());
                d.setCategory(bp.getCategory());
                salesReportDataList.add(d);
            }
        }
        salesList = salesReportDataList;
        return salesReportDataList;
    }
    public void generateCsv(HttpServletResponse response) throws  IOException {
        response.setContentType("text/csv");
        response.addHeader("Content-Disposition", "attachment; filename=\"salesReport.csv\"");

        csvGenerator.writeSalesToCsv(salesList, response.getWriter());
        salesList.clear();
    }
}

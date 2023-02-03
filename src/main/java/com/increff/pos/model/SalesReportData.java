package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SalesReportData {

    private String brand;
    private String category;
    private int quantity;
    private double revenue;

    public SalesReportData() {
        this.setQuantity(0);
        this.setRevenue(0.0);
    }
}

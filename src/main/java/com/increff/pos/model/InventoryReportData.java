package com.increff.pos.model;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryReportData {
    private int id;
    private String brand;
    private String category;
    private int quantity;
}

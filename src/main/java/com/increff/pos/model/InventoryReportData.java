package com.increff.pos.model;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InventoryReportData {
    private Integer id;
    private String brand;
    private String category;
    private Integer quantity;
}

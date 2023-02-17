package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductErrorData {
    private String barCode;
    private String brand;
    private String category;
    private String name;
    private Double mrp;
    private String message;
}

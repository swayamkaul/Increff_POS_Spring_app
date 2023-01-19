package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductForm {
    @NotNull
    private String barCode;
    @NotNull
    private String brand;
    @NotNull
    private String category;
    @NotNull
    private String name;
    @NotNull
    private Double mrp;
}

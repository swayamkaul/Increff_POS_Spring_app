package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ProductForm {
    @NotBlank
    private String barCode;
    @NotBlank
    private String brand;
    @NotBlank
    private String category;
    @NotBlank
    private String name;
    @NotNull
    private Double mrp;
}

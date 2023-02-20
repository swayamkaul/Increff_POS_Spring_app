package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ProductForm {
    @NotBlank
    @Size(min = 1, max = 15)
    private String barCode;
    @NotBlank
    @Size(min = 1, max = 100)
    private String brand;
    @NotBlank
    @Size(min = 1, max = 100)
    private String category;
    @NotBlank
    @Size(min = 1, max = 100)
    private String name;
    @NotNull
    @Min(value=0)
    private Double mrp;
}

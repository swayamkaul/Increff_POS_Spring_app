package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrderItemForm {
    @NotBlank
    private String barCode;
    @NotNull
    @Min(value=1)
    private Integer quantity;
    @NotNull
    @Min(value=1)
    private double sellingPrice;
}

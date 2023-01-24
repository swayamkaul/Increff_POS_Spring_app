package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class OrderItemForm {
    @NotBlank
    private String barCode;
    @NotNull
    private Integer quantity;
    @NotNull
    private double sellingPrice;
}

package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemForm {
    @NotNull
    private String barCode;
    @NotNull
    private Integer quantity;
    @NotNull
    private double sellingPrice;
}

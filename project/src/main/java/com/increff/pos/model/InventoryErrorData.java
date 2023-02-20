package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryErrorData {
    private String barCode;
    private Integer quantity;
    private String message;
}

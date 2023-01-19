package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class InventoryForm {
    @NotNull
    private String barCode;
    @NotNull
    private int quantity;
}

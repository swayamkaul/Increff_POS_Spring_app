package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class InventoryForm {
    @NotBlank
    private String barCode;
    @NotNull
    private int quantity;
}

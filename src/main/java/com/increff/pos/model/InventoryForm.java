package com.increff.pos.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InventoryForm {
    @NotBlank
    private String barCode;
    @NotNull
    private Integer quantity;
}

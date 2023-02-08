package com.increff.pos.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class InventoryForm {
    @NotBlank
    private String barCode;
    @Min(value=1)
    @NotNull
    private Integer quantity;
}

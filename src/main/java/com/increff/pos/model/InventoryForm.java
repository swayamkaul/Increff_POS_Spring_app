package com.increff.pos.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class InventoryForm {
    @NotBlank
    @Size(min = 1, max = 15)
    private String barCode;
    @Min(value=0)
    @NotNull
    private Integer quantity;
}

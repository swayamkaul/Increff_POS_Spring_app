package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
public class OrderItemForm {
    @NotBlank
    @Size(min = 1, max = 15)
    private String barCode;
    @NotNull
    @Min(value=1)
    private Integer quantity;
    @NotNull
    @Min(value=0)
    private Double sellingPrice;
}

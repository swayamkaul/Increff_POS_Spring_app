package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
//TODO clean annotations
public class InventoryPojo extends AbstractVersionPojo{
    @Id
    private int id;
    @NotNull
    @Min(value = 1)
    private int quantity;

}

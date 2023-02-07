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
    @Column(nullable = false)
    @Min(value = 0)
    private int quantity;

}

package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"id"}) })
public class InventoryPojo extends AbstractVersionPojo{
    @Id
    @Column(name="id",nullable = false)
    private int id;
    @Column(name = "quantity",nullable = false)
    private int quantity;

}

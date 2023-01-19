package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
//TODO add unique constraint
//TODO add indexes depending on the select queries you're running
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"barCode"}) })
public class ProductPojo extends AbstractVersionPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @Column(name = "barCode",nullable = false)
    private String barCode;

    //TODO change it to camelCase, db column should  be in snakeCase DONE
    @Column(name = "brand_category",nullable = false)
    private int brandCategory;
    @Column(name = "name",nullable = false)
    private String name;
    @Column(name = "mrp",nullable = false)
    private Double mrp;
}

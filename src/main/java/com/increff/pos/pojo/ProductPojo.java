package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
//TODO add indexes depending on the select queries you're running
//TODO hibernate snakecase naming stg
@Table(indexes={@Index(columnList = "barcode", unique = true)},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})})
public class ProductPojo extends AbstractVersionPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    @NotBlank
    private String barCode;
    //TODO change it to camelCase, db column should  be in snakeCase DONE
    @Column(nullable = false)
    private int brandCategory;
    @NotBlank
    private String name;
    @Column(nullable = false)
    @Min(value = 0)
    private Double mrp;
}

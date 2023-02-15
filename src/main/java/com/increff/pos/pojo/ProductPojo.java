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
@Table(indexes={@Index(columnList = "barcode", unique = true)},
        uniqueConstraints = {@UniqueConstraint(columnNames = {"barcode"})})
public class ProductPojo extends AbstractVersionPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private String barCode;
    @Column(nullable = false)
    private Integer brandCategory;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    @Min(value = 0)
    private Double mrp;
}

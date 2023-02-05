package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"orderId","productId"}) })
public class OrderItemPojo extends AbstractVersionPojo{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    private Integer orderId;
    @Column(nullable = false)
    private Integer productId;
    @Column(nullable = false)
    @Min(value = 1)
    private Integer quantity;
    @Column(nullable = false)
    @Min(value = 0)
    private Double sellingPrice;

}

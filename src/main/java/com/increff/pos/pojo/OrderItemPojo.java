package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"id","orderId"}) })
public class OrderItemPojo extends AbstractVersionPojo{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "orderId", nullable = false)
    private Integer orderId;
    @Column(name = "productId", nullable = false)
    private Integer productId;
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    @Column(name = "sellingPrice", nullable = false)
    private double sellingPrice;

}

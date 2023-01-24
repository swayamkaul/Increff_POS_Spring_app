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
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer productId;
    @NotNull
    @Min(value = 1)
    private Integer quantity;
    @NotNull
    @Min(value = 0)
    private Double sellingPrice;

}

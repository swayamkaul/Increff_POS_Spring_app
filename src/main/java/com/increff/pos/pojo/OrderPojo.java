package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class OrderPojo extends AbstractVersionPojo{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;
    private boolean editable;//To be removed

}
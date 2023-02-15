package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryData extends InventoryForm{
private Integer id;
private String brand;
private String category;
private String name;
}

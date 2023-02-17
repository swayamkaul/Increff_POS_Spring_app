package com.increff.pos.pojo;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@Setter
@Table(indexes ={@Index(columnList = "brand,category")},
		uniqueConstraints = {@UniqueConstraint(columnNames = {"brand", "category"})})

public class BrandPojo extends AbstractVersionPojo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String brand;
	@Column(nullable = false)
	private String category;

}

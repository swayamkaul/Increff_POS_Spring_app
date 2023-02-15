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
//TODO create AbstractVersionPojo, createdAt, UpdatedAt,version
//TODO what is the use of version  (Optimistic and pessimistic locking algorithms)
public class BrandPojo extends AbstractVersionPojo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false)
	private String brand;
	@Column(nullable = false)
	private String category;

}

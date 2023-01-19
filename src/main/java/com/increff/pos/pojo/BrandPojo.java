package com.increff.pos.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "brand", "category" }) })
//TODO create AbstractVersionPojo, createdAt, UpdatedAt,version DONE
//TODO what is the use of version DONE (Optimistic and pessimistic locking algorithms)
public class BrandPojo extends AbstractVersionPojo{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private int id;
	@Column(name = "brand",nullable = false)
	private String brand;
	@Column(name = "category",nullable = false)
	private String category;

}

package com.increff.pos.model;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//TODO Add @NotNull on form variables	DONE
public class BrandForm {
	@NotNull
	private String brand;
	@NotNull
	private String category;
}

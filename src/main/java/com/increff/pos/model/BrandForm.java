package com.increff.pos.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
//TODO Add @NotNull on form variables	DONE
public class BrandForm {
	@NotBlank
	private String brand;
	@NotBlank
	private String category;
}

package com.increff.pos.model;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
	@NotNull
	private String email;
	@NotNull
	private String password;
}

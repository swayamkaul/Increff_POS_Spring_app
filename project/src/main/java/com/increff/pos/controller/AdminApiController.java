package com.increff.pos.controller;

import java.util.List;

import com.increff.pos.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.pos.model.UserData;
import com.increff.pos.model.UserForm;
import com.increff.pos.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping(path = "/api/admin/user")
public class AdminApiController {

	@Autowired
	UserDto userDto;

	@ApiOperation(value = "Adds a user")
	@RequestMapping(path = "", method = RequestMethod.POST)
	public void addUser(@RequestBody UserForm form) throws ApiException {
		userDto.add(form);
	}
	@ApiOperation(value = "Deletes a user")
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable Integer id) {
		userDto.delete(id);
	}


	@ApiOperation(value = "Gets list of all users")
	@RequestMapping(path = "", method = RequestMethod.GET)
	public List<UserData> getAllUser() {
		return userDto.getAllUser();
	}


}

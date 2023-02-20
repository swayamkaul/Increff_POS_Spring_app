package com.increff.pos.controller;

import java.util.Objects;

import com.increff.pos.dto.UserDto;
import com.increff.pos.util.NormaliseUtil;
import com.increff.pos.util.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.increff.pos.model.InfoData;
import com.increff.pos.model.UserForm;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping(path = "/site/init")
public class InitApiController extends AbstractUiController {

	@Autowired
	private UserService service;
	@Autowired
	private InfoData info;

	@Autowired
	private UserDto userDto;
	@Value("${app.admin.email}")
	private String admin_email;

	@ApiOperation(value = "Initializes application")
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView showPage(UserForm form) {
		info.setMessage("");
		return mav("init.html");
	}

	@ApiOperation(value = "Initializes application")
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView initSite(UserForm form) throws ApiException {
		ValidateUtil.validateForms(form);
		NormaliseUtil.normalise(form);
		if (userDto.checkEmailExists(form.getEmail())) {
			info.setMessage("Account already Exists, please use existing credentials");
		}
		else if(Objects.equals(form.getEmail(), admin_email))
		{
			form.setRole("supervisor");
			userDto.add(form);
			info.setMessage("Signed Up Successfully, you can login now");
		}
		else
		{
			form.setRole("operator");
			userDto.add(form);
			info.setMessage("Signed Up Successfully, you can login now");
		}
		return mav("init.html");
	}


}

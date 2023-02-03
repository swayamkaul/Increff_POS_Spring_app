package com.increff.pos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/brands")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/products")
	public ModelAndView product() {
		return mav("product.html");
	}
	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}
	@RequestMapping(value = "/ui/orders")
	public ModelAndView order() {
		return mav("order.html");
	}
	@RequestMapping(value = "/ui/dailysales")
	public ModelAndView DailySales() {
		return mav("dailysales.html");
	}
	@RequestMapping(value = "/ui/salesreport")
	public ModelAndView SalesReport() {
		return mav("salesreport.html");
	}
	@RequestMapping(value = "/ui/admin")
	public ModelAndView admin() {
		return mav("user.html");
	}

}

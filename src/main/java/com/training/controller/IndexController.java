package com.training.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.training.model.ResponseDataModel;
import com.training.service.IProductService;

@Controller
@RequestMapping(value = { "/" })
public class IndexController {
	
	@Autowired
	IProductService productService;

	@GetMapping
	public String initPage() {
		return "index";
	}

	@GetMapping(value = { "/home" })
	public String homePage() {
		return "homePage";
	}

	@GetMapping(value = { "login" })
	public String login() {
		return "login";
	}

	@PostMapping(value = { "api/search/{pageNumber}" })
	@ResponseBody
	public ResponseDataModel searchApi(@RequestBody Map<String, Object> searchConditions,
			@PathVariable("pageNumber") int pageNumber) {
		return productService.searchWithConditionsWeb(searchConditions, pageNumber);
	}
}
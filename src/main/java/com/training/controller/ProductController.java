package com.training.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.training.entity.ProductEntity;
import com.training.model.ResponseDataModel;
import com.training.service.IBrandService;
import com.training.service.IProductService;

@Controller
@RequestMapping(value = { "/product" })
public class ProductController {

	@Autowired
	IProductService productService;
	@Autowired
	IBrandService brandService;

	@GetMapping
	public String initPage(Model model) {
		return "product-index";
	}

	@PostMapping(value = { "/add" })
	public String add(@ModelAttribute ProductEntity productEntity, RedirectAttributes redirectAttributes) {
		if (productService.findByProductName(productEntity.getProductName()) != null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Product Name is duplicated");
			return "redirect:/product/add";
		}
		ProductEntity productEntityUpdate = productService.add(productEntity);
		if (productEntityUpdate == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Add Product Fail");
			return "redirect:/product/add";
		} else {
			return "redirect:/product";
		}
	}

	@GetMapping("/api/find")
	@ResponseBody
	public ResponseDataModel findProductByIdApi(@RequestParam("id") Long productId) {
		return productService.findByProductIdApi(productId);
	}

	@PostMapping(value = { "/update" })
	public String update(@ModelAttribute ProductEntity productEntity, RedirectAttributes redirectAttributes) {
		ProductEntity productEntityUpdate = productService.update(productEntity);
		if (productEntityUpdate == null) {
			redirectAttributes.addFlashAttribute("errorMessage", "Update Product Fail");
			return "redirect:/product/update?id=" + productEntity.getProductId();
		} else {
			return "redirect:/product";
		}

	}

	@PostMapping(value = { "/api/add" })
	@ResponseBody
	public ResponseDataModel addApi(@ModelAttribute ProductEntity productEntity) {
		return productService.addApi(productEntity);
	}

	@PostMapping(value = { "/api/update" })
	@ResponseBody
	public ResponseDataModel updateApi(@ModelAttribute ProductEntity productEntity) {
		return productService.updateApi(productEntity);
	}

	@DeleteMapping(value = { "/api/delete/{productId}" })
	@ResponseBody
	public ResponseDataModel deleteApi(@PathVariable("productId") Long productId) {
		return productService.deleteApi(productId);
	}

	@GetMapping(value = { "/api/findAll/{pageNumber}" })
	@ResponseBody
	public ResponseDataModel findAllWithPagerApi(@PathVariable("pageNumber") int page) {
		return productService.findAllWithPagerApi(page);
	}

	@PostMapping(value = { "/api/search/{pageNumber}" })
	@ResponseBody
	public ResponseDataModel searchApi(@RequestBody Map<String, Object> searchConditions,
			@PathVariable("pageNumber") int pageNumber) {
		return productService.searchWithConditions(searchConditions, pageNumber);
	}

	@GetMapping(value = { "/api/brandList" })
	@ResponseBody
	public ResponseDataModel brandList() {
		return brandService.getAllApi();
	}
	@PostMapping(value = {"api/productList"})
	@ResponseBody
	public Map<String, Object> productList (@RequestBody Map<String, Object> conditionsMap) {
		return productService.findAllApi(conditionsMap);
		
	}
}

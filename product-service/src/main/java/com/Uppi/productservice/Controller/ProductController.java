package com.Uppi.productservice.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Uppi.productservice.Service.ProductService;
import com.Uppi.productservice.dto.ProductRequest;
import com.Uppi.productservice.dto.ProductResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/product")
//@RequiredArgsConstructor
public class ProductController {

	private ProductService productService;
	
	public ProductController(ProductService productService) {
		super();
		this.productService = productService;
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public void CreateMapping(@RequestBody ProductRequest productRequest) {
		productService.CreateProduct(productRequest);
	}
	
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ProductResponse> getAllProducts(){
		return productService.getAllProducts();
	}

}

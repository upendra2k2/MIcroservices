package com.Uppi.productservice.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Uppi.productservice.Model.Product;
import com.Uppi.productservice.Repository.ProductRepository;
import com.Uppi.productservice.dto.ProductRequest;
import com.Uppi.productservice.dto.ProductResponse;

import lombok.Builder;
import lombok.RequiredArgsConstructor;


@Service
//@RequiredArgsConstructor
public class ProductService {

	private ProductRepository productRepository;
	
	
	public ProductService(ProductRepository productRepository) {
		super();
		this.productRepository = productRepository;
	}
	
	public void CreateProduct(ProductRequest productRequest) {
		Product product=Product.builder()
								.name(productRequest.getName())
								.description(productRequest.getDescription())
								.price(productRequest.getPrice())
								.build();
		
		productRepository.save(product);
		System.out.println("Product " +product.getId()+" is Saved");	
	}
	
	public List<ProductResponse> getAllProducts(){
		
		List<Product> products=productRepository.findAll();
		
		return products.stream().map(this::mapToProductResponse).toList();
	}
	
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
								.id(product.getId())
								.name(product.getName())
								.description(product.getDescription())
								.price(product.getPrice())
								.build();
	}

}

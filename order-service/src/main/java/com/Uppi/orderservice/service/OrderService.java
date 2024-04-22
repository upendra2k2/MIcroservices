package com.Uppi.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.Uppi.orderservice.dto.InventoryResponse;
import com.Uppi.orderservice.dto.OrderLineItemsDto;
import com.Uppi.orderservice.dto.OrderRequest;
import com.Uppi.orderservice.model.Order;
import com.Uppi.orderservice.model.orderLineItems;
import com.Uppi.orderservice.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
//@RequiredArgsConstructor
public class OrderService {

	private OrderRepository orderRepository;
	private WebClient webClient;
	
	
	
	public OrderService(OrderRepository orderRepository,WebClient webClient) {
		super();
		this.orderRepository = orderRepository;
		this.webClient=webClient;
	}

	public void placeOrder(OrderRequest orderRequest) {
		Order order=new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<orderLineItems> orderLineItems1=orderRequest.getOrderLineItemsDtoList()
												.stream().map(this::mapToDto).toList();
		
		order.setOrderLineItemsList(orderLineItems1);
		
		List<String> skucodes=order.getOrderLineItemsList().stream().map(orderLineItems::getSkuCode).toList();
		
		//call inventory service if the product is in stock
		
		InventoryResponse[] inventoryResponseArray=webClient.get()
				 					.uri("http://localhost:8082/api/inventory",
				 							uriBuilder->uriBuilder.queryParam("skuCode", skucodes).build())
				 					.retrieve()
				 					.bodyToMono(InventoryResponse[].class)
				 					.block();
		
		boolean allProductsInStock=Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
		
		if(allProductsInStock) {			
			orderRepository.save(order);
		}
		else {
			throw new IllegalArgumentException("product is not in stock, please try again later");
		}
	
	}
	
	public orderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		orderLineItems orderLineItems1=new orderLineItems();
		orderLineItems1.setPrice(orderLineItemsDto.getPrice());
		orderLineItems1.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems1.setSkuCode(orderLineItemsDto.getSkuCode());
		
		return orderLineItems1;
	}
}

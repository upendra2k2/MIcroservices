package com.Uppi.Inventoryservice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Uppi.Inventoryservice.Repository.InventoryRepository;
import com.Uppi.Inventoryservice.dto.InventoryResponse;
import com.Uppi.Inventoryservice.model.Inventory;

@Service
public class InventoryService {

	private InventoryRepository inventoryRepository;
	
	
	
	public InventoryService(InventoryRepository inventoryRepository) {
		super();
		this.inventoryRepository = inventoryRepository;
	}


	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		return inventoryRepository.findBySkuCodeIn(skuCode).stream()
											.map(inventory->
												InventoryResponse.builder()
															.skuCode(inventory.getSkuCode())
															.isInStock(inventory.getQuantity()>0)
															.build()
											).toList();
											
	}
}

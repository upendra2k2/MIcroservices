package com.Uppi.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Uppi.orderservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}

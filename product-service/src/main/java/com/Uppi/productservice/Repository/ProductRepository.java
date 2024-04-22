package com.Uppi.productservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.Uppi.productservice.Model.Product;

public interface ProductRepository extends MongoRepository<Product, String>{

}

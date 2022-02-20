package com.hepsiburada.ecommerce.repository;

import com.hepsiburada.ecommerce.model.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, String> {

    Optional<Product> findByProductCode(String name);


}

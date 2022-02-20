package com.hepsiburada.ecommerce.repository;

import com.hepsiburada.ecommerce.model.ProductOrder;
import org.springframework.data.repository.CrudRepository;

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {

}

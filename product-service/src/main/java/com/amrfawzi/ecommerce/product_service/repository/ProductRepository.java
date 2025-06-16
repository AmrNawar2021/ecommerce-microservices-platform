package com.amrfawzi.ecommerce.product_service.repository;


import com.amrfawzi.ecommerce.product_service.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Product findByName(String name);
    List<Product> findByOwnerId(Long ownerId);

}


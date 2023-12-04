package com.BikkadIT.electronic.store.repositories;

import com.BikkadIT.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findTitleContaining(String subTitle);

    List<Product> findByLiveTrue();
}

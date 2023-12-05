package com.BikkadIT.electronic.store.repositories;

import com.BikkadIT.electronic.store.entities.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findByTitleContaining(String subTitle, Pageable pageable);

    List<Product> findByLiveTrue(Pageable pageable);
}

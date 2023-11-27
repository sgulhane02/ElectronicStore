package com.BikkadIT.electronic.store.repositories;

import com.BikkadIT.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, String> {
}

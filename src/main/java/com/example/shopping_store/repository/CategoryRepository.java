package com.example.shopping_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_store.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{

	Boolean existsByName(String name);

	List<Category> findByIsActiveTrue();

}

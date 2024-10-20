package com.example.shopping_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_store.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{

	Cart findByProductIdAndUserId(Integer pid, Integer uid);

	List<Cart> findByUserId(Integer id);

	Integer countByUserId(Integer id);

}

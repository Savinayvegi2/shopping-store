package com.example.shopping_store.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.shopping_store.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	User findByEmail(String email);

	List<User> findByRole(String role);

	User findByResetToken(String token);

	Boolean existsByEmail(String email);

}

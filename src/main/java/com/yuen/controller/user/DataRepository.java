package com.yuen.controller.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.yuen.domain.web.Product;

public interface DataRepository extends JpaRepository<Product, Integer> {

	//@Query("SELECT p FROM Product p")
	Page<Product> findAll(Pageable pageable);

}

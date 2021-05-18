package com.yuen.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.yuen.domain.web.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	@Query("select u from User u")
	List<User> findAll();
	
	//@Query("from User u left join fetch u.roles where u.email = ?1")
	User findByEmail(String email);
	
}

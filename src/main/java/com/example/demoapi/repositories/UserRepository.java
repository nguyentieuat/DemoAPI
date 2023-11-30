package com.example.demoapi.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoapi.models.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>{

	Users findByUsername(String username);
	
}
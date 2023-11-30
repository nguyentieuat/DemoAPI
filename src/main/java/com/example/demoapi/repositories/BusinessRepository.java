package com.example.demoapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demoapi.models.Business;

@Repository
public interface BusinessRepository extends JpaRepository<Business, Long>{

	Business findByBusinessCodeAndStatusNot(String businessCode, int status);
	
}
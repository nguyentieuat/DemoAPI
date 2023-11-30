package com.example.demoapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demoapi.dtos.IUserBusinessDTO;
import com.example.demoapi.models.UserBankBusiness;

@Repository
public interface UserBankBusinessRepository extends JpaRepository<UserBankBusiness, Long> {
	@Query(value = "select ubb.business_code as businessCode, ubb.username \r\n" 
			+ "from user_bank_business ubb \r\n"
			+ "where ubb.user_bank_code = :userBankCode", nativeQuery = true)
	List<IUserBusinessDTO> findAllUserBusinessByUserBankCode(@Param("userBankCode") String userBankCode);

}

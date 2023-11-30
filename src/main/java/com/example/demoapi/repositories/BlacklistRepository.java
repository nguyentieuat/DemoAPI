package com.example.demoapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demoapi.models.Blacklist;

@Repository
public interface BlacklistRepository extends JpaRepository<Blacklist, Long>{

	List<Blacklist> findAllByUserBankCode(String userBankCode);

	List<Blacklist> findAllByDeviceCode(String deviceCode);

	@Modifying
    @Query(value = "delete \r\n" + 
    		"from blacklist \r\n" + 
    		"where user_bank_code = :userBankCode and device_code = :deviceCode", nativeQuery = true)
	void deleteByUserBankCodeAndDeviceCode(@Param("userBankCode") String userBankCode,@Param("deviceCode") String deviceCode);
	
	@Modifying
    @Query(value = "delete \r\n" + 
    		"from blacklist \r\n" + 
    		"where user_bank_code = :userBankCode", nativeQuery = true)
	void deleteByUserBankCode(@Param("userBankCode") String userBankCode);
	
	@Modifying
    @Query(value = "delete \r\n" + 
    		"from blacklist \r\n" + 
    		"where device_code = :deviceCode", nativeQuery = true)
	void deleteByDeviceCode(@Param("deviceCode") String deviceCode);
}
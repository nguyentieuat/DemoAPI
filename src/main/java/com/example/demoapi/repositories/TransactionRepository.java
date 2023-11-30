package com.example.demoapi.repositories;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demoapi.models.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	Transaction findByTransactionId(String transactionId);

	@Query(value = "select sum(amount)\r\n" + 
			"from transaction\r\n" + 
			"where username = :username and business_code = :businessCode", nativeQuery = true)
	Float sumTotalSpentByUsernameAndBusiness(@Param("username") String username, @Param("businessCode") String businessCode);

	@SuppressWarnings("rawtypes")
	@Query(value = "select count(*), sum(amount) \r\n" + 
			"from \"transaction\" t \r\n" + 
			"where t.created_at > (CURRENT_TIMESTAMP - interval '12 hours') and username = :username and business_code = :businessCode", nativeQuery = true)
	Map getTotalTransactionByUserAndBusinessIn12hours(@Param("username") String username, @Param("businessCode") String businessCode);

	@Query(value = "select sum(amount)\r\n" + 
			"from transaction\r\n" + 
			"where user_bank_code  = :userBankCode ", nativeQuery = true)
	Float sumTotalSpentByUserBankCode(@Param("userBankCode") String userBankCode);
	
	@Query(value = "select sum(amount)\r\n" + 
			"from transaction\r\n" + 
			"where device_code  = :deviceCode ", nativeQuery = true)
	Float sumTotalSpentByDeviceCode(@Param("deviceCode") String deviceCode);

}
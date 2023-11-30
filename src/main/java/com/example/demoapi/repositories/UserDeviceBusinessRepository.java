package com.example.demoapi.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demoapi.dtos.IUserBusinessDTO;
import com.example.demoapi.models.UserDeviceBusiness;

@Repository
public interface UserDeviceBusinessRepository extends JpaRepository<UserDeviceBusiness, Long> {

	@Query(value = "select udb.business_code as businessCode, udb.username \r\n" 
			+ "from user_device_business udb \r\n"
			+ "where udb.device_code  = :deviceCode", nativeQuery = true)
	List<IUserBusinessDTO> findAllUserBusinessByDeviceCode(@Param("deviceCode") String deviceCode);

}

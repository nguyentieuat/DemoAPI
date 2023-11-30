package com.example.demoapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoapi.models.Users;
import com.example.demoapi.repositories.TransactionRepository;
import com.example.demoapi.repositories.UserRepository;
import com.example.demoapi.services.UserService;
import com.example.demoapi.utils.Constant;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void updateRankUser(Users user, String businessCode, float amount) {
		Float sumTotalSpent = transactionRepository.sumTotalSpentByUsernameAndBusiness(user.getUsername(), businessCode);
		
		if (sumTotalSpent >= Constant.MOCK_GOLD && sumTotalSpent < Constant.MOCK_PLATINUM) {
			user.setRank(1);
		} else if (sumTotalSpent >= Constant.MOCK_PLATINUM && sumTotalSpent < Constant.MOCK_DIAMOND) {
			user.setRank(2);
		} else if (sumTotalSpent >=  Constant.MOCK_DIAMOND) {
			user.setRank(3);
		} else {
			user.setRank(0);
		}
		userRepository.save(user);
	}

}

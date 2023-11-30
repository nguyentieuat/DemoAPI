package com.example.demoapi.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoapi.models.Users;
import com.example.demoapi.repositories.TransactionRepository;
import com.example.demoapi.repositories.UserRepository;
import com.example.demoapi.services.UserService;
import com.example.demoapi.utils.Utils;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;

	@Override
	public void updateRankUser(Users user, String businessCode, float amount) {
		Float sumTotalSpent = transactionRepository.sumTotalSpentByUsernameAndBusiness(user.getUsername(), businessCode);
		int rank = Utils.getRank(sumTotalSpent);
		user.setRank(rank);
		userRepository.save(user);
	}

}

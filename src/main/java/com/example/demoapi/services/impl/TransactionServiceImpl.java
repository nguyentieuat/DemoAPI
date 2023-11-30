package com.example.demoapi.services.impl;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demoapi.dtos.TransactionRequestDTO;
import com.example.demoapi.dtos.TransactionResponseDTO;
import com.example.demoapi.exception.BusinessException;
import com.example.demoapi.models.Business;
import com.example.demoapi.models.Transaction;
import com.example.demoapi.models.Users;
import com.example.demoapi.repositories.BusinessRepository;
import com.example.demoapi.repositories.TransactionRepository;
import com.example.demoapi.repositories.UserRepository;
import com.example.demoapi.services.TransactionService;
import com.example.demoapi.services.UserService;
import com.example.demoapi.utils.Utils;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private BusinessRepository businessRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Override
	public boolean checkTransactionExist(String transactionId) {
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		if (!Objects.isNull(transaction)) {
			return true;
		}
		return false;
	}

	@Override
	public Transaction saveTransaction(TransactionRequestDTO transactionRequestDTO) {
		String transactionId = transactionRequestDTO.getTransactionId();
		String businessCode = transactionRequestDTO.getBusinessCode();
		Transaction transaction = transactionRepository.findByTransactionId(transactionId);
		if (Objects.isNull(transaction)) {
			transaction = new Transaction();
			transaction.setTransactionId(transactionId);

			Business business = businessRepository
					.findByBusinessCodeAndStatusNot(businessCode, -1);
			transaction.setBusiness(business);

			Users user = userRepository.findByUsername(transactionRequestDTO.getUsername());
			transaction.setUser(user);

			transaction.setUserBankCode(transactionRequestDTO.getUserBankCode());

			transaction.setDeviceCode(transactionRequestDTO.getDeviceCode());

			float amount = transactionRequestDTO.getAmount();
			transaction.setAmount(amount);

			transaction.setCreatedAt(Utils.convertStringTimeToLocalTime(transactionRequestDTO.getCreateTime()));

			transactionRepository.saveAndFlush(transaction);

			userService.updateRankUser(user, businessCode, amount);

			return transaction;
		} else {
			throw new BusinessException("Transaction had existed");
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public TransactionResponseDTO getResponseSave(Transaction transaction) {
		Users user = transaction.getUser();
		Business business = transaction.getBusiness();
		LocalDateTime currentTime = LocalDateTime.now();
		Map totalTransactionByUserIn12hours = transactionRepository
				.getTotalTransactionByUserAndBusinessIn12hours(user.getUsername(), business.getBusinessCode());
		int totalNumTransaction = ((BigInteger) totalTransactionByUserIn12hours.get("count")).intValue();
		float totalAmount = (float) totalTransactionByUserIn12hours.get("sum");

		TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
		transactionResponseDTO.setTransactionId(transaction.getTransactionId());
		transactionResponseDTO.setTotalAmount(totalAmount);
		transactionResponseDTO.setTotalNumTransaction(totalNumTransaction);
		transactionResponseDTO.setUserStatus(user.getStatus());
		transactionResponseDTO.setUserRank(user.getRank());
		transactionResponseDTO.setCreateTime(Utils.convertLocalTimeToString(currentTime));

		return transactionResponseDTO;
	}

}

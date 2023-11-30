package com.example.demoapi.services;

import com.example.demoapi.dtos.TransactionRequestDTO;
import com.example.demoapi.dtos.TransactionResponseDTO;
import com.example.demoapi.models.Transaction;

public interface TransactionService {

	boolean checkTransactionExist(String transactionId);

	Transaction saveTransaction(TransactionRequestDTO transactionRequestDTO);

	TransactionResponseDTO getResponseSave(Transaction transaction);

}

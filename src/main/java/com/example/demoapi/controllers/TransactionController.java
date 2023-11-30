package com.example.demoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoapi.dtos.ResponseDTO;
import com.example.demoapi.dtos.TransactionRequestDTO;
import com.example.demoapi.dtos.TransactionResponseDTO;
import com.example.demoapi.models.Transaction;
import com.example.demoapi.services.TransactionService;

@RestController
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	
	@PostMapping(value = "/addTransaction")
    public Object addTransaction(@RequestBody TransactionRequestDTO transactionRequestDTO) {
		String transactionId = transactionRequestDTO.getTransactionId();
		
		//validate transaction id
		boolean checkTransactionExist = transactionService.checkTransactionExist(transactionId);
		if (checkTransactionExist) {
			ResponseDTO responseDTO = new ResponseDTO();
			responseDTO.setStatus(501);
			responseDTO.setObject("Transaction id existed");
			return responseDTO;
		}
		
		// Save transaction
		Transaction saveTransaction = transactionService.saveTransaction(transactionRequestDTO);
		
		// Get response
		TransactionResponseDTO responseSave = transactionService.getResponseSave(saveTransaction);
		return responseSave;
	}
	

}

package com.example.demoapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demoapi.dtos.ResponseDTO;
import com.example.demoapi.dtos.TransactionRequestDTO;
import com.example.demoapi.dtos.TransactionResponseDTO;
import com.example.demoapi.models.Transaction;
import com.example.demoapi.services.TransactionService;
import com.example.demoapi.utils.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class TransactionControllerTest {

	@Mock
	private TransactionService transactionService;
	
	@InjectMocks
    TransactionController transactionController;

	@Test
	void testAddTransactionIdExisted() throws JsonMappingException, JsonProcessingException {
		String dataInput = "{\r\n" + 
				"   \"business\": \"B_01\", \r\n" + 
				"   \"bank_name\": \"VCB\",\r\n" + 
				"   \"user_bank_code\": \"044106505656562\", \r\n" + 
				"   \"user_name\": \"kk_009\", \r\n" + 
				"   \"transaction_id\": \"HRK202311201519060016360\", \r\n" + 
				"   \"device\": \"056f3a6a4828ff743da425215a637ba\", \r\n" + 
				"   \"amount\": 400000,\r\n" + 
				"   \"create_time\": \"2023-11-30 10:19:06\"\r\n" + 
				"}"		;
		
		ObjectMapper mapper = new ObjectMapper();
		TransactionRequestDTO transactionRequestDTO = mapper.readValue(dataInput, TransactionRequestDTO.class);  
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(transactionService.checkTransactionExist(transactionRequestDTO.getTransactionId())).thenReturn(true);
		
		ResponseDTO responseEntity = (ResponseDTO) transactionController.addTransaction(transactionRequestDTO);

        assertThat(responseEntity.getStatus()).isEqualTo(501);
        assertThat((String)responseEntity.getObject()).isEqualTo("Transaction id existed");
	}
	
	@Test
	void testAddTransactionIdNotExisted() throws JsonMappingException, JsonProcessingException {
		String dataInput = "{\r\n" + 
				"   \"business\": \"B_01\", \r\n" + 
				"   \"bank_name\": \"VCB\",\r\n" + 
				"   \"user_bank_code\": \"044106505656562\", \r\n" + 
				"   \"user_name\": \"kk_009\", \r\n" + 
				"   \"transaction_id\": \"HRK202311201519060016360\", \r\n" + 
				"   \"device\": \"056f3a6a4828ff743da425215a637ba\", \r\n" + 
				"   \"amount\": 400000,\r\n" + 
				"   \"create_time\": \"2023-11-30 10:19:06\"\r\n" + 
				"}"		;
		
		ObjectMapper mapper = new ObjectMapper();
		TransactionRequestDTO transactionRequestDTO = mapper.readValue(dataInput, TransactionRequestDTO.class);  
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		when(transactionService.checkTransactionExist(transactionRequestDTO.getTransactionId())).thenReturn(false);
		
		Transaction transaction = new Transaction();
		transaction.setId(0);
		transaction.setTransactionId(transactionRequestDTO.getTransactionId());
		when(transactionService.saveTransaction(transactionRequestDTO)).thenReturn(transaction);
		
		TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
		transactionResponseDTO.setTransactionId(transaction.getTransactionId());
		transactionResponseDTO.setCreateTime(Utils.convertLocalTimeToString(LocalDateTime.now()));
		when(transactionService.getResponseSave(transaction)).thenReturn(transactionResponseDTO);
		
		TransactionResponseDTO responseEntity = (TransactionResponseDTO) transactionController.addTransaction(transactionRequestDTO);

        assertThat(responseEntity.getTransactionId()).isEqualTo(transactionRequestDTO.getTransactionId());
        assertThat(responseEntity.getCreateTime()).isNotEmpty();
	}


}

package com.example.demoapi.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransactionRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;

	@JsonProperty("business")
	private String businessCode;
	
	@JsonProperty("bank_name")
	private String bankName;
	
	@JsonProperty("user_bank_code")
	private String userBankCode;
	
	@JsonProperty("user_name")
	private String username;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	@JsonProperty("device")
	private String deviceCode;
	
	@JsonProperty("amount")
	private float amount;
	
	@JsonProperty("create_time")
	private String createTime;

}

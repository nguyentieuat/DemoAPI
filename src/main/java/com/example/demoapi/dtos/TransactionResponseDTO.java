package com.example.demoapi.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TransactionResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	@JsonProperty("total_amount")
	private float totalAmount;
	
	@JsonProperty("num_transaction")
	private int totalNumTransaction;
	
	@JsonProperty("status")
	private int userStatus;
	
	@JsonProperty("rank")
	private int userRank;
	
	@JsonProperty("create_time")
	private String createTime;
	

	


}

package com.example.demoapi.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class BlacklistRequestDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("user_bank_code")
	private String userBankCode;
	
	@JsonProperty("device")
	private String deviceCode;
	
	@JsonProperty("type")
	private int type;
	
	@JsonProperty("des")
	private String description;
	
	@JsonProperty("create_time")
	private String createTime;

}

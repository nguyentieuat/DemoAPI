package com.example.demoapi.dtos;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResponseDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("result")
	private Object object;

}

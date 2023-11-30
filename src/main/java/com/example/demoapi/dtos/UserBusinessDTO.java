package com.example.demoapi.dtos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserBusinessDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String username;

	private String businessCode;

	@Override
	public String toString() {
		StringBuffer sbf = new StringBuffer();
		sbf.append("[\"").append("").append("\",\"").append(username).append("\"]");
		return sbf.toString();
	}
}

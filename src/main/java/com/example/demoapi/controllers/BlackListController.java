package com.example.demoapi.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demoapi.dtos.BlacklistRequestDTO;
import com.example.demoapi.dtos.BlacklistResponseDTO;
import com.example.demoapi.dtos.ResponseDTO;
import com.example.demoapi.services.BlackListService;

@RestController
public class BlackListController {
	
	@Autowired
	private BlackListService blackListService;
	
	@PostMapping(value = "/manageCheating")
	public Object manageCheating(@RequestBody BlacklistRequestDTO blacklistRequestDTO) {
		BlacklistResponseDTO blacklistResponseDTO = blackListService.saveBlacklist(blacklistRequestDTO);
	
		return blacklistResponseDTO;
	}
	
	@DeleteMapping(value = "/deleteBlacklist")
	public Object deleteBlacklist(@RequestBody BlacklistRequestDTO blacklistRequestDTO) {

		ResponseDTO responseDTO = blackListService.deleteBlacklist(blacklistRequestDTO);
	
		return responseDTO;
	}

}

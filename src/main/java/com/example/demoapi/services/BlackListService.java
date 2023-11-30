package com.example.demoapi.services;

import com.example.demoapi.dtos.BlacklistRequestDTO;
import com.example.demoapi.dtos.BlacklistResponseDTO;
import com.example.demoapi.dtos.ResponseDTO;

public interface BlackListService {

	BlacklistResponseDTO saveBlacklist(BlacklistRequestDTO backlistRequestDTO);

	ResponseDTO deleteBlacklist(BlacklistRequestDTO backlistRequestDTO);

}

package com.example.demoapi.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.demoapi.dtos.BlacklistRequestDTO;
import com.example.demoapi.dtos.BlacklistResponseDTO;
import com.example.demoapi.dtos.ResponseDTO;
import com.example.demoapi.services.BlackListService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class BlackListControllerTest {
	@Mock
	private BlackListService blackListService;

	@InjectMocks
	BlackListController blackListController;

	@Test
	void testManageCheating() throws JsonMappingException, JsonProcessingException {
		String dataInput = "{\r\n" + "   \"user_bank_code\": \"044106505656562\", \r\n" + "   \"type\": 1, \r\n"
				+ "   \"des\" : \"Spam transaction\",\r\n" + "   \"create_time\": \"2023-11-20 15:19:06\"\r\n" + "}";

		ObjectMapper mapper = new ObjectMapper();
		BlacklistRequestDTO blacklistRequestDTO = mapper.readValue(dataInput, BlacklistRequestDTO.class);

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

		BlacklistResponseDTO blacklistResponseDTO = new BlacklistResponseDTO();
		blacklistResponseDTO.setUserBankCode(blacklistRequestDTO.getUserBankCode());

		when(blackListService.saveBlacklist(blacklistRequestDTO)).thenReturn(blacklistResponseDTO);

		BlacklistResponseDTO blacklistResponseDTO1 = (BlacklistResponseDTO) blackListController
				.manageCheating(blacklistRequestDTO);
		assertThat(blacklistResponseDTO1.getUserBankCode()).isEqualTo(blacklistResponseDTO.getUserBankCode());
	}

	@Test
	void testDeleteBlacklist() throws JsonMappingException, JsonProcessingException {
		String dataInput = "{\r\n" + "   \"user_bank_code\": \"044106505656562\"\r\n" + "}";

		ObjectMapper mapper = new ObjectMapper();
		BlacklistRequestDTO blacklistRequestDTO = mapper.readValue(dataInput, BlacklistRequestDTO.class);

		MockHttpServletRequest request = new MockHttpServletRequest();
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(200);
		responseDTO.setObject("Delete blacklist success");
		
		when(blackListService.deleteBlacklist(blacklistRequestDTO)).thenReturn(responseDTO);

		ResponseDTO responseEntity = (ResponseDTO) blackListController.deleteBlacklist(blacklistRequestDTO);


		assertThat(responseEntity.getStatus()).isEqualTo(200);
		assertThat((String) responseEntity.getObject()).isEqualTo("Delete blacklist success");
	}

}

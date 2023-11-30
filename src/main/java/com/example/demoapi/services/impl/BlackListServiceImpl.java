package com.example.demoapi.services.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.demoapi.dtos.BlacklistRequestDTO;
import com.example.demoapi.dtos.BlacklistResponseDTO;
import com.example.demoapi.dtos.IUserBusinessDTO;
import com.example.demoapi.dtos.ResponseDTO;
import com.example.demoapi.exception.BusinessException;
import com.example.demoapi.models.Blacklist;
import com.example.demoapi.repositories.BlacklistRepository;
import com.example.demoapi.repositories.TransactionRepository;
import com.example.demoapi.repositories.UserBankBusinessRepository;
import com.example.demoapi.repositories.UserDeviceBusinessRepository;
import com.example.demoapi.services.BlackListService;
import com.example.demoapi.utils.Utils;

@Service
public class BlackListServiceImpl implements BlackListService {
	@Autowired
	private BlacklistRepository blacklistRepository;
	@Autowired
	private UserBankBusinessRepository userBankBusinessRepository;
	@Autowired
	private UserDeviceBusinessRepository userDeviceBusinessRepository;
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public BlacklistResponseDTO saveBlacklist(BlacklistRequestDTO blacklistRequestDTO) {
		// Get info deviceCode and userBankCode
		String deviceCode = blacklistRequestDTO.getDeviceCode();
		String userBankCode = blacklistRequestDTO.getUserBankCode();
		if (!StringUtils.hasLength(userBankCode) && !StringUtils.hasLength(deviceCode)) {
			throw new BusinessException("userBankCode and deviceCode cannot be both empty");
		}
		
		// Convert DTO to obj entity Blacklist
		Blacklist blacklist = modelMapper.map(blacklistRequestDTO, Blacklist.class);
		BlacklistResponseDTO resultResponse = new BlacklistResponseDTO();
		resultResponse.setUserBankCode(userBankCode);
		resultResponse.setDeviceCode(deviceCode);

		// Set default type by input
		int typeInput = blacklistRequestDTO.getType();
		resultResponse.setType(typeInput);
		
		// Get total spent to distribute rank
		List<Blacklist> blacklists;
		List<IUserBusinessDTO> userBusinesss;
		Float sumTotalSpent;
		int rank;
		if (StringUtils.hasLength(userBankCode)) {
			sumTotalSpent = transactionRepository.sumTotalSpentByUserBankCode(userBankCode);
			
			blacklists = blacklistRepository.findAllByUserBankCode(userBankCode);
			userBusinesss = userBankBusinessRepository.findAllUserBusinessByUserBankCode(userBankCode);

		} else {
			sumTotalSpent = transactionRepository.sumTotalSpentByDeviceCode(deviceCode);
			
			blacklists = blacklistRepository.findAllByDeviceCode(deviceCode);
			userBusinesss = userDeviceBusinessRepository.findAllUserBusinessByDeviceCode(deviceCode);
		}
		
		rank = Utils.getRank(sumTotalSpent);
		
		// Distribute to set type
		if (rank == 3) {
			blacklist.setType(0);
			resultResponse.setType(0);
		} else if (typeInput == 2 && rank != 3) {
			blacklist.setType(2);
			resultResponse.setType(2);
		} else {
			if (!Objects.isNull(blacklists) && blacklists.size() > 0) {
				Map<Integer, Long> collectType = blacklists.stream()
						.collect(Collectors.groupingBy(Blacklist::getType, Collectors.counting()));
				Long valueType2 = collectType.get(2);
				if (!Objects.isNull(valueType2) && collectType.get(2) >= 1) {
					blacklist.setType(2);
					resultResponse.setType(2);
				} else {
					Long valueType1 = collectType.get(1);

					if (rank == 0 && !Objects.isNull(valueType1) && valueType1 > 1 && typeInput == 1) {
						blacklist.setType(2);
						resultResponse.setType(2);
					}
					if (rank == 1 && valueType1 > 2 && typeInput == 1) {
						blacklist.setType(2);
						resultResponse.setType(2);
					}
					if (rank == 2 && valueType1 > 3 && typeInput == 1) {
						blacklist.setType(2);
						resultResponse.setType(2);
					}
				}
			}
		}
		
		// Get info of business with username
		if (!Objects.isNull(userBusinesss) && userBusinesss.size() > 0) {
			String[][] arrayUserBusinesss = new String[userBusinesss.size()][2];
			for (int i = 0; i < userBusinesss.size(); i++) {
				IUserBusinessDTO iUserBusinessDTO = userBusinesss.get(i);
				arrayUserBusinesss[i] =  new String[]{iUserBusinessDTO.getBusinessCode(), iUserBusinessDTO.getUsername()};
				
			}
			resultResponse.setBusiness(arrayUserBusinesss);
		}

		blacklist.setCreatedAt(Utils.convertStringTimeToLocalTime(blacklistRequestDTO.getCreateTime()));
		blacklistRepository.save(blacklist);
		
		resultResponse.setDescription(blacklistRequestDTO.getDescription());
		resultResponse.setCreateTime(Utils.convertLocalTimeToString(LocalDateTime.now()));
		return resultResponse;
	}

	@Override
	@Transactional
	public ResponseDTO deleteBlacklist(BlacklistRequestDTO blacklistRequestDTO) {
		String deviceCode = blacklistRequestDTO.getDeviceCode();
		String userBankCode = blacklistRequestDTO.getUserBankCode();
		if (!StringUtils.hasLength(userBankCode) && !StringUtils.hasLength(deviceCode)) {
			throw new BusinessException("userBankCode and deviceCode cannot be both empty");
		}
		if (StringUtils.hasLength(userBankCode)&& StringUtils.hasLength(deviceCode)  ) {
			blacklistRepository.deleteByUserBankCodeAndDeviceCode(userBankCode, deviceCode);
		} else if (StringUtils.hasLength(userBankCode)&& !StringUtils.hasLength(deviceCode)  ) {
			blacklistRepository.deleteByUserBankCode(userBankCode);
		} else if (!StringUtils.hasLength(userBankCode)&& StringUtils.hasLength(deviceCode)  ) {
			blacklistRepository.deleteByDeviceCode(deviceCode);
		}
			
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setStatus(200);
		responseDTO.setObject("Delete blacklist success");
		return responseDTO;
	}

}

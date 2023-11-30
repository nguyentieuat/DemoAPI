package com.example.demoapi.services;

import com.example.demoapi.models.Users;

public interface UserService {

	void updateRankUser(Users user, String businessCode, float amount);

}

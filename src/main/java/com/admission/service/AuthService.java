package com.admission.service;

public interface AuthService {

	void handleLoginSuccess(String userName);
	void handleLoginFailure(String userName);
	void logout(String authorizationHeader);

}

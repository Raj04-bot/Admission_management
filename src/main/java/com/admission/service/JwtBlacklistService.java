package com.admission.service;

public interface JwtBlacklistService {
	
	 public void blacklistToken(String token);
	 public boolean isTokenBlacklisted(String token);

}

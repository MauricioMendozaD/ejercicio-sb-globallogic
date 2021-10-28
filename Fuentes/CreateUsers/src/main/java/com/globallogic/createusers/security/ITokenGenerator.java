package com.globallogic.createusers.security;

public interface ITokenGenerator {
	
	String getJWTToken(String username);

}

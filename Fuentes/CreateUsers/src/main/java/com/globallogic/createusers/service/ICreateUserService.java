package com.globallogic.createusers.service;

import com.globallogic.createusers.dto.Response;
import com.globallogic.createusers.entity.Users;

public interface ICreateUserService {
	
	Response createUser(Users user) throws Exception;

}

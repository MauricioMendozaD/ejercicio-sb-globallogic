package com.globallogic.createusers;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.globallogic.createusers.controller.UserController;
import com.globallogic.createusers.entity.Phones;
import com.globallogic.createusers.entity.Users;

class CreateUserTest {
	
	@Autowired
	UserController userController;

	@Test
	void save() {
		
		userController = new UserController();
		
		Users user = new Users();
		user.setName("juan");
		user.setEmail("juan@globallogic.com");
		user.setPassword("Juangloballogic12");
		List<Phones> phones = new ArrayList<>();
		Phones phone = new Phones();
		phone.setCitycode("1");
		phone.setCountrycode("57");
		phone.setNumber("1234567");
		phones.add(phone);
		user.setPhones(phones);
		
		ResponseEntity<Object> usuarioCreado = userController.save(user);
		
		//Assertions.assertDoesNotThrow();
		
		fail("Not yet implemented");
	}

}

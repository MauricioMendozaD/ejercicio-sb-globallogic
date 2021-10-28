package com.globallogic.createusers;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.globallogic.createusers.security.ITokenGenerator;

@SpringBootTest
class CreateUsersApplicationTests {
	
	@Autowired
	private ITokenGenerator itoken;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void generateValidJWTTest() {
		
		//UserController userController = new UserController();
		String regEx = "^[A-Za-z0-9-_=]+\\.[A-Za-z0-9-_=]+\\.?[A-Za-z0-9-_.+/=]*$";
		String secretKey = "juan@rodriguez.com";

		assertTrue(Pattern.compile(regEx).matcher(itoken.getJWTToken(secretKey)).matches());
		
	}
}

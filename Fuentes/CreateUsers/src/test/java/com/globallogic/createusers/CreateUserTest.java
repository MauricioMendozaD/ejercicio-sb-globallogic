package com.globallogic.createusers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.globallogic.createusers.controller.UserController;
import com.globallogic.createusers.repository.IUserRepo;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CreateUserTest {
	
	@MockBean
	private IUserRepo userRepo;
	
	@Autowired
	UserController userController;
	
	@Autowired
	private MockMvc mockMvc;

	/**
	 * Test que valida que un request completo sea procesado exitosamente. 
	 * @throws Exception
	 */
	@Test
	void createUserSuccessfullyTest() throws Exception {
		
		MediaType textPlainUtf8 = new MediaType(MediaType.APPLICATION_JSON);
	    String user = "{\"name\": \"Juan Rodriguez\", "
	    		     + "\"email\" : \"juan@rodriguez.com\", "
	    		     + "\"password\" : \"Hunter22\" , "
	    		     + "\"phones\" : [{ \"number\" : \"1234567\","
	    		     				 + "\"citycode\" : \"1\", "
	    		     				 + "\"countrycode\" : \"57\" }]}";
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	      .content(user)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isCreated())
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(textPlainUtf8))
	      ;
	}
	
	/**
	 * Test que valida si el correo es ingresado.
	 * @throws Exception
	 */
	@Test
	void createUserMissingEmailTest() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.APPLICATION_JSON);
	    String user = "{\"name\": \"Juan Rodriguez\", "
//	    		     + "\"email\" : \"juan@rodriguez.com\", "
	    		     + "\"password\" : \"Hunter22\" , "
	    		     + "\"phones\" : [{ \"number\" : \"1234567\","
	    		     				 + "\"citycode\" : \"1\", "
	    		     				 + "\"countrycode\" : \"57\" }]}";
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	      .content(user)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(textPlainUtf8))
	      ;
	}
	
	/**
	 * Test que valida si el correo posee el formato correcto.
	 * @throws Exception
	 */
	@Test
	void createUserBadEmailFormatTest() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.APPLICATION_JSON);
	    String user = "{\"name\": \"Juan Rodriguez\", "
	    		     + "\"email\" : \"juan@rodr@iguez.com\", "
	    		     + "\"password\" : \"Hunter22\" , "
	    		     + "\"phones\" : [{ \"number\" : \"1234567\","
	    		     				 + "\"citycode\" : \"1\", "
	    		     				 + "\"countrycode\" : \"57\" }]}";
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	      .content(user)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(textPlainUtf8))
	      ;
	}
	
	/**
	 * Test que valida que el password posee el formato correcto.
	 * @throws Exception
	 */
	@Test
	void createUserWrongPasswordFormatUnsuccessfullyTest() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.APPLICATION_JSON);
	    String user = "{\"name\": \"Juan Rodriguez\", "
	    		     + "\"email\" : \"juan@rodriguez.com\", "
	    		     + "\"password\" : \"hunter22\" , "
	    		     + "\"phones\" : [{ \"number\" : \"1234567\","
	    		     				 + "\"citycode\" : \"1\", "
	    		     				 + "\"countrycode\" : \"57\" }]}";
	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	      .content(user)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isBadRequest())
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(textPlainUtf8))
	      ;
	}
	
	/**
	 * Test que valida que usuario sea creado y no ingrese ningun telefono.
	 * @throws Exception
	 */
	@Test
	void createUserNoPhoneSuccessfullyTest() throws Exception {
		MediaType textPlainUtf8 = new MediaType(MediaType.APPLICATION_JSON);
	    String user = "{\"name\": \"Juan Rodriguez\", "
	    		     + "\"email\" : \"juan@rodriguez.com\", "
	    		     + "\"password\" : \"Hunter22\" }";

	    mockMvc.perform(MockMvcRequestBuilders.post("/users")
	      .content(user)
	      .contentType(MediaType.APPLICATION_JSON))
	      .andExpect(MockMvcResultMatchers.status().isCreated())
	      .andExpect(MockMvcResultMatchers.content()
	        .contentType(textPlainUtf8))
	      ;
	}

}

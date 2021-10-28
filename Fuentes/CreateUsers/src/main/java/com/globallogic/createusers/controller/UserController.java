package com.globallogic.createusers.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.createusers.dto.Response;
import com.globallogic.createusers.entity.Users;
import com.globallogic.createusers.exceptions.FunctionalExceptionHandler;
import com.globallogic.createusers.service.ICreateUserService;


@RestController
@Validated
public class UserController {
	
	private final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private ICreateUserService createUserService;

	@PostMapping("/users")
	public ResponseEntity<Object> save(@Valid @RequestBody(required = false) Users user) {
		
		logger.info("Comienza creacion de usuario: " + user.getEmail());
		logger.debug("Request recibido: Nombre: " + user.getName() + 
				", email: " + user.getEmail() + 
				", password: " + user.getPassword());
		
		if (user.getPhones() != null) {
			logger.debug("Telefono1: " + user.getPhones().get(0).getCitycode() + " " 
									   + user.getPhones().get(0).getCountrycode() + " " 
									   + user.getPhones().get(0).getNumber());
		}
		
		Response respuesta = null;
		
		try {
			
			respuesta = createUserService.createUser(user);
			
			return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
			
		} catch (ConstraintViolationException cve) {
			
			logger.error("Ya existe un usuario con Email: " + user.getEmail());
			return FunctionalExceptionHandler.handleFunctionalException("El correo ya registrado");
			
		} catch (Exception e) {
			
			if (e.getMessage().toLowerCase().contains("on public.user(email)")) {
				logger.error("Ya existe un usuario con Email: " + user.getEmail());
				return FunctionalExceptionHandler.handleFunctionalException("El correo ya registrado");

			} else {
				logger.error("Excepcion desconocida: ", e);
				
				Map<String, List<String>> body = new HashMap<>();
				List<String> errors = new ArrayList<String>();
				errors.add("Error interno del servidor");
				body.put("mensaje", errors);
				
				return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
	}
}

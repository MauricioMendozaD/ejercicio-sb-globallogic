package com.globallogic.createusers.controller;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.globallogic.createusers.dto.Response;
import com.globallogic.createusers.entity.Users;
import com.globallogic.createusers.repository.IUserRepo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@Validated
public class UserController {
	
	@Autowired
	IUserRepo usersRepo;

	@PostMapping("/users")
	@Validated(Users.class)
	public ResponseEntity<Response> save(@Valid @RequestBody Users user) {
		
		Response respuesta = null;
		Users userCreated = null;
		String token = null;
		
		try {
			System.out.println("asdf " + user.getName());
			System.out.println("asdf " + user.getPhones().get(0).getNumber());
			respuesta = new Response();
			
			token = getJWTToken(user.getEmail());
			System.out.println("Token: " + token);
			user.setToken(token); 
			
			userCreated = usersRepo.save(user);
			
			respuesta.setCreated(userCreated.getCreatedAt());
			respuesta.setId(userCreated.getId());
			respuesta.setModified(userCreated.getUpdatedAt());
			respuesta.setLastLogin(userCreated.getCreatedAt());
			respuesta.setToken(token);
			respuesta.setIsactive(true);
			
			return new ResponseEntity<Response>(respuesta, HttpStatus.CREATED);
			
		} catch (ConstraintViolationException cve) {
			
			System.out.println("Exception: " + cve.getMessage());
			respuesta = new Response();
			respuesta.setMensaje("El correo ya registrado");
			
			return new ResponseEntity<Response>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
			
		} catch (Exception e) {
			
			if (e.getMessage().toLowerCase().contains("on public.user(email)")) {
				respuesta = new Response();
				respuesta.setMensaje("El correo ya registrado");
			} else {
				System.out.println("Exception: " + e.getMessage());
				respuesta = new Response();
				respuesta.setMensaje(e.getMessage());
			}
			
			return new ResponseEntity<Response>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	private String getJWTToken(String username) {
		
		Key secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		System.out.println("SecretKey: " + secretKey.toString());
		
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils
				.commaSeparatedStringToAuthorityList("USER");
		
		String token = Jwts
				.builder()
				.setId("globalLogicJWT")
				.setSubject(username)
				.claim("authorities", grantedAuthorities.stream()
						.map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();
		
		return "Globallogic " + token;
	}
}

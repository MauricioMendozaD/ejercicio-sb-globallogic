package com.globallogic.createusers.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globallogic.createusers.dto.Response;
import com.globallogic.createusers.entity.Users;
import com.globallogic.createusers.repository.IUserRepo;
import com.globallogic.createusers.security.ITokenGenerator;
import com.globallogic.createusers.security.TokenGenerator;

@Service
public class CreateUserService implements ICreateUserService {
	
	private static ITokenGenerator iTokenGenerator = new TokenGenerator();
	
	private final Logger logger = LoggerFactory.getLogger(CreateUserService.class);
	
	@Autowired
	private IUserRepo usersRepo;

	@Override
	public Response createUser(Users user) throws Exception {
		 
		Response respuesta = null;
		Users userCreated = new Users();

		respuesta = new Response();
		
		logger.debug("Obteniendo JWT para usuario: " + user.getEmail());
		user.setToken(iTokenGenerator.getJWTToken(user.getEmail()));
		
		logger.debug("Persistiendo usuario: " + user.getEmail());
		userCreated = usersRepo.save(user);
		
		if (userCreated != null) {
			logger.info("Usuario creado exitosamente: " + user.getEmail());
			respuesta.setCreated(userCreated.getCreatedAt());
			respuesta.setId(userCreated.getId());
			respuesta.setModified(userCreated.getUpdatedAt());
			respuesta.setLastLogin(userCreated.getCreatedAt());
			respuesta.setToken(userCreated.getToken());
			respuesta.setIsactive(true);
		}
				
		return respuesta;
	 }
}

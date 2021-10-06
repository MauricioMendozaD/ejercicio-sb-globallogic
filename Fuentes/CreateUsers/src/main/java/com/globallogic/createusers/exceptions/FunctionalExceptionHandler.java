package com.globallogic.createusers.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public final class FunctionalExceptionHandler {
	

	public static ResponseEntity<Object> handleFunctionalException(String mensaje) {
		
		Map<String, List<String>> body = new HashMap<>();
		
		List<String> errors = new ArrayList<String>();
		errors.add(mensaje);
		body.put("mensaje", errors);
		
		return new ResponseEntity<>(body, HttpStatus.ACCEPTED);
	}
}

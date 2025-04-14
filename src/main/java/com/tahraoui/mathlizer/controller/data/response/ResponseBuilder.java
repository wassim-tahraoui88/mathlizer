package com.tahraoui.mathlizer.controller.data.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseBuilder {

	public static <T> ResponseEntity<T> accept(HttpStatus statusCode, T body) {
		return new ResponseEntity<>(body, statusCode);
	}
	public static <T> ResponseEntity<T> accept(HttpStatus statusCode) { return new ResponseEntity<>(statusCode); }
	public static ResponseEntity<String> reject(HttpStatus statusCode, String message) {
		return new ResponseEntity<>(message, statusCode);
	}

}

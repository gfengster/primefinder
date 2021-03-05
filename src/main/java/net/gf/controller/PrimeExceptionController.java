package net.gf.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.gf.exception.WrongRangeException;
import net.gf.model.PrimeError;

/**
 * If the input number is invalid, the service will redirect to here.
 * @author gfeng
 *
 */
@ControllerAdvice
public class PrimeExceptionController {

	@ExceptionHandler(value = WrongRangeException.class)
	public ResponseEntity<PrimeError> wrongRange(WrongRangeException exception){
		
		final ResponseEntity<PrimeError> entity 
			= new ResponseEntity<>(exception.getError(), HttpStatus.BAD_REQUEST);
				
		return entity;
	}
}

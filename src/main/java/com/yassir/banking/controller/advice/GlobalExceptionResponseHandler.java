package com.yassir.banking.controller.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.yassir.banking.dto.ErrorResponseDto;
import com.yassir.banking.exception.AccountNotFoundException;
import com.yassir.banking.exception.CustomerNotFoundException;
import com.yassir.banking.exception.InsufficientBalanceException;

@ControllerAdvice
public class GlobalExceptionResponseHandler {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionResponseHandler.class);


	@ExceptionHandler(value = { AccountNotFoundException.class, CustomerNotFoundException.class })
	protected ResponseEntity<ErrorResponseDto> recordNotFound(Exception e) {
		return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(e.getLocalizedMessage()),
				HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InsufficientBalanceException.class })
	protected ResponseEntity<ErrorResponseDto> insufficientBalance(InsufficientBalanceException e) {
		return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto(e.getLocalizedMessage()),
				HttpStatus.BAD_REQUEST);
	}

//	@ExceptionHandler(value = { ConstraintViolationException.class })
//	protected ResponseEntity<Object> handleValidation(ConstraintViolationException e) {
//
//		BasicResponse response = new BasicResponse();
//		response.setResultMessage(e.getConstraintViolations().stream().findFirst().get().getMessage());
//		response.setResultCode(6);
//
//		return new ResponseEntity<Object>(response, HttpStatus.BAD_REQUEST);
//	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	protected ResponseEntity<Object> handleValidation(MethodArgumentNotValidException e) {
		return new ResponseEntity<Object>(new ErrorResponseDto(e.getAllErrors().get(0).getDefaultMessage()),
				HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<ErrorResponseDto> handleGolbalException(Exception e) {
		
		e.printStackTrace();
		LOGGER.error(e.getLocalizedMessage());

		return new ResponseEntity<ErrorResponseDto>(new ErrorResponseDto("Internal Server Error"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

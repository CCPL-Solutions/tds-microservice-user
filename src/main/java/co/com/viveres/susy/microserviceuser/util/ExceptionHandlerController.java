package co.com.viveres.susy.microserviceuser.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.viveres.susy.microservicecommons.dto.ErrorDto;
import co.com.viveres.susy.microservicecommons.entity.MessageEntity;
import co.com.viveres.susy.microservicecommons.exception.GenericException;
import co.com.viveres.susy.microservicecommons.repository.IMessageRepository;

@RestControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

	@Autowired
	private IMessageRepository messageRepository;

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleAll(Exception e) {
		
		MessageEntity message = 
				this.messageRepository.findById("viveres-susy.generico.error-interno-servidor")
				.orElseThrow(NoSuchElementException::new);

		ErrorDto apiError = new ErrorDto(LocalDateTime.now(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR, 
				message.getCodeMessage(), 
				message.getDescripction(),
				null);

		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@ExceptionHandler({ GenericException.class })
	public ResponseEntity<Object> generalException(GenericException e) {

		ErrorDto apiError = new ErrorDto(LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(), 
				HttpStatus.BAD_REQUEST,
				e.getMessageEntity().getCodeMessage(),
				e.getMessageEntity().getDescripction(),
				Arrays.asList(e.getMessageEntity().getDescripction()));
		
		return new ResponseEntity<>(apiError, new HttpHeaders(), apiError.getStatus());
	}

	@Override
	protected ResponseEntity<Object> handleBindException(BindException e, HttpHeaders headers, 
			HttpStatus status, WebRequest request) {
		
		MessageEntity message = this.messageRepository
				.findById("viveres-susy.generico.error-validacion-campos-entrada")
				.orElseThrow(NoSuchElementException::new);

		List<String> errors = new ArrayList<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.add(error.getField().concat(":").concat(error.getDefaultMessage()));
		}
		for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName().concat(":").concat(error.getDefaultMessage()));
		}

		ErrorDto apiError = new ErrorDto(LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST,
				message.getDescripction(),
				message.getCodeMessage(), 
				errors);

		return handleExceptionInternal(e, apiError, headers, 
				apiError.getStatus(), request);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException e, HttpHeaders headers, HttpStatus status, WebRequest request) {

				MessageEntity message = this.messageRepository
				.findById("viveres-susy.generico.error-validacion-campos-entrada")
				.orElseThrow(NoSuchElementException::new);

		List<String> errors = new ArrayList<>();
		for (FieldError error : e.getBindingResult().getFieldErrors()) {
			errors.add(error.getField().concat(":").concat(error.getDefaultMessage()));
		}
		for (ObjectError error : e.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName().concat(":").concat(error.getDefaultMessage()));
		}

		ErrorDto apiError = new ErrorDto(LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST,
				message.getDescripction(),
				message.getCodeMessage(), 
				errors);

		return handleExceptionInternal(e, apiError, headers, 
				apiError.getStatus(), request);
	}
  
}

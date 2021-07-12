package br.com.moraes.restwithspringbootudemy.api.handler;

import java.util.Date;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.moraes.restwithspringbootudemy.api.exception.ExceptionResponse;
import br.com.moraes.restwithspringbootudemy.api.exception.FileStorageException;
import br.com.moraes.restwithspringbootudemy.api.exception.IncorrectDataEnteredException;
import br.com.moraes.restwithspringbootudemy.api.exception.InvalidJwtAuthenticationException;
import br.com.moraes.restwithspringbootudemy.api.exception.MyFileNotFoundException;
import br.com.moraes.restwithspringbootudemy.api.exception.ResourceNotFoundException;
import br.com.moraes.restwithspringbootudemy.api.exception.UnsuportedMathOperationException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@RestController
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ Exception.class, FileStorageException.class })
	public final ResponseEntity<ExceptionResponse> handleAllExceptions(Exception exception, WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler({ ResourceNotFoundException.class, UsernameNotFoundException.class,
			MyFileNotFoundException.class })
	public final ResponseEntity<ExceptionResponse> handleNotFoundException(Exception exception, WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.status(httpStatus).body(exceptionResponse);
	}

	@ExceptionHandler({ EmptyResultDataAccessException.class })
	public final ResponseEntity<ExceptionResponse> handleEmptyResultDataAccessException(Exception exception,
			WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.GONE;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.status(httpStatus).body(exceptionResponse);
	}

	@ExceptionHandler({ UnsuportedMathOperationException.class, DataIntegrityViolationException.class,
			IncorrectDataEnteredException.class, InvalidJwtAuthenticationException.class,
			BadCredentialsException.class })
	public final ResponseEntity<ExceptionResponse> handleBadRequestExceptions(Exception exception,
			WebRequest webRequest) {
		final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
		final ExceptionResponse exceptionResponse = tratar(exception, webRequest, httpStatus);
		return ResponseEntity.badRequest().body(exceptionResponse);
	}

	private ExceptionResponse tratar(Exception exception, WebRequest webRequest, HttpStatus httpStatus) {
		final ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
				ExceptionUtils.getRootCauseMessage(exception), webRequest.getDescription(Boolean.FALSE), httpStatus);
		log.error("Erro durante requisição - |{}| ## Detalhe do Erro: |{}|", exceptionResponse.getMessage(),
				exceptionResponse.getMessageDevelop());
		return exceptionResponse;
	}
}

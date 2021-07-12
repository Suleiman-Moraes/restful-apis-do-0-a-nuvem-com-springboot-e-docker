package br.com.moraes.restwithspringbootudemy.api.exception;

import java.io.Serializable;
import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class ExceptionResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Date timestamp;
	
	private String message;

	private String messageDevelop;

	private String details;

	@JsonIgnore
	private HttpStatus httpStatus;

	public String getHttpErro() {
		if (httpStatus != null) {
			return String.format("%s - %s", httpStatus.value(), httpStatus.getReasonPhrase());
		}
		return String.format("%s - %s", HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
	}
}

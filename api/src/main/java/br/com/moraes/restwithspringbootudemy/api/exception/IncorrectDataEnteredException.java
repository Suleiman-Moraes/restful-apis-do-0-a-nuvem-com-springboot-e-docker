package br.com.moraes.restwithspringbootudemy.api.exception;

public class IncorrectDataEnteredException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public IncorrectDataEnteredException(String mensagem, Throwable e) {
		super(mensagem, e);
	}
}

package br.com.moraes.restwithspringbootudemy.api.exception;

public class MyFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public MyFileNotFoundException(String mensagem) {
		super(mensagem);
	}

	public MyFileNotFoundException(String mensagem, Throwable e) {
		super(mensagem, e);
	}
}

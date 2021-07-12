package br.com.moraes.restwithspringbootudemy.api.exception;

public class FileStorageException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FileStorageException(String mensagem) {
		super(mensagem);
	}

	public FileStorageException(String mensagem, Throwable e) {
		super(mensagem, e);
	}
}

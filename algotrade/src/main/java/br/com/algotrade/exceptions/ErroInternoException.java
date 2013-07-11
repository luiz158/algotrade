package br.com.algotrade.exceptions;

public class ErroInternoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public ErroInternoException() {}

	public ErroInternoException(String nivel, String msg) {
		super(nivel + ": " + msg);
	}
	public ErroInternoException(String nivel, String msg, Throwable e) {
		super(nivel + ": " + msg, e);
	}
	
}

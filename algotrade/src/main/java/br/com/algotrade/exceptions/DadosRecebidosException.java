package br.com.algotrade.exceptions;


public class DadosRecebidosException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DadosRecebidosException(String nome, String especifico) {
		super("Faltando dados do ativo " + nome + ": " + especifico.toUpperCase());
	}
	public DadosRecebidosException() {
		
	}

}

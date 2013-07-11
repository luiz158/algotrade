package br.com.algotrade.negocio.dados;

import br.com.algotrade.entidades.Ativo;

public class FimDosDados implements DadosRecebidos {

	DadosRecebidos proximo;
	
	public void populaAtivo(Ativo ativo, String valor) {
		//final da iteracao
	}
		
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}
}

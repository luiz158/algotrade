package br.com.algotrade.negocio.dados;

import br.com.algotrade.entidades.Ativo;

public interface DadosRecebidos {

	void populaAtivo(Ativo ativo, String valor);
	
	public void setProximo(DadosRecebidos proximo);
	
}

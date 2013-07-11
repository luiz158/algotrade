package br.com.algotrade.negocio.dados;

import java.math.BigDecimal;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.exceptions.DadosRecebidosException;

public class VariacaoPorCento implements DadosRecebidos {

	DadosRecebidos proximo;
	
	public void populaAtivo(Ativo ativo, String valor) {
		if (ativo.getVariacaoPorCento() != null && ativo.getVariacaoPorCento().doubleValue() == 0) {
			ativo.setVariacaoPorCento(new BigDecimal(valor));
			if (ativo.getVariacaoPorCento().doubleValue() == 0) {
				throw new DadosRecebidosException(ativo.getNome(), "variacaoPorCento");
			} 
		} else {
			proximo.populaAtivo(ativo, valor);
		}
	}
		
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}
}
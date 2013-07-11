package br.com.algotrade.negocio.dados;

import java.math.BigDecimal;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.exceptions.DadosRecebidosException;

public class MaximaAtual implements DadosRecebidos {

	DadosRecebidos proximo;
	
	public void populaAtivo(Ativo ativo, String valor) {
		if (ativo.getMaxima() != null  && ativo.getMaxima().doubleValue() == 0) {
			ativo.setMaxima(new BigDecimal(valor));
			if (ativo.getMaxima().doubleValue() == 0) {
				throw new DadosRecebidosException(ativo.getNome(), "maxima");
			} 
		} else {
			proximo.populaAtivo(ativo, valor);
		}
	}
		
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}

}

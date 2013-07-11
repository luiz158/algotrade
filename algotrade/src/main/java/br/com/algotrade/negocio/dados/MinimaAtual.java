package br.com.algotrade.negocio.dados;

import java.math.BigDecimal;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.exceptions.DadosRecebidosException;

public class MinimaAtual implements DadosRecebidos {
	DadosRecebidos proximo;
	
	public void populaAtivo(Ativo ativo, String valor) {
		if (ativo.getMinima() != null && ativo.getMinima().doubleValue() == 0) {
			ativo.setMinima(new BigDecimal(valor));
			if (ativo.getMinima().doubleValue() == 0) {
				throw new DadosRecebidosException(ativo.getNome(), "minima");
			} 
		} else {
			proximo.populaAtivo(ativo, valor);
		}
	}
		
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}

}

package br.com.algotrade.negocio.dados;

import java.math.BigDecimal;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.exceptions.DadosRecebidosException;

public class Variacao implements DadosRecebidos {
	DadosRecebidos proximo;
	
	public void populaAtivo(Ativo ativo, String valor) {
		if (ativo.getVariacao() != null && ativo.getVariacao().doubleValue() == 0) {
			ativo.setVariacao(new BigDecimal(valor));
			if (ativo.getVariacao().doubleValue() == 0) {
				throw new DadosRecebidosException(ativo.getNome(), "variacao");
			} 
		} else {
			proximo.populaAtivo(ativo, valor);
		}
	}
		
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}

}

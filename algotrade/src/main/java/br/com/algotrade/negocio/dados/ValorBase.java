package br.com.algotrade.negocio.dados;

import java.math.BigDecimal;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.exceptions.DadosRecebidosException;

public class ValorBase implements DadosRecebidos {
	
	DadosRecebidos proximo;

	@Override
	public void populaAtivo(Ativo ativo, String valor) {
		if (ativo.getBase() != null && ativo.getBase().doubleValue() == 0) {
			ativo.setBase(new BigDecimal(valor));
			if (ativo.getBase().doubleValue() == 0) {
				throw new DadosRecebidosException(ativo.getNome(), "base");
			}
		} else {
			proximo.populaAtivo(ativo, valor);
		}
		
	}
	
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}

}

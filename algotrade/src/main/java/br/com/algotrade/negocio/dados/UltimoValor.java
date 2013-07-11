package br.com.algotrade.negocio.dados;

import java.math.BigDecimal;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.exceptions.DadosRecebidosException;

public class UltimoValor implements DadosRecebidos {
	DadosRecebidos proximo;
	
	public void populaAtivo(Ativo ativo, String valor) {
		if (ativo.getUltimo() != null && ativo.getUltimo().doubleValue() == 0) {
			ativo.setUltimo(new BigDecimal(valor));
			if (ativo.getUltimo().doubleValue() == 0) {
				throw new DadosRecebidosException(ativo.getNome(), "ultimo");
			} 
		} else {
			proximo.populaAtivo(ativo, valor);
		}
	}
		
	public void setProximo(DadosRecebidos proximo) {
		this.proximo = proximo;
	}
}
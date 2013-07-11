package br.com.algotrade.dao.interfaces;

import br.com.algotrade.entidades.HistoricoGeral;

public interface HistoricoGeralDAO extends GenericDAO<HistoricoGeral> {

	public HistoricoGeral loadFabricanteByNome(String nome);

}
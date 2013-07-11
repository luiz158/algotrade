package br.com.algotrade.dao.interfaces;

import br.com.algotrade.entidades.HistoricoDoDia;



public interface HistoricoDoDiaDAO extends GenericDAO<HistoricoDoDia> {
	
	public HistoricoDoDiaDAO loadFabricanteByNome(String nome);

}

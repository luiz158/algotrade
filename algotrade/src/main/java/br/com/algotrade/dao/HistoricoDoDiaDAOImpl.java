package br.com.algotrade.dao;

import org.springframework.stereotype.Repository;

import br.com.algotrade.dao.interfaces.HistoricoDoDiaDAO;
import br.com.algotrade.entidades.HistoricoDoDia;

@Repository
public class HistoricoDoDiaDAOImpl extends GenericDAOImpl<HistoricoDoDia> implements HistoricoDoDiaDAO {

	@Override
	public HistoricoDoDiaDAO loadFabricanteByNome(String nome) {
		return null;
	}


}
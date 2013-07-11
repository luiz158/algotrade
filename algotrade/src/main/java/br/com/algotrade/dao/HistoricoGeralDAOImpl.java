package br.com.algotrade.dao;

import org.springframework.stereotype.Repository;

import br.com.algotrade.dao.interfaces.HistoricoGeralDAO;
import br.com.algotrade.entidades.HistoricoGeral;

@Repository
public class HistoricoGeralDAOImpl extends GenericDAOImpl<HistoricoGeral> implements HistoricoGeralDAO {

	@Override
	public HistoricoGeral loadFabricanteByNome(String nome) {
		return null;
	}
}
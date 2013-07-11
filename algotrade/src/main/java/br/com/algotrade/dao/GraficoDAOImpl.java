package br.com.algotrade.dao;

import org.springframework.stereotype.Repository;

import br.com.algotrade.dao.interfaces.GraficoDAO;
import br.com.algotrade.entidades.Grafico;

@Repository
public class GraficoDAOImpl extends GenericDAOImpl<Grafico> implements GraficoDAO {

	@Override
	public Grafico loadFabricanteByNome(String nome) {
		return null;
	}
}

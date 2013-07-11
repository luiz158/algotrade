package br.com.algotrade.dao.interfaces;

import br.com.algotrade.entidades.Grafico;

public interface GraficoDAO extends GenericDAO<Grafico> {

	public Grafico loadFabricanteByNome(String nome);

}
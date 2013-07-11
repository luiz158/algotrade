package br.com.algotrade.dao.interfaces;

import java.util.Date;
import java.util.List;

import br.com.algotrade.entidades.Ativo;

public interface AtivoDAO extends GenericDAO<Ativo> {

	public List<Ativo> carregaAtivosByData(Date date);
	public List<Ativo> carregaAtivosDeHoje();

}
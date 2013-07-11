package br.com.algotrade.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.entidades.Ativo;

@Repository
public class AtivoDAOImpl extends GenericDAOImpl<Ativo> implements AtivoDAO {

	@Override
	@SuppressWarnings("unchecked")
	public List<Ativo> carregaAtivosByData(Date data) {
		Query query = em.createQuery("FROM Ativo a WHERE a.data = :data");
		query.setParameter("data", data);
		return query.getResultList();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Ativo> carregaAtivosDeHoje() {
		Query query = em.createQuery("FROM Ativo a WHERE a.data = :data");
		query.setParameter("data", new Date());
		return query.getResultList();
	}
}
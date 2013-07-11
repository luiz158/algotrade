package br.com.algotrade.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.algotrade.dao.interfaces.GenericDAO;

public abstract class GenericDAOImpl<T> implements GenericDAO<T> {

	@PersistenceContext
	protected EntityManager em;

	private Class<T> type;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public GenericDAOImpl() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		type = (Class) pt.getActualTypeArguments()[0];

		System.err.println("Construtor GenericDaoImpl (grava type) | em = "
				+ em);

	}

	@Override
	public long countAll(final Map<?, ?> clausulas) {
		return 0;
	}

	@Override
	public T persist(final T t) {
		this.em.persist(t);
		return t;
	}

	@Override
	public void delete(final Object id) {
		this.em.remove(this.em.getReference(type, id));
	}

	@Override
	public T find(final Object id) {
		return (T) this.em.find(type, id);
	}

	@Override
	public T merge(final T t) {
		return this.em.merge(t);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> selectAll() {
		return em.createQuery("FROM " + type.getSimpleName()).getResultList();
	}
	@Override
	public void refresh(final T t) {
		this.em.refresh(t);
	}
}

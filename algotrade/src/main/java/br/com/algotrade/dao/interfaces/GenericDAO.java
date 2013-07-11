package br.com.algotrade.dao.interfaces;

import java.util.List;
import java.util.Map;

public interface GenericDAO<T> {

	long countAll(Map<?, ?> clausulas);

	T persist(T t);

	void delete(Object id);

	void refresh(final T t);

	T find(Object id);

	T merge(T t);
	
	List<T> selectAll();
}
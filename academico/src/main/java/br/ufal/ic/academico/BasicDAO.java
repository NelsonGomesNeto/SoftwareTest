package br.ufal.ic.academico;

import io.dropwizard.hibernate.AbstractDAO;
import org.hibernate.SessionFactory;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class BasicDAO<T> extends AbstractDAO<T> {

	public BasicDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public T get(Serializable id) {
		return super.get(id);
	}

	@Override
	public T persist(T entity) {
		return super.persist(entity);
	}

	public void delete(Serializable id) {
		currentSession().delete(this.get(id));
	}
}
package br.ufal.ic.academico.secretary;

import br.ufal.ic.academico.BasicDAO;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class SecretaryDAO extends BasicDAO<Secretary> {

	public SecretaryDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public ArrayList<Secretary> getAll() {
		return(ArrayList<Secretary>) currentSession().createQuery("from Secretary").list();
	}
}

package br.ufal.ic.academico.subject;

import br.ufal.ic.academico.BasicDAO;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class SubjectDAO extends BasicDAO<Subject> {

	public SubjectDAO(SessionFactory sessionFactory) { super(sessionFactory); }

	public ArrayList<Subject> getAll() {
		return(ArrayList<Subject>) currentSession().createQuery("from Subject").list();
	}
}

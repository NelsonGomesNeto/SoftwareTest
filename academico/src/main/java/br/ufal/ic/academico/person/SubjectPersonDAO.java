package br.ufal.ic.academico.person;

import br.ufal.ic.academico.BasicDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

public class SubjectPersonDAO extends BasicDAO<SubjectPerson> {

	public SubjectPersonDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}

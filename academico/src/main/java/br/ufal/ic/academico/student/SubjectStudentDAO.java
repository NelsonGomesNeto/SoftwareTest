package br.ufal.ic.academico.student;

import br.ufal.ic.academico.BasicDAO;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

public class SubjectStudentDAO extends BasicDAO<SubjectStudent> {

	public SubjectStudentDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}
}

package br.ufal.ic.academico.student;

import br.ufal.ic.academico.BasicDAO;
import br.ufal.ic.academico.subject.Subject;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class StudentDAO extends BasicDAO<Student> {

	public StudentDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public ArrayList<Student> getAll() { return((ArrayList<Student>) currentSession().createQuery("from Student").list()); }

	public ArrayList<String> getAllName() { return((ArrayList<String>) currentSession().createQuery("select concat(p.lastName, ', ', p.firstName) from Student p").list()); }
}

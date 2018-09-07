package br.ufal.ic.academico.course;

import br.ufal.ic.academico.BasicDAO;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class CourseDAO extends BasicDAO<Course> {

	public CourseDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public ArrayList<Course> getAll() {
		return(ArrayList<Course>) currentSession().createQuery("from Course").list();
	}
}

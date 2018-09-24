package br.ufal.ic.academico.course;

import br.ufal.ic.academico.BasicDAO;
import br.ufal.ic.academico.department.Department;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class CourseDAO extends BasicDAO<Course> {

	public CourseDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

//	public Department getDepartment(Course course) {
//		return currentSession().createQuery("select D from Department D where course.id in select");
//	}

	public ArrayList<Course> getAll() {
		return(ArrayList<Course>) currentSession().createQuery("from Course").list();
	}
}

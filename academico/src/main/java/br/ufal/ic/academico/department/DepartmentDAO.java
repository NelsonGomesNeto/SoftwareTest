package br.ufal.ic.academico.department;

import br.ufal.ic.academico.BasicDAO;
import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.subject.Subject;
import org.hibernate.SessionFactory;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DepartmentDAO extends BasicDAO<Department> {

	public DepartmentDAO(SessionFactory sessionFactory) { super(sessionFactory); }

	public ArrayList<Department> getAll() {
		return ((ArrayList<Department>) currentSession().createQuery("from Department").list());
	}
}

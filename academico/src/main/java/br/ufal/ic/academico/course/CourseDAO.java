package br.ufal.ic.academico.course;

import br.ufal.ic.academico.BasicDAO;
import br.ufal.ic.academico.department.Department;
import br.ufal.ic.academico.secretary.Secretary;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.ArrayList;

public class CourseDAO extends BasicDAO<Course> {

	public CourseDAO(SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public Course getByNameAndDegreeLevel(String name, String degreeLevel) {
		Course course = (Course) currentSession()
			.createQuery("select c from Course c where c.name = :name and c.degreeLevel = :degreelevel")
			.setParameter("name", name)
			.setParameter("degreelevel", degreeLevel)
			.uniqueResult();
		return(course);
	}

	public Department getDepartment(Course course) {
//		Secretary secretary = (Secretary) currentSession().createQuery("select s from Secretary s where :courseid in (select c.id from s.courses c)")
//											.setParameter("courseid", course.getId())
//											.uniqueResult();
//		return(null);
		Department department = (Department) currentSession().createQuery("select d from Department d where (:courseid in (select c.id from d.graduate.courses c)) or (:courseid in (select c.id from d.postgraduate.courses c))")
			.setParameter("courseid", course.getId())
			.uniqueResult();
		return(department);
	}

	public ArrayList<Course> getAll() {
		return(ArrayList<Course>) currentSession().createQuery("from Course").list();
	}
}

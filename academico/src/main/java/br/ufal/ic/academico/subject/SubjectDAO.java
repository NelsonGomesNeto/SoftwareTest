package br.ufal.ic.academico.subject;

import br.ufal.ic.academico.BasicDAO;
import br.ufal.ic.academico.student.Student;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class SubjectDAO extends BasicDAO<Subject> {

	public SubjectDAO(SessionFactory sessionFactory) { super(sessionFactory); }

	public ArrayList<Subject> getAll() {
		return(ArrayList<Subject>) currentSession().createQuery("from Subject").list();
	}

	public ArrayList<Student> getStudentsInSubject(Subject subject) {
		return((ArrayList<Student>) currentSession()
			.createQuery("select p from Student p where :subjectId in (select s.subject.id from p.subjects s)")
			.setParameter("subjectId", subject.getId())
			.list());
	}

	public Subject getByCode(String code) {
		Subject subject = (Subject) currentSession().createQuery("select s from Subject s where s.code = :code")
			.setParameter("code", code).uniqueResult();
		return(subject);
	}
}

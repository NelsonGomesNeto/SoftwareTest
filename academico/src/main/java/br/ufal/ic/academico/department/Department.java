package br.ufal.ic.academico.department;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.subject.Subject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Getter
@RequiredArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	@OneToOne
	private Secretary graduate, postgraduate;

	public Department(String name, Secretary graduate, Secretary postgraduate) {
		this.name = name;
		this.graduate = graduate;
		this.postgraduate = postgraduate;
	}

	public boolean hasSubject(Subject subject) {
		if (graduate != null)
			for (Course course: graduate.getCourses())
				if (course.getSubjects().contains(subject))
					return(true);
		if (postgraduate != null)
			for (Course course: postgraduate.getCourses())
				if (course.getSubjects().contains(subject))
					return(true);

		return(false);
	}

	public ArrayList<Subject> allSubjects() {
		ArrayList<Subject> subjects = new ArrayList<>();
		if (graduate != null)
			for (Course course: graduate.getCourses())
				subjects.addAll(course.getSubjects());
		if (postgraduate != null)
			for (Course course: postgraduate.getCourses())
				subjects.addAll(course.getSubjects());
		return(subjects);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Department) {
			Department d = (Department) obj;
			return(d.getName().equals(this.getName()));
		}
		return(false);
	}
}

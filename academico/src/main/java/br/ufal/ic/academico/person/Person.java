package br.ufal.ic.academico.person;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.subject.Subject;
import javafx.util.Pair;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	private String firstName, lastName;
	private Integer credits;
	@ManyToOne
	private Course studying;
	@OneToMany
	private List<SubjectPerson> subjects;

	public Person(String firstName, String lastName, Course studying) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.credits = 0;
		this.studying = studying;
		this.subjects = new ArrayList<>();
	}

	public void enrollCourse(Course course) { this.studying = course; }

	public void enrollSubject(SubjectPerson subjectPerson) {
		this.subjects.add(subjectPerson);
	}

	public void completeSubject(Subject subject) {
		for (SubjectPerson subjectPerson: this.subjects) {
			if (subjectPerson.getSubject() == subject) {
				subjectPerson.complete();
				this.credits += subjectPerson.getSubject().getCredits();
				return;
			}
		}
	}

	public int findSubjectIndex(Subject subject) {
		int index = 0;
		for (SubjectPerson subjectPerson: this.subjects) {
			if (subjectPerson.getSubject() == subject) return(index);
			index ++;
		}
		return(-1);
	}
}

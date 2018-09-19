package br.ufal.ic.academico.person;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.subject.Subject;
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
	private List<Subject> subjects;
	@OneToMany
	private List<Subject> completedSubjects;

	public Person(String firstName, String lastName, Course studying) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.credits = 0;
		this.studying = studying;
		this.subjects = new ArrayList<>();
		this.completedSubjects = new ArrayList<>();
	}

	public void enrollSubject(Subject subject) {
		this.subjects.add(subject);
	}

	public void completeSubject(Subject subject) {
//		this.subjects.clear();
//		this.subjects.remove(subject);
//		this.completedSubjects.add(subject);
	}
}

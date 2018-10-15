package br.ufal.ic.academico.student;

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
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
 
	private String firstName, lastName;
	private Integer credits;
	@ManyToOne
	private Course studying;
	@OneToMany
	private List<SubjectStudent> subjects;

	public Student(String firstName, String lastName, Course studying) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.credits = 0;
		this.studying = studying;
		this.subjects = new ArrayList<>();
	}

	public void enrollSubject(SubjectStudent subjectStudent) {
		this.subjects.add(subjectStudent);
	}

	public void completeSubject(Subject subject) {
		for (SubjectStudent subjectStudent: this.subjects) {
			if (subjectStudent.getSubject() == subject && !subjectStudent.getCompleted()) {
				subjectStudent.complete();
				this.credits += subjectStudent.getSubject().getCredits();
				return;
			}
		}
	}

	public boolean completedSubject(Subject subject) {
		for (SubjectStudent subjectStudent: this.subjects)
			if (subjectStudent.getCompleted() && subjectStudent.getSubject().getId().equals(subject.getId()))
				return(true);
		return(false);
	}

	public int findSubjectIndex(Subject subject) {
		int index = 0;
		for (SubjectStudent subjectStudent: this.subjects) {
			if (subjectStudent.getSubject() == subject) return(index);
			index ++;
		}
		return(-1);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Student) {
			Student s = (Student) obj;
			return(s.getId().equals(this.getId()));
		}
		return(false);
	}
}

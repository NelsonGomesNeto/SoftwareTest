package br.ufal.ic.academico.course;

import br.ufal.ic.academico.subject.Subject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
@Getter
@RequiredArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private ArrayList<Subject> subjects;

	public Course(String name, ArrayList<Subject> subjects) {
		this.name = name;
		this.subjects = subjects;
	}
}

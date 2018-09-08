package br.ufal.ic.academico.course;

import br.ufal.ic.academico.subject.Subject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Subject> subjects;

	public Course(String name, ArrayList<Subject> subjects) {
		this.name = name;
		this.subjects = subjects;
	}
}

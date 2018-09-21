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
	@ManyToMany(cascade = {CascadeType.ALL})
	private List<Subject> subjects;
	private String degreeLevel;

	public Course(String name, ArrayList<Subject> subjects, String degreeLevel) {
		this.name = name;
		this.subjects = subjects;
		if (!degreeLevel.equals("graduate"))
			this.degreeLevel = "postgraduate";
		else
			this.degreeLevel = "graduate";
	}
}

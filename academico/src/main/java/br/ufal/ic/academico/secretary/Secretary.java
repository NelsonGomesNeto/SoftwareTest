package br.ufal.ic.academico.secretary;

import br.ufal.ic.academico.course.Course;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Secretary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	String degreeLevel;
	@OneToMany(cascade = {CascadeType.ALL})
	List<Course> courses;

	public Secretary(String degreeLevel, ArrayList<Course> courses) {
		if (!degreeLevel.equals("graduate"))
			this.degreeLevel = "postgraduate";
		else
			this.degreeLevel = "graduate";
		this.courses = courses;
	}
}

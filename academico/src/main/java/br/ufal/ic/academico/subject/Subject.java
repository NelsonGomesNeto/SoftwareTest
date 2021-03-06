package br.ufal.ic.academico.subject;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private String code;
	private Integer credits, requiredCredits;
	@ManyToMany(cascade = {CascadeType.ALL})
	private List<Subject> requiredSubjects;
	private String degreeLevel;
	private String professor;

	public Subject(String name, String code, Integer credits, Integer requiredCredits, ArrayList<Subject> requiredSubjects, String degreeLevel, String professor) {
		this.name = name;
		this.code = code;
		this.credits = credits;
		this.requiredCredits = requiredCredits;
		this.requiredSubjects = requiredSubjects;
		if (!degreeLevel.equals("graduate"))
			this.degreeLevel = "postgraduate";
		else
			this.degreeLevel = "graduate";
		this.professor = professor;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Subject) {
			Subject s = (Subject) obj;
			return(s.getCode().equals(this.getCode()));
		}
		return(false);
	}
}

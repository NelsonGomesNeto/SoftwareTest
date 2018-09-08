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

	private String name, code;
	private Integer credits, requiredCredits;
	@OneToMany(cascade = {CascadeType.ALL})
	private List<Subject> requiredSubjects;

	public Subject(String name, String code, Integer credits, Integer requiredCredits, ArrayList<Subject> requiredSubjects) {
		this.name = name;
		this.code = code;
		this.credits = credits;
		this.requiredCredits = requiredCredits;
		this.requiredSubjects = requiredSubjects;
	}
}

package br.ufal.ic.academico.subject;

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
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name, code;
	private Integer credits, requiredCredits;
	private ArrayList<Subject> requiredSubjects;

	public Subject(String name, String code, Integer credits, Integer requiredCredits, ArrayList<Subject> requiredSubjects) {
		this.name = name;
		this.code = code;
		this.credits = credits;
		this.requiredCredits = requiredCredits;
		this.requiredSubjects = requiredSubjects;
	}
}

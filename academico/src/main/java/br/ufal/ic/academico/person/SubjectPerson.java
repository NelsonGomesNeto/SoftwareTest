package br.ufal.ic.academico.person;

import br.ufal.ic.academico.subject.Subject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class SubjectPerson {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Subject subject;
	private Boolean completed;

	public SubjectPerson(Subject subject, Boolean completed) {
		this.subject = subject;
		this.completed = completed;
	}

	void complete() {
		this.completed = true;
	}
}

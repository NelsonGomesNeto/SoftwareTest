package br.ufal.ic.academico.student;

import br.ufal.ic.academico.subject.Subject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class SubjectStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@OneToOne
	private Subject subject;
	private Boolean completed;

	public SubjectStudent(Subject subject, Boolean completed) {
		this.subject = subject;
		this.completed = completed;
	}

	void complete() {
		this.completed = true;
	}
}

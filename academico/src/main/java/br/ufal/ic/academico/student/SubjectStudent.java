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

//	@Override
//	public boolean equals(Object obj) {
//		if (obj instanceof SubjectStudent) {
//			SubjectStudent ss = (SubjectStudent) obj;
//			return(ss.getCompleted() == this.getCompleted() && ss.getSubject().equals(this.getSubject()));
//		}
//		return(false);
//	}
}

package br.ufal.ic.academico.department;

import br.ufal.ic.academico.secretary.Secretary;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@RequiredArgsConstructor
public class Department {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;
	private Secretary graduate, postgraduate;

	public Department(String name, Secretary graduate, Secretary postgraduate) {
		this.name = name;
		this.graduate = graduate;
		this.postgraduate = postgraduate;
	}
}

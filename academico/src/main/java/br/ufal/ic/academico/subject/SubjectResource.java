package br.ufal.ic.academico.subject;

import br.ufal.ic.academico.student.Student;
import br.ufal.ic.academico.student.StudentDAO;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;

@Path("subject")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {

	private final SubjectDAO subjectDAO;
	private final StudentDAO studentDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Subject> subjects = subjectDAO.getAll();
		return(Response.ok(subjects).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Subject s = subjectDAO.get(id);
		return(Response.ok(s).build());
	}

	@GET
	@Path("/{id}/schedule")
	@UnitOfWork
	public Response getSchedule(@PathParam("id") Long id) {

		log.info("getSchedule: id={}", id);
		Subject subject = subjectDAO.get(id);
		ArrayList<Long> requiredSubjectsIds = new ArrayList<>();
		for (Subject s: subject.getRequiredSubjects())
			requiredSubjectsIds.add(s.getId());
		ArrayList<SubjectDTO.Student> students = new ArrayList<>();
		ArrayList<Student> student = subjectDAO.getStudentsInSubject(subject);
		for (Student p: student)
			students.add(new SubjectDTO.Student(p.getId(), p.getFirstName(), p.getLastName()));
		SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getId(), subject.getCredits(), subject.getRequiredCredits(), requiredSubjectsIds, subject.getDegreeLevel(), subject.getProfessor(), students);
		return(Response.ok(subjectDTO).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(SubjectDTO entity) {

		log.info("save: {}", entity);
		ArrayList<Subject> requiredSubjects = new ArrayList<>();
		entity.requiredSubjectsIds.forEach((id) -> requiredSubjects.add(subjectDAO.get(id)));
		Subject s = new Subject(entity.name, entity.credits, entity.requiredCredits, requiredSubjects, entity.degreeLevel, entity.professor);
		return(Response.ok(subjectDAO.persist(s)).build());
}

	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public Response delete(@PathParam("id") Long id) {

		log.info("delete: id={}", id);
		subjectDAO.delete(id);
		return(Response.status(Response.Status.NO_CONTENT).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class SubjectDTO {

		private String name;
		private Long id;
		private Integer credits, requiredCredits;
		private ArrayList<Long> requiredSubjectsIds;
		private String degreeLevel;
		private String professor;
		private ArrayList<Student> students;

		@Getter
		@ToString
		private static class Student {
			private Long enrollmentId;
			private String firstName, lastName;

			private Student(Long enrollmentId, String firstName, String lastName) {
				this.enrollmentId = enrollmentId;
				this.firstName = firstName;
				this.lastName = lastName;
			}
		}
	}
}

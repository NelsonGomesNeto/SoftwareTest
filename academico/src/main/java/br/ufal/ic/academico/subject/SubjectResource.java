package br.ufal.ic.academico.subject;

import br.ufal.ic.academico.student.Student;
import io.dropwizard.hibernate.UnitOfWork;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("subject")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class SubjectResource {

	private final SubjectDAO subjectDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Subject> subjects = subjectDAO.getAll();
		return(Response.ok(subjects).build());
	}

	@GET
	@Path("/code/{code}")
	@UnitOfWork
	public Response getByCode(@PathParam("code") String code) {

		log.info("getByCode: code={}", code);
		Subject s = subjectDAO.getByCode(code);
		if (s == null)
			return(Response.status(Response.Status.NOT_FOUND)
				.entity(String.format("There's no subject with code %s", code)).build());
		return(Response.ok(s).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Subject s = subjectDAO.get(id);
		if (s == null)
			return(Response.status(Response.Status.NOT_FOUND)
			.entity(String.format("There's no subject with id %d", id)).build());
		return(Response.ok(s).build());
	}

	@GET
	@Path("/{code}/schedule")
	@UnitOfWork
	public Response getSchedule(@PathParam("code") String code) {

		log.info("getSchedule: code={}", code);
		Subject subject = subjectDAO.getByCode(code);
		ArrayList<String> requiredSubjectsCodes = new ArrayList<>();
		for (Subject s: subject.getRequiredSubjects())
			requiredSubjectsCodes.add(s.getCode());
		ArrayList<SubjectDTO.Student> students = new ArrayList<>();
		ArrayList<Student> student = subjectDAO.getStudentsInSubject(subject);
		for (Student p: student)
			students.add(new SubjectDTO.Student(p.getId(), p.getFirstName(), p.getLastName()));
		SubjectDTO subjectDTO = new SubjectDTO(subject.getName(), subject.getCode(), subject.getCredits(), subject.getRequiredCredits(), requiredSubjectsCodes, subject.getDegreeLevel(), subject.getProfessor(), students);
		return(Response.ok(subjectDTO).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(SubjectDTO entity) {

		log.info("save: {}", entity);
		if (entity == null)
			return(Response.status(Response.Status.BAD_REQUEST)
				.entity("json doesn't have a subject").build());
		if (subjectDAO.getByCode(entity.code) != null)
			return(Response.status(Response.Status.BAD_REQUEST)
				.entity(String.format("Subject with code %s already exists", entity.code)).build());

		ArrayList<Subject> requiredSubjects = new ArrayList<>();
		entity.requiredSubjectsCodes.forEach((code) -> {
			Subject aux = subjectDAO.getByCode(code);
			if (aux != null) requiredSubjects.add(aux);
		});
		Subject s = new Subject(entity.name, entity.code, entity.credits, entity.requiredCredits, requiredSubjects, entity.degreeLevel, entity.professor);
		return(Response.ok(subjectDAO.persist(s)).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class SubjectDTO {

		private String name;
		private String code;
		private Integer credits, requiredCredits;
		private ArrayList<String> requiredSubjectsCodes;
		private String degreeLevel;
		private String professor;

		private ArrayList<Student> students;

		@Getter
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

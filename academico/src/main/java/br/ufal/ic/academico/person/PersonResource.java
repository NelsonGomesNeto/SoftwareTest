package br.ufal.ic.academico.person;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseDAO;
import br.ufal.ic.academico.subject.Subject;
import br.ufal.ic.academico.subject.SubjectDAO;
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

@Path("person")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

	private final PersonDAO personDAO;
	private final CourseDAO courseDAO;
	private final SubjectDAO subjectDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Person> people = personDAO.getAll();
		return(Response.ok(people).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Person p = personDAO.get(id);
		return(Response.ok(p).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(PersonDTO entity) {

		log.info("save: {}", entity);
		Course studying = courseDAO.get(entity.studyingId);
		Person p = new Person(entity.firstName, entity.lastName, studying);
		return(Response.ok(personDAO.persist(p)).build());
	}

	@PUT
	@Path("/{pid}/enroll/{sid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response enroll(@PathParam("pid") Long pid, @PathParam("sid") Long sid) {

		log.info("pid={} enrolls sid={}", pid, sid);
		Person p = personDAO.get(pid);
		Subject subject = subjectDAO.get(sid);
//		if (subject.getRequiredCredits() > p.getCredits())
//			return(Response.status(428).entity("Student doesn't have enough credits (" + subject.getRequiredCredits().toString() + ")").build());
		p.enrollSubject(subject);
		return(Response.ok(personDAO.update(p)).build());
	}

	@PUT
	@Path("/{pid}/complete/{sid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response completeSubject(@PathParam("pid") Long pid, @PathParam("sid") Long sid) {

		log.info("pid={} completing sid={}", pid, sid);
		Person p = personDAO.get(pid);
		Subject subject = subjectDAO.get(sid);
		p.completeSubject(subject);
		return(Response.ok(personDAO.persist(p)).build());
	}

	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public Response delete(@PathParam("id") Long id) {

		log.info("delete: id={}", id);
		personDAO.delete(id);
		return(Response.status(Response.Status.NO_CONTENT).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class PersonDTO {

		private String firstName, lastName;
		private Long studyingId;
	}
}

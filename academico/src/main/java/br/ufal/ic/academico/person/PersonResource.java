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
import org.hibernate.Criteria;

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
	private final SubjectPersonDAO subjectPersonDAO;

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
	@Path("/{pid}/enrollCourse/{cid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response enrollCourse(@PathParam("pid") Long pid, @PathParam("cid") Long cid) {

		log.info("pid={} enrolls course cid={}", pid, cid);
		Person p = personDAO.get(pid);
		Course course = courseDAO.get(cid);
		if (p.getStudying() != null)
			return(Response.status(428).entity("Student already enrolled in a course").build());
		p.enrollCourse(course);
		return(Response.ok(personDAO.persist(p)).build());
	}

	@PUT
	@Path("/{pid}/enrollSubject/{sid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response enrollSubject(@PathParam("pid") Long pid, @PathParam("sid") Long sid) {

		log.info("pid={} enrolls subject sid={}", pid, sid);
		Person p = personDAO.get(pid);
		Subject subject = subjectDAO.get(sid);
		if (p.getStudying() == null)
			return(Response.status(428).entity("Student not in a course").build());
		if (!p.getStudying().getSubjects().contains(subject))
			return(Response.status(428).entity("Student's course doesn't have that subject").build());
		if (subject.getRequiredCredits() > p.getCredits())
			return(Response.status(428).entity("Student doesn't have enough credits (" + subject.getRequiredCredits().toString() + ")").build());
		for (Subject req: subject.getRequiredSubjects()) {
			int index = p.findSubjectIndex(req);
			if (index == -1 || !p.getSubjects().get(index).getCompleted())
				return(Response.status(428).entity("Student didn't complete required subjects").build());
		}
		SubjectPerson subjectPerson = new SubjectPerson(subject, false);
		p.enrollSubject(subjectPerson);
		subjectPersonDAO.persist(subjectPerson);
		return(Response.ok(personDAO.persist(p)).build());
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

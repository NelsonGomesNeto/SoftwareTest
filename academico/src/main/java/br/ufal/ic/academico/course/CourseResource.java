package br.ufal.ic.academico.course;

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

@Path("course")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class CourseResource {

	private final CourseDAO courseDAO;
	private final SubjectDAO subjectDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Course> courses = courseDAO.getAll();
		return(Response.ok(courses).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Course p = courseDAO.get(id);
		return(Response.ok(p).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(CourseDTO entity) {

		log.info("save: {}", entity);
		ArrayList<Subject> subjects = new ArrayList<>();
		entity.subjectsIds.forEach((id) -> subjects.add(subjectDAO.get(id)));
		Course p = new Course(entity.name, subjects);
		return(Response.ok(courseDAO.persist(p)).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class CourseDTO {

		private String name;
		private ArrayList<Integer> subjectsIds;
	}
}

package br.ufal.ic.academico.secretary;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseDAO;
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

@Path("secretary")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class SecretaryResource {

	private final SecretaryDAO secretaryDAO;
	private final CourseDAO courseDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Secretary> secretaries = secretaryDAO.getAll();
		return(Response.ok(secretaries).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Secretary s = secretaryDAO.get(id);
		return(Response.ok(s).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(SecretaryDTO entity) {

		log.info("save: {}", entity);
		ArrayList<Course> courses = new ArrayList<>();
		entity.coursesIds.forEach((id) -> courses.add(courseDAO.get(id)));
		Secretary s = new Secretary(entity.degreeLevel, courses);
		return(Response.ok(secretaryDAO.persist(s)).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class SecretaryDTO {

		private String degreeLevel;
		ArrayList<Long> coursesIds;
	}
}

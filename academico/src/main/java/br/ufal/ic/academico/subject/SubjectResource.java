package br.ufal.ic.academico.subject;

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
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Subject s = subjectDAO.get(id);
		return(Response.ok(s).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(SubjectDTO entity) {

		log.info("save: {}", entity);
		ArrayList<Subject> requiredSubjects = new ArrayList<>();
		entity.requiredSubjectsIds.forEach((id) -> requiredSubjects.add(subjectDAO.get(id)));
		Subject s = new Subject(entity.name, entity.code, entity.credits, entity.requiredCredits, requiredSubjects, entity.degreeLevel);
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

		private String name, code;
		private Integer credits, requiredCredits;
		private ArrayList<Long> requiredSubjectsIds;
		private String degreeLevel;
	}
}

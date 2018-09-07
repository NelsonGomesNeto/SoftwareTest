package br.ufal.ic.academico.person;

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
		Person p = new Person(entity.firstName, entity.lastName);
		return(Response.ok(personDAO.persist(p)).build());
	}

	@PUT
	@Path("/{id}")
	@UnitOfWork
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, PersonDTO entity) {

		log.info("update: id={}, {}", id, entity);
		Person p = personDAO.get(id);
		// TODO CHANGES (there's nothing to be changed right now)
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
	}
}

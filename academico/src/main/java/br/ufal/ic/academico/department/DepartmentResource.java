package br.ufal.ic.academico.department;

import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryDAO;
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

@Path("department")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource {

	private final DepartmentDAO departmentDAO;
	private final SecretaryDAO secretaryDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Department> departments = departmentDAO.getAll();
		return(Response.ok(departments).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Department d = departmentDAO.get(id);
		return(Response.ok(d).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(DepartmentDTO entity) {

		log.info("save: {}", entity);
		Secretary graduate = entity.graduateId == -1 ? null : secretaryDAO.get(entity.graduateId);
		Secretary postgraduate = entity.postgraduateId == -1 ? null : secretaryDAO.get(entity.postgraduateId);

		Department d = new Department(entity.name, graduate, postgraduate);
		return(Response.ok(departmentDAO.persist(d)).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class DepartmentDTO {

		private String name;
		private Long graduateId, postgraduateId;
	}
}

package br.ufal.ic.academico.department;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryDAO;
import br.ufal.ic.academico.subject.Subject;
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
import java.util.stream.Collectors;

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
	@Path("/notDeep")
	@UnitOfWork
	public Response getAllNotDeep() {

		log.info("getAllNotDeep");
		ArrayList<Department> departments = departmentDAO.getAll();
		ArrayList<DepartmentDTO> departmentDTOS = new ArrayList<>();
		ArrayList<DepartmentDTO.Subject> graduateSecretary = new ArrayList<>(), postgraduateSecretary = new ArrayList<>();
		for (Department d: departments) {
			if (d.getGraduate() != null)
				for (Course c: d.getGraduate().getCourses())
					for (Subject s: c.getSubjects()) {
						ArrayList<Long> arrayList = (ArrayList<Long>) s.getRequiredSubjects().stream().map(Subject::getId).collect(Collectors.toList());
						graduateSecretary.add(new DepartmentDTO.Subject(s.getName(), s.getId(), s.getCredits(), s.getRequiredCredits(), arrayList, s.getDegreeLevel(), s.getProfessor()));
					}
			if (d.getPostgraduate() != null)
				for (Course c: d.getPostgraduate().getCourses())
					for (Subject s: c.getSubjects()) {
						ArrayList<Long> arrayList = (ArrayList<Long>) s.getRequiredSubjects().stream().map(Subject::getId).collect(Collectors.toList());
						postgraduateSecretary.add(new DepartmentDTO.Subject(s.getName(), s.getId(), s.getCredits(), s.getRequiredCredits(), arrayList, s.getDegreeLevel(), s.getProfessor()));
					}
			departmentDTOS.add(new DepartmentDTO(d.getName(), graduateSecretary, postgraduateSecretary));
		}
		return(Response.ok(departmentDTOS).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Department d = departmentDAO.get(id);
		return(Response.ok(d).build());
	}

	@GET
	@Path("/{id}/subjects")
	@UnitOfWork
	public Response getSubjects(@PathParam("id") Long id) {

		Department d = departmentDAO.get(id);
		log.info("getSubjects");
		return(Response.ok(d.allSubjects()).build());
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
		@ToString.Exclude
		private Long graduateId, postgraduateId;
		private ArrayList<DepartmentDTO.Subject> graduateSecretary, postgraduateSecretary;

		public DepartmentDTO(String name, ArrayList<Subject> graduateSecretary, ArrayList<Subject> postgraduateSecretary) {
			this.name = name;
			this.graduateSecretary = graduateSecretary;
			this.postgraduateSecretary = postgraduateSecretary;
		}

		@Getter
		@ToString
		private static class Subject {
			private String name;
			private Long id;
			private Integer credits, requiredCredits;
			private ArrayList<Long> requiredSubjectsIds;
			private String degreeLevel;
			private String professor;

			private Subject(String name, Long id, Integer credits, Integer requiredCredits, ArrayList<Long> requiredSubjectsIds, String degreeLevel, String professor) {
				this.name = name;
				this.id = id;
				this.credits = credits;
				this.requiredCredits = requiredCredits;
				this.requiredSubjectsIds = requiredSubjectsIds;
				this.degreeLevel = degreeLevel;
				this.professor = professor;
			}
		}

	}
}

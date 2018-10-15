package br.ufal.ic.academico.student;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseDAO;
import br.ufal.ic.academico.department.Department;
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

@Path("student")
@Slf4j
@RequiredArgsConstructor
@Produces(MediaType.APPLICATION_JSON)
public class StudentResource {

	private final StudentDAO studentDAO;
	private final CourseDAO courseDAO;
	private final SubjectDAO subjectDAO;
	private final SubjectStudentDAO subjectStudentDAO;

	@GET
	@UnitOfWork
	public Response getAll() {

		log.info("getAll");
		ArrayList<Student> people = studentDAO.getAll();
		return(Response.ok(people).build());
	}

	@GET
	@Path("/names")
	@UnitOfWork
	public Response getAllName() {

		log.info("getAllName");
		return(Response.ok(studentDAO.getAllName()).build());
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getById(@PathParam("id") Long id) {

		log.info("getById: id={}", id);
		Student p = studentDAO.get(id);
		return(Response.ok(p).build());
	}

	@GET
	@Path("/{id}/proofOfEnrollment")
	@UnitOfWork
	public Response getProofOfEnrollment(@PathParam("id") Long id) {

		log.info("getProofOfEnrollment: id={}", id);
		Student student = studentDAO.get(id);
		ArrayList<ProofOfEnrollmentDTO.Subject> subjects = new ArrayList<>();
		for (SubjectStudent subject: student.getSubjects())
			subjects.add(new ProofOfEnrollmentDTO.Subject(subject.getSubject().getId(), subject.getSubject().getName(), subject.getSubject().getCode()));
		ProofOfEnrollmentDTO proofOfEnrollmentDTO = new ProofOfEnrollmentDTO(student.getFirstName(), student.getLastName(), student.getStudying().getName(), student.getId(), student.getCredits(), subjects);
		return(Response.ok(proofOfEnrollmentDTO).build());
	}

	@GET
	@Path("/{id}/subjectsFromDepartment")
	@UnitOfWork
	public Response getSubjectsFromDepartment(@PathParam("id") Long id) {

		log.info("getSubjectFromDepartment: id={}", id);
		Student p = studentDAO.get(id);
		Department d = courseDAO.getDepartment(p.getStudying());
		return(Response.ok(d.allSubjects()).build());
	}

	@POST
	@UnitOfWork
	@Consumes("application/json")
	public Response save(StudentDTO entity) {

		log.info("save: {}", entity);
		if (entity == null || entity.studyingId == null)
			return(Response.status(Response.Status.BAD_REQUEST)
				.entity("json doesn't have a student").build());
		if (courseDAO.get(entity.studyingId) == null)
			return(Response.status(Response.Status.BAD_REQUEST)
				.entity(String.format("Course with id %s doesn't exist", entity.studyingId.toString())).build());

		Course studying = courseDAO.get(entity.studyingId);
		Student p = new Student(entity.firstName, entity.lastName, studying);
		return(Response.ok(studentDAO.persist(p)).build());
	}

	@PUT
	@Path("/{stId}/enrollSubject/{sbCode}")
	@UnitOfWork
	@Consumes("application/json")
	public Response enrollSubject(@PathParam("stId") Long stId, @PathParam("sbCode") String sbCode) {

		log.info("stId={} enrolls subject sbCode={}", stId, sbCode);
		Student student = studentDAO.get(stId);
		if (student == null)
			return(Response.status(Response.Status.BAD_REQUEST).entity(
				String.format("Student with id %d doesn't exist", stId)).build());
		Subject subject = subjectDAO.getByCode(sbCode);
		if (subject == null)
			return(Response.status(Response.Status.BAD_REQUEST).entity(
				String.format("Subject with code %s doesn't exist", sbCode)).build());

		// First requirement
		if (subject.getRequiredCredits() > student.getCredits())
			return(Response.status(Response.Status.PRECONDITION_FAILED).entity("Student doesn't have enough credits (" + subject.getRequiredCredits().toString() + ")").build());
		for (Subject req: subject.getRequiredSubjects()) {
			int index = student.findSubjectIndex(req);
			if (index == -1 || !student.getSubjects().get(index).getCompleted())
				return(Response.status(Response.Status.PRECONDITION_FAILED).entity("Student didn't complete required subjects").build());
		}

		// Second requirement
		Department department = courseDAO.getDepartment(student.getStudying());
		if (department == null || !department.hasSubject(subject))
			return(Response.status(Response.Status.PRECONDITION_FAILED).entity("Student's department doesn't have that subject").build());
		if (student.getStudying().getDegreeLevel().equals("postgraduate") && subject.getDegreeLevel().equals("graduate"))
			return(Response.status(Response.Status.PRECONDITION_FAILED).entity("Postgraduate students can't enroll on graduate subjects").build());
		if (student.getStudying().getDegreeLevel().equals("graduate") && subject.getDegreeLevel().equals("postgraduate") && student.getCredits() < 170)
			return(Response.status(Response.Status.PRECONDITION_FAILED).entity("Graduate student doesn't have at least 170 credits to enroll postgraduate subjects").build());
		if (student.completedSubject(subject))
			return(Response.status(Response.Status.BAD_REQUEST).entity("Student already completed that subject").build());

		SubjectStudent subjectStudent = new SubjectStudent(subject, false);
		student.enrollSubject(subjectStudent);
		subjectStudentDAO.persist(subjectStudent);
		return(Response.ok(studentDAO.persist(student)).build());
	}

	@PUT
	@Path("/{stId}/complete/{sbCode}")
	@UnitOfWork
	@Consumes("application/json")
	public Response completeSubject(@PathParam("stId") Long stId, @PathParam("sbCode") String sbCode) {

		log.info("stId={} completing sbCode={}", stId, sbCode);
		Student student = studentDAO.get(stId);
		if (student == null)
			return(Response.status(Response.Status.BAD_REQUEST).entity(
				String.format("Student with id %d doesn't exist", stId)).build());
		Subject subject = subjectDAO.getByCode(sbCode);
		if (subject == null)
			return(Response.status(Response.Status.BAD_REQUEST).entity(
				String.format("Subject with code %s doesn't exist", sbCode)).build());

		student.completeSubject(subject);
		return(Response.ok(studentDAO.persist(student)).build());
	}

	@Getter
	@AllArgsConstructor
	@RequiredArgsConstructor
	@ToString
	public static class StudentDTO {

		private String firstName, lastName;
		private Long studyingId;
	}

	@Getter
	@AllArgsConstructor
	public static class ProofOfEnrollmentDTO {

		private String firstName, lastName, studying;
		private Long enrollmentId;
		private Integer credits;
		private ArrayList<ProofOfEnrollmentDTO.Subject> subjects;

		@Getter
		private static class Subject {
			private Long id;
			private String name;
			private String code;

			private Subject(Long id, String name, String code) {
				this.id = id;
				this.name = name;
				this.code = code;
			}
		}
	}
}

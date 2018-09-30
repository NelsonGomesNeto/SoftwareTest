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
			subjects.add(new ProofOfEnrollmentDTO.Subject(subject.getSubject().getId(), subject.getSubject().getName()));
		ProofOfEnrollmentDTO proofOfEnrollmentDTO = new ProofOfEnrollmentDTO(student.getFirstName(), student.getLastName(), student.getStudying().getName(), student.getId(), subjects);
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
		Course studying = courseDAO.get(entity.studyingId);
		Student p = new Student(entity.firstName, entity.lastName, studying);
		return(Response.ok(studentDAO.persist(p)).build());
	}

	@PUT
	@Path("/{pid}/enrollCourse/{cid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response enrollCourse(@PathParam("pid") Long pid, @PathParam("cid") Long cid) {

		log.info("pid={} enrolls course cid={}", pid, cid);
		Student p = studentDAO.get(pid);
		Course course = courseDAO.get(cid);
		if (p.getStudying() != null)
			return(Response.status(428).entity("Student already enrolled in a course").build());
		p.enrollCourse(course);
		return(Response.ok(studentDAO.persist(p)).build());
	}

	@PUT
	@Path("/{pid}/enrollSubject/{sid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response enrollSubject(@PathParam("pid") Long pid, @PathParam("sid") Long sid) {

		log.info("pid={} enrolls subject sid={}", pid, sid);
		Student p = studentDAO.get(pid);
		Subject subject = subjectDAO.get(sid);
		if (p.getStudying() == null)
			return(Response.status(428).entity("Student not in a course").build());
		if (!p.getStudying().getSubjects().contains(subject))
			return(Response.status(428).entity("Student's course doesn't have that subject").build());

		// First requirement
		if (subject.getRequiredCredits() > p.getCredits())
			return(Response.status(428).entity("Student doesn't have enough credits (" + subject.getRequiredCredits().toString() + ")").build());
		for (Subject req: subject.getRequiredSubjects()) {
			int index = p.findSubjectIndex(req);
			if (index == -1 || !p.getSubjects().get(index).getCompleted())
				return(Response.status(428).entity("Student didn't complete required subjects").build());
		}

		// Second requirement
		Department department = courseDAO.getDepartment(p.getStudying());
		if (department == null || !department.hasSubject(subject))
			return(Response.status(428).entity("Student's department doesn't have that subject").build());
		if (p.getStudying().getDegreeLevel().equals("postgraduate") && subject.getDegreeLevel().equals("graduate"))
			return(Response.status(428).entity("Postgraduate students can't enroll on graduate subjects").build());
		if (p.getStudying().getDegreeLevel().equals("graduate") && subject.getDegreeLevel().equals("postgraduate") && p.getCredits() < 170)
			return(Response.status(428).entity("Graduate student doesn't have at least 170 credits to enroll postgraduate subjects").build());
		if (p.completedSubject(subject))
			return(Response.status(428).entity("Student already completed that subject").build());

		SubjectStudent subjectStudent = new SubjectStudent(subject, false);
		p.enrollSubject(subjectStudent);
		subjectStudentDAO.persist(subjectStudent);
		return(Response.ok(studentDAO.persist(p)).build());
	}

	@PUT
	@Path("/{pid}/complete/{sid}")
	@UnitOfWork
	@Consumes("application/json")
	public Response completeSubject(@PathParam("pid") Long pid, @PathParam("sid") Long sid) {

		log.info("pid={} completing sid={}", pid, sid);
		Student p = studentDAO.get(pid);
		Subject subject = subjectDAO.get(sid);
		p.completeSubject(subject);
		return(Response.ok(studentDAO.persist(p)).build());
	}

	@DELETE
	@Path("/{id}")
	@UnitOfWork
	public Response delete(@PathParam("id") Long id) {

		log.info("delete: id={}", id);
		studentDAO.delete(id);
		return(Response.status(Response.Status.NO_CONTENT).build());
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
	@RequiredArgsConstructor
	@ToString
	public static class ProofOfEnrollmentDTO {

		private String firstName, lastName, studying;
		private Long enrollmentId;
		private ArrayList<ProofOfEnrollmentDTO.Subject> subjects;

		@Getter
		@ToString
		private static class Subject {
			private Long id;
			private String name;

			private Subject(Long id, String name) {
				this.id = id;
				this.name = name;
			}
		}
	}
}

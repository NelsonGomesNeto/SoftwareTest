package br.ufal.ic.academico;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseResource;
import br.ufal.ic.academico.department.DepartmentResource;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryResource;
import br.ufal.ic.academico.student.Student;
import br.ufal.ic.academico.student.StudentResource;
import br.ufal.ic.academico.student.SubjectStudent;
import br.ufal.ic.academico.subject.Subject;
import br.ufal.ic.academico.subject.SubjectResource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(DropwizardExtensionsSupport.class)
public class StudentTest {

	public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension<>(AcademicoApp.class, ResourceHelpers.resourceFilePath("config.yml"));
	public static Client client;
	public final Course course = new Course("Computer Science", new ArrayList<>(), "graduate");
	public final Subject subjectP1 = new Subject("P1", "CS1", 1, 0, new ArrayList<>(), "graduate", "Willy Tiengo");
//	public final Subject subjectP2 = new Subject("P2", "CS2", 1, 1, new ArrayList<>(), "graduate", "Willy Tiengo");
	public final Subject subjectArts = new Subject("Arts", "A1", 1, 0, new ArrayList<>(), "graduate", "Human");
	public final Student expected = new Student("Nelson", "Gomes", course);
	public static Student student, postgraduateStudent;
	public static ArrayList<Student> expectedStudents = new ArrayList<>();
	public static ArrayList<Subject> expectedSubjects = new ArrayList<>();

	@BeforeAll
	static void setup() {
		client = RULE.client();

		SubjectResource.SubjectDTO s = new SubjectResource.SubjectDTO("P1", "CS1", 170, 0, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		SubjectResource.SubjectDTO s2 = new SubjectResource.SubjectDTO("Arts", "A1", 1, 0, new ArrayList<>(), "graduate", "Human", null);
		SubjectResource.SubjectDTO s3 = new SubjectResource.SubjectDTO("P2", "CS2", 1, 1, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		ArrayList<String> required = new ArrayList<>(); required.add("CS1");
		SubjectResource.SubjectDTO s4 = new SubjectResource.SubjectDTO("P3", "CS3", 1, 0, required, "graduate", "Willy Tiengo", null);
		SubjectResource.SubjectDTO s5 = new SubjectResource.SubjectDTO("AI", "AI1", 1, 0, new ArrayList<>(), "postgraduate", "Machine", null);
		Subject sub = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s), Subject.class); // 1
		expectedSubjects.add(sub);
		client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s2), Subject.class); // 2
		sub = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s3), Subject.class); // 3
		expectedSubjects.add(sub);
		sub = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s4), Subject.class); // 4
		expectedSubjects.add(sub);
		sub = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s5), Subject.class); // 5
		expectedSubjects.add(sub);

		ArrayList<String> subjectsCodes = new ArrayList<>(); subjectsCodes.add("CS1"); subjectsCodes.add("CS2"); subjectsCodes.add("CS3");
		CourseResource.CourseDTO c = new CourseResource.CourseDTO("Computer Science", subjectsCodes, "graduate");
		client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c)); // 6
		subjectsCodes = new ArrayList<>(); subjectsCodes.add("AI1");
		c = new CourseResource.CourseDTO("Artificial Intelligence", subjectsCodes, "postgraduate");
		client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c)); // 7

		SecretaryResource.SecretaryDTO s6 = new SecretaryResource.SecretaryDTO("graduate");
		SecretaryResource.SecretaryDTO s7 = new SecretaryResource.SecretaryDTO("postgraduate");
		client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(s6)); // 8
		client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(s7)); // 9

		ArrayList<Long> coursesIds = new ArrayList<>(); coursesIds.add(6L);
		client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 8))
			.request().put(Entity.json(coursesIds), Secretary.class);
		coursesIds = new ArrayList<>(); coursesIds.add(7L);
		client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 9))
			.request().put(Entity.json(coursesIds), Secretary.class);
	}

	@Test
	void orderedTests() {
		createStudent();
		getById();
		getAllName();
		getAll();
		enrollSubject();
		getSubjectsFromDepartment();
		getProofOfEnrollment();
	}

	void createStudent() {

		StudentResource.StudentDTO s = new StudentResource.StudentDTO("Nelson", "Gomes", 6L);
		StudentResource.StudentDTO s2 = new StudentResource.StudentDTO("Nelson", "Gomes", 2L);
		StudentResource.StudentDTO s3 = new StudentResource.StudentDTO("Post", "Graduate", 7L);

		student = client.target(
			String.format("http://localhost:%d/academico/student/", RULE.getLocalPort()))
			.request().post(Entity.json(s), Student.class); // 10
		expectedStudents.add(student);

		postgraduateStudent = client.target(
			String.format("http://localhost:%d/academico/student/", RULE.getLocalPort()))
			.request().post(Entity.json(s3), Student.class); // 11
		expectedStudents.add(postgraduateStudent);

		Response cantCreate = client.target(
			String.format("http://localhost:%d/academico/student/", RULE.getLocalPort()))
			.request().post(Entity.json(s2));
		Response cantCreate2 = client.target(
			String.format("http://localhost:%d/academico/student/", RULE.getLocalPort()))
			.request().post(Entity.json(null));

		assertAll("failed student creation",
			() -> assertEquals("Nelson", student.getFirstName(), "first name wasn't saved"),
			() -> assertEquals("Gomes", student.getLastName(), "last name wasn't saved"),
			() -> assertEquals(0, student.getCredits().intValue(), "credits aren't 0"),
			() -> assertEquals(course, student.getStudying(), "course wasn't saved"),
			() -> assertEquals(new ArrayList<SubjectStudent>(), student.getSubjects(), "subjects wasn't saved")
		);
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), cantCreate.getStatus(), "shouldn't create student when course is invalid");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), cantCreate.getStatus(), "invalid object shouldn't create student");
	}

	void enrollSubject() {

		// if (student == null)
		Response undefinedStudent = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), 0, "A1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), undefinedStudent.getStatus(), "shouldn't enroll with undefined student id");
		// if (subject == null)
		Response undefinedSubject = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "AA"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), undefinedSubject.getStatus(), "shouldn't enroll with undefined subject code");

		// First requirement
		// if (subject.getRequiredCredits() > student.getCredits())
		Response notEnoughCredits = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "CS2"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), notEnoughCredits.getStatus(), "shouldn't enroll when student doesn't have enough credits");

		// for (Subject req: subject.getRequiredSubjects()) {
		Response didntCompleteRequired = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "CS3"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), didntCompleteRequired.getStatus(), "shouldn't enroll when student didn't complete required subjects");


		// Second requirement
		// TODO
		// if (department == null || !department.hasSubject(subject))
		Response undefinedDepartment = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "CS1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), undefinedDepartment.getStatus(), "shouldn't enroll when student's department doesn't exist");

		DepartmentResource.DepartmentDTO d = new DepartmentResource.DepartmentDTO("Computing Institute", 8L, 9L, null, null);
		LinkedHashMap department = client.target(
			String.format("http://localhost:%d/academico/department/", RULE.getLocalPort()))
			.request().post(Entity.json(d), LinkedHashMap.class);
//
		// if (department == null || !department.hasSubject(subject))
		Response undefinedSubjectOnDepartment = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "A1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), undefinedSubjectOnDepartment.getStatus(), "shouldn't enroll when student's department doesn't have wanted subject");

		// if (student.getStudying().getDegreeLevel().equals("postgraduate") && subject.getDegreeLevel().equals("graduate"))
		Response postgraduateCantDoGraduate = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), postgraduateStudent.getId(), "CS1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), postgraduateCantDoGraduate.getStatus(), "shouldn't enroll postgraduate student on graduate subject");
		Response postgraduateCanPostgraduate = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), postgraduateStudent.getId(), "AI1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), postgraduateCanPostgraduate.getStatus(), "shouldn't enroll postgraduate student on postgraduate subject");

		// if (student.getStudying().getDegreeLevel().equals("graduate") && subject.getDegreeLevel().equals("postgraduate") && student.getCredits() < 170)
		Response graduateCantDoPostgraduateWithoutCredits = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "AI1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.PRECONDITION_FAILED.getStatusCode(), graduateCantDoPostgraduateWithoutCredits.getStatus(), "shouldn't enroll graduate student (with less then 170 credits) on postgraduate");

		completeSubject();

		// if (student.getStudying().getDegreeLevel().equals("graduate") && subject.getDegreeLevel().equals("postgraduate") && student.getCredits() < 170) VALID
		Response graduateCanDoPostgraduateWithCredits = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "AI1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), graduateCanDoPostgraduateWithCredits.getStatus(), "should enroll graduate student (with more then 170 credits) on postgraduate");

		// if (student.completedSubject(subject))
		Response graduateSimpleEnroll = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "CS1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), graduateSimpleEnroll.getStatus(), "shouldn't enroll student on completed course");
	}

	void completeSubject() {

		Response graduateSimpleEnroll = client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), student.getId(), "CS1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), graduateSimpleEnroll.getStatus(), "should enroll student");

		Response completeSubject = client.target(
			String.format("http://localhost:%d/academico/student/%d/complete/%s", RULE.getLocalPort(), 0, "AI1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), completeSubject.getStatus(), "shouldn't complete when student's id is invalid");
		completeSubject = client.target(
			String.format("http://localhost:%d/academico/student/%d/complete/%s", RULE.getLocalPort(), student.getId(), "AA"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), completeSubject.getStatus(), "shouldn't complete when student's id is invalid");

		completeSubject = client.target(
			String.format("http://localhost:%d/academico/student/%d/complete/%s", RULE.getLocalPort(), student.getId(), "CS1"))
			.request().put(Entity.json(""));
		assertEquals(Response.Status.OK.getStatusCode(), completeSubject.getStatus(), "shouldn't complete when student's id is invalid");
	}

	void getById() {

		Student s = client.target(
			String.format("http://localhost:%d/academico/student/%d", RULE.getLocalPort(), student.getId()))
			.request().get(Student.class);
		assertAll("failed to get student",
			() -> assertEquals("Nelson", student.getFirstName(), "first name wasn't saved"),
			() -> assertEquals("Gomes", student.getLastName(), "last name wasn't saved"),
			() -> assertEquals(0, student.getCredits().intValue(), "credits aren't 0"),
			() -> assertEquals(course, student.getStudying(), "course wasn't saved"),
			() -> assertEquals(new ArrayList<SubjectStudent>(), student.getSubjects(), "subjects wasn't saved")
		);

		LinkedHashMap linkedHashMap = client.target(
			String.format("http://localhost:%d/academico/student/%d", RULE.getLocalPort(), 0))
			.request().get(LinkedHashMap.class);
		assertNull(linkedHashMap, "student with invalid id should return null");
	}

	void getAllName() {

		ArrayList<String> names = client.target(
			String.format("http://localhost:%d/academico/student/names", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ArrayList<String> expectedNames = new ArrayList<>(); expectedNames.add("Gomes, Nelson"); expectedNames.add("Graduate, Post");
		assertEquals(expectedNames, names, "failed to get names from all students");
	}

	void getAll() {

		ArrayList<LinkedHashMap> studentsJSON = client.target(
			String.format("http://localhost:%d/academico/student", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Student> students = new ArrayList<>();
		for (LinkedHashMap l: studentsJSON) students.add(mapper.convertValue(l, Student.class));
		assertEquals(expectedStudents, students, "failed to get all students");
	}

	void getSubjectsFromDepartment() {

		ArrayList<LinkedHashMap> subjectsJSON = client.target(
			String.format("http://localhost:%d/academico/student/%d/subjectsFromDepartment", RULE.getLocalPort(), student.getId()))
			.request().get(ArrayList.class);
		System.out.println(subjectsJSON);
		ArrayList<Subject> subjects = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (LinkedHashMap l: subjectsJSON) subjects.add(mapper.convertValue(l, Subject.class));

		assertEquals(expectedSubjects, subjects, "failed to get all subject from student's department");
	}

	void getProofOfEnrollment() {

		LinkedHashMap proofOfEnrollmentJSON = client.target(
			String.format("http://localhost:%d/academico/student/%d/proofOfEnrollment", RULE.getLocalPort(), student.getId()))
			.request().get(LinkedHashMap.class);
		String expectedProofOfEnrollment = "{firstName=Nelson, lastName=Gomes, studying=Computer Science, enrollmentId=10, credits=170, subjects=[{id=1, name=P1, code=CS1}, {id=5, name=AI, code=AI1}]}";
		assertEquals(expectedProofOfEnrollment, proofOfEnrollmentJSON.toString(), "failed to get proofOfEnrollment");
	}
}

package br.ufal.ic.academico;

import br.ufal.ic.academico.course.CourseResource;
import br.ufal.ic.academico.department.DepartmentResource;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryResource;
import br.ufal.ic.academico.student.StudentResource;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SubjectTest {

	public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension<>(AcademicoApp.class, ResourceHelpers.resourceFilePath("config.yml"));
	public static Client client;
	public final Subject expected = new Subject("P1", "CS1", 1, 0, new ArrayList<>(), "graduate", "Willy Tiengo");
	public static ArrayList<Subject> expectedSubjects = new ArrayList<>();

	@BeforeAll
	static void setup() {
		client = RULE.client();
	}

	void setupStudents() {
		ArrayList<String> subjectsCodes = new ArrayList<>(); subjectsCodes.add("CS1"); subjectsCodes.add("CS2");
		CourseResource.CourseDTO c = new CourseResource.CourseDTO("Computer Science", subjectsCodes, "graduate");
		client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c)); // 3

		SecretaryResource.SecretaryDTO secretary = new SecretaryResource.SecretaryDTO("graduate");
		client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(secretary)); // 4
		ArrayList<Long> coursesIds = new ArrayList<>(); coursesIds.add(3L);
		client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 4))
			.request().put(Entity.json(coursesIds), Secretary.class);

		DepartmentResource.DepartmentDTO d = new DepartmentResource.DepartmentDTO("Computing Institute", 4L, -1L, null, null);
		client.target(
			String.format("http://localhost:%d/academico/department/", RULE.getLocalPort()))
			.request().post(Entity.json(d)); // 5

		StudentResource.StudentDTO s = new StudentResource.StudentDTO("Nelson", "Gomes", 3L);
		client.target(
			String.format("http://localhost:%d/academico/student/", RULE.getLocalPort()))
			.request().post(Entity.json(s)); // 6

		client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), 6, "CS1"))
			.request().put(Entity.json(""));

		client.target(
			String.format("http://localhost:%d/academico/student/%d/complete/%s", RULE.getLocalPort(), 6, "CS1"))
			.request().put(Entity.json(""));

		client.target(
			String.format("http://localhost:%d/academico/student/%d/enrollSubject/%s", RULE.getLocalPort(), 6, "CS2"))
			.request().put(Entity.json(""));
	}

	@Test
	void orderedTests() {
		createSubject();
		setupStudents();
		getById();
		getByCode();
		getAll();
		getSchedule();
		equals();
	}

	void createSubject() {

		SubjectResource.SubjectDTO s = new SubjectResource.SubjectDTO("P1", "CS1", 1, 0, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		ArrayList<String> req = new ArrayList<>(); req.add("CS1");
		SubjectResource.SubjectDTO s2 = new SubjectResource.SubjectDTO("P2", "CS2", 1, 0, req, "graduate", "Willy Tiengo", null);

		Subject subject = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s), Subject.class); // 1
		expectedSubjects.add(subject);
		Subject aux = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s2), Subject.class); // 2
		expectedSubjects.add(aux);

		Response cantCreate = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s));

		Response cantCreate2 = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(null));

		assertEquals(expected, subject, "failed subject creation");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), cantCreate.getStatus(), "shouldn't create subject with same code");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), cantCreate2.getStatus(), "incompatible object shouldn't create subject");
	}

	void getById() {

		Subject subject = client.target(
			String.format("http://localhost:%d/academico/subject/1", RULE.getLocalPort()))
			.request().get(Subject.class);

		Response notFound = client.target(
			String.format("http://localhost:%d/academico/subject/0", RULE.getLocalPort()))
			.request().get();

		assertEquals(expected, subject, "failed to get course by id");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't have found anything");
	}

	void getByCode() {

		Subject subject = client.target(
			String.format("http://localhost:%d/academico/subject/code/CS1", RULE.getLocalPort()))
			.request().get(Subject.class);

		Response notFound = client.target(
			String.format("http://localhost:%d/academico/subject/code/AA", RULE.getLocalPort()))
			.request().get();

		assertEquals(expected, subject, "failed to get course by code");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't have found anything");
	}

	void getAll() {

		ArrayList<LinkedHashMap> subjectsJSON = client.target(
			String.format("http://localhost:%d/academico/subject", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Subject> subjects = new ArrayList<>();
		for (LinkedHashMap l: subjectsJSON) subjects.add(mapper.convertValue(l, Subject.class));
		assertEquals(expectedSubjects, subjects, "failed to get all subjects");
	}

	void getSchedule() {

		LinkedHashMap scheduleJSON = client.target(
			String.format("http://localhost:%d/academico/subject/%s/schedule", RULE.getLocalPort(), "CS2"))
			.request().get(LinkedHashMap.class);
		System.out.println(scheduleJSON);
		String expectedSchedule = "{name=P2, code=CS2, credits=1, requiredCredits=0, requiredSubjectsCodes=[CS1], degreeLevel=graduate, professor=Willy Tiengo, students=[{enrollmentId=6, firstName=Nelson, lastName=Gomes}]}";
		assertEquals(expectedSchedule, scheduleJSON.toString(), "failed to get subject's schedule");
	}

	void equals() {

		Subject a = new Subject("a", "a", 0, 0, new ArrayList<>(), "graduate", "a"),
			b = new Subject("a", "a", 0, 0, new ArrayList<>(), "graduate", "a"),
			c = new Subject("a", "b", 0, 0, new ArrayList<>(), "graduate", "a");
		assertAll("equality of courses failing",
			() -> assertEquals(a, b, "objects should be equal"),
			() -> assertNotEquals(a, c, "objects shouldn't be equal because of code"),
			() -> assertNotEquals(a, null, "invalid comparision should return false"));
	}
}

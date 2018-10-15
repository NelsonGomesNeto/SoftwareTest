package br.ufal.ic.academico;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseResource;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryResource;
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
import java.util.Comparator;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(DropwizardExtensionsSupport.class)
public class SecretaryTest {

	public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension<>(AcademicoApp.class, ResourceHelpers.resourceFilePath("config.yml"));
	public static Client client;
	public static Course course;
	public static Secretary expected = new Secretary("graduate", new ArrayList<>());

	@BeforeAll
	static void setup() {
		client = RULE.client();

		SubjectResource.SubjectDTO s = new SubjectResource.SubjectDTO("P1", "CS1", 1, 0, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		SubjectResource.SubjectDTO s2 = new SubjectResource.SubjectDTO("Arts", "A1", 1, 0, new ArrayList<>(), "graduate", "Human", null);
		SubjectResource.SubjectDTO s3 = new SubjectResource.SubjectDTO("P2", "CS2", 1, 1, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		ArrayList<String> required = new ArrayList<>(); required.add("CS1");
		SubjectResource.SubjectDTO s4 = new SubjectResource.SubjectDTO("P3", "CS3", 1, 0, required, "graduate", "Willy Tiengo", null);
		client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s));
		client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s2));
		client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s3));
		client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s4));

		ArrayList<String> subjectsCodes = new ArrayList<>(); subjectsCodes.add("CS1"); subjectsCodes.add("CS2"); subjectsCodes.add("CS3");
		CourseResource.CourseDTO c = new CourseResource.CourseDTO("Computer Science", subjectsCodes, "graduate");
		course = client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c), Course.class);
	}

	@Test
	void orderedTests() {
		createSecretary();
		getById();
		addCourses();
		getById();
		getAll();
	}

	void createSecretary() {

		SecretaryResource.SecretaryDTO s = new SecretaryResource.SecretaryDTO("graduate");
		Secretary secretary = client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(s), Secretary.class);
		assertEquals(expected, secretary, "failed secretary creation");
		client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(s), Secretary.class);
	}

	void addCourses() {

		ArrayList<Long> coursesIds = new ArrayList<>(); coursesIds.add(5L); coursesIds.add(1L);
		ArrayList<Course> courses = new ArrayList<>(); courses.add(course);
		expected.getCourses().addAll(courses);
		Secretary secretary = client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 6))
			.request().put(Entity.json(coursesIds), Secretary.class);
		assertEquals(expected, secretary, "failed to add courses to secretary");

		Response notFound = client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 1))
			.request().put(Entity.json(coursesIds));
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't find secretary with invalid id");
	}

	void getById() {

		Secretary secretary = client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 6))
			.request().get(Secretary.class);
		assertEquals(expected, secretary, "failed to get secretary");

		Response notFound = client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 1))
			.request().get();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't find secretary with invalid id");
	}

	void getAll() {

		Secretary s = new Secretary("graduate", new ArrayList<>());
		ArrayList<Secretary> secretariesExpected = new ArrayList<>(); secretariesExpected.add(s); secretariesExpected.add(expected);
		ArrayList<Secretary> secretaries = new ArrayList<>();
		ArrayList<LinkedHashMap> ss = client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ObjectMapper objectMapper = new ObjectMapper();
		for (LinkedHashMap a: ss) secretaries.add(objectMapper.convertValue(a, Secretary.class));
		secretaries.sort(new Comparator<Secretary>() {
			@Override
			public int compare(Secretary o1, Secretary o2) {
				if (o1.getId() > o2.getId()) return -1;
				return 1;
			}
		});
		assertEquals(secretariesExpected, secretaries, "failed to get all secretaries");

	}
}

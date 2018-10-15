package br.ufal.ic.academico;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseResource;
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
public class CourseTest {

	public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension<>(AcademicoApp.class, ResourceHelpers.resourceFilePath("config.yml"));
	public static Client client;
	final Course expected = new Course("Computer Science", new ArrayList<>(), "graduate");
	public static ArrayList<Course> expectedCourses = new ArrayList<>();

	@BeforeAll
	static void setup() {
		client = RULE.client();
	}

	@Test
	void orderedTests() {
		createCourse();
		getCourseById();
		getAll();
		equals();
	}

//	@Test
	void createCourse() {

		CourseResource.CourseDTO c = new CourseResource.CourseDTO("Computer Science", new ArrayList<>(), "graduate");

		Course course = client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c), Course.class);
		expectedCourses.add(course);

		Response cantCreate = client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c));

		Response cantCreate2 = client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(null));

		assertEquals(expected, course, "failed course creation");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), cantCreate.getStatus(), "shouldn't create courses with same name and degree level");
		assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), cantCreate2.getStatus(), "incompatible object shouldn't create course");
	}

//	@Test
	void getCourseById() {

		Course course = client.target(
			String.format("http://localhost:%d/academico/course/1", RULE.getLocalPort()))
			.request().get(Course.class);
		Response notFound = client.target(
			String.format("http://localhost:%d/academico/course/2", RULE.getLocalPort()))
			.request().get();

		assertEquals(expected, course, "failed to get course by id");
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't have found anything");
	}

	void getAll() {

		ArrayList<LinkedHashMap> coursesJSON = client.target(
			String.format("http://localhost:%d/academico/course", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ObjectMapper mapper = new ObjectMapper();
		ArrayList<Course> courses = new ArrayList<>();
		for (LinkedHashMap l: coursesJSON) courses.add(mapper.convertValue(l, Course.class));
		assertEquals(expectedCourses, courses, "failed to get all courses");
	}

	void equals() {

		Course a = new Course("Computer Science", new ArrayList<>(), "graduate"),
			b = new Course("Computer Science", new ArrayList<>(), "graduate"),
			c = new Course("Computer Science", new ArrayList<>(), "postgraduate"),
			d = new Course("Arts", new ArrayList<>(), "graduate");
		assertAll("equality of courses failing",
			() -> assertEquals(a, b, "objects should be equal"),
			() -> assertNotEquals(a, c, "objects shouldn't be equal because of degree level"),
			() -> assertNotEquals(a, d, "objects shouldn't be equal because of name"),
			() -> assertNotEquals(a, null, "invalid comparision should return false"));
	}
}

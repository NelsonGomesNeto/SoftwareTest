package br.ufal.ic.academico;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseResource;
import br.ufal.ic.academico.department.Department;
import br.ufal.ic.academico.department.DepartmentResource;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryResource;
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
public class DepartmentTest {

	public static DropwizardAppExtension<ConfigApp> RULE = new DropwizardAppExtension<>(AcademicoApp.class, ResourceHelpers.resourceFilePath("config.yml"));
	public static Client client;
	public static Secretary graduate, postgraduate;
	public static Department expectedWithGraduate, expectedWithoutSecretary;
	public static ArrayList<Subject> expectedSubjects = new ArrayList<>();
	public static ArrayList<Department> expectedDepartments = new ArrayList<>();

	@BeforeAll
	static void setup() {
		client = RULE.client();

		SubjectResource.SubjectDTO s = new SubjectResource.SubjectDTO("P1", "CS1", 1, 0, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		SubjectResource.SubjectDTO s2 = new SubjectResource.SubjectDTO("Arts", "A1", 1, 0, new ArrayList<>(), "graduate", "Human", null);
		SubjectResource.SubjectDTO s3 = new SubjectResource.SubjectDTO("P2", "CS2", 1, 1, new ArrayList<>(), "graduate", "Willy Tiengo", null);
		ArrayList<String> required = new ArrayList<>(); required.add("CS1");
		SubjectResource.SubjectDTO s4 = new SubjectResource.SubjectDTO("P3", "CS3", 1, 0, required, "graduate", "Willy Tiengo", null);
		Subject ss = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s), Subject.class);
		expectedSubjects.add(ss);
		client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s2));
		ss = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s3), Subject.class);
		expectedSubjects.add(ss);
		ss = client.target(
			String.format("http://localhost:%d/academico/subject/", RULE.getLocalPort()))
			.request().post(Entity.json(s4), Subject.class);
		expectedSubjects.add(ss);

		ArrayList<String> subjectsCodes = new ArrayList<>(); subjectsCodes.add("CS1"); subjectsCodes.add("CS2"); subjectsCodes.add("CS3");
		CourseResource.CourseDTO c = new CourseResource.CourseDTO("Computer Science", subjectsCodes, "graduate");
		client.target(
			String.format("http://localhost:%d/academico/course/", RULE.getLocalPort()))
			.request().post(Entity.json(c), Course.class);

		SecretaryResource.SecretaryDTO s5 = new SecretaryResource.SecretaryDTO("graduate");
		client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(s5));
		SecretaryResource.SecretaryDTO s6 = new SecretaryResource.SecretaryDTO("postgraduate");
		postgraduate = client.target(
			String.format("http://localhost:%d/academico/secretary/", RULE.getLocalPort()))
			.request().post(Entity.json(s6), Secretary.class);
		ArrayList<Long> coursesIds = new ArrayList<>(); coursesIds.add(5L);
		graduate = client.target(
			String.format("http://localhost:%d/academico/secretary/%d", RULE.getLocalPort(), 6))
			.request().put(Entity.json(coursesIds), Secretary.class);

		expectedWithGraduate = new Department("Computing Institute", graduate, postgraduate);
		expectedWithoutSecretary = new Department("Computing Institute", null, null);
	}

	@Test
	void orderedTests() {
		createDepartment();
		getById();
		getSubjects();
		getAll();
		getAllNotDeep();
		equals();
	}

	void createDepartment() {

		DepartmentResource.DepartmentDTO d = new DepartmentResource.DepartmentDTO("Computing Institute", 6L, 7L, null, null);
		final Department dd = client.target(
			String.format("http://localhost:%d/academico/department/", RULE.getLocalPort()))
			.request().post(Entity.json(d), Department.class);
		assertAll("failed department creation",
			() -> assertEquals(expectedWithGraduate.getName(), dd.getName(), "didn't save name"),
			() -> assertEquals(expectedWithGraduate.getGraduate(), dd.getGraduate(), "didn't save graduate secretary"),
			() -> assertEquals(expectedWithGraduate.getPostgraduate(), dd.getPostgraduate(), "didn't save postgraduate"));
		expectedDepartments.add(dd);

		d = new DepartmentResource.DepartmentDTO("Computing Institute", -1L, -1L, null, null);
		final Department ddd = client.target(
			String.format("http://localhost:%d/academico/department/", RULE.getLocalPort()))
			.request().post(Entity.json(d), Department.class);
		assertAll("failed department creation",
			() -> assertEquals(expectedWithoutSecretary.getName(), ddd.getName(), "didn't save name"),
			() -> assertEquals(expectedWithoutSecretary.getGraduate(), ddd.getGraduate(), "didn't save graduate secretary"),
			() -> assertEquals(expectedWithoutSecretary.getPostgraduate(), ddd.getPostgraduate(), "didn't save postgraduate"));
		expectedDepartments.add(ddd);

		d = new DepartmentResource.DepartmentDTO("Computing Institute", -1L, 6L, null, null);
		final Department dddd = client.target(
			String.format("http://localhost:%d/academico/department/", RULE.getLocalPort()))
			.request().post(Entity.json(d), Department.class);
		assertAll("failed department creation",
			() -> assertEquals(expectedWithoutSecretary.getName(), dddd.getName(), "didn't save name"),
			() -> assertEquals(expectedWithoutSecretary.getGraduate(), dddd.getGraduate(), "didn't save graduate secretary"),
			() -> assertEquals(expectedWithoutSecretary.getPostgraduate(), dddd.getPostgraduate(), "didn't save postgraduate"));
		expectedDepartments.add(dddd);
	}

	void getById() {

		final Department dd = client.target(
			String.format("http://localhost:%d/academico/department/8", RULE.getLocalPort()))
			.request().get(Department.class);
		assertAll("failed department creation",
			() -> assertEquals(expectedWithGraduate.getName(), dd.getName(), "didn't save name"),
			() -> assertEquals(expectedWithGraduate.getGraduate(), dd.getGraduate(), "didn't save graduate secretary"),
			() -> assertEquals(expectedWithGraduate.getPostgraduate(), dd.getPostgraduate(), "didn't save postgraduate"));

		final Department ddd = client.target(
			String.format("http://localhost:%d/academico/department/9", RULE.getLocalPort()))
			.request().get(Department.class);
		assertAll("failed department creation",
			() -> assertEquals(expectedWithoutSecretary.getName(), ddd.getName(), "didn't save name"),
			() -> assertEquals(expectedWithoutSecretary.getGraduate(), ddd.getGraduate(), "didn't save graduate secretary"),
			() -> assertEquals(expectedWithoutSecretary.getPostgraduate(), ddd.getPostgraduate(), "didn't save postgraduate"));

		final Department dddd = client.target(
			String.format("http://localhost:%d/academico/department/10", RULE.getLocalPort()))
			.request().get(Department.class);
		assertAll("failed department creation",
			() -> assertEquals(expectedWithoutSecretary.getName(), dddd.getName(), "didn't save name"),
			() -> assertEquals(expectedWithoutSecretary.getGraduate(), dddd.getGraduate(), "didn't save graduate secretary"),
			() -> assertEquals(expectedWithoutSecretary.getPostgraduate(), dddd.getPostgraduate(), "didn't save postgraduate"));

		Response notFound = client.target(
			String.format("http://localhost:%d/academico/department/1", RULE.getLocalPort()))
			.request().get();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't found department with invalid id");
	}

	void getSubjects() {

		ArrayList<LinkedHashMap> subjectsJSON = client.target(
			String.format("http://localhost:%d/academico/department/8/subjects", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ArrayList<Subject> subjects = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (LinkedHashMap l: subjectsJSON) subjects.add(mapper.convertValue(l, Subject.class));
		assertEquals(expectedSubjects, subjects, "failed to retrieve subjects from department");

		Response notFound = client.target(
			String.format("http://localhost:%d/academico/department/1/subjects", RULE.getLocalPort()))
			.request().get();
		assertEquals(Response.Status.NOT_FOUND.getStatusCode(), notFound.getStatus(), "shouldn't found department with invalid id");
	}

	void getAll() {

		ArrayList<LinkedHashMap> departmentsJSON = client.target(
			String.format("http://localhost:%d/academico/department", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		ArrayList<Department> departments = new ArrayList<>();
		ObjectMapper mapper = new ObjectMapper();
		for (LinkedHashMap l: departmentsJSON) departments.add(mapper.convertValue(l, Department.class));

		assertEquals(expectedDepartments, departments, "failed getting all departments");
	}

	void getAllNotDeep() {

		ArrayList<LinkedHashMap> departmentsJSON = client.target(
			String.format("http://localhost:%d/academico/department/notDeep", RULE.getLocalPort()))
			.request().get(ArrayList.class);
		String expectedDepartments = "[{name=Computing Institute, graduateId=null, postgraduateId=null, graduateSecretary=[{name=P1, id=1, credits=1, requiredCredits=0, requiredSubjectsIds=[], degreeLevel=graduate, professor=Willy Tiengo}, {name=P2, id=3, credits=1, requiredCredits=1, requiredSubjectsIds=[], degreeLevel=graduate, professor=Willy Tiengo}, {name=P3, id=4, credits=1, requiredCredits=0, requiredSubjectsIds=[1], degreeLevel=graduate, professor=Willy Tiengo}], postgraduateSecretary=[]}, {name=Computing Institute, graduateId=null, postgraduateId=null, graduateSecretary=[{name=P1, id=1, credits=1, requiredCredits=0, requiredSubjectsIds=[], degreeLevel=graduate, professor=Willy Tiengo}, {name=P2, id=3, credits=1, requiredCredits=1, requiredSubjectsIds=[], degreeLevel=graduate, professor=Willy Tiengo}, {name=P3, id=4, credits=1, requiredCredits=0, requiredSubjectsIds=[1], degreeLevel=graduate, professor=Willy Tiengo}], postgraduateSecretary=[]}, {name=Computing Institute, graduateId=null, postgraduateId=null, graduateSecretary=[{name=P1, id=1, credits=1, requiredCredits=0, requiredSubjectsIds=[], degreeLevel=graduate, professor=Willy Tiengo}, {name=P2, id=3, credits=1, requiredCredits=1, requiredSubjectsIds=[], degreeLevel=graduate, professor=Willy Tiengo}, {name=P3, id=4, credits=1, requiredCredits=0, requiredSubjectsIds=[1], degreeLevel=graduate, professor=Willy Tiengo}], postgraduateSecretary=[]}]";
		System.out.println(departmentsJSON);
		assertEquals(expectedDepartments, departmentsJSON.toString(), "failed getting all departments without deep");
	}

	void equals() {

		Department a = new Department("IC", null, null), b = new Department("IC", null, null),
			c = new Department("LOL", null, null);
		assertAll("equality of courses failing",
			() -> assertEquals(a, b, "objects should be equal"),
			() -> assertNotEquals(a, c, "objects shouldn't be equal because of name"),
			() -> assertNotEquals(a, null, "invalid comparision should return false"));
	}
}

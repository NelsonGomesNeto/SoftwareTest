package br.ufal.ic.academico;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseDAO;
import br.ufal.ic.academico.course.CourseResource;
import br.ufal.ic.academico.department.Department;
import br.ufal.ic.academico.department.DepartmentDAO;
import br.ufal.ic.academico.department.DepartmentResource;
import br.ufal.ic.academico.student.*;
import br.ufal.ic.academico.secretary.Secretary;
import br.ufal.ic.academico.secretary.SecretaryDAO;
import br.ufal.ic.academico.secretary.SecretaryResource;
import br.ufal.ic.academico.subject.Subject;
import br.ufal.ic.academico.subject.SubjectDAO;
import br.ufal.ic.academico.subject.SubjectResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import javafx.scene.Scene;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author Willy
 */
@Slf4j
public class AcademicoApp extends Application<ConfigApp> {

    public static void main(String[] args) throws Exception {
        new AcademicoApp().run(args);
    }

    @Override
    public String getName() {
        return "hello-world";
    }

    @Override
    public void initialize(Bootstrap<ConfigApp> bootstrap) {
        log.info("initialize");
        bootstrap.addBundle(hibernate);
    }

    @Override
    public void run(ConfigApp config, Environment environment) {

        final StudentDAO studentDAO = new StudentDAO(hibernate.getSessionFactory());
        final SubjectDAO subjectDAO = new SubjectDAO(hibernate.getSessionFactory());
	    final CourseDAO courseDAO = new CourseDAO(hibernate.getSessionFactory());
	    final SecretaryDAO secretaryDAO = new SecretaryDAO(hibernate.getSessionFactory());
	    final DepartmentDAO departmentDAO = new DepartmentDAO(hibernate.getSessionFactory());
	    final SubjectStudentDAO subjectStudentDAO = new SubjectStudentDAO(hibernate.getSessionFactory());

        final StudentResource studentResource = new StudentResource(studentDAO, courseDAO, subjectDAO, subjectStudentDAO);
	    final SubjectResource subjectResource = new SubjectResource(subjectDAO, studentDAO);
	    final CourseResource courseResource = new CourseResource(courseDAO, subjectDAO);
	    final SecretaryResource secretaryResource = new SecretaryResource(secretaryDAO, courseDAO);
	    final DepartmentResource departmentResource = new DepartmentResource(departmentDAO, secretaryDAO);
        
        environment.jersey().register(studentResource);
	    environment.jersey().register(subjectResource);
	    environment.jersey().register(courseResource);
	    environment.jersey().register(secretaryResource);
	    environment.jersey().register(departmentResource);
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(Student.class, Subject.class, Course.class, Secretary.class, Department.class, SubjectStudent.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}

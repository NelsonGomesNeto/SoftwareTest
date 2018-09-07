package br.ufal.ic.academico;

import br.ufal.ic.academico.course.Course;
import br.ufal.ic.academico.course.CourseDAO;
import br.ufal.ic.academico.course.CourseResource;
import br.ufal.ic.academico.person.PersonResource;
import br.ufal.ic.academico.person.Person;
import br.ufal.ic.academico.person.PersonDAO;
import br.ufal.ic.academico.subject.Subject;
import br.ufal.ic.academico.subject.SubjectDAO;
import br.ufal.ic.academico.subject.SubjectResource;
import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
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

        final PersonDAO personDAO = new PersonDAO(hibernate.getSessionFactory());
        final SubjectDAO subjectDAO = new SubjectDAO(hibernate.getSessionFactory());
	    final CourseDAO courseDAO = new CourseDAO(hibernate.getSessionFactory());

        final PersonResource personResource = new PersonResource(personDAO);
	    final SubjectResource subjectResource = new SubjectResource(subjectDAO);
	    final CourseResource courseResource = new CourseResource(courseDAO, subjectDAO);
        
        environment.jersey().register(personResource);
	    environment.jersey().register(subjectResource);
	    environment.jersey().register(courseResource);
    }

    private final HibernateBundle<ConfigApp> hibernate
            = new HibernateBundle<ConfigApp>(Person.class, Subject.class, Course.class) {
        
        @Override
        public DataSourceFactory getDataSourceFactory(ConfigApp configuration) {
            return configuration.getDatabase();
        }
    };
}

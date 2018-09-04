package br.ufal.ic.academico.exemplos;

import io.dropwizard.hibernate.AbstractDAO;
import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

/**
 *
 * @author Willy
 */
public class PersonDAO extends AbstractDAO<Person> {
    
    public PersonDAO(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Person get(Serializable id) throws HibernateException {
        return super.get(id);
    }
    
    @Override
    public Person persist(Person entity) throws HibernateException {
        return super.persist(entity);
    }
}

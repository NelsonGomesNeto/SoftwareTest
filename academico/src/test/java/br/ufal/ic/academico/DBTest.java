package br.ufal.ic.academico;

import br.ufal.ic.academico.person.Person;
import br.ufal.ic.academico.person.PersonDAO;
import io.dropwizard.testing.junit5.DAOTestExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;
import lombok.SneakyThrows;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 *
 * @author Willy
 */
@ExtendWith(DropwizardExtensionsSupport.class)
public class DBTest {
    
    public DAOTestExtension dbTesting = DAOTestExtension.newBuilder()
            .addEntityClass(Person.class).build();
    
    private PersonDAO dao;

    @BeforeEach
    @SneakyThrows
    public void setUp() {
        System.out.println("setUp");
        dao = new PersonDAO(dbTesting.getSessionFactory());
    }
    
    @Test
    public void testCRUD() {

        System.out.println("testCRUD");
        
        Person c1 = new Person("1", "2");
        
        Person saved = dbTesting.inTransaction(() -> dao.persist(c1));
        
        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertEquals(c1.getFirstName(), saved.getFirstName(), "First name isn't being saved on database");
        assertEquals(c1.getLastName(), saved.getLastName(), "Last name isn't being saved on database");
    }
}

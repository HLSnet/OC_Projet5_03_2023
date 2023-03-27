package com.safetynet.safetynetalerts.persontest;

import com.safetynet.safetynetalerts.iotest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.PERSON;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonTest {
    private List<Person> persons;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.initialisation();
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
    }


    @Autowired
    private PersonDao personDaoImpl;


//*********************************************************************************************************
//  Tests unitaires de la méthode 'findBy' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testFindAnExistingPerson() {
        // Arrange, Act
        Person person = personDaoImpl.findByName("Jamie", "Peters");

        // Assert
        assertNotNull(person);
        assertEquals(person.getFirstName(), "Jamie");
        assertEquals(person.getLastName(), "Peters");
        assertEquals(person.getAddress(), "908 73rd St");
        assertEquals(person.getCity(), "Culver");
        assertEquals(person.getZip(), "97451");
        assertEquals(person.getPhone(), "841-874-7462");
        assertEquals(person.getEmail(), "jpeter@email.com");

        assertTrue(this.persons.contains(person));
    }

    @Test
    void testFindANonExistingPerson() {
        // Arrange, Act
        Person person = personDaoImpl.findByName("Averell", "Dalton");

        // Assert
        assertNull(person);

        assertFalse(this.persons.contains(person));
    }



//*********************************************************************************************************
//  Tests unitaires de la méthode 'save' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testSaveNewPerson() {
        // Arrange
        Person personToAdd = new Person();

        personToAdd.setFirstName("Averell");
        personToAdd.setLastName("Dalton");
        personToAdd.setAddress("19 Saloon St");
        personToAdd.setCity("Daisy town");
        personToAdd.setZip("00000");
        personToAdd.setPhone("111-222-3333");
        personToAdd.setEmail("a.dalton@jail.com");

        // Act
        Boolean result = personDaoImpl.save(personToAdd);

        // Assert
        assertTrue(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(this.persons.contains(personToAdd));
    }


    @Test
    void testSaveAnExistingPerson() {
        // Arrange
        Person personToAdd = new Person();

        personToAdd.setFirstName("Jamie");
        personToAdd.setLastName("Peters");
		personToAdd.setAddress("908 73rd St");
		personToAdd.setCity("Culver");
		personToAdd.setZip("97451");
		personToAdd.setPhone("841-874-7462");
		personToAdd.setEmail("jpeter@email.com");

        // Act
        Boolean result = personDaoImpl.save(personToAdd);

        // Assert
        assertFalse(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(this.persons.contains(personToAdd));
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'update' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testUpdateAnExistingPerson() {
        // Arrange
        Person personToModify = new Person();

        personToModify.setFirstName("Jamie");
        personToModify.setLastName("Peters");
        personToModify.setAddress("1 here St");
        personToModify.setCity("Nowhere");
        personToModify.setZip("00000");
        personToModify.setPhone("012-345-6789");
        personToModify.setEmail("jpeter.new@email.com");

        // Act
        Boolean result = personDaoImpl.update(personToModify);

        // Assert
        assertTrue(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(this.persons.contains(personToModify));
    }


    @Test
    void testUpdateANonExistingPerson() {
        // Arrange
        Person personToModify = new Person();

        personToModify.setFirstName("Averell");
        personToModify.setLastName("Dalton");
        personToModify.setAddress("19 Saloon St");
        personToModify.setCity("Daisy town");
        personToModify.setZip("00000");
        personToModify.setPhone("111-222-3333");
        personToModify.setEmail("a.dalton@jail.com");

        // Act
        Boolean result = personDaoImpl.update(personToModify);

        // Assert
        assertFalse(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(this.persons.contains(personToModify));
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'delete' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testDeleteAnExistingPerson() {
        // Arrange
        Person personToDelete = new Person();

        personToDelete.setFirstName("Jamie");
        personToDelete.setLastName("Peters");
        personToDelete.setAddress("908 73rd St");
        personToDelete.setCity("Culver");
        personToDelete.setZip("97451");
        personToDelete.setPhone("841-874-7462");
        personToDelete.setEmail("jpeter@email.com");

        // Act
        Boolean result = personDaoImpl.delete(personToDelete.getFirstName(), personToDelete.getLastName());

        // Assert
        assertTrue(result);
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToDelete));
    }

    @Test
    void testDeleteANonExistingPerson() {
        // Arrange
        Person personToDelete = new Person();

        personToDelete.setFirstName("Averell");
        personToDelete.setLastName("Dalton");

        // Act
        Boolean result = personDaoImpl.delete(personToDelete.getFirstName(), personToDelete.getLastName());

        // Assert
        assertFalse(result);
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToDelete));
    }
}

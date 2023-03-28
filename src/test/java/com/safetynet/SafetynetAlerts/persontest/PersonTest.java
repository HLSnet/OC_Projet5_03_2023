package com.safetynet.safetynetalerts.persontest;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
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
        // ARRANGE, ACT
        Person person = personDaoImpl.findByName("Jamie", "Peters");

        // ASSERT
        assertNotNull(person);
        assertTrue(this.persons.contains(person));
    }

    @Test
    void testFindANonExistingPerson() {
        // ARRANGE, ACT
        Person person = personDaoImpl.findByName("Averell", "Dalton");

        // ASSERT
        assertNull(person);
        assertFalse(this.persons.contains(person));
    }



//*********************************************************************************************************
//  Tests unitaires de la méthode 'save' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testSaveNewPerson() {
        // ARRANGE
        Person personToAdd = new Person();
        personToAdd.setFirstName("Averell");
        personToAdd.setLastName("Dalton");
        personToAdd.setAddress("19 Saloon St");
        personToAdd.setCity("Daisy town");
        personToAdd.setZip("00000");
        personToAdd.setPhone("111-222-3333");
        personToAdd.setEmail("a.dalton@jail.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.save(personToAdd);

        // ASSERT
        assertTrue(result);
        // On vérifie que l'ajout a été fait
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(this.persons.contains(personToAdd));
        // On vérifie qu'il n'y a eu qu'un ajout (pas d'ajout multiple)
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore +1 );
    }


    @Test
    void testSaveAnExistingPerson() {
        // ARRANGE
        Person personToAdd = new Person();
        personToAdd.setFirstName("Jamie");
        personToAdd.setLastName("Peters");
		personToAdd.setAddress("908 73rd St");
		personToAdd.setCity("Culver");
		personToAdd.setZip("97451");
		personToAdd.setPhone("841-874-7462");
		personToAdd.setEmail("jpeter@email.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.save(personToAdd);

        // ASSERT
        assertFalse(result);
        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'update' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testUpdateAnExistingPerson() {
        // ARRANGE
        Person personToModify = new Person();
        personToModify.setFirstName("Jamie");
        personToModify.setLastName("Peters");
        personToModify.setAddress("1 here St");
        personToModify.setCity("Nowhere");
        personToModify.setZip("00000");
        personToModify.setPhone("012-345-6789");
        personToModify.setEmail("jpeter.new@email.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.update(personToModify);

        // ASSERT
        assertTrue(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(this.persons.contains(personToModify));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }


    @Test
    void testUpdateANonExistingPerson() {
        // ARRANGE
        Person personToModify = new Person();
        personToModify.setFirstName("Averell");
        personToModify.setLastName("Dalton");
        personToModify.setAddress("19 Saloon St");
        personToModify.setCity("Daisy town");
        personToModify.setZip("00000");
        personToModify.setPhone("111-222-3333");
        personToModify.setEmail("a.dalton@jail.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.update(personToModify);

        // ASSERT
        assertFalse(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(this.persons.contains(personToModify));
        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'delete' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testDeleteAnExistingPerson() {
        // ARRANGE
        Person personToDelete = new Person();
        personToDelete.setFirstName("Jamie");
        personToDelete.setLastName("Peters");
        personToDelete.setAddress("908 73rd St");
        personToDelete.setCity("Culver");
        personToDelete.setZip("97451");
        personToDelete.setPhone("841-874-7462");
        personToDelete.setEmail("jpeter@email.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.delete(personToDelete.getFirstName(), personToDelete.getLastName());

        // ASSERT
        assertTrue(result);
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToDelete));
        // On vérifie qu'il n'y a eu uniquement qu'une suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore -1);
    }

    @Test
    void testDeleteANonExistingPerson() {
        // ARRANGE
        Person personToDelete = new Person();
        personToDelete.setFirstName("Averell");
        personToDelete.setLastName("Dalton");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.delete(personToDelete.getFirstName(), personToDelete.getLastName());

        // ASSERT
        assertFalse(result);
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToDelete));
        // On vérifie qu'il n'y a pas eu de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }
}

package com.safetynet.safetynetalerts.persontest;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonTest {
    private List<Person> persons;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
        JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));
    }


    @Autowired
    private PersonDao personDaoImpl;


//*********************************************************************************************************
//  Tests unitaires de la méthode 'findBy' de la classe  PersonDaoImpl
//*********************************************************************************************************
    @Test
    void testFindAnExistingPerson() {
        // ARRANGE, ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        Person person = personDaoImpl.findByName("Jamie", "Peters");

        // ASSERT
        assertNotNull(person);
        assertTrue(persons.contains(person));
    }

    @Test
    void testFindANonExistingPerson() {
        // ARRANGE, ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        Person person = personDaoImpl.findByName("Averell", "Dalton");

        // ASSERT
        assertNull(person);
        assertFalse(persons.contains(person));
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
        assertTrue(persons.contains(personToAdd));
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
        Person personToUpdate = new Person();
        personToUpdate.setFirstName("Jamie");
        personToUpdate.setLastName("Peters");
        personToUpdate.setAddress("1 here St");
        personToUpdate.setCity("Nowhere");
        personToUpdate.setZip("00000");
        personToUpdate.setPhone("012-345-6789");
        personToUpdate.setEmail("jpeter.new@email.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.update(personToUpdate);

        // ASSERT
        assertTrue(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(persons.contains(personToUpdate));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }


    @Test
    void testUpdateANonExistingPerson() {
        // ARRANGE
        Person personToUpdate = new Person();
        personToUpdate.setFirstName("Averell");
        personToUpdate.setLastName("Dalton");
        personToUpdate.setAddress("19 Saloon St");
        personToUpdate.setCity("Daisy town");
        personToUpdate.setZip("00000");
        personToUpdate.setPhone("111-222-3333");
        personToUpdate.setEmail("a.dalton@jail.com");

        // ACT
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.update(personToUpdate);

        // ASSERT
        assertFalse(result);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToUpdate));
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
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
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
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToDelete));
        // On vérifie qu'il n'y a pas eu de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }
}

package com.safetynet.safetynetalerts.daotest;

import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JsonFileIO;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PersonDaoTest {
    private static Logger logger = LoggerFactory.getLogger(PersonDaoTest.class);
    private List<Person> persons;

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des methodes DAO de la classe personDaoImpl");
    }

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JsonFileIO(JSONFILE_TEST_PATHNAME);
    }


    @Autowired
    private PersonDao personDaoImpl;


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'findBy' de la classe  PersonDaoImpl
    //*********************************************************************************************************
    @Test
    void testFindAnExistingPerson() {
        // ARRANGE, ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        Person person = personDaoImpl.findByName("Jamie", "Peters");

        // ASSERT
        assertNotNull(person);
        assertTrue(persons.contains(person));
    }

    @Test
    void testFindANonExistingPerson() {
        // ARRANGE, ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
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
        Person personToAdd = new Person(
                "Averell",
                "Dalton",
                "19 Saloon St",
                "Daisy town",
                "00000",
                "111-222-3333",
                "a.dalton@jail.com");

        // ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.save(personToAdd);

        // ASSERT
        assertTrue(result);
        // On vérifie que l'ajout a été fait
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(persons.contains(personToAdd));
        // On vérifie qu'il n'y a eu qu'un ajout (pas d'ajout multiple)
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore +1 );
    }


    @Test
    void testSaveAnExistingPerson() {
        // ARRANGE
        Person personToAdd = new Person(
                "Jamie",
                "Peters",
                "908 73rd St",
                "Culver",
                "97451",
                "841-874-7462",
                "jpeter@email.com");


        // ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
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
        Person personToUpdate = new Person(
                "Jamie",
                "Peters",
                "1 here St",
                "Nowhere",
                "00000",
                "012-345-6789",
                "jpeter.new@email.com");


        // ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.update(personToUpdate);

        // ASSERT
        assertTrue(result);
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertTrue(persons.contains(personToUpdate));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }


    @Test
    void testUpdateANonExistingPerson() {
        // ARRANGE
        Person personToUpdate = new Person(
                "Averell",
                "Dalton",
                "19 Saloon St",
                "Daisy town",
                "00000",
                "111-222-3333",
                "a.dalton@jail.com");

        // ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.update(personToUpdate);

        // ASSERT
        assertFalse(result);
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
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
        Person personToDelete = new Person(
                "Jamie",
                "Peters",
                "908 73rd St",
                "Culver",
                "97451",
                "841-874-7462",
                "jpeter@email.com");

        // ACT
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.delete(personToDelete.getFirstName(), personToDelete.getLastName());

        // ASSERT
        assertTrue(result);
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
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
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        int nbPersonsBefore = persons.size();
        Boolean result = personDaoImpl.delete(personToDelete.getFirstName(), personToDelete.getLastName());

        // ASSERT
        assertFalse(result);
        persons = JsonFileIO.readFromJsonFileToList(PERSON, Person.class);
        assertFalse(persons.contains(personToDelete));
        // On vérifie qu'il n'y a pas eu de suppression
        int nbPersonsAfter = persons.size();
        assertEquals(nbPersonsAfter, nbPersonsBefore);
    }
}

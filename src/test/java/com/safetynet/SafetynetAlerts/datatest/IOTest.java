package com.safetynet.safetynetalerts.datatest;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.PERSON;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IOTest {
    @BeforeEach
    void setUpData(){
        SetupJsonFile.initialisation();
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
    }

    @Test
    void testReadFromJsonFileToList(){
        // Arrange, Act
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);

        //Assert
        assertEquals(persons.get(2).getLastName(),"Peters");
    }

    @Test
    void testWriteListToJsonFile(){
        // Arrange
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        persons.remove(1);

        //Act
        JasonFileIO.writeListToJsonFile(PERSON, persons);
        persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);

        //Assert
        assertEquals(persons.get(2).getLastName(),"Stelzer");
    }

}

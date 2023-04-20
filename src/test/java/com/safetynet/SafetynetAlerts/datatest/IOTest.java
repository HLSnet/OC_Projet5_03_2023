package com.safetynet.safetynetalerts.datatest;

import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IOTest {

    private static Logger logger = LoggerFactory.getLogger(IOTest.class);

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des methodes lecture et ecriture dans le fichier JSON de la classe IOTest");
    }


    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
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

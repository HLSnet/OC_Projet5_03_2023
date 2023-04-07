package com.safetynet.safetynetalerts.datatest;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IOTest {
    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);

        // Version 1
//        FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
//        JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));
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

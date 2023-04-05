package com.safetynet.safetynetalerts.serviceTest;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.ChildDto;
import com.safetynet.safetynetalerts.dto.PersonsGivenStationDto;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class ServiceTest {
    @Autowired
    private AlertService alertService;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
        JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPersonsRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void testFindPersonsWithAnExistingStation() {
        // ARRANGE
        int stationNumber = 4;

        Person person = new Person();
        person.setFirstName("Ron");
        person.setLastName("Peters");
        person.setAddress("112 Steppes Pl");
        person.setCity("Culver");
        person.setZip("97451");
        person.setPhone("841-874-8888");
        person.setEmail("jpeter@email.com");

        // ACT
        PersonsGivenStationDto personsGivenStationDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assert(personsGivenStationDto.getPersons().contains(person));

        assertEquals(personsGivenStationDto.getNbAdult(), 4);
        assertEquals(personsGivenStationDto.getNbChild(), 0);

        assertEquals(personsGivenStationDto.getPersons().size(), 4);
    }

    @Test
    void testFindPersonsWithNoExistingStation() {
        // ARRANGE
        int stationNumber = 30;

        // ACT
        PersonsGivenStationDto personsGivenStationDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertNull(personsGivenStationDto);
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'getChildsdRelatedToAnAddress' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void testFindChildsWithExistingAddress() {
        // ARRANGE , ACT
        List<ChildDto> childsDto = alertService.getChildsdRelatedToAnAddress( "1509 Culver St");

        // ASSERT
        assertEquals(childsDto.size(), 2);
        assertEquals(childsDto.get(0).getFirstName(), "Tenley");
        assertEquals(childsDto.get(0).getLastName(), "Boyd");
        assertEquals(childsDto.get(0).getAge(), 11);

        assertEquals(childsDto.get(0).getHouseholdMembers().size(), 4);
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getFirstName(), "Jacob");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getLastName(), "Boyd");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getAddress(), "1509 Culver St");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getCity(),"Culver");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getZip(),"97451");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getPhone(),"841-874-6513");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getEmail(),"drk@email.com");
}

    @Test
    void testFindChildsWithNoExistingAddress() {
        // ARRANGE,  ACT
        List<ChildDto> childsDto = alertService.getChildsdRelatedToAnAddress("Nowhere St");

        // ASSERT
        assertNull(childsDto);
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPhoneRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void  testFindPhoneNumbersWithExistingStation() {
        // ARRANGE, ACT
        List<String> phoneNumbers = alertService.getPhoneNumbersRelatedToAStation(2);

        // ASSERT
        assertEquals(phoneNumbers.size(), 5);
        assertEquals(phoneNumbers.get(1), "841-874-7878");
}

    @Test
    void testFindPhoneNumbersWithNoExistingAddress() {
        // ARRANGE,  ACT
        List<String> phoneNumbers = alertService.getPhoneNumbersRelatedToAStation(20);
        // ASSERT
        assertNull(phoneNumbers);
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPersonsRelatedToAnAddress' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void  testFindPersonsWithExistingAddress() {
    // ARRANGE


    // ACT



    // ASSERT

}



//*********************************************************************************************************
//  Tests unitaires de la méthode 'getHouseRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test4() {
    // ARRANGE


    // ACT



    // ASSERT

}



//*********************************************************************************************************
//  Tests unitaires de la méthode 'getInfoPerson' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test5() {
    // ARRANGE


    // ACT



    // ASSERT

}




//*********************************************************************************************************
//  Tests unitaires de la méthode 'getMailRelatedToACity' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void  testFindMailsWithExistingCity() {
        // ARRANGE, ACT
        List<String> mails = alertService.getMailsRelatedToACity("Culver");

        // ASSERT
        assertEquals(mails.size(), 23);
        assertEquals(mails.get(1), "drk@email.com");
    }

    @Test
    void testFindMailsWithNoExistingAddress() {
        // ARRANGE,  ACT
        List<String> mails = alertService.getMailsRelatedToACity("Paris");
        // ASSERT
        assertNull(mails);
    }



}

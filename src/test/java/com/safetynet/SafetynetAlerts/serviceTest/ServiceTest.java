package com.safetynet.safetynetalerts.serviceTest;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.repository.FirestationDaoImpl;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
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
        FirestationDaoImpl.checkIntegrityJsonFileFirestationAndSort();
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPersonsRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void testFindPersonsWithAnExistingStation() {
        // ARRANGE
        int stationNumber = 2;

        FirestationPersonDto firestationPersonDto = new FirestationPersonDto();

        firestationPersonDto.setFirstName("Warren");
        firestationPersonDto.setLastName("Zemicks");
        firestationPersonDto.setAddress("892 Downing Ct");
        firestationPersonDto.setPhone("841-874-7512");

        // ACT
        FirestationDto firestationDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertTrue(firestationDto.getPersons().contains(firestationPersonDto));

        assertEquals(firestationDto.getNbAdult(), 4);
        assertEquals(firestationDto.getNbChild(), 1);

        assertEquals(firestationDto.getPersons().size(), 5);
    }

    @Test
    void testFindPersonsWithNoExistingStation() {
        // ARRANGE
        int stationNumber = 30;

        // ACT
        FirestationDto firestationDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertNull(firestationDto);
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'getChildsdRelatedToAnAddress' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void testFindChildsWithExistingAddress() {
        // ARRANGE , ACT
        List<ChildAlertDto> childsDto = alertService.getChildsdRelatedToAnAddress( "1509 Culver St");

        // ASSERT
        assertEquals(childsDto.size(), 2);
        assertEquals(childsDto.get(0).getFirstName(), "Tenley");
        assertEquals(childsDto.get(0).getLastName(), "Boyd");
        assertEquals(childsDto.get(0).getAge(), 11);

        assertEquals(childsDto.get(0).getHouseholdMembers().size(), 4);
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getFirstName(), "Jacob");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getLastName(), "Boyd");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getPhone(),"841-874-6513");
        assertEquals(childsDto.get(0).getHouseholdMembers().get(1).getEmail(),"drk@email.com");
}

    @Test
    void testFindChildsWithNoExistingAddress() {
        // ARRANGE,  ACT
        List<ChildAlertDto> childsDto = alertService.getChildsdRelatedToAnAddress("Nowhere St");

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
        assertEquals(phoneNumbers.get(1), "841-874-7458");
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
    // ARRANGE, ACT
    FireDto  fireDto = alertService.getPersonsRelatedToAnAddress("1509 Culver St");

    FirePersonDto firePersonDto = new FirePersonDto();
    firePersonDto.setLastName("Boyd");
    firePersonDto.setPhone("841-874-6544");
    firePersonDto.setEmail("jaboyd@email.com");
    firePersonDto.setAge(37);

    ArrayList<String> medications = new ArrayList<>();
    medications.add("tetracyclaz:650mg");
    firePersonDto.setMedications(medications);

    ArrayList<String> allergies = new ArrayList<>();
    allergies.add("xilliathal");
    firePersonDto.setAllergies(allergies);

    // ASSERT
    assertEquals(fireDto.getStation(), 3);
    assertTrue(fireDto.getHouseholdMembers().contains(firePersonDto));
}

    @Test
    void testFindPersonsWithNoExistingAddress() {
        // ARRANGE
        int stationNumber = 30;

        // ACT
        FirestationDto fireDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertNull(fireDto);
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
void  testGetInfoPersonOfAnExistingPerson() {
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
        List<String> communityEmail = alertService.getMailsRelatedToACity("Culver");

        // ASSERT
        assertEquals(communityEmail.size(), 23);
        assertEquals(communityEmail.get(1), "drk@email.com");

    }

    @Test
    void testFindMailsWithNoExistingAddress() {
        // ARRANGE,  ACT
        List<String> mails = alertService.getMailsRelatedToACity("Paris");

        // ASSERT
        assertNull(mails);
    }



}

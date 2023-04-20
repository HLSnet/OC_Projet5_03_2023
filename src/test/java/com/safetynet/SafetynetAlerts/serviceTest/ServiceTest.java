package com.safetynet.safetynetalerts.servicetest;

import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.repository.FirestationDaoImpl;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;



@SpringBootTest
public class ServiceTest {
    @Autowired
    private AlertService alertService;

    private static Logger logger = LoggerFactory.getLogger(ServiceTest.class);

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des methodes des controllers REST de la classe AlertService");
    }


    @BeforeEach
    void setUpData(){
        // Le fichier de test utilisé est une copie du fichier réel
        SetupJsonFile.reloadTestFile(JSONFILE_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        FirestationDaoImpl.checkIntegrityJsonFileFirestationAndSort();
    }


    //*********************************************************************************************************
    // URL1 : http://localhost:8080/firestation?stationNumber=<station_number>
    //
    // Tests unitaires de la méthode 'getPersonsRelatedToAStation' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public void FindPersonsWithAnExistingStation() {
        // ARRANGE
        int stationNumber = 2;

        FirestationPersonDto firestationPersonDto = new FirestationPersonDto(
            "Warren",
            "Zemicks",
            "892 Downing Ct",
            "841-874-7512");

        // ACT
        FirestationDto firestationDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertTrue(firestationDto.getPersons().contains(firestationPersonDto));

        assertEquals(firestationDto.getNbAdult(), 4);
        assertEquals(firestationDto.getNbChild(), 1);

        assertEquals(firestationDto.getPersons().size(), 5);
    }

    @Test
    public void FindPersonsWithNoExistingStation() {
        // ARRANGE
        int stationNumber = 20;

        // ACT
        FirestationDto firestationDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertNull(firestationDto);
    }

    //*********************************************************************************************************
    // URL2 :  http://localhost:8080/childAlert?address=<address>
    //
    // Tests unitaires de la méthode 'getChildsdRelatedToAnAddress' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public void FindChildsWithExistingAddress() {
        // ARRANGE , ACT
        String address =  "1509 Culver St";
        List<ChildAlertDto> childsDto = alertService.getChildsdRelatedToAnAddress( address);

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
    public void FindChildsWithNoExistingAddress() {
        // ARRANGE,  ACT
        String address = "Nowhere St";
        List<ChildAlertDto> childsDto = alertService.getChildsdRelatedToAnAddress(address);

        // ASSERT
        assertNull(childsDto);
    }


    //*********************************************************************************************************
    // URL3 : http://localhost:8080/phoneAlert?firestation=<firestation_number>
    //
    // Tests unitaires de la méthode 'getPhoneNumbersRelatedToAStation' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public  void  FindPhoneNumbersWithExistingStation() {
        // ARRANGE, ACT
        List<String> phoneNumbers = alertService.getPhoneNumbersRelatedToAStation(2);

        // ASSERT
        assertEquals(phoneNumbers.size(), 5);
        assertEquals(phoneNumbers.get(0), "841-874-6513");
        assertEquals(phoneNumbers.get(1), "841-874-7878");
        assertEquals(phoneNumbers.get(2), "841-874-7512");
        assertEquals(phoneNumbers.get(3), "841-874-7512");
        assertEquals(phoneNumbers.get(4), "841-874-7458");
}

    @Test
    public void FindPhoneNumbersWithNoExistingAddress() {
        // ARRANGE,  ACT
        List<String> phoneNumbers = alertService.getPhoneNumbersRelatedToAStation(20);
        // ASSERT
        assertNull(phoneNumbers);
    }



    //*********************************************************************************************************
    // URL4 : http://localhost:8080/fire?address=<address>
    //
    // Tests unitaires de la méthode 'getPersonsRelatedToAnAddress' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public void  FindPersonsWithExistingAddress() {
        // ARRANGE, ACT
        FireDto  fireDto = alertService.getPersonsRelatedToAnAddress("1509 Culver St");

        FirePersonDto firePersonDto = new FirePersonDto(
                "Felicia",
                "Boyd",
                "841-874-6544",
                37,
                "jaboyd@email.com",
                new ArrayList<>(Arrays.asList("tetracyclaz:650mg")),
                new ArrayList<>(Arrays.asList("xilliathal")));


        // ASSERT
        assertEquals(fireDto.getStation(), 3);
        assertTrue(fireDto.getHouseholdMembers().contains(firePersonDto));
    }

    @Test
    public void FindPersonsWithNoExistingAddress() {
        // ASSERT
        assertNull(alertService.getPersonsRelatedToAnAddress("Nowhere"));
    }



    //*********************************************************************************************************
    // URL5 : http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    //
    // Tests unitaires de la méthode 'getHousesRelatedToAListOfStations' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public void FindPersonsWithExistingStations() {
        // ARRANGE
        List<Integer> stations = Arrays.asList(3,4);

        FloodPersonDto floodPersonDto = new FloodPersonDto(
                "Felicia",
                "Boyd",
                "841-874-6544",
                37,
                new ArrayList<>(Arrays.asList("tetracyclaz:650mg")),
                new ArrayList<>(Arrays.asList("xilliathal")));

        // ACT
        List<FloodDto> floodDtos = alertService.getHousesRelatedToAListOfStations(stations);

        // ASSERT
        assertEquals(floodDtos.size(), 5);
        assertEquals(floodDtos.get(0).getAddress(), "112 Steppes Pl");
        assertEquals(floodDtos.get(0).getHouseholdMembers().size(), 3);
        assertEquals(floodDtos.get(0).getHouseholdMembers().get(1).getLastName(), "Peters");
    }


    @Test
    public void FindPersonsWithNoExistingStations() {
        // ARRANGE
        List<Integer> stations = Arrays.asList(20,30);

        // ACT
        List<FloodDto> floodDto = alertService.getHousesRelatedToAListOfStations(stations);

        // ASSERT
        assertNull(floodDto);
}


    //*********************************************************************************************************
    // URL6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    //
    // Tests unitaires de la méthode 'getInfoPerson' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public void  GetInfoPersonOfAnExistingPerson() {
        // ARRANGE, ACT
        String firstName = "Sophia";
        String lastName = "Zemicks";

        // ACT
        List<InfoPersonDto> infoPersonsDto = alertService.getInfoPerson( firstName, lastName);

        InfoPersonDto infoPersonDto = new InfoPersonDto(
             "Sophia",
            "Zemicks",
            "892 Downing Ct",
            35,
            "soph@email.com",
            new ArrayList<>(Arrays.asList(
                    "aznol:60mg",
                    "hydrapermazol:900mg",
                    "pharmacol:5000mg",
                    "terazine:500mg")),
            new ArrayList<>(Arrays.asList(
                    "peanut",
                    "shellfish",
                    "aznol")));

        // ASSERT
        assertTrue(infoPersonsDto.contains(infoPersonDto));
        assertEquals(infoPersonsDto.size(), 3);
    }
    @Test
    public void GetInfoPersonOfNoExistingPerson() {
        // ARRANGE
        String firstname = "Averell";
        String lastname = "Dalton";

        // ACT
        List<InfoPersonDto> infoPersonsDto = alertService.getInfoPerson( firstname, lastname);

        // ASSERT
        assertNull(infoPersonsDto);
    }


    //*********************************************************************************************************
    // URL7 :  http://localhost:8080/communityEmail?city=<city>
    //
    // Tests unitaires de la méthode 'getMailRelatedToACity' de la classe  AlertServiceImpl
    //*********************************************************************************************************
    @Test
    public void  FindMailsWithExistingCity() {
        // ARRANGE
        String city = "Culver";

        // ACT
        List<String> communityEmail = alertService.getMailsRelatedToACity(city);

        // ASSERT
        assertThat(communityEmail).containsExactlyInAnyOrder(
                "jaboyd@email.com",
                "drk@email.com",
                "tenz@email.com",
                "tcoop@ymail.com",
                "lily@email.com",
                "soph@email.com",
                "ward@email.com",
                "zarc@email.com",
                "reg@email.com",
                "jpeter@email.com",
                "aly@imail.com",
                "bstel@email.com",
                "ssanw@email.com",
                "clivfd@ymail.com",
                "gramps@email.com" );
    }

    @Test
    public void FindMailsWithNoExistingCity() {
        // ARRANGE
        String city = "Paris";

        // ACT
        List<String> mails = alertService.getMailsRelatedToACity(city);

        // ASSERT
        assertNull(mails);
    }
}

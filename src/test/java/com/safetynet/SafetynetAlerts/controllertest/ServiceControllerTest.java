package com.safetynet.safetynetalerts.controllertest;


import com.safetynet.safetynetalerts.controller.ServiceController;
import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.repository.JsonFileIO;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ServiceController.class)
public class ServiceControllerTest {

    private static Logger logger = LoggerFactory.getLogger(ServiceControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    @BeforeEach
    void setUpData() {
        SetupJsonFile.reloadTestFile(JSONFILE_BAK_PATHNAME, JSONFILE_TEST_PATHNAME);
        new JsonFileIO(JSONFILE_TEST_PATHNAME);
    }

    //***************************************************************************************************
    // URL1 : http://localhost:8080/firestation?stationNumber=<station_number>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************

    @Test
    public void AlertService_shouldGetPersonsRelatedToAStation_ResultNotNull() throws Exception {
        int stationNumber = 2;
        FirestationDto firestationDto = new FirestationDto();

        when(alertService.getPersonsRelatedToAStation(stationNumber)).thenReturn(firestationDto);

        logger.info("TU -> AlertService_shouldGetPersonsRelatedToAStation_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getPersonsRelatedToAStation");
        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPersonsRelatedToAStation(stationNumber);

    }

    @Test
    public void AlertService_shouldGetPersonsRelatedToAStation_ResultNull() throws Exception {
        int stationNumber = 0;
        FirestationDto firestationDto = null;

        when(alertService.getPersonsRelatedToAStation(stationNumber)).thenReturn(firestationDto);

        logger.info("TU -> AlertService_shouldGetPersonsRelatedToAStation_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getPersonsRelatedToAStation");
        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))

                .andExpect(status().isNoContent());

        verify(alertService, times(1)).getPersonsRelatedToAStation(stationNumber);
    }


    //***************************************************************************************************
    // URL2 :  http://localhost:8080/childAlert?address=<address>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************

    @Test
    public  void AlertService_shouldGetChildsdRelatedToAnAddress_ResultNotNull()  throws Exception {
        String address = "1509 Culver St";
        List<ChildAlertDto> childAlertDtos = new ArrayList<>();

        when(alertService.getChildsdRelatedToAnAddress(address)).thenReturn(childAlertDtos);

        logger.info("TU -> AlertService_shouldGetChildsdRelatedToAnAddress_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getChildsdRelatedToAnAddress");
        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getChildsdRelatedToAnAddress(address);
    }

    @Test
    public  void AlertService_shouldGetChildsdRelatedToAnAddress_ResultNull()  throws Exception {
        String address = "Nowhere";

        when(alertService.getChildsdRelatedToAnAddress(address)).thenReturn(null);

        logger.info("TU -> AlertService_shouldGetChildsdRelatedToAnAddress_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getChildsdRelatedToAnAddress");
        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService, times(1)).getChildsdRelatedToAnAddress(address);
    }

    //***************************************************************************************************
    // URL3 : http://localhost:8080/phoneAlert?firestation=<firestation_number>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************
    @Test
    public void AlertService_shouldGetPhoneNumbersRelatedToAStation_ResultNotNull()  throws Exception {
        int stationNumber = 2;
        List<String> phoneNumbers = new ArrayList<>();

        when(alertService.getPhoneNumbersRelatedToAStation(stationNumber)).thenReturn(phoneNumbers);

        logger.info("TU -> AlertService_shouldGetPhoneNumbersRelatedToAStation_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getPhoneNumbersRelatedToAStation");
        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPhoneNumbersRelatedToAStation(stationNumber);
    }

    @Test
    public void AlertService_shouldGetPhoneNumbersRelatedToAStation_ResultNull()  throws Exception {
        int stationNumber = 0;

        when(alertService.getPhoneNumbersRelatedToAStation(stationNumber)).thenReturn(null);

        logger.info("TU -> AlertService_shouldGetPhoneNumbersRelatedToAStation_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getPhoneNumbersRelatedToAStation");
        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService, times(1)).getPhoneNumbersRelatedToAStation(stationNumber);
    }


    //***************************************************************************************************
    // URL4 : http://localhost:8080/fire?address=<address>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************
    @Test
    public void AlertService_shouldGetPersonsRelatedToAnAddress_ResultNotNull() throws Exception {
        String address = "1509 Culver St";
        FireDto  fireDto = new FireDto();
        fireDto.setHouseholdMembers(new ArrayList<>());

        when(alertService.getPersonsRelatedToAnAddress(address)).thenReturn(fireDto);

        logger.info("TU -> AlertService_shouldGetPersonsRelatedToAnAddress_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getPersonsRelatedToAnAddress");
        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getPersonsRelatedToAnAddress(address);
    }


    @Test
    public void AlertService_shouldGetPersonsRelatedToAnAddress_ResultNull() throws Exception {
        String address = "Nowhere";

        when(alertService.getPersonsRelatedToAnAddress(address)).thenReturn(null);

        logger.info("TU -> AlertService_shouldGetPersonsRelatedToAnAddress_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getPersonsRelatedToAnAddress");
        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isNoContent());

        verify(alertService,times(1)).getPersonsRelatedToAnAddress(address);
    }


    //***************************************************************************************************
    // URL5 : http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************
    @Test
    public void AlertService_shouldGetHousesRelatedToAListOfStations_ResultNotNull()  throws Exception {
        List<Integer> stations = Arrays.asList(3,4);
        List<FloodDto> floodDtos = new ArrayList<>();

        when(alertService.getHousesRelatedToAListOfStations(stations)).thenReturn(floodDtos);

        logger.info("TU -> AlertService_shouldGetHousesRelatedToAListOfStations_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getHousesRelatedToAListOfStations");
        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService, times(1)).getHousesRelatedToAListOfStations(stations);
    }

    @Test
    public void AlertService_shouldGetHousesRelatedToAListOfStations_ResultNull()  throws Exception {
        List<Integer> stations = Arrays.asList(30,40);

        when(alertService.getHousesRelatedToAListOfStations(stations)).thenReturn(null);

        logger.info("TU -> AlertService_shouldGetHousesRelatedToAListOfStations_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getHousesRelatedToAListOfStations");
        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getHousesRelatedToAListOfStations(stations);
    }



    //***************************************************************************************************
    // URL6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************
    @Test
    public void AlertService_shouldGetInfoPerson_ResultNotNull() throws Exception {
        List<InfoPersonDto> InfoPersonDtos = new ArrayList<>();

        String firstName = "Sophia";
        String lastName = "Zemicks";

        when(alertService.getInfoPerson(firstName, lastName)).thenReturn(InfoPersonDtos);

        logger.info("TU -> AlertService_shouldGetInfoPerson_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getInfoPerson");
        mockMvc.perform(get("http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getInfoPerson(firstName, lastName);
    }

    @Test
    public void AlertService_shouldGetInfoPerson_ResultNull() throws Exception {
        String firstName = "Averell";
        String lastName = "Dalton";

        when(alertService.getInfoPerson(firstName, lastName)).thenReturn(null);

        logger.info("TU -> AlertService_shouldGetInfoPerson_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getInfoPerson");
        mockMvc.perform(get("http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService,times(1)).getInfoPerson(firstName, lastName);
    }


    //***************************************************************************************************
    // URL7 :  http://localhost:8080/communityEmail?city=<city>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************
    @Test
    public void AlertService_shouldGetMailsRelatedToACity_ResultNotNull() throws Exception {
        List<String> mails = new ArrayList<>();
        String city = "Culver";

        when(alertService.getMailsRelatedToACity(city)).thenReturn(mails);

        logger.info("TU -> AlertService_shouldGetMailsRelatedToACity_ResultNotNull() : Test unitaire de cas nominal de la methode AlertService::getMailsRelatedToACity");
        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getMailsRelatedToACity(city);
    }


    @Test
    public void AlertService_shouldGetMailsRelatedToACity_ResultNull() throws Exception {
        String city = "Paris";

        when(alertService.getMailsRelatedToACity(city)).thenReturn(null);

        logger.info("TU -> AlertService_shouldGetMailsRelatedToACity_ResultNull() : Test unitaire de cas d'erreur de la methode AlertService::getMailsRelatedToACity");
        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService,times(1)).getMailsRelatedToACity(city);
    }
   }

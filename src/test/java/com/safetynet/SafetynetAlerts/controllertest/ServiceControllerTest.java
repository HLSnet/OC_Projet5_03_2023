package com.safetynet.safetynetalerts.controllertest;


import com.safetynet.safetynetalerts.controller.ServiceController;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AlertService alertService;

    @BeforeEach
    void setUpData() {
        SetupJsonFile.reloadTestFile(JSONFILE_BAK_PATHNAME, JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
    }

    //***************************************************************************************************
    // URL1 : http://localhost:8080/firestation?stationNumber=<station_number>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************

    @Test
    public void AlertService_shouldUseGetPersonsRelatedToAStation_ResultNotNull() throws Exception {
        int stationNumber = 2;
        FirestationDto firestationDto = new FirestationDto();

        when(alertService.getPersonsRelatedToAStation(stationNumber)).thenReturn(firestationDto);

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPersonsRelatedToAStation(stationNumber);

    }

    @Test
    public void AlertService_shouldUseGetPersonsRelatedToAStation_ResultNull() throws Exception {
        int stationNumber = 0;
        FirestationDto firestationDto = null;

        when(alertService.getPersonsRelatedToAStation(stationNumber)).thenReturn(firestationDto);

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
    public  void AlertService_shouldUseGetChildsdRelatedToAnAddress_ResultNotNull()  throws Exception {
        String address = "1509 Culver St";
        List<ChildAlertDto> childAlertDtos = new ArrayList<>();

        when(alertService.getChildsdRelatedToAnAddress(address)).thenReturn(childAlertDtos);

        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getChildsdRelatedToAnAddress(address);
    }

    @Test
    public  void AlertService_shouldUseGetChildsdRelatedToAnAddress_ResultNull()  throws Exception {
        String address = "Nowhere";

        when(alertService.getChildsdRelatedToAnAddress(address)).thenReturn(null);

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
    public void AlertService_shouldUseGetPhoneNumbersRelatedToAStation_ResultNotNull()  throws Exception {
        int stationNumber = 2;
        List<String> phoneNumbers = new ArrayList<>();

        when(alertService.getPhoneNumbersRelatedToAStation(stationNumber)).thenReturn(phoneNumbers);

        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPhoneNumbersRelatedToAStation(stationNumber);
    }

    @Test
    public void AlertService_shouldUseGetPhoneNumbersRelatedToAStation_ResultNull()  throws Exception {
        int stationNumber = 0;

        when(alertService.getPhoneNumbersRelatedToAStation(stationNumber)).thenReturn(null);

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
    public void AlertService_shouldUseGetPersonsRelatedToAnAddress_ResultNotNull() throws Exception {
        String address = "1509 Culver St";
        FireDto  fireDto = new FireDto();
        fireDto.setHouseholdMembers(new ArrayList<>());

        when(alertService.getPersonsRelatedToAnAddress(address)).thenReturn(fireDto);

        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getPersonsRelatedToAnAddress(address);
    }


    @Test
    public void AlertService_shouldUseGetPersonsRelatedToAnAddress_ResultNull() throws Exception {
        String address = "Nowhere";

        when(alertService.getPersonsRelatedToAnAddress(address)).thenReturn(null);

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
    public void AlertService_shouldUseGetHousesRelatedToAListOfStations_ResultNotNull()  throws Exception {
        List<Integer> stations = Arrays.asList(3,4);
        List<FloodDto> floodDtos = new ArrayList<>();

        when(alertService.getHousesRelatedToAListOfStations(stations)).thenReturn(floodDtos);

        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService, times(1)).getHousesRelatedToAListOfStations(stations);
    }

    @Test
    public void AlertService_shouldUseGetHousesRelatedToAListOfStations_ResultNull()  throws Exception {
        List<Integer> stations = Arrays.asList(30,40);

        when(alertService.getHousesRelatedToAListOfStations(stations)).thenReturn(null);

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
    public void AlertService_shouldUseGetInfoPerson_ResultNotNull() throws Exception {
        List<InfoPersonDto> InfoPersonDtos = new ArrayList<>();

        String firstName = "Sophia";
        String lastName = "Zemicks";

        when(alertService.getInfoPerson(firstName, lastName)).thenReturn(InfoPersonDtos);

        mockMvc.perform(get("http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getInfoPerson(firstName, lastName);
    }

    @Test
    public void AlertService_shouldUseGetInfoPerson_ResultNull() throws Exception {
        String firstName = "Averell";
        String lastName = "Dalton";

        when(alertService.getInfoPerson(firstName, lastName)).thenReturn(null);

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
    public void AlertService_shouldUseGetMailsRelatedToACity_ResultNotNull() throws Exception {
        List<String> mails = new ArrayList<>();
        String city = "Culver";

        when(alertService.getMailsRelatedToACity(city)).thenReturn(mails);

        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getMailsRelatedToACity(city);
    }


    @Test
    public void AlertService_shouldUseGetMailsRelatedToACity_ResultNull() throws Exception {
        String city = "Paris";

        when(alertService.getMailsRelatedToACity(city)).thenReturn(null);

        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));

        verify(alertService,times(1)).getMailsRelatedToACity(city);
    }
   }

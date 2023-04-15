package com.safetynet.safetynetalerts.controllertest;


import com.safetynet.safetynetalerts.controller.ServiceController;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.PersonDao;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    public void testGetPersonsRelatedToAStationResultNotNull() throws Exception {
        int stationNumber = 2;
        FirestationDto firestationDto = new FirestationDto();

        when(alertService.getPersonsRelatedToAStation(stationNumber)).thenReturn(firestationDto);

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPersonsRelatedToAStation(stationNumber);

    }

    @Test
    public void testGetPersonsRelatedToAStatioResultNull() throws Exception {
        int stationNumber = 2;
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
    public  void testGetChildsdRelatedToAnAddressResultNotNull()  throws Exception {
        String address = "Nowhere";
        List<ChildAlertDto> childAlertDtos = new ArrayList<>();

        when(alertService.getChildsdRelatedToAnAddress(address)).thenReturn(childAlertDtos);

        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getChildsdRelatedToAnAddress(address);
    }

    @Test
    public  void testGetChildsdRelatedToAnAddressResultNull()  throws Exception {
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
    public void testGetPhoneNumbersRelatedToAStationResultNotNull()  throws Exception {
        int stationNumber = 2;
        List<String> phoneNumbers = new ArrayList<>();

        when(alertService.getPhoneNumbersRelatedToAStation(stationNumber)).thenReturn(phoneNumbers);

        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPhoneNumbersRelatedToAStation(stationNumber);
    }

    @Test
    public void testGetPhoneNumbersRelatedToAStationResultNull()  throws Exception {
        int stationNumber = 2;

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
    public void testGetPersonsRelatedToAnAddressResultNotNull() throws Exception {
        String address = "Nowhere";
        FireDto  fireDto = new FireDto();
        fireDto.setHouseholdMembers(new ArrayList<>());

        when(alertService.getPersonsRelatedToAnAddress(address)).thenReturn(fireDto);

        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isOk());

        verify(alertService,times(1)).getPersonsRelatedToAnAddress(address);
    }


    @Test
    public void testGetPersonsRelatedToAnAddressResultNull() throws Exception {
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




//    @Test
//    void testFindPersonsWithExistingStations() {
//        // ARRANGE
//        List<Integer> stations = Arrays.asList(3,4);
//
//        FloodPersonDto floodPersonDto = new FloodPersonDto(
//                "Felicia",
//                "Boyd",
//                "841-874-6544",
//                37,
//                new ArrayList<>(Arrays.asList("tetracyclaz:650mg")),
//                new ArrayList<>(Arrays.asList("xilliathal")));
//
//        // ACT
//        FloodDto floodDto = alertService.getHousesRelatedToAListOfStations(stations);
//
//        // ASSERT
//        assertEquals(floodDto.getMapHousePersons().size(), 5);
//        assertEquals(floodDto.getMapHousePersons().get("112 Steppes Pl").size(), 3);
//        assertTrue(floodDto.getMapHousePersons().get("1509 Culver St").contains(floodPersonDto));
//    }









    //***************************************************************************************************
    // URL6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************




//    @Test
//    void  testGetInfoPersonOfAnExistingPerson() {
//        // ARRANGE, ACT
//        List<InfoPersonDto> infoPersonsDto = alertService.getInfoPerson( "Sophia", "Zemicks");
//
//        InfoPersonDto infoPersonDto = new InfoPersonDto(
//                "Zemicks",
//                "892 Downing Ct",
//                35,
//                "soph@email.com",
//                new ArrayList<>(Arrays.asList(
//                        "aznol:60mg",
//                        "hydrapermazol:900mg",
//                        "pharmacol:5000mg",
//                        "terazine:500mg")),
//                new ArrayList<>(Arrays.asList(
//                        "peanut",
//                        "shellfish",
//                        "aznol")));
//
//        // ASSERT
//        assertTrue(infoPersonsDto.contains(infoPersonDto));
//        assertEquals(infoPersonsDto.size(), 3);
//    }
//








    //***************************************************************************************************
    // URL7 :  http://localhost:8080/communityEmail?city=<city>
    //
    // Tests unitaires de la classe  ServiceController
    //***************************************************************************************************





//
//    @Test
//    void  testFindMailsWithExistingCity() {
//        // ARRANGE, ACT
//        List<String> communityEmail = alertService.getMailsRelatedToACity("Culver");
//
//        // ASSERT
//        assertEquals(communityEmail.size(), 23);
//        assertEquals(communityEmail.get(1), "drk@email.com");
//    }








}

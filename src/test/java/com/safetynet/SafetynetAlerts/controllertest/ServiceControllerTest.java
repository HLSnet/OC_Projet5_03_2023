package com.safetynet.safetynetalerts.controllertest;


import com.safetynet.safetynetalerts.controller.ServiceController;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.ChildAlertDto;
import com.safetynet.safetynetalerts.dto.FirestationDto;
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

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void testGetPersonsRelatedToAStationResultNotnull() throws Exception {
        // La station existe
        int stationNumber = 2;

        FirestationDto firestationDto = new FirestationDto();

        when(alertService.getPersonsRelatedToAStation(stationNumber)).thenReturn(firestationDto);

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk());

        verify(alertService, times(1)).getPersonsRelatedToAStation(stationNumber);

    }

    @Test
    public void testGetPersonsRelatedToAStatioResultNull() throws Exception {
        // La station n'existe pas
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
//
//    @Test
//    public  void testGetChildsdRelatedToAnAddress
//    List<ChildAlertDto> childAlertDtos = alertService.getChildsdRelatedToAnAddress(address);

}

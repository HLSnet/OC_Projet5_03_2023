package com.safetynet.safetynetalerts.controllerintegration;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.FirestationPersonDto;
import com.safetynet.safetynetalerts.repository.FirestationDaoImpl;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ServiceControllerIT {

    @Autowired
    MockMvc mockMvc;


    @BeforeEach
    void setUpData(){
        // Le fichier de test utilisé est une copie du fichier réel
        SetupJsonFile.reloadTestFile(JSONFILE_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        FirestationDaoImpl.checkIntegrityJsonFileFirestationAndSort();
    }


    //***************************************************************************************************
    //
    // Tests d'intégration de l'URL1 : http://localhost:8080/firestation?stationNumber=<station_number>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnAListOfPersons_whenGivenAnExistingStationNumber() throws Exception {
        int stationNumber = 2;

        List<FirestationPersonDto> firestationPersonDtos = Arrays.asList(
                new FirestationPersonDto("Eric","Cadigan","951 LoneTree Rd","841-874-7458"),
                new FirestationPersonDto("Jonanathan","Marrack","29 15th St","841-874-6513"),
                new FirestationPersonDto("Sophia", "Zemicks", "892 Downing Ct", "841-874-7878"),
                new FirestationPersonDto("Warren","Zemicks","892 Downing Ct","841-874-7512"),
                new FirestationPersonDto("Zach","Zemicks","892 Downing Ct","841-874-7512"));

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk())
                .andExpect((MockMvcResultMatchers.jsonPath("$.persons",  Matchers.hasSize(5))))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbAdult").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.nbChild").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[0].firstName").value(firestationPersonDtos.get(0).getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[0].lastName").value(firestationPersonDtos.get(0).getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[0].address").value(firestationPersonDtos.get(0).getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[0].phone").value(firestationPersonDtos.get(0).getPhone()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[1].firstName").value(firestationPersonDtos.get(1).getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[1].lastName").value(firestationPersonDtos.get(1).getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[1].address").value(firestationPersonDtos.get(1).getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[1].phone").value(firestationPersonDtos.get(1).getPhone()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[2].firstName").value(firestationPersonDtos.get(2).getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[2].lastName").value(firestationPersonDtos.get(2).getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[2].address").value(firestationPersonDtos.get(2).getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[2].phone").value(firestationPersonDtos.get(2).getPhone()))

                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[3].firstName").value(firestationPersonDtos.get(3).getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[3].lastName").value(firestationPersonDtos.get(3).getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[3].address").value(firestationPersonDtos.get(3).getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[3].phone").value(firestationPersonDtos.get(3).getPhone()))
                
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[4].firstName").value(firestationPersonDtos.get(4).getFirstName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[4].lastName").value(firestationPersonDtos.get(4).getLastName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[4].address").value(firestationPersonDtos.get(4).getAddress()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.persons[4].phone").value(firestationPersonDtos.get(4).getPhone()));
    }
    @Test
    public void AlertService_ShouldReturnNoContent_whenGivenANonExistingStationNumber() throws Exception {
        int stationNumber = 20;

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isNoContent());
        }


    //***************************************************************************************************
    // Tests d'intégration de l'URL2 :
    //
    // http://localhost:8080/childAlert?address=<address>
    //***************************************************************************************************
//
//
//
//    String address =  "1509 Culver St";
//        mockMvc.perform(get(" http://localhost:8080/childAlert?address=" + stationNumber))
//            .andExpect(status().isOk())
//


    //***************************************************************************************************
    // Tests d'intégration de l'URL3 :
    //
    // http://localhost:8080/phoneAlert?firestation=<firestation_number>
    //***************************************************************************************************


    //***************************************************************************************************
    // Tests d'intégration de l'URL4 :
    //
    // http://localhost:8080/fire?address=<address>
    //***************************************************************************************************



    //***************************************************************************************************
    // Tests d'intégration de l'URL5 :
    //
    // http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    //***************************************************************************************************


    //***************************************************************************************************
    // Tests d'intégration de l'URL6 :
    //
    // http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    //***************************************************************************************************


    //***************************************************************************************************
    // Tests d'intégration de l'URL7 :
    //
    // http://localhost:8080/communityEmail?city=<city>
    //***************************************************************************************************



}

package com.safetynet.safetynetalerts.controllerintegration;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.FirestationPersonDto;
import com.safetynet.safetynetalerts.repository.FirestationDaoImpl;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

                //    .andExpect(jsonPath("$[0].firstName", is("Laurent")));

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.persons", hasSize(5)))
                .andExpect(jsonPath("$.nbAdult", is(4)))
                .andExpect(jsonPath("$.nbChild", is(1)))
                .andExpect(jsonPath("$.persons[0].firstName", is(firestationPersonDtos.get(0).getFirstName())))
                .andExpect(jsonPath("$.persons[0].lastName", is(firestationPersonDtos.get(0).getLastName())))
                .andExpect(jsonPath("$.persons[0].address", is(firestationPersonDtos.get(0).getAddress())))
                .andExpect(jsonPath("$.persons[0].phone", is(firestationPersonDtos.get(0).getPhone())))

                .andExpect(jsonPath("$.persons[1].firstName", is(firestationPersonDtos.get(1).getFirstName())))
                .andExpect(jsonPath("$.persons[1].lastName", is(firestationPersonDtos.get(1).getLastName())))
                .andExpect(jsonPath("$.persons[1].address", is(firestationPersonDtos.get(1).getAddress())))
                .andExpect(jsonPath("$.persons[1].phone", is(firestationPersonDtos.get(1).getPhone())))

                .andExpect(jsonPath("$.persons[2].firstName", is(firestationPersonDtos.get(2).getFirstName())))
                .andExpect(jsonPath("$.persons[2].lastName", is(firestationPersonDtos.get(2).getLastName())))
                .andExpect(jsonPath("$.persons[2].address", is(firestationPersonDtos.get(2).getAddress())))
                .andExpect(jsonPath("$.persons[2].phone", is(firestationPersonDtos.get(2).getPhone())))

                .andExpect(jsonPath("$.persons[3].firstName", is(firestationPersonDtos.get(3).getFirstName())))
                .andExpect(jsonPath("$.persons[3].lastName", is(firestationPersonDtos.get(3).getLastName())))
                .andExpect(jsonPath("$.persons[3].address", is(firestationPersonDtos.get(3).getAddress())))
                .andExpect(jsonPath("$.persons[3].phone", is(firestationPersonDtos.get(3).getPhone())))
                
                .andExpect(jsonPath("$.persons[4].firstName", is(firestationPersonDtos.get(4).getFirstName())))
                .andExpect(jsonPath("$.persons[4].lastName", is(firestationPersonDtos.get(4).getLastName())))
                .andExpect(jsonPath("$.persons[4].address", is(firestationPersonDtos.get(4).getAddress())))
                .andExpect(jsonPath("$.persons[4].phone", is(firestationPersonDtos.get(4).getPhone())));
    }
    @Test
    public void AlertService_ShouldReturnNoContent_whenGivenANonExistingStationNumber() throws Exception {
        int stationNumber = 0;

        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isNoContent());
        }


    //***************************************************************************************************
    // Tests d'intégration de l'URL2 :
    //
    // http://localhost:8080/childAlert?address=<address>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnAListOfChild_whenGivenAnExistingAddress() throws Exception {
        String address =  "1509 Culver St";

        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk());

        // TODO
    }


    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingAddress() throws Exception {
        String address = "Nowhere St";

        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    //***************************************************************************************************
    // Tests d'intégration de l'URL3 :
    //
    // http://localhost:8080/phoneAlert?firestation=<firestation_number>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnAListOfPhoneNumbers_whenGivenAnExistingStationNumber() throws Exception {
        int stationNumber = 2;

        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk());

        // TODO
    }
    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingStationNumber() throws Exception {
        int stationNumber = 0;

        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    //***************************************************************************************************
    // Tests d'intégration de l'URL4 :
    //
    // http://localhost:8080/fire?address=<address>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnPersons_whenGivenAnExistingAddress() throws Exception {
        String address =  "1509 Culver St";

        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isOk());

        // TODO
    }



    @Test
    public void AlertService_ShouldReturnNoContent_whenGivenANonExistingAddress() throws Exception {
        String address =  "Nowhere";

        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isNoContent());
    }


    //***************************************************************************************************
    // Tests d'intégration de l'URL5 :
    //
    // http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnListOfHomes_whenGivenAListOfExistingStationNumbers() throws Exception {
        List<Integer> stations = Arrays.asList(3,4);

        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isOk());

        // TODO
    }

    @Test
    public void AlertService_ShouldReturnNoContent_whenGivenAListOfNonExistingStationNumbers() throws Exception {
        List<Integer> stations = Arrays.asList(30,40);

        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isNoContent());
    }


    //***************************************************************************************************
    // Tests d'intégration de l'URL6 :
    //
    // http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnPersonInformations_whenGivenAnExistingPersonName() throws Exception {
        String firstName = "Sophia";
        String lastName = "Zemicks";

        mockMvc.perform(get("http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().isOk());

        // TODO
    }

    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingPersonName() throws Exception {
        String firstName = "Averell";
        String lastName = "Dalton";

        mockMvc.perform(get("http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }


    //***************************************************************************************************
    // Tests d'intégration de l'URL7 :
    //
    // http://localhost:8080/communityEmail?city=<city>
    //***************************************************************************************************
    @Test
    public void AlertService_ShouldReturnTheListOfAllEmailsOfACity_whenGivenAnExistingCity() throws Exception {
        String city = "Culver";

        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk());

        // TODO
    }

    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingCity() throws Exception {
        String city = "Paris";

        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

}

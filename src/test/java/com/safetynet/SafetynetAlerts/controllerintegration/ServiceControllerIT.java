package com.safetynet.safetynetalerts.controllerintegration;

import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.FirestationPersonDto;
import com.safetynet.safetynetalerts.repository.FirestationDaoImpl;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(ServiceControllerIT.class);

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
                new FirestationPersonDto("Jonanathan","Marrack","29 15th St","841-874-6513"),
                new FirestationPersonDto("Sophia", "Zemicks", "892 Downing Ct", "841-874-7878"),
                new FirestationPersonDto("Warren","Zemicks","892 Downing Ct","841-874-7512"),
                new FirestationPersonDto("Zach","Zemicks","892 Downing Ct","841-874-7512"),
                new FirestationPersonDto("Eric","Cadigan","951 LoneTree Rd","841-874-7458"));

        logger.info("TI -> AlertService_ShouldReturnAListOfPersons_whenGivenAnExistingStationNumber()");
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
        logger.info("TI -> AlertService_ShouldReturnNoContent_whenGivenANonExistingStationNumber()");
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

        logger.info("TI -> AlertService_ShouldReturnAListOfChild_whenGivenAnExistingAddress()");
        mockMvc.perform(get("http://localhost:8080/childAlert?address=" + address))
                .andExpect(status().isOk())

                .andExpect(jsonPath("$.[0].firstName", is("Tenley")))
                .andExpect(jsonPath("$.[0].lastName", is("Boyd")))
                .andExpect(jsonPath("$.[0].age", is(11)))
                .andExpect(jsonPath("$.[0].householdMembers",hasSize(4)))

                .andExpect(jsonPath("$.[1].firstName", is("Roger")))
                .andExpect(jsonPath("$.[1].lastName", is("Boyd")))
                .andExpect(jsonPath("$.[1].age", is(5)))
                .andExpect(jsonPath("$.[1].householdMembers",hasSize(4)));

        // TO COMPLETE (IF NEEDED)
    }


    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingAddress() throws Exception {
        String address = "Nowhere St";

        logger.info("TI -> AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingAddress()");
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

        logger.info("TI -> AlertService_ShouldReturnAListOfPhoneNumbers_whenGivenAnExistingStationNumber()");
        mockMvc.perform(get("http://localhost:8080/phoneAlert?firestation=" + stationNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$.[0]", is("841-874-6513")))
                .andExpect(jsonPath("$.[1]", is("841-874-7878")))
                .andExpect(jsonPath("$.[2]", is("841-874-7512")))
                .andExpect(jsonPath("$.[3]", is("841-874-7512")))
                .andExpect(jsonPath("$.[4]", is("841-874-7458")));
    }


    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingStationNumber() throws Exception {
        int stationNumber = 0;

        logger.info("TI -> AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingStationNumber()");
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

        logger.info("TI -> AlertService_ShouldReturnPersons_whenGivenAnExistingAddress()");
        mockMvc.perform(get("http://localhost:8080/fire?address=" + address))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.station", is(3)))
                .andExpect(jsonPath("$.householdMembers", hasSize(5)))

                .andExpect(jsonPath("$.householdMembers[4].firstName", is("Felicia")))
                .andExpect(jsonPath("$.householdMembers[4].lastName", is("Boyd")))
                .andExpect(jsonPath("$.householdMembers[4].phone", is("841-874-6544")))
                .andExpect(jsonPath("$.householdMembers[4].age", is(37)))
                .andExpect(jsonPath("$.householdMembers[4].email", is("jaboyd@email.com")))
                .andExpect(jsonPath("$.householdMembers[4].medications[0]", is("tetracyclaz:650mg")))
                .andExpect(jsonPath("$.householdMembers[4].allergies[0]", is("xilliathal")));

        // TO COMPLETE (IF NEEDED)
    }

    @Test
    public void AlertService_ShouldReturnNoContent_whenGivenANonExistingAddress() throws Exception {
        String address =  "Nowhere";

        logger.info("TI -> AlertService_ShouldReturnNoContent_whenGivenANonExistingAddress()");
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

        logger.info("TI -> AlertService_ShouldReturnListOfHomes_whenGivenAListOfExistingStationNumbers()");
        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].address", is("112 Steppes Pl")))
                .andExpect(jsonPath("$[0].householdMembers", hasSize(3)))
                .andExpect(jsonPath("$[0].householdMembers[1].lastName", is("Peters")));

        // TO COMPLETE (IF NEEDED)  
    }

    @Test
    public void AlertService_ShouldReturnNoContent_whenGivenAListOfNonExistingStationNumbers() throws Exception {
        List<Integer> stations = Arrays.asList(30,40);

        logger.info("TI -> AlertService_ShouldReturnNoContent_whenGivenAListOfNonExistingStationNumbers()");
        mockMvc.perform(get("http://localhost:8080/flood/stations?stations=" + stations.get(0) + ","+ stations.get(1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
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

        logger.info("TI -> AlertService_ShouldReturnPersonInformations_whenGivenAnExistingPersonName()");
        mockMvc.perform(get("http://localhost:8080/personInfo?firstName=" + firstName + "&lastName=" + lastName))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))

                .andExpect(jsonPath("$.[0].firstName", is("Sophia")))
                .andExpect(jsonPath("$.[0].lastName", is("Zemicks")))
                .andExpect(jsonPath("$.[0].age", is(35)))
                .andExpect(jsonPath("$.[0].email", is("soph@email.com")))
                .andExpect(jsonPath("$.[0].address", is("892 Downing Ct")))
                .andExpect(jsonPath("$.[0].medications[0]", is("aznol:60mg")))
                .andExpect(jsonPath("$.[0].medications[1]", is("hydrapermazol:900mg")))
                .andExpect(jsonPath("$.[0].medications[2]", is("pharmacol:5000mg")))
                .andExpect(jsonPath("$.[0].medications[3]", is("terazine:500mg")))
                .andExpect(jsonPath("$.[0].allergies[0]", is("peanut")))
                .andExpect(jsonPath("$.[0].allergies[1]", is("shellfish")))
                .andExpect(jsonPath("$.[0].allergies[2]", is("aznol")))

                .andExpect(jsonPath("$.[1].firstName", is("Warren")))
                .andExpect(jsonPath("$.[1].lastName", is("Zemicks")))
                .andExpect(jsonPath("$.[1].age", is(38)))
                .andExpect(jsonPath("$.[1].email", is("ward@email.com")))
                .andExpect(jsonPath("$.[1].address", is("892 Downing Ct")))

                .andExpect(jsonPath("$.[2].firstName", is("Zach")))
                .andExpect(jsonPath("$.[2].lastName", is("Zemicks")))
                .andExpect(jsonPath("$.[2].age", is(6)))
                .andExpect(jsonPath("$.[2].email", is("zarc@email.com")))
                .andExpect(jsonPath("$.[2].address", is("892 Downing Ct")));
    }

    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingPersonName() throws Exception {
        String firstName = "Averell";
        String lastName = "Dalton";

        logger.info("TI -> AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingPersonName()");
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

        logger.info("TI -> AlertService_ShouldReturnTheListOfAllEmailsOfACity_whenGivenAnExistingCity()");
        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(15)))
                .andExpect(jsonPath("$.[0]", is("aly@imail.com")))
                .andExpect(jsonPath("$.[1]", is("bstel@email.com")))
                .andExpect(jsonPath("$.[2]", is("clivfd@ymail.com")))
                .andExpect(jsonPath("$.[3]", is("drk@email.com")))
                .andExpect(jsonPath("$.[4]", is("gramps@email.com")))
                .andExpect(jsonPath("$.[5]", is("jaboyd@email.com")))
                .andExpect(jsonPath("$.[6]", is("jpeter@email.com")))
                .andExpect(jsonPath("$.[7]", is("lily@email.com")))
                .andExpect(jsonPath("$.[8]", is("reg@email.com")))
                .andExpect(jsonPath("$.[9]", is("soph@email.com")))
                .andExpect(jsonPath("$.[10]", is("ssanw@email.com")))
                .andExpect(jsonPath("$.[11]", is("tcoop@ymail.com")))
                .andExpect(jsonPath("$.[12]", is("tenz@email.com")))
                .andExpect(jsonPath("$.[13]", is("ward@email.com")))
                .andExpect(jsonPath("$.[14]", is("zarc@email.com")));
    }

    @Test
    public void AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingCity() throws Exception {
        String city = "Paris";

        logger.info("TI -> AlertService_ShouldReturnAnEmptyList_whenGivenANonExistingCity()");
        mockMvc.perform(get("http://localhost:8080/communityEmail?city=" + city))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

}

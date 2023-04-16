package com.safetynet.safetynetalerts.controllerintegration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerIT {

    @Autowired
    MockMvc mockMvc;


    //***************************************************************************************************
    // URL1 : http://localhost:8080/firestation?stationNumber=<station_number>
    //
    // Tests d'intégration de l'URL
    //***************************************************************************************************
    @Test
    public void testItGetPersonsRelatedToAStationResultNotNull() throws Exception {
        int stationNumber = 2;
        mockMvc.perform(get("http://localhost:8080/firestation?stationNumber=" + stationNumber))
                .andExpect(status().isOk());



    }

}

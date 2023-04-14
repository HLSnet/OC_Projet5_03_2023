package com.safetynet.safetynetalerts.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.controller.FirestationController;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.util.UriUtils;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FirestationDao firestationDao;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
    }


    //***************************************************************************************************
    // TESTS REQUETE GET : recherche par numéro de station
    //
    // http://localhost:8080/firestation/station/{stationNumber}
    //***************************************************************************************************

    @Test
    public void testGetFirestationOk() throws Exception {
        // La station existe
        List<Firestation> firestations = new ArrayList<>();
        int station = 1;

        Firestation firestation = new Firestation("644 Gershwin Cir", station);
        firestations.add(firestation);
        firestation = new Firestation("908 73rd St", station);
        firestations.add(firestation);
        firestation = new Firestation("947 E. Rose Dr", station);
        firestations.add(firestation);

        when(firestationDao.findByStation(1)).thenReturn(firestations);

        mockMvc.perform(get("/firestation/station/" + station))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].address").value("644 Gershwin Cir"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].station").value(station))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].address").value("908 73rd St"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[1].station").value(station))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].address").value("947 E. Rose Dr"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[2].station").value(station));

        verify(firestationDao, times(1)).findByStation(station);
    }

    @Test
    public void testGetFirestationNok() throws Exception {
        // La station n'existe pas
        List<Firestation> firestations = new ArrayList<>();
        int station = 10;

        when(firestationDao.findByStation(station)).thenReturn(firestations);

        mockMvc.perform(get("/firestation/station/" + station))
                .andExpect(status().isNoContent());


        verify(firestationDao, times(1)).findByStation(station);
    }

    //***************************************************************************************************
    // TESTS REQUETE GET : recherche par adresse
    //
    // http://localhost:8080/firestation/adress/{adress}
    //***************************************************************************************************
    @Test
    public void testGetAdressesOk() throws Exception {
        // L'adresse existe
        String adresse = "834 Binoc Ave";
        int station = 3;

        Firestation firestation = new Firestation(adresse, station);

        when(firestationDao.findByAdress(adresse)).thenReturn(firestation);

        mockMvc.perform(get("/firestation/adress/" + adresse ))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value(adresse))
                .andExpect(MockMvcResultMatchers.jsonPath("$.station").value(station));

        verify(firestationDao, times(1)).findByAdress(adresse);
    }
    @Test
    public void testGetAdressesNok() throws Exception {
        // L'adresse n'existe pas
        String adresse = "Nowhere";

        when(firestationDao.findByAdress(adresse)).thenReturn(null);

        mockMvc.perform(get("/firestation/adress/" + adresse ))
                .andExpect(status().isNoContent());

        verify(firestationDao, times(1)).findByAdress(adresse);
    }


    //***************************************************************************************************
    // TESTS REQUETE POST
    //
    // http://localhost:8080/firestation
    //***************************************************************************************************

    @Test
    public void testAddFirestationOk() throws Exception {
        // La firestation  n'existe pas dans le fichier : ajout possible
        String adresse = "New adress in the city";
        int station = 19;

        Firestation firestation = new Firestation(adresse, station);

        when(firestationDao.save(firestation)).thenReturn(true);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isCreated()) // On vérifie que le code de réponse est "201 Created"
                //  Le caractère d’espacement n’est pas autorisé dans une URL : un espace sera codé %20
                //  Le caractère “%” sert de préfixe pour coder des caractères qui seraient problématiques dans une URL.
                // On utilise UriUtils.encodePath pour afficher les espaces dans l'Url
                .andExpect(header().string("Location", "http://localhost/firestation/adress/" + UriUtils.encodePath(adresse, "UTF-8")));

        verify(firestationDao, times(1)).save(firestation);
    }

    @Test
    public void testAddFirestationNok() throws Exception {
        // La firestation existe déja dans le fichier : pas d'ajout
        String adresse = "834 Binoc Ave";
        int station = 3;

        Firestation firestation = new Firestation(adresse, station);

        when(firestationDao.save(firestation)).thenReturn(false);

        mockMvc.perform(post("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isNoContent());

        verify(firestationDao, times(1)).save(firestation);
    }


    //***************************************************************************************************
    // TESTS REQUETE PUT
    //
    // http://localhost:8080/firestation
    //***************************************************************************************************
    @Test
    public void testUpdateFirestationOk() throws Exception {
        // La firestation  existe dans le fichier : modification possible
        String adresse = "834 Binoc Ave";
        int station = 3;

        Firestation firestation = new Firestation(adresse, station);

        when(firestationDao.update(firestation)).thenReturn(true);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isOk());

        verify(firestationDao, times(1)).update(firestation);
    }


    @Test
    public void testUpdateFirestationNok() throws Exception {
        // La firestation n'existe pas dans le fichier : modification impossible
        String adresse = "New adress in the city";
        int station = 19;

        Firestation firestation = new Firestation(adresse, station);

        when(firestationDao.update(firestation)).thenReturn(false);

        mockMvc.perform(put("/firestation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(firestation)))
                .andExpect(status().isNoContent());

        verify(firestationDao, times(1)).update(firestation);
    }

    //***************************************************************************************************
    // TESTS REQUETE DELETE : suppression par station
    //
    // http://localhost:8080/firestation/{stationNumber}
    //***************************************************************************************************
    @Test
    public void testDeleteStationOk() throws Exception {
        // La station existe : suppression possible
        int station = 1;

        when(firestationDao.deleteStation(station)).thenReturn(true);

        mockMvc.perform(delete("/firestation/station/" + station))
               .andExpect(status().isOk());

        verify(firestationDao, times(1)).deleteStation(station);
    }

    @Test
    public void testDeleteStationNok() throws Exception {
        // La station n'existe pas : suppression impossible
        int station = 100;

        when(firestationDao.deleteStation(station)).thenReturn(false);

        mockMvc.perform(delete("/firestation/station/" + station))
                .andExpect(status().isNoContent());

        verify(firestationDao, times(1)).deleteStation(station);
    }

    //***************************************************************************************************
    // TESTS REQUETE DELETE : suppression par adresse
    //
    // http://localhost:8080/firestation/adress/{adress}
    //***************************************************************************************************
    @Test
    public void testDeleteAdressOk() throws Exception {
        // L'adresse existe : suppression possible
        String adresse = "834 Binoc Ave";

        when(firestationDao.deleteAdress(adresse)).thenReturn(true);

        mockMvc.perform(delete("/firestation/adress/" + adresse))
                .andExpect(status().isOk());

        verify(firestationDao, times(1)).deleteAdress(adresse);
    }


    @Test
    public void testDeleteAdressNok() throws Exception {
        // L'adresse n'existe pas : suppression impossible
        String adresse = "Nowhere";

        when(firestationDao.deleteAdress(adresse)).thenReturn(false);

        mockMvc.perform(delete("/firestation/adress/" + adresse))
                .andExpect(status().isNoContent());

        verify(firestationDao, times(1)).deleteAdress(adresse);
    }

}

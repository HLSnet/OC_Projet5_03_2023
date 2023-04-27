package com.safetynet.safetynetalerts.controllertest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.controller.MedicalRecordController;
import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.JsonFileIO;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {

    private static Logger logger = LoggerFactory.getLogger(MedicalRecordControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalrecordDao medicalrecordDao;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JsonFileIO(JSONFILE_TEST_PATHNAME);
    }

    //***************************************************************************************************
    // TESTS REQUETE GET
    //
    // http://localhost:8080/medicalrecord/{firstName}/{lastName}
    //***************************************************************************************************
    @Test
    public void testGetMedicalrecordOk() throws Exception {
        // Le dossier médical existe
        Medicalrecord medicalrecord = new Medicalrecord(
                "Averell",
                "Dalton",
                "01/01/1900",
                new ArrayList<>(Arrays.asList(
                        "paracetamol:1000mg",
                        "doliprane:500mg",
                        "whisky: 2l")),
                new ArrayList<>(Arrays.asList(
                        "Lucky Luke",
                        "Jail")));

        when(medicalrecordDao.findByName("Averell", "Dalton")).thenReturn(medicalrecord);

        logger.info("TU -> testGetMedicalrecordOk() : Test unitaire de cas nominal de la methode MedicalrecordDao::findByName");
        mockMvc.perform(get("/medicalrecord/Averell/Dalton"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Averell"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dalton"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthdate").value("01/01/1900"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications.[0]").value("paracetamol:1000mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications.[1]").value("doliprane:500mg"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.medications.[2]").value("whisky: 2l"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies.[0]").value("Lucky Luke"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.allergies.[1]").value("Jail"));

        verify(medicalrecordDao, times(1)).findByName("Averell", "Dalton");
    }

    @Test
    public void testGetMedicalrecordNok() throws Exception {
        // La personne n'existe pas
        when(medicalrecordDao.findByName("Averell", "Dalton")).thenReturn(null);
        logger.info("TU -> testGetMedicalrecordNok() : Test unitaire de cas d'erreur de la methode MedicalrecordDao::findByName");
        mockMvc.perform(get("/medicalrecord/Averell/Dalton"))
                .andExpect(status().isNoContent());

        verify(medicalrecordDao, times(1)).findByName("Averell", "Dalton");
    }


    //***************************************************************************************************
    // TESTS REQUETE POST
    //
    // http://localhost:8080/medicalrecord
    //***************************************************************************************************
    @Test
    public void testAddMedicalrecordOk() throws Exception {
        // Le dossier médical n'existe pas dans le fichier : ajout possible
        Medicalrecord medicalrecord = new Medicalrecord(
                "Averell",
                "Dalton",
                "01/01/1900",
                new ArrayList<>(Arrays.asList(
                        "paracetamol:1000mg",
                        "doliprane:500mg",
                        "whisky: 2l")),
                new ArrayList<>(Arrays.asList(
                        "Lucky Luke",
                        "Jail")));

        when(medicalrecordDao.save(medicalrecord)).thenReturn(true);

        logger.info("TU -> testAddMedicalrecordOk() : Test unitaire de cas nominal de la methode MedicalrecordDao::save");
        mockMvc.perform(post("/medicalrecord")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(medicalrecord)))
                .andExpect(status().isCreated())// On vérifie que le code de réponse est "201 Created"
                .andExpect(header().string("Location", "http://localhost/medicalrecord/Averell/Dalton"));

        verify(medicalrecordDao, times(1)).save(medicalrecord);
    }

    @Test
    public void testAddMedicalrecordNok() throws Exception {
        // Le dossier médical existe déja dans le fichier : pas d'ajout
        Medicalrecord medicalrecord = new Medicalrecord(
                "Averell",
                "Dalton",
                "01/01/1900",
                new ArrayList<>(Arrays.asList(
                        "paracetamol:1000mg",
                        "doliprane:500mg",
                        "whisky: 2l")),
                new ArrayList<>(Arrays.asList(
                        "Lucky Luke",
                        "Jail")));

        when(medicalrecordDao.save(medicalrecord)).thenReturn(false);

        logger.info("TU -> testAddMedicalrecordNok() : Test unitaire de cas d'erreur de la methode MedicalrecordDao::save");
        mockMvc.perform(post("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalrecord)))
                .andExpect(status().isConflict());

        verify(medicalrecordDao, times(1)).save(medicalrecord);
    }


    //***************************************************************************************************
    // TESTS REQUETE PUT
    //
    // http://localhost:8080/medicalrecord
    //***************************************************************************************************
    @Test
    public void testUpdateMedicalrecordOk() throws Exception {
        // Le dossier médical existe dans le fichier : modification possible
        Medicalrecord medicalrecord = new Medicalrecord(
                "Averell",
                "Dalton",
                "01/01/1900",
                new ArrayList<>(Arrays.asList(
                        "paracetamol:1000mg",
                        "doliprane:500mg",
                        "whisky: 2l")),
                new ArrayList<>(Arrays.asList(
                        "Lucky Luke",
                        "Jail")));

        when(medicalrecordDao.update(medicalrecord)).thenReturn(true);

        logger.info("TU -> medicalrecordDao() : Test unitaire de cas nominal de la methode MedicalrecordDao::update");
        mockMvc.perform(put("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalrecord)))
                .andExpect(status().isOk());

        verify(medicalrecordDao, times(1)).update(medicalrecord);
    }

    @Test
    public void testUpdateMedicalrecordNok() throws Exception {
        // Le dossier médical  n'existe pas dans le fichier : modification impossible
        Medicalrecord medicalrecord = new Medicalrecord(
                "Averell",
                "Dalton",
                "01/01/1900",
                new ArrayList<>(Arrays.asList(
                        "paracetamol:1000mg",
                        "doliprane:500mg",
                        "whisky: 2l")),
                new ArrayList<>(Arrays.asList(
                        "Lucky Luke",
                        "Jail")));

        when(medicalrecordDao.update(medicalrecord)).thenReturn(false);

        logger.info("TU -> testUpdateMedicalrecordNok() : Test unitaire de cas d'erreur de la methode MedicalrecordDao::update");
        mockMvc.perform(put("/medicalrecord")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(medicalrecord)))
                .andExpect(status().isNoContent());

        verify(medicalrecordDao, times(1)).update(medicalrecord);
    }


    //***************************************************************************************************
    // TESTS REQUETE DELETE
    //
    // http://localhost:8080/medicalrecord/{firstName}/{lastName}
    //***************************************************************************************************
    @Test
    public void testDeleteMedicalrecordOk() throws Exception {
        // Le dossier médical existe : suppression possible
        when(medicalrecordDao.delete("Averell", "Dalton")).thenReturn(true);

        logger.info("TU -> testDeleteMedicalrecordOk() : Test unitaire de cas nominal de la methode MedicalrecordDao::delete");
        mockMvc.perform(delete("/medicalrecord/Averell/Dalton"))
                .andExpect(status().isOk());
        verify(medicalrecordDao, times(1)).delete("Averell", "Dalton");

    }

    @Test
    public void testDeleteMedicalrecordNok() throws Exception {
        // Le dossier médical n'existe pas : suppression impossible
        when(medicalrecordDao.delete("Averell", "Dalton")).thenReturn(false);

        logger.info("TU -> testDeleteMedicalrecordNok() : Test unitaire de cas d'erreur de la methode MedicalrecordDao::delete");
        mockMvc.perform(delete("/medicalrecord/Averell/Dalton"))
                .andExpect(status().isNoContent());
        verify(medicalrecordDao, times(1)).delete("Averell", "Dalton");
    }

}

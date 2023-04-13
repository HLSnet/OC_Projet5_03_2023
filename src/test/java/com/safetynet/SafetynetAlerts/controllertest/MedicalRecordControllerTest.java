package com.safetynet.safetynetalerts.controllertest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.controller.MedicalRecordController;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MedicalrecordDao medicalrecordDao;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
    }

    //***************************************************************************************************
    // TESTS REQUETES GET
    //***************************************************************************************************
    @Test
    public void testGetMedicalrecordOk() throws Exception {
        // test 1 : le dossier médical existe
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
        // test 2 : la personne n'existe pas
        when(medicalrecordDao.findByName("Averell", "Dalton")).thenReturn(null);
        mockMvc.perform(get("/medicalrecord/Averell/Dalton"))
                .andExpect(status().isNoContent());

        verify(medicalrecordDao, times(1)).findByName("Averell", "Dalton");
    }


    //***************************************************************************************************
    // TESTS REQUETES POST
    //***************************************************************************************************
//    @Test
//    public void testPostPersonOk() throws Exception {
//        // test 1 : La personne n'existe pas dans le fichier : ajout possible
//        Person person = new Person();
//        person.setFirstName("Averell");
//        person.setLastName("Dalton");
//        person.setAddress("19 Saloon St");
//        person.setCity("Daisy town");
//        person.setZip("00000");
//        person.setPhone("111-222-3333");
//        person.setEmail("a.dalton@jail.com");
//
//        when(personDao.save(person)).thenReturn(true);
//
//        mockMvc.perform(post("/person")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(person)))
//                .andExpect(status().isCreated()) // On vérifie que le code de réponse est "201 Created"
//                .andExpect(header().string("Location", "http://localhost/person/Averell/Dalton"));
//
//        verify(personDao, times(1)).save(person);
//    }


//    @Test
//    public void testPostPersonNok() throws Exception {
//        // test 2 : la personne existe déja dans le fichier : pas d'ajout
//        Person person = new Person();
//        person.setFirstName("Averell");
//        person.setLastName("Dalton");
//        person.setAddress("19 Saloon St");
//        person.setCity("Daisy town");
//        person.setZip("00000");
//        person.setPhone("111-222-3333");
//        person.setEmail("a.dalton@jail.com");
//
//        when(personDao.save(person)).thenReturn(false);
//        mockMvc.perform(post("/person")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(person)))
//                .andExpect(status().isNoContent());
//
//        verify(personDao, times(1)).save(person);
//    }


    //***************************************************************************************************
    // TESTS REQUETES PUT
    //***************************************************************************************************
//    @Test
//    public void testPutPersonOk() throws Exception {
//        // test 1 : La personne existe dans le fichier : modification possible
//        Person person = new Person();
//        person.setFirstName("Averell");
//        person.setLastName("Dalton");
//        person.setAddress("19 Saloon St");
//        person.setCity("Daisy town");
//        person.setZip("00000");
//        person.setPhone("111-222-3333");
//        person.setEmail("a.dalton@jail.com");
//
//        when(personDao.update(person)).thenReturn(true);
//
//        mockMvc.perform(put("/person")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(person)))
//                .andExpect(status().isOk());
//
//        verify(personDao, times(1)).update(person);
//    }
//
//
//    @Test
//    public void testPutPersonNok() throws Exception {
//        // test 2 : La personne n'existe pas dans le fichier : modification impossible
//        Person person = new Person();
//        person.setFirstName("Averell");
//        person.setLastName("Dalton");
//        person.setAddress("19 Saloon St");
//        person.setCity("Daisy town");
//        person.setZip("00000");
//        person.setPhone("111-222-3333");
//        person.setEmail("a.dalton@jail.com");
//
//        mockMvc.perform(put("/person")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(person)))
//                .andExpect(status().isNoContent());
//
//        verify(personDao, times(1)).update(person);
//    }

    //***************************************************************************************************
    // TESTS REQUETES DELETE
    //***************************************************************************************************
//    @Test
//    public void testDelatePersonOk() throws Exception {
//        when(personDao.delete("Averell", "Dalton")).thenReturn(true);
//        mockMvc.perform(delete("/person/Averell/Dalton"));
//
//    }
//
//    @Test
//    public void testDeletePersonNok() throws Exception {
//        // test 2 : la personne n'existe pas
//        when(personDao.delete("Averell", "Dalton")).thenReturn(false);
//        mockMvc.perform(get("/person/Averell/Dalton"))
//                .andExpect(status().isNoContent());
//    }

}

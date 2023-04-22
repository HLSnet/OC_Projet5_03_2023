package com.safetynet.safetynetalerts.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.controller.PersonController;
import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.PersonDao;
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


import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;

import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

        private static Logger logger = LoggerFactory.getLogger(PersonController.class);

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PersonDao personDao;

        @BeforeEach
        void setUpData(){
                SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
                new JasonFileIO(JSONFILE_TEST_PATHNAME);
        }


        //***************************************************************************************************
        // TESTS REQUETE GET
        //
        // http://localhost:8080/person/{firstName}/{lastName}
        //***************************************************************************************************
        @Test
        public void testGetPersonOk() throws Exception {
                // La personne existe
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.findByName("Averell", "Dalton")).thenReturn(person);

                logger.info("TU -> testGetPersonOk() : Test unitaire de cas nominal de la methode PersonDao::findByName");
                mockMvc.perform(get("/person/Averell/Dalton"))
                        .andExpect(status().isOk())
                        .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Averell"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Dalton"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("19 Saloon St"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Daisy town"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.zip").value("00000"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("111-222-3333"))
                        .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("a.dalton@jail.com"));

                verify(personDao, times(1)).findByName("Averell", "Dalton");

        }

        @Test
        public void testGetPersonNok() throws Exception {
        // La personne n'existe pas
        when(personDao.findByName("Averell", "Dalton")).thenReturn(null);
                logger.info("TU -> testGetPersonNok() : Test unitaire de cas d'erreur de la methode PersonDao::findByName");
        mockMvc.perform(get("/person/Averell/Dalton"))
                .andExpect(status().isNoContent());

                verify(personDao, times(1)).findByName("Averell", "Dalton");
        }


        //***************************************************************************************************
        // TESTS REQUETE POST
        //
        // http://localhost:8080/person
        //***************************************************************************************************
        @Test
        public void testPostPersonOk() throws Exception {
                // La personne n'existe pas dans le fichier : ajout possible
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.save(person)).thenReturn(true);
                logger.info("TU -> testPostPersonOk() : Test unitaire de cas nominal de la methode PersonDao::save");
                mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isCreated()) // On vérifie que le code de réponse est "201 Created"
                        .andExpect(header().string("Location", "http://localhost/person/Averell/Dalton"));

                verify(personDao, times(1)).save(person);
        }


        @Test
        public void testPostPersonNok() throws Exception {
                // La personne existe déja dans le fichier : pas d'ajout
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.save(person)).thenReturn(false);
                logger.info("TU -> testPostPersonNok() : Test unitaire de cas d'erreur de la methode PersonDao::save");
                mockMvc.perform(post("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isConflict());

                verify(personDao, times(1)).save(person);
        }


        //***************************************************************************************************
        // TESTS REQUETE PUT
        //
        // http://localhost:8080/person
        //***************************************************************************************************
        @Test
        public void testPutPersonOk() throws Exception {
                // La personne existe dans le fichier : modification possible
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.update(person)).thenReturn(true);
                logger.info("TU -> testPutPersonOk() : Test unitaire de cas nominal de la methode PersonDao::update");
                mockMvc.perform(put("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isOk());

                verify(personDao, times(1)).update(person);
        }


        @Test
        public void testPutPersonNok() throws Exception {
                // La personne n'existe pas dans le fichier : modification impossible
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.update(person)).thenReturn(false);
                logger.info("TU -> testPutPersonNok() : Test unitaire de cas d'erreur de la methode PersonDao::update");
                mockMvc.perform(put("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isNoContent());

                verify(personDao, times(1)).update(person);
        }

        //***************************************************************************************************
        // TESTS REQUETE DELETE
        //***************************************************************************************************
        @Test
        public void testDeletePersonOk() throws Exception {
                // La personne existe : suppression possible
                when(personDao.delete("Averell", "Dalton")).thenReturn(true);
                logger.info("TU -> testDeletePersonOk() : Test unitaire de cas nominal de la methode PersonDao::delete");
                mockMvc.perform(delete("/person/Averell/Dalton"))
                      .andExpect(status().isOk());
                verify(personDao, times(1)).delete("Averell", "Dalton");

        }

        @Test
        public void testDeletePersonNok() throws Exception {
                // La personne n'existe pas : suppression impossible
                when(personDao.delete("Averell", "Dalton")).thenReturn(false);
                logger.info("TU -> testDeletePersonNok() : Test unitaire de cas d'erreur de la methode PersonDao::delete");
                mockMvc.perform(delete("/person/Averell/Dalton"))
                        .andExpect(status().isNoContent());
                verify(personDao, times(1)).delete("Averell", "Dalton");
        }
}

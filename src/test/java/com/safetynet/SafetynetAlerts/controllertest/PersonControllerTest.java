package com.safetynet.safetynetalerts.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.safetynetalerts.controller.PersonController;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        // TESTS REQUETES GET
        //***************************************************************************************************
        @Test
        public void testGetPersonOk() throws Exception {
                // test 1 : la personne existe
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.findByName("Averell", "Dalton")).thenReturn(person);

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
        // test 2 : la personne n'existe pas
        when(personDao.findByName("Averell", "Dalton")).thenReturn(null);
        mockMvc.perform(get("/person/Averell/Dalton"))
                .andExpect(status().isNoContent());

                verify(personDao, times(1)).findByName("Averell", "Dalton");
        }


        //***************************************************************************************************
        // TESTS REQUETES POST
        //***************************************************************************************************
        @Test
        public void testPostPersonOk() throws Exception {
                // test 1 : La personne n'existe pas dans le fichier : ajout possible
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.save(person)).thenReturn(true);

                mockMvc.perform(post("/person")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isCreated()) // On vérifie que le code de réponse est "201 Created"
                        .andExpect(header().string("Location", "http://localhost/person/Averell/Dalton"));

                verify(personDao, times(1)).save(person);
        }


        @Test
        public void testPostPersonNok() throws Exception {
                // test 2 : la personne existe déja dans le fichier : pas d'ajout
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.save(person)).thenReturn(false);
                       mockMvc.perform(post("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isNoContent());

                verify(personDao, times(1)).save(person);
        }


        //***************************************************************************************************
        // TESTS REQUETES PUT
        //***************************************************************************************************
        @Test
        public void testPutPersonOk() throws Exception {
                // test 1 : La personne existe dans le fichier : modification possible
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                when(personDao.update(person)).thenReturn(true);

                mockMvc.perform(put("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isOk());

                verify(personDao, times(1)).update(person);
        }


        @Test
        public void testPutPersonNok() throws Exception {
                // test 2 : La personne n'existe pas dans le fichier : modification impossible
                Person person = new Person(
                        "Averell",
                        "Dalton",
                        "19 Saloon St",
                        "Daisy town",
                        "00000",
                        "111-222-3333",
                        "a.dalton@jail.com");

                mockMvc.perform(put("/person")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(person)))
                        .andExpect(status().isNoContent());

                verify(personDao, times(1)).update(person);
        }

        //***************************************************************************************************
        // TESTS REQUETES DELETE
        //***************************************************************************************************
        @Test
        public void testDeletePersonOk() throws Exception {
                when(personDao.delete("Averell", "Dalton")).thenReturn(true);
                mockMvc.perform(delete("/person/Averell/Dalton"))
                      .andExpect(status().isOk());
                verify(personDao, times(1)).delete("Averell", "Dalton");

        }

        @Test
        public void testDeletePersonNok() throws Exception {
                // test 2 : la personne n'existe pas
                when(personDao.delete("Averell", "Dalton")).thenReturn(false);
                mockMvc.perform(delete("/person/Averell/Dalton"))
                        .andExpect(status().isNoContent());
                verify(personDao, times(1)).delete("Averell", "Dalton");
        }
}

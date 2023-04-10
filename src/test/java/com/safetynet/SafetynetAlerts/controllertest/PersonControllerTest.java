package com.safetynet.safetynetalerts.controllertest;

import com.safetynet.safetynetalerts.controller.PersonController;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private PersonDao personDao;

//        @Test
//        public void testGetPerson() throws Exception {
//            mockMvc.perform(get("/person/Jamie/Peters"))
//                    .andExpect(status().isOk());
//        }
}

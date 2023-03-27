package com.safetynet.safetynetalerts.medicalrecord;

import com.safetynet.safetynetalerts.iotest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;

public class MedicalrecordTest {

    private List<Medicalrecord> medicalrecords;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.initialisation();
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
    }


    @Autowired
    private MedicalrecordDao medicalrecordDaoImpl;


//*********************************************************************************************************
//  Tests unitaires de la méthode 'findBy' de la classe  MedicalrecordDao
//*********************************************************************************************************
//    @Test
//    void testFindAnExistingMedicalrecordDao() {
//        // Arrange, Act
//        Medicalrecord medicalrecord = medicalrecordDaoImpl.findByName("Sophia", "Zemicks");
//
//        // Assert
//        assertNotNull(medicalrecord);
//        assertEquals(medicalrecord.getFirstName(), "Sophia");
//        assertEquals(medicalrecord.getLastName(), "Zemicks");
//        assertEquals(medicalrecord.getBirthdate(), "03/06/1988");
//        assertEquals(medicalrecord.getMedications(), List.of("aznol:60mg", "hydrapermazol:900mg", "pharmacol:5000mg", "terazine:500mg"));
//        assertEquals(medicalrecord.getAllergies(), List.of("peanut", "shellfish", "aznol" ));
//
//        assertTrue(this.medicalrecords.contains(medicalrecord));
//    }
//
//    @Test
//    void testFindANonExistingPerson() {
//        // Arrange, Act
//        Person person = personDaoImpl.findByName("Averell", "Dalton");
//
//        // Assert
//        assertNull(person);
//
//        assertFalse(this.persons.contains(person));
//    }



}

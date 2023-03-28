package com.safetynet.safetynetalerts.medicalrecord;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    @Test
    void testFindAnExistingMedicalrecordDao() {
        // ARRANGE, ACT
        Medicalrecord medicalrecord = medicalrecordDaoImpl.findByName("Sophia", "Zemicks");

        // ASSERT
        assertNotNull(medicalrecord);

        assertTrue(this.medicalrecords.contains(medicalrecord));
    }

    @Test
    void testFindANonExistingPerson() {
        // ARRANGE, ACT
        Medicalrecord medicalrecord = medicalrecordDaoImpl.findByName("Averell", "Dalton");

        // ASSERT
        assertNull(medicalrecord);
        assertFalse(this.medicalrecords.contains(medicalrecord));
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'save' de la classe  MedicalrecordDao
//*********************************************************************************************************
    @Test
    void testSaveNewPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToAdd = new Medicalrecord();
        medicalrecordToAdd.setFirstName("Averell");
        medicalrecordToAdd.setLastName("Dalton");
        medicalrecordToAdd.setBirthdate("01/01/1900");

        ArrayList<String> medications= new ArrayList<>();
        medications.add("paracetamol:1000mg");
        medications.add("doliprane:500mg");
        medications.add("whisky: 2l");
        medicalrecordToAdd.setMedications(medications);

        ArrayList<String> allergies= new ArrayList<>();
        medications.add("Lucky Luke");
        medications.add("Jail");
        medicalrecordToAdd.setAllergies(allergies);

        // ACT
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.save(medicalrecordToAdd);

        // ASSERT
        assertTrue(result);
        // On vérifie que l'ajout a été fait
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertTrue(this.medicalrecords.contains(medicalrecordToAdd));
        // On vérifie qu'il n'y a eu qu'un ajout (pas d'ajout multiple)
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore +1 );
    }


    @Test
    void testSaveAnExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToAdd = new Medicalrecord();
        medicalrecordToAdd.setFirstName("Sophia");
        medicalrecordToAdd.setLastName("Zemicks");
        medicalrecordToAdd.setBirthdate("03/06/1988");

        ArrayList<String> medications= new ArrayList<>();
        medications.add("aznol:60mg");
        medications.add("hydrapermazol:900mg");
        medications.add("pharmacol:5000mg");
        medications.add("terazine:500mg");
        medicalrecordToAdd.setMedications(medications);

        ArrayList<String> allergies= new ArrayList<>();
        allergies.add("peanut");
        allergies.add("shellfish");
        allergies.add("aznol");
        medicalrecordToAdd.setAllergies(allergies);


        // ACT
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.save(medicalrecordToAdd);

        // ASSERT
        assertFalse(result);
        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore);
    }

}

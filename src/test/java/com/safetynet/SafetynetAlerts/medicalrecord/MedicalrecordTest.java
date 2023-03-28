package com.safetynet.safetynetalerts.medicalrecord;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Medicalrecord;
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


//*********************************************************************************************************
//  Tests unitaires de la méthode 'update' de la classe  MedicalrecordDao
//*********************************************************************************************************
    @Test
    void testUpdateAnExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToUpdate = new Medicalrecord();
        medicalrecordToUpdate.setFirstName("Sophia");
        medicalrecordToUpdate.setLastName("Zemicks");
        medicalrecordToUpdate.setBirthdate("03/06/1988");

        ArrayList<String> medications= new ArrayList<>();
        medications.add("aznol:60mg");
        medications.add("hydrapermazol:900mg");
        medications.add("pharmacol:5000mg");
        medications.add("terazine:500mg");
        medicalrecordToUpdate.setMedications(medications);

        ArrayList<String> allergies= new ArrayList<>();
        allergies.add("peanut");
        allergies.add("shellfish");
        allergies.add("aznol");
        medicalrecordToUpdate.setAllergies(allergies);

        // ACT
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.update(medicalrecordToUpdate);

        // ASSERT
        assertTrue(result);
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertTrue(this.medicalrecords.contains(medicalrecordToUpdate));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore);
    }


    @Test
    void testUpdateANonExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToUpdate = new Medicalrecord();
        medicalrecordToUpdate.setFirstName("Averell");
        medicalrecordToUpdate.setLastName("Dalton");
        medicalrecordToUpdate.setBirthdate("01/01/1900");

        ArrayList<String> medications= new ArrayList<>();
        medications.add("paracetamol:1000mg");
        medications.add("doliprane:500mg");
        medications.add("whisky: 2l");
        medicalrecordToUpdate.setMedications(medications);

        ArrayList<String> allergies= new ArrayList<>();
        medications.add("Lucky Luke");
        medications.add("Jail");
        medicalrecordToUpdate.setAllergies(allergies);


        // ACT
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.update(medicalrecordToUpdate);

        // ASSERT
        assertFalse(result);
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertFalse(this.medicalrecords.contains(medicalrecordToUpdate));
        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore);
    }

    //*********************************************************************************************************
//  Tests unitaires de la méthode 'delete' de la classe  MedicalrecordDao
//*********************************************************************************************************
    @Test
    void testDeleteAnExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToDelete = new Medicalrecord();
        medicalrecordToDelete.setFirstName("Sophia");
        medicalrecordToDelete.setLastName("Zemicks");
        medicalrecordToDelete.setBirthdate("03/06/1988");

        ArrayList<String> medications= new ArrayList<>();
        medications.add("aznol:60mg");
        medications.add("hydrapermazol:900mg");
        medications.add("pharmacol:5000mg");
        medications.add("terazine:500mg");
        medicalrecordToDelete.setMedications(medications);

        ArrayList<String> allergies= new ArrayList<>();
        allergies.add("peanut");
        allergies.add("shellfish");
        allergies.add("aznol");
        medicalrecordToDelete.setAllergies(allergies);

        // ACT
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.delete(medicalrecordToDelete.getFirstName(), medicalrecordToDelete.getLastName());

        // ASSERT
        assertTrue(result);
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertFalse(medicalrecords.contains(medicalrecordToDelete));
        // On vérifie qu'il n'y a eu uniquement qu'une suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore -1);
    }

    @Test
    void testDeleteANonExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToDelete = new Medicalrecord();
        medicalrecordToDelete.setFirstName("Averell");
        medicalrecordToDelete.setLastName("Dalton");

        // ACT
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.delete(medicalrecordToDelete.getFirstName(), medicalrecordToDelete.getLastName());

        // ASSERT
        assertFalse(result);
        medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertFalse(medicalrecords.contains(medicalrecordToDelete));
        // On vérifie qu'il n'y a pas eu de suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore);
    }
}

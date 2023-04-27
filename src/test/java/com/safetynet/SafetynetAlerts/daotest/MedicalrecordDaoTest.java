package com.safetynet.safetynetalerts.daotest;

import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.JsonFileIO;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class MedicalrecordDaoTest {

    private List<Medicalrecord> medicalrecords;

    private static Logger logger = LoggerFactory.getLogger(MedicalrecordDaoTest.class);

    @BeforeAll
    static void startingCode(){
        logger.debug("*** TESTS UNITAIRES ***  des methodes DAO de la classe MedicalrecordDaoImpl");
    }


    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JsonFileIO(JSONFILE_TEST_PATHNAME);
    }


    @Autowired
    private MedicalrecordDao medicalrecordDaoImpl;


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'findBy' de la classe  MedicalrecordDao
    //*********************************************************************************************************
    @Test
    void testFindAnExistingMedicalrecordDao() {
        // ARRANGE, ACT
        Medicalrecord medicalrecord = medicalrecordDaoImpl.findByName("Felicia", "Boyd");

        // ASSERT
        assertNotNull(medicalrecord);
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertTrue(medicalrecords.contains(medicalrecord));
    }

    @Test
    void testFindANonExistingPerson() {
        // ARRANGE, ACT
        Medicalrecord medicalrecord = medicalrecordDaoImpl.findByName("Averell", "Dalton");

        // ASSERT
        assertNull(medicalrecord);
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertFalse(medicalrecords.contains(medicalrecord));
    }


    //*********************************************************************************************************
    //  Tests unitaires de la méthode 'save' de la classe  MedicalrecordDao
    //*********************************************************************************************************
    @Test
    void testSaveNewPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToAdd = new Medicalrecord(
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

        // ACT
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.save(medicalrecordToAdd);

        // ASSERT
        assertTrue(result);
        // On vérifie que l'ajout a été fait
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertTrue(this.medicalrecords.contains(medicalrecordToAdd));
        // On vérifie qu'il n'y a eu qu'un ajout (pas d'ajout multiple)
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore +1 );
    }


    @Test
    void testSaveAnExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToAdd = new Medicalrecord(
                "Sophia",
                "Zemicks",
                "03/06/1988",
                new ArrayList<>(Arrays.asList(
                        "aznol:60mg",
                        "hydrapermazol:900mg",
                        "pharmacol:5000mg",
                        "terazine:500mg")),
                new ArrayList<>(Arrays.asList(
                        "peanut",
                        "shellfish",
                        "aznol")));


        // ACT
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
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
        Medicalrecord medicalrecordToUpdate = new Medicalrecord(
                "Sophia",
                "Zemicks",
                "03/06/1988",
                new ArrayList<>(Arrays.asList(
                        "aznol:60mg",
                        "hydrapermazol:900mg",
                        "pharmacol:5000mg",
                        "terazine:500mg")),
                new ArrayList<>(Arrays.asList(
                        "peanut",
                        "shellfish",
                        "aznol")));

        // ACT
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.update(medicalrecordToUpdate);

        // ASSERT
        assertTrue(result);
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertTrue(this.medicalrecords.contains(medicalrecordToUpdate));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore);
    }


    @Test
    void testUpdateANonExistingPerson() {
        // ARRANGE
        Medicalrecord medicalrecordToUpdate = new Medicalrecord(
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


        // ACT
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.update(medicalrecordToUpdate);

        // ASSERT
        assertFalse(result);
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
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
        Medicalrecord medicalrecordToDelete = new Medicalrecord(
                "Sophia",
                "Zemicks",
                "03/06/1988",
                new ArrayList<>(Arrays.asList(
                        "aznol:60mg",
                        "hydrapermazol:900mg",
                        "pharmacol:5000mg",
                        "terazine:500mg")),
                new ArrayList<>(Arrays.asList(
                        "peanut",
                        "shellfish",
                        "aznol")));


        // ACT
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.delete(medicalrecordToDelete.getFirstName(), medicalrecordToDelete.getLastName());

        // ASSERT
        assertTrue(result);
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
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
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        int nbMedicalrecordsBefore = medicalrecords.size();
        Boolean result = medicalrecordDaoImpl.delete(medicalrecordToDelete.getFirstName(), medicalrecordToDelete.getLastName());

        // ASSERT
        assertFalse(result);
        medicalrecords = JsonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        assertFalse(medicalrecords.contains(medicalrecordToDelete));
        // On vérifie qu'il n'y a pas eu de suppression
        int nbMedicalrecordsAfter = medicalrecords.size();
        assertEquals(nbMedicalrecordsAfter, nbMedicalrecordsBefore);
    }
}

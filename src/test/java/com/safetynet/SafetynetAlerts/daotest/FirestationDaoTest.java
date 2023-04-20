package com.safetynet.safetynetalerts.daotest;

import com.safetynet.safetynetalerts.datautility.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class FirestationDaoTest {
        private List<Firestation> firestations;

        private static Logger logger = LoggerFactory.getLogger(FirestationDaoTest.class);

        @BeforeAll
        static void startingCode(){
            logger.debug("*** TESTS UNITAIRES ***  des methodes DAO de la classe FirestationDaoImpl");
        }

        @BeforeEach
        void setUpData(){
            SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
            new JasonFileIO(JSONFILE_TEST_PATHNAME);
        }


        @Autowired
        private FirestationDao firestationDaoImpl;


//*********************************************************************************************************
//  Tests unitaires de la méthode 'findByAdress' de la classe  FirestationDaoImpl
//*********************************************************************************************************
    @Test
    void testFindAnExistingAdress() {
        // ARRANGE, ACT
        Firestation firestation = firestationDaoImpl.findByAddress("892 Downing Ct");

        // ASSERT
        assertNotNull(firestation);
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertTrue(this.firestations.contains(firestation));
    }

    @Test
    void testFindANonExistingAdress() {
        // ARRANGE, ACT
        Firestation firestation = firestationDaoImpl.findByAddress("Nowhere");

        // ASSERT
        assertNull(firestation);
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertFalse(this.firestations.contains(firestation));
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'findByStation' de la classe  FirestationDaoImpl
//*********************************************************************************************************
    @Test
    void testFindAnExistingStation() {
        // ARRANGE
        final int STATION_TO_FIND = 1;

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        List<Firestation> firestationSelected = new ArrayList<>();
        for (Firestation firestation : firestations){
            if (firestation.getStation() == STATION_TO_FIND ){
                firestationSelected.add(firestation);
            }
        }

        // ACT
        List<Firestation> firestationsFound = firestationDaoImpl.findByStation(STATION_TO_FIND);

        // ASSERT
        assertEquals(firestationsFound.size(), firestationSelected.size());
        for (Firestation firestation : firestationsFound){
            assertTrue(this.firestations.contains(firestation));
        }
    }

    @Test
    void testFindANonExistingStation() {
        // ARRANGE, ACT
        List<Firestation> firestationsFound  = firestationDaoImpl.findByStation(10);

        // ASSERT
        assertTrue(firestationsFound.isEmpty());
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'save' de la classe  FirestationDaoImpl
//*********************************************************************************************************

    @Test
    void testSaveNewFirestation() {
        // ARRANGE
        Firestation firestationToAdd = new Firestation();
        firestationToAdd.setAddress("Here St");
        firestationToAdd.setStation(11);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();

        // ACT
        Boolean result = firestationDaoImpl.save(firestationToAdd);

        // ASSERT
        assertTrue(result);
        // On vérifie que l'ajout a été fait
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertTrue(this.firestations.contains(firestationToAdd));
        // On vérifie qu'il n'y a eu qu'un ajout (pas d'ajout multiple)
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore +1 );
    }


    @Test
    void testSaveAnExistingFirestation() {
        // ARRANGE
        Firestation firestationToAdd = new Firestation();
        firestationToAdd.setAddress("29 15th St");
        firestationToAdd.setStation(2);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();

        // ACT
        Boolean result = firestationDaoImpl.save(firestationToAdd);

        // ASSERT
        assertFalse(result);
        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore );
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'update' de la classe  FirestationDaoImpl
//*********************************************************************************************************

    @Test
    void testUpdateAnExistingFirestation() {
        // ARRANGE
        Firestation firestationToUpdate = new Firestation();
        firestationToUpdate.setAddress("29 15th St");
        firestationToUpdate.setStation(2);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();

        // ACT
        Boolean result = firestationDaoImpl.update(firestationToUpdate);

        // ASSERT
        assertTrue(result);
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertTrue(firestations.contains(firestationToUpdate));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore);
    }

    @Test
    void testUpdateANonExistingFirestation() {
        // ARRANGE
        Firestation firestationToUpdate = new Firestation();
        firestationToUpdate.setAddress("nowhere");
        firestationToUpdate.setStation(0);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();

        // ACT
        Boolean result = firestationDaoImpl.update(firestationToUpdate);

        // ASSERT
        assertFalse(result);
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertFalse(firestations.contains(firestationToUpdate));

        // On vérifie qu'il n'y a pas eu d'ajout ou de suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore);
    }



//*********************************************************************************************************
//  Tests unitaires des méthodes 'delete' de la classe  FirestationDaoImpl
//*********************************************************************************************************
    @Test
    void testDeleteAnExistingStation() {
        // ARRANGE
        final int STATION_TO_DELETE = 1;
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();

        int nbStationToDelete = 0;
        for (Firestation firestation : firestations){
            if (firestation.getStation() == STATION_TO_DELETE ){
                nbStationToDelete++;
            }
        }

        // ACT
        Boolean result = firestationDaoImpl.deleteStation(STATION_TO_DELETE);

        // ASSERT
        assertTrue(result);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        boolean stationExist = false;
        for (Firestation firestation : firestations){
            if (firestation.getStation() == STATION_TO_DELETE ){
                stationExist = true;
                break;
            }
        }
        assertFalse(stationExist);

        // On vérifie qu'il n'y a eu uniquement qu'une suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore -nbStationToDelete);
    }

    @Test
    void testDeleteANonExistingStation() {
        // ARRANGE
        final int STATION_TO_DELETE = 10;
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();

        // ACT
        Boolean result = firestationDaoImpl.deleteStation(STATION_TO_DELETE);

        // ASSERT
        assertFalse(result);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        boolean stationExist = false;
        for (Firestation firestation : firestations){
            if (firestation.getStation() == STATION_TO_DELETE ){
                stationExist = true;
            }
        }
        assertFalse(stationExist);

        // On vérifie qu'il n'y a pas eu de suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore);
    }


    @Test
    void testDeleteAnExistingAdress() {
        // ARRANGE
        Firestation firestationToDelete = new Firestation();
        firestationToDelete.setAddress("29 15th St");
        firestationToDelete.setStation(2);

        // ACT
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();
        Boolean result = firestationDaoImpl.deleteAddress(firestationToDelete.getAddress());

        // ASSERT
        assertTrue(result);
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertFalse(firestations.contains(firestationToDelete));

        // On vérifie qu'il n'y a eu uniquement qu'une suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore -1);
    }

    @Test
    void testDeleteANonExistingAdress() {
        // ARRANGE
        Firestation firestationToDelete = new Firestation();
        firestationToDelete.setAddress("Nowhere");
        firestationToDelete.setStation(100);

        // ACT
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        int nbFirestationsBefore = firestations.size();
        Boolean result = firestationDaoImpl.deleteAddress(firestationToDelete.getAddress());

        // ASSERT
        assertFalse(result);

        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertFalse(firestations.contains(firestationToDelete));

        // On vérifie qu'il n'y a pas eu de suppression
        int nbFirestationsAfter = firestations.size();
        assertEquals(nbFirestationsAfter, nbFirestationsBefore);
    }

}

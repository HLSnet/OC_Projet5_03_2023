package com.safetynet.safetynetalerts.firestation;

import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
public class FirestationTest {
        private List<Firestation> firestations;


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
        Firestation firestation = firestationDaoImpl.findByAdress("892 Downing Ct");

        // ASSERT
        assertNotNull(firestation);
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        assertTrue(this.firestations.contains(firestation));
    }

    @Test
    void testFindANonExistingAdress() {
        // ARRANGE, ACT
        Firestation firestation = firestationDaoImpl.findByAdress("8920 Downing Ct");

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
        firestations = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        List<Firestation> firestationSelected = new ArrayList<>();
        for (Firestation firestation : firestations){
            if (firestation.getStation() == 1 ){
                firestationSelected.add(firestation);
            }
        }

        // ACT
        List<Firestation> firestationsFound = firestationDaoImpl.findByStation(1);

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



//*********************************************************************************************************
//  Tests unitaires de la méthode 'update' de la classe  FirestationDaoImpl
//*********************************************************************************************************




//*********************************************************************************************************
//  Tests unitaires de la méthode 'delete' de la classe  PersonDaoImpl
//*********************************************************************************************************




}

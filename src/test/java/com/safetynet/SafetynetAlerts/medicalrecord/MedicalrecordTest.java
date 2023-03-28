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


}

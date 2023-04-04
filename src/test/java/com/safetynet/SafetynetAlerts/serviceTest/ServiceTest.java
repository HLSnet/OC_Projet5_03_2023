package com.safetynet.safetynetalerts.serviceTest;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.datatest.SetupJsonFile;
import com.safetynet.safetynetalerts.dto.PersonDto;
import com.safetynet.safetynetalerts.repository.JasonFileIO;
import com.safetynet.safetynetalerts.service.AlertService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_BAK_PATHNAME;
import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_TEST_PATHNAME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@SpringBootTest
public class ServiceTest {
    @Autowired
    private AlertService alertService;

    @BeforeEach
    void setUpData(){
        SetupJsonFile.reloadTestFile(JSONFILE_TEST_BAK_PATHNAME , JSONFILE_TEST_PATHNAME);
        new JasonFileIO(JSONFILE_TEST_PATHNAME);
        FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
        JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));
    }


//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPersonsRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
    @Test
    void testFindAnExistingStation() {
        // ARRANGE
        int stationNumber = 3;

        // ACT
        PersonDto personDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertEquals(personDto.getPersons().get(0).getFirstName(), "Tessa");
        assertEquals(personDto.getPersons().get(0).getLastName(), "Carman");
        assertEquals(personDto.getPersons().get(0).getAddress(), "834 Binoc Ave");
        assertEquals(personDto.getPersons().get(0).getPhone(), "841-874-6512");
        assertEquals(personDto.getNbAdult(), 0);
        assertEquals(personDto.getNbChild(), 1);
    }

    @Test
    void testFindANoneExistingStation() {
        // ARRANGE
        int stationNumber = 30;

        // ACT
        PersonDto personDto = alertService.getPersonsRelatedToAStation(stationNumber);

        // ASSERT
        assertNull(personDto);
    }

//*********************************************************************************************************
//  Tests unitaires de la méthode 'getChildsdRelatedToAnAddress' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test1() {
    // ARRANGE


    // ACT



    // ASSERT

}


//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPhoneRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test2() {
    // ARRANGE


    // ACT



    // ASSERT

}



//*********************************************************************************************************
//  Tests unitaires de la méthode 'getPersonsRelatedToAnAddress' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test3() {
    // ARRANGE


    // ACT



    // ASSERT

}



//*********************************************************************************************************
//  Tests unitaires de la méthode 'getHouseRelatedToAStation' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test4() {
    // ARRANGE


    // ACT



    // ASSERT

}



//*********************************************************************************************************
//  Tests unitaires de la méthode 'getInfoPerson' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test5() {
    // ARRANGE


    // ACT



    // ASSERT

}




//*********************************************************************************************************
//  Tests unitaires de la méthode 'getMailRelatedToACity' de la classe  AlertServiceImpl
//*********************************************************************************************************
@Test
void test61() {
    // ARRANGE


    // ACT



    // ASSERT

}



}

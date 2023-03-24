package com.safetynet.SafetynetAlerts;

import com.safetynet.SafetynetAlerts.model.Person;
import com.safetynet.SafetynetAlerts.utilities.JasonFileIO;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.safetynet.SafetynetAlerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SafetynetAlertsApplicationTests {

	@BeforeEach
	void setUpData(){
		SetupFileTest.initializeFileTest();
		new JasonFileIO(JSONFILE_TEST_PATHNAME);
	}


	@Test
	void contextLoads() {
	}

	@Test
	void testReadFromJsonFileToList(){
		// act
		List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);

		//Assert
		assertEquals(persons.get(2).getLastName(),"Peters");
	}

	@Test
	void testWriteListToJsonFile(){
		// Arrange
		List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
		persons.remove(1);

		//Act
		JasonFileIO.writeListToJsonFile(PERSON, persons);
		persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);

		//Assert
		assertEquals(persons.get(2).getLastName(),"Stelzer");
	}







//
//	@Tag("TestPersonDao")
//	@Test
//	void addAPersonInTheList(){
//		Person person = new Person();
//		PersonDaoImpl personDaoImpl = new PersonDaoImpl();
//
//		//Arrange
//		person.setFirstName("Shawna");
//		person.setLastName("Stelzer");
//		person.setAddress("1 here St");
//		person.setCity("Nowhere");
//		person.setZip("00000");
//		person.setPhone("123-345-6789");
//		person.setEmail("ssanw_new@email.com");
//
//	    //Act
//		personDaoImpl.save(person);
//
//		//Assert
//
//
//	}

}

package com.safetynet.SafetynetAlerts;

import com.safetynet.SafetynetAlerts.model.Person;
import com.safetynet.SafetynetAlerts.repository.JasonFileIO;
import com.safetynet.SafetynetAlerts.serviceDao.PersonDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.safetynet.SafetynetAlerts.constants.DBConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SafetynetAlertsApplicationTests {

	@BeforeEach
	void setUpData(){
		SetupJsonFileTest.initialisation();
		new JasonFileIO(JSONFILE_TEST_PATHNAME);
	}


//	@Test
//	void contextLoads() {
//	}

	@Test
	void testReadFromJsonFileToList(){
		// Arrange, Act
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

	@Autowired
	private PersonDaoImpl personDaoImpl;

	@Test
	void testSaveNewPerson() {
		// Arrange
		Person personToAdd = new Person();
		personToAdd.setFirstName("Averell");
		personToAdd.setLastName("Dalton");
		personToAdd.setAddress("19 Saloon St");
		personToAdd.setCity("Daisy town");
		personToAdd.setZip("00000");
		personToAdd.setPhone("111-222-3333");
		personToAdd.setEmail("a.dalton@jail.com");

		// Act
		Person result = personDaoImpl.save(personToAdd);

		// Assert
		assertNotNull(result);
		assertEquals(personToAdd.getFirstName(), result.getFirstName());
		assertEquals(personToAdd.getLastName(), result.getLastName());
	}


	@Test
	void testSaveAnExistingPerson() {
		// Arrange
		Person personToAdd = new Person();

		personToAdd.setFirstName("Jamie");
		personToAdd.setLastName("Peters");
//		personToAdd.setAddress("908 73rd St");
//		personToAdd.setCity("Culver");
//		personToAdd.setZip("97451");
//		personToAdd.setPhone("841-874-7462");
//		personToAdd.setEmail("jpeter@email.com");

		// Act
		Person result = personDaoImpl.save(personToAdd);

		// Assert
		assertNull(result);
	}



	@Test
	void testUpdateAnExistingPerson() {
		// Arrange
		Person personToModify = new Person();

		personToModify.setFirstName("Jamie");
		personToModify.setLastName("Peters");
		personToModify.setAddress("1 here St");
		personToModify.setCity("Nowhere");
		personToModify.setZip("00000");
		personToModify.setPhone("012-345-6789");
		personToModify.setEmail("jpeter.new@email.com");

		// Act
		Person result = personDaoImpl.update(personToModify);

		// Assert
		assertNotNull(result);
		assertEquals(result.getFirstName(), personToModify.getFirstName());
		assertEquals(result.getLastName(), personToModify.getLastName());
		assertEquals(result.getAddress(), "1 here St");
		assertEquals(result.getCity(), "Nowhere");
		assertEquals(result.getZip(), "00000");
		assertEquals(result.getPhone(), "012-345-6789");
		assertEquals(result.getEmail(), "jpeter.new@email.com");
	}


	@Test
	void testUpdateANonExistingPerson() {
		// Arrange
		Person personToModify = new Person();

		personToModify.setFirstName("Averell");
		personToModify.setLastName("Dalton");
		personToModify.setAddress("19 Saloon St");
		personToModify.setCity("Daisy town");
		personToModify.setZip("00000");
		personToModify.setPhone("111-222-3333");
		personToModify.setEmail("a.dalton@jail.com");

		// Act
		Person result = personDaoImpl.update(personToModify);

		// Assert
		assertNull(result);
	}



	@Test
	void testDeleteAnExistingPerson() {
		// Arrange, Act
		Person result = personDaoImpl.delete("Jamie", "Peters");

		// Assert
		assertNotNull(result);
		assertEquals(result.getFirstName(), "Jamie");
		assertEquals(result.getLastName(), "Peters");
		assertEquals(result.getAddress(), "908 73rd St");
		assertEquals(result.getCity(), "Culver");
		assertEquals(result.getZip(), "97451");
		assertEquals(result.getPhone(), "841-874-7462");
		assertEquals(result.getEmail(), "jpeter@email.com");
	}

	@Test
	void testDeleteANonExistingPerson() {
		// Arrange, Act
		Person result = personDaoImpl.delete("Averell", "Dalton");

		// Assert
		assertNull(result);
	}

	@Test
	void testFindAnExistingPerson() {
		// Arrange, Act
		Person result = personDaoImpl.findByName("Jamie", "Peters");

		// Assert
		assertNotNull(result);
		assertEquals(result.getFirstName(), "Jamie");
		assertEquals(result.getLastName(), "Peters");
		assertEquals(result.getAddress(), "908 73rd St");
		assertEquals(result.getCity(), "Culver");
		assertEquals(result.getZip(), "97451");
		assertEquals(result.getPhone(), "841-874-7462");
		assertEquals(result.getEmail(), "jpeter@email.com");
	}

	@Test
	void testFindANonExistingPerson() {
		// Arrange, Act
		Person result = personDaoImpl.findByName("Averell", "Dalton");

		// Assert
		assertNull(result);
	}




}

package com.safetynet.SafetynetAlerts;

import com.safetynet.SafetynetAlerts.web.dao.PersonDaoImpl;
import com.safetynet.SafetynetAlerts.web.model.Person;
import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SafetynetAlertsApplicationTests {


	// On crée les listes d'objet parsées à partir du fichier json pour les tests
	public static DataManager dataManagerTest;
	@Test
	void contextLoads() {
		dataManagerTest = new DataManager();
	}

	@Tag("TestPersonDao")
	@Test
	void addAPersonInTheList(){
		Person person = new Person();
		PersonDaoImpl personDaoImpl = new PersonDaoImpl();

		//Arrange
		person.setFirstName("Shawna");
		person.setLastName("Stelzer");
		person.setAddress("1 here St");
		person.setCity("Nowhere");
		person.setZip("00000");
		person.setPhone("123-345-6789");
		person.setEmail("ssanw_new@email.com");

	    //Act
		personDaoImpl.save(person);

		//Assert


	}



}

package com.safetynet.SafetynetAlerts.web.dao;


import com.safetynet.SafetynetAlerts.web.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.SafetynetAlerts.SafetynetAlertsApplication.dataManager;
import static com.safetynet.SafetynetAlerts.web.constants.DBConstants.PERSON;

@Repository
public class PersonDaoImpl implements PersonDao{


    @Override
    public List<Person> findAll() {
        Object person;
        return dataManager.readFromJsonFileToList(PERSON, Person.class);
    }

    @Override
    public void save(Person person) {
        List<Person> persons = dataManager.readFromJsonFileToList(PERSON, Person.class);
        persons.add(person);
        dataManager.writeListToJsonFile(PERSON, persons);
    }

    @Override
    public Person update(Person personToAdd) {
        List<Person> persons = dataManager.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(personToAdd.getFirstName())  && person.getLastName().equals(personToAdd.getLastName())){
                persons.remove(person);
                persons.add(personToAdd);
                dataManager.writeListToJsonFile(PERSON, persons);
                return  personToAdd;
            }
        }
        return null;
    }

    @Override
    public Person delete(String firstName, String lastName) {
        List<Person> persons = dataManager.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(firstName)  && person.getLastName().equals(lastName)){
                persons.remove(person);
                dataManager.writeListToJsonFile(PERSON, persons);
                return  person;
            }
        }
        return null;
    }
}

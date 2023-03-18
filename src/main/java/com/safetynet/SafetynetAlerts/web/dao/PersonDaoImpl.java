package com.safetynet.SafetynetAlerts.web.dao;


import com.safetynet.SafetynetAlerts.web.model.Person;
import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.SafetynetAlerts.SafetynetAlertsApplication.dataManager;

@Repository
public class PersonDaoImpl implements PersonDao{


    @Override
    public List<Person> findAll() {
        Object person;
        return dataManager.readFromJsonFileToList("persons", Person.class);
    }

    @Override
    public Person save(Person person) {
        dataManager.persons.add(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        for (Person personDb : dataManager.persons){
            if (personDb.getFirstName().equals(person.getFirstName())  && personDb.getLastName().equals(personDb.getLastName())){
                dataManager.persons.remove(personDb);
                dataManager.persons.add(person);
                return  person;
            }
        }
        return null;
    }

    @Override
    public Person delete(String firstName, String lastName) {
        for (Person person : dataManager.persons){
            if (person.getFirstName().equals(firstName)  && person.getLastName().equals(lastName)){
                dataManager.persons.remove(person);
                return  person;
            }
        }
        return null;
    }
}

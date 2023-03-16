package com.safetynet.SafetynetAlerts.web.dao;


import com.safetynet.SafetynetAlerts.web.model.Person;
import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PersonDaoImpl implements PersonDao{


    @Override
    public List<Person> findAll() {
        return DataManager.persons;
    }

    @Override
    public Person save(Person person) {
        DataManager.persons.add(person);
        return person;
    }

    @Override
    public Person update(Person person) {
        for (Person personDb : DataManager.persons){
            if (personDb.getFirstName().equals(person.getFirstName())  && personDb.getLastName().equals(personDb.getLastName())){
                DataManager.persons.remove(personDb);
                DataManager.persons.add(person);
                return  person;
            }
        }
        return null;
    }

    @Override
    public Person delete(String firstName, String lastName) {
        for (Person person : DataManager.persons){
            if (person.getFirstName().equals(firstName)  && person.getLastName().equals(lastName)){
                DataManager.persons.remove(person);
                return  person;
            }
        }
        return null;
    }
}

package com.safetynet.SafetynetAlerts.dao;


import com.safetynet.SafetynetAlerts.model.Person;
import com.safetynet.SafetynetAlerts.utilities.JasonFileIO;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.SafetynetAlerts.constants.DBConstants.PERSON;

@Repository
public class PersonDaoImpl implements PersonDao{


    @Override
    public List<Person> findAll() {
        Object person;
        return JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
    }

    @Override
    public void save(Person person) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        persons.add(person);
        JasonFileIO.writeListToJsonFile(PERSON, persons);
    }

    @Override
    public Person update(Person personToAdd) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(personToAdd.getFirstName())  && person.getLastName().equals(personToAdd.getLastName())){
                persons.remove(person);
                persons.add(personToAdd);
                JasonFileIO.writeListToJsonFile(PERSON, persons);
                return  personToAdd;
            }
        }
        return null;
    }

    @Override
    public Person delete(String firstName, String lastName) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(firstName)  && person.getLastName().equals(lastName)){
                persons.remove(person);
                JasonFileIO.writeListToJsonFile(PERSON, persons);
                return  person;
            }
        }
        return null;
    }
}

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
        return JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
    }

    @Override
    public Person findByName(String firstName, String lastName) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(firstName)  && person.getLastName().equals(lastName)){
                return  person;
            }
        }
        return null;
    }


    @Override
    public Person save(Person personToAdd) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(personToAdd.getFirstName())  && person.getLastName().equals(personToAdd.getLastName())){
                // La personne existe dans le fichier donc pas d'ajout
                return  null;
            }
        }
        persons.add(personToAdd);
        JasonFileIO.writeListToJsonFile(PERSON, persons);
        return personToAdd;
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

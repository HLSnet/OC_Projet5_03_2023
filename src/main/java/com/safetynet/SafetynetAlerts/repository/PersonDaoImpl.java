package com.safetynet.safetynetalerts.repository;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;


@Repository
public class PersonDaoImpl implements PersonDao {


    @Override
    public List<Person> findAll() {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        return persons;
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
    public Boolean save(Person personToAdd) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(personToAdd.getFirstName())  && person.getLastName().equals(personToAdd.getLastName())){
                // La personne existe dans le fichier donc pas d'ajout
                return  NOT_ADDED;
            }
        }
        persons.add(personToAdd);

        FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
        JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));

        JasonFileIO.writeListToJsonFile(PERSON, persons);
        return ADDED;
    }


    @Override
    public Boolean update(Person personToUpdate) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(personToUpdate.getFirstName())  && person.getLastName().equals(personToUpdate.getLastName())){
                persons.remove(person);
                persons.add(personToUpdate);

                FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
                JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));

                JasonFileIO.writeListToJsonFile(PERSON, persons);
                return  UPDATE_COMPLETED;
            }
        }
        return NO_UPDATE;
    }

    @Override
    public Boolean delete(String firstName, String lastName) {
        List<Person> persons = JasonFileIO.readFromJsonFileToList(PERSON, Person.class);
        for (Person person : persons){
            if (person.getFirstName().equals(firstName)  && person.getLastName().equals(lastName)){
                persons.remove(person);

                FilterProvider filters = new SimpleFilterProvider().addFilter("filtreDynamique", SimpleBeanPropertyFilter.serializeAll());
                JasonFileIO.setMapper(JasonFileIO.getMapper().setFilterProvider(filters));

                JasonFileIO.writeListToJsonFile(PERSON, persons);
                return  DELETION_COMPLETED;
            }
        }
        return NO_DELETION;
    }
}



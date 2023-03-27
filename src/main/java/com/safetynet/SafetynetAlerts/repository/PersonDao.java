package com.safetynet.safetynetalerts.repository;


import com.safetynet.safetynetalerts.model.Person;

import java.util.List;


public interface PersonDao {

    List<Person> findAll();

    Person findByName(String firstName, String lastName);

    Boolean save(Person person);

    Boolean update(Person person);

    Boolean delete(String firstName, String lastName);

}

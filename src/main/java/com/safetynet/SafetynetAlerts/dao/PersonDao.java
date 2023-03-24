package com.safetynet.SafetynetAlerts.dao;


import com.safetynet.SafetynetAlerts.model.Person;

import java.util.List;


public interface PersonDao {

    List<Person> findAll();

    Person findByName(String firstName, String lastName);

    Person save(Person person);

    Person update(Person person);

    Person delete(String firstName, String lastName);

}

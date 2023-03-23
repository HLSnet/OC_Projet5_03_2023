package com.safetynet.SafetynetAlerts.dao;


import com.safetynet.SafetynetAlerts.model.Person;

import java.util.List;


public interface PersonDao {

    List<Person> findAll();

    void save(Person person);

    Person update(Person person);

    Person delete(String firstName, String lastName);

}

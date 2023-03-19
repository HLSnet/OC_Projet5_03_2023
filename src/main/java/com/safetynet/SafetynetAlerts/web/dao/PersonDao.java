package com.safetynet.SafetynetAlerts.web.dao;


import com.safetynet.SafetynetAlerts.web.model.Person;

import java.util.List;


public interface PersonDao {

    List<Person> findAll();

    void save(Person person);

    Person update(Person person);

    Person delete(String firstName, String lastName);

}

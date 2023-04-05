package com.safetynet.safetynetalerts.dto;

import com.safetynet.safetynetalerts.model.Person;

import java.util.ArrayList;
import java.util.List;


public class PersonsGivenStationDto {

    private List<Person> persons = new ArrayList<>();
    private int nbAdult = 0;
    private int nbChild = 0;

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    public int getNbAdult() {
        return nbAdult;
    }

    public void setNbAdult(int nbAdult) {
        this.nbAdult = nbAdult;
    }

    public int getNbChild() {
        return nbChild;
    }

    public void setNbChild(int nbChild) {
        this.nbChild = nbChild;
    }

}

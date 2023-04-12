package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;


public class FirestationDto {

    private List<FirestationPersonDto> persons = new ArrayList<>();
    private int nbAdult = 0;
    private int nbChild = 0;

    public FirestationDto(List<FirestationPersonDto> persons, int nbAdult, int nbChild) {
        this.persons = persons;
        this.nbAdult = nbAdult;
        this.nbChild = nbChild;
    }

    public FirestationDto() {
    }

    public List<FirestationPersonDto> getPersons() {
        List<FirestationPersonDto> persons = new ArrayList<>(this.persons);
        return persons;
    }

    public void setPersons(List<FirestationPersonDto> persons) {
        List<FirestationPersonDto> personsToSet = new ArrayList<>(persons);
        this.persons = personsToSet;
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

    @Override
    public String toString() {
        return "FirestationDto{" +
                "persons=" + persons +
                ", nbAdult=" + nbAdult +
                ", nbChild=" + nbChild +
                '}';
    }
}

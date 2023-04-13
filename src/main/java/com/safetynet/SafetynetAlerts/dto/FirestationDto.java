package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;


public class FirestationDto {

    private List<FirestationPersonDto> persons = new ArrayList<>();
    private int nbAdult = 0;
    private int nbChild = 0;

    public FirestationDto(List<FirestationPersonDto> persons, int nbAdult, int nbChild) {
        this.persons = new ArrayList<>(persons);
        this.nbAdult = nbAdult;
        this.nbChild = nbChild;
    }

    public FirestationDto() {
    }

    public List<FirestationPersonDto> getPersons() {
               return new ArrayList<>(this.persons);
    }

    public void setPersons(List<FirestationPersonDto> persons) {
        this.persons = new ArrayList<>(persons);
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

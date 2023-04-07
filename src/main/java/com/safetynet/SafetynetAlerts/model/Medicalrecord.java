package com.safetynet.safetynetalerts.model;


import java.util.ArrayList;
import java.util.Objects;

public class Medicalrecord{
    private String firstName;
    private String lastName;
    private String birthdate;
    private ArrayList<String> medications;
    private ArrayList<String> allergies;

    public Medicalrecord() {
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }


    public ArrayList<String> getMedications() {
        ArrayList<String> medications = new ArrayList<>(this.medications);
        return medications;
    }

    public void setMedications(ArrayList<String> medications) {
        ArrayList<String> medicationsToSet = new ArrayList<>(medications);
        this.medications = medicationsToSet;
    }

    public ArrayList<String> getAllergies() {
        ArrayList<String> allergies = new ArrayList<>(this.allergies);
        return allergies;
    }

    public void setAllergies(ArrayList<String> allergies) {
        ArrayList<String> allergiesToSet = new ArrayList<>(allergies);
        this.allergies = allergiesToSet;
    }


    @Override
    public String toString() {
        return "Medicalrecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medications=" + medications +
                ", allergies=" + allergies +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medicalrecord that = (Medicalrecord) o;
        return Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(birthdate, that.birthdate) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, birthdate, medications, allergies);
    }
}

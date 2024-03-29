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

    public Medicalrecord(String firstName, String lastName, String birthdate, ArrayList<String> medications, ArrayList<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.medications = new ArrayList<>(medications);
        this.allergies = new ArrayList<>(allergies);
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
        return new ArrayList<>(this.medications);
    }

    public void setMedications(ArrayList<String> medications) {
        this.medications = new ArrayList<>(medications);
    }

    public ArrayList<String> getAllergies() {
         return new ArrayList<>(this.allergies);
    }

    public void setAllergies(ArrayList<String> allergies) {
        this.allergies = new ArrayList<>(allergies);
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

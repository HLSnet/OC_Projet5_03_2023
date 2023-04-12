package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.Objects;

public class InfoPersonDto {

    private String lastName;
    private String address;
    private int age;
    private String email;
    private ArrayList<String> medications;
    private ArrayList<String> allergies;

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getMedications() {
        return medications;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoPersonDto that = (InfoPersonDto) o;
        return age == that.age && Objects.equals(lastName, that.lastName) && Objects.equals(address, that.address) && Objects.equals(email, that.email) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, address, age, email, medications, allergies);
    }

    public InfoPersonDto(String lastName, String address, int age, String email, ArrayList<String> medications, ArrayList<String> allergies) {
        this.lastName = lastName;
        this.address = address;
        this.age = age;
        this.email = email;
        this.medications = medications;
        this.allergies = allergies;
    }

    public InfoPersonDto() {
    }
}

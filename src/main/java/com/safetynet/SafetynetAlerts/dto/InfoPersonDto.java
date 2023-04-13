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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
        this.medications = new ArrayList<>(medications);
        this.allergies = new ArrayList<>(allergies);
    }

    public InfoPersonDto() {
    }
}

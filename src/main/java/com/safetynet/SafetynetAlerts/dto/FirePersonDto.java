package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.Objects;


public class FirePersonDto {
    private String firstName;
        private String lastName;
        private String phone;
        private int age;
        private String email;
        private ArrayList<String> medications;
        private ArrayList<String> allergies;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return phone;
    }

    public void setAddress(String address) {
        this.phone = address;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public int getAge() {
        return age;
    }

    public String getEmail() {
        return email;
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
        FirePersonDto that = (FirePersonDto) o;
        return age == that.age && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, phone, age, email, medications, allergies);
    }

    public FirePersonDto(String firstName, String lastName, String phone, int age, String email, ArrayList<String> medications, ArrayList<String> allergies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.age = age;
        this.email = email;
        this.medications = new ArrayList<>(medications);
        this.allergies = new ArrayList<>(allergies);
    }

    public FirePersonDto() {
    }

}

package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.Objects;


public class FirePersonDto {

        private String lastName;
        private String phone;
        private int age;
        private String email;
        private ArrayList<String> medications;
        private ArrayList<String> allergies;

    public String getPhone() {
        return phone;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FirePersonDto that = (FirePersonDto) o;
        return age == that.age && Objects.equals(lastName, that.lastName) && Objects.equals(phone, that.phone) && Objects.equals(email, that.email) && Objects.equals(medications, that.medications) && Objects.equals(allergies, that.allergies);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lastName, phone, age, email, medications, allergies);
    }
}

package com.safetynet.safetynetalerts.dto;

import com.safetynet.safetynetalerts.model.Person;

import java.util.ArrayList;
import java.util.List;

public class ChildDto {
    private String firstName;
    private String lastName;
    private int age;
    private List<Person> householdMembers = new ArrayList<>();

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Person> getHouseholdMembers() {
        return householdMembers;
    }

    public void setHouseholdMembers(List<Person> householdMembers) {
        this.householdMembers = householdMembers;
    }
}

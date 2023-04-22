package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;

public class ChildAlertDto {
    private String firstName;
    private String lastName;
    private int age;
    private List<ChildAlertPersonDto> householdMembers ;

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

    public List<ChildAlertPersonDto> getHouseholdMembers() {
        return new ArrayList<>(this.householdMembers);
    }

    public void setHouseholdMembers(List<ChildAlertPersonDto> householdMembers) {
        this.householdMembers = new ArrayList<>(householdMembers);
    }

    public ChildAlertDto(String firstName, String lastName, int age, List<ChildAlertPersonDto> householdMembers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.householdMembers = new ArrayList<>(householdMembers);
    }

    public ChildAlertDto() {
    }

    @Override
    public String toString() {
        return "ChildAlertDto{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", householdMembers=" + householdMembers +
                '}';
    }
}

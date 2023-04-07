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
        List<ChildAlertPersonDto> householdMembers = new ArrayList<>(this.householdMembers);
        return householdMembers;
    }

    public void setHouseholdMembers(List<ChildAlertPersonDto> householdMembers) {
        List<ChildAlertPersonDto> householdMembersToSet = new ArrayList<>(householdMembers);
        this.householdMembers = householdMembersToSet;
    }


}

package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FloodDto {
    private String address;
    private List<FloodPersonDto> householdMembers ;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<FloodPersonDto> getHouseholdMembers() {
        return new ArrayList<>(householdMembers);
    }

    public void setHouseholdMembers(List<FloodPersonDto> householdMembers) {
        this.householdMembers = new ArrayList<>(householdMembers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FloodDto floodDto = (FloodDto) o;
        return Objects.equals(address, floodDto.address) && Objects.equals(householdMembers, floodDto.householdMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, householdMembers);
    }
}

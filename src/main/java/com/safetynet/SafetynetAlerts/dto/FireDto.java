package com.safetynet.safetynetalerts.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FireDto {
    private int station;
    private List<FirePersonDto> householdMembers ;

    public int getStation() {
        return station;
    }

    public void setStation(int station) {
        this.station = station;
    }

    public List<FirePersonDto> getHouseholdMembers() {
        return new ArrayList<>(this.householdMembers);
    }

    public void setHouseholdMembers(List<FirePersonDto> householdMembers) {
        this.householdMembers = new ArrayList<>(householdMembers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FireDto fireDto = (FireDto) o;
        return station == fireDto.station && Objects.equals(householdMembers, fireDto.householdMembers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(station, householdMembers);
    }

    public FireDto(int station, List<FirePersonDto> householdMembers) {
        this.station = station;
        this.householdMembers = new ArrayList<>(householdMembers);
    }

    public FireDto() {
    }

    @Override
    public String toString() {
        return "FireDto{" +
                "station=" + station +
                ", householdMembers=" + householdMembers +
                '}';
    }
}

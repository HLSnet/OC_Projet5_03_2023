package com.safetynet.safetynetalerts.model;

import java.util.Objects;

public class Firestation{
    private String address;
    private String station;

    public Firestation() {
    }

    public Firestation(String address, String station) {
        this.address = address;
        this.station = station;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    @Override
    public String toString() {
        return "Firestation{" +
                "address='" + address + '\'' +
                ", station='" + station + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Firestation that = (Firestation) o;
        return Objects.equals(address, that.address) && Objects.equals(station, that.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, station);
    }
}

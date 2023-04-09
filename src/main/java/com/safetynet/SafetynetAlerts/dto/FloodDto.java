package com.safetynet.safetynetalerts.dto;

import java.util.List;
import java.util.Map;

public class FloodDto {
    Map<String, List<FloodPersonDto>> mapHousePersons;

    public Map<String, List<FloodPersonDto>> getMapHousePersons() {
        return mapHousePersons;
    }

    public void setMapHousePersons(Map<String, List<FloodPersonDto>> mapHousePersons) {
        this.mapHousePersons = mapHousePersons;
    }
}

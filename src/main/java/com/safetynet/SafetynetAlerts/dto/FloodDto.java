package com.safetynet.safetynetalerts.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FloodDto {
    Map<String, List<FloodPersonDto>> mapHousePersons;

    public FloodDto(Map<String, List<FloodPersonDto>> mapHousePersons) {
        this.mapHousePersons = new HashMap<String, List<FloodPersonDto>>(mapHousePersons);
    }

    public FloodDto() {
    }

    public Map<String, List<FloodPersonDto>> getMapHousePersons() {
        return new HashMap<String, List<FloodPersonDto>>(this.mapHousePersons);
    }

    public void setMapHousePersons(Map<String, List<FloodPersonDto>> mapHousePersons) {
        this.mapHousePersons = new HashMap<String, List<FloodPersonDto>>(mapHousePersons);
    }


}

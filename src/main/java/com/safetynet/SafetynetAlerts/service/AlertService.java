package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.*;

import java.util.List;


public interface AlertService  {

        public FirestationDto getPersonsRelatedToAStation(int stationNumber) ;

        public List<ChildAlertDto> getChildsdRelatedToAnAddress(String address);

        public List<String> getPhoneNumbersRelatedToAStation(int firestation);

        public FireDto getPersonsRelatedToAnAddress(String address);

        public FloodDto getHousesRelatedToAListOfStations(List<Integer> stations);

        public List<InfoPersonDto> getInfoPerson(String firstName, String lastName);

        public List<String> getMailsRelatedToACity(String city);
}

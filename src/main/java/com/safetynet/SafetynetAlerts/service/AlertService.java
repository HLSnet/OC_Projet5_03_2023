package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.ChildAlertDto;
import com.safetynet.safetynetalerts.dto.FirestationDto;

import java.util.List;


public interface AlertService  {

        public FirestationDto getPersonsRelatedToAStation(int stationNumber) ;

        public List<ChildAlertDto> getChildsdRelatedToAnAddress(String address);

        public List<String> getPhoneNumbersRelatedToAStation(int firestation);

        public List<Object> getPersonsRelatedToAnAddress(String address);

        public List<String> getHouseRelatedToAStation(List<Integer> stations);

        public List<Object> getInfoPerson(String firstName, String lastName);

        public List<String> getMailsRelatedToACity(String city);
}

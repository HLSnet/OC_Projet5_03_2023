package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.PersonDto;

import java.util.List;


public interface AlertService  {

        public PersonDto getPersonsRelatedToAStation(int stationNumber) ;

        public List<Object> getChildsdRelatedToAnAddress(String address);

        public List<String> getPhoneRelatedToAStation(int firestation);

        public List<Object> getPersonsRelatedToAnAddress(String address);

        public List<String> getHouseRelatedToAStation(List<Integer> stations);

        public List<Object> getInfoPerson(String firstName, String lastName);

        public List<String> getMailRelatedToACity(String city);
}

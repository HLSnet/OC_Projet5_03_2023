package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.PersonDto;

import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.util.List;


public interface AlertService  {

        public PersonDto getPersonsRelatedToAStation(@RequestParam int stationNumber) throws ParseException;

        public List<Object> getChildsdRelatedToAnAddress(@RequestParam String address);

        public List<String> getPhoneRelatedToAStation(@RequestParam int firestation);

        public List<Object> getPersonsRelatedToAnAddress(@RequestParam String address);

        public List<String> getHouseRelatedToAStation(@RequestParam List<Integer> stations);

        public List<Object> getInfoPerson(@RequestParam String firstName, @RequestParam String lastName);

        public List<String> getMailRelatedToACity(@RequestParam String city);
}

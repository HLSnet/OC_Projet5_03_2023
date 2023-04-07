package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import com.safetynet.safetynetalerts.repository.PersonDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService{
    @Autowired
    PersonDao personDao;

    @Autowired
    FirestationDao firestationDao;

    @Autowired
    MedicalrecordDao medicalrecordDao;

    public FirestationDto getPersonsRelatedToAStation(int stationNumber) {
        FirestationDto firestationDto = new FirestationDto();
        FirestationPersonDto firestationPersonDto;

        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        for (Firestation firestation: firestations) {
            for (Person person: persons){
                if (firestation.getAddress().equals(person.getAddress())){
                    firestationPersonDto= new FirestationPersonDto();
                    firestationPersonDto.setFirstName(person.getFirstName());
                    firestationPersonDto.setLastName(person.getLastName());
                    firestationPersonDto.setAddress(person.getAddress());
                    firestationPersonDto.setPhone(person.getPhone());

                    List<FirestationPersonDto> listFirestationPersonDto = firestationDto.getPersons();
                    listFirestationPersonDto.add(firestationPersonDto);
                    firestationDto.setPersons(listFirestationPersonDto);

                    for (Medicalrecord medicalrecord: medicalrecords){
                        if (medicalrecord.getFirstName().equals(person.getFirstName())  && medicalrecord.getLastName().equals(person.getLastName())){

                            if (calculateAge(medicalrecord.getBirthdate()) > 18){
                                firestationDto.setNbAdult(firestationDto.getNbAdult() + 1) ;
                            }
                            else {
                                firestationDto.setNbChild(firestationDto.getNbChild() + 1); }
                            break;
                        }
                    }
                }
            }
        }
        return firestationDto.getPersons().isEmpty()? null : firestationDto;
    }

    public List<ChildAlertDto> getChildsdRelatedToAnAddress(String address) {
        List<ChildAlertDto> childsAlertDto  = new ArrayList<>();
        ChildAlertDto childAlertDto;

        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        List<ChildAlertPersonDto> householdMembersFound = new ArrayList<>();
        ChildAlertPersonDto childAlertPersonDto;

        // On récupère toutes les personnes ayant l'adresse fournie (les membres du foyer)
        for (Person person: persons) {
            if (person.getAddress().equals(address)) {
                childAlertPersonDto = new ChildAlertPersonDto();
                childAlertPersonDto.setFirstName(person.getFirstName());
                childAlertPersonDto.setLastName(person.getLastName());
                childAlertPersonDto.setPhone(person.getPhone());
                childAlertPersonDto.setEmail(person.getEmail());
                householdMembersFound.add(childAlertPersonDto);
            }
        }

        int index = -1;
        for (ChildAlertPersonDto person: householdMembersFound) {
            index++;
            // On recherche dans medicalrecord la personne ayant le firstName et LastName recherchés
            for (Medicalrecord medicalrecord: medicalrecords){
                if (medicalrecord.getFirstName().equals(person.getFirstName())  && medicalrecord.getLastName().equals(person.getLastName())){
                    int age = calculateAge(medicalrecord.getBirthdate());
                    // Si c'est un enfant (age <= 18) on crée un objet ChildAlertDto que l'on ajoute à childsAlertDto
                    if ( age <= 18){
                        childAlertDto = new ChildAlertDto();
                        childAlertDto.setFirstName(medicalrecord.getFirstName());
                        childAlertDto.setLastName(medicalrecord.getLastName());
                        childAlertDto.setAge(age);
                        ArrayList<ChildAlertPersonDto> householdMembersMinusChild = new ArrayList<>(householdMembersFound);
                        householdMembersMinusChild.remove(index);
                        childAlertDto.setHouseholdMembers(householdMembersMinusChild);

                        childsAlertDto.add(childAlertDto);
                    }
                    break;
                }
            }
        }
        return childsAlertDto.isEmpty()? null : childsAlertDto   ;
    }

    public List<String> getPhoneNumbersRelatedToAStation(int stationNumber) {
        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);
        List<String> phoneNumbers = new ArrayList<>();
        for (Firestation firestation: firestations) {
                if (firestation.getStation() == stationNumber){
                    for (Person person: persons){
                        if (firestation.getAddress().equals(person.getAddress())){
                            phoneNumbers.add(person.getPhone());
                        }
                }
            }
        }
        return phoneNumbers.isEmpty()? null : phoneNumbers;
    }

    public FireDto getPersonsRelatedToAnAddress(String address) {
        FireDto fireDto = new FireDto();

        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        fireDto.setStation(firestationDao.findByAdress(address).getStation());

        List<FirePersonDto> householdMembersFound = new ArrayList<>();
        FirePersonDto firePersonDto;

        // On récupère toutes les personnes ayant l'adresse fournie (les membres du foyer)
        for (Person person: persons) {
            if (person.getAddress().equals(address)) {
                firePersonDto = new FirePersonDto();
                firePersonDto.setLastName(person.getLastName());
                firePersonDto.setPhone(person.getPhone());
                firePersonDto.setEmail(person.getEmail());

                for (Medicalrecord medicalrecord : medicalrecords) {
                    if (medicalrecord.getFirstName().equals(person.getFirstName()) && medicalrecord.getLastName().equals(person.getLastName())) {
                        firePersonDto.setAge(calculateAge(medicalrecord.getBirthdate()));
                        firePersonDto.setMedications(medicalrecord.getMedications());
                        firePersonDto.setAllergies(medicalrecord.getAllergies());
                        break;
                    }
                }
                householdMembersFound.add(firePersonDto);
            }
        }
        fireDto.setHouseholdMembers(householdMembersFound);
        return fireDto.getHouseholdMembers().isEmpty()? null : fireDto;
    }

    public List<String> getHouseRelatedToAStation(List<Integer> stations) {
        return null;
    }

    public List<Object> getInfoPerson(String firstName, String lastName) {
        return null;
    }

    public List<String> getMailsRelatedToACity(String city) {
        List<Person> persons = personDao.findAll();
        List<String> mails = new ArrayList<>();
        for (Person person: persons){
            if (person.getCity().equals(city)){
                mails.add(person.getEmail());
            }
        }
        return mails.isEmpty()? null : mails;
    }


    int calculateAge(String birthdate){
        int age;
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate currentDate = LocalDate.now();
        age = Period.between(birthDate, currentDate).getYears();
        return age;
    }
}

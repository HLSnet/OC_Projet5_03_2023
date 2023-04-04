package com.safetynet.safetynetalerts.service;


import com.safetynet.safetynetalerts.dto.ChildDto;
import com.safetynet.safetynetalerts.dto.PersonDto;
import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import com.safetynet.safetynetalerts.repository.PersonDao;

import org.springframework.stereotype.Service;


import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class AlertServiceImpl implements AlertService{

    private final PersonDao personDao;
    private final FirestationDao firestationDao;
    private final MedicalrecordDao medicalrecordDao;

    public AlertServiceImpl(PersonDao personDao, FirestationDao firestationDao, MedicalrecordDao medicalrecordDao) {
        this.personDao = personDao;
        this.firestationDao = firestationDao;
        this.medicalrecordDao = medicalrecordDao;
    }

    public PersonDto getPersonsRelatedToAStation(int stationNumber) {

        PersonDto personDto = new PersonDto();

        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate ;
        LocalDate currentDate = LocalDate.now();

        for (Firestation firestation: firestations) {
            for (Person person: persons){
                if (firestation.getAddress().equals(person.getAddress())){
                    personDto.getPersons().add(person);
                    for (Medicalrecord medicalrecord: medicalrecords){
                        if (medicalrecord.getFirstName().equals(person.getFirstName())  && medicalrecord.getLastName().equals(person.getLastName())){
                            birthDate = LocalDate.parse(medicalrecord.getBirthdate(), formatter);

                            if ((Period.between(birthDate, currentDate).getYears()) > 18){
                                personDto.setNbAdult(personDto.getNbAdult() + 1) ;
                            }
                            else {personDto.setNbChild(personDto.getNbChild() + 1); }
                        }
                    }
                }
            }
        }

        return personDto.getPersons().isEmpty()? null : personDto;
    }

    public List<ChildDto> getChildsdRelatedToAnAddress(String address) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate birthDate ;
        LocalDate currentDate = LocalDate.now();

        List<ChildDto> childsDto  = new ArrayList<>();
        ChildDto childDto  = new ChildDto();


        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        List<Person> personsFound = new ArrayList<>();

        // On récupère toutes les personnes ayant l'adresse fournie
        for (Person person: persons) {
            if (person.getAddress().equals(address)) {
                personsFound.add(person);
            }
        }

        List<Person> householdMembers = new ArrayList<>(personsFound);
        int index = -1;
        for (Person person: personsFound) {
            index++;
            // On recherche dans medicalrecord la personne ayant le firstName et LastName recherchés
            for (Medicalrecord medicalrecord: medicalrecords){
                if (medicalrecord.getFirstName().equals(person.getFirstName())  && medicalrecord.getLastName().equals(person.getLastName())){
                    // On calcule l'âge en fonction de l'année de naissance
                    birthDate = LocalDate.parse(medicalrecord.getBirthdate(), formatter);
                    int age = Period.between(birthDate, currentDate).getYears();
                    // Si c'est un enfant (age <= 18) on crée un objet childDto que l'on ajoute à childsDto
                    if ( age <= 18){
                        childDto.setFirstName(medicalrecord.getFirstName());
                        childDto.setLastName(medicalrecord.getLastName());
                        childDto.setAge(age);
                        childDto.setHouseholdMembers(new ArrayList<Person>(householdMembers));
                        childDto.getHouseholdMembers().remove(index);

                        childsDto.add(childDto);
                    }
                    break;
                }
            }
        }

        return childsDto.isEmpty()? null : childsDto;
    }

    public List<String> getPhoneRelatedToAStation(int firestation) {
        return null;
    }

    public List<Object> getPersonsRelatedToAnAddress(String address) {
        return null;
    }

    public List<String> getHouseRelatedToAStation(List<Integer> stations) {
        return null;
    }

    public List<Object> getInfoPerson(String firstName, String lastName) {
        return null;
    }

    public List<String> getMailRelatedToACity(String city) {
        return null;
    }
}

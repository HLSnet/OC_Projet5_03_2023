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
import java.util.*;

@Service
public class AlertServiceImpl implements AlertService{
    @Autowired
    PersonDao personDao;

    @Autowired
    FirestationDao firestationDao;

    @Autowired
    MedicalrecordDao medicalrecordDao;

    ///////////////////////////////////////////////////////////////////////////////////
    // URL1 : http://localhost:8080/firestation?stationNumber=<station_number>
    ///////////////////////////////////////////////////////////////////////////////////
    public FirestationDto getPersonsRelatedToAStation(int stationNumber) {
        // On instancie l'objet DTO à renvoyer
        FirestationDto firestationDto = new FirestationDto();

        // On récupère la liste des firestation ayant le numéro de station demandé
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);

        // On récupère les listes des personnes et des dossiers médicaux
        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        // Optimisation du code : on retire du parcours des listes les personnes déjà trouvées
        Person personToRetrievFromTheList = null;
        Medicalrecord medicalrecordToRetrievFromTheList = null;

            for (Firestation firestation: firestations) {
                for (Person person: persons){
                    personToRetrievFromTheList = null;
                    if (firestation.getAddress().equals(person.getAddress())){
                        personToRetrievFromTheList = person;
                        FirestationPersonDto firestationPersonDto= new FirestationPersonDto(
                                person.getFirstName(),
                                person.getLastName(),
                                person.getAddress(),
                                person.getPhone());

                        // On récupère la liste (une copie) des personnes correspondant au critère de recherche
                        List<FirestationPersonDto> firestationPersonDtos = firestationDto.getPersons();
                        // On y ajoute la nouvelle personne
                        firestationPersonDtos.add(firestationPersonDto);
                        // On met à jour la liste
                        firestationDto.setPersons(firestationPersonDtos);

                        for (Medicalrecord medicalrecord: medicalrecords){
                            medicalrecordToRetrievFromTheList = null;
                            if (medicalrecord.getFirstName().equals(person.getFirstName())  && medicalrecord.getLastName().equals(person.getLastName())){
                                medicalrecordToRetrievFromTheList = medicalrecord;
                                if (calculateAge(medicalrecord.getBirthdate()) > 18){
                                    firestationDto.setNbAdult(firestationDto.getNbAdult() + 1) ;
                                }
                                else {
                                    firestationDto.setNbChild(firestationDto.getNbChild() + 1); }
                                break;
                            }
                        }
                        medicalrecords.remove(medicalrecordToRetrievFromTheList);
                    }
                }
            persons.remove(personToRetrievFromTheList);
        }

        return firestationDto.getPersons().isEmpty()? null : firestationDto;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // URL2 :  http://localhost:8080/childAlert?address=<address>
    ///////////////////////////////////////////////////////////////////////////////////
    public List<ChildAlertDto> getChildsdRelatedToAnAddress(String address) {
        List<ChildAlertDto> childAlertDtos  = new ArrayList<>();

        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        List<ChildAlertPersonDto> householdMembersFound = new ArrayList<>();

        // On récupère toutes les personnes ayant l'adresse fournie (les membres du foyer)
        for (Person person: persons) {
            if (person.getAddress().equals(address)) {
                ChildAlertPersonDto childAlertPersonDto = new ChildAlertPersonDto(
                        person.getFirstName(),
                        person.getLastName(),
                        person.getPhone(),
                        person.getEmail());
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
                    // Si c'est un enfant (age <= 18) on crée un objet ChildAlertDto que l'on ajoute à childAlertDtos
                    if ( age <= 18){
                        // On créé la liste de personnes habitant avec l'enfant
                        ArrayList<ChildAlertPersonDto> householdMembersMinusChild = new ArrayList<>(householdMembersFound);
                        householdMembersMinusChild.remove(index);

                        ChildAlertDto childAlertDto = new ChildAlertDto(
                                medicalrecord.getFirstName(),
                                medicalrecord.getLastName(),
                                age,
                                householdMembersMinusChild);

                        childAlertDtos.add(childAlertDto);
                    }
                    break;
                }
            }
        }
        return childAlertDtos.isEmpty()? null : childAlertDtos;
    }

    ///////////////////////////////////////////////////////////////////////////////////
    // URL3 : http://localhost:8080/phoneAlert?firestation=<firestation_number>
    ///////////////////////////////////////////////////////////////////////////////////
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

    ///////////////////////////////////////////////////////////////////////////////////
    // URL4 : http://localhost:8080/fire?address=<address>
    ///////////////////////////////////////////////////////////////////////////////////
    public FireDto getPersonsRelatedToAnAddress(String address) {
        FireDto fireDto = new FireDto();

        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();
        Firestation firestation = firestationDao.findByAddress(address);

        List<FirePersonDto> householdMembersFound = new ArrayList<>();
        if (firestation != null) {
            fireDto.setStation(firestationDao.findByAddress(address).getStation());
            // On récupère toutes les personnes ayant l'adresse fournie (les membres du foyer)
            for (Person person : persons) {
                if (person.getAddress().equals(address)) {
                    FirePersonDto firePersonDto = new FirePersonDto();
                    firePersonDto.setFirstName(person.getFirstName());
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
        }
        fireDto.setHouseholdMembers(householdMembersFound);
        return fireDto.getHouseholdMembers().isEmpty()? null : fireDto;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // URL5 : http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    ////////////////////////////////////////////////////////////////////////////////////
    public List<FloodDto> getHousesRelatedToAListOfStations(List<Integer> stations) {
        List<FloodDto> floodDtos = null;

        List<Person> persons = personDao.findAll();
        List<Firestation> firestations = firestationDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        // On génère la liste des adresses desservies par les firestation
        List<String> addresses = new ArrayList<>();
        for (int station: stations){
            for (Firestation firestation : firestations){
                if (firestation.getStation() == station){
                    addresses.add(firestation.getAddress());
                }
            }
        }

        if (!addresses.isEmpty()) {
            floodDtos = new ArrayList<>();
            for (String address : addresses) {
                FloodDto floodDto = new FloodDto();
                floodDto.setAddress(address);
                List<FloodPersonDto> floodPersonDtos = new ArrayList<>();
                for (Person person : persons) {
                    if (person.getAddress().equals(address)) {
                        FloodPersonDto floodPersonDto = new FloodPersonDto();
                        floodPersonDto.setFirstName(person.getFirstName());
                        floodPersonDto.setLastName(person.getLastName());
                        floodPersonDto.setPhone(person.getPhone());
                        for (Medicalrecord medicalrecord : medicalrecords) {
                            if (medicalrecord.getFirstName().equals(person.getFirstName()) && medicalrecord.getLastName().equals(person.getLastName())) {
                                floodPersonDto.setAge(calculateAge(medicalrecord.getBirthdate()));
                                floodPersonDto.setMedications(medicalrecord.getMedications());
                                floodPersonDto.setAllergies(medicalrecord.getAllergies());
                                break;
                            }
                        }
                        floodPersonDtos.add(floodPersonDto);
                    }
                }
                floodDto.setHouseholdMembers(floodPersonDtos);
                floodDtos.add(floodDto);
            }
        }
        return floodDtos;
    }

    /////////////////////////////////////////////////////////////////////////////////////
    // URL6 : http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    /////////////////////////////////////////////////////////////////////////////////////
    public List<InfoPersonDto> getInfoPerson(String firstName, String lastName) {
        List<InfoPersonDto> infoPersonDtos = new ArrayList<>();

        List<Person> persons = personDao.findAll();
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();

        for (Person person: persons){
            if (person.getLastName().equals(lastName)){
                InfoPersonDto infoPersonDto= new InfoPersonDto();
                infoPersonDto.setFirstName(person.getFirstName());
                infoPersonDto.setLastName(person.getLastName());
                infoPersonDto.setAddress(person.getAddress());
                infoPersonDto.setEmail(person.getEmail());

                for (Medicalrecord medicalrecord : medicalrecords) {
                    if (medicalrecord.getFirstName().equals(person.getFirstName()) && medicalrecord.getLastName().equals(lastName)) {
                        infoPersonDto.setAge(calculateAge(medicalrecord.getBirthdate()));
                        infoPersonDto.setMedications(medicalrecord.getMedications());
                        infoPersonDto.setAllergies(medicalrecord.getAllergies());
                        break;
                    }
                }
                infoPersonDtos.add(infoPersonDto);
            }
        }
        return infoPersonDtos.isEmpty()? null : infoPersonDtos;
    }

    ////////////////////////////////////////////////////////////////////////////////////
    // URL7 :  http://localhost:8080/communityEmail?city=<city>
    ////////////////////////////////////////////////////////////////////////////////////
    public List<String> getMailsRelatedToACity(String city) {
        List<Person> persons = personDao.findAll();

        // On prends un type de données Set pour éliminer les doublons d'e-mails
        Set<String> mailsSet = new HashSet<>();
        for (Person person: persons){
            if (person.getCity().equals(city)){
                mailsSet.add(person.getEmail());
            }
        }
        List<String> mails = new ArrayList<>(mailsSet);
        Collections.sort(mails);
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

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
    /**
     * This method retrieves the list of persons related to the specified station number,
     * along with the number of adults and children among them.
     *
     * @param stationNumber the station number to search for
     * @return a {@link FirestationDto} object containing the list of persons and the number of adults and children,
     *         or null if no persons are found
     */
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
    /**
     * This method retrieves information about all children living at a given address along with the other household members.
     *
     * @param address the address to search for children.
     * @return A list of {@link ChildAlertDto} objects representing all children living at the given address,
     *         along with the other household members. If no children are found, returns null.
     */
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
    /**
     * This method retrieves a list of phone numbers related to a given fire station number.
     *
     * @param stationNumber the fire station number to search for
     * @return a list of phone numbers related to the given fire station number, or null if no phone numbers are found
     */

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
    /**
     * This method retrieves information about the people living at the provided address,
     * records and the fire station serving that address.
     *
     * @param address the address to search for
     * @return a {@link FireDto} object containing information about the people living at the provided address,
     *         and the fire station serving that address, or null if no  matching records were found.
     */
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
    /**
     * This method retrieves information about people living at addresses served by a list of fire stations,
     * grouped by adress
     *
     * @param stations a list of integers representing the fire station numbers
     * @return a list of {@link FloodDto} objects representing people living at addresses served by a list of fire stations,
     *         grouped by adress
     */
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
    /**
     * This method retrieves information about persons based on their first and last names.
     *
     * @param firstName the first name of the person to retrieve information about
     * @param lastName  the last name of the person to retrieve information about
     * @return a list of {@link InfoPersonDto} objects containing information about the persons
     *         matching the first and last names, or null if no matching persons are found
     */

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
    /**
     * This method retrieves a list of e-mails for people living in a given city.
     *
     * @param city the name of the city to search for
     * @return a list of e-mails for people living in the given city, sorted alphabetically,
     *         or null if no e-mails were found
     */

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

    /**
     * Calculates the age of a person based on his birthdate.
     *
     * @param birthdate a string representing the person's birthdate in the format "MM/dd/yyyy"
     * @return the person's age as an integer
     */
    int calculateAge(String birthdate){
        int age;
        LocalDate birthDate = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        LocalDate currentDate = LocalDate.now();
        age = Period.between(birthDate, currentDate).getYears();
        return age;
    }
}

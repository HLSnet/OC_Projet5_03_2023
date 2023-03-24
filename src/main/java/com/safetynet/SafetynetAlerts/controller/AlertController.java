package com.safetynet.SafetynetAlerts.controller;

import com.safetynet.SafetynetAlerts.dao.FirestationDao;
import com.safetynet.SafetynetAlerts.dao.MedicalrecordDao;
import com.safetynet.SafetynetAlerts.dao.PersonDao;
import com.safetynet.SafetynetAlerts.model.Firestation;
import com.safetynet.SafetynetAlerts.model.Medicalrecord;
import com.safetynet.SafetynetAlerts.model.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class AlertController {

    // Remarque : attributs définis en private final afin que Spring se charge d'en fabriquer une instance
    // à préfèrer à @Autowired
    private final PersonDao personDao;
    private final FirestationDao firestationDao;
    private final MedicalrecordDao medicalrecordDao;

    public AlertController(PersonDao personDao, FirestationDao firestationDao, MedicalrecordDao medicalrecordDao) {
        this.personDao = personDao;
        this.firestationDao = firestationDao;
        this.medicalrecordDao = medicalrecordDao;
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // EndPoints
    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/person
    ////////////////////////////////////////////////////////////////////////////////////

    // Afficher toutes les personnes
    @GetMapping(value = "/person")
    public List<Person> getPersons() {
        return personDao.findAll();
    }


    // Afficher les informations d'une personne en fournissant son lastName et firstName
    @GetMapping(value = "/person")
    public Person getPerson(@RequestParam String firstName, @RequestParam String lastName) {

        return personDao.findByName(firstName, lastName);
    }


    // Ajouter une nouvelle personne
    @PostMapping(value = "/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {
        Person personAdded = personDao.save(person);
        if (Objects.isNull(personAdded)) {
            return ResponseEntity.noContent().build();
        }

        // Pour renvoyer le code "201 Created"  l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .queryParam("firstName", personAdded.getFirstName())
                .queryParam("lastName", personAdded.getLastName())
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    // Mettre à jour une personne existante (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    @PutMapping(value = "/person")
    public void updatePerson(@RequestBody Person person) {
        personDao.update(person);
    }

    // Supprimer une personne (utilisez une combinaison de prénom et de nom comme identificateur unique).
    @DeleteMapping(value = "/person")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personDao.delete(firstName, lastName);
    }


    ////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/firestation
    ////////////////////////////////////////////////////////////////////////////////////

    // Afficher toutes les firestation
    @GetMapping(value = "/firestation")
    public List<Firestation> getFirestation() {
        return firestationDao.findAll();
    }




//    // Ajouter un mapping caserne/adresse
//    @PostMapping(value = "/adress")
//    public void addFirestation(@RequestBody String adress) {
//        FirestationDao.save(firestation);
//    }
//
//    // Mettre à jour le numéro de la caserne de pompiers d'une adresse
//    @PutMapping(value = "/person")
//    public void UpdateFirestation(@RequestBody Person person) {
//        FirestationDao.update(person);
//    }
//
//    // Supprimer le mapping d'une caserne ou d'une adresse
//    @DeleteMapping(value = "/station")
//    public void deleteFirestation(@RequestBody Person person) {
//        FirestationDao.delete(person);
//    }

    ////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/medicalRecord
    ////////////////////////////////////////////////////////////////////////////////////

    // Afficher toutes les medicalrecord
    @GetMapping(value = "/medicalrecord")
    public List<Medicalrecord> getMedicalrecord() {
        return medicalrecordDao.findAll();
    }



//    // Ajouter un dossier médical
//    @PostMapping(value = "/adress")
//    public void addMedicalRecord(@RequestBody String adress) {
//        MedicalRecord.save(firestation);
//    }
//
//    // Mettre à jour un dossier médical existant (comme évoqué précédemment, supposer que
//    // le prénom et le nom de famille ne changent pas)
//    @PutMapping(value = "/person")
//    public void UpdateMedicalRecord(@RequestBody Person person) {
//        MedicalRecord.update(person);
//    }
//
//    // Supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme
//    // identificateur unique)
//    @DeleteMapping(value = "/station")
//    public void deleteMedicalRecord(@RequestBody Person person) {
//        MedicalRecord.delete(person);
//    }



}

package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Objects;

@RestController
public class FirestationController {

    // Remarque : attributs définis en private final afin que Spring se charge d'en fabriquer une instance (à préfèrer à @Autowired)
    private final FirestationDao firestationDao;

    public FirestationController (FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les firestation
    @GetMapping(value = "/firestation")
    public List<Firestation> getAllFirestations() {
        return firestationDao.findAll();
    }


    // Afficher le numéro de station  correspondant à une adresse
    @GetMapping(value = "/firestation/adress/{adress}")
    public ResponseEntity<Firestation> getFirestation(@PathVariable String adress) {
        Firestation firestationGot = firestationDao.findByAdress(adress);

        if (Objects.isNull(firestationGot)) {
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(firestationGot);
    }


    // Afficher les adresses couvertes par une firestation  (on fournit un numéro de station)
    @GetMapping(value = "/firestation/{stationNumber}")
    public ResponseEntity<List<Firestation>> getAdress(@PathVariable int stationNumber) {
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);

        if (firestations.isEmpty()) {
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(firestations);
    }

    //***************************************************************************************************
    // REQUETES POST
    //***************************************************************************************************

    // Ajouter un mapping caserne/adresse
    @PostMapping(value = "/firestation")
    public ResponseEntity<Firestation> addFirestation(@RequestBody Firestation firestation) {

        if (!firestationDao.save(firestation)) {
            //On renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }

        // On renvoie le code "201 Created"
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    //***************************************************************************************************
    // REQUETES PUT
    //***************************************************************************************************

    // Mettre à jour le numéro de la caserne de pompiers d'une adresse
    @PutMapping(value = "/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody Firestation firestation) {

        if (!firestationDao.update(firestation)) {
            //Si le mapping caserne/adresse n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETES DELETE
    //***************************************************************************************************

    //  Supprimer le mapping d'une caserne
    @DeleteMapping(value= "/firestation/{stationNumber}")
    public ResponseEntity<Firestation> deleteStation(@PathVariable int stationNumber) {

        if (!firestationDao.deleteStation(stationNumber)) {
            //Si la station n'est dans aucun mapping caserne/adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }

    //  Supprimer le mapping d'une adresse
    @DeleteMapping(value= "/firestation/adress/{adress}")
    public ResponseEntity<Firestation> deleteAdress(@PathVariable String adress) {

        if (!firestationDao.deleteAdress(adress)) {
            //Si le mapping caserne/adresse pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }
}

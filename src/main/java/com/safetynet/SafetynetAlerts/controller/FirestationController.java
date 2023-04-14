package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class FirestationController {

    @Autowired
    FirestationDao firestationDao;

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les firestation
    // http://localhost:8080/firestation/all
    @GetMapping(value = "/firestation/all")
    public List<Firestation> getAllFirestations() {
        return firestationDao.findAll();
    }


    // Afficher le numéro de station  correspondant à une adresse
    // http://localhost:8080/firestation/adress/{adress}
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
    // http://localhost:8080/firestation/station/{stationNumber}
    @GetMapping(value = "/firestation/station/{stationNumber}")
    public ResponseEntity<List<Firestation>> getAdresses(@PathVariable int stationNumber) {
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);

        if (firestations.isEmpty()) {
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(firestations);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter un mapping caserne/adresse
    // http://localhost:8080/firestation
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFirestation(@RequestBody Firestation firestation) {

        if (!firestationDao.save(firestation)) {
            //On renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/adress/{adress}")
                .buildAndExpand(firestation.getAddress())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour le numéro de la caserne de pompiers d'une adresse
    // http://localhost:8080/firestation
    @PutMapping(value = "/firestation")
    public ResponseEntity<Void> updateFirestation(@RequestBody Firestation firestation) {

        if (!firestationDao.update(firestation)) {
            //Si le mapping caserne/adresse n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    //  Supprimer le mapping d'une caserne
    // http://localhost:8080/firestation/{stationNumber}
    @DeleteMapping(value= "/firestation/station/{stationNumber}")
    public ResponseEntity<Void> deleteStation(@PathVariable int stationNumber) {

        if (!firestationDao.deleteStation(stationNumber)) {
            //Si la station n'est dans aucun mapping caserne/adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }

    //  Supprimer le mapping d'une adresse
    // http://localhost:8080/firestation/adress/{adress}
    @DeleteMapping(value= "/firestation/adress/{adress}")
    public ResponseEntity<Void> deleteAdress(@PathVariable String adress) {

        if (!firestationDao.deleteAdress(adress)) {
            //Si le mapping caserne/adresse pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }
}

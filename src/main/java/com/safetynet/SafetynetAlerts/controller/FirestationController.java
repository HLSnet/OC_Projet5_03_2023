package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class FirestationController {

    private static Logger logger = LoggerFactory.getLogger(FirestationController.class);

    @Autowired
    FirestationDao firestationDao;

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les firestation
    // http://localhost:8080/firestation/all
    @GetMapping(value = "/firestation/all")
    public List<Firestation> getAllFirestations() {
        logger.info("Requete GET en cours : http://localhost:8080/firestation/all");
        return firestationDao.findAll();
    }


    // Afficher le numéro de station  correspondant à une adresse
    // http://localhost:8080/firestation/address/{address}
    @GetMapping(value = "/firestation/address/{address}")
    public ResponseEntity<Firestation> getFirestation(@PathVariable String address) {
        logger.info("Requete Get en cours : http://localhost:8080/firestation/address/{}", address);
        Firestation firestationGot = firestationDao.findByAddress(address);

        if (Objects.isNull(firestationGot)) {
            logger.error("Resultat de la requete GET en cours : 204 No Content");
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete GET en cours : 200 ok");
        return ResponseEntity.ok(firestationGot);
    }


    // Afficher les adresses couvertes par une firestation  (on fournit un numéro de station)
    // http://localhost:8080/firestation/station/{stationNumber}
    @GetMapping(value = "/firestation/station/{stationNumber}")
    public ResponseEntity<List<Firestation>> getAdresses(@PathVariable int stationNumber) {
        logger.info("Requete Get en cours : http://localhost:8080/firestation/station/{}", stationNumber);
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);

        if (firestations.isEmpty()) {
            logger.error("Resultat de la requete GET en cours : 204 No Content");
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete GET en cours : 200 ok");
        return ResponseEntity.ok(firestations);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter un mapping caserne/adresse
    // http://localhost:8080/firestation
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFirestation(@RequestBody Firestation firestation) {
        logger.info("Requete POST en cours : http://localhost:8080/firestation");
        if (!firestationDao.save(firestation)) {
            logger.error("Resultat de la requete POST en cours : 204 No Content");
            //On renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/adress/{adress}")
                .buildAndExpand(firestation.getAddress())
                .toUri();
        logger.info("Resultat de la requete POST en cours : 201 created URI = {}", location);
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour le numéro de la caserne de pompiers d'une adresse
    // http://localhost:8080/firestation
    @PutMapping(value = "/firestation")
    public ResponseEntity<Void> updateFirestation(@RequestBody Firestation firestation) {
        logger.info("Requete PUT en cours : http://localhost:8080/firestation");
        if (!firestationDao.update(firestation)) {
            logger.error("Resultat de la requete PUT en cours : 204 No Content");
            //Si le mapping caserne/adresse n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete PUT en cours : 200 ok");
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    //  Supprimer le mapping d'une caserne
    // http://localhost:8080/firestation/{stationNumber}
    @DeleteMapping(value= "/firestation/station/{stationNumber}")
    public ResponseEntity<Void> deleteStation(@PathVariable int stationNumber) {
        logger.info("Requete DELETE en cours : http://localhost:8080/firestation/{}", stationNumber);
        if (!firestationDao.deleteStation(stationNumber)) {
            logger.error("Resultat de la requete DELETE en cours : 204 No Content");
            //Si la station n'est dans aucun mapping caserne/adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete DELETE en cours : 200 ok");
        return ResponseEntity.ok().build();
    }

    //  Supprimer le mapping d'une adresse
    // http://localhost:8080/firestation/address/{address}
    @DeleteMapping(value= "/firestation/address/{address}")
    public ResponseEntity<Void> deleteAdress(@PathVariable String address) {
        logger.info("Requete DELETE en cours : http://localhost:8080/firestation/address/{}", address);
        if (!firestationDao.deleteAddress(address)) {
            logger.error("Resultat de la requete DELETE en cours : 204 No Content");
            //Si le mapping caserne/adresse pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete DELETE en cours : 200 ok");
        return ResponseEntity.ok().build();
    }
}

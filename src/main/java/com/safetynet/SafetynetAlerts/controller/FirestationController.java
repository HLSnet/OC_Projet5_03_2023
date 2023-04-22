package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public List<Firestation> getAllFirestations(@NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        // On récupère la liste des firestation
        List<Firestation> firestations = firestationDao.findAll();
        logger.info(" Resultat de la requete {} en cours : {}", request.getMethod(), firestations);

        return firestations;
    }


    // Afficher le numéro de station  correspondant à une adresse
    // http://localhost:8080/firestation/address/{address}
    @GetMapping(value = "/firestation/address/{address}")
    public ResponseEntity<Firestation> getFirestation(@PathVariable String address, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());
        Firestation firestationGot = firestationDao.findByAddress(address);

        if (Objects.isNull(firestationGot)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), firestationGot);
        return ResponseEntity.ok(firestationGot);
    }


    // Afficher les adresses couvertes par une firestation  (on fournit un numéro de station)
    // http://localhost:8080/firestation/station/{stationNumber}
    @GetMapping(value = "/firestation/station/{stationNumber}")
    public ResponseEntity<List<Firestation>> getAdresses(@PathVariable int stationNumber, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());
        List<Firestation> firestations = firestationDao.findByStation(stationNumber);

        if (firestations.isEmpty()) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            //Si la firestation ne couvre aucune adresse : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), firestations);
        return ResponseEntity.ok(firestations);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter un mapping caserne/adresse
    // http://localhost:8080/firestation
    @PostMapping(value = "/firestation")
    public ResponseEntity<Void> addFirestation(@RequestBody Firestation firestation, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), firestation);
        if (!firestationDao.save(firestation)) {
            logger.error(" Resultat de la requete {} en cours : statut = 409 conflit la ressource existe deja", request.getMethod());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/adress/{adress}")
                .buildAndExpand(firestation.getAddress())
                .toUri();
        logger.info(" Resultat de la requete {} en cours : statut =  201 Created ; URL = {}", request.getMethod(), location);

        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour le numéro de la caserne de pompiers d'une adresse
    // http://localhost:8080/firestation
    @PutMapping(value = "/firestation")
    public ResponseEntity<Void> updateFirestation(@RequestBody Firestation firestation, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), firestation);
        if (!firestationDao.update(firestation)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    //  Supprimer le mapping d'une caserne
    // http://localhost:8080/firestation/{stationNumber}
    @DeleteMapping(value= "/firestation/station/{stationNumber}")
    public ResponseEntity<Void> deleteStation(@PathVariable int stationNumber, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());
        if (!firestationDao.deleteStation(stationNumber)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }

    //  Supprimer le mapping d'une adresse
    // http://localhost:8080/firestation/address/{address}
    @DeleteMapping(value= "/firestation/address/{address}")
    public ResponseEntity<Void> deleteAdress(@PathVariable String address, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());
        if (!firestationDao.deleteAddress(address)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }
}

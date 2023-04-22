package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.dto.*;
import com.safetynet.safetynetalerts.service.AlertService;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
public class ServiceController {
    private static Logger logger = LoggerFactory.getLogger(ServiceController.class);

    private final AlertService alertService;

    public ServiceController(AlertService alertService) {
        this.alertService = alertService;
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 1/ Cette url doit retourner une liste des personnes couvertes par la caserne de pompiers correspondante.
    //    Donc, si le numéro de station = 1, elle doit renvoyer les habitants couverts par la station numéro 1. La liste
    //    doit inclure les informations spécifiques suivantes : prénom, nom, adresse, numéro de téléphone. De plus,
    //    elle doit fournir un décompte du nombre d'adultes et du nombre d'enfants (tout individu âgé de 18 ans ou
    //    moins) dans la zone desservie.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/firestation?stationNumber=<station_number>
    @GetMapping("/firestation")
    public ResponseEntity<FirestationDto>  getPersonsRelatedToAStation(@RequestParam int stationNumber, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?stationNumber={}", request.getMethod(), request.getRequestURL(), stationNumber);

        FirestationDto firestationDto = alertService.getPersonsRelatedToAStation(stationNumber);
        if (Objects.isNull(firestationDto)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), firestationDto);
        return ResponseEntity.ok(firestationDto);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 2/ Cette url doit retourner une liste d'enfants (tout individu âgé de 18 ans ou moins) habitant à cette adresse.
    //    La liste doit comprendre le prénom et le nom de famille de chaque enfant, son âge et une liste des autres
    //    membres du foyer. S'il n'y a pas d'enfant, cette url peut renvoyer une chaîne vide.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/childAlert?address=<address>
    @GetMapping("/childAlert")
    public ResponseEntity<List<ChildAlertDto>>  getChildsdRelatedToAnAddress(@RequestParam String address, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?address={}", request.getMethod(), request.getRequestURL(), address);

        List<ChildAlertDto> childAlertDtos = alertService.getChildsdRelatedToAnAddress(address);
        if (Objects.isNull(childAlertDtos)) {
            logger.error(" Resultat de la requete {} en cours : liste vide", request.getMethod());
            return ResponseEntity.ok(Collections.emptyList());
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), childAlertDtos);
        return ResponseEntity.ok(childAlertDtos);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 3/ Cette url doit retourner une liste des numéros de téléphone des résidents desservis par la caserne de
    //     pompiers. Nous l'utiliserons pour envoyer des messages texte d'urgence à des foyers spécifiques.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/phoneAlert?firestation=<firestation_number>
    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneNumbersRelatedToAStation(@RequestParam int firestation, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?firestation={}", request.getMethod(), request.getRequestURL(), firestation);

        List<String> phoneNumbers = alertService.getPhoneNumbersRelatedToAStation(firestation);
        if (Objects.isNull(phoneNumbers)) {
            logger.error(" Resultat de la requete {} en cours : liste vide", request.getMethod());
            return ResponseEntity.ok(Collections.emptyList());
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), phoneNumbers);
        return ResponseEntity.ok(phoneNumbers);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 4/ Cette url doit retourner la liste des habitants vivant à l’adresse donnée ainsi que le numéro de la caserne
    //    de pompiers la desservant. La liste doit inclure le nom, le numéro de téléphone, l'âge et les antécédents
    //    médicaux (médicaments, posologie et allergies) de chaque personne.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/fire?address=<address>
    @GetMapping("/fire")
    public ResponseEntity<FireDto> getPersonsRelatedToAnAddress(@RequestParam String address, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?address={}", request.getMethod(), request.getRequestURL(), address);

        FireDto fireDto = alertService.getPersonsRelatedToAnAddress(address);
        if (Objects.isNull(fireDto)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), fireDto);
        return ResponseEntity.ok(fireDto);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 5/ Cette url doit retourner une liste de tous les foyers desservis par la caserne. Cette liste doit regrouper les
    //    personnes par adresse. Elle doit aussi inclure le nom, le numéro de téléphone et l'âge des habitants, et
    //    faire figurer leurs antécédents médicaux (médicaments, posologie et allergies) à côté de chaque nom.
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/flood/stations?stations=<a list of station_numbers>
    @GetMapping("/flood/stations")
    public ResponseEntity<List<FloodDto>> getHousesRelatedToAListOfStations(@RequestParam List<Integer> stations, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?stations={},{}", request.getMethod(), request.getRequestURL(), stations.get(0), stations.get(1));

        List<FloodDto> floodDtos = alertService.getHousesRelatedToAListOfStations(stations);
        if (Objects.isNull(floodDtos)) {
            logger.error(" Resultat de la requete {} en cours : liste vide", request.getMethod());
            return ResponseEntity.ok(Collections.emptyList());
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), floodDtos);
        return ResponseEntity.ok(floodDtos);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 6/ Cette url doit retourner le nom, l'adresse, l'âge, l'adresse mail et les antécédents médicaux (médicaments,
    //    posologie, allergies) de chaque habitant. Si plusieurs personnes portent le même nom, elles doivent
    //    toutes apparaître.
    //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
    @GetMapping("/personInfo")
    public ResponseEntity<List<InfoPersonDto>> getInfoPerson(@RequestParam String firstName, @RequestParam String lastName, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?firstName={}&lastName={}", request.getMethod(), request.getRequestURL(), firstName,lastName);

        List<InfoPersonDto> InfoPersonDtos = alertService.getInfoPerson(firstName, lastName);
        if (Objects.isNull(InfoPersonDtos)) {
            logger.error(" Resultat de la requete {} en cours : liste vide", request.getMethod());
            return ResponseEntity.ok(Collections.emptyList());
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), InfoPersonDtos);
        return ResponseEntity.ok(InfoPersonDtos);
    }


    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 7/ Cette url doit retourner les adresses mail de tous les habitants de la ville
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // http://localhost:8080/communityEmail?city=<city>
    @GetMapping("/communityEmail")
    public ResponseEntity<List<String>> getMailRelatedToACity(@RequestParam String city, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}?city={}", request.getMethod(), request.getRequestURL(), city);

        List<String> mails = alertService.getMailsRelatedToACity(city);
        if (Objects.isNull(mails)) {
            logger.error(" Resultat de la requete {} en cours : liste vide", request.getMethod());
            return ResponseEntity.ok(Collections.emptyList());
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), mails);
        return ResponseEntity.ok(mails);
    }
}
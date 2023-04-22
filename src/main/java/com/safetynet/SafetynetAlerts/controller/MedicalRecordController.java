package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
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
public class MedicalRecordController {

    private static Logger logger = LoggerFactory.getLogger(MedicalRecordController.class);

    @Autowired
    MedicalrecordDao medicalrecordDao;

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les medicalrecord
    // http://localhost:8080/medicalrecord
    @GetMapping(value = "/medicalrecord")
    public List<Medicalrecord> getMedicalrecord(@NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        // On récupère la liste de medicalrecord
        List<Medicalrecord> medicalrecords = medicalrecordDao.findAll();
        logger.info(" Resultat de la requete {} en cours :  {}", request.getMethod(), medicalrecords);

        return medicalrecords;
    }


    // Afficher les informations d'un dossier médical en fournissant son lastName et firstName
    // http://localhost:8080/medicalrecord/{firstName}/{lastName}
    @GetMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Medicalrecord> getMedicalrecord(@PathVariable String firstName, @PathVariable  String lastName, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        Medicalrecord medicalrecordGot = medicalrecordDao.findByName(firstName, lastName);
        if (Objects.isNull(medicalrecordGot)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), medicalrecordGot);
        return ResponseEntity.ok(medicalrecordGot);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter un medicalrecord
    // http://localhost:8080/medicalrecord
    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<Void> addMedicalrecord(@RequestBody Medicalrecord medicalrecord, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), medicalrecord);

        if (!medicalrecordDao.save(medicalrecord)) {
            logger.error("Resultat de la requete POST en cours : 409 conflit la ressource existe deja");
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(medicalrecord.getFirstName() ,medicalrecord.getLastName())
                .toUri();

        logger.info(" Resultat de la requete {} en cours : statut =  201 Created ; URL = {}", request.getMethod(), location);
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour un dossier médical existant (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    // http://localhost:8080/medicalrecord
    @PutMapping(value = "/medicalrecord")
    public ResponseEntity<Void> updateMedicalrecord(@RequestBody Medicalrecord medicalrecord, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), medicalrecord);
        if (!medicalrecordDao.update(medicalrecord)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    // Supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme identificateur unique).
    // http://localhost:8080/medicalrecord/{firstName}/{lastName}
    @DeleteMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalrecord(@PathVariable String firstName, @PathVariable  String lastName, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());
        if (!medicalrecordDao.delete(firstName, lastName)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }

        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }
}

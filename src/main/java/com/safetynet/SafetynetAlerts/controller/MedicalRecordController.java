package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
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
    public List<Medicalrecord> getMedicalrecord() {
        logger.info("Requete GET en cours : http://localhost:8080/medicalrecord");
        return medicalrecordDao.findAll();
    }


    // Afficher les informations d'un dossier médical en fournissant son lastName et firstName
    // http://localhost:8080/medicalrecord/{firstName}/{lastName}
    @GetMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Medicalrecord> getMedicalrecord(@PathVariable String firstName, @PathVariable  String lastName) {
        logger.info("Requete Get en cours : http://localhost:8080/medicalrecord/{}/{}", firstName, lastName);

        Medicalrecord medicalrecordGot = medicalrecordDao.findByName(firstName, lastName);
        if (Objects.isNull(medicalrecordGot)) {
            logger.error("Resultat de la requete GET en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete GET en cours : 200 ok");
        return ResponseEntity.ok(medicalrecordGot);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter un medicalrecord
    // http://localhost:8080/medicalrecord
    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<Void> addMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
        logger.info("Requete POST en cours : http://localhost:8080/medicalrecord");
        if (!medicalrecordDao.save(medicalrecord)) {
            logger.error("Resultat de la requete POST en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(medicalrecord.getFirstName() ,medicalrecord.getLastName())
                .toUri();

        logger.info("Resultat de la requete POST en cours : 201 created URI = {}", location);
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour un dossier médical existant (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    // http://localhost:8080/medicalrecord
    @PutMapping(value = "/medicalrecord")
    public ResponseEntity<Void> updateMedicalrecord(@RequestBody Medicalrecord medicalrecord) {
        logger.info("Requete PUT en cours : http://localhost:8080/medicalrecord");
        if (!medicalrecordDao.update(medicalrecord)) {
            logger.error("Resultat de la requete PUT en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete PUT en cours : 200 ok");
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    // Supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme identificateur unique).
    // http://localhost:8080/medicalrecord/{firstName}/{lastName}
    @DeleteMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalrecord(@PathVariable String firstName, @PathVariable  String lastName) {
        logger.info("Requete DELETE en cours : http://localhost:8080/medicalrecord/{}/{}", firstName, lastName);
        if (!medicalrecordDao.delete(firstName, lastName)) {
            logger.error("Resultat de la requete DELETE en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }

        logger.info("Resultat de la requete DELETE en cours : 200 ok");
        return ResponseEntity.ok().build();
    }
}

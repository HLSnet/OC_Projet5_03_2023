package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
public class MedicalRecordController {

    @Autowired
    MedicalrecordDao medicalrecordDao;

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les medicalrecord
    @GetMapping(value = "/medicalrecord")
    public List<Medicalrecord> getMedicalrecord() {
        return medicalrecordDao.findAll();
    }


    // Afficher les informations d'un dossier médical en fournissant son lastName et firstName
    @GetMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Medicalrecord> getMedicalrecord(@PathVariable String firstName, @PathVariable  String lastName) {
        Medicalrecord medicalrecordGot = medicalrecordDao.findByName(firstName, lastName);

        if (Objects.isNull(medicalrecordGot)) {
            //Si le dossier médical n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(medicalrecordGot);
    }

    //***************************************************************************************************
    // REQUETES POST
    //***************************************************************************************************

    // Ajouter un medicalrecord
    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<Void> addMedicalrecord(@RequestBody Medicalrecord medicalrecord) {

        if (!medicalrecordDao.save(medicalrecord)) {
            //On renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(medicalrecord.getFirstName() ,medicalrecord.getLastName())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETES PUT
    //***************************************************************************************************

    // Mettre à jour un dossier médical existant (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    @PutMapping(value = "/medicalrecord")
    public ResponseEntity<Void> updateMedicalrecord(@RequestBody Medicalrecord medicalrecord) {

        if (!medicalrecordDao.update(medicalrecord)) {
            //Si le dossier médical n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETES DELETE
    //***************************************************************************************************

    // Supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme identificateur unique).
    @DeleteMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Void> deleteMedicalrecord(@PathVariable String firstName, @PathVariable  String lastName) {

        if (!medicalrecordDao.delete(firstName, lastName)) {
            //Si le dossier médical n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }
}

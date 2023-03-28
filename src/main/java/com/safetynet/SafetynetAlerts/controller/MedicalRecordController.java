package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;


@RestController
public class MedicalRecordController {

    // Remarque : attributs définis en private final afin que Spring se charge d'en fabriquer une instance (à préfèrer à @Autowired)
    private final MedicalrecordDao medicalrecordDao;

    public MedicalRecordController(MedicalrecordDao medicalrecordDao) {
        this.medicalrecordDao = medicalrecordDao;
    }

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

    @PostMapping(value = "/medicalrecord")
    public ResponseEntity<Medicalrecord> addMedicalrecord(@RequestBody Medicalrecord medicalrecord) {

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


    // Mettre à jour un dossier médical existant (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    @PutMapping(value = "/medicalrecord")
    public ResponseEntity<Medicalrecord> updateMedicalrecord(@RequestBody Medicalrecord medicalrecord) {

        if (!medicalrecordDao.update(medicalrecord)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK" et la fiche de la  ressource supprimée .
        return ResponseEntity.ok().build();
    }



    // Supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme identificateur unique).
    @DeleteMapping(value = "/medicalrecord/{firstName}/{lastName}")
    public ResponseEntity<Medicalrecord> deletePerson(@PathVariable String firstName, @PathVariable  String lastName) {

        if (!medicalrecordDao.delete(firstName, lastName)) {
            //Si le dossier médical n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK" et la fiche de la  ressource supprimée .
        return ResponseEntity.ok().build();

    }

}

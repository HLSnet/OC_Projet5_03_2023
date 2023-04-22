package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonDao;
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
public class PersonController {

    private static Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    PersonDao personDao;

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les personnes
    // http://localhost:8080/person
    @GetMapping(value = "/person")
    public List<Person> getPersons(@NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        // On récupère la liste de personnes
        List<Person> persons = personDao.findAll();
        logger.info(" Resultat de la requete {} en cours :  {}", request.getMethod(), persons);

        return persons;
    }

    // Afficher les informations d'une personne en fournissant son lastName et firstName
    // http://localhost:8080/person/{firstName}/{lastName}
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String firstName, @PathVariable  String lastName, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        Person personGot = personDao.findByName(firstName, lastName);
        if (Objects.isNull(personGot)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.info(" Resultat de la requete {} en cours : statut =  200 OK ; reponse = {}", request.getMethod(), personGot);
        return ResponseEntity.ok(personGot);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter une personne
    // http://localhost:8080/person
    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person person, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), person);

        if (!personDao.save(person)) {
            logger.error(" Resultat de la requete {} en cours : statut = 409 conflit la ressource existe deja", request.getMethod());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(person.getFirstName() ,person.getLastName())
                .toUri();

        logger.info(" Resultat de la requete {} en cours : statut =  201 Created ; URL = {}", request.getMethod(), location);
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour une personne existante (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    // http://localhost:8080/person
    @PutMapping(value = "/person")
    public ResponseEntity<Void> updatePerson(@RequestBody Person person, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {} ressource a ajouter {}", request.getMethod(), request.getRequestURL(), person);

        if (!personDao.update(person)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    // Supprimer une personne (utilisez une combinaison de prénom et de nom comme identificateur unique).
    // http://localhost:8080/person/{firstName}/{lastName}
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable  String lastName, @NotNull HttpServletRequest request) {
        logger.info(" Requete {} en cours : {}", request.getMethod(), request.getRequestURL());

        if (!personDao.delete(firstName, lastName)) {
            logger.error(" Resultat de la requete {} en cours : statut = 204 No Content", request.getMethod());
            return ResponseEntity.noContent().build();
        }
        logger.error(" Resultat de la requete {} en cours : statut = 200 ok", request.getMethod());
        return ResponseEntity.ok().build();
    }
}

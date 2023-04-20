package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonDao;
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
    public List<Person> getPersons() {
        logger.info("Requete GET en cours : http://localhost:8080/person");
        return personDao.findAll();
    }

    // Afficher les informations d'une personne en fournissant son lastName et firstName
    // http://localhost:8080/person/{firstName}/{lastName}
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String firstName, @PathVariable  String lastName) {
        logger.info("Requete Get en cours : http://localhost:8080/person/{}/{}", firstName, lastName);
        Person personGot = personDao.findByName(firstName, lastName);
        if (Objects.isNull(personGot)) {
            logger.error("Resultat de la requete GET en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete GET en cours : 200 ok");
        return ResponseEntity.ok(personGot);
    }

    //***************************************************************************************************
    // REQUETE POST
    //***************************************************************************************************

    // Ajouter une personne
    // http://localhost:8080/person
    @PostMapping(value = "/person")
    public ResponseEntity<Void> addPerson(@RequestBody Person person) {
        logger.info("Requete POST en cours : http://localhost:8080/person");
        if (!personDao.save(person)) {
            logger.error("Resultat de la requete POST en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(person.getFirstName() ,person.getLastName())
                .toUri();

        logger.info("Resultat de la requete POST en cours : 201 created URI = {}", location);
        return ResponseEntity.created(location).build();
    }

    //***************************************************************************************************
    // REQUETE PUT
    //***************************************************************************************************

    // Mettre à jour une personne existante (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    // http://localhost:8080/person
    @PutMapping(value = "/person")
    public ResponseEntity<Void> updatePerson(@RequestBody Person person) {
        logger.info("Requete PUT en cours : http://localhost:8080/person");
        if (!personDao.update(person)) {
            logger.error("Resultat de la requete PUT en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete PUT en cours : 200 ok");
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETE DELETE
    //***************************************************************************************************

    // Supprimer une personne (utilisez une combinaison de prénom et de nom comme identificateur unique).
    // http://localhost:8080/person/{firstName}/{lastName}
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Void> deletePerson(@PathVariable String firstName, @PathVariable  String lastName) {
        logger.info("Requete DELETE en cours : http://localhost:8080/person/{}/{}", firstName, lastName);
        if (!personDao.delete(firstName, lastName)) {
            logger.error("Resultat de la requete DELETE en cours : 204 No Content");
            return ResponseEntity.noContent().build();
        }
        logger.info("Resultat de la requete DELETE en cours : 200 ok");
        return ResponseEntity.ok().build();
    }
}

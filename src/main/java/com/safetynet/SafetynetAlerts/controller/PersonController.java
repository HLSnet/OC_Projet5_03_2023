package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class PersonController {

    // Remarque : attributs définis en private final afin que Spring se charge d'en fabriquer une instance (à préfèrer à @Autowired)
    private final PersonDao personDao;

    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }


    // Afficher toutes les personnes
    @GetMapping(value = "/person")
    public List<Person> getPersons() {
        return personDao.findAll();
    }


    // Afficher les informations d'une personne en fournissant son lastName et firstName
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Person> getPerson(@PathVariable String firstName, @PathVariable  String lastName) {
        Person personGot = personDao.findByName(firstName, lastName);

        if (Objects.isNull(personGot)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(personGot);
    }


    @PostMapping(value = "/person")
    public ResponseEntity<Person> addPerson(@RequestBody Person person) {

        if (!personDao.save(person)) {
            //On renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }

        // On renvoie le code "201 Created" et l'URI vers la ressource créée dans le champ Location.
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{firstName}/{lastName}")
                .buildAndExpand(person.getFirstName() ,person.getLastName())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    // Mettre à jour une personne existante (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    @PutMapping(value = "/person")
    public ResponseEntity<Person> updatePerson(@RequestBody Person person) {

        if (!personDao.update(person)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK" et la fiche de la  ressource supprimée .
        return ResponseEntity.ok().build();
    }


    // Supprimer une personne (utilisez une combinaison de prénom et de nom comme identificateur unique).
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<Person> deletePerson(@PathVariable String firstName, @PathVariable  String lastName) {

        if (!personDao.delete(firstName, lastName)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK" et la fiche de la  ressource supprimée .
        return ResponseEntity.ok().build();

    }


}

package com.safetynet.safetynetalerts.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.safetynet.safetynetalerts.model.Person;
import com.safetynet.safetynetalerts.repository.PersonDao;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
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

    //***************************************************************************************************
    // REQUETES GET
    //***************************************************************************************************

    // Afficher toutes les personnes
    @GetMapping(value = "/person")
    public MappingJacksonValue getPersons() {
        List<Person> persons = personDao.findAll();

        SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider listFilters = new SimpleFilterProvider().addFilter("filtreDynamique", filterPerson);
        MappingJacksonValue personsFiltered = new MappingJacksonValue(persons);
        personsFiltered.setFilters(listFilters);

        return personsFiltered;
    }


    // Afficher les informations d'une personne en fournissant son lastName et firstName
    @GetMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<MappingJacksonValue> getPerson(@PathVariable String firstName, @PathVariable  String lastName) {
        Person personGot = personDao.findByName(firstName, lastName);

        if (Objects.isNull(personGot)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }

        SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.serializeAll();
        FilterProvider listFilters = new SimpleFilterProvider().addFilter("filtreDynamique", filterPerson);
        MappingJacksonValue personFiltered = new MappingJacksonValue(personGot);
        personFiltered.setFilters(listFilters);

        return ResponseEntity.ok(personFiltered);
    }

    //***************************************************************************************************
    // REQUETES POST
    //***************************************************************************************************

    // Ajouter une personne
    @PostMapping(value = "/person")
    public ResponseEntity<String> addPerson(@RequestBody Person person) {

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

    //***************************************************************************************************
    // REQUETES PUT
    //***************************************************************************************************

    // Mettre à jour une personne existante (pour le moment, supposons que le prénom et le nom de
    // famille ne changent pas, mais que les autres champs peuvent être modifiés)
    @PutMapping(value = "/person")
    public ResponseEntity<String> updatePerson(@RequestBody Person person) {

        if (!personDao.update(person)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();
    }

    //***************************************************************************************************
    // REQUETES DELETE
    //***************************************************************************************************

    // Supprimer une personne (utilisez une combinaison de prénom et de nom comme identificateur unique).
    @DeleteMapping(value = "/person/{firstName}/{lastName}")
    public ResponseEntity<String> deletePerson(@PathVariable String firstName, @PathVariable  String lastName) {

        if (!personDao.delete(firstName, lastName)) {
            //Si la personne n'existe pas dans le fichier : on renvoie le code : "204 No Content"
            return ResponseEntity.noContent().build();
        }
        // On renvoie le code "200 OK"
        return ResponseEntity.ok().build();

    }


}

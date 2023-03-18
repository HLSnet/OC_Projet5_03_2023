package com.safetynet.SafetynetAlerts.web.repository;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.safetynet.SafetynetAlerts.web.model.Firestation;
import com.safetynet.SafetynetAlerts.web.model.Medicalrecord;
import com.safetynet.SafetynetAlerts.web.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public   List<Person> persons = new ArrayList();
    public   List<Firestation> firestations= new ArrayList();
    public   List<Medicalrecord> medicalrecords= new ArrayList();
    private ObjectMapper mapper = new ObjectMapper();

    String  JSONFILE_PATHNAME = "src\\main\\resources\\persons_firestations_medicalrecords.json";
    public DataManager() {

        try {
            JsonNode rootNode = mapper.readTree(new File("src\\main\\resources\\persons_firestations_medicalrecords.json"));

            // Get persons
            JsonNode personsNode = rootNode.get("persons");

            for (JsonNode personNode : personsNode) {
                Person person = mapper.treeToValue(personNode, Person.class);
                persons.add(person);
            }

            // Get firestations
            JsonNode firestationsNode = rootNode.get("firestations");

            for (JsonNode firestationNode : firestationsNode) {
                Firestation firestation = mapper.treeToValue(firestationNode, Firestation.class);
                firestations.add(firestation);
            }

            // Get medicalrecords
            JsonNode medicalrecordsNode = rootNode.get("medicalrecords");

            for (JsonNode medicalrecordNode : medicalrecordsNode) {
                Medicalrecord medicalrecord = mapper.treeToValue(medicalrecordNode, Medicalrecord.class);
                medicalrecords.add(medicalrecord);
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


     try {
         JsonNode rootNode = mapper.readTree(new File("src\\main\\resources\\persons_firestations_medicalrecords_test.json"));

         // On memorise le node person
         JsonNode personsNode = rootNode.get("persons");

         // On supprime le node person dans le treenode
         ((ObjectNode) rootNode).remove("persons");

         // On enregistre dans un fichier json le treenode (sans la liste de Person)
         mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src\\main\\resources\\persons_firestations_medicalrecords_test_bis.json"), rootNode);

         // On déclare une liste de JsonNode
         ArrayNode  listpersonsNode = mapper.createArrayNode();

         // On transfère la liste d'object de classe Person dans la liste de JsonNode
         for (Person person : persons) {
             listpersonsNode.add( mapper.valueToTree(person));
         }

         // On ajoute la liste de JsonNode dans le treenode
         ((ObjectNode) rootNode).set("persons", listpersonsNode);

         // On enregistre dans un fichier json le treenode (avec la liste de Person)
         mapper.writerWithDefaultPrettyPrinter().writeValue(new File("src\\main\\resources\\persons_firestations_medicalrecords_test_ter.json"), rootNode);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> readFromJsonFileToList(String nodeName, Class<T> objectToParse) {

        List<T> list = new ArrayList<>();
        try {
            JsonNode rootNode = mapper.readTree(new File(JSONFILE_PATHNAME));

            JsonNode objectsNode = rootNode.get(nodeName);


            for (JsonNode objectNode : objectsNode) {
                T object = mapper.treeToValue(objectNode, objectToParse);
                list.add(object);
            }

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}

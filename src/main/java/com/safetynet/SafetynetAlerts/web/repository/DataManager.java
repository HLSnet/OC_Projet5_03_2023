package com.safetynet.SafetynetAlerts.web.repository;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.safetynet.SafetynetAlerts.web.constants.DBConstants.JSONFILE_PATHNAME;

public class DataManager {

    private ObjectMapper mapper = new ObjectMapper();





    public <T> List<T> readFromJsonFileToList(String nodeName, Class<T> tClass) {

        List<T> listObjects = new ArrayList<>();
        try {
            JsonNode rootNode = mapper.readTree(new File(JSONFILE_PATHNAME));

            // On récupère le JsonNode (qui est une liste )
            JsonNode listNodes = rootNode.get(nodeName);

            // On remplit la liste des objets à récupérer à partir de la liste de JsonNode
            for (JsonNode node : listNodes) {
                T object = mapper.treeToValue(node, tClass);
                listObjects.add(object);
            }

        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listObjects;
    }


    public  <T> void writeListToJsonFile(String nodeName,  List<T> listObjects) {

        try {
            JsonNode rootNode = mapper.readTree(new File(JSONFILE_PATHNAME));

            // On supprime le node dans le treenode
            ((ObjectNode) rootNode).remove(nodeName);

            // On déclare une liste de JsonNode
            ArrayNode  listNodes = mapper.createArrayNode();

            // On transfère la liste d'object dans la liste de JsonNode
            for (T object : listObjects) {
                listNodes.add( mapper.valueToTree(object));
            }

            // On ajoute la liste de JsonNode dans le treenode
            ((ObjectNode) rootNode).set(nodeName, listNodes);

            // On enregistre dans un fichier json le treenode (avec la liste de Person)
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(JSONFILE_PATHNAME), rootNode);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

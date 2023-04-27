package com.safetynet.safetynetalerts.repository;


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

/**
 *  Classe permettant de récupérer ou sauvegarder des listes d'objets
 *  contenues dans le fichier json passé en paramètre à l'instanciation de l'objet
 *
 */
public class JsonFileIO {

    private static String jasonFilePathname;

    static ObjectMapper mapper;

    public JsonFileIO(String jasonFilePathname) {
        this.jasonFilePathname = jasonFilePathname;
        mapper = new ObjectMapper();
    }

    /**
     * Method returning the list of objets from the json file
     *
     * @param name of the objet as written in the json file (= node name)
     * @param class of the objet
     * @return : the list of objets
     */
    public static <T> List<T> readFromJsonFileToList(String nodeName, Class<T> tClass) {

        List<T> listObjects = new ArrayList<>();
        try {
            JsonNode rootNode = mapper.readTree(new File(jasonFilePathname));

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

    /**
     * Method writting  the list of objets in the json file
     *
     * @param name of the objet as written in the json file (= node name)
     * @param the list of objets
     */
    public  static <T> void writeListToJsonFile(String nodeName,  List<T> listObjects) {

        try {
            JsonNode rootNode = mapper.readTree(new File(jasonFilePathname));

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
            mapper.writerWithDefaultPrettyPrinter().writeValue(new File(jasonFilePathname), rootNode);

        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

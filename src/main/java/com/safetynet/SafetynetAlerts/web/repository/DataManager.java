package com.safetynet.SafetynetAlerts.web.repository;


import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.safetynet.SafetynetAlerts.web.model.Firestation;
import com.safetynet.SafetynetAlerts.web.model.Medicalrecord;
import com.safetynet.SafetynetAlerts.web.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {
    public  static List<Person> persons = new ArrayList();
    public  static List<Firestation> firestations= new ArrayList();
    public  static List<Medicalrecord> medicalrecords= new ArrayList();
    private ObjectMapper mapper = new ObjectMapper();

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
    }

}

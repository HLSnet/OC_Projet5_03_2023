package com.safetynet.safetynetalerts.repository;


import com.safetynet.safetynetalerts.model.Firestation;


import java.util.List;

public interface FirestationDao {

    List<Firestation> findAll();

    Firestation findByName(String firstName, String lastName);

    Boolean save(Firestation firestation);

    Boolean update(Firestation firestation);

    Boolean delete(Firestation firestation);
}
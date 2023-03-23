package com.safetynet.SafetynetAlerts.dao;


import com.safetynet.SafetynetAlerts.model.Firestation;


import java.util.List;

public interface FirestationDao {

    List<Firestation> findAll();

    Firestation save(Firestation firestation);

    Firestation update(Firestation firestation);

    Firestation delete(Firestation firestation);
}
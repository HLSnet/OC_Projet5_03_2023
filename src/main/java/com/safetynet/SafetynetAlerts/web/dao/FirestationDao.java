package com.safetynet.SafetynetAlerts.web.dao;


import com.safetynet.SafetynetAlerts.web.model.Firestation;


import java.util.List;

public interface FirestationDao {

    List<Firestation> findAll();

    Firestation save(Firestation firestation);

    Firestation update(Firestation firestation);

    Firestation delete(Firestation firestation);
}
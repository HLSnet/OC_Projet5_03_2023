package com.safetynet.SafetynetAlerts.web.dao;

import com.safetynet.SafetynetAlerts.web.model.Firestation;
import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class FirestationDaoImpl implements FirestationDao{
    @Override
    public List<Firestation> findAll() {
        return DataManager.firestations;
    }

    @Override
    public Firestation save(Firestation firestation) {
        return null;
    }

    @Override
    public Firestation update(Firestation firestation) {
        return null;
    }

    @Override
    public Firestation delete(Firestation firestation) {
        return null;
    }
}
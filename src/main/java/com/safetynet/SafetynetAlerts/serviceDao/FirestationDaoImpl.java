package com.safetynet.SafetynetAlerts.serviceDao;

import com.safetynet.SafetynetAlerts.model.Firestation;
import com.safetynet.SafetynetAlerts.repository.JasonFileIO;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.safetynet.SafetynetAlerts.constants.DBConstants.FIRESTATION;

@Repository
public class FirestationDaoImpl implements FirestationDao{
    @Override
    public List<Firestation> findAll() {
        return JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
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
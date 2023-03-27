package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.FIRESTATION;


@Repository
public class FirestationDaoImpl implements FirestationDao {
    @Override
    public List<Firestation> findAll() {
        return JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
    }

    @Override
    public Firestation findByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Boolean save(Firestation firestation) {
        return null;
    }

    @Override
    public Boolean update(Firestation firestation) {
        return null;
    }

    @Override
    public Boolean delete(Firestation firestation) {
        return null;
    }
}
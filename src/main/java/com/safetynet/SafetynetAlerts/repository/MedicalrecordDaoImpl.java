package com.safetynet.safetynetalerts.repository;


import com.safetynet.safetynetalerts.model.Medicalrecord;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.MEDICAL_RECORD;


@Repository
public class MedicalrecordDaoImpl implements MedicalrecordDao {

    @Override
    public List<Medicalrecord> findAll() {
        return JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
    }

    @Override
    public Medicalrecord findByName(String firstName, String lastName) {
        return null;
    }

    @Override
    public Boolean save(Medicalrecord medicalrecord) {
        return null;
    }

    @Override
    public Boolean update(Medicalrecord medicalrecord) {
        return null;
    }

    @Override
    public Boolean delete(Medicalrecord medicalrecord) {
        return null;
    }
}
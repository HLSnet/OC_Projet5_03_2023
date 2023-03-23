package com.safetynet.SafetynetAlerts.dao;

import com.safetynet.SafetynetAlerts.model.Medicalrecord;
import com.safetynet.SafetynetAlerts.utilities.JasonFileIO;
import org.springframework.stereotype.Repository;

import java.util.List;


import static com.safetynet.SafetynetAlerts.constants.DBConstants.MEDICAL_RECORD;

@Repository
public class MedicalrecordDaoImpl implements MedicalrecordDao{
    @Override
    public List<Medicalrecord> findAll() {
        return JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
    }

    @Override
    public Medicalrecord save(Medicalrecord medicalrecord) {
        return null;
    }

    @Override
    public Medicalrecord update(Medicalrecord medicalrecord) {
        return null;
    }

    @Override
    public Medicalrecord delete(Medicalrecord medicalrecord) {
        return null;
    }
}
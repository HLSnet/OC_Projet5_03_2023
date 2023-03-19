package com.safetynet.SafetynetAlerts.web.dao;

import com.safetynet.SafetynetAlerts.web.model.Medicalrecord;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.SafetynetAlerts.SafetynetAlertsApplication.dataManager;
import static com.safetynet.SafetynetAlerts.web.constants.DBConstants.MEDICAL_RECORD;

@Repository
public class MedicalrecordDaoImpl implements MedicalrecordDao{
    @Override
    public List<Medicalrecord> findAll() {
        return dataManager.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
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
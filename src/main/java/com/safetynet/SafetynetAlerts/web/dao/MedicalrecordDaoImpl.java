package com.safetynet.SafetynetAlerts.web.dao;

import com.safetynet.SafetynetAlerts.web.model.Medicalrecord;
import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.SafetynetAlerts.SafetynetAlertsApplication.dataManager;

@Repository
public class MedicalrecordDaoImpl implements MedicalrecordDao{
    @Override
    public List<Medicalrecord> findAll() {
        return dataManager.medicalrecords;
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
package com.safetynet.SafetynetAlerts.web.dao;

import com.safetynet.SafetynetAlerts.web.model.Medicalrecord;


import java.util.List;

public interface MedicalrecordDao {

    List<Medicalrecord> findAll();

    Medicalrecord save(Medicalrecord medicalrecord);

    Medicalrecord update(Medicalrecord medicalrecord);

    Medicalrecord delete(Medicalrecord medicalrecord);
}
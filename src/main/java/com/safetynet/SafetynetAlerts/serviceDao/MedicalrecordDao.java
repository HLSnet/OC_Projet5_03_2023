package com.safetynet.SafetynetAlerts.serviceDao;

import com.safetynet.SafetynetAlerts.model.Medicalrecord;


import java.util.List;

public interface MedicalrecordDao {

    List<Medicalrecord> findAll();

    Medicalrecord save(Medicalrecord medicalrecord);

    Medicalrecord update(Medicalrecord medicalrecord);

    Medicalrecord delete(Medicalrecord medicalrecord);
}
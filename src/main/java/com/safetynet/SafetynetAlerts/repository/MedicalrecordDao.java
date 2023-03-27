package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Medicalrecord;


import java.util.List;

public interface MedicalrecordDao {

    List<Medicalrecord> findAll();

    Medicalrecord findByName(String firstName, String lastName);

    Boolean save(Medicalrecord medicalrecord);

    Boolean update(Medicalrecord medicalrecord);

    Boolean delete(Medicalrecord medicalrecord);
}
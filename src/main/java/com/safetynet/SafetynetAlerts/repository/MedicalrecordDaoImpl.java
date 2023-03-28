package com.safetynet.safetynetalerts.repository;


import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.model.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;
import static com.safetynet.safetynetalerts.constants.DBConstants.ADDED;


@Repository
public class MedicalrecordDaoImpl implements MedicalrecordDao {

    @Override
    public List<Medicalrecord> findAll() {
        return JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
    }

    @Override
    public Medicalrecord findByName(String firstName, String lastName) {
        List<Medicalrecord> medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        for (Medicalrecord medicalrecord : medicalrecords){
            if (medicalrecord.getFirstName().equals(firstName)  && medicalrecord.getLastName().equals(lastName)){
                return  medicalrecord;
            }
        }
        return null;
    }


    @Override
    public Boolean save(Medicalrecord medicalrecordToAdd) {
        List<Medicalrecord> medicalrecords = JasonFileIO.readFromJsonFileToList(MEDICAL_RECORD, Medicalrecord.class);
        for (Medicalrecord medicalrecord : medicalrecords){
            if (medicalrecord.getFirstName().equals(medicalrecordToAdd.getFirstName())  && medicalrecord.getLastName().equals(medicalrecordToAdd.getLastName())){
                // Le dossier médical existe dans le fichier donc pas d'ajout
                return  NOT_ADDED;
            }
        }
        medicalrecords.add(medicalrecordToAdd);
        JasonFileIO.writeListToJsonFile(MEDICAL_RECORD, medicalrecords);
        return ADDED;
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
package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Medicalrecord;
import com.safetynet.safetynetalerts.repository.MedicalrecordDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MedicalRecordController {

    // Remarque : attributs définis en private final afin que Spring se charge d'en fabriquer une instance
    // à préfèrer à @Autowired
    private final MedicalrecordDao medicalrecordDao;

    public MedicalRecordController(MedicalrecordDao medicalrecordDao) {
        this.medicalrecordDao = medicalrecordDao;
    }

    // Afficher toutes les medicalrecord
    @GetMapping(value = "/medicalrecord")
    public List<Medicalrecord> getMedicalrecord() {
        return medicalrecordDao.findAll();
    }



//    // Ajouter un dossier médical
//    @PostMapping(value = "/adress")
//    public void addMedicalRecord(@RequestBody String adress) {
//        MedicalRecord.save(firestation);
//    }
//
//    // Mettre à jour un dossier médical existant (comme évoqué précédemment, supposer que
//    // le prénom et le nom de famille ne changent pas)
//    @PutMapping(value = "/person")
//    public void UpdateMedicalRecord(@RequestBody Person person) {
//        MedicalRecord.update(person);
//    }
//
//    // Supprimer un dossier médical (utilisez une combinaison de prénom et de nom comme
//    // identificateur unique)
//    @DeleteMapping(value = "/station")
//    public void deleteMedicalRecord(@RequestBody Person person) {
//        MedicalRecord.delete(person);
//    }


}

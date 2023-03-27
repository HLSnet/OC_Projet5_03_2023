package com.safetynet.safetynetalerts.controller;

import com.safetynet.safetynetalerts.model.Firestation;
import com.safetynet.safetynetalerts.repository.FirestationDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FirestationController {

    // Remarque : attributs définis en private final afin que Spring se charge d'en fabriquer une instance (à préfèrer à @Autowired)
    private final FirestationDao firestationDao;

    public FirestationController (FirestationDao firestationDao) {
        this.firestationDao = firestationDao;
    }


    // Afficher toutes les firestation
    @GetMapping(value = "/firestation")
    public List<Firestation> getFirestation() {
        return firestationDao.findAll();
    }




//    // Ajouter un mapping caserne/adresse
//    @PostMapping(value = "/adress")
//    public void addFirestation(@RequestBody String adress) {
//        FirestationDao.save(firestation);
//    }
//
//    // Mettre à jour le numéro de la caserne de pompiers d'une adresse
//    @PutMapping(value = "/person")
//    public void UpdateFirestation(@RequestBody Person person) {
//        FirestationDao.update(person);
//    }
//
//    // Supprimer le mapping d'une caserne ou d'une adresse
//    @DeleteMapping(value = "/station")
//    public void deleteFirestation(@RequestBody Person person) {
//        FirestationDao.delete(person);
//    }

}

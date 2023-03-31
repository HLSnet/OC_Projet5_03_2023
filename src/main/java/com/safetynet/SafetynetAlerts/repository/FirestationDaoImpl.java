package com.safetynet.safetynetalerts.repository;

import com.safetynet.safetynetalerts.model.Firestation;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.safetynet.safetynetalerts.constants.DBConstants.*;


@Repository
public class FirestationDaoImpl implements FirestationDao {

//***********************************************************************************************************
//  UTILITAIRE
//***********************************************************************************************************
    /**
     * Objectif :
     *
     * 1/ Supprimer 2 types de redondances de données:
     *
     *  - redondance de mapping caserne/adresse
     *  - redondance d'adresse (une adresse ne peut être desservie que par une unique caserne de pompier)
     *
     * 2/ Rendre la liste plus lisible en groupant les adresses par station
     *
     * Remarque : appelée 1 fois au démarrage de l'application, l'application gérant
     *            l'unicité des données transmises durant son utilisation.
     */
    public static void checkIntegrityJsonFileAndSort() {
        // On recupère la liste des firestation
        List<Firestation> firestationList = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);

        // On nettoie la liste des doublons
        Set<Firestation> firestationsSet = new HashSet<>(firestationList);
        List<Firestation> firestationListCleaned = new ArrayList<>(firestationsSet);

        // On trie la liste par stations
        Collections.sort(firestationListCleaned);

        // On supprime les doublons de station pour une même adresse (on garde la 1ère station lue dans la liste)
        List<Firestation> firestationListChecked = new ArrayList<>();
        List<String> ListAdress = new ArrayList<>();
        for (Firestation firestation : firestationListCleaned){
            if (!ListAdress.contains(firestation.getAddress()) ){
                ListAdress.add(firestation.getAddress());
                firestationListChecked.add(firestation);
            }
        }
        // On écrit dans le fichier la liste nettoyée de ses doublons
        JasonFileIO.writeListToJsonFile(FIRESTATION, firestationListChecked);
    }


//***********************************************************************************************************
//  DAO
//***********************************************************************************************************

    @Override
    public List<Firestation> findAll() {
        // On recupère la liste des firestation
        List<Firestation> firestationList = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);
        return  firestationList;
    }

    @Override
    public Firestation  findByAdress(String adress) {
            // On recupère la liste des firestation
            List<Firestation> firestationList = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);

            for (Firestation firestation : firestationList){
                if (firestation.getAddress().equals(adress)){
                    return firestation;
                }
            }
            return null;
        }


    @Override
    public List<Firestation>  findByStation(int station) {
        // On recupère la liste des firestation
        List<Firestation> firestationList = JasonFileIO.readFromJsonFileToList(FIRESTATION, Firestation.class);

        // On crée la liste de firestation à retourner
        List<Firestation> firestationSelected = new ArrayList<>();
        for (Firestation firestation : firestationList){
            if (firestation.getStation() == station ){
                firestationSelected.add(firestation);
            }
        }
        return firestationSelected;
    }

    @Override
    public Boolean save(Firestation firestation) {
        return false;
    }


    @Override
    public Boolean update(Firestation firestation) {
        return false;
    }

    @Override
    public Boolean deleteStation(int station) {
        return false;
    }

    @Override
    public Boolean deleteAdress(String adress) {
        return false;
    }

}
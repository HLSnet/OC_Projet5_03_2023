package com.safetynet.safetynetalerts.repository;


import com.safetynet.safetynetalerts.model.Firestation;


import java.util.List;

public interface FirestationDao {

    List<Firestation> findAll();

    Firestation findByAddress(String adress) ;

    List<Firestation>  findByStation(int station);

    Boolean save(Firestation firestation);

    Boolean update(Firestation firestation);

    Boolean deleteStation(int station);

    Boolean deleteAddress(String adress);
}
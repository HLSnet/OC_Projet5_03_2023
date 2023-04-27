package com.safetynet.safetynetalerts;

import com.safetynet.safetynetalerts.repository.FirestationDaoImpl;
import com.safetynet.safetynetalerts.repository.JsonFileIO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.safetynet.safetynetalerts.constants.DBConstants.JSONFILE_PATHNAME;


@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// On indique le fichier json à utiliser dans l'application
		new JsonFileIO(JSONFILE_PATHNAME);
		// On supprime les doublons présents dans la section firestation du fichier json
		FirestationDaoImpl.checkIntegrityJsonFileFirestationAndSort();
	}
}

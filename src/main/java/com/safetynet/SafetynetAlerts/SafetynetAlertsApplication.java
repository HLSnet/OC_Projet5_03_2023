package com.safetynet.SafetynetAlerts;

import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetAlertsApplication {

	// On crée les listes d'objet parsées à partir du fichier json
	public static DataManager dataManager;

	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
		dataManager = new DataManager();
	}

}

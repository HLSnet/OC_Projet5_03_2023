package com.safetynet.SafetynetAlerts;

import com.safetynet.SafetynetAlerts.utilities.JasonFileIO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.safetynet.SafetynetAlerts.constants.DBConstants.JSONFILE_PATHNAME;

@SpringBootApplication
public class SafetynetAlertsApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(SafetynetAlertsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// On indique le fichier json à utiliser dans l'application (notammment par les DAO)
		new JasonFileIO(JSONFILE_PATHNAME);
	}
}

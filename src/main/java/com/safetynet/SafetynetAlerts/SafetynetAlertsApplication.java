package com.safetynet.SafetynetAlerts;

import com.safetynet.SafetynetAlerts.web.repository.DataManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SafetynetAlertsApplication {

	public static void main(String[] args) {

		SpringApplication.run(SafetynetAlertsApplication.class, args);
		new DataManager();
	}

}

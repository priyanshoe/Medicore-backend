package com.medicore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MedicoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(MedicoreApplication.class, args);
		System.err.println("medicore server running");
	}
	
	@GetMapping({"/","/health"})
	public String checkServer() {
		return "medicore server is running";
	}

}

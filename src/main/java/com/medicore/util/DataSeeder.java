package com.medicore.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {

	@Bean
	CommandLineRunner seedUsers(DataSeedService seedService) {
		return args -> seedService.seedUser();
	}
}

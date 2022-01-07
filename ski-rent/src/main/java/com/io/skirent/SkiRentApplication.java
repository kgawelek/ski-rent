package com.io.skirent;

import com.io.skirent.equipment.Equipment;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
public class SkiRentApplication implements RepositoryRestConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(SkiRentApplication.class, args);
	}

	@Override
	public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config, CorsRegistry cors) {
		config.exposeIdsFor(Equipment.class); // chyba poprawnie dodaje do configu springa, ale nic to nie zmienia
		boolean b = config.isIdExposedFor(Equipment.class); // zwraca true
	}
}

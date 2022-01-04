package com.fatec.sicm.mantemCliente.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.ports.ClienteRepository;
@Configuration
public class LoadDatabase {
	Logger log = LogManager.getLogger(this.getClass());
	 @Bean
	  CommandLineRunner initDatabase(ClienteRepository repository) {

	    return args -> {
	      log.info("Preloading " + repository.save(new Cliente("Jose", "10/02/1960", "M", "99504993052", "04280130", "Rua Aguia de Haia", "2983")));
	      log.info("Preloading " + repository.save(new Cliente("Jose", "20/02/1966", "M", "43011831084", "08545160", "Rua Joao Alfredo", "157")));
	      
	    };
	  }
}

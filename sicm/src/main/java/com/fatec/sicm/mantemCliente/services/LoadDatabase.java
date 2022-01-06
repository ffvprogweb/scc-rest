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
			Cliente cliente1 = new Cliente("Jose", "10/02/1960", "M", "99504993052", "04280130", "2983");
			cliente1.setDataCadastro("18/05/2020");
			log.info("Preloading " + repository.save(cliente1));
			Cliente cliente2 = new Cliente("Jose", "04/10/1974", "M", "43011831084", "08545160", "2983");
			cliente2.setDataCadastro("18/07/2019");
			log.info("Preloading " + repository.save(cliente2));

		};
	}
	
}

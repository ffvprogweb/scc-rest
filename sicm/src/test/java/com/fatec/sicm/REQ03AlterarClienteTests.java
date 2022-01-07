package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sicm.mantemCliente.ports.ClienteRepository;
@SpringBootTest
class REQ03AlterarClienteTests {
	private Logger logger = LogManager.getLogger(this.getClass());;
	@Autowired
	ClienteRepository repository;
	
	private Validator validator;
	private ValidatorFactory validatorFactory;
	@ParameterizedTest // pode-se criar o arquivo req03_atualizar_cliente.csv
	@CsvSource({
		//String nome, String dataNascimento, String sexo, String cpf,  String cep, String complemento
		    "Jose da Silva,20/10/1965,M,33476818004,02111031, 232",
		    "Jose da Silva,20/10/1965,M,27102305001,02301150, 232",
		    "Jose da Silva,20/10/1965,M,96535550060,04849022, 232",
			})
	public void atualizar_cliente(String isbn, String titulo, String autor, String re) {
	
	
	}

}

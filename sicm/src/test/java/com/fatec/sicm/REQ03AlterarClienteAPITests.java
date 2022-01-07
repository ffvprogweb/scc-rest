package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

import com.fatec.sicm.mantemCliente.services.ClienteServicoI;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ03AlterarClienteAPITests {
	String urlBase = "/api/v1/clientes";
	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	ClienteServicoI servico;
	@Test
	void ct01_quando_cliente_cadastrado_retorna_cliente_modificado() {
		fail("Not yet implemented");
	}

}

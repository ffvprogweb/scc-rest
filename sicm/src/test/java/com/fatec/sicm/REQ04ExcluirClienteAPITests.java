package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.ports.ClienteService;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ04ExcluirClienteAPITests {
	
	String urlBase = "/api/v1/clientes";
	@Autowired
	ClienteService servico;
	@Autowired
	TestRestTemplate testRestTemplate;
	@Test
	void ct01_dado_que_o_cliente_esta_cadastrado_quando_remove_cosulta_retorna_nao_encontrado() {
		//Dado que o cliente esta cadastrado
		Optional<Cliente> umCliente = servico.consultaPorId(1L);
		Cliente cliente = umCliente.get();
		//Quando solicita a exclusão
		ResponseEntity <Cliente> resposta = testRestTemplate.exchange(urlBase + "/id/{id}", HttpMethod.DELETE, null, Cliente.class, cliente.getId());
		// Entao retorna nao encontrado
		assertEquals(HttpStatus.NO_CONTENT, resposta.getStatusCode());
		Optional<Cliente> resultado = servico.consultaPorId(cliente.getId());
		assertTrue(resultado.isEmpty());
	}
	@Test
	void ct02_dado_que_o_cliente_nao_esta_cadastrado_quando_remove_retorna_nao_encontrado() {
		//Dado que o cliente esta cadastrado
		//Quando solicita a exclusão
		ResponseEntity <?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}", HttpMethod.DELETE, null, Cliente.class, 99L);
		// Entao retorna nao encontrado
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		
	}
}

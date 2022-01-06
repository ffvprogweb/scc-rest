package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fatec.sicm.mantemCliente.model.Cliente;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ02ConsultarLivroAPITests {
	String urlBase = "/api/v1/clientes";
	@Autowired
	TestRestTemplate testRestTemplate;
	
	@Test
	void ct01_quando_consulta_id_cadastrado_retorna_detalhes_do_cliente() {
		//Dado que o cliente esta cadastrado
		Cliente cliente = new Cliente("Jose", "10/02/1960", "M", "99504993052", "04280130", "2983");
		cliente.setDataCadastro("18/05/2020");
		cliente.setId(1L);
		//Quando consulta
		ResponseEntity<Cliente> resposta = testRestTemplate.getForEntity(urlBase + "/id/" + cliente.getId(), Cliente.class);
		//Entao retorna os detalhes do cliente
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(cliente, resposta.getBody());
	}

	@Test
	void ct02_quando_consulta_id_nao_cadastrado_retorna_not_found() {
		//Dado que o id de cliente nao esta cadastrado
		//Quando consulta
		ResponseEntity<Cliente> resposta = testRestTemplate.getForEntity(urlBase + "/id/" + 99, Cliente.class);
		//Entao retorna not found
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}
	@Test
	void ct03_quando_consulta_cpf_cadastrado_retorna_detalhes_do_cliente() {
		//Dado que o cliente esta cadastrado
		Cliente cliente = new Cliente("Jose", "10/02/1960", "M", "99504993052", "04280130", "2983");
		cliente.setDataCadastro("18/05/2020");
		cliente.setId(1L);
		//Quando consulta
		ResponseEntity<Cliente> resposta = testRestTemplate.getForEntity(urlBase + "/" + cliente.getCpf(), Cliente.class);
		//Entao retorna os detalhes do cliente
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(cliente, resposta.getBody());
	}
	@Test
	void ct04_quando_consulta_cpf_nao_cadastrado_retorna_not_found() {
		//Dado que o cpf de cliente nao esta cadastrado
		//Quando consulta
		ResponseEntity<Cliente> resposta = testRestTemplate.getForEntity(urlBase + "/" + 99, Cliente.class);
		//Entao retorna not found
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

}

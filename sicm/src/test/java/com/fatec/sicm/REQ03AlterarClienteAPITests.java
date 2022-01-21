package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.fatec.sicm.mantemCliente.model.Cliente;

import com.fatec.sicm.mantemCliente.services.ClienteServicoI;
import com.google.gson.Gson;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ03AlterarClienteAPITests {
	Logger logger = LogManager.getLogger(this.getClass());
	String urlBase = "/api/v1/clientes";
	@Autowired
	TestRestTemplate testRestTemplate;
	@Autowired
	ClienteServicoI servico;
	@Test
	void ct01_quando_cliente_cadastrado_retorna_cliente_modificado() {
		Optional<Cliente> umCliente = servico.consultaPorId(1L);
		Cliente clienteModificado = umCliente.get();
		clienteModificado.setNome("Jose da Silva");
		System.out.println(clienteModificado.toString());

		HttpEntity<Cliente> httpEntity = new HttpEntity<>(clienteModificado);
		ResponseEntity<Cliente> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, clienteModificado.getId());
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Cliente cliente = resposta.getBody();
		System.out.println(cliente.toString());
		assertEquals("Jose da Silva", resposta.getBody().getNome());
	}
	@Test
	void ct02_quando_cliente_nao_cadastrado_retorna_erro() {
		logger.info(">> caso de teste ct02 iniciado");
		Optional<Cliente> umCliente = servico.consultaPorId(1L);
		Cliente cliente = umCliente.get();
		cliente.setId(99L);
		System.out.println(cliente.toString());
		HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);
		ResponseEntity<?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());
		//ResponseEntity<List<String>> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<List<String>>() {}, cliente.getId());
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		//assertTrue(cliente.equals(resposta.getBody()));
	}
	@Test
	void ct03_dado_que_o_cliente_esta_cadastrado_quando_altera_para_cep_nao_cadastrado_retorna_erro() {
		
		Optional<Cliente> umCliente = servico.consultaPorId(1L);
		Cliente cliente = umCliente.get();
		cliente.setCep("00");
		Gson dadosDeEntrada = new Gson();
		String entity = dadosDeEntrada.toJson(cliente);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(entity,headers);
		//HttpEntity<Cliente> httpEntity = new HttpEntity<>(cliente);
		//ResponseEntity<?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());
		ResponseEntity<String> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, String.class, cliente.getId());
		//ResponseEntity<List<String>> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, new ParameterizedTypeReference<List<String>>() {}, cliente.getId());
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
	}
}

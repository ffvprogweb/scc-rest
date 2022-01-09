package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.model.Endereco;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class REQ01ConsulatCepAPITests {
	@Autowired
	TestRestTemplate testRestTemplate;
	@Test
	void ct01_quando_cep_cadastrado_retorna_ok() {
		
		String url = "https://viacep.com.br/ws/{cep}/json/";
		ResponseEntity<String> resposta = null;
		resposta = testRestTemplate.getForEntity(url, String.class, "04280130");
		//ResponseEntity<?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());
		//ResponseEntity<List<String>> resposta = testRestTemplate.exchange(url ,HttpMethod.PUT, null, new ParameterizedTypeReference<List<String>>() {}, "00");
		
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		
	}
	@Test
	void ct02_quando_cep_nao_cadastrado_retorna_erro() {
		
		String url = "https://viacep.com.br/ws/{cep}/json/";
		ResponseEntity<String> resposta = testRestTemplate.getForEntity(url, String.class, "0428013");
		//ResponseEntity<?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());
		//ResponseEntity<List<String>> resposta = testRestTemplate.exchange(url ,HttpMethod.PUT, null, new ParameterizedTypeReference<List<String>>() {}, "00");
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
	}
	@Test
	void ct03_quando_cep_nao_cadastrado_retorna_erro() {
		
		String url = "https://viacep.com.br/ws/{cep}/json/";
		ResponseEntity<String> resposta = testRestTemplate.getForEntity(url, String.class, "");
		//ResponseEntity<?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());
		//ResponseEntity<List<String>> resposta = testRestTemplate.exchange(url ,HttpMethod.PUT, null, new ParameterizedTypeReference<List<String>>() {}, "00");
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
	}
	@Test
	void ct04_quando_cep_nao_cadastrado_retorna_erro() {
		
		String url = "https://viacep.com.br/ws/{cep}/json/";
		ResponseEntity<String> resposta = testRestTemplate.getForEntity(url, String.class, "%");
		//ResponseEntity<?> resposta = testRestTemplate.exchange(urlBase + "/id/{id}",HttpMethod.PUT, httpEntity, Cliente.class, cliente.getId());
		//ResponseEntity<List<String>> resposta = testRestTemplate.exchange(url ,HttpMethod.PUT, null, new ParameterizedTypeReference<List<String>>() {}, "00");
		
		assertEquals(HttpStatus.BAD_REQUEST, resposta.getStatusCode());
		
	}
}

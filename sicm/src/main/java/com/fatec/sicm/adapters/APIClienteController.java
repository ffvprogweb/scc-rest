package com.fatec.sicm.adapters;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.model.Endereco;
import com.fatec.sicm.mantemCliente.ports.ClienteService;
import com.google.gson.Gson;

@RestController // controller deve retornar valores escritos diretamente no body da resposta
@RequestMapping("/api/v1/clientes")
public class APIClienteController {
	//https://stackabuse.com/how-to-return-http-status-codes-in-a-spring-boot-application/
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ClienteService servico;

	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid Cliente cliente, BindingResult result) {
		logger.info(">>>>>> 1. controller cadastrar - post iniciado");
		ResponseEntity<?> response = null;
		if (result.hasErrors()) {
			logger.info(">>>>>> controller create - dados inválidos => " + cliente.toString());
			response = ResponseEntity.badRequest().body("Dados inválidos.");
		} else {
			Optional<Cliente> umCliente = Optional.ofNullable(servico.consultaPorCpf(cliente.getCpf()));
			// Optional<String> logradouro =
			// Optional.ofNullable(servico.obtemEndereco(cliente.getCep()));
			ResponseEntity<String> endereco = consultaCep(cliente.getCep());
			if (umCliente.isEmpty() & endereco.getStatusCode().equals(HttpStatus.OK)) {
				logger.info(">>>>>> controller create - dados validos");
				Gson gson = new Gson();
				Endereco novoEndereco = gson.fromJson(endereco.getBody(), Endereco.class);
				cliente.setEndereco(novoEndereco.getLogradouro());
				response = ResponseEntity.status(HttpStatus.CREATED).body(servico.save(cliente));

			} else {
				response = ResponseEntity.badRequest().body("Cliente já cadastrado ou CEP inválido");
				logger.info(">>>>>> controller create - cadastro realizado com sucesso");

			}
		}
		return response;
	}

	@GetMapping
	public ResponseEntity<List<Cliente>> consultaTodos() {
		logger.info(">>>>>> 1. controller consulta todos chamado");
		List<Cliente> listaDeClientes = new ArrayList<Cliente>();
		servico.consultaTodos().forEach(listaDeClientes::add);
		if (listaDeClientes.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(listaDeClientes, HttpStatus.OK);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<Cliente> findByCpf(@PathVariable String cpf) {
		logger.info(">>>>>> 1. controller chamou servico consulta por cpf => " + cpf);
		ResponseEntity<Cliente> response = null;
		Cliente cliente = servico.consultaPorCpf(cpf);
		Optional<Cliente> optCliente = Optional.ofNullable(cliente);
		if (optCliente.isPresent()) {
			response = ResponseEntity.status(HttpStatus.OK).body(optCliente.get());
		} else {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		return response;
	}

	@GetMapping("/id/{id}") // pagina 144 do spring in action
	public ResponseEntity<Cliente> findById(@PathVariable String id) {
		logger.info(">>>>>> 1. controller chamou servico consulta por id => " + id);
		Long ident = Long.parseLong(id);
		Optional<Cliente> cliente = servico.consultaPorId(ident);
		if (cliente.isPresent()) {
			return new ResponseEntity<>(cliente.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<Cliente> update(@PathVariable("id") long id, @RequestBody @Valid Cliente clienteModificado) {//throws URISyntaxException {
		logger.info(">>>>>> 1. controller alterar - put iniciado");
		Optional<Cliente> umCliente = servico.consultaPorId(clienteModificado.getId());
		if (umCliente.isPresent()) {
			logger.info(">>>>>> controller update - cliente cadastrado para put update");
			ResponseEntity<String> endereco = consultaCep(clienteModificado.getCep());
			//logger.info(">>>>>> 1. controller alterar - response - " + endereco.getBody());
			
			if (endereco.getStatusCode().equals(HttpStatus.OK)) {
			
				Gson gson = new Gson();
				Endereco novoEndereco = gson.fromJson(endereco.getBody(), Endereco.class);
				logger.info(">>>>>> controller update - dados validos => " + novoEndereco.getLogradouro());
				clienteModificado.setEndereco(novoEndereco.getLogradouro());
				
				return new ResponseEntity<>(servico.altera(clienteModificado), HttpStatus.OK);
			}
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	@DeleteMapping("/id/{id}")
	public ResponseEntity<String> remover(@PathVariable String id){
		Long ident = Long.parseLong(id);
		Optional<Cliente> cliente = servico.consultaPorId(ident);
		
		if (cliente.isPresent()) {
			servico.delete(ident);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}
		
	}
	//@GetMapping("/cep/{cep}") 
	public ResponseEntity<String> consultaCep(@PathVariable("cep") String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		logger.info(">>>>>> 1. controller consultaCep - " + cep);
		ResponseEntity<String> resposta = null;
		//resposta = template.getForEntity(url, String.class, cep);
		//ResponseEntity<Endereco> response = template.getForEntity(url, Endereco.class, cep);
		//ResponseEntity<String> response = template.getForEntity(url, String.class, cep);
		//ResponseEntity<String> response = template.exchange(url, HttpMethod.GET, null, String.class, cep);
		//Endereco endereco = template.getForObject(url, Endereco.class, cep);
		try {
			resposta = template.getForEntity(url, String.class, cep);
			//logger.info(">>>>>> 3. obtem endereco ==> " + resposta.getStatusCode().toString());

		} catch (ResourceAccessException e) {
			logger.info(">>>>>> consulta CEP erro nao esperado ");
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

		} catch (HttpClientErrorException e) {
			logger.info(">>>>>> consulta CEP inválido erro HttpClientErrorException"	+ e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return resposta;
	}
}

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.ports.ClienteService;

@RestController //controller deve retornar valores escritos diretamente no body da resposta
@RequestMapping("/api/v1/clientes")
public class APIClienteController {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ClienteService servico;
	
	@PostMapping
	public ResponseEntity<?> cadastrar(@RequestBody @Valid Cliente cliente, BindingResult result){
		logger.info(">>>>>> 1. controller cadastrar - post iniciado");
		ResponseEntity<?> response = null;
		if (result.hasErrors()) {
			logger.info(">>>>>> controller create - dados inválidos => " + cliente.toString());
			response = ResponseEntity.badRequest().body("Dados inválidos.");
		} else {
			Optional<Cliente> umCliente = Optional.ofNullable(servico.consultaPorCpf(cliente.getCpf()));
			if (umCliente.isPresent()) {
				logger.info(">>>>>> controller create - cliente já cadastrado");
				response = ResponseEntity.badRequest().body("Cliente já cadastrado");
			} else {
				response = ResponseEntity.status(HttpStatus.CREATED).body(servico.save(cliente));
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
	@GetMapping("/id/{id}") //pagina 144 do spring in action
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
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Cliente clienteModificado, BindingResult result) {
		logger.info(">>>>>> 1. controller cadastrar - post iniciado");
		ResponseEntity<?> response = null;
		if (result.hasErrors()) {
			logger.info(">>>>>> controller create - dados inválidos => " + clienteModificado.toString());
			response = ResponseEntity.badRequest().body("Dados inválidos.");
		} else {
			Optional<Cliente> umCliente = Optional.ofNullable(servico.consultaPorCpf(clienteModificado.getCpf()));
			if (umCliente.isPresent()) {
				logger.info(">>>>>> controller create - cliente já cadastrado");
				response = ResponseEntity.badRequest().body("Cliente já cadastrado");
			} else {
				response = ResponseEntity.status(HttpStatus.CREATED).body(servico.save(clienteModificado));
				logger.info(">>>>>> controller create - cadastro realizado com sucesso");
			}
		}
		return response;
	}
}

package com.fatec.sicm.mantemCliente.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.model.Endereco;
import com.fatec.sicm.mantemCliente.ports.ClienteRepository;
import com.fatec.sicm.mantemCliente.ports.ClienteService;

@Service
public class ClienteServicoI implements ClienteService {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ClienteRepository repository;

	public List<Cliente> consultaTodos() {
		logger.info(">>>>>> servico consultaTodos chamado");
		return repository.findAll();
	}

	@Override
	public Cliente consultaPorCpf(String cpf) {
		logger.info(">>>>>> servico consultaPorCpf chamado");
		return repository.findByCpf(cpf);
	}

	@Override
	public Optional<Cliente> consultaPorId(Long id) {
		logger.info(">>>>>> servico consultaPorId chamado");
		return repository.findById(id);
	}

	@Override
	public Cliente save(Cliente cliente) {
		logger.info(">>>>>> servico save chamado ");
		cliente.obtemDataAtual(new DateTime());
		return repository.save(cliente);
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> servico delete por id chamado");
		repository.deleteById(id);
	}

	@Override
	public Cliente altera(Cliente clienteModificado) {
		logger.info(">>>>>> servico update chamado");
		
		return repository.save(clienteModificado);
	}

	
}

package com.fatec.sicm.mantemCliente.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
		Optional<String> logradouro = Optional.ofNullable(obtemEndereco(cliente.getCep()));
		if (logradouro.isPresent()) {
			cliente.setEndereco(logradouro.get());
		} else {
			cliente.setEndereco("");
		}
		return repository.save(cliente);
	}

	@Override
	public void delete(Long id) {
		logger.info(">>>>>> servico delete por id chamado");

	}

	@Override
	public Cliente update(Cliente clienteModificado) {
		logger.info(">>>>>> servico update chamado");
		Optional<Cliente> umCliente = consultaPorId(clienteModificado.getId());
		Cliente cliente = null;
		if (umCliente.isPresent()) {
			cliente = umCliente.get();

			cliente.setNome(clienteModificado.getNome());
			cliente.setDataNascimento(clienteModificado.getDataNascimento());
			cliente.setSexo(clienteModificado.getSexo());
			cliente.setCep(clienteModificado.getCep());
			cliente.setComplemento(clienteModificado.getComplemento());
			
		}
		return repository.save(cliente);
	}

	public String obtemEndereco(String cep) {
		RestTemplate template = new RestTemplate();
		String url = "https://viacep.com.br/ws/{cep}/json/";
		// Endereco endereco = template.getForObject(url, Endereco.class, cep);
		try {
			ResponseEntity<Endereco> response = template.getForEntity(url, Endereco.class, cep);
			Endereco endereco = response.getBody();
			logger.info(">>>>>> 3. obtem endereco ==> " + response.getStatusCode().toString());
			logger.info(">>>>>> 3. obtem endereco ==> " + endereco.toString());
			return endereco.getLogradouro();
		} catch (ResourceAccessException e) {
			logger.info(">>>>>> servico obtem endereco erro nao esperado ");
			return null;
		}

	}
}

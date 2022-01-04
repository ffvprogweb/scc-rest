package com.fatec.sicm.mantemCliente.services;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.ports.ClienteRepository;
import com.fatec.sicm.mantemCliente.ports.ClienteService;

@Service
public class ClienteServicoI implements ClienteService{
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ClienteRepository repository;
	public List<Cliente> consultaTodos(){
		logger.info(">>>>>> servico consultaTodos chamado");
		return repository.findAll();
	}
	@Override
	public Cliente consultaPorCpf(String cpf) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Optional<Cliente> consultaPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Cliente save(Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Cliente update(Long id, Cliente cliente) {
		// TODO Auto-generated method stub
		return null;
	}

}

package com.fatec.sicm.mantemCliente.ports;

import java.util.List;
import java.util.Optional;

import com.fatec.sicm.mantemCliente.model.Cliente;

public interface ClienteService {
	
	List<Cliente> consultaTodos();
	Cliente consultaPorCpf(String cpf);
	Optional<Cliente> consultaPorId(Long id);
	Cliente save(Cliente cliente);
	void delete (Long id);
	Cliente update ( Cliente cliente);

}

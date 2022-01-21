package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.ports.ClienteRepository;
import com.fatec.sicm.mantemCliente.ports.ClienteService;
import com.fatec.sicm.mantemCliente.services.ClienteServicoI;
@SpringBootTest
class REQ03AlterarClienteTests {
	private Logger logger = LogManager.getLogger(this.getClass());;
	@Autowired
	ClienteRepository repository;
	@Autowired
	ClienteServicoI servico;
	
	@Test
	void ct01_quando_cliente_esta_cadastrado_retorna_cliente_alterado() {
		Optional<Cliente> umCliente = servico.consultaPorId(1L);
		Cliente clienteModificado = umCliente.get();
		clienteModificado.setNome("Jose da Silva");
		Cliente cliente = servico.altera(clienteModificado);
		assertTrue(clienteModificado.equals(cliente));
	}
	@Test
	void ct02_quando_cep_invalido_servico_altera_cliente_retorna_nulo() {
		Optional<Cliente> umCliente = servico.consultaPorId(1L);
		Cliente clienteModificado = umCliente.get();
		clienteModificado.setNome("Jose da Silva");
		clienteModificado.setCep("00");
		Cliente cliente = servico.altera(clienteModificado);
		assertNull(cliente);
	}
}

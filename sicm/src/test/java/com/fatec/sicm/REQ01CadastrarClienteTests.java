package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.TransactionSystemException;

import com.fatec.sicm.mantemCliente.model.Cliente;
import com.fatec.sicm.mantemCliente.ports.ClienteRepository;
import com.fatec.sicm.mantemCliente.ports.ClienteService;

@SpringBootTest
class REQ01CadastrarClienteTests {
	Logger logger = LogManager.getLogger(this.getClass());
	@Autowired
	ClienteService servico;
    @Autowired
    ClienteRepository repository;

	// MethodUnderTest is the name of the method you are testing.
	// Scenario is the condition under which you test the method.
	// ExpectedResult is what you expect the method under test to do in the current

	// -------------------------------------------------------------------------------------------------------------------
	// testes de unidade
	// -------------------------------------------------------------------------------------------------------------------
	@Test
	void ct01_quando_cadastra_cliente_valido_data_deve_ser_a_data_atual() {
		// Dado
		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String dataCadastro = dataAtual.toString(fmt);
		// Quando
		Cliente cliente = new Cliente("Jose", "12/02/1960", "M", "99504993052", "04280130", "2983");
		cliente.obtemDataAtual(dataAtual);
		// Entao
		assertTrue(dataCadastro.equals(cliente.getDataCadastro()));
	}

	@Test
	void ct02_quando_cadastra_data_nascimento_valida_nao_deve_falhar() {
		// Dado
		Cliente cliente = null;
		String dataValida = "28/02/1960";
		// Quando
		try {
			cliente = new Cliente("Jose", dataValida, "M", "99504993052", "04280130", "2983");
		} catch (IllegalArgumentException e) {
			fail("Quando a data é valida nao deve falhar");

		}
		// Entao
		assertTrue(cliente.getDataNascimento().equals(dataValida));
	}

	@Test
	void ct03_quando_cadastra_data_nascimento_invalida_retorna_erro() {
		// Dado
		Cliente cliente = null;
		// Quando
		try {
			cliente = new Cliente("Jose", "31/02/1960", "M", "99504993052", "04280130", "2983");
			fail("Quando a data é invalida deve falhar");
		} catch (IllegalArgumentException e) {
			// Entao
			assertEquals("Data invalida", e.getMessage());
		}

	}
	// -------------------------------------------------------------------------------------------------------------------
	// testes de integração
	// -------------------------------------------------------------------------------------------------------------------

	@Test
	void ct04_quando_existem_dois_clientes_cadastrados_consulta_todos_retorna2() {

		// Dado que existem 2 clientes cadastrados (LoadDatabase)
		// Quando consulta todos
		List<Cliente> listaDeClientes = servico.consultaTodos();
		// Entao
		assertEquals(2, listaDeClientes.size());

	}

	@Test
	void ct05_quando_cliente_nao_esta_cadastrado_cadastra_com_sucesso() {
		// Dado
		Cliente clienteEsperado = null;
		Cliente clienteObtido = null;
		// Quando
		try {
			clienteEsperado = new Cliente("Jose", "31/03/1960", "M", "12213766754", "08545160", "2983");
			clienteEsperado.obtemDataAtual(new DateTime());
			clienteObtido = servico.save(clienteEsperado);
			clienteEsperado.setId(clienteObtido.getId());
			repository.deleteById(clienteObtido.getId());
		//Entao
			assertTrue(clienteEsperado.equals(clienteObtido));
		} catch (Exception e) {
			fail("Falha nao esperada no cadastro de cliente " + e.getMessage());
		}
	}
	@Test
	void ct06_quando_cliente_ja_cadastrado_retorna_erro() {
		logger.info(">>>>>> caso de teste 6");
		// Dado que o cliente ja esta cadastrado
		// Quando
	
			Cliente cliente = new Cliente("Jose", "31/03/1960", "M", "99504993052", "08545160", "2983");
			Cliente umCliente = servico.save(cliente);
		
		//(DataIntegrityViolationException e) {
			assertNull(umCliente);
		}
	
	@Test
	void ct07_quando_cpf_invalido_retorna_erro() {
		// Dado que o cpf é invalido
		// Quando
		try {
			Cliente cliente = new Cliente("Jose", "31/03/1960", "M", "9950499305", "08545160", "2983");
			servico.save(cliente);
			fail("Quando cpf invalido deveria disparar uma exceptionr.");
		//Entao
		} catch (TransactionSystemException e) {
			assertEquals("Could not commit JPA transaction",e.getMessage().substring(0, 32));
		}
	}
}

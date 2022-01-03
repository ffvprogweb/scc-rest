package com.fatec.sicm;

import static org.junit.jupiter.api.Assertions.*;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fatec.sicm.mantemCliente.model.Cliente;

@SpringBootTest
class REQ01CadastrarCliente {

	// MethodUnderTest is the name of the method you are testing.
	// Scenario is the condition under which you test the method.
	// ExpectedResult is what you expect the method under test to do in the current
	@Test
	void quando_cadastra_cliente_valido_data_deve_ser_a_data_atual() {
		// Dado
		DateTime dataAtual = new DateTime();
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		String dataCadastro = dataAtual.toString(fmt);
		// Quando
		Cliente cliente = new Cliente("Jose", "12/02/1960", "M", "99504993052", "04280130", "Rua Aguia de Haia",
				"2983");
		// Entao
		assertTrue(dataCadastro.equals(cliente.getDataCadastro()));
	}

	@Test
	void quando_cadastra_data_nascimento_valida_nao_deve_falhar() {
		//Dado
		Cliente cliente = null;
		String dataValida = "28/02/1960";
		//Quando
		try {
			cliente = new Cliente("Jose", dataValida, "M", "99504993052", "04280130", "Rua Aguia de Haia", "2983");
		} catch (IllegalArgumentException e) {
			fail("Quando a data é valida nao deve falhar");
			
		}
		//Entao
		assertTrue(cliente.getDataNascimento().equals(dataValida)) ;
	}
	@Test
	void quando_cadastra_data_nascimento_invalida_retorna_erro() {
		//Dado
		Cliente cliente = null;
		//Quando
		try {
			cliente = new Cliente("Jose", "31/02/1960", "M", "99504993052", "04280130", "Rua Aguia de Haia", "2983");
			fail("Quando a data é invalida deve falhar");
		} catch (IllegalArgumentException e) {
		//Entao
			assertEquals("Data invalida",e.getMessage());
		}
		
	}
}

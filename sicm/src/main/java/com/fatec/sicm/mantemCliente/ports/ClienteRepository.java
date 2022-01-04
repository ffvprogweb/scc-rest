package com.fatec.sicm.mantemCliente.ports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.fatec.sicm.mantemCliente.model.Cliente;
@Repository
public interface ClienteRepository extends JpaRepository <Cliente, Long>{
	public Cliente findByCpf(@Param("cpf") String cpf);
	List<Cliente> findAllByNomeIgnoreCaseContaining(String nome);
}

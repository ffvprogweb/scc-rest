package com.fatec.sicm.mantemCliente.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;


import org.joda.time.DateTime;

public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message="Nome é requerido")
	private String nome;
	private String dataCadastro;
	private String dataNascimento;
	private String sexo;
	
	private String cpf;
	@NotBlank(message="O CEP é obritatório.")
	private String cep;
	@NotBlank(message="O endereço deve ser informado")
	private String complemento;
}

package com.fatec.sicm.mantemCliente.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
@Entity
public class Cliente {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message="Nome é requerido")
	private String nome;
	@Pattern(regexp="^(0?[1-9]|[12][0-9]|3[01])[\\/-](0?[1-9]|1[012])[\\/-]\\d{4}$", message="A data de vencimento deve estar no formato MM/YY") //https://www.regular-expressions.info/
	private String dataCadastro;
	private String dataNascimento;
	private String sexo;
	@CPF
	private String cpf;
	@NotBlank(message="O CEP é obritatório.")
	private String cep;
	private String endereco;
	@NotBlank(message="O complemento deve ser informado")
	private String complemento;
	
	
	public Cliente(String nome, String dataNascimento, String sexo, String cpf,  String cep, String endereco, String complemento) {
	
		this.nome = nome;
		setDataNascimento(dataNascimento);
		this.sexo = sexo;
		this.cpf = cpf;
		this.cep = cep;
		this.endereco = endereco;
		this.complemento = complemento;
		DateTime dataAtual = new DateTime();
		setDataCadastro(dataAtual);
		
	}
	public Cliente() {
		
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(DateTime dataAtual) {
		DateTimeFormatter fmt = DateTimeFormat.forPattern("dd/MM/YYYY");
		this.dataCadastro = dataAtual.toString(fmt);
	}
	public String getDataNascimento() {
		return dataNascimento;
	}
	public void setDataNascimento(String dataNascimento)  {
		boolean isValida = false;
		DateTimeFormatter fmt = DateTimeFormat.forPattern("yyyy/MM/dd");
		if (validaData(dataNascimento) == true) {
			this.dataNascimento = dataNascimento;
		} else {
			throw new IllegalArgumentException("Data invalida");
		}
		
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getCep() {
		return cep;
	}
	public void setCep(String cep) {
		this.cep = cep;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getComplemento() {
		return complemento;
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public boolean validaData(String data) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		df.setLenient(false); //
		try {
			df.parse(data); // data válida (exemplo 30 fev - 31 nov)
			return true;
		} catch (ParseException ex) {
			return false;
		}
	}
	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", dataCadastro=" + dataCadastro + ", dataNascimento="
				+ dataNascimento + ", sexo=" + sexo + ", cpf=" + cpf + ", cep=" + cep + ", endereco=" + endereco
				+ ", complemento=" + complemento + "]";
	}
	
}

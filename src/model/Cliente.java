package model;

import dao.BD;

public class Cliente {
	private String cpf;
	private String nome;
	private String email;
	private String senha;
	private TipoCliente tipo;
	private Endereco endereco;//FALTA IMPLEMENTAR
	private String rg;
	
	
	public Cliente(String senha,String email,String cpf, String rg,String nome, Endereco endereco) {
		this.cpf = cpf;
		this.rg = rg;
		this.nome = nome;
		this.endereco = endereco;
		this.email = email;
		this.senha = senha;
		this.tipo = TipoCliente.COMUM;
		
	}	
	
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public TipoCliente getTipo() {
		return tipo;
	}
	public void setTipo(TipoCliente tipoCliente) {
		this.tipo = tipoCliente;
	}

	public void cadastrarDados(BD bd, Conta conta) {
		bd.adicionaConta(conta);
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}

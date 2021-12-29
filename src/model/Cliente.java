package model;

import dao.BD;

public class Cliente {
	private String cpf;
	private String nome;
	private TipoCliente tipo;
	
	public Cliente(String cpf, String nome) {
		this.cpf = cpf;
		this.nome = nome;
		this.tipo = TipoCliente.COMUM;
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
		bd.conta.add(conta);
	}
}

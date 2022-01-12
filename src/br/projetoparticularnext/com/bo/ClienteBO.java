package br.projetoparticularnext.com.bo;

import br.projetoparticularnext.com.bean.Endereco;
import br.projetoparticularnext.com.bean.cliente.Cliente;
import br.projetoparticularnext.com.bean.conta.Conta;

public class ClienteBO {
	public Cliente cliente;
	public ClienteBO() {
		
	}

	public ClienteBO(String senha, String email, String cpf, String rg, String nome, Endereco endereco) {
		this.cliente = cadastrarCliente(senha, email, cpf, rg, nome,endereco);
	}
	//Cadastra Cliente
	private Cliente cadastrarCliente(String senha, String email, String cpf, String rg, String nome, Endereco endereco) {
		Cliente cliente = new Cliente(senha, email, cpf, rg, nome, endereco);// Cadastra cliente
		return cliente;
	}
	// RECUPERA SENHA
		public static boolean recuperaSenha(String email) {
			// futuramente com o banco e uso de APIs implementar
			return true;
		}
}

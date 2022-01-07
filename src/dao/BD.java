package dao;

import java.util.ArrayList;
import model.Endereco;
import model.cliente.Cliente;
import model.conta.Conta;
import model.conta.ContaCorrente;
import model.conta.ContaPoupanca;

public class BD {
	public static ArrayList<ContaPoupanca> contaPoupanca;
	public static ArrayList<ContaCorrente> contaCorrente;
	public static int tipoConta = 0;

	public BD() {
		contaPoupanca = new ArrayList<ContaPoupanca>();
		contaCorrente = new ArrayList<ContaCorrente>();
		criarObjetosEstaticos();
	}

//CRIA UM OBJETO ESTATICO DE CONTA E CLIENTE PARA FINS DE TESTE
	private void criarObjetosEstaticos() {
		Cliente cliente1 = new Cliente("1234", "luizfernando962@gmail.com", "43546219830", "447218402",
				"Fernando Silveira",
				new Endereco("Cesário Lange", "SP", "Centro", "1759", "Rua do Comércio", "18285-000"));
		contaCorrente.add(new ContaCorrente(cliente1));
		contaPoupanca.add(new ContaPoupanca(cliente1));

	}

	// ADICIONA CONTA CORRENTE AO BD
	public void adicionaContaCorrente(ContaCorrente cc) {
		contaCorrente.add(cc);
	}

	// ADICIONA CONTA POUPANCA AO BD
	public void adicionaContaPoupanca(ContaPoupanca cp) {
		contaPoupanca.add(cp);
	}

	// IDENTIFICA CONTA NO BD APARTIR DO NUMERO DA CONTA 
	public static Conta identificaContaNum(String numDesti) {
		for (int i = 0; i < contaPoupanca.size(); i++) {
			if (contaPoupanca.get(i).getNumero().equals(numDesti)) {
				tipoConta = 2;
				return contaPoupanca.get(i);
			}
		}
		for (int i = 0; i < contaCorrente.size(); i++) {
			if (contaCorrente.get(i).getNumero().equals(numDesti)) {
				tipoConta = 1;
				return contaCorrente.get(i);
			}
		}
		return null;
	}

	// VERIFICA EXISTENCIA DO CPF NO BANCO
	//
	public static boolean consultaCpfBanco(String cpf) {

		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < contaCorrente.size(); i++) {
			if (contaCorrente.get(i).getCliente().getCpf().equals(cpf)) {
				return true;
			}

		}
		for (int i = 0; i < contaPoupanca.size(); i++) {
			if (contaPoupanca.get(i).getCliente().getCpf().equals(cpf)) {
				return true;
			}

		}
		return false;
	}

	// VERIFICA LISTA NO BANCO DE DADOS ESTATICO
	public ContaCorrente retornaContaCorrente(String cpf, String senha) {
		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < contaCorrente.size(); i++) {
			if (contaCorrente.get(i).getCliente().getCpf().equals(cpf)
					&& contaCorrente.get(i).getCliente().getSenha().equals(senha)) {
				return contaCorrente.get(i);
			}
		}
		return null;
	}

	// VERIFICA LISTA NO BANCO DE DADOS ESTATICO
	public ContaPoupanca retornaContaPoupanca(String cpf, String senha) {
		for (int i = 0; i < contaPoupanca.size(); i++) {
			if (contaPoupanca.get(i).getCliente().getCpf().equals(cpf)
					&& contaPoupanca.get(i).getCliente().getSenha().equals(senha)) {
				return contaPoupanca.get(i);
			}
		}
		return null;
	}

}

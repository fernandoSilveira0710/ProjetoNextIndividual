package dao;

import java.util.ArrayList;

import model.Cliente;
import model.Conta;
import model.TipoCliente;

public class BD {
	public static ArrayList<Conta> conta;

	public BD() {
		conta = new ArrayList<Conta>();
		conta.add(new Conta(new Cliente("43546219830", "Fernando Silveira")));
	}

	// IDENTIFICA CONTA NO BD APARTIR DO NUMERO DA CONTA
	public static Conta identificaContaNum(String numDesti) {
		for (int i = 0; i < conta.size(); i++) {
			if (conta.get(i).getNumero().equals(numDesti)) {
				return conta.get(i);
			}
		}
		return null;
	}

	// VERIFICA LISTA NO BANCO DE DADOS ESTATICO
	public static Conta consultaCpf(String cpf) {
		Conta contaConsulta = null;
		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < conta.size(); i++) {
			if (conta.get(i).getCliente().getCpf().equals(cpf)) {
				contaConsulta = conta.get(i);
			}

		}
		return contaConsulta;
	}

}

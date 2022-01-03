package dao;

import java.util.ArrayList;

import model.Cliente;
import model.Conta;
import model.Endereco;
import model.TipoCliente;

public class BD {
	public static ArrayList<Conta> conta;

	public BD() {
		conta = new ArrayList<Conta>();
		conta.add(new Conta(
				new Cliente("1234", "luizfernando962@gmail.com", "43546219830", "447218402", "Fernando Silveira",
						new Endereco("Cesário Lange", "SP", "Centro", "1759", "Rua do Comércio", "18285-000"))));
	}

	// ADICIONA CONTA AO BD
	public static void adicionaConta(Conta contaUser) {
		conta.add(contaUser);
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

	// VERIFICA EXISTENCIA DO CPF NO BANCO
	public static boolean consultaCpfBanco(String cpf) {
		Conta contaConsulta = null;

		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < conta.size(); i++) {
			if (conta.get(i).getCliente().getCpf().equals(cpf)) {
				return true;
			}

		}
		return false;
	}

	// VERIFICA LISTA NO BANCO DE DADOS ESTATICO
	public static Conta retornaContaBanco(String cpf, String senha) {
		Conta contaConsulta = null;

		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < conta.size(); i++) {
			if (conta.get(i).getCliente().getCpf().equals(cpf) && conta.get(i).getCliente().getSenha().equals(senha)) {
				System.out.println("ENTROU AQUI");
				contaConsulta = conta.get(i);
			}

		}
		return contaConsulta;
	}

}

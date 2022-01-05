package controller;

import dao.BD;
import model.Cliente;
import model.Conta;
import model.Endereco;
import util.Utils;
import util.ValidaCPF;

public class ControlLogin {
	static BD bd = new BD();
	static Conta conta = null;
	static Utils utils = new Utils();
	static ValidaCPF validaCPF = new ValidaCPF();

	// VERIFICA SE SENHA É COMPATIVEL COM CONTA
	public static boolean testaLogin(String cpfConsole) {
		if (bd.consultaCpfBanco(cpfConsole)) {
			return true;
		} else {
			return false;
		}
	}

	// CRIA CONTA ANTES DE CADASTRAR
	public static void cadastrarConta(String senha, String cpfConsole, String rg, String nome, String email, String rua,
			String bairro, String numero, String cidade, String estado, String cep) {
		Endereco endereco = new Endereco(cidade, estado, bairro, numero, rua, cep);
		Cliente cliente = new Cliente(senha, email, cpfConsole, rg, nome, endereco);
		conta = new Conta(cliente);
		cliente.cadastrarDados(bd, conta);
	}

	// RETORNA UMA STRING COM DETAALHES DA CONTA
	public static String exibeDetalhesConta() {
		return "\n\n>>OLÁ " + conta.getCliente().getNome().toUpperCase() + "\n>>NUMERO CONTA: " + conta.getNumero()
				+ "\n>>SALDO DISPONIVEL:" + utils.convertToReais(conta.consultarSaldo()) + "\n>>TIPO DE CONTA:"
				+ conta.getCliente().getTipo();
	}

	// BUSCA CONTA E TRANSFERE EM CONTA
	public static String[] buscaContaeTransfere(String numDestino, double valorDeTransferencia) {
		String resposta[] = new String[2];

		Conta contaDestino = consultaContaDestinoExistente(numDestino);
		if (contaDestino != null) {

			if (conta.transferir(contaDestino, valorDeTransferencia)) {
				resposta[0] = "\n>>TRANSFERENCIA DE " + utils.convertToReais(valorDeTransferencia) + "\n PARA "
						+ contaDestino.getCliente().getNome().toUpperCase() + " REALIZADO COM SUCESSO<< \n\n";
				resposta[1] = "0";
			} else {
				resposta[0] = "ERRO NA TRANSFERENCIA!SALDO INSUFICIENTE \n";
				resposta[1] = "1";
			}
		} else {
			resposta[0] = "ESTA CONTA NÃO EXISTE! \n";
			resposta[1] = "2";
		}
		return resposta;
	}

// CONSULTA CONTA DE DESTINO
	private static Conta consultaContaDestinoExistente(String numDestino) {
		Conta contaDestino = bd.identificaContaNum(numDestino);// conta recebida do bd
		return contaDestino;
	}

	// SAQUE EM CONTA
	public static boolean saqueConta() {
		return conta.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")));
	}

	// DEPOSITA EM CONTA
	public static boolean depositaNaConta() {
		return conta.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")));
	}

// RETORNA SALDO
	public static String consultaSaldo() {
		return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(conta.consultarSaldo()) + "<<\n";
	}

	// ENVIA DADOS PARA UMA CLASSE VALIDACAO E RETORNA BOOLEAN
	public static boolean validarCpf(String cpf) {
		if (validaCPF.verificaCpf(cpf)) {
			return true;
		} else {
			System.out.println("\n|CPF INVALIDO! DIGITE CORRETAMENTE|\n");
			return false;
		}
	}

	// CHAMA O BANCO E ENVIA CPF VALIDADO RETORNANDO A CONTA
	public static int buscaContaCadastrada(boolean login, String cpfConsole, String senha) {
		conta = bd.retornaContaBanco(validaCPF.removeCaracteresEspeciais(cpfConsole), senha);
		if (conta != null && login) {
			return 0;
		} else if (!login) {
			return 1;
		} else {
			return 2;
		}
	}

}

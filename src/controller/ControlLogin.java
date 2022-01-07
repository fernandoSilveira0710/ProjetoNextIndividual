package controller;

import dao.BD;
import model.Endereco;
import model.cliente.Cliente;
import model.conta.Conta;
import model.conta.ContaCorrente;
import model.conta.ContaPoupanca;
import util.Utils;
import util.ValidaCPF;

public class ControlLogin {
	static BD bd = new BD();
	static Utils utils = new Utils();
	static ValidaCPF validaCPF = new ValidaCPF();
	static ContaPoupanca cp;
	static ContaCorrente cc;
	public static int id = 0;

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
			String bairro, String numero, String cidade, String estado, String cep, String tipoConta) {
		Endereco endereco = new Endereco(cidade, estado, bairro, numero, rua, cep);
		Cliente cliente = new Cliente(senha, email, cpfConsole, rg, nome, endereco);
		identificaECadastraContas(tipoConta, cliente);
	}

// IDENTIFICA O TIPO DE CONTA ENUM
	private static void identificaECadastraContas(String tipoConta, Cliente cliente) {
		if (tipoConta.equals("1")) {
			cc = new ContaCorrente(cliente);
			bd.adicionaContaCorrente(cc);
			id = 1;
		} else if (tipoConta.equals("2")) {
			cp = new ContaPoupanca(cliente);
			bd.adicionaContaPoupanca(cp);
			id = 2;
		} else if (tipoConta.equals("3")) {
			cp = new ContaPoupanca(cliente);
			cc = new ContaCorrente(cliente);
			bd.adicionaContaCorrente(cc);
			bd.adicionaContaPoupanca(cp);
			id = 3;
		}

	}

	// RETORNA UMA STRING COM DETAALHES DA CONTA
	public static String exibeDetalhesConta() {
		if (cc != null && cp != null) {
			return "\n\n>>OLÁ " + cc.getCliente().getNome().toUpperCase() + "\n>>NUMERO CORRENTE: " + cc.getNumero()
					+ " | NUMERO POUPANCA: " + cp.getNumero() + "\n>>SALDO CORRENTE:"
					+ utils.convertToReais(cc.consultarSaldo()) + "\n>>SALDO POUPANCA:"
					+ utils.convertToReais(cp.consultarSaldo()) + "\n>>TIPO DE CONTA:" + cc.getCliente().getTipo();
		} else if (cc != null && cp == null) {
			return "\n\n>>OLÁ " + cc.getCliente().getNome().toUpperCase() + "\n>>NUMERO CONTA CORRENTE: "
					+ cc.getNumero() + "\n>>SALDO DISPONIVEL:" + utils.convertToReais(cc.consultarSaldo())
					+ "\n>>TIPO DE CONTA:" + cc.getCliente().getTipo();
		} else if (cc == null && cp != null) {
			return "\n\n>>OLÁ " + cp.getCliente().getNome().toUpperCase() + "\n>>NUMERO CONTA POUPANÇA: "
					+ cp.getNumero() + "\n>>SALDO DISPONIVEL:" + utils.convertToReais(cp.consultarSaldo())
					+ "\n>>TIPO DE CONTA:" + cp.getCliente().getTipo();
		}
		return null;

	}

	// BUSCA CONTA E TRANSFERE EM CONTA
	public static String[] buscaContaeTransfere(String numDestino, double valorDeTransferencia, boolean tipo) {
		String resposta[] = new String[2];
		Conta contaDestino = consultaContaDestinoExistente(numDestino);
		boolean verifica = false;
		if (contaDestino != null) {
			if (!tipo && bd.tipoConta == 1) {
				verifica = cc.transferir(contaDestino, valorDeTransferencia);
			} else if (!tipo && bd.tipoConta == 2) {
				valorDeTransferencia += 5.6;
				verifica = cc.transferir(contaDestino, valorDeTransferencia);
			} else if (tipo && bd.tipoConta == 1) {
				valorDeTransferencia += 5.6;
				verifica = cp.transferir(contaDestino, valorDeTransferencia);
			} else if (tipo && bd.tipoConta == 2) {
				verifica = cp.transferir(contaDestino, valorDeTransferencia);
			}
			if (verifica) {
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
	public static boolean saqueConta(boolean b) {
		if (!b) {
			return cc.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")));
		} else {
			return cp.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")));
		}

	}

	// DEPOSITA EM CONTA
	public static boolean depositaNaConta(boolean b) {
		if (!b) {
			return cc.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")));
		} else {
			return cp.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")));
		}

	}

// RETORNA SALDO
	public static String consultaSaldo(boolean b) {
		if (!b) {
			return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(cc.consultarSaldo()) + "<<\n";
		} else {
			return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(cp.consultarSaldo()) + "<<\n";
		}
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
		cc = bd.retornaContaCorrente(validaCPF.removeCaracteresEspeciais(cpfConsole), senha);// ANEXANDO CONTA CORRENTE
																								// DO BANCO AO CC LOCAL
		cp = bd.retornaContaPoupanca(validaCPF.removeCaracteresEspeciais(cpfConsole), senha);// ANEXANDO CONTA POUPANCA
																								// DO BANCO AO CP LOCAL
		identificaContas();
		if (cc != null && login || cp != null && login) {

			return 0;
		} else if (!login) {
			return 1;
		} else {
			return 2;
		}
	}

	private static void identificaContas() {
		if (cc != null) {
			id = 1;
		}if (cp != null) {
			id = 2;
		}if (cp != null && cc != null) {
			id = 3;
		}
		System.out.println(id);

	}

	// RECUPERA SENHA
	public static boolean recuperaSenha(String email) {
		// futuramente com o banco e uso de APIs implementar
		return true;
	}

	// ZERAR CADASTROS ALOCADOS EM MEMORIA
	public static void zerarAlocacoesDeMemoria() {
		cc = null;
		cp = null;
		id = 0;
	}

}

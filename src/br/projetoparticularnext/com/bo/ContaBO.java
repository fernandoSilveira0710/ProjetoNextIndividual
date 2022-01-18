package br.projetoparticularnext.com.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.projetoparticularnext.com.bean.cliente.Cliente;
import br.projetoparticularnext.com.bean.cliente.TipoCliente;
import br.projetoparticularnext.com.bean.conta.Conta;
import br.projetoparticularnext.com.bean.conta.TipoConta;
import br.projetoparticularnext.com.utils.Banco;
import br.projetoparticularnext.com.utils.Utils;
import br.projetoparticularnext.com.utils.ValidaCPF;

public class ContaBO {
	static List<Conta> listConta = new ArrayList<Conta>();
	public static Conta cp;
	public static Conta cc;
	static Utils utils = new Utils();
	static ValidaCPF validaCPF = new ValidaCPF();
	public static int id = 0;
	public static boolean taxasRendimentos = false;

	public ContaBO() {

	}

	public ContaBO(Cliente cliente, TipoConta tipoConta) {
		cadastraConta(cliente, tipoConta);
	}

	// CADASTRA CLIENTE
	private void cadastraConta(Cliente cliente, TipoConta tipoConta) {
		if (tipoConta.name().equals("ContaCorrente")) {
			cc = new Conta(cliente, tipoConta);
			Banco.cadastraConta(cc.getNumero(), cc);
		} else {
			cp = new Conta(cliente, tipoConta);
			Banco.cadastraConta(cp.getNumero(), cp);
		}
	}

	// DEBITA E CREDITA COM BASE NA CONTA
	public static void TaxaseRendimentos() {
		if (cc != null) {
			if (cc.getData().equals(Utils.dataAtual())) {
				double valor = cc.getSaldo();
				double taxa = valor * (0.45 / 100);
				valor -= taxa;
				System.out.println("TAXANDO: " + Utils.convertToReais(taxa));
				cc.setSaldo(valor);
				cc.setData(Utils.getDateAdd1Month());
				System.out.println("PR�XIMO M�S DE COBRAN�A: " + cc.getData());
			}
		} else if (cp != null) {
			if (cp.getData().equals(Utils.dataAtual())) {
				double valor = cp.getSaldo();
				double taxa = valor * (0.03 / 100);
				valor += taxa;
				System.out.println("RENDENDO: " + Utils.convertToReais(taxa));
				cp.setSaldo(valor);
				cp.setData(Utils.getDateAdd1Month());
				System.out.println("PR�XIMO M�S DE COBRAN�A: " + cp.getData());

			}
		}

	}

	// VERIFICA SE SENHA � COMPATIVEL COM CONTA
	public static boolean testaLogin(String cpfConsole) {
		if (Banco.consultaCpfBanco(cpfConsole)) {
			return true;
		} else {
			return false;
		}
	}

	// RETORNA UMA STRING COM DETAALHES DA CONTA
	public static String exibeDetalhesConta() {
		int tipo = 0;
		String retorno = "";
		int cont = 0;
		for (Conta conta : listConta) {
			if(cont == 0) {
				retorno += "\n\n>>SEJA BEM VINDO "+conta.getCliente().getNome().toUpperCase()
						+"    >>TIPO CONTA: "+conta.getCliente().getTipo().name().toUpperCase();
				cont++;
			}
			if (conta.getTipoConta() == TipoConta.ContaCorrente) {
				id = 1;
				tipo++;
				cc = conta; // SETA CONTA EM CONTA CORRENTE
				retorno += "\n>>NUMERO CORRENTE: " + conta.getNumero() + "    >>SALDO CORRENTE:"
						+ utils.convertToReais(consultarSaldo(conta));
			}
			if (conta.getTipoConta() == TipoConta.ContaPoupanca) {
				id = 2;
				cp = conta; // SETA CONTA EM CONTA POUPANCA
				retorno += "\n>>NUMERO POUPAN�A: " + conta.getNumero() + "    >>SALDO POUPAN�A:"
						+ utils.convertToReais(consultarSaldo(conta));
				tipo++;
			}
			if (conta.getTipoConta() == TipoConta.ContaCorrente && conta.getTipoConta() == TipoConta.ContaPoupanca) {
				id = 3;
			}
		}
		if (tipo == 2)
			id = 3;
		return retorno;
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
//		System.out.println("CPF:" + cpfConsole + " Senha:" + senha);
		listConta = Banco.retornaConta(validaCPF.removeCaracteresEspeciais(cpfConsole), senha);// ANEXANDO CONTA
		if (!listConta.isEmpty() && login) {
			return 0;
		} else if (!login) {
			return 1;
		} else {
			return 2;
		}
	}

	// IDENTIFICA O TIPO DE CONTA ENUM
	private static void identificaECadastraContas(String tipoConta, Conta conta) {
		if (tipoConta.equals("1")) {
			Banco.cadastraConta(tipoConta, conta);
			id = 1;
		} else if (tipoConta.equals("2")) {
			Banco.cadastraConta(tipoConta, conta);
			id = 2;
		} else if (tipoConta.equals("3")) {
			Banco.cadastraConta(tipoConta, conta);
			Banco.cadastraConta(tipoConta, conta);
			id = 3;
		}

	}

	// BUSCA CONTA E TRANSFERE EM CONTA
	public static String[] buscaContaeTransfere(String numDestino, double valorDeTransferencia, boolean tipo) {

		String resposta[] = new String[2];
		Conta contaDestino = consultaContaDestinoExistente(numDestino);
		double taxa = 5.6;
		boolean verifica = false;
		transfereEntreContas("TRANSFERENCIA", valorDeTransferencia, tipo, resposta, contaDestino, taxa, verifica);
		return resposta;
	}

	public static void transfereEntreContas(String tipoTransferencia, double valorDeTransferencia, boolean tipo,
			String[] resposta, Conta contaDestino, double taxa, boolean verifica) {
		if (contaDestino != null) {
			if (!tipo && contaDestino.getTipoConta().ordinal() == 0) {
				verifica = transferir(contaDestino, valorDeTransferencia, cc);
			} else if (!tipo && contaDestino.getTipoConta().ordinal() == 1) {
				if ((consultarSaldo(cc) - taxa) >= valorDeTransferencia) {
					saque(5.60, cc);// DESCONTA TAXA
					verifica = transferir(contaDestino, valorDeTransferencia, cc);
					resposta[0] = "TAXA DE R$" + taxa + " FOI APLICADA POR SE TRATAR DE CONTAS DIFERENTES";
				} else {
					resposta[0] = "LEMBRE-SE QUE A TAXA DE R$" + taxa + " � APLICADA A SUA " + tipoTransferencia
							+ " ENTRE CONTAS DIFERENTES!";

				}
			} else if (tipo && contaDestino.getTipoConta().ordinal() == 1) {
				if ((consultarSaldo(cp) - taxa) >= valorDeTransferencia) {
					saque(5.60, cp);// DESCONTA TAXA
					verifica = transferir(contaDestino, valorDeTransferencia, cp);
					resposta[0] = "TAXA DE R$" + taxa + " FOI APLICADA POR SE TRATAR DE CONTAS DIFERENTES";
				} else {
					resposta[0] = "LEMBRE-SE QUE A TAXA DE R$" + taxa + " � APLICADA A SUA " + tipoTransferencia
							+ " ENTRE CONTAS DIFERENTES!";
				}
			} else if (tipo && contaDestino.getTipoConta().ordinal() == 0) {
				verifica = transferir(contaDestino, valorDeTransferencia, cp);
			}
			if (verifica) {
				resposta[0] += "\n>>" + tipoTransferencia + " DE " + utils.convertToReais(valorDeTransferencia)
						+ "\n PARA " + contaDestino.getCliente().getNome().toUpperCase()
						+ " REALIZADO COM SUCESSO<< \n\n";
				resposta[1] = "0";
			} else {
				resposta[0] += "\nERRO EM " + tipoTransferencia + "!SALDO INSUFICIENTE \n";
				resposta[1] = "1";
			}
		} else {
			resposta[0] = "ESTA CONTA N�O EXISTE! \n";
			resposta[1] = "2";
		}
	}

	// DEPOSITA EM CONTA
	public static boolean depositaNaConta(boolean b) {
		if (!b) {
			return depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")), cc);
		} else {
			return depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")), cp);
		}

	}

	// DEPOSITA DA CONTA SETADA
	public static boolean depositar(double valDeposito, Conta c) {
		double saldoComDeposito = c.getSaldo() + valDeposito;
		c.setSaldo(saldoComDeposito);
		verificaTipoConta(c);
		return true;
	}

//RECEBE CONTA E VERIFICA SE MUDA PARA OUTRO TIPO
	public static double verificaTipoConta(Conta c) {
		if (c.getSaldo() <= 5000) {
			c.getCliente().setTipo(TipoCliente.COMUM);
			return c.getCliente().getTipo().getLimite();
		} else if (c.getSaldo() >= 5000 && c.getSaldo() < 15000) {
			c.getCliente().setTipo(TipoCliente.SUPER);
			return c.getCliente().getTipo().getLimite();
		} else {
			c.getCliente().setTipo(TipoCliente.PREMIUM);
			return c.getCliente().getTipo().getLimite();
		}
	}

	// RETORNA SALDO
	public static String consultaSaldo(boolean b) {
		if (!b) {
			return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(consultarSaldo(cc)) + "<<\n";
		} else {
			return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(consultarSaldo(cp)) + "<<\n";
		}
	}

	// SACA DA CONTA RECBIDA
	public static boolean saque(double valor, Conta c) {
		if (c.getSaldo() >= valor) {
			double saldoComSaque = c.getSaldo() - valor;
			c.setSaldo(saldoComSaque);
			verificaTipoConta(c);
			return true;
		} else
			return false;
	}

	// TRANSFERE DE CONTA X PARA CONTA Y
	public static boolean transferir(Conta contaDestino, double valTransferencia, Conta c) {
		if (c.getSaldo() >= valTransferencia) {
			contaDestino.setSaldo(contaDestino.getSaldo() + valTransferencia);
			c.setSaldo(c.getSaldo() - valTransferencia);
			verificaTipoConta(c);
			return true;
		} else {
			return false;
		}

	}

	// retorna saldo
	public static double consultarSaldo(Conta c) {
		return c.getSaldo();
	}

	// SAQUE EM CONTA
	public static boolean saqueConta(boolean b) {
		if (!b) {
			return saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")), cc);
		} else {
			return saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")), cp);
		}

	}

	// CONSULTA CONTA DE DESTINO
	private static Conta consultaContaDestinoExistente(String numDestino) {
		Conta contaDestino = Banco.buscaContaPorNumero(numDestino);// conta recebida do bd
		return contaDestino;
	}

	// VERIFICA EXISTENCIA DE CONTAS
	public static String retornaeVerificaContaExistente(String tipo) {
		if (tipo.equals("cc")) {
			if (cc != null) {
				return cc.getNumero();
			} else
				return "0";
		} else if (tipo.equals("cp")) {
			if (cp != null) {
				return cp.getNumero();
			} else
				return "0";
		} else
			return "0";
	}

	// ZERAR CADASTROS ALOCADOS EM MEMORIA
	public static void zerarAlocacoesDeMemoria() {
		cc = null;
		cp = null;
		id = 0;
	}

//verifica a conta com base na conta existente e retorna o limite
	public static double buscaLimiteConta() {
		if (cc != null) {
			double limite = verificaTipoConta(cc);
			System.out.println("\n    >>LIMITE PR� APROVADO DE " + Utils.convertToReais(limite) + "<<    ");
			return limite;
		} else {
			double limite = verificaTipoConta(cp);
			System.out.println("\n    >>LIMITE PR� APROVADO DE " + Utils.convertToReais(limite) + "<<    ");
			return limite;

		}
	}

}

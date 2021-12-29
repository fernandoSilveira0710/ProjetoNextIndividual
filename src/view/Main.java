package view;

import model.Cliente;
import model.Conta;
import util.Utils;
import util.ValidaCPF;
import dao.BD;

public class Main {
	static Utils utils = new Utils();
	static ValidaCPF validaCPF = new ValidaCPF();
	static BD bd = new BD();
	static String cpfConsole = "";
	static boolean logado = false;

	public static void main(String[] args) {
		logado = false;
		menuInicio();
		if (!logado)
			menuCadastro(); // caso logado ele puxa o cadastro
	}

// EXIBE MENU LOGIN
	private static void menuInicio() {
		System.out.println("|จจจจจBEM VINDO AO BANCO NEXTจจจจจ|");
		System.out.println("|จจจจจจจจจจจจจจLOGINจจจจจจจจจจจจจจ|");
		System.out.println("|                                 |");
		while (true) {
			cpfConsole = utils.lerConsole("|จจจDIGITE SEU CPF:");
			if (validarCpf(cpfConsole))// valida cpf
				break;
		}
		chamarBanco();
	}

// EXIBE MENU CADASTRO
	private static void menuCadastro() {
		System.out.println("|จจจจจจจจจจจจจจจจจจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจจจจจจจจจจCADASTROจจจจจจจจจจจจจจ|");
		cadastrar();
	}

// CADASTRA O USUARIO
	private static void cadastrar() {
		System.out.println("SEU CPF: " + cpfConsole);
		String nome = utils.lerConsole("|DIGITE SEU NOME: ");
		// CONTINUAR DAQUI
		Cliente cliente = new Cliente(cpfConsole, nome);
		Conta conta = new Conta(cliente);
		cliente.cadastrarDados(bd, conta);
		menuPrincipal(conta);
		buscaOperacaoPrincipal(conta);

	}

// EXIBE MENU PRINCIPAL
	private static void menuPrincipal(Conta conta) {
		System.out.println("|จSEJA BEM VINDO " + conta.getCliente().getNome().toUpperCase() + "|");
		System.out.println("|NUMERO CONTA: " + conta.getNumero() + "          |");
		System.out.println("|SALDO DISPONIVEL:" + utils.convertToReais(conta.consultarSaldo()) + " |");
		System.out.println("|TIPO DE CONTA:" + conta.getCliente().getTipo() + " |");
		System.out.println("|จจจจจจจจจจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจMENU PRINCIPALจจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจจ1 - TRANSFERIRจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจจ2 - DEPOSITARจจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจจ3 - CONSULTAR SALDOจจจจจจจจจจจ|");
		System.out.println("|จจจ4 - SACARจจจจจจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจจ0 - SAIRจจจจจจจจจจจจจจจจจจจจจจ|");
		System.out.println("|จจจจจจจจจจจจจจจจจจจจจจจจจจจจจจจจจ|");
	}

//SWITCH COM OPERAวีES PRINCIPAIS(SALDO,SAQUE,TRANSFERENCIA,DEPOSITO E SAIR)
	private static void buscaOperacaoPrincipal(Conta conta) {
		boolean loop = true;
		while (loop) {
			int operacao = Integer.parseInt(utils.lerConsole("|DIGITE A OPERAวรO: "));

			switch (operacao) {
			case 1: {
				while (true) {
					String numDesti = utils.lerConsole("DIGITE O NUMERO DA CONTA DESTINO: ");
					Conta contaDestino = bd.identificaContaNum(numDesti);// conta recebida do bd
					if (contaDestino != null) {
						double valor = Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA TRANSFERIR: "));
						if (conta.transferir(contaDestino, valor)) {
							System.out.println("\n!!!TRANSFERENCIA DE " + utils.convertToReais(valor) + " PARA "
									+ contaDestino.getCliente().getNome().toUpperCase()
									+ " REALIZADO COM SUCESSO!!!! \n\n");
							menuPrincipal(conta);
							break;
						} else {
							System.err.println("ERRO NA TRANSFERENCIA!SALDO INSUFICIENTE \n");
						}
					} else {
						System.err.println("ESTA CONTA NรO EXISTE! \n");
					}
				}
				break;
			}
			case 2: {
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (conta
							.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")))) {
						System.out.println("DEPOSITO REALIZADO COM SUCESSO! \n\n");
						menuPrincipal(conta);
						break;
					} else {
						System.err.println("ERRO NO DEPOSITO! \n");
					}
				}
				break;
			}
			case 3: {
				System.out.println("| SALDO DISPONIVEL: " + utils.convertToReais(conta.consultarSaldo()) + "|");
				break;
			}
			case 4: {
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (conta.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")))) {
						System.out.println("SAQUE REALIZADO COM SUCESSO!");
						menuPrincipal(conta);
						break;
					} else {
						System.err.println("SALDO INSUFICIENTE");
					}
				}
				break;
			}
			case 0: {
				System.out.println("\n\n");
				System.out.println("|จจจจจจจจLOGOFF CONCLUIDO!จจจจจจจจ|");
				System.out.println("\n\n");
				loop = false;
				break;
			}
			default:
				System.out.println("|จจDIGITE NUMEROS ENTRE 0 E 3จจจจจ|");
			}
			if (!loop)
				menuInicio();
		}
	}

// ENVIA DADOS PARA UMA CLASSE VALIDACAO E RETORNA BOOLEAN
	private static boolean validarCpf(String cpf) {
		if (validaCPF.verificaCpf(cpf)) {
			return true;
		} else {
			System.out.println("|CPF INVALIDO! DIGITE CORRETAMENTE|");
			return false;
		}
	}

// CHAMA O BANCO E ENVIA CPF VALIDADO RETORNANDO A CONTA 
	private static void chamarBanco() {
		Conta conta = bd.consultaCpf(validaCPF.removeCaracteresEspeciais(cpfConsole));
		if (conta != null) {
			logado = true;
			menuPrincipal(conta);
			buscaOperacaoPrincipal(conta);
		} else {
			cadastrar();
		}
	}

}

package view;

import model.Endereco;
import model.cliente.Cliente;
import model.conta.Conta;
import util.Utils;
import util.ValidaCPF;
import controller.ControlLogin;
import dao.BD;

public class Main {
	static Utils utils = new Utils();
	static String cpfConsole = "";
	static String senha = "";
	static boolean logado = false; // FLAG

	public static void main(String[] args) {
		logado = false;
		menuInicio();

	}

// EXIBE MENU LOGIN
	private static void menuInicio() {
		System.out.println(" _________________________________ ");
		System.out.println("|-----BEM VINDO AO BANCO NEXT-----|");
		System.out.println("|------------  LOGIN  ------------|");
		System.out.println("|                                 |");

		while (true) {
			cpfConsole = utils.lerConsole("|DIGITE SEU CPF:");
			if (ControlLogin.validarCpf(cpfConsole)) {
				if (ControlLogin.testaLogin(cpfConsole)) {// Verifica se cpf existe no banco
					senha = utils.lerConsole("|DIGITE SUA SENHA: ");
					buscaContaCadastrada(ControlLogin.buscaContaCadastrada(true, cpfConsole, senha));
				} else {
					break;
				}
			}

		}
		System.out.println("|_________________________________|");
		buscaContaCadastrada(ControlLogin.buscaContaCadastrada(false, cpfConsole, senha));
	}

// CADASTRA O USUARIO
	private static void cadastrar() {
		System.out.println(" _________________________________ ");
		System.out.println("|----  CADASTRO DADOS BASICOS  ---|");
		System.out.println("|SEU CPF: " + cpfConsole);
		String rg = utils.lerConsole("|DIGITE SEU RG: ");
		String nome = utils.lerConsole("|DIGITE SEU NOME COMPLETO: ");
		String email = utils.lerConsole("|DIGITE SEU EMAIL: ");
		senha = utils.lerConsole("DIGITE SUA SENHA: ");
		System.out.println(" __________________________________ ");
		System.out.println("|-------  CADASTRO ENDEREÇO  ------|");
		String rua = utils.lerConsole("|DIGITE SUA RUA: ");
		String bairro = utils.lerConsole("|DIGITE SEU BAIRRO: ");
		String numero = utils.lerConsole("|DIGITE SEU NUMERO: ");
		String cidade = utils.lerConsole("|DIGITE SUA CIDADE: ");
		String estado = utils.lerConsole("|DIGITE SEU ESTADO: ");
		String cep = utils.lerConsole("|DIGITE SEU CEP: ");
		System.out.println("|_________________________________|");

		// FALTA VALIDAR CAMPOS ANTES DE ENVIAR
		ControlLogin.cadastrarConta(senha, cpfConsole, rg, nome, email, rua, bairro, numero, cidade, estado, cep);
		System.out.println("\n\n>>CLIENTE CADASTRADO COM SUCESSO!<<\n\n");
		menuPrincipal();
		buscaOperacaoPrincipal();

	}

// EXIBE MENU PRINCIPAL
	private static void menuPrincipal() {
		System.out.println(ControlLogin.exibeDetalhesConta());
		System.out.println(" _________________________________ ");
		System.out.println("|--------  MENU PRINCIPAL  -------|");
		System.out.println("|1 - TRANSFERIR                   |");
		System.out.println("|2 - DEPOSITAR                    |");
		System.out.println("|3 - CONSULTAR SALDO              |");
		System.out.println("|4 - SACAR                        |");
		System.out.println("|0 - SAIR                         |");
		System.out.println("|_________________________________|");
	}

//SWITCH COM OPERAÇÕES PRINCIPAIS(SALDO,SAQUE,TRANSFERENCIA,DEPOSITO E SAIR)
	private static void buscaOperacaoPrincipal() {
		boolean loop = true;
		while (loop) {
			int operacao = Integer.parseInt(utils.lerConsole("|DIGITE A OPERAÇÃO: "));

			switch (operacao) {
			case 1: { // transferencia
				while (true) {
					String numDestino = utils.lerConsole("DIGITE O NUMERO DA CONTA DESTINO: ");
					double valorDeTransferencia = Double
							.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA TRANSFERIR: "));
					String[] resposta = ControlLogin.buscaContaeTransfere(numDestino, valorDeTransferencia);
					System.out.println(resposta[0]);// Exibe resposta do control
					if (resposta[1].equals("0")) {
						menuPrincipal();
						break;
					}
				}
				break;
			}
			case 2: { // deposito
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (ControlLogin.depositaNaConta()) {
						System.out.println("\n\n>>DEPOSITO REALIZADO COM SUCESSO!<< \n\n");
						menuPrincipal();
						break;
					} else {
						System.err.println("\n>>ERRO NO DEPOSITO!<< \n");
					}
				}
				break;
			}
			case 3: { // saldo
				System.out.println(ControlLogin.consultaSaldo());
				break;
			}
			case 4: { // saque
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (ControlLogin.saqueConta()) {
						System.out.println("\n\n>>SAQUE REALIZADO COM SUCESSO!<<");
						menuPrincipal();
						break;
					} else {
						System.err.println("\n>>SALDO INSUFICIENTE<<\n");
					}
				}
				break;
			}
			case 0: { // sair
				System.out.println("\n\n|        LOGOFF CONCLUIDO!        |\n\n");
				loop = false;
				break;
			}
			default:
				System.out.println("|    DIGITE NUMEROS ENTRE 0 E 3   |");
			}
			if (!loop)
				menuInicio();
		}
	}

	// VERIFICA O BANCO SE CONTEM CONTA E EXIBE A INFORMAÇÃO
	private static void buscaContaCadastrada(int op) {

		if (op == 0) {
			logado = true;
			menuPrincipal();
			buscaOperacaoPrincipal();
		} else if (op == 1) {
			cadastrar();
		} else {
			System.out.println("\n|CPF OU SENHA INCORRETOS!\n");
		}
	}
}

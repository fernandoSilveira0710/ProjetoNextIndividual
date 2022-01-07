package view;

import model.Endereco;
import util.Utils;
import controller.ControlLogin;

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

		System.out.println(" __________________________________ ");
		System.out.println("|-------  DEFINIR TIPO CONTA  -----|");
		System.out.println("|1- CONTA CORRRENTE                |\n" + "|2- CONTA POUPANÇA                 |\n"
				+ "|3- AMBOS(CORRENTE E POUPANÇA)     |");
		String tipoConta = utils.lerConsole("|DIGITE A OPÇAO:");
		;

		System.out.println("|_________________________________|");

		// FALTA VALIDAR CAMPOS ANTES DE ENVIAR
		ControlLogin.cadastrarConta(senha, cpfConsole, rg, nome, email, rua, bairro, numero, cidade, estado, cep,
				tipoConta);
		System.out.println("\n\n>>CLIENTE E CONTA CADASTRADOS COM SUCESSO!<<\n\n");
		menuPrincipal();
	}

// EXIBE MENU PRINCIPAL
// SWITCH COM OPERAÇÕES PRINCIPAIS(SALDO,SAQUE,TRANSFERENCIA,DEPOSITO,PIX E
// SAIR)
	private static void menuPrincipal() {

		System.out.println(ControlLogin.exibeDetalhesConta());
		System.out.println(" _________________________________ ");
		System.out.println("|--------  MENU PRINCIPAL  -------|");
		System.out.println("|1 - TRANSFERIR                   |");
		System.out.println("|2 - DEPOSITAR                    |");
		System.out.println("|3 - CONSULTAR SALDO              |");
		System.out.println("|4 - SACAR                        |");
		System.out.println("|5 - PIX                          |");
		System.out.println("|0 - SAIR                         |");
		System.out.println("|_________________________________|");
		buscaOperacaoPrincipal();
	}

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
					String[] resposta = ControlLogin.buscaContaeTransfere(numDestino, valorDeTransferencia,
							exibeOpcao("QUAL CONTA?"));
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
					if (ControlLogin.depositaNaConta(exibeOpcao("QUAL CONTA?"))) {
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
				System.out.println(ControlLogin.consultaSaldo(exibeOpcao("QUAL CONTA?")));
				break;
			}
			case 4: { // saque
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (ControlLogin.saqueConta(exibeOpcao("QUAL CONTA?"))) {
						System.out.println("\n\n>>SAQUE REALIZADO COM SUCESSO!<<");
						menuPrincipal();
						break;
					} else {
						System.err.println("\n>>SALDO INSUFICIENTE<<\n");
					}
				}
				break;
			}
			case 5: { // area pix
				exibeMenuPix();
				break;
			}
			case 0: { // sair
				System.out.println("\n\n|        LOGOFF CONCLUIDO!        |\n\n");
				loop = false;
				ControlLogin.zerarAlocacoesDeMemoria(); // zera os cadastros setados em memória
				break;
			}
			default:
				System.out.println("|    DIGITE NUMEROS ENTRE 0 E 3   |");
			}
			if (!loop)
				menuInicio();
		}
	}

	// EXIBE MENU COM AS OPÇÕES DE PIX
	private static void exibeMenuPix() {
		System.out.println(" _________________________________ ");
		System.out.println("|----------  MENU PIX   ----------|");
		System.out.println("|1 - CADASTRAR CHAVE PIX          |");
		System.out.println("|2 - VISUALIZAR CHAVES PIX        |");
		System.out.println("|3 - TRANSFERIR VIA PIX           |");
		System.out.println("|4 - RECEBER VIA PIX              |");
		System.out.println("|0 - VOLTAR AO MENU ANTERIOR      |");
		System.out.println("|_________________________________|");
		String op = utils.lerConsole("|DIGITE A OPERAÇÃO: ");
		buscaOperacaoPix(op);
	}

	private static void buscaOperacaoPix(String op) {
		switch (op) {
		case "1": {

		}
		case "2": {

		}
		case "3": {

		}
		case "4": {

		}
		case "0": {

		}
		default:
			System.out.println("DIGITE VALORES ENTRE 0 E 4");
		}

	}

	// VERIFICA O BANCO SE CONTEM CONTA E EXIBE A INFORMAÇÃO
	private static void buscaContaCadastrada(int op) {
		if (op == 0) {
			logado = true;
			menuPrincipal();
		} else if (op == 1) {
			cadastrar();
		} else {
			System.out.println("\n|CPF OU SENHA INCORRETOS!\n");

		}
	}

//EXIBE E SOLICITA EMAIL PARA RECUPERAR SENHA
	private static void exibirRecuperacaoSenha() {
		System.out.println("|-------  RECUPERACAO SENHA  -----|");
		while (true) {
			String emailRecuperar = utils.lerConsole("|DIGITE O EMAIL CADASTRADO: ");
			if (ControlLogin.recuperaSenha(emailRecuperar)) {
				System.out.println("\n\n|        RECUPERAÇÃO DE SENHA ENVIADA COM SUCESSO!        |\n\n");
				break;
			}
		}
		System.out.println("|_________________________________|");

	}

	// EXIBE SAIR PARA OUTRO MENU
	private static boolean exibeOpcao(String titulo) {
		if (ControlLogin.id == 3) {
			System.out.println("-------  " + titulo + "  -----");
			String resposta = utils.lerConsole("| 0 - CORRENTE | 1 - POUPANCA |" + "\n|DIGITE A OPÇÃO:              |");
			if (resposta.equals("0")) {
				System.out.println("|___________________________  |");
				return false;
			} else {
				return true;
			}
		} else if (ControlLogin.id == 2) {
			return true;
		} else if (ControlLogin.id == 1) {
			return false;
		}
		return true;

	}

}

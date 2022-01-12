package br.projetoparticularnext.com.view;

import br.projetoparticularnext.com.bean.Endereco;
import br.projetoparticularnext.com.bean.conta.TipoConta;
import br.projetoparticularnext.com.bo.ClienteBO;
import br.projetoparticularnext.com.bo.ContaBO;
import br.projetoparticularnext.com.bo.EnderecoBO;
import br.projetoparticularnext.com.bo.PixBO;
import br.projetoparticularnext.com.utils.Banco;
import br.projetoparticularnext.com.utils.Menus;
import br.projetoparticularnext.com.utils.Utils;

public class Main {
	static Utils utils = new Utils();
	static Banco banco = new Banco();
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
			if (ContaBO.validarCpf(cpfConsole)) {
				if (ContaBO.testaLogin(cpfConsole)) {// Verifica se cpf existe no banco
					senha = utils.lerConsole("|DIGITE SUA SENHA: ");
					buscaContaCadastrada(ContaBO.buscaContaCadastrada(true, cpfConsole, senha));
				} else {
					break;
				}
			}

		}
		System.out.println("|_________________________________|");
		cadastrar();
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

		System.out.println("|_________________________________|");

		// FALTA VALIDAR CAMPOS ANTES DE ENVIAR
		// CADASTRA ENDERECO,CLIENTE E CONTA
		EnderecoBO enderecoBO = new EnderecoBO(cidade, estado, bairro, numero, rua, cep);
		identificaECadastraContas(tipoConta, senha, email, cpfConsole, rg, nome, enderecoBO.endereco);
		Utils.loading("\n\nSALVANDO CLIENTE E CADASTRANDO CONTA"); // EXIBE LOADING NA TELA
		System.out.println("\n\n>>CLIENTE E CONTA CADASTRADOS COM SUCESSO!<<\n\n");
		ContaBO.buscaContaCadastrada(true, cpfConsole, senha);// RECUPERA LISTA DE CONTAS DESTE CPF
		menuPrincipal();
	}

// IDENTIFICA O TIPO DE CONTA ENUM e cadastra
	private static void identificaECadastraContas(String tipoConta, String senha, String email, String cpf, String rg,
			String nome, Endereco endereco) {
		ClienteBO clienteBO = new ClienteBO(senha, email, cpf, rg, nome, endereco);
		if (tipoConta.equals("1")) {
			new ContaBO(clienteBO.cliente, TipoConta.ContaCorrente);
		} else if (tipoConta.equals("2")) {
			new ContaBO(clienteBO.cliente, TipoConta.ContaPoupanca);
		} else if (tipoConta.equals("3")) {
			new ContaBO(clienteBO.cliente, TipoConta.ContaCorrente);
			new ContaBO(clienteBO.cliente, TipoConta.ContaPoupanca);
		}

	}

// EXIBE MENU PRINCIPAL
// SWITCH COM OPERAÇÕES PRINCIPAIS(SALDO,SAQUE,TRANSFERENCIA,DEPOSITO,PIX E
// SAIR)
	private static void menuPrincipal() {

		ContaBO.TaxaseRendimentos();// consulta taxas e rendimentos e seta valores /mes

		boolean loop = true;
		while (loop) {
			Menus.exibeOpcoesConta();// EXIBE DETALHES CONTA E MENU PRINCIPAL
			String operacao = utils.lerConsole("|DIGITE A OPERAÇÃO: ");

			switch (operacao) {
			case "1": { //transferencia
				while (true) {
					String numDestino = utils.lerConsole("DIGITE O NUMERO DA CONTA DESTINO: ");
					double valorDeTransferencia = Double
							.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA TRANSFERIR: "));
					String[] resposta = ContaBO.buscaContaeTransfere(numDestino, valorDeTransferencia,
							Menus.exibeOpcao("QUAL CONTA?"));
					Utils.loading("TRANSFERINDO"); // EXIBE LOADING NA TELA
					System.out.println(resposta[0]);// Exibe resposta do control
					if (resposta[1].equals("0")) {
						menuPrincipal();
						break;
					}
				}
				break;
			}
			case "2": { // deposito
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (ContaBO.depositaNaConta(Menus.exibeOpcao("QUAL CONTA?"))) {
						Utils.loading("\n\nDEPOSITANDO"); // EXIBE LOADING NA TELA
						System.out.println("\n\n>>DEPOSITO REALIZADO COM SUCESSO!<< \n\n");
						menuPrincipal();
						break;
					} else {
						Utils.loading("\n\nSACANDO"); // EXIBE LOADING NA TELA
						System.err.println("\n>>ERRO NO DEPOSITO!<< \n");
					}
				}
				break;
			}
			case "3": { // saldo
				System.out.println(ContaBO.consultaSaldo(Menus.exibeOpcao("QUAL CONTA?")));
				break;
			}
			case "4": { // saque
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (ContaBO.saqueConta(Menus.exibeOpcao("QUAL CONTA?"))) {
						Utils.loading("\n\nSACANDO"); // EXIBE LOADING NA TELA
						System.out.println("\n\n>>SAQUE REALIZADO COM SUCESSO!<<");
						menuPrincipal();
						break;
					} else {
						Utils.loading("\n\nSACANDO"); // EXIBE LOADING NA TELA
						System.err.println("\n>>SALDO INSUFICIENTE<<\n");
					}
				}
				break;
			}
			case "5": { // area pix
				Menus.exibeMenuPix();
				String op = utils.lerConsole("|DIGITE A OPERAÇÃO: ");
				buscaOperacaoPix(op);
				break;
			}
			case "0": { // sair
				Utils.loading("\n\nSAINDO"); // EXIBE LOADING NA TELA
				System.out.println("\n\n|        LOGOFF CONCLUIDO!        |\n\n"
						+ "====================================================================================");
				ContaBO.zerarAlocacoesDeMemoria(); // zera os cadastros setados em memória
				Banco.zerarTodasAsInstanciasBanco();
				loop = false;
				break;
			}
			default:
				System.out.println("|    DIGITE NUMEROS ENTRE 0 E 3   |");
			}
		}
		menuInicio();
	}

	private static void buscaOperacaoPix(String op) {
		switch (op) {
		case "1": {
			exibirChavesDisponiveis();
			exibirOpcoesChavesPixCadastro();
			menuPrincipal();
			break;
		}
		case "2": {
			exibirChavesDisponiveis();
			break;
		}
		case "3": {
			String chavePix = utils.lerConsole("DIGITE A CHAVE PIX DE DESTINO: ");
			double valor = Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA TRANFERIR: "));
			String[] resposta = PixBO.buscaETRansferePix(chavePix,valor,Menus.exibeOpcao("QUAL CONTA?"));
			System.out.println(resposta[0]);// Exibe resposta do control
			if (resposta[1].equals("0")) {
				menuPrincipal();
				break;
			}
		}
		case "4": {
			exibirChavesDisponiveis();
			int chavePix = Integer.parseInt(utils.lerConsole("DIGITE O ID DE CHAVE: "));
			if (PixBO.deletarChave(chavePix)) {
				System.out.println("           >>>PIX REMOVIDO COM SUCESSO!<<<    ");
				break;
			}else {
				System.err.println("           >>>O ID DO PIX NÃO EXISTE!<<<    ");
			}
			
		}
		
		case "0": {
			menuPrincipal();
		}
		default:
			System.out.println("DIGITE VALORES ENTRE 0 E 4");
		}

	}

	// CPF,Email,Telefone,Aleatorio;
	private static void exibirOpcoesChavesPixCadastro() {
		while (true) {
			Menus.exibeTiposChavesPix();
			String op = utils.lerConsole("|DIGITE A OPÇÃO: ");
			if (buscaOperacaoTipoPix(op)) {
				System.out.println("\n   >>CHAVE PIX REGISTRADA COM SUCESSO!<<  ");
				break;
			} else {
				System.out.println("\n   >>ERRO NA CHAVE PIX OU PIX JÁ EXISTE, DIGITE NOVAMENTE!<<  ");
			}
		}
	}

//VERIFICA TIPO DE OPERACAO PIX E RETORNA MSG TOMANDO UMA AÇÃO
	// int idCC, int idCP, TipoChavePix tipoChavePix, String cpf, boolean b
	// int tipoChavePix, String conteudoChave,int chave, boolean b
	private static boolean buscaOperacaoTipoPix(String op) {

		switch (op) {
		case "0": {
			return PixBO.cadastraChavePix(0, cpfConsole, true);
		}
		case "1": {
			String email = utils.lerConsole("DIGITE O EMAIL: ");
			return PixBO.cadastraChavePix(1, email, true);
		}
		case "2": {
			String telefone = utils.lerConsole("DIGITE O TELEFONE: ");
			return PixBO.cadastraChavePix(2, telefone, true);
		}
		case "3": {
			return PixBO.cadastraChavePix(3, "", true);
		}
		default:
			System.out.println("DIGITE VALORES ENTRE 0 E 3");
			return false;
		}

	}

	private static void exibirChavesDisponiveis() {
		String textoCompleto = "";
		if (ContaBO.cc != null && ContaBO.cp != null) {
			textoCompleto = PixBO.exibirChavesPix(ContaBO.cc.getNumero(), ContaBO.cp.getNumero());
			System.out.println(textoCompleto);
		} else if (ContaBO.cc != null) {
			textoCompleto = PixBO.exibirChavesPix(ContaBO.cc.getNumero(), "0");
			System.out.println(textoCompleto);
		} else if (ContaBO.cp != null) {
			textoCompleto = PixBO.exibirChavesPix("0", ContaBO.cp.getNumero());
			System.out.println(textoCompleto);
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
			if (ClienteBO.recuperaSenha(emailRecuperar)) {
				System.out.println("\n\n|        RECUPERAÇÃO DE SENHA ENVIADA COM SUCESSO!        |\n\n");
				break;
			}
		}
		System.out.println("|_________________________________|");

	}

}

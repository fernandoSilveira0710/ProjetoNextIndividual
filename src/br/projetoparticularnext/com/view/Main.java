package br.projetoparticularnext.com.view;

import br.projetoparticularnext.com.bean.Endereco;
import br.projetoparticularnext.com.bean.cartao.CartaoCredito;
import br.projetoparticularnext.com.bean.cartao.CartaoDebito;
import br.projetoparticularnext.com.bean.conta.TipoConta;
import br.projetoparticularnext.com.bo.CartaoBO;
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
		// Utils.returnDataDiaDefinido("20");

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
			System.out.println(ContaBO.exibeDetalhesConta());// BUSCA DETALHES CONTA
			Menus.exibeOpcoesConta();// EXIBE DETALHES CONTA E MENU PRINCIPAL
			String operacao = utils.lerConsole("|DIGITE A OPERAÇÃO: ");

			switch (operacao) {
			case "1": { // transferencia
				while (true) {
					String numDestino = utils.lerConsole("DIGITE O NUMERO DA CONTA DESTINO: ");
					double valorDeTransferencia = Double
							.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA TRANSFERIR: "));
					String[] resposta = ContaBO.buscaContaeTransfere(numDestino, valorDeTransferencia,
							Menus.exibeOpcaoConta("QUAL CONTA?"));
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
					if (ContaBO.depositaNaConta(Menus.exibeOpcaoConta("QUAL CONTA?"))) {
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
				System.out.println(ContaBO.consultaSaldo(Menus.exibeOpcaoConta("QUAL CONTA?")));
				break;
			}
			case "4": { // saque
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (ContaBO.saqueConta(Menus.exibeOpcaoConta("QUAL CONTA?"))) {
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
			case "6": { // area crédito
				boolean cadastrado = false;
				boolean cartaoAtivado = false;
				CartaoCredito credito = CartaoBO.recuperaCartaoCredito();
				if (credito != null) {
					cartaoAtivado = credito.isAtivo();
					cadastrado = true;
					Menus.dadosCartoes(credito.getNumero(), String.valueOf(credito.getDataVencimento()),
							credito.getValorFatura(), credito.getLimite(), "disponivel",cartaoAtivado);
				}
				Menus.exibeOpcoesCartaoCredito(cadastrado, cartaoAtivado);
				String op = utils.lerConsole("|DIGITE A OPERAÇÃO: ");
				buscaOperacaoCredito(op, cadastrado, cartaoAtivado, credito);
				break;
			}
			case "7": { // area debitos
				boolean cadastrado = false;
				boolean cartaoAtivado = false;
				CartaoDebito debito = CartaoBO.recuperaCartaoDebito();
				if (debito != null) {
					cadastrado = true;
					cartaoAtivado = debito.isAtivo();
					Menus.dadosCartoes(debito.getNumero(), "", debito.getLimitePorTransacao(),
							debito.getLimitePorTransacao(), "P/Transacao",cartaoAtivado);
				}
				Menus.exibeOpcoesCartaoDebito(cadastrado, cartaoAtivado);
				String op = utils.lerConsole("|DIGITE A OPERAÇÃO: ");
				buscaOperacaoDebito(op, cadastrado, cartaoAtivado,debito);
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

	// BUSCA OPERAÇÕES E EXECUTA OPERAÇÕES DO MENU DÉBITO
	private static void buscaOperacaoCredito(String op, boolean cadastrado, boolean ativado, CartaoCredito credito) {
		if (op.equals("*")) {
			String numBandeira = retornaBandeira();
			if (!numBandeira.equals("") || !numBandeira.equals("0") && cadastrado) {
				long senha = retornaSenha();// solicita senha e retorna
				String dataVencimento = escolherDataVencimento();
				if (CartaoBO.cadastraCartaoCredito(numBandeira, senha, true, dataVencimento)) {
					System.out.println("     >>CARTÃO DE CRÉDITO CRIADO COM SUCESSO!<<    ");
				} else {
					System.err.println("\n     >>HOUVE UM ERRO AO CRIAR O CARTÃO!<<     ");
				}

			}
		} else if (op.equals("1") && cadastrado) {//COMPRAR
			if(ativado) {
				comprarComCartao(1);// 1 credito e 2 debito
			}else {
				System.err.println("         >>CARTÃO SE ENCONTRA BLOQUEADO!<<");
			}
		} else if (op.equals("2") && cadastrado) {// CONSULTAR FATURA
			System.out.println(CartaoBO.consultaFaturasCredito());  
		} else if (op.equals("3") && cadastrado) {// ALTERA VENCIMENTO
			System.out.println("DIA DE VENCIMENTO ATUAL: " + credito.getDataVencimento());
			String dataVencimento = escolherDataVencimento();
			System.out.println(
					CartaoBO.alteraDataVencimento(dataVencimento) ? "\n          >>DATA ALTERADA COM SUCESSO!<<\n"
							: "        >>HOUVE UM ERRO NA ALTERAÇÃO DA DATA!<<");
		} else if (op.equals("4") && cadastrado) {// PAGAMENTO DE FATURA
			System.out.println("\n>>FATURA ATUAL:"+utils.convertToReais(credito.getValorFatura()));
			double valorPagamento = Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA PAGAR: "));
			System.out.println(CartaoBO.debitarFaturaCredito(valorPagamento));
		} else if (op.equals("5") && cadastrado) {// BLOQUEIA CARTON
			exibeAtivacao(ativado, 2);// 1 = debito 2= credito
		}
	}

	public static void comprarComCartao(int op) {
		Menus.exibeMenuCompra();
		String descricao = utils.lerConsole("NOME DO PRODUTO: ");
		double valor = Double.parseDouble(utils.lerConsole("DIGITE O VALOR DO PRODUTO: "));
		String senha = utils.lerConsole("DIGITE A SENHA: ");
		if(op ==1 ) {
			System.out.println(
					CartaoBO.cadastraCompraCredito(descricao,valor,senha));
		}else {
			System.out.println(
					CartaoBO.cadastraCompraDebito(descricao,valor,senha));
		}
	}

	// BUSCA OPERAÇÕES E EXECUTA OPERAÇÕES DO MENU CRÉDITO
	private static void buscaOperacaoDebito(String op, boolean cadastrado, boolean ativado, CartaoDebito debito) {
		if (op.equals("*")) {
			String numBandeira = retornaBandeira();
			if (!numBandeira.equals("") || !numBandeira.equals("0") && cadastrado) {
				// SETANDO SENHA
				long senha = retornaSenha();
				if (CartaoBO.cadastraCartaoDebito(numBandeira, senha, true, returnLimite())) {
					System.out.println("     >>CARTÃO DE DÉBITO CRIADO COM SUCESSO!<<    ");
				} else {
					System.err.println("\n     >>HOUVE UM ERRO AO CRIAR O CARTÃO!<<     ");
				}
			}
		} else if (op.equals("1") && cadastrado) {//COMPRAR
			comprarComCartao(2);// 1 credito e 2 debito
			
		} else if (op.equals("2") && cadastrado) {// CONSULTA EXTRATO
			System.out.println(CartaoBO.consultaExtratoDebito());

		} else if (op.equals("3") && cadastrado) {// ALTERA LIMITE P/TRANSACAO
			System.out.println("\n--------------- ALTERAR LIMITE POR TRANSACAO ---------------");
			System.out.println(">>LIMITE ATUAL: " + debito.getLimitePorTransacao());
			double novoLimiteTransacao = returnLimite();
			System.out.println(
					CartaoBO.alterarLimitePorTransacao(novoLimiteTransacao) ? "\n          >>LIMITE ALTERADO COM SUCESSO!<<\n"
							: "        >>HOUVE UM ERRO NA ALTERAÇÃO DO LIMITE!<<");
		} else if (op.equals("4") && cadastrado) {// BLOQUEIA CARTON
			exibeAtivacao(ativado, 1);// 1 = debito 2= credito
		}
	}
// retorna o limite com base na opçao selicionada
	private static double returnLimite() {
		Menus.exibeLimites();// EXIBE LIMITES NO CONSOLE
		String opLimite = utils.lerConsole("|DIGITE A OPERAÇÃO DO LIMITE:");
		if (opLimite.equals("1")) {
			return 100.0;
		} else if (opLimite.equals("2")) {
			return 500.0;
		} else if (opLimite.equals("3")) {
			return 1000.0;
		} else if (opLimite.equals("4")) {
			return 5000.0;
		} else if (opLimite.equals("5")) {
			return 10000.0;
		}
		return 100.0;
	}
// retorna a bandeira do cartão com base na opcao selecionada
	public static String retornaBandeira() {
		Menus.exibeBandeirasCartoes();
		String numBandeira = utils.lerConsole("|DIGITE A OPERAÇÃO DA BANDEIRA: ");
		// SETANDO BANDEIRA
		if (numBandeira.equals("0")) {// VOLTA MENU ANTERIOR
			System.out.println("\n\nCANCELOU A OPERAÇÃO DE CADASTRO!");

		} else if (numBandeira.equals("1"))// VISA
		{
			System.out.println("\n    >>VISA SELECIONADO!<<    \n");
			numBandeira = "VISA";

		} else if (numBandeira.equals("2")) {// MASTERCARD
			System.out.println("\n    >>MASTERCARD SELECIONADO!<<    \n");
			numBandeira = "MASTERCARD";
		} else if (numBandeira.equals("3")) {// MASTERCARD
			System.out.println("\n    >>ELO SELECIONADO!<<    \n");
			numBandeira = "ELO";
		}
		return numBandeira;
	}

	// ATIVANDO OU DESATIVANDO CARTON
	public static void exibeAtivacao(boolean ativado, int tipo) {
		if (ativado) {
			boolean operacao = (tipo == 1) ? CartaoBO.ativaDesativaDebito(false) : CartaoBO.ativaDesativaCredito(false);
			System.out.println(
					(operacao) ? "\n     >>CARTÃO BLOQUEADO!<<     \n" : "\n     >>HOUVE UM ERRO NO BLOQUEIO<<     \n");

		} else {
			boolean operacao = (tipo == 1) ? CartaoBO.ativaDesativaDebito(true) : CartaoBO.ativaDesativaCredito(true);
			System.out.println((operacao) ? "\n     >>CARTÃO DESBLOQUEADO!<<     \n"
					: "\n     >>HOUVE UM ERRO NO DESBLOQUEIO<<     \n");
		}
	}

// SOLICITA UMA SENHA DO USUARIO E RETORNA 
	public static long retornaSenha() {
		// SETANDO SENHA
		long senha = 0000;
		while (true) {
			try {
				senha = Long.parseLong(utils.lerConsole("|DIGITE UMA SENHA DE 4 NUMEROS: "));
				break;
			} catch (NumberFormatException e) {
				System.err.println("SUA SENHA DEVE CONTER APENAS NUMEROS");
				continue;
			}
		}
		return senha;
	}

// ESCOLHER DATA DE VENCIMENTO E RETORNA
	private static String escolherDataVencimento() {
		Menus.exibeDatasVencimento();
		String op = utils.lerConsole("DIGITE A DATA DE VENCIMENTO DA FATURA: ");
		if (op.equals("1")) {
			return "1";
		} else if (op.equals("2")) {
			return "5";
		} else if (op.equals("3")) {
			return "10";
		} else if (op.equals("4")) {
			return "15";
		}
		return "5";
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
			String[] resposta = PixBO.buscaETRansferePix(chavePix, valor, Menus.exibeOpcaoConta("QUAL CONTA?"));
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
			} else {
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
			menuPrincipal();// chama menu principal
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
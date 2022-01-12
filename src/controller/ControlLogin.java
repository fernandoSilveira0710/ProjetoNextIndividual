//package controller;
//
//import java.util.UUID;
//
//import br.projetoparticularnext.com.bean.Endereco;
//import br.projetoparticularnext.com.bean.cliente.Cliente;
//import br.projetoparticularnext.com.bean.conta.Conta;
//import br.projetoparticularnext.com.bean.conta.ContaCorrente;
//import br.projetoparticularnext.com.bean.conta.ContaPoupanca;
//import br.projetoparticularnext.com.bean.pix.Pix;
//import br.projetoparticularnext.com.bean.pix.TipoChavePix;
//import br.projetoparticularnext.com.utils.Banco;
//import br.projetoparticularnext.com.utils.Utils;
//import br.projetoparticularnext.com.utils.ValidaCPF;
//
//public class ControlLogin {
//	static Banco bd = new Banco();
//	static Utils utils = new Utils();
//	static ValidaCPF validaCPF = new ValidaCPF();
//	static ContaPoupanca cp;
//	static ContaCorrente cc;
//	public static int id = 0;
//
//	// VERIFICA SE SENHA É COMPATIVEL COM CONTA
//	public static boolean testaLogin(String cpfConsole) {
//		if (bd.consultaCpfBanco(cpfConsole)) {
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	// CRIA CONTA ANTES DE CADASTRAR
//	public static void cadastrarConta(String senha, String cpfConsole, String rg, String nome, String email, String rua,
//			String bairro, String numero, String cidade, String estado, String cep, String tipoConta) {
//		Endereco endereco = new Endereco(cidade, estado, bairro, numero, rua, cep);
//		Cliente cliente = new Cliente(senha, email, cpfConsole, rg, nome, endereco);
//		identificaECadastraContas(tipoConta, cliente);
//	}
//
//// IDENTIFICA O TIPO DE CONTA ENUM
//	private static void identificaECadastraContas(String tipoConta, Cliente cliente) {
//		if (tipoConta.equals("1")) {
//			cc = new ContaCorrente(cliente);
//			bd.adicionaContaCorrente(cc);
//			id = 1;
//		} else if (tipoConta.equals("2")) {
//			cp = new ContaPoupanca(cliente);
//			bd.adicionaContaPoupanca(cp);
//			id = 2;
//		} else if (tipoConta.equals("3")) {
//			cp = new ContaPoupanca(cliente);
//			cc = new ContaCorrente(cliente);
//			bd.adicionaContaCorrente(cc);
//			bd.adicionaContaPoupanca(cp);
//
//			id = 3;
//		}
//
//	}
//
//	// RETORNA UMA STRING COM DETAALHES DA CONTA
//	public static String exibeDetalhesConta() {
//		if (cc != null && cp != null) {
//			return "\n\n>>OLÁ " + cc.getCliente().getNome().toUpperCase() + "\n>>NUMERO CORRENTE: " + cc.getNumero()
//					+ " | NUMERO POUPANCA: " + cp.getNumero() + "\n>>SALDO CORRENTE:"
//					+ utils.convertToReais(cc.consultarSaldo()) + "\n>>SALDO POUPANCA:"
//					+ utils.convertToReais(cp.consultarSaldo()) + "\n>>TIPO DE CONTA:" + cc.getCliente().getTipo();
//		} else if (cc != null && cp == null) {
//			return "\n\n>>OLÁ " + cc.getCliente().getNome().toUpperCase() + "\n>>NUMERO CONTA CORRENTE: "
//					+ cc.getNumero() + "\n>>SALDO DISPONIVEL:" + utils.convertToReais(cc.consultarSaldo())
//					+ "\n>>TIPO DE CONTA:" + cc.getCliente().getTipo();
//		} else if (cc == null && cp != null) {
//			return "\n\n>>OLÁ " + cp.getCliente().getNome().toUpperCase() + "\n>>NUMERO CONTA POUPANÇA: "
//					+ cp.getNumero() + "\n>>SALDO DISPONIVEL:" + utils.convertToReais(cp.consultarSaldo())
//					+ "\n>>TIPO DE CONTA:" + cp.getCliente().getTipo();
//		}
//		return null;
//
//	}
//
//	// BUSCA CONTA E TRANSFERE EM CONTA
//	public static String[] buscaContaeTransfere(String numDestino, double valorDeTransferencia, boolean tipo) {
//		String resposta[] = new String[2];
//		Conta contaDestino = consultaContaDestinoExistente(numDestino);
//		boolean verifica = false;
//		if (contaDestino != null) {
//			if (!tipo && bd.tipoConta == 1) {
//				verifica = cc.transferir(contaDestino, valorDeTransferencia);
//			} else if (!tipo && bd.tipoConta == 2) {
//				cc.saque(5.60);// DESCONTA TAXA
//				verifica = cc.transferir(contaDestino, valorDeTransferencia);
//			} else if (tipo && bd.tipoConta == 2) {
//				verifica = cp.transferir(contaDestino, valorDeTransferencia);
//			}
//			if (verifica) {
//				resposta[0] = "\n>>TRANSFERENCIA DE " + utils.convertToReais(valorDeTransferencia) + "\n PARA "
//						+ contaDestino.getCliente().getNome().toUpperCase() + " REALIZADO COM SUCESSO<< \n\n";
//				resposta[1] = "0";
//			} else {
//				resposta[0] = "ERRO NA TRANSFERENCIA!SALDO INSUFICIENTE \n";
//				resposta[1] = "1";
//			}
//		} else {
//			resposta[0] = "ESTA CONTA NÃO EXISTE! \n";
//			resposta[1] = "2";
//		}
//		return resposta;
//	}
//
//	// CONSULTA CONTA DE DESTINO
//	private static Conta consultaContaDestinoExistente(String numDestino) {
//		Conta contaDestino = bd.identificaContaNum(numDestino);// conta recebida do bd
//		return contaDestino;
//	}
//
//	// SAQUE EM CONTA
//	public static boolean saqueConta(boolean b) {
//		if (!b) {
//			return cc.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")));
//		} else {
//			return cp.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")));
//		}
//
//	}
//
//	// DEPOSITA EM CONTA
//	public static boolean depositaNaConta(boolean b) {
//		if (!b) {
//			return cc.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")));
//		} else {
//			return cp.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")));
//		}
//
//	}
//
//// RETORNA SALDO
//	public static String consultaSaldo(boolean b) {
//		if (!b) {
//			return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(cc.consultarSaldo()) + "<<\n";
//		} else {
//			return "\n>>SALDO DISPONIVEL: " + utils.convertToReais(cp.consultarSaldo()) + "<<\n";
//		}
//	}
//
//	// ENVIA DADOS PARA UMA CLASSE VALIDACAO E RETORNA BOOLEAN
//	public static boolean validarCpf(String cpf) {
//		if (validaCPF.verificaCpf(cpf)) {
//			return true;
//		} else {
//			System.out.println("\n|CPF INVALIDO! DIGITE CORRETAMENTE|\n");
//			return false;
//		}
//	}
//
//	// CHAMA O BANCO E ENVIA CPF VALIDADO RETORNANDO A CONTA
//	public static int buscaContaCadastrada(boolean login, String cpfConsole, String senha) {
//		cc = bd.retornaContaCorrente(validaCPF.removeCaracteresEspeciais(cpfConsole), senha);// ANEXANDO CONTA CORRENTE
//																								// DO BANCO AO CC LOCAL
//		cp = bd.retornaContaPoupanca(validaCPF.removeCaracteresEspeciais(cpfConsole), senha);// ANEXANDO CONTA POUPANCA
//																								// DO BANCO AO CP LOCAL
//		identificaContas();
//		if (cc != null && login || cp != null && login) {
//
//			return 0;
//		} else if (!login) {
//			return 1;
//		} else {
//			return 2;
//		}
//	}
////	// BUSCA E TRANFERE VIA CHAVE PIX
////	public static boolean buscaETRansferePix(String chavePix) {
////		return bd.buscaeTransferePix(chavePix);
////	}
//
//	// EXIBE CHAVE PIX BUSCANDO POR CC E CP
//	public static String exibirChavesPix() {
//		if (cc == null && cp != null) {
//			return bd.consultaPix(0, cp.getId());
//		} else if (cc != null && cp == null) {
//			return bd.consultaPix(cc.getId(), 0);
//		}
//		return bd.consultaPix(cc.getId(), cp.getId());
//	}
//
//	// CRIA CHAVE PIX
//	public static boolean cadastraChavePix(int tipoChavePix, String conteudoChave, boolean b) {
//		return bd.cadastraChavePix(cc.getId(), cp.getId(), identificaTipoChavePix(tipoChavePix),
//				identificaConteudoChave(tipoChavePix, conteudoChave), b);
//	}
//
//	// IDENTIFICA CONTEUDO CHAVE
//	private static String identificaConteudoChave(int chave, String conteudoChave) {
//		String cpf = null;
//		if (cc != null)
//			cpf = cc.getCliente().getCpf();
//		if (cp != null)
//			cpf = cp.getCliente().getCpf();
//		switch (chave) {
//		case 0: {
//			return cpf;
//		}
//		case 1: {
//			return conteudoChave;
//
//		}
//		case 2: {
//			return conteudoChave;
//		}
//		case 3: {
//			return Utils.gerarAleatorio();
//		}
//		}
//		return gerarAleatorio();
//	}
//
//
//
//	// IDENTIFICA TIPO DE CHAVE
//	private static TipoChavePix identificaTipoChavePix(int tipoChavePix) {
//		switch (tipoChavePix) {
//		case 0: {
//			return TipoChavePix.CPF;
//		}
//		case 1: {
//			return TipoChavePix.Email;
//
//		}
//		case 2: {
//			return TipoChavePix.Telefone;
//		}
//		case 3: {
//			return TipoChavePix.Aleatorio;
//		}
//		}
//		return TipoChavePix.Aleatorio;
//	}
//
//	// RECUPERA SENHA
//	public static boolean recuperaSenha(String email) {
//		// futuramente com o banco e uso de APIs implementar
//		return true;
//	}
//
//	// ZERAR CADASTROS ALOCADOS EM MEMORIA
//	public static void zerarAlocacoesDeMemoria() {
//		cc = null;
//		cp = null;
//		id = 0;
//	}
//
//}

package br.projetoparticularnext.com.utils;

import br.projetoparticularnext.com.bo.ContaBO;

public class Menus {
	private static Utils utils = new Utils();

	// EXIBE MENU COM AS OPÇÕES DE PIX
	public static void exibeMenuPix() {
		System.out.println(" _________________________________ ");
		System.out.println("|----------  MENU PIX   ----------|");
		System.out.println("|1 - CADASTRAR CHAVE PIX          |");
		System.out.println("|2 - VISUALIZAR CHAVES PIX        |");
		System.out.println("|3 - TRANSFERIR VIA PIX           |");
		System.out.println("|4 - DELETAR CHAVE PIX            |");
		System.out.println("|0 - VOLTAR AO MENU ANTERIOR      |");
		System.out.println("|_________________________________|");
	}

	public static void exibeTiposChavesPix() {
		System.out.println(" _________________");
		System.out.println("|--- TIPO PIX ----|");
		System.out.println("|0 - CPF          |");
		System.out.println("|1 - EMAIL        |");
		System.out.println("|2 - TELEFONE     |");
		System.out.println("|3 - ALEATORIO    |");
		System.out.println("|_________________|");
	}

	// EXIBE SAIR PARA OUTRO MENU
	public static boolean exibeOpcaoConta(String titulo) {
		if (ContaBO.id == 3) {
			System.out.println("-------  " + titulo + "  -----");
			String resposta = utils.lerConsole("| 0 - CORRENTE | 1 - POUPANCA |" + "\n|DIGITE A OPÇÃO:");
			if (resposta.equals("0")) {
				System.out.println("|___________________________  |");
				return false;
			} else {
				return true;
			}
		} else if (ContaBO.id == 2) {
			return true;
		} else if (ContaBO.id == 1) {
			return false;
		}
		return true;

	}

	public static void exibeOpcoesConta() {
		System.out.println(" ___________________________________");
		System.out.println("|--------  MENU PRINCIPAL  ---------|");
		System.out.println("|1 - TRANSFERIR        2 - DEPOSITO |");
		System.out.println("|3 - CONSULTAR SALDO   4 - SACAR    |");
		System.out.println("|5 - CRÉDITO           6 - DÉBITO   |");
		System.out.println("|7 - PIX               0 - SAIR     |");
		System.out.println("|___________________________________|");
	}

	// EXIBE OPÇOES DE CARTÕES
	public static void exibeOpcoesCartaoCredito(boolean cadastrado, boolean ativado ) {
		System.out.println(" ___________________________");
		System.out.println("|---- CARTÃO DE CRÉDITO ----|");
		if(!cadastrado)System.out.println("|* - CRIAR CARTÃO DE CRÉDITO|");
		if(cadastrado)System.out.println("|1 - COMPRAR                |");
		if(cadastrado)System.out.println("|2 - CONSULTAR FATURA       |");
		if(cadastrado)System.out.println("|3 - ALTERAR VENCIMENTO     |");
		if(cadastrado)System.out.println("|4 - PAGAMENTO DE FATURA    |");
		if(cadastrado && ativado)System.out.println("|5 - BLOQUEAR CARTAO        |");
		if(cadastrado && !ativado)System.out.println("|5 - DESBLOQUEAR CARTAO     |");
		System.out.println("|0 - VOLTAR MENU ANTERIOR   |");
		System.out.println("|___________________________|");
	}

	// EXIBE OPÇOES DE CARTÕES
	public static void exibeOpcoesCartaoDebito(boolean cadastrado, boolean ativado ) {
		              System.out.println(" ______________________________");
		              System.out.println("|----- CARTÃO DE DÉBITO -------|");
		if(!cadastrado)System.out.println("|* - CRIAR CARTÃO DE DÉBITO   |");
		if(cadastrado)System.out.println("|1 - COMPRAR                   |");
		if(cadastrado)System.out.println("|2 - CONSULTAR EXTRATO         |");
		if(cadastrado)System.out.println("|3 - ALTERAR LIMITE P/TRANSACAO|");
		if(cadastrado && ativado)System.out.println("|4 - BLOQUEAR CARTAO        |");
		if(cadastrado && !ativado)System.out.println("|4 - DESBLOQUEAR CARTAO     |");
		              System.out.println("|0 - VOLTAR MENU ANTERIOR      |");
		              System.out.println("|______________________________|");
	}
	public static void exibeBandeirasCartoes() {
		System.out.println(" _________________________");
		System.out.println("|--- ESCOLHA A BANDEIRA --|");
		System.out.println("|1 - VISA                 |");
		System.out.println("|2 - MASTERCARD           |");
		System.out.println("|3 - ELO      			  |");
		System.out.println("|0 - CANCELAR             |");
		System.out.println("|_________________________|");
	}
	public static void exibeDatasVencimento() {
		System.out.println(" _____________");
		System.out.println("|--- DATA --- |");
		System.out.println("|1 - DIA 1    |");
		System.out.println("|2 - DIA 5    |");
		System.out.println("|3 - DIA 10   |");
		System.out.println("|4 - DIA 15   |");
		System.out.println("|0 - CANCELAR |");
		System.out.println("|____________ |");
	}
	public static void exibeLimites() {
		System.out.println(" ___________________");
		System.out.println("|LIMITES P/TRANSACAO|");
		System.out.println("|1 - R$100,00       |");
		System.out.println("|2 - R$500,00       |");
		System.out.println("|3 - R$1000,00      |");
		System.out.println("|4 - R$5000,00      |");
		System.out.println("|5 - R$10000,00     |");
		System.out.println("|___________________|");
	}
	
	public static void dadosCartoes(String numero, String validade, double fatura, double limite,String descrLimite, boolean status) {
		String tipo = "";
		if(status) tipo = "desbloqueado";
		else tipo = "bloqueado";
		System.out.println(" _________________________________________");
		System.out.println("|-------- DADOS DO CARTAO ----------------|");
		System.out.println("| Limite "+descrLimite+":"+Utils.convertToReais(limite)+"         ");
		System.out.println("| numero:"+numero.toUpperCase()+"         ");
		if(!validade.equals(""))System.out.println("| vencimento:"+validade.toUpperCase()+"       ");
		if(!validade.equals(""))System.out.println("| fatura:"+Utils.convertToReais(fatura)+"       status:"+tipo);
		System.out.println(" _________________________________________");
	}

	public static void exibeMenuCompra() {
		System.out.println(" _________________________");
		System.out.println("|------ NOVA COMPRA ------|");
	}
}
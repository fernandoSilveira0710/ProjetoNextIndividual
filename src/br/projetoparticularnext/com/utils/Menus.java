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
			System.out.println("|3 - TRANSFERIR VIA PIX (INDISP)  |");
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
		public static boolean exibeOpcao(String titulo) {
			System.out.println(ContaBO.id);
			if (ContaBO.id == 3) {
				System.out.println("-------  " + titulo + "  -----");
				String resposta = utils .lerConsole("| 0 - CORRENTE | 1 - POUPANCA |" + "\n|DIGITE A OPÇÃO:");
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
			System.out.println(" _________________________________ ");
			System.out.println("|--------  MENU PRINCIPAL  -------|");
			System.out.println("|1 - TRANSFERIR                   |");
			System.out.println("|2 - DEPOSITAR                    |");
			System.out.println("|3 - CONSULTAR SALDO              |");
			System.out.println("|4 - SACAR                        |");
			System.out.println("|5 - PIX                          |");
			System.out.println("|0 - SAIR                         |");
			System.out.println("|_________________________________|");
		}
		
		
}

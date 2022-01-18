package br.projetoparticularnext.com.bo;

import br.projetoparticularnext.com.bean.cartao.*;
import br.projetoparticularnext.com.utils.Banco;
import br.projetoparticularnext.com.utils.Utils;

public class CartaoBO {

	// DEBITA FATURA AUTOMATICAMENTE
		public static void faturaAutomatica() {
			
		}
	// cadastra cartão de crédito ou débito
	// String bandeira, String senha, boolean isAtivo, double limite => CARTAO
	// CREDITO CONTRUTOR
	public static boolean cadastraCartaoCredito(String bandeira, String senha, boolean isAtivo, String dataVencimento) {
		double limite = ContaBO.buscaLimiteConta();
		CartaoCredito credito = new CartaoCredito(bandeira, senha, isAtivo, limite, dataVencimento);
		if (Banco.cadastraCartaoCredito(credito)) {
			return true;
		} else
			return false;

	}

	// String bandeira, String senha, boolean isAtivo, double limitePorTransacao
	public static boolean cadastraCartaoDebito(String bandeira, String senha, boolean isAtivo,
			double limitePorTransacao) {
		CartaoDebito debito = new CartaoDebito(bandeira,senha, isAtivo, limitePorTransacao);
		if (Banco.cadastraCartaoDebito(debito)) {
			return true;
		} else
			return false;

	}

	public static CartaoCredito recuperaCartaoCredito() {
		return Banco.recuperaCartaoCredito();
	}

	public static CartaoDebito recuperaCartaoDebito() {
		return Banco.recuperaCartaoDebito();
	}
//DESATIVA CARTÃO DE CREDITO
	public static boolean ativaDesativaCredito(boolean b) {
		return Banco.ativaDesativaCartaoCredito(b);
	}

	public static boolean ativaDesativaDebito(boolean b) {
		
		return Banco.ativaDesativaCartaoDebito(b);
	}

	public static boolean alteraDataVencimento(String dataVencimento) {
		return Banco.updateDataVencimento(dataVencimento);
		
	}
	public static String consultaExtratoDebito() {
		return Banco.consultaExtratoDebito();
	}

	public static String consultaFaturasCredito() {
		return Banco.consultaFaturaCredito();
	}

	public static boolean alterarLimitePorTransacao(double novoLimiteTransacao) {
		return Banco.updateLimitePortTransacao(novoLimiteTransacao);
	}

	public static String cadastraCompraCredito(String descricao, double valor, String senha) {
		return Banco.cadastraCompraCredito(descricao,valor,senha);
	}

	public static String cadastraCompraDebito(String descricao, double valor, String senha) {
		return Banco.cadastraCompraDebito(descricao,valor,senha);
	}

	public static String debitarFaturaCredito(double valorPagamento) {
		return Banco.debitarFaturaCredito(valorPagamento);
	}


}

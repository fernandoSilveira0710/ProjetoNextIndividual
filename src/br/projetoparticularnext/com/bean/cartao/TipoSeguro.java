package br.projetoparticularnext.com.bean.cartao;

import br.projetoparticularnext.com.utils.Const;

public enum TipoSeguro {
	MORTE(Const.REGRA_MORTE,Const.VALOR_SEGURO_ANUAL_MORTE),
	INVALIDEZ(Const.REGRA_INVALIDEZ,Const.VALOR_SEGURO_ANUAL_INVALIDEZ),
	DESEMPREGO(Const.REGRA_DESEMPREGO,Const.VALOR_SEGURO_ANUAL_DESEMPREGO);

	private String[] regra;
	private double valorSeguro;
	
	TipoSeguro(String[] regra, double valorSeguro) {
		this.regra = regra;
		this.valorSeguro = valorSeguro;
	}
	
	public String[] getRegra() {
		return this.regra;
	}
	
	public double getValorSeguro() {
		return this.valorSeguro;
	}
}

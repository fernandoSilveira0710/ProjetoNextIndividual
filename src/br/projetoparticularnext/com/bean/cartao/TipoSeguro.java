package br.projetoparticularnext.com.bean.cartao;

import br.projetoparticularnext.com.utils.Const;

public enum TipoSeguro {
	MORTE(Const.REGRA_MORTE,Const.VALOR_SEGURO_ANUAL_MORTE,Const.TAXA_SEGURO_MORTE),
	INVALIDEZ(Const.REGRA_INVALIDEZ,Const.VALOR_SEGURO_ANUAL_INVALIDEZ,Const.TAXA_SEGURO_INVALIDEZ),
	DESEMPREGO(Const.REGRA_DESEMPREGO,Const.VALOR_SEGURO_ANUAL_DESEMPREGO,Const.TAXA_SEGURO_DESEMPREGO);

	private String[] regra;
	private double valorSeguro;
	private double taxa;
	
	TipoSeguro(String[] regra, double valorSeguro, double taxa) {
		this.regra = regra;
		this.valorSeguro = valorSeguro;
		this.taxa = taxa;
	}
	
	public String[] getRegra() {
		return this.regra;
	}
	
	public double getValorSeguro() {
		return this.valorSeguro;
	}

	double getTaxa() {
		return this.taxa;
	}
}

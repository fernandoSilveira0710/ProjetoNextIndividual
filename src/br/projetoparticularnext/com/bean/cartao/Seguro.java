package br.projetoparticularnext.com.bean.cartao;

import br.projetoparticularnext.com.utils.Const;

public class Seguro {
	private int id;
	private String regras[];
	private double valor;

	private TipoSeguro tipoSeguro;
	private String nome;

	public Seguro( TipoSeguro tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
		this.nome = tipoSeguro.name();
		this.id = newId();
		this.valor = buscaValorApolice(tipoSeguro);
		this.regras = buscaRegra(tipoSeguro);
	}

	private double buscaValorApolice(TipoSeguro tipoSeguro) {
		return tipoSeguro.getValorSeguro();
	}

	private int newId() {
		return Const.SEGUROS_CRIADOS++;
	}
	//BUSCA REGRA APÓS INICIALIZAR CONSTRUTOR
	private String[] buscaRegra(TipoSeguro tipoSeguro) {
		return tipoSeguro.getRegra();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String[] getRegras() {
		return regras;
	}

	public void setRegras(String[] regras) {
		this.regras = regras;
	}

	public TipoSeguro getTipoSeguro() {
		return tipoSeguro;
	}

	public void setTipoSeguro(TipoSeguro tipoSeguro) {
		this.tipoSeguro = tipoSeguro;
	}
	public double getValorApolice() {
		return valor;
	}

	public void setValorApolice(double valorApolice) {
		this.valor = valorApolice;
	}

}

package br.projetoparticularnext.com.bean.cartao;

import java.util.Calendar;

import br.projetoparticularnext.com.utils.Utils;

public class Compra {
	private String dataCompra;
	private double valor;
	private String descricao;
	private int id;
	private static int idAutoIncrementavel = 1;// auto incremento
	
	public Compra(double valor, String descricao) {
		this.id = newId();
		this.dataCompra = Utils.dataAtual();
		this.valor = valor;
		this.descricao = descricao;
	}

	private int newId() {
		return idAutoIncrementavel++;
	}

	public String getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(String dataCompra) {
		this.dataCompra = dataCompra;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
	

	
}

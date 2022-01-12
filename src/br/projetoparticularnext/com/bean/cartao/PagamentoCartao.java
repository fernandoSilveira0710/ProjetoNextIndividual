package br.projetoparticularnext.com.bean.cartao;

import java.util.Date;

public class PagamentoCartao {
	private int id;
	private double valor;
	private Date data;
	private Cartao cartao;
	
	public void salvarPagamento(int id,double valor,Date data) {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Cartao getCartao() {
		return cartao;
	}

	public void setCartao(Cartao cartao) {
		this.cartao = cartao;
	}
	
}

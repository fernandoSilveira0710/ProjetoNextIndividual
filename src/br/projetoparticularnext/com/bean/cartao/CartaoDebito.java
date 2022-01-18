package br.projetoparticularnext.com.bean.cartao;

import java.util.ArrayList;
import java.util.List;

import br.projetoparticularnext.com.utils.Utils;

public class CartaoDebito extends Cartao {
	private double limitePorTransacao;
	private List<Compra> compras;

	public CartaoDebito(String bandeira, String senha, boolean isAtivo, double limitePorTransacao) {
		super(Utils.geraBlocosNumeros(4), bandeira, senha, isAtivo);
		this.limitePorTransacao = limitePorTransacao;
		this.compras = new ArrayList<Compra>();
	}

	public double getLimitePorTransacao() {
		return limitePorTransacao;
	}

	public void setLimitePorTransacao(double limitePorTransacao) {
		this.limitePorTransacao = limitePorTransacao;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}
	
	
	
}

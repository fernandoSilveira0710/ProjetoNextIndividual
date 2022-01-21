package br.projetoparticularnext.com.bean.cartao;

import java.util.ArrayList;
import java.util.List;

import br.projetoparticularnext.com.utils.Utils;

public class CartaoDebito extends Cartao {
	private double limitePorTransacao;

	public CartaoDebito(String bandeira, String senha, boolean isAtivo, double limitePorTransacao) {
		super(Utils.geraBlocosNumeros(4), bandeira, senha, isAtivo);
		this.limitePorTransacao = limitePorTransacao;
	}

	public double getLimitePorTransacao() {
		return limitePorTransacao;
	}

	public void setLimitePorTransacao(double limitePorTransacao) {
		this.limitePorTransacao = limitePorTransacao;
	}

	
	
	
}

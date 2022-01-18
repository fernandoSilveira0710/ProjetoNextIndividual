package br.projetoparticularnext.com.bean.cartao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import br.projetoparticularnext.com.utils.Utils;

public class CartaoCredito extends Cartao {


	private double limite;
	private List<Compra> compras;
	private String dataVencimento;
	private double valorFatura;

	public CartaoCredito(String bandeira, String senha, boolean isAtivo, double limite, String dataVencimento) {
		super(Utils.geraBlocosNumeros(4), bandeira, senha, isAtivo);// cartao pede
		this.limite = limite;
		this.compras = new ArrayList<Compra>();
		this.dataVencimento = Utils.returnDataDiaDefinido(dataVencimento);
		this.valorFatura = 0.0;
	}
	

	public double getLimite() {
		return limite;
	}

	public void setLimite(double limite) {
		this.limite = limite;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void addCompra(Compra compra) {
		this.compras.add(compra);
	}

	public String getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public double getValorFatura() {
		return valorFatura;
	}

	public void setValorFatura(double valorFatura) {
		this.valorFatura = valorFatura;
	}

}

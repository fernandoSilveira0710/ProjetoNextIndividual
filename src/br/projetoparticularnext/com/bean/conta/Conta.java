package br.projetoparticularnext.com.bean.conta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.projetoparticularnext.com.bean.cliente.Cliente;
import br.projetoparticularnext.com.bean.cliente.TipoCliente;
import br.projetoparticularnext.com.bean.pix.Pix;
import br.projetoparticularnext.com.utils.Utils;

public class Conta {
	private int id;
	private String numero;
	private double saldo;
	private Cliente cliente;
	private static int contasCriadas = 1;
	private TipoConta tipoConta;
	private List<Pix> listPix;
	private Date data;

	public List<Pix> getListPix() {
		return listPix;
	}

	public void setListPix(List<Pix> listPix) {
		this.listPix = listPix;
	}

	public Conta(Cliente cliente, TipoConta tipoConta) {
		this.listPix = new ArrayList<Pix>();
		this.numero = novaConta();
		this.saldo = 0.0;
		this.cliente = cliente;
		this.id = novoId();
		this.tipoConta = tipoConta;
		this.data = Utils.dataAtual();
	}
	private int novoId() {
		return contasCriadas++;
	}

	public int getId() {
		return id;
	}

	public TipoConta getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(TipoConta tipoConta) {
		this.tipoConta = tipoConta;
	}

	private String novaConta() {
		return String.valueOf(contasCriadas++);
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public double getSaldo() {
		return saldo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}

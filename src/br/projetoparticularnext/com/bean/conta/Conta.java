package br.projetoparticularnext.com.bean.conta;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.projetoparticularnext.com.bean.cliente.Cliente;
import br.projetoparticularnext.com.bean.cliente.TipoCliente;
import br.projetoparticularnext.com.bean.pix.Pix;
import br.projetoparticularnext.com.utils.Utils;

public class Conta {
	protected int id;
	protected String numero;
	protected double saldo;
	protected Cliente cliente;
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

//	VERIFICA TIPO DE CONTA A CADA OPERAÇÃO
	public void verificaTipoConta() {
		if (this.getSaldo() <= 5000) {
			this.getCliente().setTipo(TipoCliente.COMUM);
		} else if (saldo >= 5000 && saldo < 15000) {
			this.getCliente().setTipo(TipoCliente.SUPER);
		} else
			this.getCliente().setTipo(TipoCliente.PREMIUM);
	}

	public boolean saque(double valor) {
		if (this.getSaldo() >= valor) {
			this.saldo -= valor;
			verificaTipoConta();
			return true;
		} else
			return false;
	}

	public boolean transferir(Conta contaDestino, double valTransferencia) {
		if (this.getSaldo() >= valTransferencia) {
			contaDestino.setSaldo(contaDestino.getSaldo() + valTransferencia);
			this.setSaldo(this.getSaldo() - valTransferencia);
			verificaTipoConta();
			return true;
		} else {
			return false;
		}

	}

	public boolean depositar(double valDeposito) {
		this.saldo += valDeposito;
		verificaTipoConta();
		return true;
	}

	public double consultarSaldo() {
		return this.getSaldo();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

}

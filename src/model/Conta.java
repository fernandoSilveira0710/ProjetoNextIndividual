package model;

public class Conta {
	private String numero;
	private double saldo;

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	private Cliente cliente;
	public static int contasCriadas = 1;

	public Conta(Cliente cliente) {
		this.numero = novaConta();
		this.saldo = 0.0;
		this.cliente = cliente;
	}

	private String novaConta() {
		return String.valueOf(contasCriadas++);
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

}

package model.conta;

import model.cliente.Cliente;

public class ContaCorrente extends Conta {

	private double taxaManutencao;
	
	public ContaCorrente(Cliente cliente) {
		super(cliente);

	}

	public double descontarTaxa(double saldo, double taxaManutencao) {

		return 0.0;
	}
}

package model.conta;

import model.cliente.Cliente;

public class ContaPoupanca extends Conta {
public ContaPoupanca(Cliente cliente) {
		
		super(cliente);
	}

private double taxaRendimento;
	
	public double acrescentarRendimento(double saldo, double taxaManutencao) {
		return 0.0;
	}
}

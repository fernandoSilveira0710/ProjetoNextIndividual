package br.projetoparticularnext.com.bean.cliente;

public enum TipoCliente {
	COMUM(1000),SUPER(5000),PREMIUM(10000);
	
	private double limite;
	
	TipoCliente(int limite) {
		this.limite = limite;
	}
	
	public double getLimite() {
		return this.limite;
	}
		
}

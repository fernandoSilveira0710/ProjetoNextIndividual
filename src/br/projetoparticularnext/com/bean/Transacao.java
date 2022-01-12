package br.projetoparticularnext.com.bean;

public interface Transacao {
	public abstract void transferir();
	
	public abstract void  descontarTaxas();
}

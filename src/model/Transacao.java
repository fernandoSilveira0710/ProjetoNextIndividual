package model;

public interface Transacao {
	public abstract void transferir();
	
	public abstract void  descontarTaxas();
}

package br.projetoparticularnext.com.bean.cartao;

import br.projetoparticularnext.com.bean.conta.Conta;

public class Cartao {
	protected String numero;
	protected String bandeira;
	protected String senha;
	protected boolean isAtivo;
	protected Conta conta;
	
	public boolean alterarStatus(double limite,boolean isAtivo) {
		return true;
	}
	public String trocarSenha(String senha) {
		return null;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getBandeira() {
		return bandeira;
	}
	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivo() {
		return isAtivo;
	}
	public void setAtivo(boolean isAtivo) {
		this.isAtivo = isAtivo;
	}
	public Conta getConta() {
		return conta;
	}
	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
}

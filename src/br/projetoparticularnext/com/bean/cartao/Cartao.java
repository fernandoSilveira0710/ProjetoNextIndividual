package br.projetoparticularnext.com.bean.cartao;

import java.util.ArrayList;
import java.util.List;

import br.projetoparticularnext.com.utils.Const;

public class Cartao {
	private int id;
	private String numero;
	private String bandeira;
	private String senha;
	private boolean isAtivo;
	private List<Compra> compras;

	public Cartao(String numero, String bandeira, String senha, boolean isAtivo) {
		this.id = novoId();
		this.numero = numero;
		this.bandeira = bandeira;
		this.senha = senha;
		this.isAtivo = isAtivo;
		this.compras = new ArrayList<Compra>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean alterarStatus(double limite, boolean isAtivo) {
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

	// gera contas
	private int novoId() {
		return Const.CARTOES_CRIADOS++;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

}

package br.projetoparticularnext.com.bean.pix;

import java.util.Date;

import br.projetoparticularnext.com.bean.Transacao;
import br.projetoparticularnext.com.bean.conta.Conta;

public class Pix implements Transacao {
	public int id;
	public TipoChavePix tipoChave;
	public double valor;
	public Date data;
	public String conteudoChave;
	public boolean isAtivado;
	private static int pixCriados = 1;

	public Pix() {
		id = novoId();
	}
	
	public boolean ativarChave(TipoChavePix tipoChave, String conteudoChave, boolean isAtivado) {
		this.tipoChave = tipoChave;
		this.conteudoChave = conteudoChave;
		this.isAtivado = isAtivado;
		return true;
	}

	public TipoChavePix getTipoChave() {
		return tipoChave;
	}

	public void setTipoChave(TipoChavePix tipoChave) {
		this.tipoChave = tipoChave;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getConteudoChave() {
		return conteudoChave;
	}

	public void setConteudoChave(String conteudoChave) {
		this.conteudoChave = conteudoChave;
	}

	public boolean isAtivado() {
		return isAtivado;
	}

	public void setAtivado(boolean isAtivado) {
		this.isAtivado = isAtivado;
	}

	private int novoId() {
		return pixCriados++;
	}

	@Override
	public void transferir() {
		
	}

	@Override
	public void descontarTaxas() {

	}

}

package model.pix;

import java.util.Date;

import model.Transacao;
import model.conta.Conta;

public class Pix implements Transacao {
	public int id;
	public TipoChavePix tipoChave;
	public double valor;
	public Date data;
	public String conteudoChave;
	public boolean isAtivado;
	public Conta conta;

	public boolean ativarChave(TipoChavePix tipoChave, String conteudoChave, boolean isAtivado) {
		// FALTA IMPLEMENTAR
		return false;
	}

	@Override
	public void transferir() {

	}

	@Override
	public void descontarTaxas() {

	}

}

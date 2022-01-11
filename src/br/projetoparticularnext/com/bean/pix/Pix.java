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
	//public Conta conta; achei meio confuso esse conta
	public int[] idsContas = new int[2];//  SETA IDS DA CONTAS 
	private static int pixCriados = 1;

	public Pix(int idCC, int idCP) {
		id = novoId();
		this.idsContas[0] = idCC;
		this.idsContas[1] = idCP;
	}

	public boolean ativarChave(TipoChavePix tipoChave, String conteudoChave, boolean isAtivado) {
		this.tipoChave = tipoChave;
		this.conteudoChave = conteudoChave;
		this.isAtivado = isAtivado;
		return true;
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

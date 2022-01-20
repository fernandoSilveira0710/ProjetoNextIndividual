package br.projetoparticularnext.com.bean.pix;

import br.projetoparticularnext.com.utils.Const;

public class Pix  {
	public int id;
	public TipoChavePix tipoChave;
	public String conteudoChave;
	public boolean isAtivado;

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
		return Const.PIX_CRIADOS++;
	}

}

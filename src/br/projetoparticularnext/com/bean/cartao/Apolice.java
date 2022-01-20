package br.projetoparticularnext.com.bean.cartao;

import br.projetoparticularnext.com.utils.Const;
import br.projetoparticularnext.com.utils.Utils;

public class Apolice {
	private int id;
	private String dataAssinatura;
	private String dataCarencia;
	private Seguro seguro;

	public Apolice(Seguro seguro) {
		this.id = newId();
		this.dataAssinatura = Utils.dataAtual();
		this.dataCarencia = Utils.getDateAddDays(Const.DIAS_DE_CARENCIA_APOLICE);//
		this.seguro = seguro;
	}

	private int newId() {
		return Const.APOLICES_CRIADOS++;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDataAssinatura() {
		return dataAssinatura;
	}

	public void setDataAssinatura(String dataAssinatura) {
		this.dataAssinatura = dataAssinatura;
	}

	public String getDataCarencia() {
		return dataCarencia;
	}

	public void setDataCarencia(String dataCarencia) {
		this.dataCarencia = dataCarencia;
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

}

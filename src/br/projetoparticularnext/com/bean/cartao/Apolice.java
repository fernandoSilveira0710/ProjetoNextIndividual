package br.projetoparticularnext.com.bean.cartao;

import br.projetoparticularnext.com.utils.Const;
import br.projetoparticularnext.com.utils.Utils;

public class Apolice {
	private int id;
	private String dataAssinatura;
	private String dataCarencia;
	private String dataValidade;
	private int anos;
	private Seguro seguro;

	public Apolice(Seguro seguro, int anos) {
		this.id = newId();
		this.anos = anos;
		this.setDataValidade(Utils.getDateAddYears(anos));
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

	public void setDataCarencia(int dataCarencia) {
		this.dataCarencia = Utils.getDateAddDays(dataCarencia);;
	}

	public Seguro getSeguro() {
		return seguro;
	}

	public void setSeguro(Seguro seguro) {
		this.seguro = seguro;
	}

	public String getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(String dataValidade) {
		this.dataValidade = dataValidade;
	}

	public int getAnos() {
		return anos;
	}

	public void setAnos(int anos) {
		this.anos = anos;
	}

}

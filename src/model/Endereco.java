package model;

public class Endereco {
	private String cidade;
	private String estado;
	private String bairro;
	private String numero;
	private String rua;
	private String cep;
	// comprovanteEndereco : imagem

	public Endereco(String cidade, String estado, String bairro, String numero, String rua, String cep) {
		super();
		this.cidade = cidade;
		this.estado = estado;
		this.bairro = bairro;
		this.numero = numero;
		this.rua = rua;
		this.cep = cep;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}

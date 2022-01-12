package br.projetoparticularnext.com.bo;

import br.projetoparticularnext.com.bean.Endereco;

public class EnderecoBO {
	public Endereco endereco;
	
	
	public EnderecoBO(String cidade, String estado, String bairro, String numero, String rua, String cep) {
		this.endereco = cadastraEndereco(cidade,estado,bairro,numero,rua,cep);
	}

	private Endereco cadastraEndereco(String cidade, String estado, String bairro, String numero, String rua, String cep) {
		return new Endereco(cidade, estado, bairro, numero, rua, cep);
	}
	

}

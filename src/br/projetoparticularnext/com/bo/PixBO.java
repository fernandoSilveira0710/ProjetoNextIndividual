package br.projetoparticularnext.com.bo;

import java.util.List;

import br.projetoparticularnext.com.bean.conta.Conta;
import br.projetoparticularnext.com.bean.pix.TipoChavePix;
import br.projetoparticularnext.com.utils.Banco;
import br.projetoparticularnext.com.utils.Utils;

public class PixBO {
	public PixBO() {
		
	}
		// CRIA CHAVE PIX
		public static boolean cadastraChavePix(int tipoChavePix, String conteudoChave, boolean b) {
			return Banco.cadastraChavePix(ContaBO.retornaeVerificaContaExistente("cc"),
					ContaBO.retornaeVerificaContaExistente("cp"), 
					identificaTipoChavePix(tipoChavePix),
					identificaConteudoChave(tipoChavePix, 
							conteudoChave), b);
		}
		// IDENTIFICA TIPO DE CHAVE
		private static TipoChavePix identificaTipoChavePix(int tipoChavePix) {
			switch (tipoChavePix) {
			case 0: {
				return TipoChavePix.CPF;
			}
			case 1: {
				return TipoChavePix.Email;

			}
			case 2: {
				return TipoChavePix.Telefone;
			}
			case 3: {
				return TipoChavePix.Aleatorio;
			}
			}
			return TipoChavePix.Aleatorio;
		}
		// IDENTIFICA CONTEUDO CHAVE
		private static String identificaConteudoChave(int chave, String conteudoChave) {
			if(chave == 3) {
				return Utils.gerarAleatorio();
			}
			return conteudoChave;
		}
		public static List<String> exibirChavesPix(String idCC, String idCP) {
			return Banco.consultaPix(idCC,idCP);
		}
		// BUSCA E TRANFERE VIA CHAVE PIX
	public static String[] buscaETRansferePix(String chavePix,double valor,boolean tipo) {
		String resposta[] = new String[2];
		Conta contaDestino = Banco.buscaeTransferePix(chavePix);
		if(contaDestino != null) {
			resposta = ContaBO.transfereEntreContas("PIX",valor, tipo, contaDestino, 0, false);
		}else {
			resposta[0] = "         >>Esta conta não existe!<<";
			resposta[1] = "1";
		}
		
		return resposta;
	}
	public static boolean deletarChave(int chavePix) {
		return Banco.deletarChave(chavePix);
		
	}
		
}

package dao;

import java.util.ArrayList;

import model.Endereco;
import model.cliente.Cliente;
import model.conta.Conta;
import model.conta.ContaCorrente;
import model.conta.ContaPoupanca;
import model.pix.Pix;
import model.pix.TipoChavePix;

public class BD {
	public static ArrayList<ContaPoupanca> listCP;
	public static ArrayList<ContaCorrente> listCC;
	public static ArrayList<Pix> listPix;

	public static int tipoConta = 0;

	public BD() {
		listPix = new ArrayList<Pix>();
		listCP = new ArrayList<ContaPoupanca>();
		listCC = new ArrayList<ContaCorrente>();
		criarObjetosEstaticos();
	}

//CRIA UM OBJETO ESTATICO DE CONTA E CLIENTE PARA FINS DE TESTE
	private void criarObjetosEstaticos() {
		Cliente cliente1 = new Cliente("1234", "luizfernando962@gmail.com", "43546219830", "447218402",
				"Fernando Silveira",
				new Endereco("Cesário Lange", "SP", "Centro", "1759", "Rua do Comércio", "18285-000"));
		ContaCorrente cc = new ContaCorrente(cliente1);//1
		ContaPoupanca cp = new ContaPoupanca(cliente1);//2
		Pix pix = new Pix(cc.getId(), cp.getId());
		pix.ativarChave(TipoChavePix.CPF, "43546219830", true);
		listPix.add(pix);
		listCC.add(cc);
		listCP.add(cp);

	}

	// ADICIONA CONTA CORRENTE AO BD
	public void adicionaContaCorrente(ContaCorrente cc) {
		listCC.add(cc);
	}

	// ADICIONA CONTA POUPANCA AO BD
	public void adicionaContaPoupanca(ContaPoupanca cp) {
		listCP.add(cp);
	}

	// CRIA CHAVE PIX
	public boolean cadastraChavePix(int idCC, int idCP, TipoChavePix tipoChavePix, String conteudoChave, boolean b) {
		if (identificaPixExistente(conteudoChave)) {
			System.out.println(listPix.size());
			Pix pix = new Pix(idCC, idCP);
			pix.ativarChave(tipoChavePix, conteudoChave, b);
			listPix.add(pix);
			return true;
		}
		return false;
	}

//	public boolean buscaeTransferePix(String chavePix) {
//		identificaeRetorna(chavePix);
//		return false;
//	}
//	// IDENTIFICA E RETORNA CHAVE NO BANCO PIX
//		public static Pix identificaeRetorna(String conteudoChave) {
//			for (Pix pix : listPix) {
//				if (pix.conteudoChave.equals(conteudoChave)) {
//					tipoConta = 2;
//					return pix;
//				}
//			}
//			return null;
//		}

	// IDENTIFICA CHAVE NO BANCO PIX
	public static boolean identificaPixExistente(String conteudoChave) {
		for (Pix pix : listPix) {
			if (pix.conteudoChave.equals(conteudoChave)) {
				tipoConta = 2;
				return false;
			}
		}
		return true;
	}

	// IDENTIFICA CONTA NO BD APARTIR DO NUMERO DA CONTA
	public static Conta identificaContaNum(String numDesti) {
		for (ContaPoupanca cp : listCP) {
			if (cp.getNumero().equals(numDesti)) {
				tipoConta = 2;
				return cp;
			}
		}
		for (ContaCorrente cc : listCC) {
			if (cc.getNumero().equals(numDesti)) {
				tipoConta = 1;
				return cc;
			}
		}
		return null;
	}

	public static String consultaPix(int idCC, int idCP) {
//		System.out.println(idCC+"|"+idCP);
		String chavesPix = "\n--- SUAS CHAVES ---\n\n";
		for (Pix pix : listPix) {
			if (pix.idsContas[0] == idCC || pix.idsContas[1] == idCP) {
				chavesPix += "|TIPO:" + pix.tipoChave.name() + " CHAVE:" + pix.conteudoChave + "\n";
			}
		}

		return chavesPix;
	}

	// VERIFICA EXISTENCIA DO CPF NO BANCO
	public static boolean consultaCpfBanco(String cpf) {

		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < listCC.size(); i++) {
			if (listCC.get(i).getCliente().getCpf().equals(cpf)) {
				return true;
			}

		}
		for (int i = 0; i < listCP.size(); i++) {
			if (listCP.get(i).getCliente().getCpf().equals(cpf)) {
				return true;
			}

		}
		return false;
	}

	// VERIFICA LISTA NO BANCO DE DADOS ESTATICO
	public ContaCorrente retornaContaCorrente(String cpf, String senha) {
		// cria uma instancia do banco e verifica se existe usuario cadastrado
		for (int i = 0; i < listCC.size(); i++) {
			if (listCC.get(i).getCliente().getCpf().equals(cpf)
					&& listCC.get(i).getCliente().getSenha().equals(senha)) {
				return listCC.get(i);
			}
		}
		return null;
	}

	// VERIFICA LISTA NO BANCO DE DADOS ESTATICO
	public ContaPoupanca retornaContaPoupanca(String cpf, String senha) {
		for (int i = 0; i < listCP.size(); i++) {
			if (listCP.get(i).getCliente().getCpf().equals(cpf)
					&& listCP.get(i).getCliente().getSenha().equals(senha)) {
				return listCP.get(i);
			}
		}
		return null;
	}

}

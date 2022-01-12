package br.projetoparticularnext.com.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.projetoparticularnext.com.bean.Endereco;
import br.projetoparticularnext.com.bean.cliente.Cliente;
import br.projetoparticularnext.com.bean.conta.Conta;
import br.projetoparticularnext.com.bean.conta.TipoConta;
import br.projetoparticularnext.com.bean.pix.Pix;
import br.projetoparticularnext.com.bean.pix.TipoChavePix;

public class Banco {
	public static Map<String, Conta> banco_De_Dados;
	public static ArrayList<Pix> listPix;
	static boolean ccIsActive = false;
	static boolean cpIsActive = false;
	static Conta cc, cp;

	public Banco() {
		banco_De_Dados = new HashMap<String, Conta>();
		listPix = new ArrayList<Pix>();
		criarObjetosEstaticos();
	}

//CRIA UM OBJETO ESTATICO DE CONTA E CLIENTE PARA FINS DE TESTE
	private void criarObjetosEstaticos() {
		Cliente cliente1 = new Cliente("1234", "luizfernando962@gmail.com", "43546219830", "447218402",
				"Fernando Silveira",
				new Endereco("Cesário Lange", "SP", "Centro", "1759", "Rua do Comércio", "18285-000"));
		Conta conta = new Conta(cliente1, TipoConta.ContaCorrente);// 1
		Pix pix = new Pix();
		pix.ativarChave(TipoChavePix.CPF, "43546219830", true);
		conta.getListPix().add(pix);
		cadastraConta(conta.getNumero(), conta);
		Cliente cliente2 = new Cliente("1234", "isabela@gmail.com", "43096078882", "447218402", "Isabela Silveira",
				new Endereco("Cesário Lange", "SP", "Centro", "1759", "Rua do Comércio", "18285-000"));
		Conta conta2 = new Conta(cliente2, TipoConta.ContaPoupanca);// 1
		Pix pix2 = new Pix();
		pix2.ativarChave(TipoChavePix.CPF, "43096078882", true);
		conta2.getListPix().add(pix2);
		cadastraConta(conta2.getNumero(), conta2);
	}

	// BUSCA CONTA POR NUMERO DE CONTA
	public static Conta buscaContaPorNumero(String numeroDestino) {
		Conta conta = banco_De_Dados.get(numeroDestino);
		if (conta == null) {
//			System.out.println("Conta não encontrada");
			return null;
		}
		return conta;
	}

	// CADASTRA CLIENTE
	public static void cadastraConta(String numeroCOnta, Conta conta) {
		banco_De_Dados.put(numeroCOnta, conta);
//		System.out.println("BANCO DE DADOS CADASTRANDO..." + banco_De_Dados.size());
	}

	public static boolean consultaCpfBanco(String cpfConsole) {
		for (Map.Entry<String, Conta> tipoConta : banco_De_Dados.entrySet()) {
			if (tipoConta.getValue().getCliente().getCpf().equals(cpfConsole)) {
				return true;
			}
		}
		return false;
	}

	public static List<Conta> retornaConta(String cpf, String senha) {
		List<Conta> listContas = new ArrayList<Conta>();
		for (Map.Entry<String, Conta> tipoConta : banco_De_Dados.entrySet()) {
			if (tipoConta.getValue().getCliente().getCpf().equals(cpf)
					&& tipoConta.getValue().getCliente().getSenha().equals(senha)) {
//				System.out.println("ENCONTRADO:" + tipoConta.getValue().getTipoConta());
				listContas.add(tipoConta.getValue());
			}
		}
		return listContas;
	}

	public static String consultaPix(String idCC, String idCP) {
		String chavesPix = "\n--- SUAS CHAVES ---\n\n";
		cc = buscaContaPorNumero(String.valueOf(idCC));
		cp = buscaContaPorNumero(String.valueOf(idCP));
		if (cc != null) {
			if (cc.getNumero().equals(idCC)) {
				ccIsActive = true;
				for (Pix pix : cc.getListPix()) {
					chavesPix += pix.id + " - TIPO:" + pix.tipoChave.name() + " CHAVE:" + pix.conteudoChave + "\n";
				}
			}
		} else if (cp != null) {
			if (cp.getNumero().equals(idCP)) {
				cpIsActive = true;
				for (Pix pix : cp.getListPix()) {
					chavesPix += pix.id + " - " + "TIPO:" + pix.tipoChave.name() + " CHAVE:" + pix.conteudoChave + "\n";
				}

			}

		}
		return chavesPix;
	}

	// CRIA CHAVE PIX
	public static boolean cadastraChavePix(String idCC, String idCP, TipoChavePix tipoChavePix, String conteudoChave,
			boolean b) {
		consultaPix(idCC, idCP);
		if (!identificaPixExistente(conteudoChave)) {
			System.out.println("EXISTE PIX ");
			Pix pix = new Pix();
			pix.ativarChave(tipoChavePix, conteudoChave, b);
			if (ccIsActive && cpIsActive) {
				System.out.println("EXISTE DUAS CONTAS");
				banco_De_Dados.get(idCC).getListPix().add(pix);
				return true;
			} else if (ccIsActive) {
				System.out.println("EXISTE SÓ CC");
				banco_De_Dados.get(idCC).getListPix().add(pix);
				return true;
			}

			else {
				System.out.println("EXISTE SÓ CP");
				banco_De_Dados.get(idCP).getListPix().add(pix);
				return true;
			}
		}
		return false;
	}

	private static boolean identificaPixExistente(String conteudoChave) {

		if (cc != null) {
			for (Pix pix : cc.getListPix()) {
				if (pix.getConteudoChave().equals(conteudoChave)) {
					return true;
				}
			}
		} else {
			for (Pix pix : cp.getListPix()) {
				if (pix.getConteudoChave().equals(conteudoChave)) {
					return true;
				}
			}
		}
		return false;

	}

	// zera as instancias e limpa os atributos staticos
	public static void zerarTodasAsInstanciasBanco() {
		cc = null;
		cp = null;
		ccIsActive = false;
		cpIsActive = false;
	}

	public static Conta buscaeTransferePix(String chavePix) {
		for (Map.Entry<String, Conta> tipoConta : banco_De_Dados.entrySet()) {
			for (Pix pix : tipoConta.getValue().getListPix()) {
				if (pix.conteudoChave.equals(chavePix)) {
					return tipoConta.getValue();
				}
			}
		}
		return null;
	}

	public static boolean deletarChave(int chavePix) {
		if (cc != null) {
			for(Pix pix : cc.getListPix()) {
				if(pix.id == chavePix) {
					cc.getListPix().remove(pix);
					return true;
				}
			}
			
		} else if (cp != null) {
			for(Pix pix : cp.getListPix()) {
				if(pix.id == chavePix) {
					cp.getListPix().remove(pix);
					return true;
				}
			}
		}
		return false;
	}
}

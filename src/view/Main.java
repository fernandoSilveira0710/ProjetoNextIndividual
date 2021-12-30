package view;

import model.Cliente;
import model.Conta;
import model.Endereco;
import util.Utils;
import util.ValidaCPF;
import dao.BD;

public class Main {
	static Utils utils = new Utils();
	static ValidaCPF validaCPF = new ValidaCPF();
	static BD bd = new BD();
	static String cpfConsole = "";
	static boolean logado = false; // FLAG

	public static void main(String[] args) {
		logado = false;
		menuInicio();
		if (!logado)
			menuCadastro(); // caso logado ele puxa o cadastro
	}

// EXIBE MENU LOGIN
	private static void menuInicio() {
		System.out.println(" _________________________________ ");
		System.out.println("|-----BEM VINDO AO BANCO NEXT-----|");
		System.out.println("|------------  LOGIN  ------------|");
		System.out.println("|                                 |");

		while (true) {
			cpfConsole = utils.lerConsole("|DIGITE SEU CPF:");

			if (validarCpf(cpfConsole))// valida cpf
				break;
		}
		System.out.println("|_________________________________|");
		chamarBanco();
	}

// EXIBE MENU CADASTRO
	private static void menuCadastro() {
		System.out.println("\n|----------  CADASTRO  ---------|");
		cadastrar();
	}

// CADASTRA O USUARIO
	private static void cadastrar() {
		System.out.println(" _________________________________ ");
		System.out.println("|----  CADASTRO DADOS BASICOS  ---|");
		System.out.println("|SEU CPF: " + cpfConsole);
		String rg = utils.lerConsole("|DIGITE SEU RG: ");
		String nome = utils.lerConsole("|DIGITE SEU NOME COMPLETO: ");
		System.out.println(" __________________________________ ");
		System.out.println("|-------  CADASTRO ENDEREÇO  ------|");
		String rua = utils.lerConsole("|DIGITE SUA RUA: ");
		String bairro = utils.lerConsole("|DIGITE SEU BAIRRO: ");
		String numero = utils.lerConsole("|DIGITE SEU NUMERO: ");
		String cidade = utils.lerConsole("|DIGITE SUA CIDADE: ");
		String estado = utils.lerConsole("|DIGITE SEU ESTADO: ");
		String cep = utils.lerConsole("|DIGITE SEU CEP: ");
		System.out.println("|_________________________________|");
		// FALTA VALIDAR CAMPOS ANTES DE ENVIAR
		Endereco endereco = new Endereco(cidade,estado,bairro,numero,rua,cep);
		// CONTINUAR DAQUI
		Cliente cliente = new Cliente(cpfConsole, rg,nome,endereco);
		Conta conta = new Conta(cliente);
		cliente.cadastrarDados(bd, conta);
		System.out.println("\n\n>>CLIENTE CADASTRADO COM SUCESSO!<<\n\n");
		menuPrincipal(conta);
		buscaOperacaoPrincipal(conta);

	}

// EXIBE MENU PRINCIPAL
	private static void menuPrincipal(Conta conta) {
		System.out.println("\n\n>>OLÁ " + conta.getCliente().getNome().toUpperCase());
		System.out.println(">>NUMERO CONTA: " + conta.getNumero());
		System.out.println(">>SALDO DISPONIVEL:" + utils.convertToReais(conta.consultarSaldo()));
		System.out.println(">>TIPO DE CONTA:" + conta.getCliente().getTipo());
		System.out.println(" _________________________________ ");
		System.out.println("|--------  MENU PRINCIPAL  -------|");
		System.out.println("|1 - TRANSFERIR                   |");
		System.out.println("|2 - DEPOSITAR                    |");
		System.out.println("|3 - CONSULTAR SALDO              |");
		System.out.println("|4 - SACAR                        |");
		System.out.println("|0 - SAIR                         |");
		System.out.println("|_________________________________|");
	}

//SWITCH COM OPERAÇÕES PRINCIPAIS(SALDO,SAQUE,TRANSFERENCIA,DEPOSITO E SAIR)
	private static void buscaOperacaoPrincipal(Conta conta) {
		boolean loop = true;
		while (loop) {
			int operacao = Integer.parseInt(utils.lerConsole("|DIGITE A OPERAÇÃO: "));

			switch (operacao) {
			case 1: { // transferencia
				while (true) {
					String numDesti = utils.lerConsole("DIGITE O NUMERO DA CONTA DESTINO: ");
					Conta contaDestino = bd.identificaContaNum(numDesti);// conta recebida do bd
					if (contaDestino != null) {
						double valor = Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA TRANSFERIR: "));
						if (conta.transferir(contaDestino, valor)) {
							System.out.println("\n>>TRANSFERENCIA DE " + utils.convertToReais(valor) + "\n PARA "
									+ contaDestino.getCliente().getNome().toUpperCase()
									+ " REALIZADO COM SUCESSO<< \n\n");
							menuPrincipal(conta);
							break;
						} else {
							System.err.println("ERRO NA TRANSFERENCIA!SALDO INSUFICIENTE \n");
						}
					} else {
						System.err.println("ESTA CONTA NÃO EXISTE! \n");
					}
				}
				break;
			}
			case 2: { // deposito
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (conta
							.depositar(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA DEPOSITAR: ")))) {
						System.out.println("\n\n>>DEPOSITO REALIZADO COM SUCESSO!<< \n\n");
						menuPrincipal(conta);
						break;
					} else {
						System.err.println("\n>>ERRO NO DEPOSITO!<< \n");
					}
				}
				break;
			}
			case 3: { // saldo
				System.out.println("\n>>SALDO DISPONIVEL: " + utils.convertToReais(conta.consultarSaldo()) + "<<\n");
				break;
			}
			case 4: { // saque
				// solicita valor, envia pro metodo saque que retorna a mensagem
				while (true) {
					if (conta.saque(Double.parseDouble(utils.lerConsole("DIGITE O VALOR QUE DESEJA SACAR: ")))) {
						System.out.println("\n\n>>SAQUE REALIZADO COM SUCESSO!<<");
						menuPrincipal(conta);
						break;
					} else {
						System.err.println("\n>>SALDO INSUFICIENTE<<\n");
					}
				}
				break;
			}
			case 0: { // sair
				System.out.println("\n\n|        LOGOFF CONCLUIDO!        |\n\n");
				loop = false;
				break;
			}
			default:
				System.out.println("|    DIGITE NUMEROS ENTRE 0 E 3   |");
			}
			if (!loop)
				menuInicio();
		}
	}

// ENVIA DADOS PARA UMA CLASSE VALIDACAO E RETORNA BOOLEAN
	private static boolean validarCpf(String cpf) {
		if (validaCPF.verificaCpf(cpf)) {
			return true;
		} else {
			System.out.println("|CPF INVALIDO! DIGITE CORRETAMENTE|");
			return false;
		}
	}

// CHAMA O BANCO E ENVIA CPF VALIDADO RETORNANDO A CONTA 
	private static void chamarBanco() {
		Conta conta = bd.consultaCpf(validaCPF.removeCaracteresEspeciais(cpfConsole));
		if (conta != null) {
			logado = true;
			menuPrincipal(conta);
			buscaOperacaoPrincipal(conta);
		} else {
			cadastrar();
		}
	}

}

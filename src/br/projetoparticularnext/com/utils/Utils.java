package br.projetoparticularnext.com.utils;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;

public class Utils {
	Scanner ler = new Scanner(System.in);

	// METODO QUE L� O CONSOLE E RETORNA UMA STRING(TEXTO)
	public String lerConsole(String texto) {
		System.out.print(texto);
		String textoDigitado = ler.nextLine();
		return textoDigitado;
	}

//QUANTIA DE LINHAS NA STRING
	public static int getLineCount(String text) {

		return text.split("[\n|\r]").length;
	}

	public void fechaConsole() {
		ler.close();
	}

	// FORMATA PARA REAIS
	public static String convertToReais(double valor) {
		Locale ptBr = new Locale("pt", "BR");
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}

	// GERA ID ALEATORIO
	public static String gerarAleatorio() {
		return UUID.randomUUID().toString();
	}

	// RETORNA DATA ATUAL
	public static String dataAtual() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return sdf.format(Calendar.getInstance().getTime());
	}

	// ADD UM MES A DATA 15/05/05
	public static String getDateAdd1Month() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		return sdf.format(calendar.getTime());
	}

	// ADD ANOS COM BASE NO VALOR RECEBIDO E A DATA DE HOJE
	public static String getDateAddYears(int dataValidade) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, dataValidade);
		return sdf.format(calendar.getTime());
	}

	// add dias com base no int recebido
	public static String getDateAddDays(int dias) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_MONTH, dias);
		return sdf.format(calendar.getTime());
	}

	// ADD UM MES A DATA
	public static String returnDataDiaDefinido(String dataSemFormatar) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dataSemFormatar));
		return sdf.format(calendar.getTime());
	}

	// M�todo sleep em 200 milisegundos
	public static void sleep() {
		try {
			Thread.sleep(200);
		} catch (Exception e) {

		}
	}

	// Barrinha de carregamento em milisegundos
	// padr�o � 10
	public void loading(String text) {
		sleep();
		System.out.print("      " + text);
		for (int i = 0; i < 8; i++) {
			System.out.print(".");
			sleep();
		}
		System.out.println();
		sleep();
	}

	// GERADOR DE BLOCOS DE NUMEROS ALEAT�RIOS
	public static String geraBlocosNumeros(int qtd) {
		Random r = new Random();
		String randomNumber = "";
		for (int x = 0; x < 4; x++) {
			randomNumber += String.format(" " + "%0" + qtd + "d", r.nextInt(1001));
		}
		return randomNumber;
	}

	public static Date readConsoleData(String data) {
		SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dataFormat.parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

}

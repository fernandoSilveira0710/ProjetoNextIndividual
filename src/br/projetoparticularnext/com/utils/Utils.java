package br.projetoparticularnext.com.utils;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.UUID;

public class Utils {
	Scanner ler = new Scanner(System.in);

	// METODO QUE LÊ O CONSOLE E RETORNA UMA STRING(TEXTO)
	public String lerConsole(String texto) {
		System.out.print(texto);
		String textoDigitado = ler.nextLine();
		return textoDigitado;
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
	public static Date dataAtual() {
		Date dataAtual = new Date();
		return dataAtual;
	}

	// ADD UM MES A DATA
	public static Date getDateAdd1Month() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		Date date = calendar.getTime();
		return date;
	}

	// Método sleep em 200 milisegundos
	public static void sleep() {
		try {
			Thread.sleep(200);
		} catch (Exception e) {

		}
	}

	// Barrinha de carregamento em milisegundos
	// padrão é 10
	public static void loading(String text) {
		sleep();
		System.out.print(text);
		for (int i = 0; i < 10; i++) {
			System.out.print(".");
			sleep();
		}
		System.out.println();
		sleep();
	}

}

package util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

public class Utils {
	// METODO QUE LÊ O CONSOLE E RETORNA UMA STRING(TEXTO)
	public String lerConsole(String texto) {
		Scanner ler = new Scanner(System.in);
		System.out.print(texto);
		String textoDigitado = ler.next();

		return textoDigitado;

	}

	// FORMATA PARA REAIS
	public String convertToReais(double valor) {
		Locale ptBr = new Locale("pt", "BR");
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}

}

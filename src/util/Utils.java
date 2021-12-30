package util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;

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
	public String convertToReais(double valor) {
		Locale ptBr = new Locale("pt", "BR");
		return NumberFormat.getCurrencyInstance(ptBr).format(valor);
	}

}

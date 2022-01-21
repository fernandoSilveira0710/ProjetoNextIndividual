package br.projetoparticularnext.com.utils;

public class Const {
	// TAXAS E RENDIMENTO
	public static final double TAXA_DEBITO = 0.03;
	public static final double TAXA_CREDITO = 0.45;
	
	// REGRAS DE APOLICE
	public static final String[] REGRA_MORTE = 
		{    "(i) Indenização por despesas médico-hospitalares, ou por perda parcial ou"
			+ "total do funcionamento dos membros ou órgãos;",
			 "(ii) Reembolso de custos em diagnóstico de doenças graves, como infarto,"
			+ "acidente vascular cerebral e câncer;",
			 "(iii) Assistência funeral, para você e a sua família.",
			 "(iv) O valor do seguro individual é de R$36,00 ao ano."};
	public static final String[] REGRA_INVALIDEZ = 
		   {"(i) Invalidez parcial: ocorre quando há uma perda parcial das funções. Por ",
			"exemplo, uma pessoa que sofre um acidente e perda a visão em um só dos olhos.",
			"(ii) Invalidez total: ocorre quando há uma perda total das funções.Intuitivamente, ",
			"um bom exemplo seria o caso onde a pessoa sofre um acidente e perde a visão em ambos os olhos.",
			"(iii) O valor do seguro individual é de R$26,00 ao ano."};
	public static final String[] REGRA_DESEMPREGO = 
		     {"(i) Necessário trabalhar com registro CLT, com o tempo mínimo de estabilidade de 12 meses.",
			  "(ii)Válido apenas para desligamento involuntários e sem justa causa.",
			  "(iii)Não é valido em caso de demissão em massa (10% ou mais de demissões simultâ",
			  "neas) ou falência/encerramento das atividades.",
			  "(iv) O valor do seguro individual é de R$16,00 ao ano"};
	
	// VALORES APOLICE
	public static final double VALOR_SEGURO_ANUAL_MORTE = 25000.0;
	public static final double VALOR_SEGURO_ANUAL_INVALIDEZ = 20000.0;
	public static final double VALOR_SEGURO_ANUAL_DESEMPREGO = 10000.0;

	//DATA DE CARENCIA DA APOLICE DO SEGURO
	public static final int DIAS_DE_CARENCIA_APOLICE = 15;
	
	//TAXA SEGUROS
	public static final double TAXA_SEGURO_MORTE = 36.0;
	public static final double TAXA_SEGURO_INVALIDEZ = 26.0;
	public static final double TAXA_SEGURO_DESEMPREGO = 16.0;
	
	//IDS CLASSES
	public static int CONTAS_CRIADAS = 1;
	public static int NUMERO_CONTA = 1;
	public static int CARTOES_CRIADOS = 1;
	public static int COMPRAS_REALIZADAS = 1;
	public static int PIX_CRIADOS = 1;
	public static int APOLICES_CRIADOS = 1;
	public static int SEGUROS_CRIADOS = 1;

}

package br.com.algotrade.util;

/**
 * ALTA = 1 | BAIXA = 2 |
 * LATERAL = 3 |
 * LATERAL_DE_ALTA = 4 |
 * LATERAL_DE_BAIXA = 5
 */
public class Movimento {
	public final static int ALTA = 1;
	public final static int BAIXA = 2;
	public final static int LATERAL = 3;
	public final static int LATERAL_DE_ALTA = 4;
	public final static int LATERAL_DE_BAIXA = 5;
	
	public static int getAlta() {
		return ALTA;
	}
	public static int getBaixa() {
		return BAIXA;
	}
	public static int getLateral() {
		return LATERAL;
	}
	public static int getLateralDeAlta() {
		return LATERAL_DE_ALTA;
	}
	public static int getLateralDeBaixa() {
		return LATERAL_DE_BAIXA;
	}
	
	
}

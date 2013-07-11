package br.com.algotrade.testes;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.entidades.Grafico;
import br.com.algotrade.negocio.AtivoBuilderForex;
import br.com.algotrade.util.AtivoMock;
import br.com.algotrade.util.Movimento;

public class Testes {
	static int ordem = 0;
	int movimento = 0;
	int movimentoAnterior = 0;
	

	public static void main(String[] args) throws Exception {
		new Testes().testa5();
		// new Testes().testa4();
		// new Testes().testa3();
		// new Testes().testa2();
		// new Testes().testa();
	}
	
	private void testa5() {
		Matcher matcher = Pattern.compile("\\-?\\d?\\d\\,\\d\\d").matcher("=\"P.ACUCAR-CBD PN N1\" >Acucar</a></nobr></td><td id=\"cr1cl_last18747\">96,06</td><td>94,45</td><td>97,60</td><td class=\"\">94,25</td><td class=\"bold greenFont\">1,61</td><td class=\"bold greenFont\">1,70%</td><td id=\"cr1cl_date18747\">08/07</td></tr><tr id=\"pair_18593\"><td class=\"center\"><span class=\"newSiteIconsSprite  greenArrowIcon\">&nbsp;</span></td><td class=\"bold left\" nowrap=\"nowrap\"><nobr><a href=\"/equities/all-amer-lat-on-nm\" ");
		
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
	}
	
	@SuppressWarnings("unused")
	private void testa4() {
		double valores[] = {1,2,2,4};
		double valorAnterior = 0;
		double valorCorrente = 0;
		double valorInicio = 0;
		double valorFim = 0;
		double valorMaisAlto = 0;
		double valorMaisBaixo = 0;

		for(int i = 0; i < valores.length; i++) {
			
			if(ordem == 0) {
				valorInicio = 0;
				valorMaisAlto = 0;
				valorMaisBaixo = 0;
				valorFim = 0;
			}
			
			movimentoAnterior = movimento;
			
			valorAnterior = valorCorrente;
			valorCorrente = valores[i];
			
			//Movimentos (do candle)
			if(valorCorrente > valorAnterior) {
				movimento = Movimento.ALTA;
			} else if (valorCorrente < valorAnterior){
				movimento = Movimento.BAIXA;
			} else {
				movimento = Movimento.LATERAL;
			}
	
			if (ordem == 0) {
				valorInicio = valorCorrente;
				valorMaisBaixo = valorCorrente;
				valorMaisAlto = valorCorrente;
				ordem++;
			} else if(ordem <= 2) {
				if((movimento == Movimento.BAIXA)
						|| (movimento == Movimento.LATERAL && movimentoAnterior == Movimento.BAIXA)) {
					valorMaisBaixo = valorCorrente;
				} else {
					valorMaisAlto = valorCorrente;
				}
				ordem++;
			} 
			
			if(ordem == 3) {
				ordem = 0;
				valorFim = valorCorrente;
			}
			
			System.out.println("valorInicio: " + valorInicio);
			System.out.println("valorMaisAlto: " + valorMaisAlto);
			System.out.println("valorMaisBaixo: " + valorMaisBaixo);
			System.out.println("valorFim: " + valorFim);
			System.out.println("ordem: " + ordem);
			System.out.println("\n");

		}
	}

	@SuppressWarnings("unused")
	private void testa3() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext(
	            "applicationContext-teste.xml");
		
		AtivoBuilderForex task = context.getBean(AtivoBuilderForex.class);
		
		System.out.println(task);
	}
	
	@SuppressWarnings("unused")
	private void testa2() throws Exception {
		System.out.println(Double.MAX_VALUE == Double.MAX_VALUE);

		Ativo ativo = new AtivoMock().getUmAtivoSimples();
		Grafico grafico = new Grafico();
		grafico.setArmazenaValorSegundoFundo(new BigDecimal(10));

		ativo.setGrafico(grafico);

		System.out.println(ativo.getGrafico().getArmazenaValorSegundoFundo());

		Ativo ativo2 = ativo.clone();
		System.out.println(ativo2.getGrafico().getArmazenaValorSegundoFundo());

		BigDecimal bd = new BigDecimal(Double.MAX_VALUE);

		System.out.println(bd.doubleValue() == Double.MAX_VALUE);

		System.out.println(new BigDecimal(54.1254544654654654).setScale(2,
				RoundingMode.HALF_UP));
	}
	
	@SuppressWarnings("unused")
	private void testa() {
		int numero = 0;

		for (int i = 0; i < 1000; i++) {
			numero = i;
			System.out.println("numero = " + i);

			final int numeroCopiado = numero;

			Runnable r = new Runnable() {

				public void run() {
					int numero2 = 0;

					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// synchronized (this) {
					System.out.println(Thread.currentThread().getName()
							+ " # numeroCopiado = " + numeroCopiado
							+ " # numero2 = " + ++numero2);
					// }
				}
			};
			Thread t = new Thread(r);
			t.start();
		}
	}
}
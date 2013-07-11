package br.com.algotrade.testes.algoritmo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import br.com.algotrade.util.Movimento;

//@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-teste-calculos.xml" })
@TestExecutionListeners
@TransactionConfiguration(transactionManager = "meuTransactionManager", defaultRollback = false)
public class _1_Normal {
	@Before 
	public void antes() {
		System.out.println("***CLASSE: " + this.getClass().getSimpleName());
	}
	@Test
	public void testes() {
		valorDaVariacao = 3;
		BigDecimal[] valores = valores();
		for(int i = 0; i < valores.length; i++) {
			if(i == 0) inicializa();

			valorCorrente = valores[i];
			hit = i;
			
			//Movimentos (do candle)
			if(valorCorrente.doubleValue() > valorAnterior.doubleValue()) {
				movimento = Movimento.ALTA;
			} else if (valorCorrente.doubleValue() < valorAnterior.doubleValue()){
				movimento = Movimento.BAIXA;
			} else {
				movimento = Movimento.LATERAL;
			}
			
			//Topo
			if(valorCorrente.doubleValue() > valorDoTopo.doubleValue() && valorCorrente.doubleValue() > valorDoFundo.doubleValue()) {
				//entra aqui ao formar o segundo fundo
				if(formouSegundoFundo) { 
					valorDoFundoAnterior = valorDoFundo;
					valorDoFundo = armazenaValorSegundoFundo;
				}
				diferencaDeFundoATopo = valorCorrente.subtract(valorDoFundo);
				valorDoTopo = valorCorrente;

				ultimoFoiFundo= false;
				isNovoTopo = true;
				ultimoFoiTopo = true;
			}
			//Fundo
			if (valorCorrente.doubleValue() < valorDoFundo.doubleValue()) {
				valorDoFundoAnterior = valorDoFundo;
				valorDoFundo = valorCorrente;

				//quando base vazia
				if(!isValorAnteriorConfigurado) {
					valorAnterior = new BigDecimal(0);
					valorDoFundoAnterior = new BigDecimal(0);
					valorDoTopo = new BigDecimal(0);
				}
				ultimoFoiTopo = false;
				ultimoFoiFundo = true;
				isNovoFundo = true;
				
				resetaPadraoEFundo();
			}
			
			//confirma segundo topo
			if(formouSegundoFundo && isNovoTopo) {
				formadoSegundoTopo = true;
			}
			
			//Ordem Inversa (quanto mais cai, mais aumenta o valor)
			if((isNovoTopo || isNovoFundo)) {
				correcaoCorrente = new BigDecimal(0);
			} else {
				correcaoCorrente = correcaoCorrente.add(valorAnterior.subtract(valorCorrente));
			}
			
			//analisa primeira e segunda formacao de fundo candidato
			if(diferencaDeFundoATopo.doubleValue() >= valorDaVariacao
					||(diferencaDeFundoATopo.doubleValue() > 0
					&& formouSegundoFundo)){
				
				formadoTopoRelevante = true;

				rangeInicioDaCorrecao = diferencaDeFundoATopo.multiply(porcentagemInicioDaCorrecao);
				rangeLimiteDaCorrecao = diferencaDeFundoATopo.multiply(porcentagemLimiteDaCorrecao);
				
				//entra no range quando chega no range de inicio, e perde apenas se o range limite for ultrapassado
				if(correcaoCorrente.doubleValue() >= rangeInicioDaCorrecao.doubleValue()) {
					entrouNoRangeDeInicio = true;
				}
				
				if(correcaoCorrente.doubleValue() <= rangeLimiteDaCorrecao.doubleValue()) {
					estaNoRangeDeLimite = true;
				} else {
					estaNoRangeDeLimite = false;
					
				}
				
				//Entra na formacao do primeiro e segundo fundo
				if((entrouNoRangeDeInicio && estaNoRangeDeLimite) && ultimoFoiTopo) {
					formouSegundoFundo = true;
					armazenaValorSegundoFundo = valorCorrente;
					entrouNoRangeDeInicio = false;
					
					if(formadoSegundoTopo && movimento == Movimento.ALTA) {
						formouPivoDeCompra = true;
					}
					
				}
				
				//se ja entrou no range, pode voltar pra cima, mas nao passar pra baixo
				if(!estaNoRangeDeLimite) {
					resetaPadraoEFundo();
				}
			}
			imprimeTudo();
			if(formouPivoDeCompra) {
				resetaPadraoEFundo();
			}
			//salva
			valorAnterior = valorCorrente;
			isValorAnteriorConfigurado = true;
			//isValorDeFundoAbaixo = false;
			isNovoTopo = false;
			isNovoFundo = false;
		}
		
	}
	private void inicializa() {
		valorDoFundo = new BigDecimal(Double.MAX_VALUE);
		valorDoTopo = new BigDecimal(Double.MAX_VALUE);
		valorDoFundoAnterior = new BigDecimal(Double.MAX_VALUE);
		valorAnterior = new BigDecimal(Double.MAX_VALUE);
		
		porcentagemInicioDaCorrecao = new BigDecimal(0.50);
		porcentagemLimiteDaCorrecao = new BigDecimal(0.80);
	}
	/**
	 *Reseta por tres motivos -> 1) reduntante, pois passa sempre apos fundo. 2) ao sair de algum padrao. 
	 * 3) ao confirmar uma etapa de um padrao.
	 * @param isValorDeFundoAbaixo
	 */
	private void resetaPadraoEFundo() {
		diferencaDeFundoATopo = new BigDecimal(0);
		
		if (!isNovoFundo) {
			valorDoFundoAnterior = new BigDecimal(0);
		}

		valorDoTopo = new BigDecimal(0);
		ultimoFoiTopo = false;
		
		valorDoFundo = valorCorrente;
		ultimoFoiFundo = true;
		
		correcaoCorrente = new BigDecimal(0);
		formouPivoDeCompra = false;
		entrouNoRangeDeInicio = false;
		estaNoRangeDeLimite = false;
		formadoSegundoTopo = false;
		formadoTopoRelevante = false;
		formouSegundoFundo = false;
	}
	
	private void imprimeTudo() {
		System.out.println("-------------- "+ hit + " -------------------");
		System.out.println("valorCorrente: " + valorCorrente);
		System.out.println("---------------------------------");
		System.out.println("valorDoTopo: " + valorDoTopo);
		System.out.println("valorDoFundo: " + valorDoFundo);
		System.out.println("diferencaDeFundoATopo: " + diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
		System.out.println("");
		System.out.println("correcaoCorrente: " + correcaoCorrente);
		System.out.println("rangeInicioDaCorrecao: " + rangeInicioDaCorrecao);
		System.out.println("rangeLimiteDaCorrecao: " + rangeLimiteDaCorrecao);
		System.out.println("");
		System.out.println("formadoTopoRelevante: " + formadoTopoRelevante);
		System.out.println("formouSegundoFundo: " + formouSegundoFundo);
		System.out.println("formadoSegundoTopo: " + formadoSegundoTopo);
		System.out.println("formouPivoDeCompra: " + formouPivoDeCompra);
		System.out.println("");
		System.out.println("armazenaValorSegundoFundo: " + armazenaValorSegundoFundo);
		System.out.println("formadoSegundoTopo: " + formadoSegundoTopo);
		switch (hit) {
		case 0:
			assertEquals("valorCorrente", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertFalse(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			break;
		case 1:
			assertEquals("valorCorrente", new BigDecimal(3).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(3).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertFalse(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			
			break;
		case 2:
			assertEquals("valorCorrente", new BigDecimal(5).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(5).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(3).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertFalse(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			
			break;
		case 3:
			assertEquals("valorCorrente", new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertFalse(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			break;
		case 4:
			assertEquals("valorCorrente", new BigDecimal(6).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(6).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(3.2).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP).setScale(2, RoundingMode.HALF_UP));
			assertTrue(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			
			break;
		case 5:
			assertEquals("valorCorrente", new BigDecimal(5).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(6).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(1).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(3.2).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertTrue(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			break;
		case 6:
			assertEquals("valorCorrente", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(6).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(3.2).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertTrue(formadoTopoRelevante);
			assertTrue(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			break;
		case 7:
			assertEquals("valorCorrente", new BigDecimal(7).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(7).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(3).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2.4).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertTrue(formadoTopoRelevante);
			assertTrue(formouSegundoFundo);
			assertTrue(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertTrue(formadoSegundoTopo);
			break;
		case 8:
			assertEquals("valorCorrente", new BigDecimal(5).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(7).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(3).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2.4).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(5).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertTrue(formadoTopoRelevante);
			assertTrue(formouSegundoFundo);
			assertTrue(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertTrue(formadoSegundoTopo);
			break;
		case 9:
			assertEquals("valorCorrente", new BigDecimal(5.5).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(7).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(4).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(3).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2.4).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(5.5).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertTrue(formadoTopoRelevante);
			assertTrue(formouSegundoFundo);
			assertTrue(formadoSegundoTopo);
			assertTrue(formouPivoDeCompra);
			assertTrue(formadoSegundoTopo);
			break;
		case 10:
			assertEquals("valorCorrente", new BigDecimal(6).setScale(2, RoundingMode.HALF_UP), valorCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoTopo", new BigDecimal(6).setScale(2, RoundingMode.HALF_UP), valorDoTopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("valorDoFundo", new BigDecimal(5.5).setScale(2, RoundingMode.HALF_UP), valorDoFundo.setScale(2, RoundingMode.HALF_UP));
			assertEquals("diferencaDeFundoATopo", new BigDecimal(0.5).setScale(2, RoundingMode.HALF_UP), diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(0).setScale(2, RoundingMode.HALF_UP), correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(1.5).setScale(2, RoundingMode.HALF_UP), rangeInicioDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(2.4).setScale(2, RoundingMode.HALF_UP), rangeLimiteDaCorrecao.setScale(2, RoundingMode.HALF_UP));
			assertEquals(new BigDecimal(5.5).setScale(2, RoundingMode.HALF_UP), armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
			assertFalse(formadoTopoRelevante);
			assertFalse(formouSegundoFundo);
			assertFalse(formadoSegundoTopo);
			assertFalse(formouPivoDeCompra);
			assertFalse(formadoSegundoTopo);
			break;

		default:
		assertTrue(false);
			break;
		}
		
	}

	private BigDecimal[] valores() {
		BigDecimal[] valores = {
				new BigDecimal(4),
				new BigDecimal(3),
				new BigDecimal(5),
				new BigDecimal(2),
				new BigDecimal(6),
				new BigDecimal(5),
				new BigDecimal(4),
				new BigDecimal(7),
				new BigDecimal(5),
				new BigDecimal(5.5),
				new BigDecimal(6)
				
		};
		return valores;
	}
	
	private BigDecimal valorCorrente = new BigDecimal(0);
	private BigDecimal valorAnterior = new BigDecimal(0);
	private BigDecimal valorDoFundo = new BigDecimal(0);
	private BigDecimal valorDoTopo = new BigDecimal(0);
	@SuppressWarnings("unused")
	private boolean ultimoFoiFundo;
	private boolean ultimoFoiTopo;
	private BigDecimal diferencaDeFundoATopo = new BigDecimal(0);
	@SuppressWarnings("unused")
	private BigDecimal valorDoFundoAnterior = new BigDecimal(0);
	private BigDecimal correcaoCorrente = new BigDecimal(0);
	private boolean formouSegundoFundo;
	private BigDecimal rangeInicioDaCorrecao = new BigDecimal(0);
	private BigDecimal rangeLimiteDaCorrecao = new BigDecimal(0);
	private BigDecimal porcentagemInicioDaCorrecao = new BigDecimal(0);
	private BigDecimal porcentagemLimiteDaCorrecao = new BigDecimal(0);
	private boolean isValorAnteriorConfigurado;
	private boolean isNovoTopo;
	private boolean estaNoRangeDeLimite;
	private boolean isNovoFundo;
	private int valorDaVariacao;
	private boolean formadoSegundoTopo;
	private BigDecimal armazenaValorSegundoFundo = new BigDecimal(0);
	private boolean formouPivoDeCompra;
	private boolean formadoTopoRelevante;
	private int hit;
	private boolean entrouNoRangeDeInicio;
	private int movimento = 0;
}

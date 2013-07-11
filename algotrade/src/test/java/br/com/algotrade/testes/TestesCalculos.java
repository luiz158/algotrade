package br.com.algotrade.testes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.negocio.CalculaToposEFundos;
import br.com.algotrade.util.AtivoMock;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-teste-calculos.xml" })
@TransactionConfiguration(transactionManager = "meuTransactionManager", defaultRollback = false)
public class TestesCalculos {
		
	@Autowired
	CalculaToposEFundos calculaTEF;
	@Autowired
	AtivoMock ativoMock;
	/**
	 * Simula ultimos valores de UM Ativo, em um determinado periodo de tempo qualquer.
	 * @throws InterruptedException 
	 */
	@Test
	public void testaCalculosTopoEFundos() throws InterruptedException {
		List<Ativo> listaComUmAtivoDaBaseEUmDoSite  = new ArrayList<Ativo>();
		List<Ativo> listaDeAtivosDaBase = null;
		List<Ativo> listaDeAtivosDoSite = null;

		//perfeito
		//double[] ultimosValores = {60,70,25,70,80,30,40};
		//laterizacao e perda de primeiro fundo
		//double[] ultimosValores = {60,60,50,70,5,70,80,30,40};
		BigDecimal[] ultimosValores = {new BigDecimal(60),new BigDecimal(70),
				new BigDecimal(5),new BigDecimal(60),new BigDecimal(65),
				new BigDecimal(1), new BigDecimal(40)};
		
		
		//chamara o calculo sempre com o mesmo Ativo
		for(BigDecimal valor : ultimosValores) {
			listaComUmAtivoDaBaseEUmDoSite = new ArrayList<Ativo>();
			listaComUmAtivoDaBaseEUmDoSite = ativoMock.populaListaQueSimulaDadosDeUmAtivo(valor);
			
			for (int i = 0; i < listaComUmAtivoDaBaseEUmDoSite.size(); i++) {
				if(i % 2 == 0) {
					listaDeAtivosDaBase = new ArrayList<Ativo>();
					listaDeAtivosDaBase.add(listaComUmAtivoDaBaseEUmDoSite.get(i));
				} else {
					listaDeAtivosDoSite = new ArrayList<Ativo>();
					listaDeAtivosDoSite.add(listaComUmAtivoDaBaseEUmDoSite.get(i));
					
					calculaTEF.atualizaToposEFundos(listaDeAtivosDaBase, listaDeAtivosDoSite);
					//aprimoraAlgoritmo(listaDeAtivosDaBase, listaDeAtivosDoSite);
				}
				
			}
		}
		Thread.sleep(1 * 60 * 1000);
	}
	String dialogo;
	
	double valorDoPrimeiroTopo = 0;
	double valorDoSegundoTopo = 0;
	
	double _55PorCentoDoTopo = 0;
	double _70PorCentoDoTopo = 0;
	
	boolean isPrimeiroTopoValido;
	boolean isSegundoTopoValido;
	boolean isCompraValida;
	
	int resultadoAnterior = 100;
	int resultadoNovo = 100;

	Date dataDeHoje = new Date(); 
	public void aprimoraAlgoritmo(List<Ativo> listaAtivosBase, List<Ativo> listaAtivosSite) {
		
		
		double[] ultimosValores = {60,70,5,60,65,1,40};
 		
		
		double va = 0;
		for (double d : ultimosValores) {
			System.out.println("1 topo -> " + (d > va));
			System.out.println("2 topo -> " + (d > va));
			System.out.println("1 fundo -> " + (d < va));
			System.out.println("2 fundo -> " + (d > va));
			va = d;
		}
		
		
		
		
		
		
		}
}

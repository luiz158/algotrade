package br.com.algotrade.testes;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-teste.xml" })
@TransactionConfiguration(transactionManager = "meuTransactionManager", defaultRollback = false)
public class AgendadorThreadTest {

//	private static final int UMA_HORA = 1 * 60 * 60 * 1000;
//	private static final int DUAS_HORAS = 2 * 60 * 60 * 1000;
//	private static final int TRES_HORAS = 3 * 60 * 60 * 1000;
//	private static final int QUATRO_HORAS = 4 * 60 * 60 * 1000;
//	private static final int CINCO_HORAS = 5 * 60 * 60 * 1000;
//	private static final int SEIS_HORAS = 6 * 60 * 60 * 1000;
	private static final int SETE_HORAS = 7 * 60 * 60 * 1000;
	public static boolean LIBERADO = false;
	
	@Before
	public void vai(){
		LIBERADO = true;
	}
	//Segura o thread por x horas, para que enquanto isto, o agendador vá trabalhando
	@Test
	public void testaScheculed() throws Exception  {
		System.out.println("#####" + new Date());
		System.out.println("#####" + Thread.currentThread().getName());
		//hora que resta para o termino do pregao
		Thread.sleep(SETE_HORAS);

	}
	
}
package br.com.algotrade.testes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.util.AtivoMock;
import br.com.algotrade.util.HistoricoDoDiaMock;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-teste.xml" })
@TransactionConfiguration(transactionManager = "meuTransactionManager", defaultRollback = false)
public class HistoricoTest {

	@Autowired private AtivoMock ativoMock;
	@Autowired private HistoricoDoDiaMock historicoMock;
	
	
	
	@Test
	@Transactional
	public void persisteAtivoComHistoricosNaBaseParaTestes() throws Exception {
		Ativo ativo = ativoMock.getUmAtivoSimples();
		
		historicoMock.criaEPersisteHistoricoParaUmAtivo(ativo);
	}
	
}
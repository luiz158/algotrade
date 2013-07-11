package br.com.algotrade.testes;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.negocio.AtivoBuilderForex;
import br.com.algotrade.util.HttpClientUtil;

//sem esta configuracao, eu não testo listeners a cada teste
//@TestExecutionListeners( { DependencyInjectionTestExecutionListener.class })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-teste.xml" })
//@TestExecutionListeners
@TransactionConfiguration(transactionManager = "meuTransactionManager", defaultRollback = false)
public class AtivoTest {

	@Autowired
	private AtivoBuilderForex ativoBuilderForex;
	@Autowired
	private AtivoDAO ativoDAO;
	private static final String SITE_FOREX = 
			"http://www.forexpros.com.pt/indices/bovespa-components";

	
	@Test
	@Transactional
	public void recuperaAtivosTest() throws Exception {
		String responseBody = HttpClientUtil.requestNoSite(SITE_FOREX);

		List<Ativo> ativos = ativoBuilderForex.constroiAtivos(responseBody);
		
		for (Ativo a : ativos) {
			ativoDAO.persist(a);
		}
	}
}
package br.com.algotrade.negocio;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.util.AtivoMock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-teste.xml" })
@TransactionConfiguration(transactionManager = "meuTransactionManager", defaultRollback = false)
public class CalculaBasesEToposTest {

	private AtivoDAO ativoDao;
	@Autowired
	private AtivoMock ativoMock;
	@Autowired
	private CalculaToposEFundos calculaTEF;

	
	List<Ativo> listaAtivosBase;
	List<Ativo> listaAtivosSite;
	
	@Before
	@Transactional
	public void simulaRecuperacaoDosAtivosDoDiaNaBase() {
		Calendar c = Calendar.getInstance();
		c.set(2012, 8, 24);
		
		listaAtivosBase = ativoDao.carregaAtivosByData(c.getTime());
		listaAtivosSite = ativoMock.clonaListaDeAtivos(listaAtivosBase);
	}
	
	@Test
	public void atualizaBasesEToposTeste() {
		calculaTEF.atualizaToposEFundos(listaAtivosBase, listaAtivosSite);
	}
}
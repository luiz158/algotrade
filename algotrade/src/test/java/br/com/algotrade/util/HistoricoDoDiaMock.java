package br.com.algotrade.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.dao.interfaces.HistoricoDoDiaDAO;
import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.entidades.HistoricoDoDia;
import br.com.algotrade.entidades.HistoricoGeral;

@Component
public class HistoricoDoDiaMock {
	
	@Autowired
	private HistoricoDoDia historicoDoDia;
	@Autowired
	private HistoricoDoDiaDAO historicoDiaDAO;
	@Autowired
	private AtivoDAO ativoDao;
	
	private static final String SITE_UOL_INTRADAY_PG_1 = "http://cotacoes.economia.uol.com.br/acao/cotacoes-diarias.html?codigo=CESP6.SA&page=1&size=200";
	private static final String SITE_UOL_INTRADAY_PG_2 = "http://cotacoes.economia.uol.com.br/acao/cotacoes-diarias.html?codigo=CESP6.SA&page=2&size=200";
	
	/**
	 * Recupera um historico da base
	 * @param id
	 * @return
	 */
	public HistoricoDoDia recuperaUmHistorico(long id) {
		return historicoDiaDAO.find(id);
	}
	/**
	 * Recupera o primeiro historico da base
	 * @return
	 */
	public HistoricoDoDia recuperaUmHistorico() {
		return historicoDiaDAO.find(1L); 
	}
	/**
	 * Devolve uma nova lista com o clone do conteudo da lista enviada
	 * @param listaDeOrigem
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Transactional
	public List<HistoricoGeral> clonaListaDeHistoricoGeral(List<HistoricoGeral> listaDeOrigem) throws CloneNotSupportedException {
		List<HistoricoGeral> listaClone = new ArrayList<HistoricoGeral>();
		for (HistoricoGeral h : listaDeOrigem) {
			listaClone.add(h.clone());
		}
		return listaClone;
	}
	/**
	 * Devolveum Historico clonado
	 * @param historico
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public HistoricoDoDia clonaHistorico(HistoricoDoDia historico) throws CloneNotSupportedException {
		return historicoDoDia.clone();
	}

	/**
	 * Salva um mock na base contendo um ativo e um historico associados entre si
	 * @param ativo
	 * @throws Exception
	 */
	public void criaEPersisteHistoricoParaUmAtivo(Ativo ativo) throws Exception {
		int count = 0;
		int countMetodo = 0;
		int proximaPosicaoValida = 0;
		boolean foiCounterZerado = false;
		
		Matcher[] matcher2Paginas = obtemMathcerDasPaginas();
		
		Class<HistoricoDoDia> aClass = HistoricoDoDia.class;
		
		for (int i = 0; i < matcher2Paginas.length; i++) {
			novoValor: 
				while(matcher2Paginas[i].find()) {
					//zera count na segunda iteracao
					if(i == 1 && !foiCounterZerado) {
						count = 0;
						foiCounterZerado = true;
					}
					count++;
					
					
					//limpa sujeira
					if(count <= 6) continue novoValor;
					
					String valor = matcher2Paginas[i].group().replaceFirst("<td>", "").replaceFirst("\\,", ".");
					
					//primeira pagina recebe 40 valores
					if(i == 0) {
						//pega proximo valor apos sujeita
						if (historicoDoDia.getMinuto_1() == null) {
							historicoDoDia.setMinuto_1(new BigDecimal(valor));
							proximaPosicaoValida = atualizaProximaPosicao(proximaPosicaoValida, count);
						}
					} else if (i == 1){
						//Segunda pagina: pega proximo valor apos sujeita
						if (historicoDoDia.getMinuto_41() == null) {
							historicoDoDia.setMinuto_41(new BigDecimal(valor));
							proximaPosicaoValida = atualizaProximaPosicao(proximaPosicaoValida, count);
						}
						
					}
					//garante que pega 5 posicoes sucessivamente, apos o outro
					if (count != proximaPosicaoValida) continue novoValor;
					
					//85 eh a quantidade de metodos da classe Historico
					while(countMetodo <= 84) {
						countMetodo++;
						Method metodoGet = null;
						
						metodoGet = aClass.getMethod("getMinuto_" + countMetodo);
						
						//Traduzindo -> if(getMinuto_XX() == null) { setMinuto_XX(new BigInteger(...))}
						if (metodoGet.invoke(historicoDoDia) == null) {
							Method metodoSet = null;
							
							metodoSet = aClass.getMethod("setMinuto_" + countMetodo, BigDecimal.class);
							metodoSet.invoke(historicoDoDia, new BigDecimal(valor));

							proximaPosicaoValida = atualizaProximaPosicao(proximaPosicaoValida, count);
							continue novoValor;
						}
					}
				}
		}
		historicoDoDia = organizaDadosSimulados(historicoDoDia);
		ativo.setHistoricoDoDia(historicoDoDia);
		historicoDoDia.setAtivo(ativo);
		
		ativoDao.persist(ativo);
	}
	
	/**
	 * Obtem os dados do Site, para alimentar a BASE com um ativo possuindo um historico
	 * @return
	 * @throws Exception
	 */
	private Matcher[] obtemMathcerDasPaginas() throws Exception {
		String pattern = "<td.*?>\\s?\\-?\\d?\\d\\,\\d\\d";
		
		String responseCorpoPg1 = HttpClientUtil.requestNoSite(SITE_UOL_INTRADAY_PG_1);
		String responseCorpoPg2 = HttpClientUtil.requestNoSite(SITE_UOL_INTRADAY_PG_2);
		
		Matcher machValoresPg1 = Pattern.compile(pattern).matcher(responseCorpoPg1);
		Matcher machValoresPg2 = Pattern.compile(pattern).matcher(responseCorpoPg2);
		
		Matcher[] matcher2Paginas = {machValoresPg1, machValoresPg2};
		return matcher2Paginas;
	}
	

	/**
	 * Pega o ultimo valor, de 5 em 5 minutos
	 * @param proximaPosicao
	 * @param count
	 * @return
	 */
	private int atualizaProximaPosicao(int proximaPosicao, int count) {
		return proximaPosicao = (count + (5 * 5));
	}
	
	/**
	 * Inverte a ordem dos dados que vem da base, para simular a mesma ordem que os valores
	 * sao obtidos durante o dia
	 * @param historicoDoDia
	 * @return
	 * @throws Exception
	 */
	private HistoricoDoDia organizaDadosSimulados(HistoricoDoDia historicoDoDia) throws Exception {
		HistoricoDoDia historicoTemp = new HistoricoDoDia();
		
		int quantidadePreenchidos = 0;
		Class<HistoricoDoDia> aClass = HistoricoDoDia.class;
		Method metodoGet = null;
		Method metodoSet = null;
		for(int i = 1; i <= 84; i++) {
			metodoGet = aClass.getMethod("getMinuto_" + i);
			
			//Traduzindo -> if(getMinuto_XX() == null) { setMinuto_XX(new BigInteger(...))}
			if (metodoGet.invoke(historicoDoDia) != null) {
				quantidadePreenchidos++;
			}
		}
		int countMetodo = 1;
		while (quantidadePreenchidos > 0) {
			metodoGet = aClass.getMethod("getMinuto_" + quantidadePreenchidos);
			metodoSet = aClass.getMethod("setMinuto_" + countMetodo, BigDecimal.class);
			metodoSet.invoke(historicoTemp, metodoGet.invoke(historicoDoDia));
			
			countMetodo++;
			quantidadePreenchidos--;
			
		}
		return historicoTemp;
	}
}

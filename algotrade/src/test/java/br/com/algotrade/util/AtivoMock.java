package br.com.algotrade.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.entidades.Grafico;
import br.com.algotrade.entidades.HistoricoDoDia;
import br.com.algotrade.exceptions.ErroInternoException;

@Component
public class AtivoMock {
	@Autowired
	private AtivoDAO ativoDao;
	
	private List<Ativo> listaAtivosCache;

	
	@Transactional
	public Ativo getUmAtivoSimples() {
		Ativo ativo = new Ativo();
		ativo.setNome("CESP6.SA");
		ativo.setBase(new BigDecimal(0.01));
		ativo.setMaxima(new BigDecimal(0.01));
		ativo.setMinima(new BigDecimal(0.01));
		ativo.setUltimo(new BigDecimal(0.01));
		ativo.setUltimoMaisAlto(new BigDecimal(0.01));
		ativo.setUltimoMaisBaixo(new BigDecimal(0.01));
		ativo.setVariacao(new BigDecimal(0.01));
		ativo.setVariacaoPorCento(new BigDecimal(0.01));
		
		return ativo;
	}
	
	BigDecimal ultimoValorPassado = new BigDecimal(0.0);
	Ativo ativoDaBase = new Ativo();
	Ativo ativoDoSite = new Ativo();
	/**
	 * Devolve uma lista com 2 Ativos (
	 * Primeiro: Simula o retorno de UM ativo da BASE - 
	 * Segundo: Simula os dados de UM ativo do Site)
	 * 
	 * @param listaDeAtivosDaBase
	 * @param listaDeAtivosDoSite
	 * @param ultimosValores
	 */
	@Transactional
	public List<Ativo> populaListaQueSimulaDadosDeUmAtivo(BigDecimal ultimoValor) {
		
		String nome = "CESP6.SA";
		

		ativoDaBase.setNome(nome);
		ativoDoSite.setNome(nome);

		ativoDaBase.setUltimo(ultimoValorPassado);
		ativoDoSite.setUltimo(ultimoValor);
		
		ultimoValorPassado = ultimoValor;
		
		if(ativoDaBase.getGrafico() == null)
			ativoDaBase.setGrafico(new Grafico());
		if(ativoDoSite.getGrafico() == null)
			ativoDoSite.setGrafico(new Grafico());
		
		if(ativoDaBase.getUltimoMaisBaixo().doubleValue() == 0) 
			ativoDaBase.setUltimoMaisBaixo(new BigDecimal(9999));
		if(ativoDaBase.getUltimoMaisBaixo().doubleValue() == 0)
			ativoDoSite.setUltimoMaisBaixo(new BigDecimal(9999));
		
		List<Ativo> listaComUmAtivoDaBaseEUmDoSite = new ArrayList<Ativo>();
		listaComUmAtivoDaBaseEUmDoSite.add(ativoDaBase);
		listaComUmAtivoDaBaseEUmDoSite.add(ativoDoSite);
		
		return listaComUmAtivoDaBaseEUmDoSite;
	}
	
	public List<Ativo> listaDeAtivosDaBaseDeTeste() {
		return ativoDao.selectAll();

	}
	/**
	 * Mantem um cache referente aos ativos do dia
	 * @param ultimoValor
	 * @param historico
	 * @return
	 */
	public List<Ativo> cacheaHistoricoDoDia(BigDecimal ultimoValor, Ativo ativo) {
		if(listaAtivosCache == null) listaAtivosCache = new ArrayList<Ativo>();
		listaAtivosCache.add(ativo);
		
		return listaAtivosCache;
	}
	/**
	 * Devolve um clone da lista enviada
	 * @param listaDeOrigem
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@Transactional
	public List<Ativo> clonaListaDeAtivos(List<Ativo> listaDeOrigem) {
		HistoricoDoDia historicoDoDia = null;
		
		List<Ativo> listaAtivosClonados = new ArrayList<Ativo>();
		try {
			for (Ativo a : listaDeOrigem) {
				//TODO:verificar se eh a melhor opcao, manter relacionamento ativo-historico lazy
				//a = ativoDao.merge(a);
				historicoDoDia = a.getHistoricoDoDia().clone();
				a.setHistoricoDoDia(historicoDoDia);
				listaAtivosClonados.add(a);
			}
		} catch (CloneNotSupportedException e) {
			throw new ErroInternoException("GRAVE", "", e);
		}
		return listaAtivosClonados;
	}
}

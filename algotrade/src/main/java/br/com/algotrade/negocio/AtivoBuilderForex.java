package br.com.algotrade.negocio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.dao.interfaces.AtivoDAO;
import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.entidades.Grafico;
import br.com.algotrade.exceptions.DadosRecebidosException;
import br.com.algotrade.negocio.dados.DadosRecebidos;
import br.com.algotrade.negocio.dados.FimDosDados;
import br.com.algotrade.negocio.dados.MaximaAtual;
import br.com.algotrade.negocio.dados.MinimaAtual;
import br.com.algotrade.negocio.dados.UltimoValor;
import br.com.algotrade.negocio.dados.ValorBase;
import br.com.algotrade.negocio.dados.Variacao;
import br.com.algotrade.negocio.dados.VariacaoPorCento;

@Component
public class AtivoBuilderForex {
	
	@Autowired
	AtivoDAO ativoDao;
	@Autowired
	CalculaToposEFundos calculaTEF;
	private static final String padraoForex = "href.?..equities.p.acucar.cbd.pn.n1.*";
	

	boolean baseVeioZerada = false;
	
	public AtivoBuilderForex() {}
	
	/**
	 * Popula todos os ativos, com os dados Recebidos
	 * @param String contendo html
	 * @return lista atualizada dos ativos atuais
	 * @throws Exception 
	 */
	@Transactional
	public List<Ativo> constroiAtivos(String responseBody) throws Exception {
		
		List<Ativo> listaAtivosBase = (List<Ativo>)ativoDao.selectAll();
		List<Ativo> listaAtivosSite = new ArrayList<Ativo>();
		baseVeioZerada = false;
		
		DadosRecebidos chain1 = preparaChainQuePopulaOsAtivos();
		
		Matcher matcher = Pattern.compile(padraoForex).matcher(responseBody);
		
		/** Recupera varios ativos e popula cada um */
		while (matcher.find()) {
			// separa cada linha pelos nomes
			String[] linhas = matcher.group().split("title");

			for (String linha : linhas) {
				Ativo ativoSite = null;
				Matcher matchNome = Pattern.compile(">.*<\\/a").matcher(linha);
				if (matchNome.find()) {
					ativoSite = new Ativo();
					ativoSite.setNome(matchNome.group().replaceAll("(&nbsp;)|(>)|(<\\/a)", " ").trim());

					if (ativoSite.getNome() == null || ativoSite.getNome().equals(""))
						throw new DadosRecebidosException("sem nome", "nome");
				}
				//TODO:Ranquear também os mais liquidos
				Matcher macher = Pattern.compile("\\-?\\d?\\d\\,\\d\\d").matcher(linha);
				
				
				while (macher.find()) {
					chain1.populaAtivo(ativoSite, macher.group().trim().replaceFirst(",", "."));
				}
				if (ativoSite != null) {
					listaAtivosSite.add(ativoSite);
					replicaNaListaDeAtivos(listaAtivosBase, ativoSite);
				}
			}
		}
		
		listaAtivosBase = calculaTEF.atualizaToposEFundos(listaAtivosBase, listaAtivosSite);
		return listaAtivosBase;
	}
	
	/**
	 * Pattern Chain Of Responsibility: Configura para percorrer e popular
	 * todos ops ativos
	 * @return DadosRecebidos
	 */
	private DadosRecebidos preparaChainQuePopulaOsAtivos() {
		DadosRecebidos chain1 = new UltimoValor();
		DadosRecebidos chain2 = new ValorBase();
		DadosRecebidos chain3 = new MaximaAtual();
		DadosRecebidos chain4 = new MinimaAtual();
		DadosRecebidos chain5 = new Variacao();
		DadosRecebidos chain6 = new VariacaoPorCento();
		DadosRecebidos chain7 = new FimDosDados();
		chain1.setProximo(chain2);
		chain2.setProximo(chain3);
		chain3.setProximo(chain4);
		chain4.setProximo(chain5);
		chain5.setProximo(chain6);
		chain6.setProximo(chain7);

		return chain1;
	}
	/**
	 * Clona os ativos, pela primeira vez que a base eh usada, e portanto quando esta zerada
	 * @param ativosBase
	 * @param ativo
	 */
	private void replicaNaListaDeAtivos(List<Ativo> ativosBase, Ativo ativo) {
		//Clona os mesmos ativos de ativosTemp em caso de base vazia
		if(ativosBase.size() == 0 || baseVeioZerada) {
			if(ativosBase.size() == 0) {
				System.err.println("###IMPORTATE: Base estava vazia!###");
			}
			//incializa valores padroes na primeira vez
			try {
				Ativo ativoDaBase = ativo.clone();
				Grafico grafico = new Grafico();
				grafico.setValorDoFundo(new BigDecimal(Double.MAX_VALUE));
				grafico.setValorDoTopo(new BigDecimal(Double.MAX_VALUE));
				grafico.setValorDoFundoAnterior(new BigDecimal(Double.MAX_VALUE));
				
				ativoDaBase.setUltimo(new BigDecimal(Double.MAX_VALUE));
				ativoDaBase.setGrafico(grafico);
				ativosBase.add(ativoDaBase);
			} catch (CloneNotSupportedException e) { e.printStackTrace();}
			
			baseVeioZerada = true;
		}
	}
}

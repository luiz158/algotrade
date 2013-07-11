package br.com.algotrade.negocio;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.algotrade.entidades.Ativo;
import br.com.algotrade.entidades.Grafico;
import br.com.algotrade.exceptions.DadosRecebidosException;
import br.com.algotrade.exceptions.ErroInternoException;
import br.com.algotrade.util.Movimento;

@Component
public class CalculaToposEFundos {

	private BigDecimal valorCorrente;
	private BigDecimal valorAnterior;
	private BigDecimal valorDoTopo;
	private BigDecimal valorDoFundo;
	private BigDecimal valorDoFundoAnterior;
	private BigDecimal valorDoTopoAnterior;
	private BigDecimal armazenaValorSegundoFundo;
	private BigDecimal diferencaDeFundoATopo;
	private BigDecimal correcaoCorrente;
	private boolean formouSegundoFundo;
	private boolean isNovoFundo;
	private boolean isNovoTopo;
	private boolean ultimoFoiFundo;
	private boolean ultimoFoiTopo;
	private boolean formouPivoDeCompra;
	private boolean estaNoRangeDeInicio;
	private boolean entrouNoRangeDeInicio;
	private boolean estaNoRangeDeLimite;
	private boolean formadoSegundoTopo;
	private boolean formadoTopoRelevante;
	private boolean jaEstaNaBase;
	private int movimento;
	private int movimentoAnterior;
	private final static double VALOR_DA_VARIACAO = 0.20;
	private final static BigDecimal PORCENTAGEM_INICIO_DA_CORRECAO = new BigDecimal(0.50);
	private final static BigDecimal PORCENTAGEM_LIMITE_DA_CORRECAO = new BigDecimal("0.80");
	private int iteracao;
	private static int ordem = 0;

	
	@Transactional
	public List<Ativo> atualizaToposEFundos(List<Ativo> listaAtivosBase, List<Ativo> listaAtivosSite) {
		iteracao = 0;
		if(listaAtivosSite.size() != listaAtivosBase.size())
			throw new DadosRecebidosException();
		
		for(int i = 0; i < listaAtivosSite.size(); i++) {
			
			Ativo ativoDaBase = listaAtivosBase.get(i);
			Ativo ativoDoSite = listaAtivosSite.get(i);
			
			if(!ativoDaBase.equals(ativoDoSite))
				throw new ErroInternoException("ERRO", "Nao sao iguais");
			
			Grafico grafico = ativoDaBase.getGrafico();
		
			incializaTodosAtributos(ativoDaBase, ativoDoSite, grafico);
			
			movimentoAnterior = movimento;
			
			//Movimentos (do candle)
			if(valorCorrente.doubleValue() > valorAnterior.doubleValue()) {
				movimento = Movimento.ALTA;
			} else if (valorCorrente.doubleValue() < valorAnterior.doubleValue()){
				movimento = Movimento.BAIXA;
			} else {
				movimento = Movimento.LATERAL;
			}

			if (ordem == 0) {
				grafico.setValorInicio(valorCorrente);
				grafico.setValorMaisBaixo(valorCorrente);
				grafico.setValorMaisAlto(valorCorrente);
				ordem++;
			} else if(ordem > 0) {
				if((movimento == Movimento.BAIXA)
						|| (movimento == Movimento.LATERAL && movimentoAnterior == Movimento.BAIXA)) {
					grafico.setValorMaisBaixo(valorCorrente);
				} else {
					grafico.setValorMaisAlto(valorCorrente);
				}
				ordem++;
			} else if(ordem == 3) {
				grafico.setValorFim(valorCorrente);
			}
			
			//Topo
			if(valorCorrente.doubleValue() > valorDoTopo.doubleValue() &&
					valorCorrente.doubleValue() > valorDoFundo.doubleValue()) {
				//entra aqui ao formar o segundo fundo
				if(formouSegundoFundo) { 
					valorDoFundoAnterior = valorDoFundo;
					valorDoFundo = armazenaValorSegundoFundo;
					formadoSegundoTopo = true;

				}
				diferencaDeFundoATopo = valorCorrente.subtract(valorDoFundo);
				
				valorDoTopoAnterior = valorDoTopo;
				valorDoTopo = valorCorrente;
	
				ultimoFoiFundo = false;
				isNovoTopo = true;
				ultimoFoiTopo = true;
			}
			//Fundo
			if (valorCorrente.doubleValue() < valorDoFundo.doubleValue()) {
				valorDoFundoAnterior = valorDoFundo;
				valorDoFundo = valorCorrente;
	
				//quando base vazia
				if(!jaEstaNaBase) {
					valorAnterior = new BigDecimal(0);
					valorDoFundoAnterior = new BigDecimal(0);
					valorDoTopo = new BigDecimal(0);
				}
				ultimoFoiTopo = false;
				ultimoFoiFundo = true;
				isNovoFundo = true;
				
				resetaPadraoEFundo();
			}
			
			//Ordem Inversa (quanto mais cai, mais aumenta o valor)
			if((isNovoTopo || isNovoFundo) || valorDoTopo.doubleValue() == 0) {
				correcaoCorrente = new BigDecimal(0);
			} else {
				correcaoCorrente = correcaoCorrente.add(valorAnterior.subtract(valorCorrente));
			}
			
			//analisa primeira e segunda formacao de fundo candidato
			if(diferencaDeFundoATopo.doubleValue() >= VALOR_DA_VARIACAO
					||(diferencaDeFundoATopo.doubleValue() > 0
					&& formouSegundoFundo)){
				
				formadoTopoRelevante = true;
	
				BigDecimal rangeInicioDaCorrecao = diferencaDeFundoATopo.multiply(PORCENTAGEM_INICIO_DA_CORRECAO);
				BigDecimal rangeLimiteDaCorrecao = diferencaDeFundoATopo.multiply(PORCENTAGEM_LIMITE_DA_CORRECAO);
				
				//entra no range quando chega no range de inicio, e perde apenas se o range limite for ultrapassado
				if(correcaoCorrente.doubleValue() >= rangeInicioDaCorrecao.doubleValue()) {
					entrouNoRangeDeInicio = true;
					estaNoRangeDeInicio = true;
				} else {
					estaNoRangeDeInicio = false;
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
			if(formouPivoDeCompra) {
				resetaPadraoEFundo();
			}
			imprimeTudo();
			atribuiOsNovosValoresDoAtivo(ativoDaBase, grafico);
			alertaProvisorio(grafico);
			listaAtivosBase.add(ativoDaBase);
			
	
		}
		return listaAtivosBase;
	}
	/**
	 * alerta se alguma flag de acao for verdadeira
	 * @param ativoDaBase
	 */
	public void alertaProvisorio(final Grafico grafico) {
		boolean entraNaThreadDeAlerta = false;
		//final String msg1 = "FORMADO SEGUNDO TOPO";
		final String msg2 = "NOVO CANDIDATO À COMPRA";
		/*
		if(grafico.isFormadoSegundoTopo()) {
			System.out.println(msg1);
			entraNaThreadDeAlerta = true;
		}*/
		if(grafico.isFormouPivoDeCompra()) {
			System.out.println(msg2);
			entraNaThreadDeAlerta = true;
		}
		
		if(entraNaThreadDeAlerta) {
			Runnable r = new Runnable() {
				
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName());
					
					JOptionPane op = null;
					JDialog dialog = null;
					
					if(grafico.isFormouPivoDeCompra()) {
						String texto = msg2 + 
								"\nPivo Atual: " + grafico.getAtivo().getUltimo();
						
						op = new JOptionPane(texto, JOptionPane.INFORMATION_MESSAGE);
						dialog = op.createDialog(grafico.getAtivo().getNome());
					} /*else {
						String texto = msg1 + 
								"\nTopo Atual: " + grafico.getAtivo().getUltimo();
						
						op = new JOptionPane(texto, JOptionPane.INFORMATION_MESSAGE);
						dialog = op.createDialog(grafico.getAtivo().getNome());
						
					}*/
					
					
					dialog.setAlwaysOnTop(true);
					dialog.setModal(true);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
					System.out.println("parou");
					
				}
			};
			//TODO:REMOVER THREAD SLEEP
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Thread(r).start();
			System.out.println("CONTINUA");
		}
	}
	/**
	 * Atribui todos os valores modificados ao Ativo e seu Grafico (sua composicao)
	 * 
	 * @param ativoDaBase
	 * @param grafico
	 */
	private void atribuiOsNovosValoresDoAtivo(Ativo ativoDaBase, Grafico grafico) {
		ativoDaBase.setUltimo(valorCorrente.setScale(2, RoundingMode.HALF_UP));
		
		grafico.setValorDoFundo(valorDoFundo.setScale(2, RoundingMode.HALF_UP));
		grafico.setValorDoTopo(valorDoTopo.setScale(2, RoundingMode.HALF_UP));
		grafico.setValorDoFundoAnterior(valorDoFundoAnterior.setScale(2, RoundingMode.HALF_UP));
		grafico.setValorDoTopoAnterior(valorDoTopoAnterior.setScale(2, RoundingMode.HALF_UP));
		grafico.setArmazenaValorSegundoFundo(armazenaValorSegundoFundo.setScale(2, RoundingMode.HALF_UP));
		grafico.setDiferencaDeFundoATopo(diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
		grafico.setCorrecaoCorrente(correcaoCorrente.setScale(2, RoundingMode.HALF_UP));
		grafico.setFormouSegundoFundo(formouSegundoFundo);
		grafico.setUltimoFoiFundo(ultimoFoiFundo);
		grafico.setUltimoFoiTopo(ultimoFoiTopo);
		grafico.setFormouPivoDeCompra(formouPivoDeCompra);
		grafico.setEstaNoRangeDeInicio(estaNoRangeDeInicio);
		grafico.setEstaNoRangeDeLimite(estaNoRangeDeLimite);
		grafico.setFormadoSegundoTopo(formadoSegundoTopo);
		grafico.setFormadoTopoRelevante(formadoTopoRelevante);
		grafico.setMovimento(movimento);
		
		ativoDaBase.setGrafico(grafico);
		grafico.setAtivo(ativoDaBase); //para Fetch do JPA
	}
	/**
	 * Cada iteracao, os atributos sao inicializados ao valor default, ou da base.
	 * 
	 * @param ativoDaBase
	 * @param ativoDoSite
	 * @param grafico
	 */
	private void incializaTodosAtributos(Ativo ativoDaBase, Ativo ativoDoSite,
			Grafico grafico) {
		valorCorrente = ativoDoSite.getUltimo();
		valorAnterior = ativoDaBase.getUltimo();
		valorDoTopo = grafico.getValorDoTopo();
		valorDoFundo = grafico.getValorDoFundo();
		valorDoFundoAnterior = grafico.getValorDoFundoAnterior();
		valorDoTopoAnterior = grafico.getValorDoTopoAnterior();
		armazenaValorSegundoFundo = grafico.getArmazenaValorSegundoFundo();
		diferencaDeFundoATopo = grafico.getDiferencaDeFundoATopo();
		correcaoCorrente = grafico.getCorrecaoCorrente();
		formouSegundoFundo = grafico.isFormouSegundoFundo();
		isNovoFundo = false;
		isNovoTopo = false;
		formouPivoDeCompra = false;
		movimento = grafico.getMovimento();
		ultimoFoiFundo = grafico.isUltimoFoiFundo();
		ultimoFoiTopo = grafico.isUltimoFoiTopo();
		estaNoRangeDeInicio = grafico.isEstaNoRangeDeInicio();
		estaNoRangeDeLimite = grafico.isEstaNoRangeDeLimite();
		formadoSegundoTopo = grafico.isFormadoSegundoTopo();
		formadoTopoRelevante = grafico.isFormadoTopoRelevante();
		jaEstaNaBase = valorAnterior.doubleValue() != Double.MAX_VALUE;
	}
	/**
	 *Reseta por tres motivos -> 1) reduntante, pois passa sempre apos fundo. 2) ao sair de algum padrao. 
	 * 3) ao confirmar uma etapa de um padrao.
	 */
	private void resetaPadraoEFundo() {
		diferencaDeFundoATopo = new BigDecimal(0);
		
		if (!isNovoFundo) {
			valorDoFundoAnterior = new BigDecimal(0);
		}

		valorDoTopoAnterior = new BigDecimal(0);
		armazenaValorSegundoFundo = new BigDecimal(0);
		valorDoTopo = new BigDecimal(0);
		ultimoFoiTopo = false;
		
		valorDoFundo = valorCorrente;
		ultimoFoiFundo = true;
		
		correcaoCorrente = new BigDecimal(0);
		estaNoRangeDeInicio = false;
		entrouNoRangeDeInicio = false;
		estaNoRangeDeLimite = false;
		formadoSegundoTopo = false;
		formadoTopoRelevante = false;
		formouSegundoFundo = false;
	}
	
	private void imprimeTudo() {
		System.out.println(++iteracao + ") -----------------------------");
		System.out.println("valorCorrente: " + valorCorrente);
		System.out.println("---------------------------------");
		System.out.println("valorDoTopo: " + valorDoTopo);
		System.out.println("valorDoFundo: " + valorDoFundo);
		System.out.println("diferencaDeFundoATopo: " + diferencaDeFundoATopo.setScale(2, RoundingMode.HALF_UP));
		System.out.println("");
		System.out.println("correcaoCorrente: " + correcaoCorrente);
		//System.out.println("rangeInicioDaCorrecao: " + rangeInicioDaCorrecao);
		//System.out.println("rangeLimiteDaCorrecao: " + rangeLimiteDaCorrecao);
		System.out.println("");
		System.out.println("formadoTopoRelevante: " + formadoTopoRelevante);
		System.out.println("formouSegundoFundo: " + formouSegundoFundo);
		System.out.println("formadoSegundoTopo: " + formadoSegundoTopo);
		System.out.println("formouPivoDeCompra: " + formouPivoDeCompra);
		System.out.println("");
		System.out.println("armazenaValorSegundoFundo: " + armazenaValorSegundoFundo);
		System.out.println("formadoSegundoTopo: " + formadoSegundoTopo);
		System.out.println("");
		System.out.println("----------infos-----------------");
		System.out.println("valorAnterior: " + valorAnterior);
		System.out.println("valorDoFundoAnterior: " + valorDoFundoAnterior);
		System.out.println("valorDoTopoAnterior: " + valorDoTopoAnterior);
		System.out.println("isNovoFundo: " + isNovoFundo);
		System.out.println("isNovoTopo: " + isNovoTopo);
		System.out.println("ultimoFoiFundo: " + ultimoFoiFundo);
		System.out.println("ultimoFoiTopo: " + ultimoFoiTopo);
		System.out.println("estaNoRangeDeInicio: " + estaNoRangeDeInicio);
		System.out.println("estaNoRangeDeLimite: " + estaNoRangeDeLimite);
	}
	
}

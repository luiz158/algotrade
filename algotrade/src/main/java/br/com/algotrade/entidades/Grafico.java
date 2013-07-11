package br.com.algotrade.entidades;

import java.math.BigDecimal;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;


@Entity
public class Grafico {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_grafico")
	private Long id;
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "fk_grafico_ativo_id")
	private Ativo ativo;
	
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorDoFundo = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorDoTopo = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal diferencaDeFundoATopo = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorDoFundoAnterior = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal correcaoCorrente = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorDoTopoAnterior = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal rangeInicioDaCorrecao = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal rangeLimiteDaCorrecao = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal porcentagemInicioDaCorrecao = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal porcentagemLimiteDaCorrecao = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorMaisBaixo = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorMaisAlto = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorInicio = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal valorFim = new BigDecimal(0);
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal armazenaValorSegundoFundo = new BigDecimal(0);
	@Column(nullable = false)
	private boolean ultimoFoiFundo;
	@Column(nullable = false)
	private boolean ultimoFoiTopo;
	@Column(nullable = false)
	private boolean isValorDeFundoAbaixo;
	@Column(nullable = false)
	private boolean formouSegundoFundo;
	@Column(nullable = false)
	private boolean isValorAnteriorConfigurado;
	@Column(nullable = false)
	private boolean estaNoRangeDeLimite;
	@Column(nullable = false)
	private boolean estaNoRangeDeInicio;
	@Column(nullable = false)
	private int valorDaVariacao;
	@Column(nullable = false)
	private boolean formadoSegundoTopo;
	@Column(nullable = false)
	private boolean formouPivoDeCompra;
	@Column(nullable = false)
	private boolean formadoTopoRelevante;
	@Column(nullable = false)
	private boolean entrouNoRangeDeInicio;
	
	//Transientes
	@Column(nullable = false, length = 8, columnDefinition = "integer")
	@Transient
	private int hit;
	@Column(nullable = false, length = 8, columnDefinition = "integer")
	@Transient
	private int movimento = 0;
	@Column(nullable = false, length = 8, columnDefinition = "integer")
	@Transient
	private int movimentoAnterior = 0;

	//Getters and Setters
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Ativo getAtivo() {
		return ativo;
	}
	public void setAtivo(Ativo ativo) {
		this.ativo = ativo;
	}
	public BigDecimal getValorDoFundo() {
		return valorDoFundo;
	}
	public void setValorDoFundo(BigDecimal valorDoFundo) {
		this.valorDoFundo = valorDoFundo;
	}
	public BigDecimal getValorDoTopo() {
		return valorDoTopo;
	}
	public void setValorDoTopo(BigDecimal valorDoTopo) {
		this.valorDoTopo = valorDoTopo;
	}
	public boolean isUltimoFoiFundo() {
		return ultimoFoiFundo;
	}
	public void setUltimoFoiFundo(boolean ultimoFoiFundo) {
		this.ultimoFoiFundo = ultimoFoiFundo;
	}
	public boolean isUltimoFoiTopo() {
		return ultimoFoiTopo;
	}
	public void setUltimoFoiTopo(boolean ultimoFoiTopo) {
		this.ultimoFoiTopo = ultimoFoiTopo;
	}
	public BigDecimal getDiferencaDeFundoATopo() {
		return diferencaDeFundoATopo;
	}
	public void setDiferencaDeFundoATopo(BigDecimal diferencaDeFundoATopo) {
		this.diferencaDeFundoATopo = diferencaDeFundoATopo;
	}
	public BigDecimal getValorDoFundoAnterior() {
		return valorDoFundoAnterior;
	}
	public void setValorDoFundoAnterior(BigDecimal valorDoFundoAnterior) {
		this.valorDoFundoAnterior = valorDoFundoAnterior;
	}
	public boolean isValorDeFundoAbaixo() {
		return isValorDeFundoAbaixo;
	}
	public void setValorDeFundoAbaixo(boolean isValorDeFundoAbaixo) {
		this.isValorDeFundoAbaixo = isValorDeFundoAbaixo;
	}
	public BigDecimal getCorrecaoCorrente() {
		return correcaoCorrente;
	}
	public void setCorrecaoCorrente(BigDecimal correcaoCorrente) {
		this.correcaoCorrente = correcaoCorrente;
	}
	public boolean isFormouSegundoFundo() {
		return formouSegundoFundo;
	}
	public void setFormouSegundoFundo(boolean formouSegundoFundo) {
		this.formouSegundoFundo = formouSegundoFundo;
	}
	public BigDecimal getValorDoTopoAnterior() {
		return valorDoTopoAnterior;
	}
	public void setValorDoTopoAnterior(BigDecimal valorDoTopoAnterior) {
		this.valorDoTopoAnterior = valorDoTopoAnterior;
	}
	public BigDecimal getRangeInicioDaCorrecao() {
		return rangeInicioDaCorrecao;
	}
	public void setRangeInicioDaCorrecao(BigDecimal rangeInicioDaCorrecao) {
		this.rangeInicioDaCorrecao = rangeInicioDaCorrecao;
	}
	public BigDecimal getRangeLimiteDaCorrecao() {
		return rangeLimiteDaCorrecao;
	}
	public void setRangeLimiteDaCorrecao(BigDecimal rangeLimiteDaCorrecao) {
		this.rangeLimiteDaCorrecao = rangeLimiteDaCorrecao;
	}
	public BigDecimal getPorcentagemInicioDaCorrecao() {
		return porcentagemInicioDaCorrecao;
	}
	public void setPorcentagemInicioDaCorrecao(
			BigDecimal porcentagemInicioDaCorrecao) {
		this.porcentagemInicioDaCorrecao = porcentagemInicioDaCorrecao;
	}
	public BigDecimal getPorcentagemLimiteDaCorrecao() {
		return porcentagemLimiteDaCorrecao;
	}
	public void setPorcentagemLimiteDaCorrecao(
			BigDecimal porcentagemLimiteDaCorrecao) {
		this.porcentagemLimiteDaCorrecao = porcentagemLimiteDaCorrecao;
	}
	public BigDecimal getValorMaisBaixo() {
		return valorMaisBaixo;
	}
	public void setValorMaisBaixo(BigDecimal valorMaisBaixo) {
		this.valorMaisBaixo = valorMaisBaixo;
	}
	public BigDecimal getValorMaisAlto() {
		return valorMaisAlto;
	}
	public void setValorMaisAlto(BigDecimal valorMaisAlto) {
		this.valorMaisAlto = valorMaisAlto;
	}
	public BigDecimal getValorInicio() {
		return valorInicio;
	}
	public void setValorInicio(BigDecimal valorInicio) {
		this.valorInicio = valorInicio;
	}
	public BigDecimal getValorFim() {
		return valorFim;
	}
	public void setValorFim(BigDecimal valorFim) {
		this.valorFim = valorFim;
	}
	public boolean isValorAnteriorConfigurado() {
		return isValorAnteriorConfigurado;
	}
	public void setValorAnteriorConfigurado(boolean isValorAnteriorConfigurado) {
		this.isValorAnteriorConfigurado = isValorAnteriorConfigurado;
	}
	public boolean isEstaNoRangeDeLimite() {
		return estaNoRangeDeLimite;
	}
	public void setEstaNoRangeDeLimite(boolean estaNoRangeDeLimite) {
		this.estaNoRangeDeLimite = estaNoRangeDeLimite;
	}
	public boolean isEstaNoRangeDeInicio() {
		return estaNoRangeDeInicio;
	}
	public void setEstaNoRangeDeInicio(boolean estaNoRangeDeInicio) {
		this.estaNoRangeDeInicio = estaNoRangeDeInicio;
	}
	public int getValorDaVariacao() {
		return valorDaVariacao;
	}
	public void setValorDaVariacao(int valorDaVariacao) {
		this.valorDaVariacao = valorDaVariacao;
	}
	public boolean isFormadoSegundoTopo() {
		return formadoSegundoTopo;
	}
	public void setFormadoSegundoTopo(boolean formadoSegundoTopo) {
		this.formadoSegundoTopo = formadoSegundoTopo;
	}
	public BigDecimal getArmazenaValorSegundoFundo() {
		return armazenaValorSegundoFundo;
	}
	public void setArmazenaValorSegundoFundo(BigDecimal armazenaValorSegundoFundo) {
		this.armazenaValorSegundoFundo = armazenaValorSegundoFundo;
	}
	public boolean isFormouPivoDeCompra() {
		return formouPivoDeCompra;
	}
	public void setFormouPivoDeCompra(boolean formouPivoDeCompra) {
		this.formouPivoDeCompra = formouPivoDeCompra;
	}
	public boolean isFormadoTopoRelevante() {
		return formadoTopoRelevante;
	}
	public void setFormadoTopoRelevante(boolean formadoTopoRelevante) {
		this.formadoTopoRelevante = formadoTopoRelevante;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
	}
	public boolean isEntrouNoRangeDeInicio() {
		return entrouNoRangeDeInicio;
	}
	public void setEntrouNoRangeDeInicio(boolean entrouNoRangeDeInicio) {
		this.entrouNoRangeDeInicio = entrouNoRangeDeInicio;
	}
	public int getMovimento() {
		return movimento;
	}
	public void setMovimento(int movimento) {
		this.movimento = movimento;
	}
	public int getMovimentoAnterior() {
		return movimentoAnterior;
	}
	public void setMovimentoAnterior(int movimentoAnterior) {
		this.movimentoAnterior = movimentoAnterior;
	}
}

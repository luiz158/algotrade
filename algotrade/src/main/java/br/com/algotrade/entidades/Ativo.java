package br.com.algotrade.entidades;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


@Entity
public class Ativo implements Cloneable {
	//@Column(name = "cod_empresa", nullable = false, length = 20)  
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_ativo")
	private Long id;
	
	@Temporal(TemporalType.DATE)
	private Date data = new Date();
	@Column(nullable = false, length = 70)
	private String nome;
	@Column(nullable = false, length = 8, columnDefinition = "float default '0'")
	private BigDecimal ultimo = new BigDecimal(0);
	@Column(nullable = false, length = 3)
	private int quantidadeConsultaPorDia;
	
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal base = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal maxima = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal minima = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal variacao = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal variacaoPorCento = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal ultimoMaisBaixo = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal ultimoMaisAlto = new BigDecimal(0);
	@Transient
	@Column(nullable = false)
	private BigDecimal formadoPrimeiroTopo = new BigDecimal(0);
	@Transient
	@Column(nullable = false)
	private BigDecimal formadoSegundoTopo = new BigDecimal(0);
	@Transient
	@Column(nullable = false)
	private BigDecimal formadoPadraoParaCompra = new BigDecimal(0);
	@Transient
	@Column(nullable = false, length = 7)
	private BigDecimal volume = new BigDecimal(0);
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private Grafico grafico;
	
	@OneToOne(cascade={CascadeType.PERSIST,CascadeType.MERGE})
	private HistoricoDoDia historicoDoDia;

	@OneToMany(mappedBy="ativo", cascade={CascadeType.PERSIST,CascadeType.MERGE} )
	private List<HistoricoGeral> listaHistoricoGeral;
	
	public Ativo() {}
	
	@Override
    public Ativo clone() throws CloneNotSupportedException {
            return (Ativo)super.clone();
    }

	@Override
	public boolean equals(Object obj) {
		return this.nome.equals(((Ativo)obj).getNome());
	}
	//Getters e Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getUltimo() {
		return ultimo;
	}

	public void setUltimo(BigDecimal ultimo) {
		this.ultimo = ultimo;
	}

	public int getQuantidadeConsultaPorDia() {
		return quantidadeConsultaPorDia;
	}

	public void setQuantidadeConsultaPorDia(int quantidadeConsultaPorDia) {
		this.quantidadeConsultaPorDia = quantidadeConsultaPorDia;
	}

	public BigDecimal getBase() {
		return base;
	}

	public void setBase(BigDecimal base) {
		this.base = base;
	}

	public BigDecimal getMaxima() {
		return maxima;
	}

	public void setMaxima(BigDecimal maxima) {
		this.maxima = maxima;
	}

	public BigDecimal getMinima() {
		return minima;
	}

	public void setMinima(BigDecimal minima) {
		this.minima = minima;
	}

	public BigDecimal getVariacao() {
		return variacao;
	}

	public void setVariacao(BigDecimal variacao) {
		this.variacao = variacao;
	}

	public BigDecimal getVariacaoPorCento() {
		return variacaoPorCento;
	}

	public void setVariacaoPorCento(BigDecimal variacaoPorCento) {
		this.variacaoPorCento = variacaoPorCento;
	}

	public BigDecimal getUltimoMaisBaixo() {
		return ultimoMaisBaixo;
	}

	public void setUltimoMaisBaixo(BigDecimal ultimoMaisBaixo) {
		this.ultimoMaisBaixo = ultimoMaisBaixo;
	}

	public BigDecimal getUltimoMaisAlto() {
		return ultimoMaisAlto;
	}

	public void setUltimoMaisAlto(BigDecimal ultimoMaisAlto) {
		this.ultimoMaisAlto = ultimoMaisAlto;
	}

	public BigDecimal getFormadoPrimeiroTopo() {
		return formadoPrimeiroTopo;
	}

	public void setFormadoPrimeiroTopo(BigDecimal formadoPrimeiroTopo) {
		this.formadoPrimeiroTopo = formadoPrimeiroTopo;
	}

	public BigDecimal getFormadoSegundoTopo() {
		return formadoSegundoTopo;
	}

	public void setFormadoSegundoTopo(BigDecimal formadoSegundoTopo) {
		this.formadoSegundoTopo = formadoSegundoTopo;
	}

	public BigDecimal getFormadoPadraoParaCompra() {
		return formadoPadraoParaCompra;
	}

	public void setFormadoPadraoParaCompra(BigDecimal formadoPadraoParaCompra) {
		this.formadoPadraoParaCompra = formadoPadraoParaCompra;
	}

	public BigDecimal getVolume() {
		return volume;
	}

	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}

	public Grafico getGrafico() {
		return grafico;
	}

	public void setGrafico(Grafico grafico) {
		this.grafico = grafico;
	}

	public HistoricoDoDia getHistoricoDoDia() {
		return historicoDoDia;
	}

	public void setHistoricoDoDia(HistoricoDoDia historicoDoDia) {
		this.historicoDoDia = historicoDoDia;
	}

	public List<HistoricoGeral> getListaHistoricoGeral() {
		return listaHistoricoGeral;
	}

	public void setListaHistoricoGeral(List<HistoricoGeral> listaHistoricoGeral) {
		this.listaHistoricoGeral = listaHistoricoGeral;
	}
}
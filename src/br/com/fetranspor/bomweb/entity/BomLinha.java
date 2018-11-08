package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.entity.EntityBase;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * The Class BomLinha.
 */
@Entity
@Table( name = "bom_linha" )
public class BomLinha
	extends EntityBase
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 7876616269366906478L;

	/**
	 * Gets the bom.
	 *
	 * @return the bom
	 */
	public Bom getBom()
	{
		return this.bom;
	}

	/**
	 * Gets the capacidade.
	 *
	 * @return the capacidade
	 */
	public Integer getCapacidade()
	{
		return this.capacidade;
	}

	/**
	 * Gets the extensao total.
	 *
	 * @return the extensao total
	 */
	public Double getExtensaoTotal()
	{
		final LinhaVigencia linhaVigencia = getLinhaVigencia();
		if ( linhaVigencia == null )
		{
			return null;
		}// if
		final double extensaoTotal = linhaVigencia.getExtensaoTotal();
		return extensaoTotal;
	}

	/**
	 * Gets the frota.
	 *
	 * @return the frota
	 */
	public Integer getFrota()
	{
		return this.frota;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}

	/**
	 * Gets the kms percorridos.
	 *
	 * @return the kms percorridos
	 */
	public double getKmsPercorridos()
	{

		return ( ( this.viagensOrdinariasAB + this.viagensExtraordinariasAB ) * this.linhaVigencia.getExtensaoAB() )
			+ ( ( this.viagensOrdinariasBA + this.viagensExtraordinariasBA ) * this.linhaVigencia.getExtensaoBA() );
	}

	/**
	 * Gets the linha vigencia.
	 *
	 * @return the linha vigencia
	 */
	public LinhaVigencia getLinhaVigencia()
	{
		return this.linhaVigencia;
	}

	/**
	 * Gets the numero passageiros.
	 *
	 * @return the numero passageiros
	 */
	public long getNumeroPassageiros()
	{
		long numeroPassageiros = 0L;
		for ( final BomSecao secao : this.secoes )
		{
			numeroPassageiros += secao.getTotalPassageiros();
		}

		return numeroPassageiros;
	}

	/**
	 * Gets the numero viagens.
	 *
	 * @return the numero viagens
	 */
	public long getNumeroViagens()
	{
		return this.viagensOrdinariasAB
			+ this.viagensExtraordinariasAB
			+ this.viagensOrdinariasBA
			+ this.viagensExtraordinariasBA;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return Returns the piso1AB.
	 * @see #piso1AB
	 */
	public Double getPiso1AB()
	{
		return this.piso1AB;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return Returns the piso1BA.
	 * @see #piso1BA
	 */
	public Double getPiso1BA()
	{
		return this.piso1BA;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return Returns the piso2AB.
	 * @see #piso2AB
	 */
	public Double getPiso2AB()
	{
		return this.piso2AB;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return Returns the piso2BA.
	 * @see #piso2BA
	 */
	public Double getPiso2BA()
	{
		return this.piso2BA;
	}

	/**
	 * Gets the previa justificativa.
	 *
	 * @return the previa justificativa
	 */
	public String getPreviaJustificativa()
	{
		if ( this.ultimaJustificativa.length() < 21 )
		{
			return this.ultimaJustificativa;
		}

		return this.ultimaJustificativa.substring( 0, 20 ) + "...";
	}

	/**
	 * Gets the secoes.
	 *
	 * @return the secoes
	 */
	public List<BomSecao> getSecoes()
	{
		if ( this.secoes != null )
		{
			Collections.sort( this.secoes );
		}
		return this.secoes;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus()
	{
		return this.status;
	}

	/**
	 * Gets the tipo de veiculo.
	 *
	 * @return the tipo de veiculo
	 */
	public TipoDeVeiculo getTipoDeVeiculo()
	{
		return this.tipoDeVeiculo;
	}

	/**
	 * Gets the total receita.
	 *
	 * @return the total receita
	 */
	public BigDecimal getTotalReceita()
	{
		BigDecimal totalReceita = BigDecimal.ZERO;
		for ( final BomSecao secao : this.secoes )
		{
			totalReceita = totalReceita.add( secao.getTotalReceita() );
		}

		return totalReceita;
	}

	/**
	 * Gets the ultima justificativa.
	 *
	 * @return the ultima justificativa
	 */
	public String getUltimaJustificativa()
	{
		return this.ultimaJustificativa;
	}

	/**
	 * Gets the viagens extraordinarias ab.
	 *
	 * @return the viagens extraordinarias ab
	 */
	public Integer getViagensExtraordinariasAB()
	{
		return this.viagensExtraordinariasAB;
	}

	/**
	 * Gets the viagens extraordinarias ba.
	 *
	 * @return the viagens extraordinarias ba
	 */
	public Integer getViagensExtraordinariasBA()
	{
		return this.viagensExtraordinariasBA;
	}

	/**
	 * Gets the viagens ordinarias ab.
	 *
	 * @return the viagens ordinarias ab
	 */
	public Integer getViagensOrdinariasAB()
	{
		return this.viagensOrdinariasAB;
	}

	/**
	 * Gets the viagens ordinarias ba.
	 *
	 * @return the viagens ordinarias ba
	 */
	public Integer getViagensOrdinariasBA()
	{
		return this.viagensOrdinariasBA;
	}

	/**
	 * Checks if is inoperante.
	 *
	 * @return true, if is inoperante
	 */
	public boolean isInoperante()
	{
		return this.inoperante;
	}

	/**
	 * Sets the bom.
	 *
	 * @param bom
	 *            the new bom
	 */
	public void setBom( final Bom bom )
	{
		this.bom = bom;
	}

	/**
	 * Sets the capacidade.
	 *
	 * @param capacidade
	 *            the new capacidade
	 */
	public void setCapacidade( final Integer capacidade )
	{
		this.capacidade = capacidade;
	}

	/**
	 * Sets the frota.
	 *
	 * @param frota
	 *            the new frota
	 */
	public void setFrota( final Integer frota )
	{
		this.frota = frota;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final Long id )
	{
		this.id = id;
	}

	/**
	 * Sets the inoperante.
	 *
	 * @param inoperante
	 *            the new inoperante
	 */
	public void setInoperante( final boolean inoperante )
	{
		this.inoperante = inoperante;
	}

	/**
	 * Sets the linha vigencia.
	 *
	 * @param linhaVigencia
	 *            the new linha vigencia
	 */
	public void setLinhaVigencia( final LinhaVigencia linhaVigencia )
	{
		this.linhaVigencia = linhaVigencia;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param piso1ab
	 *            The piso1AB to set.
	 * @see #piso1AB
	 */
	public void setPiso1AB( final Double piso1ab )
	{
		this.piso1AB = piso1ab;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param piso1ba
	 *            The piso1BA to set.
	 * @see #piso1BA
	 */
	public void setPiso1BA( final Double piso1ba )
	{
		this.piso1BA = piso1ba;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param piso2ab
	 *            The piso2AB to set.
	 * @see #piso2AB
	 */
	public void setPiso2AB( final Double piso2ab )
	{
		this.piso2AB = piso2ab;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @param piso2ba
	 *            The piso2BA to set.
	 * @see #piso2BA
	 */
	public void setPiso2BA( final Double piso2ba )
	{
		this.piso2BA = piso2ba;
	}

	/**
	 * Sets the secoes.
	 *
	 * @param secoes
	 *            the new secoes
	 */
	public void setSecoes( final List<BomSecao> secoes )
	{
		this.secoes = secoes;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus( final Status status )
	{
		this.status = status;
	}

	/**
	 * Sets the tipo de veiculo.
	 *
	 * @param tipoDeVeiculo
	 *            the new tipo de veiculo
	 */
	public void setTipoDeVeiculo( final TipoDeVeiculo tipoDeVeiculo )
	{
		this.tipoDeVeiculo = tipoDeVeiculo;
	}

	/**
	 * Sets the ultima justificativa.
	 *
	 * @param justificativa
	 *            the new ultima justificativa
	 */
	public void setUltimaJustificativa( final String justificativa )
	{
		this.ultimaJustificativa = justificativa;
	}

	/**
	 * Sets the viagens extraordinarias ab.
	 *
	 * @param viagensExtraordinariasAB
	 *            the new viagens extraordinarias ab
	 */
	public void setViagensExtraordinariasAB( final Integer viagensExtraordinariasAB )
	{
		this.viagensExtraordinariasAB = viagensExtraordinariasAB;
	}

	/**
	 * Sets the viagens extraordinarias ba.
	 *
	 * @param viagensExtraordinariasBA
	 *            the new viagens extraordinarias ba
	 */
	public void setViagensExtraordinariasBA( final Integer viagensExtraordinariasBA )
	{
		this.viagensExtraordinariasBA = viagensExtraordinariasBA;
	}

	/**
	 * Sets the viagens ordinarias ab.
	 *
	 * @param viagensOrdinariasAB
	 *            the new viagens ordinarias ab
	 */
	public void setViagensOrdinariasAB( final Integer viagensOrdinariasAB )
	{
		this.viagensOrdinariasAB = viagensOrdinariasAB;
	}

	/**
	 * Sets the viagens ordinarias ba.
	 *
	 * @param viagensOrdinariasBA
	 *            the new viagens ordinarias ba
	 */
	public void setViagensOrdinariasBA( final Integer viagensOrdinariasBA )
	{
		this.viagensOrdinariasBA = viagensOrdinariasBA;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The bom.
	 */
	@NotNull
	@ManyToOne
	private Bom bom;

	/**
	 * The linha vigencia.
	 */
	@NotNull
	@ManyToOne
	@JoinColumn( name = "linha_vigencia_id" )
	private LinhaVigencia linhaVigencia;

	/**
	 * The tipo de veiculo.
	 */
	@ManyToOne
	@JoinColumn( name = "tipo_veiculo_id" )
	private TipoDeVeiculo tipoDeVeiculo;

	/**
	 * The frota.
	 */
	private Integer frota;

	/**
	 * The capacidade.
	 */
	private Integer capacidade;

	/**
	 * The viagens ordinarias ab.
	 */
	@Column( name = "viagens_ordinarias_ab" )
	private Integer viagensOrdinariasAB;

	/**
	 * The viagens extraordinarias ab.
	 */
	@Column( name = "viagens_extraordinarias_ab" )
	private Integer viagensExtraordinariasAB;

	/**
	 * The viagens ordinarias ba.
	 */
	@Column( name = "viagens_ordinarias_ba" )
	private Integer viagensOrdinariasBA;

	/**
	 * The viagens extraordinarias ba.
	 */
	@Column( name = "viagens_extraordinarias_ba" )
	private Integer viagensExtraordinariasBA;

	/**
	 * The secoes.
	 */
	@OneToMany( fetch = FetchType.EAGER, mappedBy = "bomLinha", cascade = {CascadeType.ALL} )
	private List<BomSecao> secoes;

	/**
	 * The status.
	 */
	private Status status;

	/**
	 * The ultima justificativa.
	 */
	@Column( name = "ultima_justificativa" )
	private String ultimaJustificativa;

	/**
	 * The piso1 ab.
	 */
	private Double piso1AB;

	/**
	 * The piso1 ba.
	 */
	private Double piso1BA;

	/**
	 * The piso2 ab.
	 */
	private Double piso2AB;

	/**
	 * The piso2 ba.
	 */
	private Double piso2BA;

	/**
	 * The inoperante.
	 */
	@Transient
	private transient boolean inoperante;
}

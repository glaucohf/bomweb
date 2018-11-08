package br.com.fetranspor.bomweb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class LinhaVigenciaTipoDeLinha.
 */
@Entity
@Audited
@Table( name = "linha_vigencia_tipos_linha", uniqueConstraints = {@UniqueConstraint( columnNames = {"linhaVigencia_Id",
																									"tipoDeLinha_Id"} )} )
@Inheritance( strategy = InheritanceType.JOINED )
public class LinhaVigenciaTipoDeLinha
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 9018715000971785346L;

	/**
	 * Instantiates a new linha vigencia tipo de linha.
	 */
	public LinhaVigenciaTipoDeLinha()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param obj
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( final Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		final LinhaVigenciaTipoDeLinha other = ( LinhaVigenciaTipoDeLinha ) obj;
		if ( ( this.tipoDeLinha != null )
			&& ( this.linhaVigencia != null )
			&& ( other.tipoDeLinha != null )
			&& ( other.linhaVigencia != null ) )
		{
			if ( ( this.tipoDeLinha.getId() != null )
				&& this.tipoDeLinha.getId().equals( other.getTipoDeLinha().getId() )
				&& ( this.linhaVigencia.getId() != null )
				&& this.linhaVigencia.getId().equals( other.getLinhaVigencia().getId() ) )
			{
				return true;
			}
		}
		return false;
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
	 * Gets the linha vigencia.
	 *
	 * @return the linha vigencia
	 */
	public LinhaVigencia getLinhaVigencia()
	{
		return this.linhaVigencia;
	}

	/**
	 * Gets the tipo de linha.
	 *
	 * @return the tipo de linha
	 */
	public TipoDeLinha getTipoDeLinha()
	{
		return this.tipoDeLinha;
	}

	/**
	 * Gets the tipos de linha tipos de veiculo.
	 *
	 * @return the tipos de linha tipos de veiculo
	 */
	public List<TipoDeLinhaTipoDeVeiculo> getTiposDeLinhaTiposDeVeiculo()
	{
		return this.tiposLinhaTiposVeiculo;
	}

	/**
	 * Gets the tipos de veiculo permitidos.
	 *
	 * @return the tipos de veiculo permitidos
	 */
	public List<TipoDeVeiculo> getTiposDeVeiculoPermitidos()
	{
		return this.tipoDeLinha.getTiposDeVeiculoPermitidos();
	}

	/**
	 * Gets the tipos de veiculo utilizados.
	 *
	 * @return the tipos de veiculo utilizados
	 */
	public List<TipoDeVeiculo> getTiposDeVeiculoUtilizados()
	{
		final List<TipoDeVeiculo> tiposVeiculo = new ArrayList<TipoDeVeiculo>();
		if ( this.tiposLinhaTiposVeiculo != null )
		{
			for ( final TipoDeLinhaTipoDeVeiculo tlTipoVeiculo : this.tiposLinhaTiposVeiculo )
			{
				tiposVeiculo.add( tlTipoVeiculo.getTipoDeVeiculo() );
			}
		}

		return tiposVeiculo;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final InformacoesLog tipoDeLinha = getTipoDeLinha();
		final InformacoesLog linhaVigencia = getLinhaVigencia();

		final int prime = 31;

		final int hash1 = getBeanHash( tipoDeLinha );
		final int hash2 = getBeanHash( linhaVigencia );

		final int result = prime + ( prime * hash1 ) + ( prime * hash2 );
		return result;
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
	 * Sets the tipo de linha.
	 *
	 * @param tipoDeLinha
	 *            the new tipo de linha
	 */
	public void setTipoDeLinha( final TipoDeLinha tipoDeLinha )
	{
		this.tipoDeLinha = tipoDeLinha;
	}

	/**
	 * Sets the tipos de linha tipos de veiculo.
	 *
	 * @param tiposDeVeiculoUtilizados
	 *            the new tipos de linha tipos de veiculo
	 */
	public void setTiposDeLinhaTiposDeVeiculo( final List<TipoDeLinhaTipoDeVeiculo> tiposDeVeiculoUtilizados )
	{
		this.tiposLinhaTiposVeiculo = tiposDeVeiculoUtilizados;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The tipo de linha.
	 */
	@NotNull
	@NotAudited
	@ManyToOne
	private TipoDeLinha tipoDeLinha;

	/**
	 * The linha vigencia.
	 */
	@NotNull
	@ManyToOne( fetch = FetchType.LAZY )
	@NotAudited
	private LinhaVigencia linhaVigencia;

	/**
	 * The tipos linha tipos veiculo.
	 */
	@ManyToMany( cascade = {CascadeType.PERSIST} )
	@JoinTable( joinColumns = @JoinColumn( name = "linha_tipo_linha_id" ), inverseJoinColumns = @JoinColumn( name = "tipo_linha_tipo_veiculo_id" ) )
	private List<TipoDeLinhaTipoDeVeiculo> tiposLinhaTiposVeiculo;
}

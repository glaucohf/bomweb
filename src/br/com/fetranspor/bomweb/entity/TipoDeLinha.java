package br.com.fetranspor.bomweb.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class TipoDeLinha.
 */
@Entity
@Audited
@Table( name = "tipos_linha" )
@Inheritance( strategy = InheritanceType.JOINED )
public class TipoDeLinha
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 9018715000971785346L;

	/**
	 * Instantiates a new tipo de linha.
	 */
	public TipoDeLinha()
	{
		super();
	}

	/**
	 * Instantiates a new tipo de linha.
	 *
	 * @param id
	 *            the id
	 * @param sigla
	 *            the sigla
	 * @param descricao
	 *            the descricao
	 * @param tiposDeLinhaTiposVeiculo
	 *            the tipos de linha tipos veiculo
	 */
	public TipoDeLinha(
		final Long id,
		final String sigla,
		final String descricao,
		final List<TipoDeLinhaTipoDeVeiculo> tiposDeLinhaTiposVeiculo )
	{
		super();
		this.id = id;
		this.sigla = sigla;
		this.descricao = descricao;
		this.tiposDeLinhaTiposVeiculo = tiposDeLinhaTiposVeiculo;
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
		if ( !super.equals( obj ) )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		final TipoDeLinha other = ( TipoDeLinha ) obj;
		if ( ( getId() != null ) && getId().equals( other.getId() ) )
		{
			return true;
		}
		if ( this.sigla == null )
		{
			if ( other.sigla != null )
			{
				return false;
			}
		}
		else if ( !this.sigla.equals( other.sigla ) )
		{
			return false;
		}
		return true;
	}

	/**
	 * Gets the descricao.
	 *
	 * @return the descricao
	 */
	public String getDescricao()
	{
		return this.descricao;
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

	// ----------------------------------------------------------------------

	/**
	 * Gets the listagem tipos de veiculo permitidos.
	 *
	 * @return the listagem tipos de veiculo permitidos
	 */
	public String getListagemTiposDeVeiculoPermitidos()
	{
		final StringBuffer listagem = new StringBuffer();
		final List<TipoDeVeiculo> tiposVeiculo = getTiposDeVeiculoPermitidos();

		if ( ( tiposVeiculo != null ) && !tiposVeiculo.isEmpty() )
		{
			for ( final TipoDeVeiculo tipo : tiposVeiculo )
			{
				listagem.append( tipo );
				listagem.append( ", " );
			}
			listagem.delete( listagem.length() - 2, listagem.length() );
		}

		return listagem.toString();
	}

	/**
	 * Gets the sigla.
	 *
	 * @return the sigla
	 */
	public String getSigla()
	{
		return this.sigla;
	}

	// ----------------------------------------------------------------------

	/**
	 * Gets the tipos de linha tipos veiculo.
	 *
	 * @return the tipos de linha tipos veiculo
	 */
	public List<TipoDeLinhaTipoDeVeiculo> getTiposDeLinhaTiposVeiculo()
	{
		return this.tiposDeLinhaTiposVeiculo;
	}

	/**
	 * Gets the tipos de veiculo permitidos.
	 *
	 * @return the tipos de veiculo permitidos
	 */
	public List<TipoDeVeiculo> getTiposDeVeiculoPermitidos()
	{
		final List<TipoDeVeiculo> tiposVeiculo = new ArrayList<TipoDeVeiculo>();

		if ( this.tiposDeLinhaTiposVeiculo != null )
		{
			for ( final TipoDeLinhaTipoDeVeiculo tlTipoDeVeiculo : this.tiposDeLinhaTiposVeiculo )
			{
				if ( tlTipoDeVeiculo.isActive() )
				{
					tiposVeiculo.add( tlTipoDeVeiculo.getTipoDeVeiculo() );
				}
			}
		}

		return tiposVeiculo;
	}

	/**
	 * Gets the tipos de veiculo utilizados.
	 *
	 * @param linhaVigente
	 *            the linha vigente
	 * @return the tipos de veiculo utilizados
	 */
	public List<TipoDeVeiculo> getTiposDeVeiculoUtilizados( final LinhaVigencia linhaVigente )
	{
		final List<TipoDeVeiculo> tiposVeiculo = new ArrayList<TipoDeVeiculo>();
		final List<LinhaVigenciaTipoDeLinha> lvTiposLinha = linhaVigente.getLinhasVigenciaTiposDeLinha();

		if ( lvTiposLinha != null )
		{
			for ( final LinhaVigenciaTipoDeLinha lvTipoLinha : lvTiposLinha )
			{
				if ( lvTipoLinha.isActive() && equals( lvTipoLinha.getTipoDeLinha() ) )
				{
					tiposVeiculo.addAll( lvTipoLinha.getTiposDeVeiculoUtilizados() );
				}
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
		final int prime = 31;
		int result = super.hashCode();
		result = ( prime * result ) + ( ( this.sigla == null ) ? 0 : this.sigla.hashCode() );
		return result;
	}

	/**
	 * Checks if is selecionado.
	 *
	 * @return true, if is selecionado
	 */
	public boolean isSelecionado()
	{
		return this.selecionado;
	}

	/**
	 * Sets the descricao.
	 *
	 * @param descricao
	 *            the new descricao
	 */
	public void setDescricao( final String descricao )
	{
		this.descricao = descricao;
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
	 * Sets the selecionado.
	 *
	 * @param selecionado
	 *            the new selecionado
	 */
	public void setSelecionado( final boolean selecionado )
	{
		this.selecionado = selecionado;
	}

	/**
	 * Sets the sigla.
	 *
	 * @param sigla
	 *            the new sigla
	 */
	public void setSigla( final String sigla )
	{
		this.sigla = sigla;
	}

	/**
	 * Sets the tipos de linha tipos veiculo.
	 *
	 * @param tiposDeLinhaTiposVeiculo
	 *            the new tipos de linha tipos veiculo
	 */
	public void setTiposDeLinhaTiposVeiculo( final List<TipoDeLinhaTipoDeVeiculo> tiposDeLinhaTiposVeiculo )
	{
		this.tiposDeLinhaTiposVeiculo = tiposDeLinhaTiposVeiculo;
	}

	/**
	 * Sets the tipos de veiculo permitidos.
	 *
	 * @param tiposVeiculo
	 *            the new tipos de veiculo permitidos
	 */
	public void setTiposDeVeiculoPermitidos( final List<TipoDeVeiculo> tiposVeiculo )
	{
		final List<TipoDeVeiculo> tiposDeVeiculoPermitidos = getTiposDeVeiculoPermitidos();

		this.tiposDeLinhaTiposVeiculo = new ArrayList<TipoDeLinhaTipoDeVeiculo>();

		if ( tiposVeiculo != null )
		{
			for ( final TipoDeVeiculo tipoDeVeiculo : tiposVeiculo )
			{
				if ( !tiposDeVeiculoPermitidos.contains( tipoDeVeiculo ) )
				{
					final TipoDeLinhaTipoDeVeiculo tltv = new TipoDeLinhaTipoDeVeiculo( tipoDeVeiculo, this );
					tltv.setActive( Boolean.TRUE );
					this.tiposDeLinhaTiposVeiculo.add( tltv );
				}
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getSigla() + "-" + getDescricao();
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The sigla.
	 */
	@NotNull
	private String sigla;

	/**
	 * The descricao.
	 */
	@NotNull
	private String descricao;

	/**
	 * The tipos de linha tipos veiculo.
	 */
	@NotAudited
	@OneToMany( mappedBy = "tipoDeLinha", cascade = CascadeType.ALL )
	private List<TipoDeLinhaTipoDeVeiculo> tiposDeLinhaTiposVeiculo;

	/**
	 * The selecionado.
	 */
	@Transient
	private transient boolean selecionado = Boolean.FALSE;
}

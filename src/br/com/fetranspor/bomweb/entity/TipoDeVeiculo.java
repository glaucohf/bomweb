package br.com.fetranspor.bomweb.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.envers.Audited;

/**
 * The Class TipoDeVeiculo.
 */
@Entity
@Audited
@Table( name = "tipos_veiculo" )
@Inheritance( strategy = InheritanceType.JOINED )
public class TipoDeVeiculo
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 9018715000971785346L;

	/**
	 * Instantiates a new tipo de veiculo.
	 */
	public TipoDeVeiculo()
	{
		super();
	}

	/**
	 * Instantiates a new tipo de veiculo.
	 *
	 * @param id
	 *            the id
	 * @param descricao
	 *            the descricao
	 * @param selecionado
	 *            the selecionado
	 */
	public TipoDeVeiculo( final Long id, final String descricao, final boolean selecionado )
	{
		super();
		this.id = id;
		this.descricao = descricao;
		this.selecionado = selecionado;
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
		final TipoDeVeiculo other = ( TipoDeVeiculo ) obj;
		if ( ( this.id != null ) && this.id.equals( other.getId() ) )
		{
			return true;
		}
		if ( this.codigo == null )
		{
			if ( other.codigo != null )
			{
				return false;
			}
		}
		else if ( !this.codigo.equals( other.codigo ) )
		{
			return false;
		}
		return true;
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo()
	{
		return this.codigo;
	}

	/**
	 * Gets the data criacao.
	 *
	 * @return the data criacao
	 */
	public Date getDataCriacao()
	{
		return this.dataCriacao;
	}

	// ----------------------------------------------------------------------

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
		result = ( prime * result ) + ( ( this.codigo == null ) ? 0 : this.codigo.hashCode() );
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
	 * Sets the codigo.
	 *
	 * @param codigo
	 *            the new codigo
	 */
	public void setCodigo( final String codigo )
	{
		this.codigo = codigo;
	}

	/**
	 * Sets the data criacao.
	 *
	 * @param dataCriacao
	 *            the new data criacao
	 */
	public void setDataCriacao( final Date dataCriacao )
	{
		this.dataCriacao = dataCriacao;
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
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getDescricao();
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The codigo.
	 */
	@NotNull
	private String codigo;

	/**
	 * The descricao.
	 */
	@NotNull
	private String descricao;

	/**
	 * The data criacao.
	 */
	@Column( name = "data_criacao" )
	private Date dataCriacao;

	/**
	 * The selecionado.
	 */
	@Transient
	private transient boolean selecionado = Boolean.FALSE;

}

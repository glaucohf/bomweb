package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.PermissionAction;
import br.com.decatron.framework.security.annotation.PermissionClass;
import br.com.decatron.framework.security.annotation.PermissionTarget;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class Permissao.
 */
@Entity
@Table( name = "permissoes" )
@PermissionClass
public class Permissao
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -3720345070302364898L;

	/**
	 * Instantiates a new permissao.
	 */
	public Permissao()
	{
		super();
	}

	/**
	 * Instantiates a new permissao.
	 *
	 * @param recurso
	 *            the recurso
	 * @param acao
	 *            the acao
	 */
	public Permissao( final String recurso, final String acao )
	{
		super();
		this.recurso = recurso;
		this.acao = acao;
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
		if ( obj == null )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		final Permissao other = ( Permissao ) obj;
		if ( ( this.id != null ) && ( other.id != null ) && this.id.equals( other.id ) )
		{
			return true;
		}
		if ( this.acao == null )
		{
			if ( other.acao != null )
			{
				return false;
			}
		}
		else if ( !this.acao.equals( other.acao ) )
		{
			return false;
		}
		if ( this.recurso == null )
		{
			if ( other.recurso != null )
			{
				return false;
			}
		}
		else if ( !this.recurso.equals( other.recurso ) )
		{
			return false;
		}
		return true;
	}

	// --------------------------------------------------------------------------------

	/**
	 * Gets the acao.
	 *
	 * @return the acao
	 */
	public String getAcao()
	{
		return this.acao;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public Integer getId()
	{
		return this.id;
	}

	// --------------------------------------------------------------------------------

	/**
	 * Gets the recurso.
	 *
	 * @return the recurso
	 */
	public String getRecurso()
	{
		return this.recurso;
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
		int result = 1;
		result = ( prime * result ) + ( ( this.acao == null ) ? 0 : this.acao.hashCode() );
		result = ( prime * result ) + ( ( this.recurso == null ) ? 0 : this.recurso.hashCode() );
		return result;
	}

	/**
	 * Sets the acao.
	 *
	 * @param acao
	 *            the new acao
	 */
	public void setAcao( final String acao )
	{
		this.acao = acao;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final Integer id )
	{
		this.id = id;
	}

	/**
	 * Sets the recurso.
	 *
	 * @param recurso
	 *            the new recurso
	 */
	public void setRecurso( final String recurso )
	{
		this.recurso = recurso;
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
		return this.recurso + "." + this.acao;
	}

	// --------------------------------------------------------------------------------

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * The recurso.
	 */
	@PermissionTarget
	@NotNull
	private String recurso;

	/**
	 * The acao.
	 */
	@PermissionAction
	@NotNull
	private String acao;

}

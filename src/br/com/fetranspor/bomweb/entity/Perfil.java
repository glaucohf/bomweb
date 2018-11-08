package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.RoleClass;
import br.com.decatron.framework.security.annotation.RoleName;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class Perfil.
 */
@Entity
@Table( name = "perfis" )
@RoleClass
public class Perfil
{

	/**
	 * Instantiates a new perfil.
	 */
	public Perfil()
	{
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
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
		final Perfil other = ( Perfil ) obj;
		if ( this.id == null )
		{
			if ( other.id != null )
			{
				return false;
			}
		}
		else if ( !this.id.equals( other.id ) )
		{
			return false;
		}
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome()
	{
		return this.nome;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = ( prime * result ) + ( ( this.id == null ) ? 0 : this.id.hashCode() );
		return result;
	}

	/**
	 * Checks if is detro.
	 *
	 * @return true, if is detro
	 */
	public boolean isDetro()
	{
		return getId().equals( 1 );
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public boolean isDetroAdmin()
	{
		return getId().equals( 4 );
	}

	/**
	 * Checks if is empresa.
	 *
	 * @return true, if is empresa
	 */
	public boolean isEmpresa()
	{
		return getId().equals( 2 );
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
	 * Sets the nome.
	 *
	 * @param nome
	 *            the new nome
	 */
	public void setNome( final String nome )
	{
		this.nome = nome;
	}

	/**
	 * The id.
	 */
	@Id
	private Integer id;

	/**
	 * The nome.
	 */
	@RoleName
	@NotNull
	private String nome;

}

package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.RolePermissionClass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class PerfilPermissao.
 */
@RolePermissionClass
@Entity
@Table( name = "perfis_permissoes" )
public class PerfilPermissao
	extends InformacoesLog
{

	/**
	 * The Class PerfilPermissaoPK.
	 */
	@Embeddable
	public static class PerfilPermissaoPK
		implements Serializable
	{

		/**
		 * The Constant serialVersionUID.
		 */
		private static final long serialVersionUID = -4319804503439331520L;

		/**
		 * Gets the id perfil.
		 *
		 * @return the id perfil
		 */
		public Integer getIdPerfil()
		{
			return this.idPerfil;
		}

		/**
		 * Gets the id permissao.
		 *
		 * @return the id permissao
		 */
		public Integer getIdPermissao()
		{
			return this.idPermissao;
		}

		/**
		 * Sets the id perfil.
		 *
		 * @param idPerfil
		 *            the new id perfil
		 */
		public void setIdPerfil( final Integer idPerfil )
		{
			this.idPerfil = idPerfil;
		}

		/**
		 * Sets the id permissao.
		 *
		 * @param idPermissao
		 *            the new id permissao
		 */
		public void setIdPermissao( final Integer idPermissao )
		{
			this.idPermissao = idPermissao;
		}

		/**
		 * The id perfil.
		 */
		@NotNull
		@Column( name = "id_perfil" )
		private Integer idPerfil;

		/**
		 * The id permissao.
		 */
		@NotNull
		@Column( name = "id_permissao" )
		private Integer idPermissao;

	}

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -2614098193169725526L;

	/**
	 * Instantiates a new perfil permissao.
	 */
	public PerfilPermissao()
	{
		super();
	}

	/**
	 * Instantiates a new perfil permissao.
	 *
	 * @param idPerfil
	 *            the id perfil
	 * @param idPermissao
	 *            the id permissao
	 */
	public PerfilPermissao( final Integer idPerfil, final Integer idPermissao )
	{
		super();
		final PerfilPermissaoPK pk = new PerfilPermissaoPK();
		pk.idPerfil = idPerfil;
		pk.idPermissao = idPermissao;
		setId( pk );
	}

	/**
	 * Instantiates a new perfil permissao.
	 *
	 * @param perfil
	 *            the perfil
	 * @param permissao
	 *            the permissao
	 */
	public PerfilPermissao( final Perfil perfil, final Permissao permissao )
	{
		this( perfil.getId(), permissao.getId() );
		this.perfil = perfil;
		this.permissao = permissao;
	}

	// --------------------------------------------------------------------------------

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
		final PerfilPermissao other = ( PerfilPermissao ) obj;
		if ( this.perfil == null )
		{
			if ( other.perfil != null )
			{
				return false;
			}
		}
		else if ( !this.perfil.equals( other.perfil ) )
		{
			return false;
		}
		if ( this.permissao == null )
		{
			if ( other.permissao != null )
			{
				return false;
			}
		}
		else if ( !this.permissao.equals( other.permissao ) )
		{
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public PerfilPermissaoPK getId()
	{
		return this.id;
	}

	/**
	 * Gets the perfil.
	 *
	 * @return the perfil
	 */
	public Perfil getPerfil()
	{
		return this.perfil;
	}

	// --------------------------------------------------------------------------------

	/**
	 * Gets the permissao.
	 *
	 * @return the permissao
	 */
	public Permissao getPermissao()
	{
		return this.permissao;
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
		result = ( prime * result ) + ( ( this.perfil == null ) ? 0 : this.perfil.hashCode() );
		result = ( prime * result ) + ( ( this.permissao == null ) ? 0 : this.permissao.hashCode() );
		return result;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final PerfilPermissaoPK id )
	{
		this.id = id;
	}

	/**
	 * Sets the perfil.
	 *
	 * @param perfil
	 *            the new perfil
	 */
	public void setPerfil( final Perfil perfil )
	{
		this.perfil = perfil;
	}

	/**
	 * Sets the permissao.
	 *
	 * @param permissao
	 *            the new permissao
	 */
	public void setPermissao( final Permissao permissao )
	{
		this.permissao = permissao;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.entity.InformacoesLog#tripao()
	 */
	@Override
	public String tripao()
	{
		final StringBuffer buffer = new StringBuffer();
		buffer.append( "PERFIL:" + this.perfil.getNome() + SEPARADOR );
		buffer.append( "PERMISSÃO:" + this.permissao + SEPARADOR );

		return buffer.toString();
	}

	// --------------------------------------------------------------------------------

	/**
	 * The id.
	 */
	@EmbeddedId
	private PerfilPermissaoPK id;

	/**
	 * The perfil.
	 */
	@ManyToOne
	@JoinColumn( name = "id_perfil", insertable = false, updatable = false )
	private Perfil perfil;

	/**
	 * The permissao.
	 */
	@ManyToOne
	@JoinColumn( name = "id_permissao", insertable = false, updatable = false )
	private Permissao permissao;

}

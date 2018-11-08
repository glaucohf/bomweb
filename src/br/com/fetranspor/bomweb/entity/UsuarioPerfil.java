package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.UserRoleClass;

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
 * The Class UsuarioPerfil.
 */
@UserRoleClass
@Entity
@Table( name = "usuarios_perfis" )
public class UsuarioPerfil
	extends InformacoesLog
{

	/**
	 * The Class UsuarioPerfilPK.
	 */
	@Embeddable
	public static class UsuarioPerfilPK
		implements Serializable
	{

		/**
		 * The Constant serialVersionUID.
		 */
		private static final long serialVersionUID = -4319804503439331520L;

		/**
		 * Instantiates a new usuario perfil pk.
		 */
		public UsuarioPerfilPK()
		{
		}

		/**
		 * Instantiates a new usuario perfil pk.
		 *
		 * @param idUsuario
		 *            the id usuario
		 * @param idPerfil
		 *            the id perfil
		 */
		public UsuarioPerfilPK( final Integer idUsuario, final Integer idPerfil )
		{
			this.idUsuario = idUsuario;
			this.idPerfil = idPerfil;
		}

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
		 * Gets the id usuario.
		 *
		 * @return the id usuario
		 */
		public Integer getIdUsuario()
		{
			return this.idUsuario;
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
		 * Sets the id usuario.
		 *
		 * @param idUsuario
		 *            the new id usuario
		 */
		public void setIdUsuario( final Integer idUsuario )
		{
			this.idUsuario = idUsuario;
		}

		/**
		 * The id usuario.
		 */
		@NotNull
		@Column( name = "id_usuario" )
		private Integer idUsuario;

		/**
		 * The id perfil.
		 */
		@NotNull
		@Column( name = "id_perfil" )
		private Integer idPerfil;

	}

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -5587345850291534254L;

	/**
	 * Instantiates a new usuario perfil.
	 */
	public UsuarioPerfil()
	{
		super();
	}

	/**
	 * Instantiates a new usuario perfil.
	 *
	 * @param idUsuario
	 *            the id usuario
	 * @param idPerfil
	 *            the id perfil
	 */
	public UsuarioPerfil( final Integer idUsuario, final Integer idPerfil )
	{
		super();
		this.id = new UsuarioPerfilPK( idUsuario, idPerfil );
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
		final UsuarioPerfil other = ( UsuarioPerfil ) obj;
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
		if ( this.usuario == null )
		{
			if ( other.usuario != null )
			{
				return false;
			}
		}
		else if ( !this.usuario.equals( other.usuario ) )
		{
			return false;
		}
		return true;
	}

	// ----------------------------------------------------------------------

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public UsuarioPerfilPK getId()
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

	// ----------------------------------------------------------------------

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public Usuario getUsuario()
	{
		return this.usuario;
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
		result = ( prime * result ) + ( ( this.usuario == null ) ? 0 : this.usuario.hashCode() );
		return result;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final UsuarioPerfilPK id )
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
	 * Sets the usuario.
	 *
	 * @param usuario
	 *            the new usuario
	 */
	public void setUsuario( final Usuario usuario )
	{
		this.usuario = usuario;
	}

	/**
	 * The id.
	 */
	@EmbeddedId
	private UsuarioPerfilPK id;

	// ----------------------------------------------------------------------

	/**
	 * The usuario.
	 */
	@ManyToOne
	@JoinColumn( name = "id_usuario", insertable = false, updatable = false )
	private Usuario usuario;

	/**
	 * The perfil.
	 */
	@ManyToOne
	@JoinColumn( name = "id_perfil", insertable = false, updatable = false )
	private Perfil perfil;

}

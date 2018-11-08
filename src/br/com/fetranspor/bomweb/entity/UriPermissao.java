package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.UriPermissionClass;

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
 * The Class UriPermissao.
 */
@UriPermissionClass
@Entity
@Table( name = "uris_permissao" )
public class UriPermissao
{

	/**
	 * The Class UriPermissaoPK.
	 */
	@Embeddable
	public static class UriPermissaoPK
		implements Serializable
	{

		/**
		 * The Constant serialVersionUID.
		 */
		private static final long serialVersionUID = -4319804503439331520L;

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
		 * Gets the id uri.
		 *
		 * @return the id uri
		 */
		public Integer getIdUri()
		{
			return this.idUri;
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
		 * Sets the id uri.
		 *
		 * @param idUri
		 *            the new id uri
		 */
		public void setIdUri( final Integer idUri )
		{
			this.idUri = idUri;
		}

		/**
		 * The id uri.
		 */
		@NotNull
		@Column( name = "id_uri" )
		private Integer idUri;

		/**
		 * The id permissao.
		 */
		@NotNull
		@Column( name = "id_permissao" )
		private Integer idPermissao;

	}

	/**
	 * Instantiates a new uri permissao.
	 */
	public UriPermissao()
	{
	}

	/**
	 * Instantiates a new uri permissao.
	 *
	 * @param idUri
	 *            the id uri
	 * @param idPermissao
	 *            the id permissao
	 */
	public UriPermissao( final Integer idUri, final Integer idPermissao )
	{
		final UriPermissaoPK pk = new UriPermissaoPK();
		pk.idUri = idUri;
		pk.idPermissao = idPermissao;
		setId( pk );
	}

	/**
	 * Instantiates a new uri permissao.
	 *
	 * @param uri
	 *            the uri
	 * @param permissao
	 *            the permissao
	 */
	public UriPermissao( final Uri uri, final Permissao permissao )
	{
		this( uri.getId(), permissao.getId() );
		this.uri = uri;
		this.permissao = permissao;
	}

	// --------------------------------------------------------------------------------

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
		final UriPermissao other = ( UriPermissao ) obj;
		if ( this.uri == null )
		{
			if ( other.uri != null )
			{
				return false;
			}
		}
		else if ( !this.uri.equals( other.uri ) )
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
	 * Gets the id.
	 *
	 * @return the id
	 */
	public UriPermissaoPK getId()
	{
		return this.id;
	}

	/**
	 * Gets the permissao.
	 *
	 * @return the permissao
	 */
	public Permissao getPermissao()
	{
		return this.permissao;
	}

	// --------------------------------------------------------------------------------

	/**
	 * Gets the uri.
	 *
	 * @return the uri
	 */
	public Uri getUri()
	{
		return this.uri;
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
		result = ( prime * result ) + ( ( this.uri == null ) ? 0 : this.uri.hashCode() );
		result = ( prime * result ) + ( ( this.permissao == null ) ? 0 : this.permissao.hashCode() );
		return result;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final UriPermissaoPK id )
	{
		this.id = id;
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
	 * Sets the uri.
	 *
	 * @param uri
	 *            the new uri
	 */
	public void setUri( final Uri uri )
	{
		this.uri = uri;
	}

	/**
	 * The id.
	 */
	@EmbeddedId
	private UriPermissaoPK id;

	// --------------------------------------------------------------------------------

	/**
	 * The uri.
	 */
	@ManyToOne
	@JoinColumn( name = "id_uri", insertable = false, updatable = false )
	private Uri uri;

	/**
	 * The permissao.
	 */
	@ManyToOne
	@JoinColumn( name = "id_permissao", insertable = false, updatable = false )
	private Permissao permissao;

}

package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.security.annotation.UriClass;
import br.com.decatron.framework.security.annotation.UriOpen;
import br.com.decatron.framework.security.annotation.UriPath;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * The Class Uri.
 */
@UriClass
@Entity( )
@Table( name = "uris" )
public class Uri
{

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
	 * Gets the path.
	 *
	 * @return the path
	 */
	public String getPath()
	{
		return this.path;
	}

	/**
	 * Checks if is open.
	 *
	 * @return true, if is open
	 */
	public boolean isOpen()
	{
		return this.open;
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
	 * Sets the open.
	 *
	 * @param open
	 *            the new open
	 */
	public void setOpen( final boolean open )
	{
		this.open = open;
	}

	/**
	 * Sets the path.
	 *
	 * @param uri
	 *            the new path
	 */
	public void setPath( final String uri )
	{
		this.path = uri;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Integer id;

	/**
	 * The path.
	 */
	@UriPath
	@NotNull
	private String path;

	/**
	 * The open.
	 */
	@UriOpen
	@NotNull
	private boolean open = false;

}

package br.com.fetranspor.bomweb.exception;

/**
 * <p>
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 17/07/2015
 */
public class OrphanBomLinhaException
	extends IllegalStateException
{

	/**
	 * <p>
	 * Field <code>serialVersionUID</code>
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * </p>
	 */
	public OrphanBomLinhaException()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param s
	 */
	public OrphanBomLinhaException( final String s )
	{
		super( s );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param message
	 * @param cause
	 */
	public OrphanBomLinhaException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param cause
	 */
	public OrphanBomLinhaException( final Throwable cause )
	{
		super( cause );
	}

}

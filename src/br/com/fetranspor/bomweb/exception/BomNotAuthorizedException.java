package br.com.fetranspor.bomweb.exception;

/**
 * <p>
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 17/07/2015
 */
public class BomNotAuthorizedException
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
	public BomNotAuthorizedException()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param s
	 */
	public BomNotAuthorizedException( final String s )
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
	public BomNotAuthorizedException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param cause
	 */
	public BomNotAuthorizedException( final Throwable cause )
	{
		super( cause );
	}

}

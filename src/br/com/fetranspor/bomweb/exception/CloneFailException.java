package br.com.fetranspor.bomweb.exception;

/**
 * <p>
 * </p>
 * <p>
 * FBW-232
 * </p>
 *
 * @author fred
 * @version 1.0 Created on Feb 8, 2015
 */
public class CloneFailException
	extends RuntimeException
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
	public CloneFailException()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param message
	 */
	public CloneFailException( final String message )
	{
		super( message );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param message
	 * @param cause
	 */
	public CloneFailException( final String message, final Throwable cause )
	{
		super( message, cause );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param cause
	 */
	public CloneFailException( final Throwable cause )
	{
		super( cause );
	}

}

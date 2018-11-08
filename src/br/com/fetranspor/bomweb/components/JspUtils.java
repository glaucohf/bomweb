package br.com.fetranspor.bomweb.components;

/**
 * The Class JspUtils.
 */
public class JspUtils
{

	/**
	 * Array long contains.
	 *
	 * @param list
	 *            the list
	 * @param opt
	 *            the opt
	 * @return true, if successful
	 */
	public static boolean arrayLongContains( final Long[] list, final Long opt )
	{
		if ( ( list != null ) && ( opt != null ) )
		{
			for ( final Long element : list )
			{
				if ( element.equals( opt ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Array string contains.
	 *
	 * @param list
	 *            the list
	 * @param opt
	 *            the opt
	 * @return true, if successful
	 */
	public static boolean arrayStringContains( final String[] list, final String opt )
	{
		if ( ( list != null ) && ( opt != null ) )
		{
			for ( final String element : list )
			{
				if ( element.equals( opt ) )
				{
					return true;
				}
			}
		}
		return false;
	}
}

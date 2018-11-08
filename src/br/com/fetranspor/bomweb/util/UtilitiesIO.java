package br.com.fetranspor.bomweb.util;

import java.io.File;

/**
 * <p>
 * </p>
 * 
 * @author fred
 * @version 1.0 Created on Jul 16, 2015
 */
public class UtilitiesIO
{

	/**
	 * <p>
	 * </p>
	 */
	protected UtilitiesIO()
	{
		super();
	}

	/**
	 * Delete file.
	 *
	 * @param nome
	 *            the nome
	 */
	public static void deleteFile( final String nome )
	{
		final File arquivo = new File( nome );
		if ( arquivo.exists() )
		{
			arquivo.delete();
		}// if
	}// func

}// class

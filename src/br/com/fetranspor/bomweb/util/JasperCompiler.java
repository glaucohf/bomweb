package br.com.fetranspor.bomweb.util;

import java.util.Arrays;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * </p>
 * <p>
 * FBW-303
 * </p>
 *
 * @author fred
 * @version 1.0 Created on Feb 21, 2015
 */
public class JasperCompiler
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( JasperCompiler.class );

	/**
	 * <p>
	 * </p>
	 */
	public JasperCompiler()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param filesToConvert
	 * @throws JRException
	 */
	private static void execute( final String... filesToConvert )
		throws JRException
	{
		final String dir = "src/main/resources";
		for ( final String file : filesToConvert )
		{
			if ( LOG.isInfoEnabled() )
			{
				final String logMsg = String.format( "%s", file );
				LOG.info( logMsg );
			}// if
			final String in = String.format( "%1$s/%2$s.jrxml", dir, file );
			final String out = String.format( "%1$s/%2$s.jasper", dir, file );
			JasperCompileManager.compileReportToFile( in, out );
		}// for
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param args
	 */
	public static void main( final String... args )
	{
		if ( LOG.isInfoEnabled() )
		{
			LOG.info( "INIT" );
		}// if
		try
		{
			if ( LOG.isDebugEnabled() )
			{
				LOG.debug( String.format( "Args:\r\n%s", Arrays.asList( args ) ) );
			}// if

			execute( args );

			if ( LOG.isInfoEnabled() )
			{
				LOG.info( "END" );
			}// if

			System.exit( 0 );

		}// try
		catch ( final Throwable t )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( "UNHANDLED", t );
			}// if
			System.exit( 1 );
		}// catch
	}// func

}

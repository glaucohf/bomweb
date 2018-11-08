package br.com.fetranspor.bomweb.comparator;

import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.Empresa;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <p>
 * </p>
 * FBW-26.
 *
 * @author ryniere
 * @version 1.0 Created on Jul 16, 2013
 */
public class BomComparator
	implements Comparator<Bom>
{

	/**
	 * <p>
	 * Field <code>DATE_FORMAT</code>
	 * </p>
	 * .
	 */
	final static DateFormat DATE_FORMAT = new SimpleDateFormat( "MM/yyyy" );

	/**
	 * <p>
	 * Field <code>LOG</code>
	 * </p>
	 * .
	 */
	private static final Log LOG = LogFactory.getLog( BomComparator.class );

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param bom1
	 *            the bom1
	 * @param bom2
	 *            the bom2
	 * @return the int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare( final Bom bom1, final Bom bom2 )
	{
		final Empresa company1 = bom1.getEmpresa();
		final Empresa company2 = bom2.getEmpresa();

		final String companyName1 = company1.getNome();
		final String companyName2 = company2.getNome();
		final int companyNameCompare = companyName1.compareTo( companyName2 );
		if ( companyNameCompare == 0 )
		{
			final String referenceMonth1 = bom1.getMesReferencia();
			final String referenceMonth2 = bom2.getMesReferencia();

			try
			{
				final Date date1 = DATE_FORMAT.parse( referenceMonth1 );
				final Date date2 = DATE_FORMAT.parse( referenceMonth2 );
				return date1.compareTo( date2 );
			}
			catch ( final ParseException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( null, e );
				}
			}

		}
		return companyNameCompare;
	}
}

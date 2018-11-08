package br.com.fetranspor.bomweb.util;

import br.com.decatron.framework.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The Class DateProvider.
 */
public final class DateProvider
{

	/**
	 * The testando.
	 */
	static boolean testando = false;

	/**
	 * The hoje.
	 */
	static Date hoje = null;

	/**
	 * Gets the hoje.
	 *
	 * @return the hoje
	 */
	public static Date getHoje()
	{
		if ( testando )
		{
			return hoje;
		}
		return new Date();
	}

	/**
	 * Gets the hoje sem hora minuto e segundo.
	 *
	 * @return the hoje sem hora minuto e segundo
	 */
	public static Date getHojeSemHoraMinutoESegundo()
	{
		try
		{
			return DateUtil.convertToDate( "dd/MM/yyyy", new SimpleDateFormat( "dd/MM/yyyy" ).format( getHoje() ) );
		}
		catch ( final ParseException e )
		{
			// Na prática não ocorre.
			throw new AssertionError( e );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param mesAno
	 * @return
	 * @throws ParseException
	 */
	public static Date parseMesAnoToDate( final String mesAno )
		throws ParseException

	{
		final SimpleDateFormat sdf = new SimpleDateFormat( "MM/yyyy" );
		final Date date = sdf.parse( mesAno );
		return date;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @param quantidade
	 * @return
	 */
	public static Date subtrairDia( final Date data, final int quantidade )
	{
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime( data );
		calendar.add( Calendar.DAY_OF_MONTH, -quantidade );
		return calendar.getTime();
	}
}

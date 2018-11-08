package br.com.fetranspor.bomweb.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe utilitária de data.
 *
 * @author Bruno Vianna
 */
public class DateUtil
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( DateUtil.class );

	/**
	 * The Constant ONE_SECOND_IN_MILLIS.
	 */
	public static final long ONE_SECOND_IN_MILLIS = 1000;

	/**
	 * The Constant ONE_MINUTE_IN_SECONDS.
	 */
	public static final long ONE_MINUTE_IN_SECONDS = 60;

	/**
	 * The Constant ONE_HOUR_IN_MINUTES.
	 */
	public static final long ONE_HOUR_IN_MINUTES = 60;

	/**
	 * The Constant ONE_HOUR_IN_SECONDS.
	 */
	public static final long ONE_HOUR_IN_SECONDS = 3600;

	/**
	 * The Constant HOURS_IN_DAY.
	 */
	public static final long HOURS_IN_DAY = 24;

	/**
	 * The Constant DAYS_IN_WEEK.
	 */
	public static final int DAYS_IN_WEEK = 7;

	/**
	 * Calcula quantos dias existem entre o dia de início e o dia da semana alvo. Considera apenas
	 * uma semana. Assim, este método obrigatoriamente retorna um valor menor que 7
	 *
	 * @param beginDay
	 *            the begin day
	 * @param targetDayOfWeek
	 *            the target day of week
	 * @return the int
	 */
	public static int calculateDistanceBetweenWeekDays( final int beginDay, final int targetDayOfWeek )
	{
		if ( targetDayOfWeek > beginDay )
		{
			return targetDayOfWeek - beginDay;
		}
		return ( targetDayOfWeek + DAYS_IN_WEEK ) - beginDay;
	}

	/**
	 * Obtém um calendário na data especificada.
	 *
	 * @param date
	 *            the date
	 * @return the calendar
	 */
	public static Calendar createCalendarInstance( final Date date )
	{
		final Calendar cal = Calendar.getInstance();
		cal.setTime( date );
		return cal;
	}

	/**
	 * Cria uma data no formato SHORT default Locale (no Brasil: pattern dd/MM/yy).
	 *
	 * @param dateString
	 *            - a string de data
	 * @return the date
	 */
	public static Date createDate( final String dateString )
	{
		final DateFormat df = DateFormat.getDateInstance( DateFormat.SHORT, Locale.getDefault() );
		return createDate( dateString, df );
	}

	/**
	 * Cria uma data a partir de um DateFormat especificado.
	 *
	 * @param dateString
	 *            - a string de data
	 * @param df
	 *            - o DateFormat a ser usado na formatação
	 * @return the date
	 */
	private static Date createDate( final String dateString, final DateFormat df )
	{
		Date date = null;
		try
		{
			date = df.parse( dateString );
		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		return date;
	}

	/**
	 * Cria uma Data no pattern especificado.
	 *
	 * @param dateString
	 *            a string de data que deve necessariamente estar no formato especificado em pattern
	 * @param pattern
	 *            - o formato em que a data deve estar
	 * @return the date
	 */
	public static Date createDate( final String dateString, final String pattern )
	{
		final DateFormat df = new SimpleDateFormat( pattern );
		return createDate( dateString, df );
	}

	/**
	 * Data diff.
	 *
	 * @param dataLow
	 *            the data low
	 * @param dataHigh
	 *            the data high
	 * @return the int
	 */
	public static int dataDiff( final java.util.Date dataLow, final java.util.Date dataHigh )
	{

		final GregorianCalendar startTime = new GregorianCalendar();
		final GregorianCalendar endTime = new GregorianCalendar();

		final GregorianCalendar curTime = new GregorianCalendar();
		final GregorianCalendar baseTime = new GregorianCalendar();

		startTime.setTime( dataLow );
		endTime.setTime( dataHigh );

		int dif_multiplier = 1;

		if ( dataLow.compareTo( dataHigh ) < 0 )
		{
			baseTime.setTime( dataHigh );
			curTime.setTime( dataLow );
			dif_multiplier = 1;
		}
		else
		{
			baseTime.setTime( dataLow );
			curTime.setTime( dataHigh );
			dif_multiplier = -1;
		}

		final int result_years = 0;
		int result_months = 0;
		int result_days = 0;

		while ( ( curTime.get( GregorianCalendar.YEAR ) < baseTime.get( GregorianCalendar.YEAR ) )
			|| ( curTime.get( GregorianCalendar.MONTH ) < baseTime.get( GregorianCalendar.MONTH ) ) )
		{

			final int max_day = curTime.getActualMaximum( GregorianCalendar.DAY_OF_MONTH );
			result_months += max_day;
			curTime.add( GregorianCalendar.MONTH, 1 );

		}

		result_months = result_months * dif_multiplier;

		result_days += ( endTime.get( GregorianCalendar.DAY_OF_MONTH ) - startTime.get( GregorianCalendar.DAY_OF_MONTH ) );

		return result_years + result_months + result_days;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param date
	 *            Data que será formatada.
	 * @return Retorna o mês e ano da data enviada no formato MM/yyyy
	 */
	public static String getMonthAndYearAsString( final Date date )
	{
		final Calendar cal = Calendar.getInstance();
		cal.setTime( date );
		final int month = cal.get( Calendar.MONTH ) + 1;
		final int year = cal.get( Calendar.YEAR );
		final String monthYear = String.format( "%s/%s", month, year );
		return monthYear;
	}

	/**
	 * Java time begin.
	 *
	 * @return the date
	 */
	public static Date javaTimeBegin()
	{
		final Date date = createDate( "01/01/70 00:00:00:000", "dd/MM/yy HH:mm:ss:SSS" );
		return date;
	}

	/**
	 * Retorna a data que representa o instante de invocação deste método.
	 *
	 * @return the date
	 */
	public static Date now()
	{
		return Calendar.getInstance().getTime();
	}

	/**
	 * Zera a porção de tempo (hora, minuto, segundo e milisegundo) do calendário passado.
	 *
	 * @param cal
	 *            the cal
	 * @return the calendar
	 */
	public static Calendar zeroTime( final Calendar cal )
	{
		cal.set( Calendar.HOUR_OF_DAY, 0 );
		cal.set( Calendar.MINUTE, 0 );
		cal.set( Calendar.SECOND, 0 );
		cal.set( Calendar.MILLISECOND, 0 );
		return cal;
	}

}

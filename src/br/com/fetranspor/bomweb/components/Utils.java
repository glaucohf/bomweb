package br.com.fetranspor.bomweb.components;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * The Class Utils.
 */
public class Utils
{

	/**
	 * <p>
	 * Field <code>LOCALE_BR</code>
	 * </p>
	 */
	public static final Locale LOCALE_BR = new Locale( "pt", "BR" );

	/**
	 * <p>
	 * Field <code>DATE_MY_FORMAT</code>
	 * </p>
	 */
	public static final DateFormat DATE_MY_FORMAT = new SimpleDateFormat( "MM/yyyy", LOCALE_BR );

	/**
	 * <p>
	 * Field <code>DATE_DMY_FORMAT</code>
	 * </p>
	 */
	public static final DateFormat DATE_DMY_FORMAT = new SimpleDateFormat( "dd/MM/yyyy", LOCALE_BR );

	/**
	 * Compara data com dataBase, fazendo data.compareTo(dataBase), após setar as informações de
	 * hora para meia-noite.
	 *
	 * @param data
	 *            the data
	 * @param dataBase
	 *            the data base
	 * @return data.compareTo(dataBase)
	 */
	public static int compareToSemHora( final Date data, final Date dataBase )
	{
		final Date dataSemHora = setHoraParaMeiaNoite( data );
		final Date dataBaseSemHora = setHoraParaMeiaNoite( dataBase );

		return dataSemHora.compareTo( dataBaseSemHora );
	}

	/**
	 * Sets the hora para meia noite.
	 *
	 * @param date
	 *            the date
	 * @return the date
	 */
	public static Date setHoraParaMeiaNoite( final Date date )
	{
		final Calendar calendar = Calendar.getInstance();

		calendar.setTime( date );
		calendar.set( Calendar.HOUR_OF_DAY, 0 );
		calendar.set( Calendar.MINUTE, 0 );
		calendar.set( Calendar.SECOND, 0 );
		calendar.set( Calendar.MILLISECOND, 0 );

		return calendar.getTime();
	}

	/**
	 * Transforma uma string no formato camelCase para uma string separada por espacos com a
	 * primeira inicial de cada palavra maiuscula.
	 *
	 * @param s
	 *            String escrita em camelCase
	 * @return String separada por espacos com as iniciais maisculas
	 */
	public static String splitCamelCase( final String s )
	{

		final String string = s.replaceAll( String.format(
			"%s|%s|%s",
			"(?<=[A-Z])(?=[A-Z][a-z])",
			"(?<=[^A-Z])(?=[A-Z])",
			"(?<=[A-Za-z])(?=[^A-Za-z])" ), " " );

		return string.substring( 0, 1 ).toUpperCase() + string.substring( 1 );
	}
}

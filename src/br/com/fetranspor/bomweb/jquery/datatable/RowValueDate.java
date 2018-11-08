package br.com.fetranspor.bomweb.jquery.datatable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 * </p>
 * <p>
 * FBW-354
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 14/10/2015
 */
public class RowValueDate
	implements Comparable
{

	/**
	 * <p>
	 * </p>
	 *
	 * @param date
	 * @param dateFormat
	 */
	public RowValueDate( final Date date, final DateFormat dateFormat )
	{
		super();
		final String dateStr;
		if ( date == null )
		{
			dateStr = null;
		}// if
		else
		{
			dateStr = dateFormat.format( date );
		}// else

		setDate( date );
		setDateStr( dateStr );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dateAsStr
	 * @param dateFormatPattern
	 * @return
	 */
	public static RowValueDate valueOf( final String dateAsStr, final String dateFormatPattern )
	{
		if ( dateAsStr == null )
		{
			return null;
		}// if

		final DateFormat dateFormat = new SimpleDateFormat( dateFormatPattern );
		try
		{
			final Date date = dateFormat.parse( dateAsStr );
			final RowValueDate result = new RowValueDate( date, dateFormat );
			return result;
		}
		catch ( final ParseException e )
		{
			final String msg = String.format( "Cannot parser date text '%s' with format '%s'", dateAsStr, dateFormat );
			throw new IllegalArgumentException( msg, e );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( final Object o )
	{
		if ( !( o instanceof RowValueDate ) )
		{
			return -1;
		}// if
		final RowValueDate otherSimpleDate = ( RowValueDate ) o;
		final Date date = getDate();
		final Date otherDate = otherSimpleDate.getDate();
		final int compareTo = date.compareTo( otherDate );
		return compareTo;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( final Object obj )
	{
		if ( obj instanceof RowValueDate )
		{
			final boolean result = compareTo( obj ) == 0;
			return result;
		}// if
		return false;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the date.
	 * @see #date
	 */
	private Date getDate()
	{
		return this.date;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the dateStr.
	 * @see #dateStr
	 */
	private String getDateStr()
	{
		return this.dateStr;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final Date date = getDate();
		if ( date == null )
		{
			return 0;
		}// if
		final int hashCode = date.hashCode();
		return hashCode;
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param date
	 *            The date to set.
	 * @see #date
	 */
	private void setDate( final Date date )
	{
		this.date = date;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dateStr
	 *            The dateStr to set.
	 * @see #dateStr
	 */
	private void setDateStr( final String dateStr )
	{
		this.dateStr = dateStr;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		final String dateStr = getDateStr();
		if ( dateStr == null )
		{
			return super.toString();

		}// if
		return dateStr;
	}

	/**
	 * <p>
	 * Field <code>date</code>
	 * </p>
	 */
	private Date date;

	/**
	 * <p>
	 * Field <code>dateStr</code>
	 * </p>
	 */
	private String dateStr;

}// class

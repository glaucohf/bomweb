package br.com.fetranspor.bomweb.util.queryCreator.excel;

/**
 * <p>
 * </p>
 * .
 *
 * @author gfreitas
 * @version 1.0 Created on Sep 27, 2012
 */
public class Row
{

	/**
	 * <p>
	 * </p>
	 * .
	 */
	protected Row()
	{
		super();
	}// func

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param o
	 *            the o
	 * @return the int
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo( final Row o )
	{
		final String project = getProject();
		final String oProject = o.getProject();
		final int result1 = project.compareTo( oProject );
		if ( result1 != 0 )
		{
			return result1;
		}// if

		final String person = getPerson();
		final String oPerson = o.getPerson();
		final int result2 = person.compareTo( oPerson );
		if ( result2 != 0 )
		{
			return result2;
		}// if

		final String date = getDate();
		final String oDate = o.getDate();
		final int result3 = date.compareTo( oDate );
		if ( result3 != 0 )
		{
			return result3;
		}// if

		final String note = getNote();
		final String oNote = o.getNote();
		final int result4 = note.compareTo( oNote );
		if ( result4 != 0 )
		{
			return result4;
		}// if

		return result4;
	}// func

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param obj
	 *            the obj
	 * @return true, if successful
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public final boolean equals( final Object obj )
	{
		final Row otherRow = ( Row ) obj;
		final int result = compareTo( otherRow );
		if ( result == 0 )
		{
			return true;
		}
		return false;
	}// func

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return the combo string
	 * @see br.com.mindsatwork.jmsi.model.AbstractBean#getComboString()
	 */
	public String getComboString()
	{
		return String.format(
			"%s - %s - %s - %s - %s - %s ",
			getDate(),
			getPerson(),
			getProject(),
			getTask(),
			getDuration(),
			getCosts(),
			getNote() );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the costs.
	 * @see #costs
	 */
	public String getCosts()
	{
		return this.costs;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the date.
	 * @see #date
	 */
	public String getDate()
	{
		return this.date;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the duration.
	 * @see #duration
	 */
	public String getDuration()
	{

		return this.duration;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the duration.
	 * @see #duration
	 */
	public Double getDurationToSum()
	{
		return Double.parseDouble( this.duration );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the inRemoveList.
	 * @see #inRemoveList
	 */
	public String getInRemoveList()
	{
		return this.inRemoveList;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the lessThan.
	 * @see #lessThan
	 */
	public String getLessThan()
	{
		return this.lessThan;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the note.
	 * @see #note
	 */
	public String getNote()
	{
		return this.note;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the person.
	 * @see #person
	 */
	public String getPerson()
	{
		return this.person;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the project.
	 * @see #project
	 */
	public String getProject()
	{
		return this.project;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the task.
	 * @see #task
	 */
	public String getTask()
	{
		return this.task;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return the int
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param costs
	 *            The costs to set.
	 * @see #costs
	 */
	public void setCosts( final String costs )
	{
		this.costs = costs;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param date
	 *            The date to set.
	 * @see #date
	 */
	public void setDate( final String date )
	{
		this.date = date;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param duration
	 *            The duration to set.
	 * @see #duration
	 */
	public void setDuration( final String duration )
	{
		this.duration = duration;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param duration
	 *            The duration to set.
	 * @see #duration
	 */
	public void setDurationToSum( final Double duration )
	{
		this.duration = String.valueOf( duration );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param inRemoveList
	 *            The inRemoveList to set.
	 * @see #inRemoveList
	 */
	public void setInRemoveList( final String inRemoveList )
	{
		this.inRemoveList = inRemoveList;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param lessThan
	 *            The lessThan to set.
	 * @see #lessThan
	 */
	public void setLessThan( final String lessThan )
	{
		this.lessThan = lessThan;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param note
	 *            The note to set.
	 * @see #note
	 */
	public void setNote( final String note )
	{
		this.note = note;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param person
	 *            The person to set.
	 * @see #person
	 */
	public void setPerson( final String person )
	{
		this.person = person;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param project
	 *            The project to set.
	 * @see #project
	 */
	public void setProject( final String project )
	{
		this.project = project;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param task
	 *            The task to set.
	 * @see #task
	 */
	public void setTask( final String task )
	{
		this.task = task;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return the string
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return String.format(
			"%s | %s | %s | %s | %s | %s | %s",
			getDate(),
			getPerson(),
			getProject(),
			getTask(),
			getDuration(),
			getCosts(),
			getNote() );
	}

	/**
	 * <p>
	 * Field <code>costs</code>
	 * </p>
	 * .
	 */
	private String costs;

	/**
	 * <p>
	 * Field <code>date</code>
	 * </p>
	 * .
	 */
	private String date;

	/**
	 * <p>
	 * Field <code>duration</code>
	 * </p>
	 * .
	 */
	private String duration;

	/**
	 * <p>
	 * Field <code>inRemoveList</code>
	 * </p>
	 * .
	 */
	private String inRemoveList;

	/**
	 * <p>
	 * Field <code>lessThan</code>
	 * </p>
	 * .
	 */
	private String lessThan;

	/**
	 * <p>
	 * Field <code>note</code>
	 * </p>
	 * .
	 */
	private String note;

	/**
	 * <p>
	 * Field <code>person</code>
	 * </p>
	 * .
	 */
	private String person;

	/**
	 * <p>
	 * Field <code>project</code>
	 * </p>
	 * .
	 */
	private String project;

	/**
	 * <p>
	 * Field <code>task</code>
	 * </p>
	 * .
	 */
	private String task;

}

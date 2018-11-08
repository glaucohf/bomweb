package br.com.fetranspor.bomweb.util.queryCreator.excel;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

/**
 * <p>
 * TODO CORE
 * </p>
 * .
 *
 * @author Rodolfo Caldeira
 * @version 1.0 Created on Jul 6, 2010
 */
public class ExcelColumnMapper
{

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param columnName
	 *            the column name
	 * @param columnClass
	 *            the column class
	 * @param columnAliases
	 *            the column aliases
	 */
	public ExcelColumnMapper( final String columnName, final Class columnClass, final Collection<String> columnAliases )
	{
		this( columnName, columnClass, new TreeSet( columnAliases ) );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param columnName
	 *            the column name
	 * @param columnClass
	 *            the column class
	 * @param columnAliases
	 *            the column aliases
	 */
	public ExcelColumnMapper( final String columnName, final Class columnClass, final Set<String> columnAliases )
	{
		super();
		setColumnName( columnName );
		setColumnClass( columnClass );
		setColumnAliases( columnAliases );
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the columnAliases.
	 * @see #columnAliases
	 */
	public Set<String> getColumnAliases()
	{
		return this.columnAliases;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the columnClass.
	 * @see #columnClass
	 */
	public Class getColumnClass()
	{
		return this.columnClass;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the columnIndex.
	 * @see #columnIndex
	 */
	int getColumnIndex()
	{
		return this.columnIndex;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the columnName.
	 * @see #columnName
	 */
	public String getColumnName()
	{
		return this.columnName;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param columnAliases
	 *            The columnAliases to set.
	 * @see #columnAliases
	 */
	protected void setColumnAliases( final Set<String> columnAliases )
	{
		this.columnAliases = columnAliases;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param columnClass
	 *            The columnClass to set.
	 * @see #columnClass
	 */
	protected void setColumnClass( final Class columnClass )
	{
		this.columnClass = columnClass;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param columnIndex
	 *            The columnIndex to set.
	 * @see #columnIndex
	 */
	void setColumnIndex( final int columnIndex )
	{
		this.columnIndex = columnIndex;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param columnName
	 *            The columnName to set.
	 * @see #columnName
	 */
	protected void setColumnName( final String columnName )
	{
		this.columnName = columnName;
	}

	/**
	 * <p>
	 * Field <code>columnAliases</code>
	 * </p>
	 * .
	 */
	private Set<String> columnAliases;

	/**
	 * <p>
	 * Field <code>columnClass</code>
	 * </p>
	 * .
	 */
	private Class columnClass;

	/**
	 * <p>
	 * Field <code>columnIndex</code>
	 * </p>
	 * .
	 */
	private int columnIndex;

	/**
	 * <p>
	 * Field <code>columnName</code>
	 * </p>
	 * .
	 */
	private String columnName;
}

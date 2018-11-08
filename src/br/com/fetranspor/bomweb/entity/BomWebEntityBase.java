package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.entity.EntityBase;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

/**
 * The Class BomWebEntityBase.
 */
@MappedSuperclass
@Audited
public abstract class BomWebEntityBase
	extends EntityBase
{

	/**
	 * <p>
	 * Field <code>serialVersionUID</code>
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-319
	 * </p>
	 *
	 * @param obj
	 * @return
	 */
	public static String toString( final Object obj )
	{
		if ( obj == null )
		{
			return "---";
		}// if

		final String result = obj.toString();
		return result;
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public String getComboString()
	{
		final String result = getShortComboString();
		return result;
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getLabel()
	 * @deprecated implemntação da super classe levanta exceção. Reimplemntado para métodos de
	 *             reflexão não voarem.
	 */
	@Deprecated
	@Override
	public String getLabel()
	{
		return null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	public String getShortComboString()
	{
		final String result = toString();
		return result;
	}// func

	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	public boolean isActive()
	{
		return this.active;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-319
	 * </p>
	 * <p>
	 * Como o código que converte objetos para json lê atributos (fields) ao invés de properties
	 * (get/set), essa função é usada antes da conversão para jason para setar atributos, que não
	 * precisariam existir se a conversão usasse get/set, apenas para guardar valores que poderiam
	 * ser obtidos a partir de um get.
	 * </p>
	 */
	public void populateForJson()
	{
		// valores gerados a partir de outros campos
		final String comboString = getComboString();
		final String shortComboString = getShortComboString();

		// setando valores nos atributos dummy apenas para o json
		setComboString( comboString );
		setShortComboString( shortComboString );
	}// func

	/**
	 * Sets the active.
	 *
	 * @param active
	 *            the new active
	 */
	public void setActive( final boolean active )
	{
		this.active = active;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-319
	 * </p>
	 *
	 * @param comboString
	 */
	private void setComboString( final String comboString )
	{
		this.comboString = comboString;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-319
	 * </p>
	 *
	 * @param shortComboString
	 */
	private void setShortComboString( final String shortComboString )
	{
		this.shortComboString = shortComboString;
	}

	/**
	 * The active.
	 */
	protected boolean active;

	/**
	 * <p>
	 * Field <code>comboString</code>
	 * </p>
	 * <p>
	 * FBW-319 campo criado apenas para ser usado pelo {@link #populateForJson()}
	 * </p>
	 */
	@Transient
	private String comboString;

	/**
	 * <p>
	 * Field <code>shortComboString</code>
	 * </p>
	 * <p>
	 * FBW-319 campo criado apenas para ser usado pelo {@link #populateForJson()}
	 * </p>
	 */
	@Transient
	private String shortComboString;

}

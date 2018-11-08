package br.com.fetranspor.bomweb.entity;

/**
 * The Class EntidadesLog.
 */
public class EntidadesLog
{

	/**
	 * Instantiates a new entidades log.
	 *
	 * @param labelEntidade
	 *            the label entidade
	 * @param valueEntidade
	 *            the value entidade
	 */
	public EntidadesLog( final String labelEntidade, final String valueEntidade )
	{
		this.labelEntidade = labelEntidade;
		this.valueEntidade = valueEntidade;
	}

	/**
	 * Gets the label entidade.
	 *
	 * @return the label entidade
	 */
	public String getLabelEntidade()
	{
		return this.labelEntidade;
	}

	/**
	 * Gets the value entidade.
	 *
	 * @return the value entidade
	 */
	public String getValueEntidade()
	{
		return this.valueEntidade;
	}

	/**
	 * Sets the label entidade.
	 *
	 * @param labelEntidade
	 *            the new label entidade
	 */
	public void setLabelEntidade( final String labelEntidade )
	{
		this.labelEntidade = labelEntidade;
	}

	/**
	 * Sets the value entidade.
	 *
	 * @param valueEntidade
	 *            the new value entidade
	 */
	public void setValueEntidade( final String valueEntidade )
	{
		this.valueEntidade = valueEntidade;
	}

	/**
	 * The label entidade.
	 */
	private String labelEntidade;

	/**
	 * The value entidade.
	 */
	private String valueEntidade;
}

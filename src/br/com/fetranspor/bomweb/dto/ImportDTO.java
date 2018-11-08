package br.com.fetranspor.bomweb.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ImportDTO.
 */
public class ImportDTO
{

	/**
	 * Instantiates a new import dto.
	 */
	public ImportDTO()
	{
		this.values = new ArrayList<String>();
	}

	/**
	 * Gets the values.
	 *
	 * @return the values
	 */
	public List<String> getValues()
	{
		return this.values;
	}

	/**
	 * Sets the values.
	 *
	 * @param values
	 *            the new values
	 */
	public void setValues( final List<String> values )
	{
		this.values = values;
	}

	/**
	 * The values.
	 */
	List<String> values;
}

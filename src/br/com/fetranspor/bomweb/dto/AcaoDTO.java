package br.com.fetranspor.bomweb.dto;

/**
 * The Class AcaoDTO.
 */
public class AcaoDTO
{

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId()
	{
		return this.id;
	}

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome()
	{
		return this.nome;
	}

	/**
	 * Checks if is checked.
	 *
	 * @return true, if is checked
	 */
	public boolean isChecked()
	{
		return this.checked;
	}

	/**
	 * Sets the checked.
	 *
	 * @param checked
	 *            the new checked
	 */
	public void setChecked( final boolean checked )
	{
		this.checked = checked;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final Integer id )
	{
		this.id = id;
	}

	/**
	 * Sets the nome.
	 *
	 * @param nome
	 *            the new nome
	 */
	public void setNome( final String nome )
	{
		this.nome = nome;
	}

	/**
	 * The id.
	 */
	private Integer id;

	/**
	 * The nome.
	 */
	private String nome;

	/**
	 * The checked.
	 */
	private boolean checked = Boolean.FALSE;

}

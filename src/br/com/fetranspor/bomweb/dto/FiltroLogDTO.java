package br.com.fetranspor.bomweb.dto;

import java.util.Date;

/**
 * The Class FiltroLogDTO.
 */
public class FiltroLogDTO
{

	/**
	 * Gets the data final.
	 *
	 * @return the data final
	 */
	public Date getDataFinal()
	{
		return this.dataFinal;
	}

	/**
	 * Gets the data inicial.
	 *
	 * @return the data inicial
	 */
	public Date getDataInicial()
	{
		return this.dataInicial;
	}

	/**
	 * Gets the entidade.
	 *
	 * @return the entidade
	 */
	public String getEntidade()
	{
		return this.entidade;
	}

	/**
	 * Gets the id usuario.
	 *
	 * @return the id usuario
	 */
	public Integer getIdUsuario()
	{
		return this.idUsuario;
	}

	/**
	 * Gets the revision type.
	 *
	 * @return the revision type
	 */
	public Byte getRevisionType()
	{
		return this.revisionType;
	}

	/**
	 * Sets the data final.
	 *
	 * @param dataFinal
	 *            the new data final
	 */
	public void setDataFinal( final Date dataFinal )
	{
		this.dataFinal = dataFinal;
	}

	/**
	 * Sets the data inicial.
	 *
	 * @param dataInicial
	 *            the new data inicial
	 */
	public void setDataInicial( final Date dataInicial )
	{
		this.dataInicial = dataInicial;
	}

	/**
	 * Sets the entidade.
	 *
	 * @param entidade
	 *            the new entidade
	 */
	public void setEntidade( final String entidade )
	{
		this.entidade = entidade;
	}

	/**
	 * Sets the id usuario.
	 *
	 * @param idUsuario
	 *            the new id usuario
	 */
	public void setIdUsuario( final Integer idUsuario )
	{
		this.idUsuario = idUsuario;
	}

	/**
	 * Sets the revision type.
	 *
	 * @param revisionType
	 *            the new revision type
	 */
	public void setRevisionType( final Byte revisionType )
	{
		this.revisionType = revisionType;
	}

	/**
	 * The entidade.
	 */
	private String entidade;

	/**
	 * The revision type.
	 */
	private Byte revisionType;

	/**
	 * The id usuario.
	 */
	private Integer idUsuario;

	/**
	 * The data inicial.
	 */
	private Date dataInicial;

	/**
	 * The data final.
	 */
	private Date dataFinal;

}

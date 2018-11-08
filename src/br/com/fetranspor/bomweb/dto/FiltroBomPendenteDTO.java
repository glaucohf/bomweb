package br.com.fetranspor.bomweb.dto;

/**
 * The Class FiltroBomPendenteDTO.
 */
public class FiltroBomPendenteDTO
{

	/**
	 * Gets the data final.
	 *
	 * @return the data final
	 */
	public String getDataFinal()
	{
		return this.dataFinal;
	}

	/**
	 * Gets the data inicial.
	 *
	 * @return the data inicial
	 */
	public String getDataInicial()
	{
		return this.dataInicial;
	}

	/**
	 * Gets the empresa.
	 *
	 * @return the empresa
	 */
	public String getEmpresa()
	{
		return this.empresa;
	}

	/**
	 * Gets the nome empresa.
	 *
	 * @return the nome empresa
	 */
	public String getNomeEmpresa()
	{
		return this.nomeEmpresa;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus()
	{
		return this.status;
	}

	/**
	 * Checks if is boms vigentes.
	 *
	 * @return true, if is boms vigentes
	 */
	public boolean isBomsVigentes()
	{
		return this.bomsVigentes;
	}

	/**
	 * Sets the boms vigentes.
	 *
	 * @param bomsVigentes
	 *            the new boms vigentes
	 */
	public void setBomsVigentes( final boolean bomsVigentes )
	{
		this.bomsVigentes = bomsVigentes;
	}

	/**
	 * Sets the data final.
	 *
	 * @param dataFinal
	 *            the new data final
	 */
	public void setDataFinal( final String dataFinal )
	{
		this.dataFinal = dataFinal;
	}

	/**
	 * Sets the data inicial.
	 *
	 * @param dataInicial
	 *            the new data inicial
	 */
	public void setDataInicial( final String dataInicial )
	{
		this.dataInicial = dataInicial;
	}

	/**
	 * Sets the empresa.
	 *
	 * @param empresa
	 *            the new empresa
	 */
	public void setEmpresa( final String empresa )
	{
		this.empresa = empresa;
	}

	/**
	 * Sets the nome empresa.
	 *
	 * @param nomeEmpresa
	 *            the new nome empresa
	 */
	public void setNomeEmpresa( final String nomeEmpresa )
	{
		this.nomeEmpresa = nomeEmpresa;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus( final String status )
	{
		this.status = status;
	}

	/**
	 * The empresa.
	 */
	public String empresa;

	/**
	 * The nome empresa.
	 */
	public String nomeEmpresa;

	/**
	 * The data inicial.
	 */
	public String dataInicial;

	/**
	 * The data final.
	 */
	public String dataFinal;

	/**
	 * The status.
	 */
	public String status;

	/**
	 * The boms vigentes.
	 */
	public boolean bomsVigentes;

}

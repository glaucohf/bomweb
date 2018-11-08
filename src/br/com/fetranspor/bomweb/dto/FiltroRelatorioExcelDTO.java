package br.com.fetranspor.bomweb.dto;

/**
 * The Class FiltroRelatorioExcelDTO.
 */
public class FiltroRelatorioExcelDTO
{

	/**
	 * Gets the campos relatorio como string.
	 *
	 * @return the campos relatorio como string
	 */
	public String[] getCamposRelatorioComoString()
	{
		return this.camposRelatorioComoString;
	}

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
	 * Gets the empresas como string.
	 *
	 * @return the empresas como string
	 */
	public String[] getEmpresasComoString()
	{
		return this.empresasComoString;
	}

	/**
	 * Gets the linhas como string.
	 *
	 * @return the linhas como string
	 */
	public String[] getLinhasComoString()
	{
		return this.linhasComoString;
	}

	/**
	 * Gets the tipos veiculo como string.
	 *
	 * @return the tipos veiculo como string
	 */
	public String[] getTiposVeiculoComoString()
	{
		return this.tiposVeiculoComoString;
	}

	/**
	 * Sets the campos relatorio como string.
	 *
	 * @param camposRelatorioComoString
	 *            the new campos relatorio como string
	 */
	public void setCamposRelatorioComoString( final String[] camposRelatorioComoString )
	{
		this.camposRelatorioComoString = camposRelatorioComoString;
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
	 * Sets the empresas como string.
	 *
	 * @param empresasComoString
	 *            the new empresas como string
	 */
	public void setEmpresasComoString( final String[] empresasComoString )
	{
		this.empresasComoString = empresasComoString;
	}

	/**
	 * Sets the linhas como string.
	 *
	 * @param linhasComoString
	 *            the new linhas como string
	 */
	public void setLinhasComoString( final String[] linhasComoString )
	{
		this.linhasComoString = linhasComoString;
	}

	/**
	 * Sets the tipos veiculo como string.
	 *
	 * @param tiposVeiculoComoString
	 *            the new tipos veiculo como string
	 */
	public void setTiposVeiculoComoString( final String[] tiposVeiculoComoString )
	{
		this.tiposVeiculoComoString = tiposVeiculoComoString;
	}

	/**
	 * The empresas como string.
	 */
	private String[] empresasComoString;

	/**
	 * The linhas como string.
	 */
	private String[] linhasComoString;

	/**
	 * The tipos veiculo como string.
	 */
	private String[] tiposVeiculoComoString;

	/**
	 * The campos relatorio como string.
	 */
	private String[] camposRelatorioComoString;

	/**
	 * The data inicial.
	 */
	private String dataInicial;

	/**
	 * The data final.
	 */
	private String dataFinal;
}

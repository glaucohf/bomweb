package br.com.fetranspor.bomweb.dto;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Empresa;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * The Class FiltroRelatorioDTO.
 */
public class FiltroRelatorioDTO
{

	/**
	 * Instantiates a new filtro relatorio dto.
	 */
	public FiltroRelatorioDTO()
	{
	}

	/**
	 * Instantiates a new filtro relatorio dto.
	 *
	 * @param empresas
	 *            the empresas
	 * @param linhas
	 *            the linhas
	 * @param tiposVeiculo
	 *            the tipos veiculo
	 * @param camposRelatorio
	 *            the campos relatorio
	 * @param dataInicial
	 *            the data inicial
	 * @param dataFinal
	 *            the data final
	 */
	public FiltroRelatorioDTO(
		final String[] empresas,
		final String[] linhas,
		final String[] tiposVeiculo,
		final String[] camposRelatorio,
		final String dataInicial,
		final String dataFinal )
	{

		this.empresasComoString = StringUtils.join( empresas, "," );
		this.linhasComoString = StringUtils.join( linhas, "," );
		this.tiposVeiculoComoString = StringUtils.join( tiposVeiculo, "," );

		this.camposRelatorioComoString = StringUtils.join( camposRelatorio, "," );

		this.dataInicial = dataInicial;
		this.dataFinal = dataFinal;
	}

	/**
	 * Adds the empresa.
	 *
	 * @param empresa
	 *            the empresa
	 */
	public void addEmpresa( final Empresa empresa )
	{
		if ( Check.isNull( empresa ) || Check.isNull( empresa.getId() ) )
		{
			throw new IllegalArgumentException();
		}

		if ( Check.isNotBlank( this.empresasComoString ) )
		{
			this.empresasComoString += "," + empresa.getId();
		}
		else
		{
			this.empresasComoString = empresa.getId().toString();
		}
	}

	/**
	 * Gets the campos relatorio.
	 *
	 * @return the campos relatorio
	 */
	public List<String> getCamposRelatorio()
	{
		final List<String> camposRelatorio = new ArrayList<String>();

		if ( ( this.camposRelatorioComoString == null ) || this.camposRelatorioComoString.isEmpty() )
		{
			return camposRelatorio;
		}

		for ( final String campoRelatorio : this.camposRelatorioComoString.split( "," ) )
		{
			camposRelatorio.add( campoRelatorio );
		}

		return camposRelatorio;
	}

	/**
	 * Gets the campos relatorio como array.
	 *
	 * @return the campos relatorio como array
	 */
	public String[] getCamposRelatorioComoArray()
	{
		return getCamposRelatorio().toArray( new String[0] );
	}

	/**
	 * Gets the campos relatorio como string.
	 *
	 * @return the campos relatorio como string
	 */
	public String getCamposRelatorioComoString()
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
	 * Gets the empresas.
	 *
	 * @return the empresas
	 */
	public List<String> getEmpresas()
	{
		final List<String> empresas = new ArrayList<String>();

		if ( ( this.empresasComoString == null ) || this.empresasComoString.isEmpty() )
		{
			return empresas;
		}

		for ( final String empresa : this.empresasComoString.split( "," ) )
		{
			empresas.add( empresa );
		}

		return empresas;
	}

	/**
	 * Gets the empresas como array.
	 *
	 * @return the empresas como array
	 */
	public String[] getEmpresasComoArray()
	{
		return getEmpresas().toArray( new String[0] );
	}

	/**
	 * Gets the empresas como string.
	 *
	 * @return the empresas como string
	 */
	public String getEmpresasComoString()
	{
		return this.empresasComoString;
	}

	/**
	 * Gets the linhas.
	 *
	 * @return the linhas
	 */
	public List<String> getLinhas()
	{
		final List<String> linhas = new ArrayList<String>();

		if ( ( this.linhasComoString == null ) || this.linhasComoString.isEmpty() )
		{
			return linhas;
		}

		for ( final String linha : this.linhasComoString.split( "," ) )
		{
			linhas.add( linha );
		}

		return linhas;
	}

	/**
	 * Gets the linhas como array.
	 *
	 * @return the linhas como array
	 */
	public String[] getLinhasComoArray()
	{
		return getLinhas().toArray( new String[0] );
	}

	/**
	 * Gets the linhas como string.
	 *
	 * @return the linhas como string
	 */
	public String getLinhasComoString()
	{
		return this.linhasComoString;
	}

	/**
	 * Gets the tipos veiculo.
	 *
	 * @return the tipos veiculo
	 */
	public List<String> getTiposVeiculo()
	{
		final List<String> tiposVeiculo = new ArrayList<String>();

		if ( ( this.tiposVeiculoComoString == null ) || this.tiposVeiculoComoString.isEmpty() )
		{
			return tiposVeiculo;
		}

		for ( final String tipoVeiculo : this.tiposVeiculoComoString.split( "," ) )
		{
			tiposVeiculo.add( tipoVeiculo );
		}

		return tiposVeiculo;
	}

	/**
	 * Gets the tipos veiculo como string.
	 *
	 * @return the tipos veiculo como string
	 */
	public String getTiposVeiculoComoString()
	{
		return this.tiposVeiculoComoString;
	}

	/**
	 * Sets the campos relatorio como string.
	 *
	 * @param camposRelatorioComoString
	 *            the new campos relatorio como string
	 */
	public void setCamposRelatorioComoString( final String camposRelatorioComoString )
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
	public void setEmpresasComoString( final String empresasComoString )
	{
		this.empresasComoString = empresasComoString;
	}

	/**
	 * Sets the linhas como string.
	 *
	 * @param linhasComoString
	 *            the new linhas como string
	 */
	public void setLinhasComoString( final String linhasComoString )
	{
		this.linhasComoString = linhasComoString;
	}

	/**
	 * Sets the tipos veiculo como string.
	 *
	 * @param tiposVeiculoComoString
	 *            the new tipos veiculo como string
	 */
	public void setTiposVeiculoComoString( final String tiposVeiculoComoString )
	{
		this.tiposVeiculoComoString = tiposVeiculoComoString;
	}

	/**
	 * The empresas como string.
	 */
	private String empresasComoString;

	/**
	 * The linhas como string.
	 */
	private String linhasComoString;

	/**
	 * The tipos veiculo como string.
	 */
	private String tiposVeiculoComoString;

	/**
	 * The campos relatorio como string.
	 */
	private String camposRelatorioComoString;

	/**
	 * The data inicial.
	 */
	private String dataInicial;

	/**
	 * The data final.
	 */
	private String dataFinal;
}

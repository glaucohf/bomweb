package br.com.fetranspor.bomweb.dto;

/**
 * The Class FiltroTarifaDTO.
 */
public class FiltroTarifaDTO
{

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
	 * Gets the linha.
	 *
	 * @return the linha
	 */
	public String getLinha()
	{
		return this.linha;
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
	 * Gets the nome linha.
	 *
	 * @return the nome linha
	 */
	public String getNomeLinha()
	{
		return this.nomeLinha;
	}

	/**
	 * Gets the secao.
	 *
	 * @return the secao
	 */
	public String getSecao()
	{
		return this.secao;
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public String getValor()
	{
		return this.valor;
	}

	/**
	 * Gets the valor futura.
	 *
	 * @return the valor futura
	 */
	public String getValorFutura()
	{
		return this.valorFutura;
	}

	/**
	 * Checks if is tarifa futura.
	 *
	 * @return true, if is tarifa futura
	 */
	public boolean isTarifaFutura()
	{
		return this.tarifaFutura;
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
	 * Sets the linha.
	 *
	 * @param linha
	 *            the new linha
	 */
	public void setLinha( final String linha )
	{
		this.linha = linha;
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
	 * Sets the nome linha.
	 *
	 * @param nomeLinha
	 *            the new nome linha
	 */
	public void setNomeLinha( final String nomeLinha )
	{
		this.nomeLinha = nomeLinha;
	}

	/**
	 * Sets the secao.
	 *
	 * @param secao
	 *            the new secao
	 */
	public void setSecao( final String secao )
	{
		this.secao = secao;
	}

	/**
	 * Sets the tarifa futura.
	 *
	 * @param tarifaFutura
	 *            the new tarifa futura
	 */
	public void setTarifaFutura( final boolean tarifaFutura )
	{
		this.tarifaFutura = tarifaFutura;
	}

	/**
	 * Sets the valor.
	 *
	 * @param valor
	 *            the new valor
	 */
	public void setValor( final String valor )
	{
		this.valor = valor;
	}

	/**
	 * Sets the valor futura.
	 *
	 * @param valorFutura
	 *            the new valor futura
	 */
	public void setValorFutura( final String valorFutura )
	{
		this.valorFutura = valorFutura;
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
	 * The linha.
	 */
	public String linha;

	/**
	 * The nome linha.
	 */
	public String nomeLinha;

	/**
	 * The secao.
	 */
	public String secao;

	/**
	 * The valor.
	 */
	public String valor;

	/**
	 * The valor futura.
	 */
	public String valorFutura;

	/**
	 * The tarifa futura.
	 */
	public boolean tarifaFutura;
}

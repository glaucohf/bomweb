package br.com.fetranspor.bomweb.dto;

import java.math.BigDecimal;

/**
 * The Class BomSecaoAntigaPdfDTO.
 */
public class BomSecaoAntigaPdfDTO
	extends BomSecaoPdfDTO
{

	/**
	 * Instantiates a new bom secao antiga pdf dto.
	 *
	 * @param nova
	 *            the nova
	 */
	public BomSecaoAntigaPdfDTO( final BomSecaoNovaPdfDTO nova )
	{
		super();
		this.nova = nova;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getCodigo()
	 */
	@Override
	public String getCodigo()
	{
		return this.nova.getCodigo();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getNumeroPassageiros()
	 */
	@Override
	public long getNumeroPassageiros()
	{
		return getPassageiroAB() + getPassageiroBA();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getPassageiroAB()
	 */
	@Override
	public int getPassageiroAB()
	{
		return this.nova.getPassageiroAnteriorAB();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getPassageiroBA()
	 */
	@Override
	public int getPassageiroBA()
	{
		return this.nova.getPassageiroAnteriorBA();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getPontoFinal()
	 */
	@Override
	public String getPontoFinal()
	{
		return this.nova.getPontoFinal();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getPontoInicial()
	 */
	@Override
	public String getPontoInicial()
	{
		return this.nova.getPontoInicial();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getReceita()
	 */
	@Override
	public BigDecimal getReceita()
	{
		final BigDecimal tarifaAnterior = this.nova.getTarifaAnterior();

		// Na prática não ocorre.
		if ( tarifaAnterior == null )
		{
			throw new AssertionError();
		}

		return tarifaAnterior.multiply( new BigDecimal( getNumeroPassageiros() ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#getTarifa()
	 */
	@Override
	public BigDecimal getTarifa()
	{
		return this.nova.getTarifaAnterior();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.fetranspor.bomweb.dto.BomSecaoPdfDTO#isInoperante()
	 */
	@Override
	public boolean isInoperante()
	{
		return this.nova.isInoperante();
	}

	/**
	 * The nova.
	 */
	private final BomSecaoNovaPdfDTO nova;
}

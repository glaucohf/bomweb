package br.com.fetranspor.bomweb.dto;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.BomSecao;

import java.math.BigDecimal;

/**
 * The Class BomSecaoNovaPdfDTO.
 */
public class BomSecaoNovaPdfDTO
	extends BomSecaoPdfDTO
{

	/**
	 * Instantiates a new bom secao nova pdf dto.
	 *
	 * @param bomSecao
	 *            the bom secao
	 */
	public BomSecaoNovaPdfDTO( final BomSecao bomSecao )
	{
		super();
		this.bomSecao = bomSecao;
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
		return this.bomSecao.getSecao().getCodigo();
	}

	/**
	 * Gets the inoperante.
	 *
	 * @return the inoperante
	 */
	public boolean getInoperante()
	{
		return this.bomSecao.ehInoperante();
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
		return ( long ) getPassageiroAB() + getPassageiroBA();
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
		final Integer passageiroAB = this.bomSecao.getPassageiroAB();
		if ( Check.isNull( passageiroAB ) )
		{
			return 0;
		}
		return passageiroAB;
	}

	/**
	 * Gets the passageiro anterior ab.
	 *
	 * @return the passageiro anterior ab
	 */
	protected int getPassageiroAnteriorAB()
	{
		final Integer passageiroAnteriorAB = this.bomSecao.getPassageiroAnteriorAB();
		if ( Check.isNull( passageiroAnteriorAB ) )
		{
			return 0;
		}
		return passageiroAnteriorAB;
	}

	/**
	 * Gets the passageiro anterior ba.
	 *
	 * @return the passageiro anterior ba
	 */
	protected int getPassageiroAnteriorBA()
	{
		final Integer passageiroAnteriorBA = this.bomSecao.getPassageiroAnteriorBA();
		if ( Check.isNull( passageiroAnteriorBA ) )
		{
			return 0;
		}
		return passageiroAnteriorBA;
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
		final Integer passageiroBA = this.bomSecao.getPassageiroBA();
		if ( Check.isNull( passageiroBA ) )
		{
			return 0;
		}
		return passageiroBA;
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
		return this.bomSecao.getSecao().getPontoFinal();
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
		return this.bomSecao.getSecao().getPontoInicial();
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
		final BigDecimal tarifa = getTarifa();

		// Na prática não ocorre.
		if ( tarifa == null )
		{
			throw new AssertionError();
		}

		return tarifa.multiply( new BigDecimal( getNumeroPassageiros() ) );
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
		return this.bomSecao.getTarifa();
	}

	/**
	 * Gets the tarifa anterior.
	 *
	 * @return the tarifa anterior
	 */
	protected BigDecimal getTarifaAnterior()
	{
		return this.bomSecao.getTarifaAnterior();
	}

	/**
	 * Gets the viagens extraordinarias.
	 *
	 * @return the viagens extraordinarias
	 */
	public int getViagensExtraordinarias()
	{
		return getViagensExtraordinariasAB() + getViagensExtraordinariasBA();
	}

	/**
	 * Gets the viagens extraordinarias ab.
	 *
	 * @return the viagens extraordinarias ab
	 */
	public int getViagensExtraordinariasAB()
	{
		final Integer viagensExtraordinariasAB = this.bomSecao.getBomLinha().getViagensExtraordinariasAB();
		if ( Check.isNull( viagensExtraordinariasAB ) )
		{
			return 0;
		}
		return viagensExtraordinariasAB;
	}

	/**
	 * Gets the viagens extraordinarias ba.
	 *
	 * @return the viagens extraordinarias ba
	 */
	public int getViagensExtraordinariasBA()
	{
		final Integer viagensExtraordinariasBA = this.bomSecao.getBomLinha().getViagensExtraordinariasBA();
		if ( Check.isNull( viagensExtraordinariasBA ) )
		{
			return 0;
		}
		return viagensExtraordinariasBA;
	}

	/**
	 * Gets the viagens ordinarias.
	 *
	 * @return the viagens ordinarias
	 */
	public long getViagensOrdinarias()
	{
		return ( long ) getViagensOrdinariasAB() + getViagensOrdinariasBA();
	}

	/**
	 * Gets the viagens ordinarias ab.
	 *
	 * @return the viagens ordinarias ab
	 */
	public int getViagensOrdinariasAB()
	{
		final Integer viagensOrdinariasAB = this.bomSecao.getBomLinha().getViagensOrdinariasAB();
		if ( Check.isNull( viagensOrdinariasAB ) )
		{
			return 0;
		}
		return viagensOrdinariasAB;
	}

	/**
	 * Gets the viagens ordinarias ba.
	 *
	 * @return the viagens ordinarias ba
	 */
	public int getViagensOrdinariasBA()
	{
		final Integer viagensOrdinariasBA = this.bomSecao.getBomLinha().getViagensOrdinariasBA();
		if ( Check.isNull( viagensOrdinariasBA ) )
		{
			return 0;
		}
		return viagensOrdinariasBA;
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
		return this.bomSecao.ehInoperante();
	}

	/**
	 * The bom secao.
	 */
	private final BomSecao bomSecao;
}

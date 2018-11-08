package br.com.fetranspor.bomweb.dto;

import java.math.BigDecimal;

/**
 * The Class BomSecaoPdfDTO.
 */
public abstract class BomSecaoPdfDTO
	implements Comparable<BomSecaoPdfDTO>
{

	/**
	 * <p>
	 * </p>
	 *
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( final BomSecaoPdfDTO o )
	{
		return getCodigo().compareTo( o.getCodigo() );
	}

	// O equals utiliza o codigo da secao e só e verdadeiro para secoes de uma mesma empresa.
	/**
	 * <p>
	 * </p>
	 *
	 * @param obj
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( final Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		if ( obj == null )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		final BomSecaoPdfDTO other = ( BomSecaoPdfDTO ) obj;
		if ( getCodigo() == null )
		{
			if ( other.getCodigo() != null )
			{
				return false;
			}
		}
		else if ( !getCodigo().equals( other.getCodigo() ) )
		{
			return false;
		}
		return true;
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public abstract String getCodigo();

	/**
	 * Gets the numero passageiros.
	 *
	 * @return the numero passageiros
	 */
	public abstract long getNumeroPassageiros();

	/**
	 * Gets the passageiro ab.
	 *
	 * @return the passageiro ab
	 */
	public abstract int getPassageiroAB();

	/**
	 * Gets the passageiro ba.
	 *
	 * @return the passageiro ba
	 */
	public abstract int getPassageiroBA();

	/**
	 * Gets the ponto final.
	 *
	 * @return the ponto final
	 */
	public abstract String getPontoFinal();

	/**
	 * Gets the ponto inicial.
	 *
	 * @return the ponto inicial
	 */
	public abstract String getPontoInicial();

	/**
	 * Gets the receita.
	 *
	 * @return the receita
	 */
	public abstract BigDecimal getReceita();

	/**
	 * Gets the tarifa.
	 *
	 * @return the tarifa
	 */
	public abstract BigDecimal getTarifa();

	/**
	 * Checks if is inoperante.
	 *
	 * @return true, if is inoperante
	 */
	public abstract boolean isInoperante();
}

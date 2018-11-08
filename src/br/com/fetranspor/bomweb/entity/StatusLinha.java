package br.com.fetranspor.bomweb.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The Enum StatusLinha.
 */
public enum StatusLinha
{

	/**
	 * The ativa.
	 */
	ATIVA,
	/**
	 * The cancelada.
	 */
	CANCELADA,
	/**
	 * The paralisada.
	 */
	PARALISADA,
	/**
	 * The subjudice.
	 */
	SUBJUDICE;

	/**
	 * Gets the lista status linha.
	 *
	 * @return the lista status linha
	 */
	public static List<StatusLinha> getListaStatusLinha()
	{
		final List<StatusLinha> lista = new ArrayList<StatusLinha>();
		lista.add( ATIVA );
		lista.add( CANCELADA );
		lista.add( PARALISADA );
		lista.add( SUBJUDICE );
		return lista;
	}

	/**
	 * Gets the nome.
	 *
	 * @param valor
	 *            the valor
	 * @return the nome
	 */
	public static String getNome( final Integer valor )
	{
		final List<StatusLinha> lista = getListaStatusLinha();
		for ( int i = 0; i < lista.size(); i++ )
		{
			if ( lista.get( i ).ordinal() == valor.intValue() )
			{
				return lista.get( i ).name();
			}
		}
		return "";
	}

	/**
	 * Gets the nome formatado.
	 *
	 * @return the nome formatado
	 */
	public String getNomeFormatado()
	{
		final String nome = toString();
		return nome.charAt( 0 ) + nome.substring( 1 ).toLowerCase();
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public Integer getValor()
	{
		return ordinal();
	}
}

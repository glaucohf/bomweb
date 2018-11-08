package br.com.fetranspor.bomweb.dto;

import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomSecao;

import java.math.BigDecimal;
import java.util.Collections;

/**
 * A factory for creating BomLinhaPdfDTO objects.
 */
public class BomLinhaPdfDTOFactory
{

	/**
	 * Copia as tarifas atual e anterior da seção obrigatória do BomLinha.
	 *
	 * @param bomLinhaDto
	 *            the bom linha dto
	 */
	private void copiaTarifasSecaoObrigatoria( final BomLinhaPdfDTO bomLinhaDto )
	{
		bomLinhaDto.tarifa00Anterior = BigDecimal.ZERO;

		for ( final BomSecao bomSecao : bomLinhaDto.bomLinha.getSecoes() )
		{
			if ( bomSecao.ehSecaoObrigatoria() )
			{
				bomLinhaDto.tarifa00Anterior = bomSecao.getTarifaAnterior();
				bomLinhaDto.tarifa00 = bomSecao.getTarifa();
				return;
			}
		}

		// TODO: Será que não deveria ser uma exceção diferente?
		throw new RuntimeException( "BomLinha '" + bomLinhaDto.bomLinha + "' não possui uma seção obrigatória." );
	}

	/**
	 * Cria.
	 *
	 * @param bomDto
	 *            the bom dto
	 * @param bomLinha
	 *            the bom linha
	 * @return the bom linha pdf dto
	 */
	public BomLinhaPdfDTO cria( final BomPdfDTO bomDto, final BomLinha bomLinha )
	{
		final BomLinhaPdfDTO bomLinhaDto = new BomLinhaPdfDTO();

		bomLinhaDto.bomDto = bomDto;
		bomLinhaDto.bomLinha = bomLinha;

		criaEOrdenaBomSecoes( bomLinhaDto );
		copiaTarifasSecaoObrigatoria( bomLinhaDto );

		return bomLinhaDto;
	}

	/**
	 * Cria e ordena bom secoes.
	 *
	 * @param bomLinhaDto
	 *            the bom linha dto
	 */
	protected void criaEOrdenaBomSecoes( final BomLinhaPdfDTO bomLinhaDto )
	{
		for ( final BomSecao bomSecao : bomLinhaDto.bomLinha.getSecoes() )
		{
			final BomSecaoNovaPdfDTO bomSecaoDto = new BomSecaoNovaPdfDTO( bomSecao );

			// Se teve aumento de tarifa, gera uma nova seção, para aparecer em outra linha da
			// tabela de seções.
			if ( bomSecao.aumentouTarifa() )
			{
				bomLinhaDto.bomSecoesDto.add( new BomSecaoAntigaPdfDTO( bomSecaoDto ) );
			}

			bomLinhaDto.bomSecoesDto.add( bomSecaoDto );
		}

		Collections.sort( bomLinhaDto.bomSecoesDto );
	}
}

package br.com.fetranspor.bomweb.dto;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A factory for creating BomPdfDTO objects.
 */
public class BomPdfDTOFactory
{

	/**
	 * Cria.
	 *
	 * @param bom
	 *            the bom
	 * @param bomLinhas
	 *            the bom linhas
	 * @return the bom pdf dto
	 */
	public BomPdfDTO cria( final Bom bom, final List<BomLinha> bomLinhas )
	{
		final BomPdfDTO bomDto = new BomPdfDTO();

		bomDto.bom = bom;
		bomDto.bomLinhas = bomLinhas;

		criaLinhasDto( bomDto );
		criaBomLinhasDto( bomDto );

		return bomDto;
	}

	/**
	 * Cria bom linhas dto.
	 *
	 * @param bomDto
	 *            the bom dto
	 */
	private void criaBomLinhasDto( final BomPdfDTO bomDto )
	{
		if ( Check.isNull( bomDto.bomLinhas ) )
		{
			return;
		}

		for ( final BomLinha bomLinha : bomDto.bomLinhas )
		{
			bomDto.bomLinhasDto.add( new BomLinhaPdfDTOFactory().cria( bomDto, bomLinha ) );
		}
	}

	/**
	 * Cria linhas dto.
	 *
	 * @param bomDto
	 *            the bom dto
	 */
	private void criaLinhasDto( final BomPdfDTO bomDto )
	{
		if ( Check.isNull( bomDto.bomLinhas ) )
		{
			return;
		}

		final Map<LinhaVigencia, List<BomLinha>> bomLinhasPorlinhaVigencia = mapeiaBomLinhasPorLinhaVigencia( bomDto );

		for ( final LinhaVigencia linhaVigencia : bomLinhasPorlinhaVigencia.keySet() )
		{
			final LinhaPdfDTO linhaDto = new LinhaPdfDTO(
				bomDto,
				linhaVigencia,
				bomLinhasPorlinhaVigencia.get( linhaVigencia ) );
			bomDto.linhasDto.add( linhaDto );
		}

		Collections.sort( bomDto.linhasDto );
	}

	/**
	 * Mapeia bom linhas por linha vigencia.
	 *
	 * @param bomDto
	 *            the bom dto
	 * @return the map
	 */
	private Map<LinhaVigencia, List<BomLinha>> mapeiaBomLinhasPorLinhaVigencia( final BomPdfDTO bomDto )
	{
		final Map<LinhaVigencia, List<BomLinha>> bomLinhasPorlinhaVigencia = new HashMap<LinhaVigencia, List<BomLinha>>();

		if ( Check.isNull( bomDto.bomLinhas ) )
		{
			return bomLinhasPorlinhaVigencia;
		}

		for ( final BomLinha bomLinha : bomDto.bomLinhas )
		{
			List<BomLinha> bomLinhasLinhaVigencia = bomLinhasPorlinhaVigencia.get( bomLinha.getLinhaVigencia() );

			if ( Check.isNull( bomLinhasLinhaVigencia ) )
			{
				bomLinhasLinhaVigencia = new ArrayList<BomLinha>();
				bomLinhasPorlinhaVigencia.put( bomLinha.getLinhaVigencia(), bomLinhasLinhaVigencia );
			}

			bomLinhasLinhaVigencia.add( bomLinha );
		}

		return bomLinhasPorlinhaVigencia;
	}
}

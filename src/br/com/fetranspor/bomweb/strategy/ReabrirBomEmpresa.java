package br.com.fetranspor.bomweb.strategy;

import br.com.caelum.vraptor.Result;
import br.com.decatron.framework.exception.BusinessException;
import br.com.fetranspor.bomweb.controller.BomController;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;

import java.util.List;

/**
 * <p>
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 13/05/2016
 */
public class ReabrirBomEmpresa
	extends AbstractReabrirBomStrategy
{

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @param result
	 * @param bomController
	 * @return
	 * @throws BusinessException
	 * @see br.com.fetranspor.bomweb.strategy.AbstractReabrirBomStrategy#reabrir(br.com.fetranspor.bomweb.entity.Bom,
	 *      br.com.caelum.vraptor.Result, br.com.fetranspor.bomweb.business.BomBusiness)
	 */
	@Override
	public List<BomLinha> reabrir( final Bom bom, final Result result, final BomController bomController )
		throws BusinessException
	{
		if ( bomController.podeReabrirBom( bom ) )
		{
			final List<BomLinha> bomLinhaList = bomController.pesquisarBomLinhaByBom( bom );
			for ( final BomLinha bomLinha : bomLinhaList )
			{
				bomLinha.setUltimaJustificativa( "BOM reaberto pela própria empresa." );
			}// for
			bomController.reaberturaAdapter( bom, bomLinhaList, false );

		}// if
		else
		{
			throw new BusinessException( "Esse BOM já foi reaberto 2 vezes e não pode ser reaberto novamente." );
		}
		return null;
	}
}

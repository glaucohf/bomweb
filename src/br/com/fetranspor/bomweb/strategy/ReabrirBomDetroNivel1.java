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
public class ReabrirBomDetroNivel1
	extends AbstractReabrirBomStrategy
{

	/**
	 * <p>
	 * </p>
	 */
	public ReabrirBomDetroNivel1()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @throws BusinessException
	 * @see br.com.fetranspor.bomweb.strategy.AbstractReabrirBomStrategy#reabrir(br.com.fetranspor.bomweb.entity.Bom)
	 */
	@Override
	public List<BomLinha> reabrir( final Bom bom, final Result result, final BomController bomController )
		throws BusinessException
	{
		return bomController.reabrirBomPorDetroNivel1( bom );

	}
}

package br.com.fetranspor.bomweb.strategy;

import br.com.caelum.vraptor.Result;
import br.com.decatron.framework.exception.BusinessException;
import br.com.fetranspor.bomweb.controller.BomController;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 13/05/2016
 */
public abstract class AbstractReabrirBomStrategy
{

	/**
	 * <p>
	 * Field <code>perfilImplementacao</code>
	 * </p>
	 */
	private static Map<Integer, AbstractReabrirBomStrategy> perfilImplementacao = new HashMap<Integer, AbstractReabrirBomStrategy>();

	static
	{
		perfilImplementacao = new HashMap<Integer, AbstractReabrirBomStrategy>();
		final AbstractReabrirBomStrategy reabrirBomDetroNivel1 = new ReabrirBomDetroNivel1();
		final AbstractReabrirBomStrategy reabrirBomEmpresa = new ReabrirBomEmpresa();
		perfilImplementacao.put( 1, reabrirBomDetroNivel1 );
		perfilImplementacao.put( 2, reabrirBomEmpresa );
		perfilImplementacao.put( 3, reabrirBomDetroNivel1 );
		perfilImplementacao.put( 4, reabrirBomDetroNivel1 );
		perfilImplementacao.put( 5, reabrirBomDetroNivel1 );
		perfilImplementacao.put( 6, reabrirBomDetroNivel1 );
		perfilImplementacao.put( 7, reabrirBomDetroNivel1 );
		perfilImplementacao.put( 8, reabrirBomDetroNivel1 );
	}

	/**
	 * <p>
	 * </p>
	 */
	public AbstractReabrirBomStrategy()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the perfilImplementacao.
	 * @see #perfilImplementacao
	 */
	public static Map<Integer, AbstractReabrirBomStrategy> getPerfilImplementacao()
	{
		return perfilImplementacao;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param perfilImplementacao
	 *            The perfilImplementacao to set.
	 * @see #perfilImplementacao
	 */
	public static void setPerfilImplementacao( final Map<Integer, AbstractReabrirBomStrategy> perfilImplementacao )
	{
		AbstractReabrirBomStrategy.perfilImplementacao = perfilImplementacao;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @param result
	 * @param controller
	 * @return
	 * @throws BusinessException
	 */
	public abstract List<BomLinha> reabrir( final Bom bom, Result result, BomController controller )
		throws BusinessException;

}

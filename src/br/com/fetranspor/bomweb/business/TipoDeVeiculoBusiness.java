package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.dao.TipoDeVeiculoDAO;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;

import java.util.List;

/**
 * The Class TipoDeVeiculoBusiness.
 */
@Component
public class TipoDeVeiculoBusiness
	extends VRaptorBusiness<TipoDeVeiculo>
{

	/**
	 * Instantiates a new tipo de veiculo business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 */
	public TipoDeVeiculoBusiness( final VRaptorProvider provider, final TipoDeVeiculoDAO dao )
	{
		super( provider );
		this.dao = dao;
	}

	/**
	 * Findby code.
	 *
	 * @param code
	 *            the code
	 * @return the tipo de veiculo
	 */
	public TipoDeVeiculo findbyCode( final String code )
	{
		return ( ( TipoDeVeiculoDAO ) this.dao ).findbyCode( code );
	}

	/**
	 * Listar tipos de veiculo permitidos.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	public List<TipoDeVeiculo> listarTiposDeVeiculoPermitidos( final Linha linha )
	{
		return ( ( TipoDeVeiculoDAO ) this.dao ).listarTiposDeVeiculoPermitidos( linha );
	}

	/**
	 * Removes the zeros a esquerda.
	 *
	 * @param codigo
	 *            the codigo
	 * @return the string
	 * @throws BusinessException
	 *             the business exception
	 */
	public String removeZerosAEsquerda( final String codigo )
		throws BusinessException
	{
		String codigoAjustado = null;
		try
		{
			codigoAjustado = Integer.valueOf( codigo ).toString();
		}
		catch ( final NumberFormatException e )
		{
			throw new BusinessException( "Não foi possível converter a string " + codigo + " para inteiro.", e );
		}
		return codigoAjustado;
	}

	/**
	 * Tipo de veiculo ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @return true, if successful
	 */
	public boolean tipoDeVeiculoJaExiste( final String codigo )
	{
		return ( ( TipoDeVeiculoDAO ) this.dao ).tipoDeVeiculoJaExiste( codigo );
	}

	/**
	 * Tipo de veiculo ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean tipoDeVeiculoJaExiste( final String codigo, final Long id )
	{
		return ( ( TipoDeVeiculoDAO ) this.dao ).tipoDeVeiculoJaExiste( codigo, id );
	}
}

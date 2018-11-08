package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.PerfilPermissaoBusiness;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Permissao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class PermissaoController.
 */
@Resource
public class PermissaoController
	extends VRaptorController
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( PermissaoController.class );

	/**
	 * Instantiates a new permissao controller.
	 *
	 * @param provider
	 *            the provider
	 * @param perfilPermissaoBusiness
	 *            the perfil permissao business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 */
	public PermissaoController(
		final VRaptorProvider provider,
		final PerfilPermissaoBusiness perfilPermissaoBusiness,
		final Result result,
		final Validator validator )
	{
		super( provider, result, validator );
		this.perfilPermissaoBusiness = perfilPermissaoBusiness;
	}

	/**
	 * List.
	 */
	public void list()
	{
		this.list( null );
	}

	/**
	 * List.
	 *
	 * @param perfil
	 *            the perfil
	 */
	public void list( final Perfil perfil )
	{

		this.result.include( "perfil", perfil );
		this.result.include( "perfis", this.perfilPermissaoBusiness.perfis() );

		if ( Check.isNull( perfil ) )
		{
			return;
		}

		this.result.include( "permissoes", this.perfilPermissaoBusiness.preencheDTOPermissoes( perfil ) );

	}

	/**
	 * Save.
	 *
	 * @param permissoes
	 *            the permissoes
	 * @param perfil
	 *            the perfil
	 */
	public void save( final List<Permissao> permissoes, final Perfil perfil )
	{
		try
		{
			this.perfilPermissaoBusiness.save( perfil, permissoes );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
		}
		addInfoMessage( "Salvo com sucesso." );
		this.result.redirectTo( ConfiguracaoController.class ).list();
	}

	/**
	 * The perfil permissao business.
	 */
	private final PerfilPermissaoBusiness perfilPermissaoBusiness;

}

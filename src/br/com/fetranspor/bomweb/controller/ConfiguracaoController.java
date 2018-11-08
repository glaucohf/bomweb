package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.business.ConfiguracaoBusiness;
import br.com.fetranspor.bomweb.entity.Configuracao;

import java.util.List;

/**
 * The Class ConfiguracaoController.
 */
@Resource
public class ConfiguracaoController
	extends VRaptorController
{

	/**
	 * Instantiates a new configuracao controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 */
	public ConfiguracaoController(
		final VRaptorProvider provider,
		final ConfiguracaoBusiness business,
		final Result result,
		final Validator validator )
	{
		super( provider, result, validator );
		this.business = business;
	}

	/**
	 * Bom.
	 *
	 * @return the list
	 */
	public List<Configuracao> bom()
	{
		return this.business.buscarConfiguracoesBom();
	}

	/**
	 * List.
	 */
	public void list()
	{
		// DOES NOTHING
	}

	/**
	 * Salvar.
	 *
	 * @param configuracao
	 *            the configuracao
	 */
	public void salvar( final Configuracao configuracao )
	{
		this.business.atualizarConfiguracao( configuracao );
		addInfoMessage( "Salvo com sucesso." );
		this.result.redirectTo( this ).bom();
		// result.forwardTo(this).list();
	}

	/**
	 * Salvar configuracoes.
	 *
	 * @param configuracoes
	 *            the configuracoes
	 */
	public void salvarConfiguracoes( final List<Configuracao> configuracoes )
	{
		for ( final Configuracao configuracao : configuracoes )
		{
			this.business.atualizarConfiguracao( configuracao );
		}
		addInfoMessage( "Salvo com sucesso." );
		this.result.redirectTo( this ).bom();
	}

	/**
	 * The business.
	 */
	private final ConfiguracaoBusiness business;

}

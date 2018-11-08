package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.provider.VRaptorProvider;

/**
 * The Class HomeController.
 */
@Resource
public class HomeController
	extends VRaptorController
{

	/**
	 * Instantiates a new home controller.
	 *
	 * @param provider
	 *            the provider
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 */
	public HomeController( final VRaptorProvider provider, final Result result, final Validator validator )
	{
		super( provider, result, validator );
	}

	/**
	 * Home.
	 */
	public void home()
	{
		// DOES NOTHING
	}

}

package br.com.fetranspor.bomweb.components;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.decatron.framework.security.vraptor.VRaptorAuthorization;

import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * The Class UserInterceptor.
 */
@Intercepts
@RequestScoped
public class UserInterceptor
	implements Interceptor
{

	/**
	 * Instantiates a new user interceptor.
	 *
	 * @param authorization
	 *            the authorization
	 */
	public UserInterceptor( final VRaptorAuthorization authorization )
	{
		this.authorization = authorization;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param method
	 * @return
	 * @see br.com.caelum.vraptor.interceptor.Interceptor#accepts(br.com.caelum.vraptor.resource.ResourceMethod)
	 */
	@Override
	public boolean accepts( final ResourceMethod method )
	{
		return true;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param stack
	 * @param method
	 * @param resourceInstance
	 * @throws InterceptionException
	 * @see br.com.caelum.vraptor.interceptor.Interceptor#intercept(br.com.caelum.vraptor.core.InterceptorStack,
	 *      br.com.caelum.vraptor.resource.ResourceMethod, java.lang.Object)
	 */
	@Override
	public void intercept( final InterceptorStack stack, final ResourceMethod method, final Object resourceInstance )
		throws InterceptionException
	{

		if ( this.authorization.isUserLogged() )
		{
			TransactionSynchronizationManager.unbindResourceIfPossible( "user" );
			TransactionSynchronizationManager.bindResource( "user", this.authorization.getUser() );
		}

		stack.next( method, resourceInstance );
	}

	/**
	 * The authorization.
	 */
	private final VRaptorAuthorization authorization;

}

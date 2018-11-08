package br.com.fetranspor.bomweb.components;

import br.com.caelum.vraptor.InterceptionException;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.core.InterceptorStack;
import br.com.caelum.vraptor.interceptor.Interceptor;
import br.com.caelum.vraptor.ioc.RequestScoped;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.decatron.framework.security.vraptor.VRaptorAuthorization;
import br.com.fetranspor.bomweb.controller.LoginController;

import javax.servlet.http.HttpServletRequest;

/**
 * The Class VRaptorSecurityInterceptor.
 */
@Intercepts
@RequestScoped
public final class VRaptorSecurityInterceptor
	implements Interceptor
{

	/**
	 * Instantiates a new v raptor security interceptor.
	 *
	 * @param authorization
	 *            the authorization
	 * @param request
	 *            the request
	 * @param result
	 *            the result
	 */
	public VRaptorSecurityInterceptor(
		final VRaptorAuthorization authorization,
		final HttpServletRequest request,
		final Result result )
	{
		this.authorization = authorization;
		this.request = request;
		this.result = result;
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

		String url = this.request.getRequestURI().replaceFirst( this.request.getContextPath(), "" );
		url = url.replaceAll( "/[0-9]*$", "" );

		url.isEmpty();
		final boolean isLoginPage = url.contains( "/login" );
		final boolean isLostPassword = url.contains( "/esquecisenha" );
		final boolean isNewPassword = url.contains( "/novasenha" );
		final boolean isAutenticator = url.contains( "/autenticar" );
		url.contains( "/acessonegado" );
		final boolean isChangePassword = url.contains( "/trocarsenha" );
		final boolean isExit = url.contains( "/sair" );

		if ( this.authorization.isUserLogged() )
		{
			if ( ( this.authorization.isChangePassword() ) && ( !isChangePassword ) && ( !isExit ) )
			{
				this.result.redirectTo( LoginController.class ).trocarSenha();
			}
			else
			{
				// if( (isHome) || (isChangePassword) || (isExit) || (isAccessDenied) ||
				// (authorization.hasUriPermission(url)) ) {
				stack.next( method, resourceInstance );
				// } else {
				// result.redirectTo(LoginController.class).acessonegado();
				// }
			}
		}
		else
		{
			if ( ( isLoginPage ) || ( isLostPassword ) || ( isNewPassword ) || ( isAutenticator ) )
			{
				stack.next( method, resourceInstance );
			}
			else
			{
				this.result.redirectTo( LoginController.class ).login();
			}
		}

	}

	/**
	 * The authorization.
	 */
	private final VRaptorAuthorization authorization;

	/**
	 * The request.
	 */
	private final HttpServletRequest request;

	/**
	 * The result.
	 */
	private final Result result;

}

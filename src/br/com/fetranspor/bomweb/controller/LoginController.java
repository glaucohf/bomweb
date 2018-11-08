package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.security.ChangePasswordInformations;
import br.com.decatron.framework.security.Credentials;
import br.com.decatron.framework.security.vraptor.VRaptorAuthenticator;
import br.com.decatron.framework.util.Check;
import br.com.decatron.framework.vraptor.Contexts;
import br.com.fetranspor.bomweb.business.UsuarioPerfilBusiness;
import br.com.fetranspor.bomweb.entity.Configuracao;
import br.com.fetranspor.bomweb.exception.ValidationException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class LoginController.
 *
 * @author Daniel Passos
 */
@Resource
public class LoginController
	extends VRaptorController
{

	/**
	 * The Constant TAMANHO_MIN_SENHA.
	 */
	private static final int TAMANHO_MIN_SENHA = 8;

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LoginController.class );

	/**
	 * Instantiates a new login controller.
	 *
	 * @param provider
	 *            the provider
	 * @param authenticator
	 *            the authenticator
	 * @param usuarioPerfilBusiness
	 *            the usuario perfil business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param contexts
	 *            the contexts
	 */
	public LoginController(
		final VRaptorProvider provider,
		final VRaptorAuthenticator authenticator,
		final UsuarioPerfilBusiness usuarioPerfilBusiness,
		final Result result,
		final Validator validator,
		final Contexts contexts )
	{
		super( provider, result, validator );
		this.authenticator = authenticator;
		this.usuarioPerfilBusiness = usuarioPerfilBusiness;
		contexts.getHttpSession().setAttribute( "version", Configuracao.getVersion() );
	}

	/**
	 * Acessonegado.
	 */
	public void acessonegado()
	{
	}

	/**
	 * Autenticar.
	 *
	 * @param credentials
	 *            the credentials
	 */
	public void autenticar( final Credentials credentials )
	{
		if ( this.authenticator.authenticate( credentials ) )
		{
			this.result.redirectTo( HomeController.class ).home();
		}
		else
		{
			addErrorMessage( "Login ou Senha inválidos." );
			this.result.redirectTo( this ).login();
		}
	}

	/**
	 * Esqueci senha.
	 */
	public void esqueciSenha()
	{
	}

	/**
	 * Login.
	 */
	public void login()
	{
		if ( isUserLogged() )
		{
			this.result.redirectTo( HomeController.class ).home();
		}
	}

	/**
	 * Nova senha.
	 *
	 * @param credentials
	 *            the credentials
	 */
	public void novaSenha( final Credentials credentials )
	{
		try
		{
			if ( Check.isNotNull( credentials ) && Check.isNotBlank( credentials.getUsername() ) )
			{
				this.usuarioPerfilBusiness.redefineSenhaUsuario( credentials.getUsername(), null );
				addInfoMessage( "Senha enviada com sucesso para o email cadastrado." );
				this.result.redirectTo( this ).login();
			}
			else
			{
				addErrorMessage( new ValidationException( "Por favor, informe o seu Login." ) );
				this.result.redirectTo( this ).esqueciSenha();
			}
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
			this.result.redirectTo( this ).esqueciSenha();
		}
	}

	/**
	 * Sair.
	 */
	public void sair()
	{
		this.authenticator.logout();
		this.result.redirectTo( LoginController.class ).login();
	}

	/**
	 * Trocar senha.
	 */
	public void trocarSenha()
	{
	}

	/**
	 * Trocar senha.
	 *
	 * @param changePasswordInformations
	 *            the change password informations
	 */
	public void trocarSenha( final ChangePasswordInformations changePasswordInformations )
	{
		// TODO: PASSAR A VALIDACAO PARA BUSINESS
		final String novaSenha = changePasswordInformations.getNewPassord();
		if ( ( novaSenha.length() < TAMANHO_MIN_SENHA )
			|| !novaSenha.matches( "(.*[A-Z].*)" )
			|| !novaSenha.matches( "(.*[0-9].*)" ) )
		{
			this.validator.add( new ValidationMessage(
				"A Senha deve possuir ao menos 8 caracteres, tendo ao menos um numeral e uma letra maiúscula.",
				"Erro" ) );
			this.validator.onErrorUsePageOf( LoginController.class ).trocarSenha();
		}
		else if ( this.authenticator.changePassword( changePasswordInformations ) )
		{
			addInfoMessage( "Senha alterada com sucesso." );
			this.result.redirectTo( HomeController.class ).home();
		}
		else
		{
			this.validator.add( new ValidationMessage( "Login ou Senha inválidos.", "Erro" ) );
			this.validator.onErrorUsePageOf( LoginController.class ).trocarSenha();
		}
	}

	/**
	 * The authenticator.
	 */
	private final VRaptorAuthenticator authenticator;

	/**
	 * The usuario perfil business.
	 */
	private final UsuarioPerfilBusiness usuarioPerfilBusiness;

}

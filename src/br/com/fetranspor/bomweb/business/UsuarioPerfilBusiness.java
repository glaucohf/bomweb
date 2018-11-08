package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.exception.MailException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.security.Encrypt;
import br.com.decatron.framework.security.Encrypt.AlgorithmType;
import br.com.decatron.framework.security.Password;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.dao.UsuarioPerfilDAO;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil.UsuarioPerfilPK;
import br.com.fetranspor.bomweb.util.UtilitiesMail;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class UsuarioPerfilBusiness.
 */
@Component
public class UsuarioPerfilBusiness
	extends VRaptorBusiness<UsuarioPerfil>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( UsuarioPerfilBusiness.class );

	/**
	 * Instantiates a new usuario perfil business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 * @param configuracaoBusiness
	 *            the configuracao business
	 */
	public UsuarioPerfilBusiness(
		final VRaptorProvider provider,
		final UsuarioPerfilDAO dao,
		final ConfiguracaoBusiness configuracaoBusiness )
	{
		super( provider );
		this.dao = dao;
		this.usuarioPerfilDAO = dao;
		this.configuracaoBusiness = configuracaoBusiness;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#afterSave(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void afterSave( final UsuarioPerfil entity )
		throws BusinessException
	{
		super.afterSave( entity );
		try
		{
			final String from = this.configuracaoBusiness.buscarEmailDetro();
			final String to = entity.getUsuario().getEmail();
			final String body = "Seja bem-vindo(a)!\nSeu cadastro foi realizado com sucesso.\n\nDados de acesso:\nLogin: "
				+ entity.getUsuario().getLogin()
				+ " Senha: "
				+ entity.getUsuario().getSenhaNaoCriptografada();
			final String subject = "Cadastro de Usuário";

			UtilitiesMail.sendMail( from, to, subject, body );
		}
		catch ( final MailException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#beforeSave(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void beforeSave( final UsuarioPerfil entity )
		throws BusinessException
	{
		super.beforeSave( entity );
		if ( !entity.getPerfil().isEmpresa() )
		{
			entity.getUsuario().setEmpresa( null );
		}

		if ( usuarioJaExiste( entity.getUsuario().getLogin() ) )
		{
			throw new BusinessException( "Login já existente. Favor verificar o Login do Usuário." );
		}
		if ( entity.getPerfil().isEmpresa() )
		{
			if ( this.usuarioPerfilDAO.existeUsuarioAtivoDaEmpresa( entity.getUsuario().getEmpresa().getId() ) )
			{
				throw new BusinessException( "A Empresa selecionada já possui Usuário cadastrado." );
			}
		}
	}

	/**
	 * Buscar listagem usuarios ativos.
	 *
	 * @return the list
	 */
	public List<Usuario> buscarListagemUsuariosAtivos()
	{
		return this.usuarioPerfilDAO.buscarListagemUsuariosAtivos();
	}

	/**
	 * Buscar usuario.
	 *
	 * @param id
	 *            the id
	 * @return the usuario
	 */
	public Usuario buscarUsuario( final Integer id )
	{
		return this.usuarioPerfilDAO.buscarUsuario( id );
	}

	/**
	 * Buscar usuario.
	 *
	 * @param username
	 *            the username
	 * @return the usuario
	 */
	public Usuario buscarUsuario( final String username )
	{
		return this.usuarioPerfilDAO.buscarUsuario( username );
	}

	// ----------------------------------------------------------------------

	/**
	 * Buscar usuario para log.
	 *
	 * @param id
	 *            the id
	 * @return the usuario
	 */
	public Usuario buscarUsuarioParaLog( final Integer id )
	{
		return this.usuarioPerfilDAO.buscarUsuarioParaLog( id );
	}

	/**
	 * Buscar usuario perfil.
	 *
	 * @param usuario
	 *            the usuario
	 * @return the usuario perfil
	 */
	public UsuarioPerfil buscarUsuarioPerfil( final Usuario usuario )
	{
		return this.usuarioPerfilDAO.buscarUsuarioPerfil( usuario );
	}

	/**
	 * Buscar usuario perfil para log.
	 *
	 * @param usuario
	 *            the usuario
	 * @return the usuario perfil
	 */
	public UsuarioPerfil buscarUsuarioPerfilParaLog( final Usuario usuario )
	{
		return this.usuarioPerfilDAO.buscarUsuarioPerfilParaLog( usuario );
	}

	/**
	 * Existe usuario ativo da empresa.
	 *
	 * @param idEmpresa
	 *            the id empresa
	 * @return true, if successful
	 */
	public boolean existeUsuarioAtivoDaEmpresa( final Long idEmpresa )
	{
		return this.usuarioPerfilDAO.existeUsuarioAtivoDaEmpresa( idEmpresa );
	}

	/**
	 * Perfil.
	 *
	 * @param usuario
	 *            the usuario
	 * @return the perfil
	 */
	public Perfil perfil( final Usuario usuario )
	{
		return this.usuarioPerfilDAO.perfil( usuario );
	}

	/**
	 * Perfis.
	 *
	 * @return the list
	 */
	public List<Perfil> perfis()
	{
		return this.usuarioPerfilDAO.perfis();
	}

	/**
	 * Redefine senha usuario.
	 *
	 * @param usuarioId
	 *            the usuario id
	 * @param mensagem
	 *            the mensagem
	 * @throws BusinessException
	 *             the business exception
	 */
	public void redefineSenhaUsuario( final Integer usuarioId, final String mensagem )
		throws BusinessException
	{
		final Usuario usuarioPersistido = buscarUsuario( usuarioId );
		redefineSenhaUsuario( usuarioPersistido, mensagem );
	}

	/**
	 * Redefine senha usuario.
	 *
	 * @param username
	 *            the username
	 * @param mensagem
	 *            the mensagem
	 * @throws BusinessException
	 *             the business exception
	 */
	public void redefineSenhaUsuario( final String username, final String mensagem )
		throws BusinessException
	{
		final Usuario usuarioPersistido = buscarUsuario( username );
		redefineSenhaUsuario( usuarioPersistido, mensagem );
	}

	/**
	 * Redefine senha usuario.
	 *
	 * @param usuarioPersistido
	 *            the usuario persistido
	 * @param mensagem
	 *            the mensagem
	 * @throws BusinessException
	 *             the business exception
	 */
	private void redefineSenhaUsuario( final Usuario usuarioPersistido, final String mensagem )
		throws BusinessException
	{
		if ( Check.isNull( usuarioPersistido ) )
		{
			throw new BusinessException( "Não foi possível encontrar o usuário no banco de dados." );
		}

		usuarioPersistido.setTrocarSenha( Boolean.TRUE );
		usuarioPersistido.setSenhaNaoCriptografada( new Password()
			.size( 8 )
			.useCharacter( true )
			.useNumber( true )
			.useUpperCaseCharacter( true )
			.generate() );
		usuarioPersistido.setSenha( new Encrypt().type( AlgorithmType.SHA256 ).encrypt(
			usuarioPersistido.getSenhaNaoCriptografada() ) );

		try
		{

			final StringBuffer sb = new StringBuffer();
			if ( Check.isNotBlank( mensagem ) )
			{
				sb.append( mensagem );
			}
			sb.append( "Sua senha foi redefinida com sucesso.\nNovos dados de acesso:\nLogin: "
				+ usuarioPersistido.getLogin()
				+ " Senha: "
				+ usuarioPersistido.getSenhaNaoCriptografada() );

			this.usuarioPerfilDAO.salvaUsuario( usuarioPersistido );
			final String from = this.configuracaoBusiness.buscarEmailDetro();
			final String to = usuarioPersistido.getEmail();
			final String body = sb.toString();
			final String subject = "Redefinição de senha de Usuário";

			UtilitiesMail.sendMail( from, to, subject, body );

		}
		catch ( final DaoException e )
		{
			throw new BusinessException( "Não foi possível gerar uma nova senha para o usuário.", e );
		}
		catch ( final MailException e )
		{
			throw new BusinessException( "Não foi possível enviar o email com a redefinição de senha.", e );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#update(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public UsuarioPerfil update( final UsuarioPerfil entity )
		throws BusinessException
	{

		// Usuario

		final Usuario usuario = buscarUsuario( entity.getUsuario().getId() );
		// usuario.setEmpresa(entity.getUsuario().getEmpresa());
		usuario.setNome( entity.getUsuario().getNome() );
		// usuario.setLogin(entity.getUsuario().getLogin());
		usuario.setEmail( entity.getUsuario().getEmail() );

		this.usuarioPerfilDAO.updateUsuario( usuario );

		// Perfil

		final Perfil perfil = this.usuarioPerfilDAO.perfil( usuario );

		this.usuarioPerfilDAO.removerAssociacoes( entity.getUsuario() );

		// Usuario x Perfil

		final UsuarioPerfilPK usuarioPerfilPK = new UsuarioPerfilPK( usuario.getId(), perfil.getId() );
		entity.setId( usuarioPerfilPK );
		entity.setUsuario( usuario );
		entity.setPerfil( perfil );
		entity.setActive( Boolean.TRUE );

		try
		{
			this.usuarioPerfilDAO.atualizaDados( entity );
		}
		catch ( final DaoException e )
		{
			throw new BusinessException( e );
		}

		return entity;

	}

	/**
	 * Usuario ja existe.
	 *
	 * @param login
	 *            the login
	 * @return true, if successful
	 */
	public boolean usuarioJaExiste( final String login )
	{
		return this.usuarioPerfilDAO.usuarioJaExiste( login );
	}

	/**
	 * The usuario perfil dao.
	 */
	private final UsuarioPerfilDAO usuarioPerfilDAO;

	/**
	 * The configuracao business.
	 */
	private final ConfiguracaoBusiness configuracaoBusiness;
}

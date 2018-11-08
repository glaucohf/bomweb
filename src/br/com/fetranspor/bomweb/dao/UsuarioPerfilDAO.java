package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.security.Encrypt;
import br.com.decatron.framework.security.Encrypt.AlgorithmType;
import br.com.decatron.framework.security.Password;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil.UsuarioPerfilPK;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * The Class UsuarioPerfilDAO.
 */
@Component
public class UsuarioPerfilDAO
	extends VRaptorHibernateDAO<UsuarioPerfil>
{

	/**
	 * Instantiates a new usuario perfil dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public UsuarioPerfilDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	/**
	 * Atualiza dados.
	 *
	 * @param usuarioPerfil
	 *            the usuario perfil
	 * @throws DaoException
	 *             the dao exception
	 */
	public void atualizaDados( final UsuarioPerfil usuarioPerfil )
		throws DaoException
	{
		this.session.save( usuarioPerfil.getUsuario() );
		final UsuarioPerfilPK usuarioPerfilPK = new UsuarioPerfilPK( usuarioPerfil.getUsuario().getId(), usuarioPerfil
			.getPerfil()
			.getId() );
		usuarioPerfil.setId( usuarioPerfilPK );
		usuarioPerfil.setActive( Boolean.TRUE );
		super.save( usuarioPerfil );
	}

	/**
	 * Buscar listagem usuarios ativos.
	 *
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Usuario> buscarListagemUsuariosAtivos()
	{
		return this.session.createQuery(
			"select up.usuario from UsuarioPerfil up "
				+ " inner join fetch up.usuario.empresa emp "
				+ " where up.usuario.active = 1 order by emp.id " ).list();
	}

	/**
	 * Buscar usuario.
	 *
	 * @param usuarioId
	 *            the usuario id
	 * @return the usuario
	 */
	public Usuario buscarUsuario( final Integer usuarioId )
	{
		return ( Usuario ) this.session
			.createQuery( "select up.usuario from UsuarioPerfil up where up.usuario.id = :id and up.active = 1" )
			.setParameter( "id", usuarioId )
			.uniqueResult();
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
		return ( Usuario ) this.session
			.createQuery(
				"select up.usuario from UsuarioPerfil up where up.usuario.login = :username and up.active = 1" )
			.setParameter( "username", username )
			.uniqueResult();
	}

	/**
	 * Buscar usuario para log.
	 *
	 * @param usuarioId
	 *            the usuario id
	 * @return the usuario
	 */
	public Usuario buscarUsuarioParaLog( final Integer usuarioId )
	{
		return ( Usuario ) this.session
			.createQuery( "select up.usuario from UsuarioPerfil up where up.usuario.id = :id" )
			.setParameter( "id", usuarioId )
			.uniqueResult();
	}

	/**
	 * Buscar usuario perfil.
	 *
	 * @param idUsuario
	 *            the id usuario
	 * @return the usuario perfil
	 */
	public UsuarioPerfil buscarUsuarioPerfil( final Long idUsuario )
	{
		return ( UsuarioPerfil ) this.session
			.createCriteria( UsuarioPerfil.class )
			.add( Restrictions.eq( "usuario.id", idUsuario ) )
			.uniqueResult();
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
		return ( UsuarioPerfil ) this.session
			.createCriteria( UsuarioPerfil.class )
			.add( Restrictions.eq( "usuario", usuario ) )
			.createAlias( "usuario", "u" )
			.uniqueResult();
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
		return ( UsuarioPerfil ) this.session
			.createCriteria( UsuarioPerfil.class )
			.add( Restrictions.eq( "usuario", usuario ) )
			.createAlias( "usuario", "u" )
			.uniqueResult();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param usuarioPerfil
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#delete(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void delete( final UsuarioPerfil usuarioPerfil )
		throws DaoException
	{
		final Usuario usuario = buscarUsuario( usuarioPerfil.getUsuario().getId() );

		this.session
			.createQuery( "update UsuarioPerfil up set active = 0 where up.usuario = :usuario" )
			.setParameter( "usuario", usuario )
			.executeUpdate();

		usuario.setActive( false );
		usuario.setAtivo( false );
		this.session.update( usuario );
		/*
		 * session.createQuery(
		 * "update from Usuario u set u.active = 0, u.ativo = 0 where u.id = :usuarioId")
		 * .setParameter("usuarioId", usuario.getId()) .executeUpdate();
		 */

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

		final StringBuffer sql = new StringBuffer();

		if ( Check.isNotBlank( idEmpresa.toString() ) )
		{
			sql.append( "select 1 from usuarios u " );
			sql.append( "where u.ativo = 1 " );
			sql.append( "and u.empresa_id = :idEmpresa " );

			final Query q = this.session.createSQLQuery( sql.toString() );
			q.setParameter( "idEmpresa", idEmpresa );
			return q.list().isEmpty() ? false : true;
		}
		return false;
	}

	/**
	 * Gets the usuarios ativos da empresa.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the usuarios ativos da empresa
	 */
	@SuppressWarnings( "unchecked" )
	public List<Usuario> getUsuariosAtivosDaEmpresa( final Empresa empresa )
	{

		final Criteria criteria = this.session.createCriteria( Usuario.class );
		criteria.add( Restrictions.eq( "active", true ) );
		criteria.add( Restrictions.eq( "empresa", empresa ) );
		return criteria.list();
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
		return ( Perfil ) this.session
			.createQuery( "select up.perfil from UsuarioPerfil up where up.usuario = :usuario" )
			.setParameter( "usuario", usuario )
			.uniqueResult();
	}

	/**
	 * Perfis.
	 *
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Perfil> perfis()
	{
		final Usuario user = ( Usuario ) getUser();
		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			return this.session.createQuery( "from Perfil p where p.id = 2" ).list();
		}
		return this.session.createQuery( "from Perfil" ).list();
	}

	/**
	 * Remover associacoes.
	 *
	 * @param usuario
	 *            the usuario
	 */
	public void removerAssociacoes( final Usuario usuario )
	{
		this.session
			.createQuery( "delete from UsuarioPerfil up where up.usuario = :usuario" )
			.setParameter( "usuario", usuario )
			.executeUpdate();
	}

	/**
	 * Salva usuario.
	 *
	 * @param usuario
	 *            the usuario
	 * @throws DaoException
	 *             the dao exception
	 */
	public void salvaUsuario( final Usuario usuario )
		throws DaoException
	{
		this.session.save( usuario );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param usuarioPerfil
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#save(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void save( final UsuarioPerfil usuarioPerfil )
		throws DaoException
	{
		final Usuario usuario = usuarioPerfil.getUsuario();
		usuario.setAtivo( Boolean.TRUE );
		usuario.setActive( Boolean.TRUE );
		usuario.setTrocarSenha( Boolean.TRUE );
		// TODO passar para business
		usuario.setSenhaNaoCriptografada( new Password()
			.size( 8 )
			.useCharacter( true )
			.useNumber( true )
			.useUpperCaseCharacter( true )
			.generate() );
		usuario.setSenha( new Encrypt().type( AlgorithmType.SHA256 ).encrypt( usuario.getSenhaNaoCriptografada() ) );
		this.session.save( usuarioPerfil.getUsuario() );
		final UsuarioPerfilPK usuarioPerfilPK = new UsuarioPerfilPK( usuarioPerfil.getUsuario().getId(), usuarioPerfil
			.getPerfil()
			.getId() );
		usuarioPerfil.setId( usuarioPerfilPK );
		usuarioPerfil.setActive( Boolean.TRUE );
		super.save( usuarioPerfil );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param usuarioPerfil
	 * @return
	 * @see br.com.decatron.framework.dao.AbstractDao#search(br.com.decatron.framework.entity.EntityBase)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<UsuarioPerfil> search( final UsuarioPerfil usuarioPerfil )
	{

		final Usuario user = ( Usuario ) getUser();

		final Criteria criteria = this.session.createCriteria( UsuarioPerfil.class );
		criteria.add( Restrictions.eq( "active", true ) );

		criteria.createAlias( "usuario", "u" );
		criteria.createAlias( "perfil", "p" );

		if ( Check.isNotNull( usuarioPerfil ) )
		{

			if ( Check.isNotNull( usuarioPerfil.getUsuario() )
				&& Check.isNotBlank( usuarioPerfil.getUsuario().getNome() ) )
			{
				criteria.add( Restrictions.ilike( "u.nome", usuarioPerfil.getUsuario().getNome(), MatchMode.ANYWHERE ) );
			}

			if ( Check.isNotNull( usuarioPerfil.getPerfil() )
				&& ( Check.isNotNull( usuarioPerfil.getPerfil().getId() ) ) )
			{
				criteria.add( Restrictions.eq( "perfil", usuarioPerfil.getPerfil() ) );
			}
		}

		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			criteria.add( Restrictions.eq( "u.id", user.getId() ) );
		}

		return criteria.list();

	}

	/**
	 * Update usuario.
	 *
	 * @param usuario
	 *            the usuario
	 */
	public void updateUsuario( final Usuario usuario )
	{
		this.session.update( usuario );
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
		final Criteria criteria = this.session.createCriteria( Usuario.class );
		criteria.add( Restrictions.eq( "active", true ) );
		criteria.add( Restrictions.eq( "login", login ) );
		return criteria.list().size() > 0 ? Boolean.TRUE : Boolean.FALSE;
	}

}

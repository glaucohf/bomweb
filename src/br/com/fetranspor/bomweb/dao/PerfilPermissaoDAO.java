package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.dto.AcaoDTO;
import br.com.fetranspor.bomweb.dto.RecursoDTO;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.PerfilPermissao;
import br.com.fetranspor.bomweb.entity.Permissao;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * The Class PerfilPermissaoDAO.
 */
@Component
public class PerfilPermissaoDAO
	extends VRaptorHibernateDAO<PerfilPermissao>
{

	/**
	 * Instantiates a new perfil permissao dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public PerfilPermissaoDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	/**
	 * Acoes.
	 *
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<String> acoes()
	{
		return this.session.createQuery( "select distinct p.acao  from Permissao p" ).list();
	}

	/**
	 * Apagar permissoes por.
	 *
	 * @param perfil
	 *            the perfil
	 */
	public void apagarPermissoesPor( final Perfil perfil )
	{
		final String hql = "delete from PerfilPermissao where perfil.id = :idPerfil";
		this.session.createQuery( hql ).setParameter( "idPerfil", perfil.getId() ).executeUpdate();
	}

	/**
	 * Buscar usuario.
	 *
	 * @param usuarioId
	 *            the usuario id
	 * @return the usuario
	 */
	public Usuario buscarUsuario( final Serializable usuarioId )
	{
		return ( Usuario ) this.session
			.createQuery( "select up.usuario from UsuarioPerfil up where up.usuario.id = :id" )
			.setParameter( "id", usuarioId )
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
			.uniqueResult();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#delete(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void delete( final PerfilPermissao entity )
		throws DaoException
	{
		this.session.delete( entity );
	}

	/**
	 * Find perfil permissao.
	 *
	 * @param idPerfil
	 *            the id perfil
	 * @param idPermissao
	 *            the id permissao
	 * @return the perfil permissao
	 */
	public PerfilPermissao findPerfilPermissao( final Integer idPerfil, final Integer idPermissao )
	{
		return ( PerfilPermissao ) this.session
			.createQuery( "from PerfilPermissao pp where pp.perfil.id = :idperfil and pp.permissao.id = :idpermissao " )
			.setParameter( "idperfil", idPerfil )
			.setParameter( "idpermissao", idPermissao )
			.uniqueResult();
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
	 * Permissoes.
	 *
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Permissao> permissoes()
	{
		return this.session
			.createCriteria( Permissao.class )
			.addOrder( Order.asc( "recurso" ) )
			.addOrder( Order.asc( "acao" ) )
			.list();
	}

	/**
	 * Permissoes.
	 *
	 * @param perfil
	 *            the perfil
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Permissao> permissoes( final Perfil perfil )
	{
		return this.session
			.createQuery( "select pp.permissao from PerfilPermissao pp where pp.perfil = :perfil" )
			.setParameter( "perfil", perfil )
			.list();
	}

	/**
	 * Pesquisar.
	 *
	 * @param perfil
	 *            the perfil
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<PerfilPermissao> pesquisar( final Perfil perfil )
	{
		return this.session
			.createQuery( "from PerfilPermissao p where p.perfil = :perfil" )
			.setParameter( "perfil", perfil )
			.list();
	}

	/**
	 * Preenche dto permissoes.
	 *
	 * @param perfil
	 *            the perfil
	 * @return the list
	 */
	public List<RecursoDTO> preencheDTOPermissoes( final Perfil perfil )
	{

		final List<PerfilPermissao> perfisPermissoes = pesquisar( perfil );

		final List<Permissao> permissoes = permissoes();

		final List<RecursoDTO> recursosDTO = new ArrayList<RecursoDTO>();

		String nomeRecurso = "";
		RecursoDTO dto = new RecursoDTO();
		for ( final Permissao permissao : permissoes )
		{
			if ( !nomeRecurso.equals( permissao.getRecurso() ) )
			{
				if ( !nomeRecurso.equals( "" ) )
				{
					recursosDTO.add( dto );
				}
				dto = new RecursoDTO();
				dto.setNome( permissao.getRecurso() );
				nomeRecurso = permissao.getRecurso();
				dto.setIdPerfil( perfil.getId() );
			}
			final AcaoDTO acaoDTO = new AcaoDTO();
			acaoDTO.setId( permissao.getId() );
			acaoDTO.setNome( permissao.getAcao() );
			if ( perfisPermissoes.contains( new PerfilPermissao( perfil, permissao ) ) )
			{
				acaoDTO.setChecked( Boolean.TRUE );
			}
			dto.getAcoes().add( acaoDTO );
		}

		recursosDTO.add( dto );

		return recursosDTO;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @see br.com.decatron.framework.dao.AbstractDao#search(br.com.decatron.framework.entity.EntityBase)
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<PerfilPermissao> search( final PerfilPermissao entity )
	{
		final Criteria criteria = this.session.createCriteria( PerfilPermissao.class );
		return criteria.list();
	}
}

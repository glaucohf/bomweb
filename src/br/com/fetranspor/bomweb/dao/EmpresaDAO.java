package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Usuario;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

/**
 * The Class EmpresaDAO.
 */
@Component
public class EmpresaDAO
	extends VRaptorHibernateDAO<Empresa>
{

	/**
	 * Instantiates a new empresa dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public EmpresaDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	// ----------------------------------------------------------------------

	/**
	 * Busca empresa by codigo.
	 *
	 * @param codigo
	 *            the codigo
	 * @return the list
	 */
	public List<Empresa> buscaEmpresaByCodigo( final String codigo )
	{
		final Criteria criteria = this.session.createCriteria( Empresa.class );
		criteria.add( Restrictions.eq( "codigo", codigo ) );
		criteria.add( Restrictions.eq( "active", true ) );
		return criteria.list();
	}

	/**
	 * Busca empresa by nome.
	 *
	 * @param nome
	 *            the nome
	 * @return the list
	 */
	public List<Empresa> buscaEmpresaByNome( final String nome )
	{
		final Criteria criteria = this.session.createCriteria( Empresa.class );
		criteria
			.add( Restrictions.ilike( "nome", nome, MatchMode.ANYWHERE ) )
			.add( Restrictions.eq( "active", true ) )
			.addOrder( Order.asc( "nome" ) );
		return criteria.list();
	}

	/**
	 * Busca empresa by nome exato.
	 *
	 * @param nome
	 *            the nome
	 * @return the list
	 */
	public List<Empresa> buscaEmpresaByNomeExato( final String nome )
	{
		final Criteria criteria = this.session.createCriteria( Empresa.class );
		criteria.add( Restrictions.eq( "nome", nome ) );
		criteria.add( Restrictions.eq( "active", true ) );
		return criteria.list();
	}

	/**
	 * Busca empresa sem usuario by nome.
	 *
	 * @param nome
	 *            the nome
	 * @return the object
	 */
	public Object buscaEmpresaSemUsuarioByNome( final String nome )
	{
		final DetachedCriteria dc = DetachedCriteria.forClass( Usuario.class );
		dc.createAlias( "empresa", "emp", Criteria.INNER_JOIN );
		dc.setProjection( Property.forName( "emp.id" ) );

		final Criteria criteria = this.session.createCriteria( Empresa.class );
		criteria
			.add( Restrictions.ilike( "nome", nome, MatchMode.ANYWHERE ) )
			.add( Restrictions.eq( "active", true ) )
			.add( Property.forName( "id" ).notIn( dc ) )
			.addOrder( Order.asc( "nome" ) );
		return criteria.list();
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
	public void delete( Empresa entity )
		throws DaoException
	{
		entity = get( entity.getId() );
		entity.setActive( Boolean.FALSE );
		super.update( entity );
	}

	/**
	 * Empresa ja exite.
	 *
	 * @param codigo
	 *            the codigo
	 * @return true, if successful
	 */
	public boolean empresaJaExite( final String codigo )
	{
		final Criteria criteria = this.session.createCriteria( Empresa.class );
		criteria.add( Restrictions.eq( "codigo", codigo ) );
		criteria.add( Restrictions.eq( "active", true ) );

		return criteria.list().size() > 0 ? true : false;
	}

	/**
	 * Empresa ja exite.
	 *
	 * @param codigo
	 *            the codigo
	 * @param idEmpresa
	 *            the id empresa
	 * @return true, if successful
	 */
	public boolean empresaJaExite( final String codigo, final Long idEmpresa )
	{

		final Criteria criteria = this.session.createCriteria( Empresa.class );
		criteria.add( Restrictions.eq( "codigo", codigo ) );
		criteria.add( Restrictions.eq( "active", true ) );
		criteria.add( Restrictions.ne( "id", idEmpresa ) );

		return criteria.list().size() > 0 ? true : false;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#save(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void save( final Empresa entity )
		throws DaoException
	{
		entity.setActive( true );
		entity.setDataCriacao( new Date() );
		super.save( entity );
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
	public List<Empresa> search( final Empresa entity )
	{

		final Usuario user = ( Usuario ) getUser();

		final Criteria criteria = this.session.createCriteria( Empresa.class );

		if ( Check.isNotNull( user ) && Check.isNotNull( user.getEmpresa() ) )
		{
			criteria.add( Restrictions.eq( "id", user.getEmpresa().getId() ) );
		}

		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotNull( entity ) )
		{

			if ( Check.isNotBlank( entity.getCodigo() ) )
			{
				criteria.add( Restrictions.ilike( "codigo", entity.getCodigo(), MatchMode.ANYWHERE ) );
			}

			if ( Check.isNotBlank( entity.getNome() ) )
			{
				criteria.add( Restrictions.ilike( "nome", entity.getNome(), MatchMode.ANYWHERE ) );
			}
		}

		criteria.addOrder( Order.asc( "nome" ) );

		return criteria.list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#update(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public Empresa update( final Empresa entity )
		throws DaoException
	{
		entity.setActive( true );
		return super.update( entity );
	}
}

package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * The Class TipoDeVeiculoDAO.
 */
@Component
public class TipoDeVeiculoDAO
	extends VRaptorHibernateDAO<TipoDeVeiculo>
{

	/**
	 * Instantiates a new tipo de veiculo dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public TipoDeVeiculoDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	// ----------------------------------------------------------------------

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws DaoException
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#delete(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void delete( TipoDeVeiculo entity )
		throws DaoException
	{
		entity = get( entity.getId() );
		entity.setActive( Boolean.FALSE );
		super.update( entity );
	}

	/**
	 * Findby code.
	 *
	 * @param code
	 *            the code
	 * @return the tipo de veiculo
	 */
	public TipoDeVeiculo findbyCode( final String code )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeVeiculo.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotNull( code ) && Check.isNotBlank( code ) )
		{
			criteria.add( Restrictions.eq( "codigo", code ) );
		}

		return ( TipoDeVeiculo ) criteria.uniqueResult();
	}

	/**
	 * Listar tipos de veiculo permitidos.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	public List<TipoDeVeiculo> listarTiposDeVeiculoPermitidos( final Linha linha )
	{

		final StringBuilder hql = new StringBuilder( "select t from LinhaVigencia lv " );
		hql.append( "inner join lv.tiposDeLinha tl " );
		hql.append( "inner join tl.tiposDeVeiculoPermitidos t " );
		hql.append( "where lv.id = :linhaVigenciaId" );
		final List<TipoDeVeiculo> tiposVeiculo = this.session
			.createQuery( hql.toString() )
			.setParameter( "linhaVigenciaId", linha.getLinhaVigente().getId() )
			.list();

		final List<TipoDeVeiculo> listaTiposDeVeiculoAtivos = new ArrayList<TipoDeVeiculo>();

		for ( final TipoDeVeiculo tipoVeiculo : tiposVeiculo )
		{
			if ( tipoVeiculo.isActive() )
			{
				listaTiposDeVeiculoAtivos.add( tipoVeiculo );
			}
		}

		if ( Check.isNull( linha ) )
		{
			return listaTiposDeVeiculoAtivos;
		}

		for ( final TipoDeVeiculo tipoDeVeiculo : listaTiposDeVeiculoAtivos )
		{
			if ( linha.getLinhaVigente().getTipoDeVeiculosUtilizados().contains( tipoDeVeiculo ) )
			{
				tipoDeVeiculo.setSelecionado( Boolean.TRUE );
			}
		}

		return listaTiposDeVeiculoAtivos;
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
	public void save( final TipoDeVeiculo entity )
		throws DaoException
	{
		entity.setActive( true );
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
	public List<TipoDeVeiculo> search( final TipoDeVeiculo entity )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeVeiculo.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotNull( entity ) )
		{

			if ( Check.isNotBlank( entity.getCodigo() ) )
			{
				criteria.add( Restrictions.ilike( "codigo", entity.getCodigo(), MatchMode.ANYWHERE ) );
			}

			if ( Check.isNotBlank( entity.getDescricao() ) )
			{
				criteria.add( Restrictions.ilike( "descricao", entity.getDescricao(), MatchMode.ANYWHERE ) );
			}

		}

		return criteria.list();
	}

	/**
	 * Tipo de veiculo ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @return true, if successful
	 */
	public boolean tipoDeVeiculoJaExiste( final String codigo )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeVeiculo.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotBlank( codigo ) )
		{
			criteria.add( Restrictions.eq( "codigo", codigo ) );
		}

		return criteria.list().isEmpty() ? false : true;
	}

	/**
	 * Tipo de veiculo ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean tipoDeVeiculoJaExiste( final String codigo, final Long id )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeVeiculo.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotBlank( codigo ) )
		{
			criteria.add( Restrictions.eq( "codigo", codigo ) );
		}
		if ( Check.isNotNull( id ) )
		{
			criteria.add( Restrictions.ne( "id", id ) );

		}

		return criteria.list().isEmpty() ? false : true;
	}
}

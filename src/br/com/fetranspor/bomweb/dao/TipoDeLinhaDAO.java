package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.TipoDeLinha;
import br.com.fetranspor.bomweb.entity.TipoDeLinhaTipoDeVeiculo;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

/**
 * The Class TipoDeLinhaDAO.
 */
@Component
public class TipoDeLinhaDAO
	extends VRaptorHibernateDAO<TipoDeLinha>
{

	/**
	 * Instantiates a new tipo de linha dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public TipoDeLinhaDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	// ----------------------------------------------------------------------

	/**
	 * Ativa tipo linha tipo veiculo.
	 *
	 * @param entity
	 *            the entity
	 * @throws DaoException
	 *             the dao exception
	 */
	public void ativaTipoLinhaTipoVeiculo( TipoDeLinhaTipoDeVeiculo entity )
		throws DaoException
	{
		entity = findTipoLinhaTipoVeiculo(
			entity.getTipoDeLinha().getId(),
			entity.getTipoDeVeiculo().getId(),
			Boolean.FALSE );
		if ( Check.isNotNull( entity ) )
		{
			entity.setActive( Boolean.TRUE );
			this.session.update( entity );
		}
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
	public void delete( TipoDeLinha entity )
		throws DaoException
	{
		entity = get( entity.getId() );
		entity.setActive( Boolean.FALSE );
		if ( Check.isNotNull( entity.getTiposDeLinhaTiposVeiculo() ) )
		{
			for ( final TipoDeLinhaTipoDeVeiculo tltv : entity.getTiposDeLinhaTiposVeiculo() )
			{
				deleteTipoLinhaTipoVeiculo( tltv );
			}
		}
		entity = get( entity.getId() );
		entity.setActive( Boolean.FALSE );
		super.update( entity );
	}

	/**
	 * Delete tipo linha tipo veiculo.
	 *
	 * @param entity
	 *            the entity
	 * @throws DaoException
	 *             the dao exception
	 */
	public void deleteTipoLinhaTipoVeiculo( TipoDeLinhaTipoDeVeiculo entity )
		throws DaoException
	{
		entity = findTipoLinhaTipoVeiculoAtiva( entity.getTipoDeLinha().getId(), entity.getTipoDeVeiculo().getId() );
		if ( Check.isNotNull( entity ) )
		{
			entity.setActive( Boolean.FALSE );
			this.session.update( entity );
		}
	}

	/**
	 * Existe tipo linha ativa usando tipo veiculo.
	 *
	 * @param idTipoVeiculo
	 *            the id tipo veiculo
	 * @return true, if successful
	 */
	public boolean existeTipoLinhaAtivaUsandoTipoVeiculo( final Long idTipoVeiculo )
	{
		final StringBuffer sql = new StringBuffer();

		if ( Check.isNotBlank( idTipoVeiculo.toString() ) )
		{
			sql.append( "select 1 from tipos_linha_tipos_veiculo tl " );
			sql.append( "where tl.active = 1 " );
			sql.append( "and tl.tipoDeVeiculo_id = :idTipoVeiculo " );

			final Query q = this.session.createSQLQuery( sql.toString() );
			q.setParameter( "idTipoVeiculo", idTipoVeiculo );
			return q.list().isEmpty() ? false : true;
		}
		return false;
	}

	/**
	 * Findby sigla.
	 *
	 * @param sigla
	 *            the sigla
	 * @return the tipo de linha
	 */
	public TipoDeLinha findbySigla( final String sigla )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeLinha.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotNull( sigla ) && Check.isNotBlank( sigla ) )
		{
			criteria.add( Restrictions.eq( "sigla", sigla ) );
		}

		return ( TipoDeLinha ) criteria.uniqueResult();
	}

	/**
	 * Find tipo linha tipo veiculo.
	 *
	 * @param idTipoLinha
	 *            the id tipo linha
	 * @param idTipoVeiculo
	 *            the id tipo veiculo
	 * @param somenteAtivos
	 *            the somente ativos
	 * @return the tipo de linha tipo de veiculo
	 */
	public TipoDeLinhaTipoDeVeiculo findTipoLinhaTipoVeiculo(
		final long idTipoLinha,
		final long idTipoVeiculo,
		final Boolean somenteAtivos )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeLinhaTipoDeVeiculo.class );
		criteria.add( Restrictions.eq( "tipoDeVeiculo.id", idTipoVeiculo ) );
		criteria.add( Restrictions.eq( "tipoDeLinha.id", idTipoLinha ) );
		if ( somenteAtivos )
		{
			criteria.add( Restrictions.eq( "active", Boolean.TRUE ) );
		}
		return ( TipoDeLinhaTipoDeVeiculo ) criteria.uniqueResult();
	}

	/**
	 * Find tipo linha tipo veiculo ativa.
	 *
	 * @param idTipoLinha
	 *            the id tipo linha
	 * @param idTipoVeiculo
	 *            the id tipo veiculo
	 * @return the tipo de linha tipo de veiculo
	 */
	public TipoDeLinhaTipoDeVeiculo findTipoLinhaTipoVeiculoAtiva( final long idTipoLinha, final long idTipoVeiculo )
	{
		return findTipoLinhaTipoVeiculo( idTipoLinha, idTipoVeiculo, Boolean.TRUE );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param sigla
	 * @return
	 */
	public TipoDeLinha getTipoDeLinhaBySigla( final String sigla )
	{
		final Criteria criteria = this.session.createCriteria( TipoDeLinha.class );
		criteria.add( Restrictions.eq( "active", true ) );
		criteria.add( Restrictions.eq( "sigla", sigla ) );
		final TipoDeLinha tipoDeLinha = ( TipoDeLinha ) criteria.uniqueResult();
		return tipoDeLinha;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.dao.VRaptorHibernateDAO#list()
	 */
	@SuppressWarnings( "unchecked" )
	@Override
	public List<TipoDeLinha> list()
	{
		final Criteria criteria = this.session.createCriteria( TipoDeLinha.class );
		criteria.add( Restrictions.eq( "active", true ) );
		return criteria.list();
	}

	/**
	 * Listar tipos de linha.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	public List<TipoDeLinha> listarTiposDeLinha( final Linha linha )
	{

		final List<TipoDeLinha> tiposLinha = search( null );

		if ( Check.isNull( linha ) )
		{
			return tiposLinha;
		}

		for ( final TipoDeLinha tipoDeLinha : tiposLinha )
		{
			if ( linha.getLinhaVigente().getTiposDeLinha().contains( tipoDeLinha ) )
			{
				tipoDeLinha.setSelecionado( Boolean.TRUE );
			}
		}

		return tiposLinha;
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
	public void save( final TipoDeLinha entity )
		throws DaoException
	{
		entity.setActive( true );
		if ( Check.isNotNull( entity.getTiposDeLinhaTiposVeiculo() ) )
		{
			for ( final TipoDeLinhaTipoDeVeiculo tltv : entity.getTiposDeLinhaTiposVeiculo() )
			{
				tltv.setActive( Boolean.TRUE );
			}
		}
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
	public List<TipoDeLinha> search( final TipoDeLinha entity )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeLinha.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotNull( entity ) )
		{

			if ( Check.isNotBlank( entity.getSigla() ) )
			{
				criteria.add( Restrictions.ilike( "sigla", entity.getSigla(), MatchMode.ANYWHERE ) );
			}

			if ( Check.isNotBlank( entity.getDescricao() ) )
			{
				criteria.add( Restrictions.ilike( "descricao", entity.getDescricao(), MatchMode.ANYWHERE ) );
			}

		}

		return criteria.list();
	}

	/**
	 * Tipo de linha ja existe.
	 *
	 * @param sigla
	 *            the sigla
	 * @return true, if successful
	 */
	public boolean tipoDeLinhaJaExiste( final String sigla )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeLinha.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotBlank( sigla ) )
		{
			criteria.add( Restrictions.eq( "sigla", sigla ) );
		}

		return criteria.list().isEmpty() ? false : true;
	}

	/**
	 * Tipo de linha ja existe.
	 *
	 * @param sigla
	 *            the sigla
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	public boolean tipoDeLinhaJaExiste( final String sigla, final Long id )
	{

		final Criteria criteria = this.session.createCriteria( TipoDeLinha.class );
		criteria.add( Restrictions.eq( "active", true ) );

		if ( Check.isNotBlank( sigla ) )
		{
			criteria.add( Restrictions.eq( "sigla", sigla ) );
		}
		if ( Check.isNotNull( id ) )
		{
			criteria.add( Restrictions.ne( "id", id ) );

		}

		return criteria.list().isEmpty() ? false : true;
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
	public TipoDeLinha update( final TipoDeLinha entity )
		throws DaoException
	{
		return super.update( entity );
	}
}

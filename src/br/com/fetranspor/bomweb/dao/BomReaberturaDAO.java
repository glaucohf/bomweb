package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.dao.VRaptorHibernateDAO;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomReabertura;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

/**
 * The Class EmpresaDAO.
 */
@Component
public class BomReaberturaDAO
	extends VRaptorHibernateDAO<BomReabertura>
{

	// ----------------------------------------------------------------------

	/**
	 * Instantiates a new empresa dao.
	 *
	 * @param session
	 *            the session
	 * @param provider
	 *            the provider
	 */
	public BomReaberturaDAO( final Session session, final VRaptorProvider provider )
	{
		super( session, provider );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	public List<BomReabertura> buscaBomReaberturaByBom( final Bom bom )
	{
		final Criteria criteria = this.session.createCriteria( BomReabertura.class );
		criteria.add( Restrictions.eq( "bom", bom ) );
		return criteria.list();
	}

	/**
	 * @param listaBomReabertura
	 *            the bom Reabertura
	 */
	public void deleteListaBomReabertura( final List<BomReabertura> listaBomReabertura )
	{
		for ( final BomReabertura bomReabertura : listaBomReabertura )
		{
			this.session.delete( bomReabertura );
		}
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
	public void save( final BomReabertura entity )
		throws DaoException
	{

		entity.setDataCriacao( new Date() );
		super.save( entity );
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
	public BomReabertura update( final BomReabertura entity )
		throws DaoException
	{

		final BomReabertura bomReabertura = super.update( entity );
		return bomReabertura;
	}
}

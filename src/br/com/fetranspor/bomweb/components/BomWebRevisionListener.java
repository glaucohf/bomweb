package br.com.fetranspor.bomweb.components;

import br.com.caelum.vraptor.ioc.spring.VRaptorRequestHolder;
import br.com.fetranspor.bomweb.entity.BomWebRevisionEntity;
import br.com.fetranspor.bomweb.entity.Usuario;

import org.hibernate.envers.RevisionListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * The listener interface for receiving bomWebRevision events. The class that is interested in
 * processing a bomWebRevision event implements this interface, and the object created with that
 * class is registered with a component using the component's
 * <code>addBomWebRevisionListener<code> method. When
 * the bomWebRevision event occurs, that object's appropriate
 * method is invoked.
 *
 * @see BomWebRevisionEvent
 */
public class BomWebRevisionListener
	implements RevisionListener
{

	/**
	 * <p>
	 * </p>
	 *
	 * @param revisionEntity
	 * @see org.hibernate.envers.RevisionListener#newRevision(java.lang.Object)
	 */
	@Override
	public void newRevision( final Object revisionEntity )
	{
		final BomWebRevisionEntity bomWebRevisonEntity = ( BomWebRevisionEntity ) revisionEntity;

		bomWebRevisonEntity.setData( new java.util.Date( bomWebRevisonEntity.getTimestamp() ) );
		final Usuario u = ( Usuario ) TransactionSynchronizationManager.getResource( "user" );
		bomWebRevisonEntity.setUserid( u.getId() );

		bomWebRevisonEntity.setEnderecoIP( VRaptorRequestHolder.currentRequest().getRequest().getRemoteAddr() );
	}
}

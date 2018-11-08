package br.com.fetranspor.bomweb.dao;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.components.Utils;
import br.com.fetranspor.bomweb.dto.FiltroLogDTO;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.InformacoesLog;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;

/**
 * The Class LogDAO.
 */
@Component
public class LogDAO
{

	/**
	 * The Constant PACOTE.
	 */
	public static final String PACOTE = "br.com.fetranspor.bomweb.entity.";

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LogDAO.class );

	/**
	 * Instantiates a new log dao.
	 *
	 * @param session
	 *            the session
	 */
	public LogDAO( final Session session )
	{
		this.session = session;
	}

	/**
	 * Gets the audit reader.
	 *
	 * @return the audit reader
	 */
	protected AuditReader getAuditReader()
	{
		return AuditReaderFactory.get( this.session );
	}

	/**
	 * Gets the revisao anterior.
	 *
	 * @param entidade
	 *            the entidade
	 * @param idObjeto
	 *            the id objeto
	 * @param idRevisao
	 *            the id revisao
	 * @return the revisao anterior
	 */
	public Object getRevisaoAnterior( final InformacoesLog entidade, final Object idObjeto, final Long idRevisao )
	{
		final Number revNumber = ( Number ) getAuditReader()
			.createQuery()
			.forRevisionsOfEntity( entidade.getClass(), false, true )
			.addProjection( AuditEntity.revisionNumber().max() )
			.add( AuditEntity.id().eq( idObjeto ) )
			.add( AuditEntity.revisionNumber().lt( idRevisao ) )
			.getSingleResult();

		if ( Check.isNull( revNumber ) )
		{
			return null;
		}

		return getAuditReader().find( entidade.getClass(), entidade.getId(), revNumber );
	}

	/**
	 * List.
	 *
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Object[]> list()
	{
		final List<Object[]> result = getAuditReader()
			.createQuery()
			.forRevisionsOfEntity( Empresa.class, false, true )
			.getResultList();
		return result;
	}

	/**
	 * List.
	 *
	 * @param filtroLogDTO
	 *            the filtro log dto
	 * @return the list
	 */
	@SuppressWarnings( "unchecked" )
	public List<Object[]> list( final FiltroLogDTO filtroLogDTO )
	{
		List<Object[]> result = null;
		final List<Object[]> resultTratado = new LinkedList<Object[]>();

		if ( Check.isNotNull( filtroLogDTO.getEntidade() ) )
		{
			AuditQuery query;
			try
			{
				query = getAuditReader().createQuery().forRevisionsOfEntity(
					Class.forName( PACOTE.concat( filtroLogDTO.getEntidade() ) ),
					false,
					true );

				if ( Check.isNotNull( filtroLogDTO.getIdUsuario() ) )
				{
					query.add( AuditEntity.revisionProperty( "userid" ).eq( filtroLogDTO.getIdUsuario() ) );
				}

				if ( Check.isNotNull( filtroLogDTO.getDataFinal() ) && Check.isNotNull( filtroLogDTO.getDataFinal() ) )
				{
					final Date dataInicial = Utils.setHoraParaMeiaNoite( filtroLogDTO.getDataInicial() );
					Date dataFinal = Utils.setHoraParaMeiaNoite( filtroLogDTO.getDataFinal() );
					final Calendar c = Calendar.getInstance();
					c.setTime( dataFinal );
					c.add( Calendar.DAY_OF_MONTH, 1 );
					dataFinal = c.getTime();

					query.add( AuditEntity.revisionProperty( "timestamp" ).between(
						dataInicial.getTime(),
						dataFinal.getTime() ) );
				}

				result = query.getResultList();
			}
			catch ( final ClassNotFoundException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
			}
		}

		if ( Check.isNotEmpty( result ) )
		{
			for ( final Object[] object : result )
			{
				InformacoesLog entidade = null;

				if ( Check.isNotNull( object[0] ) )
				{
					entidade = ( InformacoesLog ) object[0];
					if ( Check.isNotNull( object[2] ) && ( object[2] instanceof RevisionType ) )
					{
						RevisionType rt = ( RevisionType ) object[2];

						if ( !entidade.isActive() )
						{
							if ( rt.equals( RevisionType.MOD ) )
							{
								rt = RevisionType.DEL;
								object[2] = rt;
							}
						}

						if ( Check.isNull( filtroLogDTO.getRevisionType() )
							|| rt.equals( RevisionType.fromRepresentation( filtroLogDTO.getRevisionType() ) ) )
						{
							resultTratado.add( object );
						}
					}
				}
			}
		}
		return resultTratado;
	}

	/**
	 * The session.
	 */
	protected Session session;
}

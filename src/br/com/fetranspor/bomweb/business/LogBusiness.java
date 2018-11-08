package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.dao.LogDAO;
import br.com.fetranspor.bomweb.dto.FiltroLogDTO;
import br.com.fetranspor.bomweb.dto.LogDTO;
import br.com.fetranspor.bomweb.entity.BomWebRevisionEntity;
import br.com.fetranspor.bomweb.entity.InformacoesLog;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.entity.UsuarioPerfil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.envers.RevisionType;

/**
 * The Class LogBusiness.
 */
@Component
public class LogBusiness
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LogBusiness.class );

	/**
	 * Instantiates a new log business.
	 *
	 * @param dao
	 *            the dao
	 * @param usuarioPerfilBusiness
	 *            the usuario perfil business
	 */
	public LogBusiness( final LogDAO dao, final UsuarioPerfilBusiness usuarioPerfilBusiness )
	{
		this.dao = dao;
		this.usuarioPerfilBusiness = usuarioPerfilBusiness;
	}

	/**
	 * Adds the perfil.
	 *
	 * @param entidade
	 *            the entidade
	 * @throws SecurityException
	 *             the security exception
	 * @throws NoSuchMethodException
	 *             the no such method exception
	 * @throws IllegalArgumentException
	 *             the illegal argument exception
	 * @throws IllegalAccessException
	 *             the illegal access exception
	 * @throws InvocationTargetException
	 *             the invocation target exception
	 */
	private void addPerfil( final InformacoesLog entidade )
		throws SecurityException,
			NoSuchMethodException,
			IllegalArgumentException,
			IllegalAccessException,
			InvocationTargetException
	{
		Method m = entidade.getClass().getMethod( "getId" );
		final Integer idUsuario = ( Integer ) m.invoke( entidade );

		final Usuario usuarioLog = new Usuario();
		usuarioLog.setId( idUsuario );
		final Perfil perfil = this.usuarioPerfilBusiness.perfil( usuarioLog );

		m = entidade.getClass().getMethod( "setPerfil", String.class );
		m.invoke( entidade, perfil == null ? "" : perfil.getNome() );
	}

	/**
	 * Busca logs.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 * @throws BusinessException
	 *             the business exception
	 */
	public List<LogDTO> buscaLogs( final FiltroLogDTO filtro )
		throws BusinessException
	{

		if ( Check.isNotNull( filtro.getDataInicial() ) && Check.isNotNull( filtro.getDataFinal() ) )
		{
			if ( filtro.getDataFinal().before( filtro.getDataInicial() ) )
			{
				throw new BusinessException( "A data inicial deve ser anterior à data final." );
			}
		}

		List<Object[]> result = null;

		result = this.dao.list( filtro );

		return criaDTOS( result );
	}

	/**
	 * Cria dtos.
	 *
	 * @param objs
	 *            the objs
	 * @return the list
	 */
	private List<LogDTO> criaDTOS( final List<Object[]> objs )
	{
		final List<LogDTO> logDTOs = new ArrayList<LogDTO>();

		for ( final Object[] object : objs )
		{
			final LogDTO logDTO = new LogDTO();
			InformacoesLog entidade = null;
			RevisionType rt = null;

			if ( Check.isNotNull( object[0] ) )
			{
				entidade = ( InformacoesLog ) object[0];
				logDTO.setEntidade( entidade.getClass().getSimpleName() );
			}

			if ( Check.isNotNull( object[2] ) )
			{
				rt = ( RevisionType ) object[2];
			}

			if ( Check.isNotNull( object[1] ) && ( object[1] instanceof BomWebRevisionEntity ) )
			{
				final BomWebRevisionEntity bomWebRevisionEntity = ( BomWebRevisionEntity ) object[1];
				final Usuario usuario = this.usuarioPerfilBusiness.buscarUsuarioParaLog( bomWebRevisionEntity
					.getUserid() );

				Object idObjeto = null;

				try
				{
					if ( entidade.getClass().equals( LinhaVigencia.class ) )
					{
						// Verifica se a LinhaVigencia foi criada a partir de uma LinhaVigencia
						// antiga
						final Method m = object[0].getClass().getMethod( "getIdLinhaVigenciaAntiga" );
						idObjeto = m.invoke( object[0] );

					}
					else if ( entidade.getClass().equals( Tarifa.class ) )
					{
						final Method m = object[0].getClass().getMethod( "getIdTarifaAntiga" );
						idObjeto = m.invoke( object[0] );
					}
					else if ( entidade.getClass().equals( Usuario.class ) )
					{
						addPerfil( entidade );
					}
					if ( Check.isNull( idObjeto ) )
					{
						final Method m = object[0].getClass().getMethod( "getId" );
						idObjeto = m.invoke( object[0] );
					}

					if ( rt.equals( RevisionType.ADD ) || rt.equals( RevisionType.MOD ) )
					{

						logDTO.setDadosNovos( entidade.tripao() );

						if ( rt.equals( RevisionType.MOD ) && Check.isNotNull( idObjeto ) )
						{
							final Object revisaoAnterior = getRevisaoAnterior(
								entidade,
								idObjeto,
								bomWebRevisionEntity.getId() );
							if ( Check.isNotNull( revisaoAnterior ) )
							{
								final InformacoesLog informacoesAnterior = ( InformacoesLog ) revisaoAnterior;
								if ( informacoesAnterior.getClass().equals( Usuario.class ) )
								{
									addPerfil( informacoesAnterior );
								}
								logDTO.setDadosAntigos( informacoesAnterior.tripao() );
							}
						}
					}
					else
					{ // revision é do tipo DEL.
						logDTO.setDadosAntigos( entidade.tripao() );
					}

				}
				catch ( final IllegalAccessException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
				catch ( final IllegalArgumentException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
				catch ( final InvocationTargetException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
				catch ( final NoSuchMethodException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
				catch ( final SecurityException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}

				if ( Check.isNotNull( usuario ) )
				{
					logDTO.setUsuario( usuario.getNome() );
					final UsuarioPerfil usuarioPerfil = this.usuarioPerfilBusiness.buscarUsuarioPerfilParaLog( usuario );
					if ( Check.isNotNull( usuarioPerfil ) )
					{
						logDTO.setPerfil( usuarioPerfil.getPerfil().getNome() );
					}
				}

				if ( Check.isNotNull( bomWebRevisionEntity.getEnderecoIP() ) )
				{
					logDTO.setEnderecoIP( bomWebRevisionEntity.getEnderecoIP() );
				}

				logDTO.setData( bomWebRevisionEntity.getData() );
			}

			String operacao;
			if ( rt.equals( RevisionType.ADD ) )
			{
				operacao = "Inclusão";
			}
			else if ( rt.equals( RevisionType.MOD ) )
			{
				operacao = "Alteração";
			}
			else
			{
				operacao = "Deleção";
			}
			logDTO.setOperacao( operacao );
			logDTOs.add( logDTO );
		}

		return logDTOs;
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
		return this.dao.getRevisaoAnterior( entidade, idObjeto, idRevisao );
	}

	/**
	 * The dao.
	 */
	private final LogDAO dao;

	/**
	 * The usuario perfil business.
	 */
	private final UsuarioPerfilBusiness usuarioPerfilBusiness;
}

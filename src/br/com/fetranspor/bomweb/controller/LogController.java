package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.LogBusiness;
import br.com.fetranspor.bomweb.business.UsuarioPerfilBusiness;
import br.com.fetranspor.bomweb.dto.FiltroLogDTO;
import br.com.fetranspor.bomweb.dto.LogDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.EntidadesLog;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.PerfilPermissao;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.TipoDeLinha;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class LogController.
 */
@Resource
public class LogController
	extends VRaptorController
{

	/**
	 * The Constant ENTIDADES_LOG.
	 */
	public static final EntidadesLog[] ENTIDADES_LOG = {new EntidadesLog( "Empresa", Empresa.class.getSimpleName() ),
														new EntidadesLog( "Linha", Linha.class.getSimpleName() ),
														new EntidadesLog(
															"Tipo de Linha",
															TipoDeLinha.class.getSimpleName() ),
														new EntidadesLog(
															"Linha Vigência",
															LinhaVigencia.class.getSimpleName() ),
														new EntidadesLog( "Seção", Secao.class.getSimpleName() ),
														new EntidadesLog(
															"Tipo de Veículo",
															TipoDeVeiculo.class.getSimpleName() ),
														new EntidadesLog( "Tarifa", Tarifa.class.getSimpleName() ),
														new EntidadesLog( "BOM", Bom.class.getSimpleName() ),
														new EntidadesLog( "Usuário", Usuario.class.getSimpleName() ),
														new EntidadesLog(
															"Perfil/Permissão",
															PerfilPermissao.class.getSimpleName() )

	};

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LogController.class );

	/**
	 * Instantiates a new log controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param usuarioPerfilBusiness
	 *            the usuario perfil business
	 */
	public LogController(
		final VRaptorProvider provider,
		final LogBusiness business,
		final Result result,
		final Validator validator,
		final UsuarioPerfilBusiness usuarioPerfilBusiness )
	{
		super( provider, result, validator );
		this.business = business;
		this.usuarioPerfilBusiness = usuarioPerfilBusiness;
	}

	/**
	 * List.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	public List<LogDTO> list( final FiltroLogDTO filtro )
	{
		this.result.include( "entidades", ENTIDADES_LOG );
		this.result.include( "usuarios", this.usuarioPerfilBusiness.search( null ) );

		List<LogDTO> logDTOs = new ArrayList<LogDTO>();

		if ( Check.isNotNull( filtro ) )
		{
			try
			{
				logDTOs = this.business.buscaLogs( filtro );
			}
			catch ( final BusinessException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
				addErrorMessage( e );
			}
		}
		return logDTOs;
	}

	/**
	 * The business.
	 */
	private final LogBusiness business;

	/**
	 * The usuario perfil business.
	 */
	private final UsuarioPerfilBusiness usuarioPerfilBusiness;
}

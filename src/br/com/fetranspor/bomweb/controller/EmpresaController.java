package br.com.fetranspor.bomweb.controller;

import static br.com.caelum.vraptor.view.Results.json;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.EmpresaBusiness;
import br.com.fetranspor.bomweb.components.Utils;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Perfil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class EmpresaController.
 */
@Resource
public class EmpresaController
	extends BomWebController<Empresa>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( EmpresaController.class );

	/**
	 * Instantiates a new empresa controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 */
	public EmpresaController(
		final VRaptorProvider provider,
		final EmpresaBusiness business,
		final Result result,
		final Validator validator )
	{
		super( provider, business, result, validator );
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( !perfil.isEmpresa() )
		{
			this.autoList = Boolean.FALSE;
		}
		else
		{
			this.autoList = Boolean.TRUE;
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeEdit()
	 */
	@Override
	protected void beforeEdit()
	{
		this.result.include( "perfilEmpresa", hasRole( "Empresa" ) );
		super.beforeEdit();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.ListController#beforeList()
	 */
	@Override
	protected void beforeList()
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( perfil.isEmpresa() )
		{
			this.result.include( "disableFilter", true );
		}
		super.beforeList();
	}

	/**
	 * Before save.
	 *
	 * @param entity
	 *            the entity
	 */
	public void beforeSave( final Empresa entity )
	{
		this.result.include( "t", entity );
		final Date dataAtual = new Date();
		if ( Utils.compareToSemHora( dataAtual, entity.getInicioVigenciaBom() ) > 0 )
		{
			this.validator.add( new ValidationMessage(
				"A Data de Início da Vigência do BOM deve ser igual ou posterior à data atual.",
				"Erro" ) );
			this.validator.onErrorRedirectTo( EmpresaController.class ).insert();
		}
		super.beforeSave();
	}

	/**
	 * Before update.
	 *
	 * @param entity
	 *            the entity
	 */
	public void beforeUpdate( final Empresa entity )
	{
		this.result.include( "t", entity );
		if ( Check.isNotNull( entity.getDataCriacaoDate() )
			&& entity.getDataCriacaoDate().after( entity.getInicioVigenciaBom() ) )
		{
			try
			{
				this.validator.add( new ValidationMessage(
					"A Data de Início da Vigência do BOM deve ser posterior à data de criação da Empresa("
						+ entity.getDataCriacao()
						+ ").",
					"Erro" ) );
			}
			catch ( final ParseException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
				addErrorMessage( e );
			}
			this.validator.onErrorRedirectTo( EmpresaController.class ).edit( entity );
		}
		super.beforeUpdate();
	}

	/**
	 * Busca json.
	 *
	 * @param term
	 *            the term
	 */
	@Get
	@Path( "/empresa/busca.json" )
	public void buscaJson( final String term )
	{
		this.result
			.use( json() )
			.from( ( ( EmpresaBusiness ) this.business ).buscaEmpresasByNome( term == null ? "" : term ), "empresa" )
			.exclude( "uuid", "active", "excludeProperties", "codigo", "emailContato", "responsavel", "dataCriacao" )
			.serialize();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelAttributesName()
	 */
	@Override
	public String[] excelAttributesName()
	{
		return new String[]{"codigo",
							"nome",
							"responsavel",
							"emailContato",
							"inicioVigenciaBom"};
	}

	// Excel

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelFileName()
	 */
	@Override
	public String excelFileName()
	{
		final SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy" );
		return "Empresas_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelHeader()
	 */
	@Override
	public String[] excelHeader()
	{
		return new String[]{"Código da Empresa",
							"Nome da Empresa",
							"Operador",
							"Email",
							"Início da Vigência do BOM"};
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @see br.com.decatron.framework.controller.CrudController#save(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void save( final Empresa entity )
	{
		try
		{
			this.beforeSave( entity );
			( ( EmpresaBusiness ) this.business ).save( entity );

			addInfoMessage( saveMessage() );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
			this.result.redirectTo( EmpresaController.class ).insert();
			return;
		}
		this.result.redirectTo( EmpresaController.class ).list();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @see br.com.decatron.framework.controller.CrudController#update(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void update( final Empresa entity )
	{
		try
		{
			this.beforeUpdate( entity );
			( ( EmpresaBusiness ) this.business ).update( entity );
			addInfoMessage( saveMessage() );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
			this.result.redirectTo( EmpresaController.class ).insert();
			return;
		}
		this.result.redirectTo( EmpresaController.class ).list();
	}

}

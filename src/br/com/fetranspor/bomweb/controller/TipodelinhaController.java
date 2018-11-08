package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.business.LinhaBusiness;
import br.com.fetranspor.bomweb.business.TipoDeLinhaBusiness;
import br.com.fetranspor.bomweb.business.TipoDeVeiculoBusiness;
import br.com.fetranspor.bomweb.entity.TipoDeLinha;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class TipodelinhaController.
 */
@Resource
public class TipodelinhaController
	extends BomWebController<TipoDeLinha>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( TipodelinhaController.class );

	/**
	 * Instantiates a new tipodelinha controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param veiculoBusiness
	 *            the veiculo business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param linhaBusiness
	 *            the linha business
	 */
	public TipodelinhaController(
		final VRaptorProvider provider,
		final TipoDeLinhaBusiness business,
		final TipoDeVeiculoBusiness veiculoBusiness,
		final Result result,
		final Validator validator,
		final LinhaBusiness linhaBusiness )
	{
		super( provider, business, result, validator );
		this.autoList = Boolean.FALSE;
		this.linhaBusiness = linhaBusiness;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeDelete()
	 */
	@Override
	protected void beforeDelete()
	{
		if ( this.linhaBusiness.existeLinhaAtivaUsandoTipoLinha( this.entity.getId() ) )
		{
			this.validator.add( new ValidationMessage(
				"O Tipo de Linha está sendo usado e não pode ser excluído.",
				"Impossível Excluir" ) );
			this.validator.onErrorRedirectTo( TipodelinhaController.class ).list();
		}
		super.beforeDelete();
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
		super.beforeEdit();
		this.result.include(
			"tiposVeiculo",
			( ( TipoDeLinhaBusiness ) this.business ).listaTipoVeiculo( this.entity.getId() ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeInsert()
	 */
	@Override
	protected void beforeInsert()
	{
		super.beforeInsert();
		final List<TipoDeVeiculo> tiposDeVeiculo = ( ( TipoDeLinhaBusiness ) this.business ).listaTipoVeiculo( null );
		if ( ( tiposDeVeiculo == null ) || tiposDeVeiculo.isEmpty() )
		{
			this.validator.add( new ValidationMessage(
				"Antes de criar um Tipo de Linha é necessário criar um Tipo de Veículo.",
				"Impossível Cadastrar" ) );
			this.validator.onErrorRedirectTo( TipodelinhaController.class ).list();
		}
		else
		{
			this.result.include( "tiposVeiculo", tiposDeVeiculo );
		}
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
		return new String[]{"sigla",
							"descricao",
							"listagemTiposDeVeiculoPermitidos"};
	}

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
		return "Tipos_de_Linhas_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
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
		return new String[]{"Sigla do Tipo de Linha",
							"Descrição do Tipo de Linha",
							"Tipos de Veículos Previstos"};
	}

	// TODO TRATAR EXCEÇÃO
	/**
	 * Save.
	 *
	 * @param entity
	 *            the entity
	 * @param tiposVeiculo
	 *            the tipos veiculo
	 */
	public void save( final TipoDeLinha entity, final List<TipoDeVeiculo> tiposVeiculo )
	{
		try
		{
			if ( ( ( TipoDeLinhaBusiness ) this.business ).tipoDeLinhaJaExiste( entity.getSigla() ) )
			{
				this.validator.add( new ValidationMessage(
					"Tipo de Linha já existente.",
					"Favor verificar a sigla inserida." ) );
				this.validator.onErrorRedirectTo( TipodelinhaController.class ).insert();
			}
			( ( TipoDeLinhaBusiness ) this.business ).save( entity, tiposVeiculo );
			addInfoMessage( saveMessage() );
		}
		catch ( final DaoException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
		}
		this.result.redirectTo( TipodelinhaController.class ).list();
	}

	// TODO TRATAR EXCEÇÃO
	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @param tiposVeiculo
	 *            the tipos veiculo
	 */
	public void update( final TipoDeLinha entity, final List<TipoDeVeiculo> tiposVeiculo )
	{
		try
		{
			if ( ( ( TipoDeLinhaBusiness ) this.business ).tipoDeLinhaJaExiste( entity.getSigla(), entity.getId() ) )
			{
				this.validator.add( new ValidationMessage(
					"Tipo de Linha já existente.",
					"Favor verificar a sigla inserida." ) );
				this.validator.onErrorRedirectTo( TipodelinhaController.class ).edit( entity );
			}
			( ( TipoDeLinhaBusiness ) this.business ).update( entity, tiposVeiculo );
			addInfoMessage( updateMessage() );
		}
		catch ( final DaoException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
		}
		this.result.redirectTo( TipodelinhaController.class ).list();
	}

	/**
	 * The linha business.
	 */
	private final LinhaBusiness linhaBusiness;

}

package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.business.LinhaBusiness;
import br.com.fetranspor.bomweb.business.TipoDeLinhaBusiness;
import br.com.fetranspor.bomweb.business.TipoDeVeiculoBusiness;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class TipodeveiculoController.
 */
@Resource
public class TipodeveiculoController
	extends BomWebController<TipoDeVeiculo>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( TipodeveiculoController.class );

	/**
	 * Instantiates a new tipodeveiculo controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param linhaBusiness
	 *            the linha business
	 * @param tipoLinhaBusiness
	 *            the tipo linha business
	 */
	public TipodeveiculoController(
		final VRaptorProvider provider,
		final TipoDeVeiculoBusiness business,
		final Result result,
		final Validator validator,
		final LinhaBusiness linhaBusiness,
		final TipoDeLinhaBusiness tipoLinhaBusiness )
	{
		super( provider, business, result, validator );
		this.autoList = Boolean.FALSE;
		this.linhaBusiness = linhaBusiness;
		this.tipoLinhaBusiness = tipoLinhaBusiness;
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
		if ( this.linhaBusiness.existeLinhaAtivaUsandoTipoVeiculo( this.entity.getId() ) )
		{
			this.validator.add( new ValidationMessage(
				"O Tipo de Veículo está sendo usado em uma Linha e não pode ser excluído.",
				"Impossível Excluir" ) );
			this.validator.onErrorRedirectTo( TipodeveiculoController.class ).list();
		}
		if ( this.tipoLinhaBusiness.existeTipoLinhaAtivaUsandoTipoVeiculo( this.entity.getId() ) )
		{
			this.validator.add( new ValidationMessage(
				"O Tipo de Veículo está associado a um Tipo de Linha e não pode ser excluído.",
				"Impossível Excluir" ) );
			this.validator.onErrorRedirectTo( TipodeveiculoController.class ).list();
		}
		super.beforeDelete();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeSave()
	 */
	@Override
	protected void beforeSave()
	{
		try
		{
			validacoesComunsBeforeSaveEUpdate( this.entity );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			this.validator.add( new ValidationMessage( e.getMessage(), "" ) );
			this.result.include( "t", this.entity );
			this.validator.onErrorRedirectTo( TipodeveiculoController.class ).insert();
		}

		if ( ( ( TipoDeVeiculoBusiness ) this.business ).tipoDeVeiculoJaExiste( this.entity.getCodigo() ) )
		{
			this.validator.add( new ValidationMessage(
				"Tipo de Veículo já existente.",
				"Favor verificar o código inserido" ) );
			this.result.include( "t", this.entity );
			this.validator.onErrorRedirectTo( TipodeveiculoController.class ).insert();
		}
		super.beforeSave();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeUpdate()
	 */
	@Override
	protected void beforeUpdate()
	{
		try
		{
			validacoesComunsBeforeSaveEUpdate( this.entity );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			this.validator.add( new ValidationMessage( e.getMessage(), "" ) );
			this.result.include( "t", this.entity );
			this.validator.onErrorRedirectTo( TipodeveiculoController.class ).edit( this.entity );
		}

		if ( ( ( TipoDeVeiculoBusiness ) this.business ).tipoDeVeiculoJaExiste(
			this.entity.getCodigo(),
			this.entity.getId() ) )
		{
			this.validator.add( new ValidationMessage(
				"Tipo de Veículo já existente.",
				"Favor verificar o código inserido." ) );
			this.result.include( "t", this.entity );
			this.validator.onErrorRedirectTo( TipodeveiculoController.class ).edit( this.entity );
		}
		super.beforeUpdate();
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
							"descricao"};
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
		return "Tipos_de_Veículos_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
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
		return new String[]{"Código do Tipo de Veículo",
							"Nome do Tipo de Veículo"};
	}

	/**
	 * Validacoes comuns before save e update.
	 *
	 * @param entity
	 *            the entity
	 * @throws BusinessException
	 *             the business exception
	 */
	private void validacoesComunsBeforeSaveEUpdate( final TipoDeVeiculo entity )
		throws BusinessException
	{
		String codigoTipoVeiculo = null;
		codigoTipoVeiculo = ( ( TipoDeVeiculoBusiness ) this.business ).removeZerosAEsquerda( entity.getCodigo() );
		entity.setCodigo( codigoTipoVeiculo );
	}

	/**
	 * The linha business.
	 */
	private final LinhaBusiness linhaBusiness;

	/**
	 * The tipo linha business.
	 */
	private final TipoDeLinhaBusiness tipoLinhaBusiness;

}

package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.decatron.framework.business.AbstractBusiness;
import br.com.decatron.framework.controller.vraptor.VRaptorCrudController;
import br.com.decatron.framework.entity.EntityBase;
import br.com.decatron.framework.excel.ExcelConfiguration;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.entity.BomWebEntityBase;

/**
 * The Class BomWebController.
 *
 * @param <T>
 *            the generic type
 */
public abstract class BomWebController<T extends EntityBase>
	extends VRaptorCrudController<T>
{

	/**
	 * Instantiates a new bom web controller.
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
	public BomWebController(
		final VRaptorProvider provider,
		final AbstractBusiness<T> business,
		final Result result,
		final Validator validator )
	{
		super( provider, business, result, validator );
	}

	/**
	 * Confirm delete.
	 *
	 * @param entity
	 *            the entity
	 * @return the t
	 */
	@SuppressWarnings( "unchecked" )
	public T confirmDelete( final T entity )
	{
		final BomWebEntityBase t = ( BomWebEntityBase ) this.business.get( entity.getId() );
		if ( !t.isActive() )
		{
			// TODO Error ou Info?
			addInfoMessage( "Registro inativado ou inexistente." );
			this.result.redirectTo( this ).list();
		}
		return ( T ) t;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelConfiguration()
	 */
	@Override
	public ExcelConfiguration excelConfiguration()
	{
		return new ExcelConfiguration()
			.setHeightAttribute( 10 )
			.setHeightHeader( 10 )
			.useBoldHeader( true )
			.useCenterHeader( true )
			.useNumericCellType( true );
	}
}

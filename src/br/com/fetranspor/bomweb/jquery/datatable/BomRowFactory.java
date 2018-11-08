package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory;
import br.com.decatron.framework.security.AbstractAuthorization;
import br.com.fetranspor.bomweb.components.Utils;
import br.com.fetranspor.bomweb.controller.BomController;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Status;

/**
 * A factory for creating BomRow objects.
 */
public class BomRowFactory
	extends JQueryDataTableRowFactory<BomRow, Bom>
{

	/**
	 * Instantiates a new bom row factory.
	 *
	 * @param abstractAuthorization
	 *            the abstract authorization
	 */
	public BomRowFactory( final AbstractAuthorization abstractAuthorization )
	{
		super( abstractAuthorization );
	}

	/**
	 * Instantiates a new bom row factory.
	 *
	 * @param abstractAuthorization
	 *            the abstract authorization
	 * @param controller
	 */
	public BomRowFactory( final AbstractAuthorization abstractAuthorization, final BomController controller )
	{
		this( abstractAuthorization );
		this.controller = controller;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#cria(java.lang.Object)
	 */
	@Override
	public BomRow cria( final Bom bom )
	{
		final BomRow bomRow = new BomRow();

		bomRow.id = bom.getId();

		final Empresa empresa = bom.getEmpresa();
		final Status status = bom.getStatus();
		final String mesReferencia = bom.getMesReferencia();

		bomRow.empresa = empresa.getNome();
		bomRow.mesAnoReferencia = RowValueDate.valueOf( mesReferencia, "MM/yyyy" );
		bomRow.status = status != null ? status.name() : null;
		bomRow.statusPendencia = bom.getStatusPendencia();
		bomRow.dataLimiteFechamento = new RowValueDate( bom.getDataLimiteFechamento(), Utils.DATE_DMY_FORMAT );
		if ( this.controller != null )
		{
			bomRow.reopenStatus = this.controller.getBomReopenLength( bom );
			bomRow.canReopen = this.controller.podeReabrirBom( bom );
		}
		return bomRow;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#getRowClass()
	 */
	@Override
	public Class<BomRow> getRowClass()
	{
		return BomRow.class;
	}

	/**
	 * <p>
	 * FBW-676 Field <code>controller</code>
	 * </p>
	 */
	private BomController controller;
}

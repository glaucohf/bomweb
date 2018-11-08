package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRow;

/**
 * The Class BomRow.
 */
public class BomRow
	extends JQueryDataTableRow
{

	/**
	 * Instantiates a new bom row.
	 */
	protected BomRow()
	{
		super( "Bom" );
	}

	/**
	 * <p>
	 * FBW-676
	 * </p>
	 *
	 * @return
	 */
	public boolean getPodeReabrir()
	{
		return this.podeReabrir;
	}

	/**
	 * <p>
	 * FBW-676 - Field <code>canReopen</code>
	 * </p>
	 */
	boolean canReopen;

	/**
	 * The id.
	 */
	Long id;

	/**
	 * The empresa.
	 */
	String empresa;

	/**
	 * The mes ano referencia.
	 */
	RowValueDate mesAnoReferencia;

	/**
	 * The status.
	 */
	String status;

	/**
	 * The status pendencia.
	 */
	String statusPendencia;

	/**
	 * The data limite fechamento.
	 */
	RowValueDate dataLimiteFechamento;

	/**
	 * <p>
	 * Field <code>msgReopenBom</code>
	 * </p>
	 */
	int reopenStatus;
}

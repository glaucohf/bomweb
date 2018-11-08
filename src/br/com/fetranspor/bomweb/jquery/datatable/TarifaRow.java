package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRow;

/**
 * The Class TarifaRow.
 */
public class TarifaRow
	extends JQueryDataTableRow
{

	/**
	 * Instantiates a new tarifa row.
	 */
	protected TarifaRow()
	{
		super( "Tarifa" );
	}

	/**
	 * The id.
	 */
	Long id;

	/**
	 * The empresa.
	 */
	String empresa;

	/**
	 * The linha.
	 */
	String linha;

	/**
	 * The codigo linha.
	 */
	String codigoLinha;

	/**
	 * The codigo secao.
	 */
	String codigoSecao;

	/**
	 * The secao.
	 */
	String secao;

	/**
	 * The valor.
	 */
	String valor;

	/**
	 * The inicio vigencia.
	 */
	String inicioVigencia;

	/**
	 * The fim vigencia.
	 */
	String fimVigencia;

	/**
	 * The is futura.
	 */
	boolean isFutura;

	/**
	 * The tem tarifa futura.
	 */
	boolean temTarifaFutura;
}

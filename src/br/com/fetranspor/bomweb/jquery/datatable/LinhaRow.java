package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRow;

/**
 * The Class LinhaRow.
 */
public class LinhaRow
	extends JQueryDataTableRow
{

	/**
	 * Instantiates a new linha row.
	 */
	protected LinhaRow()
	{
		super( "Linha" );
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
	 * The secoes.
	 */
	String secoes;

	/**
	 * The nome da linha.
	 */
	String nomeDaLinha;

	/**
	 * The status.
	 */
	String status;

	/**
	 * The tipos de veiculos utilizados.
	 */
	String tiposDeVeiculosUtilizados;

	/**
	 * The extensao ab.
	 */
	String extensaoAB;

	/**
	 * The extensao ba.
	 */
	String extensaoBA;

	/**
	 * The data de inicio.
	 */
	String dataDeInicio;

	/**
	 * The data de termino.
	 */
	String dataDeTermino;

	/**
	 * The tem linha futura.
	 */
	boolean temLinhaFutura;
}

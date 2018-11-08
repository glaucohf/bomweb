package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory;
import br.com.decatron.framework.security.AbstractAuthorization;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Tarifa;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A factory for creating TarifaRow objects.
 */
public class TarifaRowFactory
	extends JQueryDataTableRowFactory<TarifaRow, Tarifa>
{

	/**
	 * Instantiates a new tarifa row factory.
	 *
	 * @param abstractAuthorization
	 *            the abstract authorization
	 */
	public TarifaRowFactory( final AbstractAuthorization abstractAuthorization )
	{
		super( abstractAuthorization );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param tarifa
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#cria(java.lang.Object)
	 */
	@Override
	public TarifaRow cria( final Tarifa tarifa )
	{
		final TarifaRow tarifaRow = new TarifaRow();

		tarifaRow.id = tarifa.getId();

		final Secao secao = tarifa.getSecao();
		tarifaRow.empresa = secao.getLinhaVigencia().getEmpresa().getNome();
		tarifaRow.linha = secao.getLinhaVigencia().getItinerarioIda();
		tarifaRow.codigoLinha = secao.getLinhaVigencia().getCodigo();
		tarifaRow.codigoSecao = secao.getCodigo();
		tarifaRow.secao = secao.getJuncao();

		Locale.setDefault( new Locale( "pt", "BR" ) );

		final DecimalFormat decimalFormat = new DecimalFormat( "#,##0.00" );
		tarifaRow.valor = decimalFormat.format( tarifa.getValor() );

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
		tarifaRow.inicioVigencia = simpleDateFormat.format( tarifa.getInicioVigencia() );
		tarifaRow.fimVigencia = tarifa.getFimVigencia() == null ? "" : simpleDateFormat
			.format( tarifa.getFimVigencia() );

		tarifaRow.isFutura = tarifa.isFutura();
		tarifaRow.temTarifaFutura = tarifa.isTemTarifaFutura();

		return tarifaRow;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#getRowClass()
	 */
	@Override
	public Class<TarifaRow> getRowClass()
	{
		return TarifaRow.class;
	}

}

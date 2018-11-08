package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory;
import br.com.decatron.framework.security.AbstractAuthorization;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Secao;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * A factory for creating LinhaRow objects.
 */
public class LinhaRowFactory
	extends JQueryDataTableRowFactory<LinhaRow, Linha>
{

	/**
	 * Instantiates a new linha row factory.
	 *
	 * @param abstractAuthorization
	 *            the abstract authorization
	 */
	public LinhaRowFactory( final AbstractAuthorization abstractAuthorization )
	{
		super( abstractAuthorization );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param linha
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#cria(java.lang.Object)
	 */
	@Override
	public LinhaRow cria( final Linha linha )
	{
		final LinhaRow linhaRow = new LinhaRow();

		linhaRow.id = linha.getId();

		final LinhaVigencia linhaVigente = linha.getLinhaVigente();
		linhaRow.empresa = linhaVigente.getEmpresa().getNome();
		linhaRow.linha = linhaVigente.getCodigo();
		linhaRow.secoes = getSecoes( linhaVigente );
		linhaRow.nomeDaLinha = linhaVigente.getPontoInicial() + " - " + linhaVigente.getPontoFinal();
		linhaRow.status = linhaVigente.getStatus().getNomeFormatado();
		linhaRow.tiposDeVeiculosUtilizados = linhaVigente.getListagemTipoDeVeiculosUtilizados();
		linhaRow.temLinhaFutura = linha.isTemLinhaFutura();

		Locale.setDefault( new Locale( "pt", "BR" ) );

		final DecimalFormat decimalFormat = new DecimalFormat( "#,##0.00" );
		linhaRow.extensaoAB = decimalFormat.format( linha.getLinhaVigente().getExtensaoAB() );
		linhaRow.extensaoBA = decimalFormat.format( linha.getLinhaVigente().getExtensaoBA() );

		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "dd/MM/yyyy" );
		linhaRow.dataDeInicio = simpleDateFormat.format( linha.getLinhaVigente().getDataInicio() );
		linhaRow.dataDeTermino = linha.getLinhaVigente().getDataTermino() == null ? "" : simpleDateFormat.format( linha
			.getLinhaVigente()
			.getDataTermino() );

		return linhaRow;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#getRowClass()
	 */
	@Override
	public Class<LinhaRow> getRowClass()
	{
		return LinhaRow.class;
	}

	/**
	 * Gets the secoes.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return the secoes
	 */
	private String getSecoes( final LinhaVigencia linhaVigencia )
	{
		String secoes = "";
		for ( final Secao secao : linhaVigencia.getSecoes() )
		{
			secoes += secao.getCodigo() + "</br>";
		}
		return secoes;
	}
}

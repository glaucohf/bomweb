package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory;
import br.com.decatron.framework.security.AbstractAuthorization;
import br.com.fetranspor.bomweb.dto.RelatorioDTO;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * A factory for creating RelatorioRow objects.
 */
public class RelatorioRowFactory
	extends JQueryDataTableRowFactory<RelatorioRow, RelatorioDTO>
{

	/**
	 * Instantiates a new relatorio row factory.
	 *
	 * @param abstractAuthorization
	 *            the abstract authorization
	 */
	public RelatorioRowFactory( final AbstractAuthorization abstractAuthorization )
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
	public RelatorioRow cria( final RelatorioDTO linha )
	{
		final RelatorioRow relatorioRow = new RelatorioRow();

		relatorioRow.empresa = ( linha.getEmpresa() == null ) ? "" : linha.getEmpresa();
		relatorioRow.mesReferencia = ( linha.getMesReferencia() == null ) ? "" : linha.getMesReferencia();
		relatorioRow.responsavelBOM = ( linha.getResponsavelBOM() == null ) ? "" : linha.getResponsavelBOM();
		relatorioRow.nomeLinha = ( linha.getNomeLinha() == null ) ? "" : linha.getNomeLinha();
		relatorioRow.codigoLinha = ( linha.getCodigoLinha() == null ) ? "" : linha.getCodigoLinha();
		relatorioRow.descricaoTipoVeiculo = ( linha.getDescricaoTipoVeiculo() == null ) ? "" : linha
			.getDescricaoTipoVeiculo();
		relatorioRow.capacidadeLinha = ( linha.getCapacidadeLinha() == null ) ? 0 : linha.getCapacidadeLinha();
		relatorioRow.frota = ( linha.getFrota() == null ) ? 0 : linha.getFrota();
		relatorioRow.linhaOperou = ( linha.getLinhaOperou() == null ) ? "" : linha.getLinhaOperou();
		relatorioRow.viagensExtraordinariasAB = ( linha.getViagensExtraordinariasAB() == null ) ? 0 : linha
			.getViagensExtraordinariasAB();
		relatorioRow.viagensExtraordinariasBA = ( linha.getViagensExtraordinariasBA() == null ) ? 0 : linha
			.getViagensExtraordinariasBA();
		relatorioRow.viagensOrdinariasAB = ( linha.getViagensOrdinariasAB() == null ) ? 0 : linha
			.getViagensOrdinariasAB();
		relatorioRow.viagensOrdinariasBA = ( linha.getViagensOrdinariasBA() == null ) ? 0 : linha
			.getViagensOrdinariasBA();
		relatorioRow.extensaoLinhaBA = ( linha.getExtensaoLinhaBA() == null ) ? "" : linha.getExtensaoLinhaBA();
		relatorioRow.picoInicioManhaAB = ( linha.getPicoInicioManhaAB() == null ) ? "" : linha.getPicoInicioManhaAB();
		relatorioRow.picoInicioManhaBA = ( linha.getPicoInicioManhaBA() == null ) ? "" : linha.getPicoInicioManhaBA();
		relatorioRow.picoFimManhaAB = ( linha.getPicoFimManhaAB() == null ) ? "" : linha.getPicoFimManhaAB();
		relatorioRow.picoFimManhaBA = ( linha.getPicoFimManhaBA() == null ) ? "" : linha.getPicoFimManhaBA();
		relatorioRow.picoInicioTardeAB = ( linha.getPicoInicioTardeAB() == null ) ? "" : linha.getPicoInicioTardeAB();
		relatorioRow.picoInicioTardeBA = ( linha.getPicoInicioTardeBA() == null ) ? "" : linha.getPicoInicioTardeBA();
		relatorioRow.picoFimTardeAB = ( linha.getPicoFimTardeAB() == null ) ? "" : linha.getPicoFimTardeAB();
		relatorioRow.picoFimTardeBA = ( linha.getPicoFimTardeBA() == null ) ? "" : linha.getPicoFimTardeBA();
		relatorioRow.duracaoViagemPicoAB = ( linha.getDuracaoViagemPicoAB() == null ) ? 0 : linha
			.getDuracaoViagemPicoAB();
		relatorioRow.duracaoViagemPicoBA = ( linha.getDuracaoViagemPicoBA() == null ) ? 0 : linha
			.getDuracaoViagemPicoBA();
		relatorioRow.duracaoViagemForaPicoAB = ( linha.getDuracaoViagemForaPicoAB() == null ) ? 0 : linha
			.getDuracaoViagemForaPicoAB();
		relatorioRow.duracaoViagemForaPicoBA = ( linha.getDuracaoViagemForaPicoBA() == null ) ? 0 : linha
			.getDuracaoViagemForaPicoBA();
		relatorioRow.hierarquizacao = ( linha.getHierarquizacao() == null ) ? "" : linha.getHierarquizacao();
		relatorioRow.numeroLinha = ( linha.getNumeroLinha() == null ) ? "" : linha.getNumeroLinha();
		relatorioRow.statusLinha = ( linha.getStatusLinha() == null ) ? "" : linha.getStatusLinha();
		relatorioRow.tipoLigacao = ( linha.getTipoLigacao() == null ) ? "" : linha.getTipoLigacao();
		relatorioRow.via = ( linha.getVia() == null ) ? "" : linha.getVia();
		relatorioRow.codigoSecao = ( linha.getCodigoSecao() == null ) ? "" : linha.getCodigoSecao();
		relatorioRow.juncaoSecao = ( linha.getJuncaoSecao() == null ) ? "" : linha.getJuncaoSecao();
		relatorioRow.passageirosAB = ( linha.getPassageirosAB() == null ) ? 0 : linha.getPassageirosAB();
		relatorioRow.passageirosAnteriorAB = ( linha.getPassageirosAnteriorAB() == null ) ? 0 : linha
			.getPassageirosAnteriorAB();
		relatorioRow.passageirosBA = ( linha.getPassageirosBA() == null ) ? 0 : linha.getPassageirosBA();
		relatorioRow.passageirosAnteriorBA = ( linha.getPassageirosAnteriorBA() == null ) ? 0 : linha
			.getPassageirosAnteriorBA();
		relatorioRow.secaoSemPassageiro = ( linha.getSecaoSemPassageiro() == null ) ? "" : linha
			.getSecaoSemPassageiro();
		relatorioRow.totalPassageirosAB = ( linha.getTotalPassageirosAB() == null ) ? 0 : linha.getTotalPassageirosAB();
		relatorioRow.totalPassageirosBA = ( linha.getTotalPassageirosBA() == null ) ? 0 : linha.getTotalPassageirosBA();

		Locale.setDefault( new Locale( "pt", "BR" ) );
		final DecimalFormat decimalFormat = new DecimalFormat( "#,##0.00" );

		relatorioRow.piso1AB = ( linha.getPiso1AB() == null ) ? "0" : decimalFormat.format( linha.getPiso1AB() );
		relatorioRow.piso2AB = ( linha.getPiso2AB() == null ) ? "0" : decimalFormat.format( linha.getPiso2AB() );
		relatorioRow.piso1BA = ( linha.getPiso1BA() == null ) ? "0" : decimalFormat.format( linha.getPiso1BA() );
		relatorioRow.piso2BA = ( linha.getPiso2BA() == null ) ? "0" : decimalFormat.format( linha.getPiso2BA() );

		relatorioRow.totalReceita = ( linha.getTotalReceita() == null ) ? "0" : decimalFormat.format( linha
			.getTotalReceita() );
		relatorioRow.tarifaAtual = ( linha.getTarifaAtual() == null ) ? "0" : decimalFormat.format( linha
			.getTarifaAtual() );
		relatorioRow.tarifaPromocional = ( linha.getTarifaPromocional() == null ) ? "" : linha.getTarifaPromocional();
		relatorioRow.tarifaAnterior = ( linha.getTarifaAnterior() == null ) ? "0" : decimalFormat.format( linha
			.getTarifaAnterior() );
		relatorioRow.tarifaVigente = ( linha.getTarifaVigente() == null ) ? "0" : decimalFormat.format( linha
			.getTarifaVigente() );

		return relatorioRow;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.jquery.datatable.JQueryDataTableRowFactory#getRowClass()
	 */
	@Override
	public Class<RelatorioRow> getRowClass()
	{
		return RelatorioRow.class;
	}
}

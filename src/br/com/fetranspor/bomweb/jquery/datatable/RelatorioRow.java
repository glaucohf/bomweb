package br.com.fetranspor.bomweb.jquery.datatable;

import br.com.decatron.framework.jquery.datatable.JQueryDataTableRow;

/**
 * The Class RelatorioRow.
 */
public class RelatorioRow
	extends JQueryDataTableRow
{

	/**
	 * Instantiates a new relatorio row.
	 */
	protected RelatorioRow()
	{
		super( "Relatorio" );
	}

	/**
	 * The empresa.
	 */
	String empresa;

	/**
	 * The mes referencia.
	 */
	String mesReferencia;

	/**
	 * The responsavel bom.
	 */
	String responsavelBOM;

	/**
	 * The nome linha.
	 */
	String nomeLinha;

	/**
	 * The codigo linha.
	 */
	String codigoLinha;

	/**
	 * The descricao tipo veiculo.
	 */
	String descricaoTipoVeiculo;

	/**
	 * The capacidade linha.
	 */
	Integer capacidadeLinha;

	/**
	 * The frota.
	 */
	Integer frota;

	/**
	 * The linha operou.
	 */
	String linhaOperou;

	/**
	 * The viagens extraordinarias ab.
	 */
	Integer viagensExtraordinariasAB;

	/**
	 * The viagens extraordinarias ba.
	 */
	Integer viagensExtraordinariasBA;

	/**
	 * The viagens ordinarias ab.
	 */
	Integer viagensOrdinariasAB;

	/**
	 * The viagens ordinarias ba.
	 */
	Integer viagensOrdinariasBA;

	/**
	 * The piso1 ab.
	 */
	String piso1AB;

	/**
	 * The piso2 ab.
	 */
	String piso2AB;

	/**
	 * The piso1 ba.
	 */
	String piso1BA;

	/**
	 * The piso2 ba.
	 */
	String piso2BA;

	/**
	 * The extensao linha ba.
	 */
	String extensaoLinhaBA;

	/**
	 * The pico inicio manha ab.
	 */
	String picoInicioManhaAB;

	/**
	 * The pico inicio manha ba.
	 */
	String picoInicioManhaBA;

	/**
	 * The pico fim manha ab.
	 */
	String picoFimManhaAB;

	/**
	 * The pico fim manha ba.
	 */
	String picoFimManhaBA;

	/**
	 * The pico inicio tarde ab.
	 */
	String picoInicioTardeAB;

	/**
	 * The pico inicio tarde ba.
	 */
	String picoInicioTardeBA;

	/**
	 * The pico fim tarde ab.
	 */
	String picoFimTardeAB;

	/**
	 * The pico fim tarde ba.
	 */
	String picoFimTardeBA;

	/**
	 * The duracao viagem pico ab.
	 */
	Integer duracaoViagemPicoAB;

	/**
	 * The duracao viagem pico ba.
	 */
	Integer duracaoViagemPicoBA;

	/**
	 * The duracao viagem fora pico ab.
	 */
	Integer duracaoViagemForaPicoAB;

	/**
	 * The duracao viagem fora pico ba.
	 */
	Integer duracaoViagemForaPicoBA;

	/**
	 * The hierarquizacao.
	 */
	String hierarquizacao;

	/**
	 * The numero linha.
	 */
	String numeroLinha;

	/**
	 * The status linha.
	 */
	String statusLinha;

	/**
	 * The tipo ligacao.
	 */
	String tipoLigacao;

	/**
	 * The via.
	 */
	String via;

	/**
	 * The codigo secao.
	 */
	String codigoSecao;

	/**
	 * The juncao secao.
	 */
	String juncaoSecao;

	/**
	 * The passageiros ab.
	 */
	Integer passageirosAB;

	/**
	 * The passageiros anterior ab.
	 */
	Integer passageirosAnteriorAB;

	/**
	 * The passageiros ba.
	 */
	Integer passageirosBA;

	/**
	 * The passageiros anterior ba.
	 */
	Integer passageirosAnteriorBA;

	/**
	 * The secao sem passageiro.
	 */
	String secaoSemPassageiro;

	/**
	 * The total passageiros ab.
	 */
	Integer totalPassageirosAB;

	/**
	 * The total passageiros ba.
	 */
	Integer totalPassageirosBA;

	/**
	 * The total receita.
	 */
	String totalReceita;

	/**
	 * The tarifa atual.
	 */
	String tarifaAtual;

	/**
	 * The tarifa promocional.
	 */
	String tarifaPromocional;

	/**
	 * The tarifa anterior.
	 */
	String tarifaAnterior;

	/**
	 * The tarifa vigente.
	 */
	String tarifaVigente;

}

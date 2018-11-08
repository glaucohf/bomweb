package br.com.fetranspor.bomweb.business;

import static br.com.decatron.framework.dsl.date.DateDsl.date;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.dsl.date.DateBuilder;
import br.com.decatron.framework.excel.DynamicExcel;
import br.com.decatron.framework.excel.Row;
import br.com.decatron.framework.excel.Sheet;
import br.com.decatron.framework.exception.AuthorizationException;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.exception.MailException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.comparator.BomComparator;
import br.com.fetranspor.bomweb.dao.BomDAO;
import br.com.fetranspor.bomweb.dao.BomReaberturaDAO;
import br.com.fetranspor.bomweb.dao.EmpresaDAO;
import br.com.fetranspor.bomweb.dao.LinhaDAO;
import br.com.fetranspor.bomweb.dao.UsuarioPerfilDAO;
import br.com.fetranspor.bomweb.dto.BomPdfDTOFactory;
import br.com.fetranspor.bomweb.dto.FiltroBomPendenteDTO;
import br.com.fetranspor.bomweb.dto.FiltroRelatorioDTO;
import br.com.fetranspor.bomweb.dto.ImportDTO;
import br.com.fetranspor.bomweb.dto.RelatorioDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomReabertura;
import br.com.fetranspor.bomweb.entity.BomSecao;
import br.com.fetranspor.bomweb.entity.Configuracao;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Justificativa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Status;
import br.com.fetranspor.bomweb.entity.StatusFiltroBomPendente;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.exception.ValidationException;
import br.com.fetranspor.bomweb.util.DateProvider;
import br.com.fetranspor.bomweb.util.UtilitiesMail;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * The Class BomBusiness.
 */
@Component
public class BomBusiness
	extends VRaptorBusiness<Bom>
{

	/**
	 * The Constant IMPORTACAO_CELULA_CAP_VEICULO.
	 */
	private static final int IMPORTACAO_CELULA_CAP_VEICULO = 7;

	/**
	 * The Constant IMPORTACAO_CELULA_COD_SECAO.
	 */
	private static final int IMPORTACAO_CELULA_COD_SECAO = 13;

	/**
	 * The Constant IMPORTACAO_CELULA_CODIGO_LINHA.
	 */
	private static final int IMPORTACAO_CELULA_CODIGO_LINHA = 2;

	/**
	 * The Constant IMPORTACAO_CELULA_FROTA.
	 */
	private static final int IMPORTACAO_CELULA_FROTA = 6;

	/**
	 * The Constant IMPORTACAO_CELULA_ID_BOM_LINHA.
	 */
	private static final int IMPORTACAO_CELULA_ID_BOM_LINHA = 1;

	/**
	 * The Constant IMPORTACAO_CELULA_ID_BOM_SECAO.
	 */
	private static final int IMPORTACAO_CELULA_ID_BOM_SECAO = 12;

	/**
	 * The Constant IMPORTACAO_CELULA_INOPERANTE.
	 */
	private static final int IMPORTACAO_CELULA_INOPERANTE = 17;

	/**
	 * The Constant IMPORTACAO_CELULA_NOME_LINHA.
	 */
	private static final int IMPORTACAO_CELULA_NOME_LINHA = 4;

	/**
	 * The Constant IMPORTACAO_CELULA_NOME_SECAO.
	 */
	private static final int IMPORTACAO_CELULA_NOME_SECAO = 14;

	/**
	 * The Constant IMPORTACAO_CELULA_NUMERO_LINHA.
	 */
	private static final int IMPORTACAO_CELULA_NUMERO_LINHA = 3;

	/**
	 * The Constant IMPORTACAO_CELULA_QTD_DE_PASS_AB.
	 */
	private static final int IMPORTACAO_CELULA_QTD_DE_PASS_AB = 18;

	/**
	 * The Constant IMPORTACAO_CELULA_QTD_DE_PASS_ANT_AB.
	 */
	private static final int IMPORTACAO_CELULA_QTD_DE_PASS_ANT_AB = 19;

	/**
	 * The Constant IMPORTACAO_CELULA_QTD_DE_PASS_ANT_BA.
	 */
	private static final int IMPORTACAO_CELULA_QTD_DE_PASS_ANT_BA = 21;

	/**
	 * The Constant IMPORTACAO_CELULA_QTD_DE_PASS_BA.
	 */
	private static final int IMPORTACAO_CELULA_QTD_DE_PASS_BA = 20;

	/**
	 * The Constant IMPORTACAO_CELULA_TARIFA.
	 */
	private static final int IMPORTACAO_CELULA_TARIFA = 15;

	/**
	 * The Constant IMPORTACAO_CELULA_TARIFA_ANTERIOR.
	 */
	private static final int IMPORTACAO_CELULA_TARIFA_ANTERIOR = 16;

	/**
	 * The Constant IMPORTACAO_CELULA_TIPO_VEICULO.
	 */
	private static final int IMPORTACAO_CELULA_TIPO_VEICULO = 5;

	/**
	 * The Constant IMPORTACAO_CELULA_VIAGEM_EXTRAORD_AB.
	 */
	private static final int IMPORTACAO_CELULA_VIAGEM_EXTRAORD_AB = 9;

	/**
	 * The Constant IMPORTACAO_CELULA_VIAGEM_EXTRAORD_BA.
	 */
	private static final int IMPORTACAO_CELULA_VIAGEM_EXTRAORD_BA = 11;

	/**
	 * The Constant IMPORTACAO_CELULA_VIAGEM_ORD_AB.
	 */
	private static final int IMPORTACAO_CELULA_VIAGEM_ORD_AB = 8;

	/**
	 * The Constant IMPORTACAO_CELULA_VIAGEM_ORD_BA.
	 */
	private static final int IMPORTACAO_CELULA_VIAGEM_ORD_BA = 10;

	/**
	 * The Constant IMPORTACAO_PRIMEIRA_LINHA_DADOS.
	 */
	private static final int IMPORTACAO_PRIMEIRA_LINHA_DADOS = 3;

	/**
	 * The Constant LOG.
	 */
	private static final Log LOG = LogFactory.getLog( BomBusiness.class );

	/**
	 * Instantiates a new bom business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 * @param tarifaBusiness
	 *            the tarifa business
	 * @param linhaDAO
	 *            the linha dao
	 * @param configuracaoBusiness
	 *            the configuracao business
	 * @param empresaDAO
	 *            the empresa dao
	 * @param usuarioPerfilDAO
	 *            the usuario perfil dao
	 * @param bomReaberturaDAO
	 */
	public BomBusiness(
		final VRaptorProvider provider,
		final BomDAO dao,
		final TarifaBusiness tarifaBusiness,
		final LinhaDAO linhaDAO,
		final ConfiguracaoBusiness configuracaoBusiness,
		final EmpresaDAO empresaDAO,
		final UsuarioPerfilDAO usuarioPerfilDAO,
		final BomReaberturaDAO bomReaberturaDAO )
	{
		super( provider );
		this.dao = dao;
		this.bomDAO = dao;
		this.tarifaBusiness = tarifaBusiness;
		this.linhaDAO = linhaDAO;
		this.configuracaoBusiness = configuracaoBusiness;
		this.empresaDAO = empresaDAO;
		this.usuarioPerfilDAO = usuarioPerfilDAO;
		this.bomReaberturaDAO = bomReaberturaDAO;
	}

	/**
	 * Atualizar dados pico linha.
	 *
	 * @param bomLinhaTela
	 *            the bom linha tela
	 * @param bomLinhaBanco
	 *            the bom linha banco
	 */
	private void atualizarDadosPicoLinha( final BomLinha bomLinhaTela, final BomLinha bomLinhaBanco )
	{

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getDuracaoViagemPicoAB() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setDuracaoViagemPicoAB(
				bomLinhaTela.getLinhaVigencia().getDuracaoViagemPicoAB() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoInicioManhaAB() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoInicioManhaAB(
				bomLinhaTela.getLinhaVigencia().getPicoInicioManhaAB() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoFimManhaAB() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoFimManhaAB( bomLinhaTela.getLinhaVigencia().getPicoFimManhaAB() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getDuracaoViagemForaPicoAB() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setDuracaoViagemForaPicoAB(
				bomLinhaTela.getLinhaVigencia().getDuracaoViagemForaPicoAB() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoInicioTardeAB() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoInicioTardeAB(
				bomLinhaTela.getLinhaVigencia().getPicoInicioTardeAB() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoFimTardeAB() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoFimTardeAB( bomLinhaTela.getLinhaVigencia().getPicoFimTardeAB() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getDuracaoViagemPicoBA() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setDuracaoViagemPicoBA(
				bomLinhaTela.getLinhaVigencia().getDuracaoViagemPicoBA() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoInicioManhaBA() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoInicioManhaBA(
				bomLinhaTela.getLinhaVigencia().getPicoInicioManhaBA() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoFimManhaBA() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoFimManhaBA( bomLinhaTela.getLinhaVigencia().getPicoFimManhaBA() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getDuracaoViagemForaPicoBA() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setDuracaoViagemForaPicoBA(
				bomLinhaTela.getLinhaVigencia().getDuracaoViagemForaPicoBA() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoInicioTardeBA() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoInicioTardeBA(
				bomLinhaTela.getLinhaVigencia().getPicoInicioTardeBA() );
		}

		if ( Check.isBlank( bomLinhaBanco.getLinhaVigencia().getPicoFimTardeBA() ) )
		{
			bomLinhaBanco.getLinhaVigencia().setPicoFimTardeBA( bomLinhaTela.getLinhaVigencia().getPicoFimTardeBA() );
		}

	}

	/**
	 * Before update.
	 *
	 * @param entity
	 *            the entity
	 * @throws BusinessException
	 *             the business exception
	 */
	private void beforeUpdate( final BomSecao entity )
		throws BusinessException
	{

		if ( !isTudoPreenchido( entity ) )
		{
			final String errorMessage = "Por favor, preencha todos os campos.";
			throw new BusinessException( errorMessage );
		}

	}

	/**
	 * Buscar bom linha.
	 *
	 * @param id
	 *            the id
	 * @return the bom linha
	 */
	public BomLinha buscarBomLinha( final Long id )
	{
		final BomLinha bomLinha = this.bomDAO.buscarBomLinha( id );

		final Usuario usuario = ( Usuario ) getUser();
		if ( !bomLinha.getBom().getEmpresa().equals( usuario.getEmpresa() ) )
		{
			throw new AuthorizationException( "Você não pode acessar esse recurso." );
		}

		return bomLinha;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	public List<BomLinha> buscarBomLinhaList( final Bom bom )
	{
		final List<BomLinha> bomLinhaList = this.bomDAO.pesquisarLinhas( bom );
		return bomLinhaList;
	}

	/**
	 * Buscar bom secao.
	 *
	 * @param id
	 *            the id
	 * @return the bom secao
	 */
	public BomSecao buscarBomSecao( final Long id )
	{
		final BomSecao bomSecao = this.bomDAO.buscarBomSecao( id );
		final BomSecao bomSecaoAnterior = this.bomDAO.buscaBomSecaoAnterior( bomSecao.getSecao(), bomSecao
			.getBomLinha()
			.getTipoDeVeiculo(), bomSecao.getBomLinha().getBom().getMesReferencia() );
		if ( Check.isNull( bomSecaoAnterior ) )
		{
			bomSecao.setPrimeiroBomSecao( true );
		}
		else
		{
			bomSecao.setPrimeiroBomSecao( false );
		}

		return bomSecao;
	}

	/**
	 * Buscar bom secoes por secao.
	 *
	 * @param secao
	 *            the secao
	 * @return the list
	 */
	public List<BomSecao> buscarBomSecoesPorSecao( final Secao secao )
	{
		return this.bomDAO.buscarBomSecoesPorSecao( secao );
	}

	// -- BOM

	/**
	 * Buscar linhas recriar.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	public List<LinhaVigencia> buscarLinhasRecriar( final Bom bom )
	{

		final List<LinhaVigencia> linhas = this.bomDAO.buscarLinhasVigenciaDoBom( bom );

		final Status[] statusFechado = {Status.FECHADO};
		final List<LinhaVigencia> linhasFechadas = this.bomDAO.buscarLinhasVigenciaPorStatusBomLinha(
			bom,
			statusFechado );
		if ( Check.isNotEmpty( linhasFechadas ) )
		{
			bom.setTemBomLinhaFechado( true );
		}

		for ( final LinhaVigencia linhaVigencia : linhas )
		{
			if ( linhasFechadas.contains( linhaVigencia ) )
			{
				linhaVigencia.setBomLinhaFechado( true );
			}
			else
			{
				if ( linhaVigencia.isActive() )
				{
					bom.setTemBomLinhaAberto( true );
				}
				else
				{
					bom.setTemLinhaExcluida( true );
				}
			}
		}

		return linhas;

	}

	/**
	 * Creates the justificativas.
	 *
	 * @param linhas
	 *            the linhas
	 * @return the list
	 */
	private List<Justificativa> createJustificativas( final List<BomLinha> linhas )
	{
		final List<Justificativa> justificativas = new ArrayList<Justificativa>();
		for ( final BomLinha bomLinha : linhas )
		{
			if ( Check.isNotNull( bomLinha.getId() ) )
			{
				final Justificativa justificativa = new Justificativa();
				justificativa.setDescricao( bomLinha.getUltimaJustificativa() );
				justificativa.setBomLinha( bomLinha );
				justificativa.setUsuario( ( Usuario ) getUser() );
				justificativa.setDataCriacao( new Date() );
				justificativas.add( justificativa );
			}
		}
		return justificativas;
	}

	/**
	 * Cria e salva bom linhas com bom secoes.
	 *
	 * @param bom
	 *            the bom
	 * @param linhaVigente
	 *            the linha vigente
	 * @return the list
	 * @throws BusinessException
	 *             the business exception
	 */
	protected List<BomLinha> criaESalvaBomLinhasComBomSecoes( final Bom bom, final LinhaVigencia linhaVigente )
		throws BusinessException
	{
		if ( ( bom == null ) || ( linhaVigente == null ) )
		{
			throw new IllegalArgumentException();
		}

		final List<BomLinha> bomLinhasNovos = new ArrayList<BomLinha>();
		final Linha linha = linhaVigente.getLinha();
		final List<Secao> secoes = linhaVigente.getSecoes();
		final Set<TipoDeVeiculo> tiposDeVeiculo = linhaVigente.getTipoDeVeiculosUtilizados();
		final String mesReferencia = bom.getMesReferencia();
		for ( final TipoDeVeiculo tipoDeVeiculo : tiposDeVeiculo )
		{
			final BomLinha bomLinhaAnterior = this.bomDAO.buscaBomLinhaAnterior( linha, tipoDeVeiculo, mesReferencia );

			final BomLinha bomLinha = new BomLinha();
			bomLinha.setBom( bom );
			bomLinha.setLinhaVigencia( linhaVigente );
			bomLinha.setPiso1AB( linhaVigente.getPiso1AB() );
			bomLinha.setPiso1BA( linhaVigente.getPiso1BA() );
			bomLinha.setPiso2AB( linhaVigente.getPiso2AB() );
			bomLinha.setPiso2BA( linhaVigente.getPiso2BA() );
			bomLinha.setTipoDeVeiculo( tipoDeVeiculo );
			bomLinha.setStatus( Status.ABERTO );

			if ( Check.isNotNull( bomLinhaAnterior ) )
			{
				bomLinha.setCapacidade( bomLinhaAnterior.getCapacidade() );
			}

			bomLinhasNovos.add( bomLinha );

			final List<BomSecao> bomSecoes = new ArrayList<BomSecao>();
			bomLinha.setSecoes( bomSecoes );
			for ( final Secao secao : secoes )
			{

				// FBW-191 INIT
				// Não pegar seçoes que tenham dataTermino
				final int mesBom = Integer.valueOf( mesReferencia.split( "/" )[0] ) - 1;
				final int anoBom = Integer.valueOf( mesReferencia.split( "/" )[1] );

				final Date dataInicio = date().withMonth( mesBom ).withYear( anoBom ).firstDayOfMonth().toDate();

				if ( Check.isNotNull( secao.getDataTermino() ) )
				{
					if ( secao.getDataTermino().compareTo( dataInicio ) <= 0 )

					{
						continue;
					}
				}

				// FBW-191 END

				final BomSecao bomSecaoAnterior = this.bomDAO.buscaBomSecaoAnterior(
					secao,
					tipoDeVeiculo,
					mesReferencia );

				final BomSecao bomSecao = new BomSecao();
				bomSecao.setBomLinha( bomLinha );
				bomSecao.setSecao( secao );
				bomSecao.setStatus( Status.ABERTO );

				// FBW-137 | pega as seções com base em sua data de vigor., no caso, passar as
				// linhas
				// vigentes que foram criadas até a data igual mesAno
				final Calendar dataReferencia = Calendar.getInstance();

				dataReferencia.set( anoBom, mesBom, 1 );
				final int maxDayOfMonth = dataReferencia.getActualMaximum( Calendar.DAY_OF_MONTH );
				dataReferencia.set( Calendar.DAY_OF_MONTH, maxDayOfMonth );

				final Date temp = new Date( dataReferencia.getTimeInMillis() );

				if ( secao.getDataCriacao().compareTo( temp ) < 0 )
				{
					// FBW-36
					final Tarifa tarifaAtual = this.tarifaBusiness.buscaTarifaMes( secao, mesReferencia );
					BigDecimal valorDaTarifaAtual;

					if ( Check.isNotNull( tarifaAtual ) )
					{
						valorDaTarifaAtual = tarifaAtual.getValor();
					}
					else
					{
						throw new BusinessException( "Erro ao criar o bom linha. Não existe tarifa cadastrada." );
					}
					bomSecao.setTarifaAtual( valorDaTarifaAtual );
					bomSecao.setTarifa( valorDaTarifaAtual );

					if ( Check.isNotNull( bomSecaoAnterior ) )
					{
						final BigDecimal tarifa = bomSecaoAnterior.getTarifa();
						if ( Check.isNotNull( tarifa ) )
						{
							bomSecao.setTarifaAnterior( tarifa );
						}
					}
					else
					{
						final Tarifa tarifaAnterior = this.tarifaBusiness.buscaTarifaAnterior( secao );
						BigDecimal valorDaTarifaAnterior;
						if ( Check.isNotNull( tarifaAnterior ) )
						{
							valorDaTarifaAnterior = tarifaAnterior.getValor();
						}
						else
						{
							valorDaTarifaAnterior = null;
						}

						if ( Check.isNull( tarifaAtual ) )
						{
							throw new BusinessException( "Não existe tarifa vigente cadastrada para: "
								+ secao.getLinhaVigencia().getItinerarioIda()
								+ " / Seção: "
								+ secao.getJuncao() );
						}

						// Caso não haja BomSecao anterior, a tarifa é igual à
						// tarifa atual.
						bomSecao.setTarifa( valorDaTarifaAtual );

						if ( Check.isNotNull( tarifaAnterior ) )
						{
							bomSecao.setTarifaAnterior( valorDaTarifaAnterior );
						}
						else
						{
							bomSecao.setTarifaAnterior( valorDaTarifaAtual );
						}
					}

					bomSecoes.add( bomSecao );
					// bomDAO.save(bomSecao);
				}// FBW-137
			}

			this.bomDAO.save( bomLinha );
		}

		return bomLinhasNovos;
	}

	/**
	 * Cria parametros jasper.
	 *
	 * @return the map
	 */
	protected Map<Object, Object> criaParametrosJasper()
	{
		final Map<Object, Object> parameters = new HashMap<Object, Object>();

		parameters.put( "REPORT_LOCALE", new Locale( "pt", "BR" ) );
		parameters.put( "CAMINHO_LOGO", getResourcePath( "logo_relatorio.gif" ) );
		parameters.put( "BOMSECAO_SUBREPORT", getResourcePath( "secao.jasper" ) );
		parameters.put( "LINHA_SUBREPORT", getResourcePath( "linha.jasper" ) );
		parameters.put( "BOMLINHA_SUBREPORT", getResourcePath( "bomLinha.jasper" ) );

		return parameters;
	}

	/**
	 * Deleta bom linhas.
	 *
	 * @param bomLinhas
	 *            the bom linhas
	 */
	private void deletaBomLinhas( final List<BomLinha> bomLinhas )
	{
		for ( final BomLinha bomLinhaAntigo : bomLinhas )
		{
			this.bomDAO.delete( bomLinhaAntigo );
		}
	}

	/**
	 * Deleta justificativas.
	 *
	 * @param justificativas
	 *            the justificativas
	 */
	private void deletaJustificativas( final List<Justificativa> justificativas )
	{
		for ( final Justificativa justificativa : justificativas )
		{
			this.bomDAO.delete( justificativa );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @throws BusinessException
	 */
	public boolean estaDentroDoPrazoDeReabertura( Bom bom )
		throws BusinessException
	{

		if ( usuarioLogadoNaoEEmpresa() )
		{
			return true;
		}// if
		bom = get( bom.getId() );
		final Date dataDeFechamentoDoBom = bom.getDataFechamento();
		final String mesReferenciaStr = bom.getMesReferencia();
		final Date prazoFinalDeReabertura = getPrazoFinalDeReabertura( dataDeFechamentoDoBom, mesReferenciaStr );
		final Date hoje = new Date();
		// FBW-676 o check
		if ( Check.isNull( prazoFinalDeReabertura )
			|| ( Check.isNotNull( prazoFinalDeReabertura ) && hoje.after( prazoFinalDeReabertura ) ) )
		{
			return false;
		}// if
		return true;

	}

	/**
	 * Excel name.
	 *
	 * @param bom
	 *            the bom
	 * @return the string
	 */
	private String excelName( final Bom bom )
	{
		final StringBuffer nome = new StringBuffer();
		nome.append( "emp" );
		nome.append( bom.getEmpresa().getCodigo() );
		nome.append( "_" );
		nome.append( bom.getMesReferencia().replaceAll( "/", "" ) );
		nome.append( ".xls" );
		return this.configuracaoBusiness.buscarDiretorioXLS() + nome.toString();
	}

	/**
	 * Exportar bom.
	 *
	 * @param bomPersistido
	 *            the bom persistido
	 * @return the file
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public File exportarBOM( final Bom bomPersistido )
		throws IOException
	{

		final DynamicExcel excel = new DynamicExcel();

		final Sheet sheet = excel.addSheet();

		preencheCabecalho( sheet, bomPersistido );
		Row row;

		final Status[] status = {	Status.ABERTO,
									Status.PREENCHIDO,
									Status.INOPERANTE};
		final List<BomLinha> bomLinhas = this.bomDAO.pesquisarLinhasPorStatus( bomPersistido, status );
		int i = 1;
		for ( final BomLinha bomLinha : bomLinhas )
		{
			final LinhaVigencia linhaVigencia = bomLinha.getLinhaVigencia();

			final List<BomSecao> bomSecoes = bomLinha.getSecoes();
			boolean primeiraLinha = true;

			LOG.info( String.format( "Numero: %s tamanha: %s \n", i++, bomSecoes.size() ) );
			for ( final BomSecao bomSecao : bomSecoes )
			{
				final Secao secao = bomSecao.getSecao();
				row = sheet.addRow().centralize();
				row
					.addCell( IMPORTACAO_CELULA_ID_BOM_LINHA )
					.setText( bomLinha.getId().toString() )
					.setBackgroundColor( new HSSFColor.TAN() );
				row
					.addCell( IMPORTACAO_CELULA_CODIGO_LINHA )
					.setText( linhaVigencia.getCodigo() )
					.setBackgroundColor( new HSSFColor.TAN() );
				row
					.addCell( IMPORTACAO_CELULA_NUMERO_LINHA )
					.setText( linhaVigencia.getNumeroLinha() )
					.setBackgroundColor( new HSSFColor.TAN() );
				row
					.addCell( IMPORTACAO_CELULA_NOME_LINHA )
					.setText( linhaVigencia.getPontoInicial() + " - " + linhaVigencia.getPontoFinal() )
					.setBackgroundColor( new HSSFColor.TAN() );
				row
					.addCell( IMPORTACAO_CELULA_TIPO_VEICULO )
					.setText( bomLinha.getTipoDeVeiculo().getDescricao() )
					.setBackgroundColor( new HSSFColor.TAN() );
				if ( primeiraLinha )
				{
					if ( bomLinha.getFrota() == null )
					{
						row
							.addCell( IMPORTACAO_CELULA_FROTA )
							.setText( "" )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					else
					{
						row
							.addCell( IMPORTACAO_CELULA_FROTA )
							.setValue( bomLinha.getFrota() )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					if ( bomLinha.getCapacidade() == null )
					{
						row
							.addCell( IMPORTACAO_CELULA_CAP_VEICULO )
							.setText( "" )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					else
					{
						row
							.addCell( IMPORTACAO_CELULA_CAP_VEICULO )
							.setValue( bomLinha.getCapacidade() )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					if ( bomLinha.getViagensOrdinariasAB() == null )
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_ORD_AB )
							.setText( "" )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					else
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_ORD_AB )
							.setValue( bomLinha.getViagensOrdinariasAB() )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					if ( bomLinha.getViagensExtraordinariasAB() == null )
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_EXTRAORD_AB )
							.setText( "" )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					else
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_EXTRAORD_AB )
							.setValue( bomLinha.getViagensExtraordinariasAB() )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					if ( bomLinha.getViagensOrdinariasBA() == null )
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_ORD_BA )
							.setText( "" )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					else
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_ORD_BA )
							.setValue( bomLinha.getViagensOrdinariasBA() )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					if ( bomLinha.getViagensExtraordinariasBA() == null )
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_EXTRAORD_BA )
							.setText( "" )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					else
					{
						row
							.addCell( IMPORTACAO_CELULA_VIAGEM_EXTRAORD_BA )
							.setValue( bomLinha.getViagensExtraordinariasBA() )
							.setBackgroundColor( new HSSFColor.PALE_BLUE() );
					}
					primeiraLinha = false;
				}
				row
					.addCell( IMPORTACAO_CELULA_ID_BOM_SECAO )
					.setText( bomSecao.getId().toString() )
					.setBackgroundColor( new HSSFColor.TAN() );
				row
					.addCell( IMPORTACAO_CELULA_COD_SECAO )
					.setText( secao.getCodigo().toString() )
					.setBackgroundColor( new HSSFColor.TAN() );
				row
					.addCell( IMPORTACAO_CELULA_NOME_SECAO )
					.setText( secao.getJuncao() )
					.setBackgroundColor( new HSSFColor.TAN() );
				if ( bomSecao.getTarifa() == null )
				{
					row
						.addCell( IMPORTACAO_CELULA_TARIFA )
						.setText( "" )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				else
				{
					row
						.addCell( IMPORTACAO_CELULA_TARIFA )
						.setValue( bomSecao.getTarifa() )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}

				if ( bomSecao.getTarifaAnterior() == null )
				{
					row
						.addCell( IMPORTACAO_CELULA_TARIFA_ANTERIOR )
						.setText( "" )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				else
				{
					row
						.addCell( IMPORTACAO_CELULA_TARIFA_ANTERIOR )
						.setValue( bomSecao.getTarifaAnterior() )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}

				row
					.addCell( IMPORTACAO_CELULA_INOPERANTE )
					.setText( Status.INOPERANTE.equals( bomSecao.getStatus() ) ? "S" : "N" )
					.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				if ( bomSecao.getPassageiroAB() == null )
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_AB )
						.setText( "" )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				else
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_AB )
						.setValue( bomSecao.getPassageiroAB() )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				if ( bomSecao.getPassageiroAnteriorAB() == null )
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_ANT_AB )
						.setText( "" )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				else
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_ANT_AB )
						.setValue( bomSecao.getPassageiroAnteriorAB() )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				if ( bomSecao.getPassageiroBA() == null )
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_BA )
						.setText( "" )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				else
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_BA )
						.setValue( bomSecao.getPassageiroBA() )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				if ( bomSecao.getPassageiroAnteriorBA() == null )
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_ANT_BA )
						.setText( "" )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
				else
				{
					row
						.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_ANT_BA )
						.setValue( bomSecao.getPassageiroAnteriorBA() )
						.setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
				}
			}
		}

		preencheLegenda( sheet );

		return excel.generate( excelName( bomPersistido ) );
	}

	/**
	 * Fechar.
	 *
	 * @param bom
	 *            the bom
	 * @throws BusinessException
	 *             the business exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public void fechar( final Bom bom )
		throws BusinessException,
			ValidationException
	{
		final Bom bomToUse = get( bom.getId() );

		validateFechar( bomToUse );

		try
		{
			this.bomDAO.fechar( bomToUse );

			geraPdf( bomToUse );

			final Usuario usuario = ( Usuario ) getUser();

			final String from = this.configuracaoBusiness.buscarEmailDetro();
			final String to = usuario.getEmail() + ", " + bomToUse.getEmpresa().getEmailContato();
			final String subject = "Fechamento do BOM";
			final String body = "O BOM da empresa "
				+ bomToUse.getEmpresa().getNome()
				+ " referente ao mês "
				+ bomToUse.getMesReferencia()
				+ " foi fechado e enviado ao DETRO com sucesso."
				+ "\n\nO arquivo em anexo deve ser guardado como comprovante.";

			final String attachment = getNomePdf( bomToUse );

			UtilitiesMail.sendMail( from, to, subject, body, attachment );

		}
		catch ( final MailException e )
		{
			throw new BusinessException(
				"Ocorreu um erro ao informar ao usuário que o BOM foi fechado. O mesmo não foi fechado.",
				e );
		}
		catch ( final DaoException e )
		{
			throw new BusinessException(
				"Ocorreu um erro ao tentar fechar o BOM. Entre em contato com o administrador do sistema.",
				e );
		}
	}

	/**
	 * Garante que nao tem bom linha fechado.
	 *
	 * @param bomLinhas
	 *            the bom linhas
	 * @throws ValidationException
	 *             the validation exception
	 */
	private void garanteQueNaoTemBomLinhaFechado( final List<BomLinha> bomLinhas )
		throws ValidationException
	{
		for ( final BomLinha bomLinha : bomLinhas )
		{
			if ( bomLinha.getStatus() == Status.FECHADO )
			{
				throw new ValidationException(
					"Não é possível reabrir a linha pois ela contém um ou mais bom linhas abertos." );
			}
		}
	}

	/**
	 * Generate.
	 *
	 * @param bom
	 *            the bom
	 * @throws BusinessException
	 *             the business exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public void generate( final Bom bom )
		throws BusinessException,
			ValidationException
	{
		final Usuario usuario = ( Usuario ) getUser();
		bom.setEmpresa( usuario.getEmpresa() );
		bom.setResponsavel( usuario.getEmpresa().getResponsavel() );

		validateGenerate( bom );

		bom.setStatus( Status.ABERTO );
		bom.setActive( Boolean.TRUE );

		try
		{
			this.bomDAO.save( bom );
		}
		catch ( final DaoException e )
		{
			throw new BusinessException( e );
		}

		final List<LinhaVigencia> linhasVigentesDaEmpresaNoMesAno = this.linhaDAO
			.buscarLinhasVigentesDaEmpresaNoMesAno( bom.getEmpresa(), bom.getMesReferencia() );
		for ( final LinhaVigencia linhaVigenteDaEmpresaNoMesAno : linhasVigentesDaEmpresaNoMesAno )
		{
			criaESalvaBomLinhasComBomSecoes( bom, linhaVigenteDaEmpresaNoMesAno );
		}
	}

	/**
	 * Gera pdf.
	 *
	 * @param bom
	 *            the bom
	 * @throws BusinessException
	 *             the business exception
	 */
	private void geraPdf( final Bom bom )
		throws BusinessException
	{
		final JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(
			Collections.singleton( new BomPdfDTOFactory().cria( bom, this.bomDAO.pesquisarLinhas( bom ) ) ) );
		final Map<Object, Object> parametrosJasper = criaParametrosJasper();
		final String nomePdf = getNomePdf( bom );

		try
		{
			final JasperReport jasperReport = ( JasperReport ) JRLoader
				.loadObject( getResourceAsStream( "bom.jasper" ) );
			final JasperPrint jasperPrint = JasperFillManager.fillReport( jasperReport, parametrosJasper, dataSource );
			JasperExportManager.exportReportToPdfFile( jasperPrint, nomePdf );
		}
		catch ( final JRException e )
		{
			throw new BusinessException( "Ocorreu um erro ao gerar o PDF do BOM.", e );
		}
	}

	/**
	 * Gerar boms pendentes inexistentes.
	 *
	 * @param empresa
	 *            the empresa
	 * @param filtro
	 *            the filtro
	 * @param bomsPersistidos
	 *            the boms persistidos
	 * @return the list
	 * @throws ParseException
	 *             the parse exception
	 */
	private List<Bom> gerarBomsPendentesInexistentes(
		final Empresa empresa,
		final FiltroBomPendenteDTO filtro,
		final List<Bom> bomsPersistidos )
		throws ParseException
	{
		final List<Bom> bomsInexistentes = new ArrayList<Bom>();
		final SimpleDateFormat monthFormat = new SimpleDateFormat( "MM/yyyy" );

		Date dtPrimeiroBom = DateUtils.truncate( empresa.getInicioVigenciaBom(), Calendar.MONTH );
		if ( Check.isNotBlank( filtro.getDataInicial() ) )
		{
			final Date dtInicialFiltro = monthFormat.parse( filtro.getDataInicial() );
			if ( dtInicialFiltro.compareTo( dtPrimeiroBom ) > 0 )
			{
				dtPrimeiroBom = dtInicialFiltro;
			}
		}

		final Integer prazoBom = Integer.parseInt( this.configuracaoBusiness.buscarConfiguracaoBom().getValor() );
		// Ultimo bom vencido é o do mes anterior ao atual, subtraido do prazo
		// em dias para fechar o bom.
		Date dtUltimoBomVencido = DateUtils.addMonths(
			DateUtils.truncate( DateUtils.addDays( new Date(), -prazoBom ), Calendar.MONTH ),
			-1 );

		if ( Check.isNotBlank( filtro.getDataFinal() ) )
		{
			final Date dtFinalFiltro = monthFormat.parse( filtro.getDataFinal() );
			if ( dtFinalFiltro.compareTo( dtUltimoBomVencido ) < 0 )
			{
				dtUltimoBomVencido = dtFinalFiltro;
			}
		}

		for ( ; dtUltimoBomVencido.compareTo( dtPrimeiroBom ) >= 0; dtPrimeiroBom = DateUtils.addMonths(
			dtPrimeiroBom,
			1 ) )
		{
			final Bom bom = new Bom();
			bom.setEmpresa( empresa );
			bom.setMesReferencia( monthFormat.format( dtPrimeiroBom ) );
			if ( !bomsPersistidos.contains( bom ) )
			{
				bom.setDataReabertura( null );
				final Date dataLimite = DateUtils.addDays( DateUtils.addMonths( dtPrimeiroBom, 1 ), prazoBom - 1 );
				bom.setDataLimiteFechamento( dataLimite );
				bom.setStatus( null );
				bomsInexistentes.add( bom );
			}
		}

		return bomsInexistentes;

	}

	/**
	 * Gerar boms pendentes inexistentes.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 * @throws ParseException
	 *             the parse exception
	 */
	private List<Bom> gerarBomsPendentesInexistentes( final FiltroBomPendenteDTO filtro )
		throws ParseException
	{
		final List<Bom> bomsPersistidos = this.bomDAO.list();
		if ( Check.isNotBlank( filtro.getEmpresa() ) )
		{
			return gerarBomsPendentesInexistentes(
				this.empresaDAO.get( Long.parseLong( filtro.getEmpresa() ) ),
				filtro,
				bomsPersistidos );
		}
		final List<Bom> bomsInexistentes = new ArrayList<Bom>();
		final List<Empresa> empresas = this.empresaDAO.list();
		for ( final Empresa empresa : empresas )
		{
			if ( empresa.isActive() )
			{
				bomsInexistentes.addAll( gerarBomsPendentesInexistentes( empresa, filtro, bomsPersistidos ) );

			}// if
		}
		return bomsInexistentes;

	}

	/**
	 * Gerar datas limites entrega.
	 *
	 * @param bomsPendentes
	 *            the boms pendentes
	 * @param prazoBom
	 *            the prazo bom
	 * @param prazoBomReaberto
	 *            the prazo bom reaberto
	 * @throws ParseException
	 *             the parse exception
	 */
	private void gerarDatasLimitesEntrega(
		final List<Bom> bomsPendentes,
		final Integer prazoBom,
		final Integer prazoBomReaberto )
		throws ParseException
	{
		final SimpleDateFormat monthFormat = new SimpleDateFormat( "MM/yyyy" );

		for ( final Bom bom : bomsPendentes )
		{
			if ( bom.getDataReabertura() == null )
			{
				bom.setDataLimiteFechamento( DateUtils.addDays(
					DateUtils.addMonths( monthFormat.parse( bom.getMesReferencia() ), 1 ),
					prazoBom - 1 ) );
			}
			else
			{
				bom.setDataLimiteFechamento( DateUtils.addDays( bom.getDataReabertura(), prazoBomReaberto ) );
			}
		}
	}

	/**
	 * Gets the bom por bom linha id.
	 *
	 * @param bomLinhaId
	 *            the bom linha id
	 * @return the bom por bom linha id
	 */
	public Bom getBomPorBomLinhaId( final Long bomLinhaId )
	{
		return this.bomDAO.buscarBomLinha( bomLinhaId ).getBom();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	public List<BomReabertura> getBomReabertura( final Bom bom )
	{
		final List<BomReabertura> bomReaberturaList = this.bomReaberturaDAO.buscaBomReaberturaByBom( bom );
		return bomReaberturaList;
	}

	/**
	 * Gets the empresas com bom fechado.
	 *
	 * @return the empresas com bom fechado
	 */
	public List<Empresa> getEmpresasComBomFechado()
	{
		return this.bomDAO.getEmpresasComBomFechado();
	}

	/**
	 * Gets the empresas com bom fechado excluindo empresa.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the empresas com bom fechado excluindo empresa
	 */
	public List<Empresa> getEmpresasComBomFechadoExcluindoEmpresa( final Empresa empresa )
	{
		return this.bomDAO.getEmpresasComBomFechadoExcluindoEmpresa( empresa );
	}

	// -- LINHA

	/**
	 * Gets the linhas do bom pelos ids empresas.
	 *
	 * @param idsEmpresas
	 *            the ids empresas
	 * @return the linhas do bom pelos ids empresas
	 */
	public List<Linha> getLinhasDoBomPelosIdsEmpresas( final List<Long> idsEmpresas )
	{
		return this.bomDAO.getLinhasDoBomPelosIdsEmpresas( idsEmpresas );
	}

	/**
	 * Gets the linhas vigencia do bom pelos ids empresas.
	 *
	 * @param idsEmpresas
	 *            the ids empresas
	 * @return the linhas vigencia do bom pelos ids empresas
	 */
	public List<LinhaVigencia> getLinhasVigenciaDoBomPelosIdsEmpresas( final List<Long> idsEmpresas )
	{
		return this.bomDAO.getLinhasVigenciaDoBomPelosIdsEmpresas( idsEmpresas );
	}

	/**
	 * Gets the linhas vigencia do bom pelos ids linhas.
	 *
	 * @param idsLinhas
	 *            the ids linhas
	 * @return the linhas vigencia do bom pelos ids linhas
	 */
	public List<LinhaVigencia> getLinhasVigenciaDoBomPelosIdsLinhas( final List<Long> idsLinhas )
	{
		return this.bomDAO.getLinhasVigenciaDoBomPelosIdsLinhas( idsLinhas );
	}

	/**
	 * Gets the map campos relatorio.
	 *
	 * @return the map campos relatorio
	 */
	public HashMap<String, String> getMapCamposRelatorio()
	{
		// <idCampo, nomeCampo>
		final HashMap<String, String> hp = new HashMap<String, String>();

		hp.put( "empresa", "Empresa" );
		hp.put( "mesReferencia", "Mês de Referência" );
		hp.put( "responsavelBOM", "Responsável BOM" );
		hp.put( "nomeLinha", "Nome da Linha" );
		hp.put( "descricaoTipoVeiculo", "Tipo de Veículo" );
		hp.put( "capacidadeLinha", "Capacidade da Linha" );
		hp.put( "codigoLinha", "Código da Linha" );
		hp.put( "frota", "Frota" );
		hp.put( "linhaOperou", "Linha Operou" );
		hp.put( "viagensExtraordinariasAB", "Viagens Extraordinárias A-B" );
		hp.put( "viagensExtraordinariasBA", "Viagens Extraordinárias B-A" );
		hp.put( "viagensOrdinariasAB", "Viagens Ordinárias A-B" );
		hp.put( "viagensOrdinariasBA", "Viagens Ordinárias B-A" );
		hp.put( "piso1AB", "Extensão Piso I A-B" );
		hp.put( "piso1BA", "Extensão Piso I B-A" );
		hp.put( "piso2AB", "Extensão Piso II A-B" );
		hp.put( "piso2BA", "Extensão Piso II B-A" );
		hp.put( "picoInicioManhaAB", "Início Horário de Pico Manhã - A-B" );
		hp.put( "picoInicioManhaBA", "Início Horário de Pico Manhã - B-A" );
		hp.put( "picoFimManhaAB", "Fim Horário de Pico Manhã - A-B" );
		hp.put( "picoFimManhaBA", "Fim Horário de Pico Manhã - B-A" );
		hp.put( "picoInicioTardeAB", "Início Horário de Pico Tarde - A-B" );
		hp.put( "picoInicioTardeBA", "Início Horário de Pico Tarde - B-A" );
		hp.put( "picoFimTardeAB", "Fim Horário de Pico Tarde - A-B" );
		hp.put( "picoFimTardeBA", "Fim Horário de Pico Tarde - B-A" );
		hp.put( "duracaoViagemPicoAB", "Duração Viagem Horário de Pico - A-B" );
		hp.put( "duracaoViagemPicoBA", "Duração Viagem Horário de Pico - B-A" );
		hp.put( "duracaoViagemForaPicoAB", "Duração Viagem Fora Horário de Pico - A-B" );
		hp.put( "duracaoViagemForaPicoBA", "Duração Viagem Fora Horário de Pico - B-A" );
		hp.put( "hierarquizacao", "Hierarquização" );
		hp.put( "numeroLinha", "Número da Linha" );
		hp.put( "tipoLigacao", "Tipo da Ligação" );
		hp.put( "via", "Via" );
		hp.put( "statusLinha", "Status da Linha" );

		hp.put( "codigoSecao", "Código da Seção" );
		hp.put( "juncaoSecao", "Nome da Seção" );
		hp.put( "passageirosAB", "Quantidade de Passageiros A-B" );
		hp.put( "passageirosAnteriorAB", "Quantidade de Passageiros A-B - Anterior" );
		hp.put( "passageirosBA", "Quantidade de Passageiros B-A" );
		hp.put( "passageirosAnteriorBA", "Quantidade de Passageiros B-A - Anterior" );
		hp.put( "secaoSemPassageiro", "Sem Passageiro" );
		hp.put( "totalPassageirosAB", "Total Passageiros A-B" );
		hp.put( "totalPassageirosBA", "Total Passageiros B-A" );
		hp.put( "totalReceita", "Total Receita" );

		hp.put( "tarifaAtual", "Tarifa Atual" );
		hp.put( "tarifaVigente", "Tarifa Vigente" );
		hp.put( "tarifaPromocional", "Tarifa Promocional" );
		hp.put( "tarifaAnterior", "Tarifa Anterior" );

		return hp;
	}

	/**
	 * Gets the nome pdf.
	 *
	 * @param bom
	 *            the bom
	 * @return the nome pdf
	 */
	private String getNomePdf( final Bom bom )
	{
		final StringBuffer nome = new StringBuffer();
		nome.append( "Emp_" );
		nome.append( bom.getEmpresa().getCodigo() );
		nome.append( "_" );
		nome.append( bom.getMesReferencia().replaceAll( "/", "_" ) );
		nome.append( ".pdf" );

		return this.configuracaoBusiness.buscarDiretorioPDF() + nome.toString();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataDeFechamentoDoBom
	 * @param mesReferenciaStr
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 */
	public Date getPrazoFinalDeReabertura( final Date dataDeFechamentoDoBom, final String mesReferenciaStr )
		throws BusinessException

	{
		// FBW-676 Se não tiver data de fechamento, não tem final de reabertura
		if ( Check.isNull( dataDeFechamentoDoBom ) )
		{
			return null;
		}
		// --FBW-676
		Date mesAno = null;
		try
		{
			mesAno = DateProvider.parseMesAnoToDate( mesReferenciaStr );
		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
				throw new BusinessException( "Ocorreu um problema interno" );
			}
		}
		final Calendar prazoDeFechamentoDoBom = Calendar.getInstance();
		prazoDeFechamentoDoBom.setTime( mesAno );
		final Configuracao configuracaoBom = this.configuracaoBusiness.buscarConfiguracaoBom();
		final String valorStr = configuracaoBom.getValor();
		final int valorConfiguracaoBom = Integer.parseInt( valorStr );
		prazoDeFechamentoDoBom.set( Calendar.DAY_OF_MONTH, valorConfiguracaoBom );
		final Configuracao configuracaoBomReaberturaPelaEmpresa = this.configuracaoBusiness
			.buscarConfiguracaoBomReaberturaPelaEmpresa();
		final String valorConfiguracaoReaberturaPelaEmpresaStr = configuracaoBomReaberturaPelaEmpresa.getValor();
		final int valorConfiguracaoReaberturaPelaEmpresa = Integer.parseInt( valorConfiguracaoReaberturaPelaEmpresaStr );
		final Calendar prazoFinalDeFechamentoDoBom = Calendar.getInstance();
		prazoFinalDeFechamentoDoBom.setTime( prazoDeFechamentoDoBom.getTime() );
		prazoFinalDeFechamentoDoBom.add( Calendar.DAY_OF_MONTH, valorConfiguracaoReaberturaPelaEmpresa );
		if ( dataDeFechamentoDoBom.after( prazoDeFechamentoDoBom.getTime() ) )
		{
			prazoFinalDeFechamentoDoBom.setTime( dataDeFechamentoDoBom );
			prazoFinalDeFechamentoDoBom.add( Calendar.DAY_OF_MONTH, valorConfiguracaoReaberturaPelaEmpresa );
		}// if
		return prazoFinalDeFechamentoDoBom.getTime();
	}

	/**
	 * Gets the resource as stream.
	 *
	 * @param name
	 *            the name
	 * @return the resource as stream
	 */
	protected InputStream getResourceAsStream( final String name )
	{
		return getClass().getClassLoader().getResourceAsStream( name );
	}

	/**
	 * Gets the resource path.
	 *
	 * @param name
	 *            the name
	 * @return the resource path
	 */
	protected String getResourcePath( final String name )
	{
		final URL resource = getClass().getClassLoader().getResource( name );

		if ( Check.isNull( resource ) )
		{
			throw new RuntimeException( "O recurso '" + name + "' não foi encontrado." );
		}

		return resource.getPath();
	}

	/**
	 * Gets the tipos veiculo de bom linha.
	 *
	 * @return the tipos veiculo de bom linha
	 */
	public List<TipoDeVeiculo> getTiposVeiculoDeBomLinha()
	{
		return this.bomDAO.getTiposVeiculoDeBomLinha();
	}

	/**
	 * Historico justificativas.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @return the list
	 */
	public List<Justificativa> historicoJustificativas( final BomLinha bomLinha )
	{
		return this.bomDAO.buscarJustificativasPorBomLinha( bomLinha );
	}

	/**
	 * Import excel.
	 *
	 * @param bom
	 *            the bom
	 * @param importFile
	 *            the import file
	 * @return the int
	 * @throws BusinessException
	 *             the business exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public int importExcel( final Bom bom, final File importFile )
		throws BusinessException,
			ValidationException
	{
		final ImportExcel excel = new ImportExcel();
		final List<ImportDTO> listImportDTOs = excel.loadArquivo( importFile );

		int numSecoesAtualizadas = 0;

		if ( Check.isNotNull( listImportDTOs ) && Check.isNotEmpty( listImportDTOs ) )
		{
			validateImportExcel( listImportDTOs, bom );

			int i = IMPORTACAO_PRIMEIRA_LINHA_DADOS;
			List<String> linha = listImportDTOs.get( i++ ).getValues();
			String id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
			BomLinha bomLinha = null;
			boolean lerBomLinha;

			while ( ( id != null ) && !"".equals( id ) )
			{
				// Processa os bomlinhas apenas uma vez(repetição de linhas
				// devido às varias seções).
				if ( ( bomLinha == null ) || !new Long( Long.parseLong( id ) ).equals( bomLinha.getId() ) )
				{
					lerBomLinha = true;
				}
				else
				{
					lerBomLinha = false;
				}

				final BomSecao bomSecao = this.bomDAO.buscarBomSecao( Long.parseLong( linha
					.get( IMPORTACAO_CELULA_ID_BOM_SECAO - 1 ) ) );
				// Não atualiza as linhas fechadas.
				if ( Status.FECHADO.equals( bomSecao.getBomLinha().getStatus() ) )
				{
					linha = listImportDTOs.get( i++ ).getValues();
					id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
					continue;
				}

				if ( lerBomLinha )
				{
					if ( bomLinha != null )
					{
						update( bomLinha );
					}
					bomLinha = this.bomDAO.buscarBomLinha( Long.parseLong( id ) );
					preencheBomLinha( bomLinha, linha );
				}

				preencheBomSecao( bomSecao, linha );
				update( bomSecao );
				bomLinha.getSecoes().add( bomSecao );
				numSecoesAtualizadas++;

				linha = listImportDTOs.get( i++ ).getValues();
				id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
			}
			if ( bomLinha != null )
			{
				update( bomLinha );
			}

		}
		return numSecoesAtualizadas;
	}

	/**
	 * Checks if is tudo preenchido.
	 *
	 * @param bom
	 *            the bom
	 * @return true, if is tudo preenchido
	 * @throws BusinessException
	 *             the business exception
	 */
	private boolean isTudoPreenchido( final Bom bom )
		throws BusinessException
	{

		final List<BomLinha> linhas = pesquisarLinhas( bom );
		for ( final BomLinha bomLinha : linhas )
		{
			if ( bomLinha.getStatus().equals( Status.ABERTO ) )
			{
				return Boolean.FALSE;
			}
			if ( !bomLinha.getStatus().equals( Status.INOPERANTE ) )
			{
				for ( final BomSecao bomSecao : bomLinha.getSecoes() )
				{
					if ( bomSecao.getStatus().equals( Status.ABERTO ) )
					{
						return Boolean.FALSE;
					}
				}
			}
		}

		return Boolean.TRUE;

	}

	// -- SECAO

	/**
	 * Checks if is tudo preenchido.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @return true, if is tudo preenchido
	 */
	private boolean isTudoPreenchido( final BomLinha bomLinha )
	{
		return ( Check.isNotNull( bomLinha.getFrota() )
			&& Check.isNotNull( bomLinha.getCapacidade() )
			&&

			Check.isNotNull( bomLinha.getViagensOrdinariasAB() )
			&& Check.isNotNull( bomLinha.getViagensExtraordinariasAB() )
			&& Check.isNotNull( bomLinha.getViagensOrdinariasBA() )
			&& Check.isNotNull( bomLinha.getViagensExtraordinariasBA() )
			&&

			Check.isNotBlank( bomLinha.getLinhaVigencia().getDuracaoViagemPicoAB() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoInicioManhaAB() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoFimManhaAB() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getDuracaoViagemForaPicoAB() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoInicioTardeAB() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoFimTardeAB() )
			&&

			Check.isNotBlank( bomLinha.getLinhaVigencia().getDuracaoViagemPicoBA() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoInicioManhaBA() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoFimManhaBA() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getDuracaoViagemForaPicoBA() )
			&& Check.isNotBlank( bomLinha.getLinhaVigencia().getPicoInicioTardeBA() ) && Check.isNotBlank( bomLinha
			.getLinhaVigencia()
			.getPicoFimTardeBA() ) );
	}

	/**
	 * Checks if is tudo preenchido.
	 *
	 * @param bomSecao
	 *            the bom secao
	 * @return true, if is tudo preenchido
	 */
	private boolean isTudoPreenchido( final BomSecao bomSecao )
	{
		return ( Check.isNotNull( bomSecao.getPassageiroAB() )
			&& Check.isNotNull( bomSecao.getPassageiroBA() )
			&& Check.isNotNull( bomSecao.getPassageiroAnteriorAB() ) && Check.isNotNull( bomSecao
			.getPassageiroAnteriorBA() ) );
	}

	/**
	 * Linha inoperante.
	 *
	 * @param idBomLinha
	 *            the id bom linha
	 * @throws BusinessException
	 *             the business exception
	 */
	public void linhaInoperante( final Long idBomLinha )
		throws BusinessException
	{
		final BomLinha bomLinha = this.bomDAO.buscarBomLinha( idBomLinha );
		bomLinha.setStatus( Status.INOPERANTE );
		this.bomDAO.update( bomLinha );
		// secoesInoperantes(bomLinha);
		tudoPreenchido( bomLinha );
	}

	/**
	 * Linhas inoperantes.
	 *
	 * @param linhas
	 *            the linhas
	 * @throws BusinessException
	 *             the business exception
	 * @throws BusinessException
	 *             the business exception
	 */
	public void linhasInoperantes( final List<BomLinha> linhas )
		throws BusinessException,
			BusinessException
	{

		boolean todasLinhasInoperantes = Boolean.TRUE;

		// Verifica se todas as linhas estao inoperantes
		for ( final BomLinha bomLinha : linhas )
		{
			if ( !bomLinha.isInoperante() )
			{
				todasLinhasInoperantes = Boolean.FALSE;
				break;
			}
		}

		if ( todasLinhasInoperantes )
		{
			throw new BusinessException( "Ao menos uma linha deve estar operando." );
		}

		for ( final BomLinha bomLinhaTela : linhas )
		{
			final BomLinha bomLinha = buscarBomLinha( bomLinhaTela.getId() );
			if ( bomLinhaTela.isInoperante() )
			{
				linhaInoperante( bomLinha.getId() );
			}
			else
			{
				if ( isTudoPreenchido( bomLinha ) )
				{
					bomLinha.setStatus( Status.PREENCHIDO );
				}
				else
				{
					bomLinha.setStatus( Status.ABERTO );
					tudoPreenchido( bomLinha.getBom() );
				}
				this.bomDAO.update( bomLinha );
			}
		}

	}

	/**
	 * Lista relatorio com filtro.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	public List<RelatorioDTO> listaRelatorioComFiltro( final FiltroRelatorioDTO filtro )
	{
		final List<String> camposBuscaPermitidos = Arrays.asList(
			"codigoSecao",
			"juncaoSecao",
			"passageirosAB",
			"passageirosAnteriorAB",
			"passageirosBA",
			"passageirosAnteriorBA",
			"secaoSemPassageiro",
			"totalPassageirosAB",
			"totalPassageirosBA",
			"totalReceita",
			"tarifaAnterior",
			"tarifaAtual",
			"tarifaVigente" );

		if ( filtro == null )
		{
			return new ArrayList<RelatorioDTO>();
		}

		boolean joinComSecao = false;
		for ( final String campoBusca : filtro.getCamposRelatorio() )
		{
			if ( camposBuscaPermitidos.contains( campoBusca ) )
			{
				joinComSecao = true;
				break;
			}
		}

		return this.bomDAO.montaRelatorioComFiltro( filtro, joinComSecao );
	}

	/**
	 * List pendentes com filtro.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 * @throws BusinessException
	 *             the business exception
	 */
	public List<Bom> listPendentesComFiltro( final FiltroBomPendenteDTO filtro )
		throws BusinessException
	{

		final Integer prazoBom = Integer.parseInt( this.configuracaoBusiness.buscarConfiguracaoBom().getValor() );
		final Integer prazoBomReaberto = Integer.parseInt( this.configuracaoBusiness
			.buscarConfiguracaoBomReaberturaPeloDetro()
			.getValor() );

		List<Bom> bomsPendentes = new ArrayList<Bom>();
		// Status Vencido é considerado para o BOM que não foi nem mesmo gerado.
		if ( !StatusFiltroBomPendente.Vencido.name().equals( filtro.status ) )
		{
			bomsPendentes = this.bomDAO.listPendentesComFiltro( filtro, prazoBom, prazoBomReaberto );
		}

		try
		{
			gerarDatasLimitesEntrega( bomsPendentes, prazoBom, prazoBomReaberto );

			// Status Vencido é considerado para o BOM que não foi nem mesmo
			// gerado.
			if ( Check.isBlank( filtro.status )
				|| StatusFiltroBomPendente.Todos.name().equals( filtro.status )
				|| StatusFiltroBomPendente.Vencido.name().equals( filtro.status ) )
			{
				final List<Bom> bomsNaoCriados = gerarBomsPendentesInexistentes( filtro );
				bomsPendentes.addAll( bomsNaoCriados );
			}

			// FBW-26
			Collections.sort( bomsPendentes, new BomComparator() );

			return bomsPendentes;
		}
		catch ( final ParseException e )
		{
			throw new BusinessException( e );
		}
	}

	/**
	 * Move justificativas dos bom linhas antigos para os bom linhas novos atraves do tipo de
	 * veiculo.
	 *
	 * @param bomLinhasAntigos
	 *            the bom linhas antigos
	 * @param bomLinhasNovos
	 *            the bom linhas novos
	 */
	private void moveJustificativasDosBomLinhasAntigosParaOsBomLinhasNovosAtravesDoTipoDeVeiculo(
		final List<BomLinha> bomLinhasAntigos,
		final List<BomLinha> bomLinhasNovos )
	{
		for ( final BomLinha bomLinhaAntigo : bomLinhasAntigos )
		{
			final List<Justificativa> justificativas = this.bomDAO.buscarJustificativasPorBomLinha( bomLinhaAntigo );

			// Se não tem justificativa nenhuma, não precisa mover.
			if ( Check.isEmpty( justificativas ) )
			{
				continue;
			}

			final Long idTipoDeVeiculo = bomLinhaAntigo.getTipoDeVeiculo().getId();
			BomLinha bomLinhaNovoCorrespondente = null;
			for ( final BomLinha bomLinhaNovo : bomLinhasNovos )
			{
				if ( idTipoDeVeiculo.equals( bomLinhaNovo.getTipoDeVeiculo().getId() ) )
				{
					bomLinhaNovoCorrespondente = bomLinhaNovo;
					break;
				}
			}

			// Se eu não achei um bom linha novo correspondente, foi porque eu
			// desassociei o tipo de veículo da linha.
			if ( Check.isNull( bomLinhaNovoCorrespondente ) )
			{
				// Apaga as justificativas pois o bom linha será apagado.
				deletaJustificativas( justificativas );
				continue;
			}

			// Copia o campo "última justificativa".
			bomLinhaNovoCorrespondente.setUltimaJustificativa( bomLinhaAntigo.getUltimaJustificativa() );
			this.bomDAO.update( bomLinhaNovoCorrespondente );

			// Move as justificativas.
			for ( final Justificativa justificativa : justificativas )
			{
				justificativa.setBomLinha( bomLinhaNovoCorrespondente );

				this.bomDAO.update( justificativa );
			}
		}
	}

	/**
	 * Pega pdf.
	 *
	 * @param bom
	 *            the bom
	 * @return the file
	 * @throws BusinessException
	 *             the business exception
	 */
	public File pegaPdf( final Bom bom )
		throws BusinessException
	{
		geraPdf( bom );
		return new File( getNomePdf( bom ) );
	}

	/**
	 * Pesquisar linhas.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 * @throws BusinessException
	 *             the business exception
	 */
	public List<BomLinha> pesquisarLinhas( Bom bom )
		throws BusinessException

	{
		final Usuario usuario = ( Usuario ) getUser();
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		bom = get( bom.getId() );
		if ( Check.isNull( bom ) )
		{
			throw new BusinessException( "Bom não encontrado." );
		}

		if ( perfil.isEmpresa() && !bom.getEmpresa().equals( usuario.getEmpresa() ) )
		{
			throw new AuthorizationException( "Você não pode acessar esse recurso." );
		}
		return this.bomDAO.pesquisarLinhas( bom );
	}

	/**
	 * Pesquisar linhas editaveis.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 * @throws BusinessException
	 *             the business exception
	 */
	public List<BomLinha> pesquisarLinhasEditaveis( Bom bom )
		throws BusinessException
	{
		final Usuario usuario = ( Usuario ) getUser();
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		bom = get( bom.getId() );
		if ( Check.isNull( bom ) )
		{
			throw new BusinessException( "Bom não encontrado." );
		}
		if ( perfil.isEmpresa() && !bom.getEmpresa().equals( usuario.getEmpresa() ) )
		{
			throw new AuthorizationException( "Você não pode acessar esse recurso." );
		}

		final Status[] status = {	Status.ABERTO,
									Status.PREENCHIDO};
		return this.bomDAO.pesquisarLinhasPorStatus( bom, status );
	}

	/**
	 * Preenche bom linha.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @param linha
	 *            the linha
	 */
	private void preencheBomLinha( final BomLinha bomLinha, final List<String> linha )
	{
		bomLinha.setFrota( Integer.parseInt( linha.get( IMPORTACAO_CELULA_FROTA - 1 ) ) );
		bomLinha.setCapacidade( Integer.parseInt( linha.get( IMPORTACAO_CELULA_CAP_VEICULO - 1 ) ) );
		bomLinha.setViagensOrdinariasAB( Integer.parseInt( linha.get( IMPORTACAO_CELULA_VIAGEM_ORD_AB - 1 ) ) );
		bomLinha
			.setViagensExtraordinariasAB( Integer.parseInt( linha.get( IMPORTACAO_CELULA_VIAGEM_EXTRAORD_AB - 1 ) ) );
		bomLinha.setViagensOrdinariasBA( Integer.parseInt( linha.get( IMPORTACAO_CELULA_VIAGEM_ORD_BA - 1 ) ) );
		bomLinha
			.setViagensExtraordinariasBA( Integer.parseInt( linha.get( IMPORTACAO_CELULA_VIAGEM_EXTRAORD_BA - 1 ) ) );
	}

	/**
	 * Preenche bom secao.
	 *
	 * @param bomSecao
	 *            the bom secao
	 * @param linha
	 *            the linha
	 */
	private void preencheBomSecao( final BomSecao bomSecao, final List<String> linha )
	{
		bomSecao.setTarifa( BigDecimal.valueOf( Double.parseDouble( linha.get( IMPORTACAO_CELULA_TARIFA - 1 ).replace(
			",",
			"." ) ) ) );
		bomSecao.setTarifaAnterior( BigDecimal.valueOf( Double.parseDouble( linha.get(
			IMPORTACAO_CELULA_TARIFA_ANTERIOR - 1 ).replace( ",", "." ) ) ) );

		bomSecao.setInoperante( "S".equals( linha.get( IMPORTACAO_CELULA_INOPERANTE - 1 ) ) ? true : false );
		if ( !bomSecao.isInoperante() )
		{
			bomSecao.setPassageiroAB( Integer.parseInt( linha.get( IMPORTACAO_CELULA_QTD_DE_PASS_AB - 1 ) ) );
			bomSecao.setPassageiroBA( Integer.parseInt( linha.get( IMPORTACAO_CELULA_QTD_DE_PASS_BA - 1 ) ) );
		}
		else
		{
			bomSecao.setPassageiroAB( 0 );
			bomSecao.setPassageiroBA( 0 );
		}
		if ( bomSecao.isInoperante()
			|| new Double( bomSecao.getTarifaAnterior().doubleValue() ).equals( bomSecao.getTarifa().doubleValue() ) )
		{
			bomSecao.setPassageiroAnteriorAB( 0 );
			bomSecao.setPassageiroAnteriorBA( 0 );
		}
		else
		{
			bomSecao
				.setPassageiroAnteriorAB( Integer.parseInt( linha.get( IMPORTACAO_CELULA_QTD_DE_PASS_ANT_AB - 1 ) ) );
			bomSecao
				.setPassageiroAnteriorBA( Integer.parseInt( linha.get( IMPORTACAO_CELULA_QTD_DE_PASS_ANT_BA - 1 ) ) );
		}
	}

	/**
	 * Preenche cabecalho.
	 *
	 * @param sheet
	 *            the sheet
	 * @param bomPersistido
	 *            the bom persistido
	 */
	private void preencheCabecalho( final Sheet sheet, final Bom bomPersistido )
	{
		Row row;

		row = sheet.addRow();
		row.addCell( 2 ).useBold().setText( "Mês Ref" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );

		row.addCell().setText( "ID Empresa" ).useBold().setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );

		row.addCell().setText( "Empresa" ).useBold().setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );

		//

		row = sheet.addRow();
		row.addCell( 2 ).setText( bomPersistido.getMesReferencia() );

		row.addCell().setText( bomPersistido.getEmpresa().getId().toString() );

		row.addCell().setText( bomPersistido.getEmpresa().getNome() );

		//

		row = sheet.addRow();
		row
			.addCell( IMPORTACAO_CELULA_VIAGEM_ORD_AB )
			.mergeColumns( 1 )
			.centralize()
			.setText( "A-B" )
			.setFontColor( new HSSFColor.WHITE() )
			.useBold()
			.setBackgroundColor( new HSSFColor.DARK_RED() );

		row
			.addCell( IMPORTACAO_CELULA_VIAGEM_ORD_BA )
			.mergeColumns( 1 )
			.centralize()
			.setText( "B-A" )
			.setFontColor( new HSSFColor.WHITE() )
			.useBold()
			.setBackgroundColor( new HSSFColor.DARK_RED() );

		row
			.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_AB )
			.mergeColumns( 1 )
			.centralize()
			.setText( "A-B" )
			.setFontColor( new HSSFColor.WHITE() )
			.useBold()
			.setBackgroundColor( new HSSFColor.DARK_RED() );

		row
			.addCell( IMPORTACAO_CELULA_QTD_DE_PASS_BA )
			.mergeColumns( 1 )
			.centralize()
			.setText( "B-A" )
			.setFontColor( new HSSFColor.WHITE() )
			.useBold()
			.setBackgroundColor( new HSSFColor.DARK_RED() );

		//

		row = sheet.addRow().useBold().centralize();

		row.addCell().setText( "ID" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Código da Linha" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Número da Linha" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Nome da Linha" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Tipo de Veículo" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Frota" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Capacidade do Veículo" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Viag. Ordinarias" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Viag. Extraord." ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Viag. Ordinarias" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Viag. Extraord." ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "ID Seção" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Código Seção" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Seção" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Tarifa (R$)" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Tarifa anterior(R$)" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Sem Passageiros(S/N)" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Qtd. de Pass." ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Qtd. de Pass. Ant." ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Qtd. de Pass." ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell().setText( "Qtd. de Pass. Ant." ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );

	}

	/**
	 * Preenche legenda.
	 *
	 * @param sheet
	 *            the sheet
	 */
	private void preencheLegenda( final Sheet sheet )
	{
		Row row;
		sheet.addRow();
		sheet.addRow();
		sheet.addRow();
		sheet.addRow();
		row = sheet.addRow();
		row.addCell( 2 ).setBackgroundColor( new HSSFColor.TAN() );
		row.addCell().setText( "Campos que já vem preenchidos" );
		row = sheet.addRow();
		row.addCell( 2 ).setBackgroundColor( new HSSFColor.PALE_BLUE() );
		row.addCell().setText( "Campos a preencher (Linha)" );
		row = sheet.addRow();
		row.addCell( 2 ).setBackgroundColor( new HSSFColor.LIGHT_ORANGE() );
		row.addCell().setText( "Campos a preencher (Seção)" );
	}

	/**
	 * Reabrir bom.
	 *
	 * @param bom
	 *            the bom
	 * @param linhas
	 *            the linhas
	 * @throws BusinessException
	 *             the business exception
	 */
	public void reabrirBom( Bom bom, final List<BomLinha> linhas )
		throws BusinessException
	{
		final List<Justificativa> justificativas = createJustificativas( linhas );
		try
		{
			this.bomDAO.reabertura( bom, justificativas, linhas );

		}
		catch ( final DaoException e )
		{
			throw new BusinessException(
				"Ocorreu um erro ao reabrir o BOM. Entre em contato com o administrador do sistema.",
				e );
		}

		bom = get( bom.getId() );
		if ( Check.isBlank( bom.getEmpresa().getEmailContato() ) )
		{
			throw new BusinessException( "A empresa não possui um email de contato cadastrado." );
		}
		String destinatarios = bom.getEmpresa().getEmailContato();

		final List<Usuario> usuarios = this.usuarioPerfilDAO.getUsuariosAtivosDaEmpresa( bom.getEmpresa() );
		for ( final Usuario usuario : usuarios )
		{
			destinatarios += ", " + usuario.getEmail();
		}

		try
		{
			final String from = this.configuracaoBusiness.buscarEmailDetro();
			final String to = destinatarios;
			final String subject = "Reabertura do BOM";
			final String body = "O BOM da empresa "
				+ bom.getEmpresa().getNome()
				+ " referente ao mês "
				+ bom.getMesReferencia()
				+ " foi reaberto."
				+ "\n\nA empresa tem o prazo de "
				+ this.configuracaoBusiness.buscarConfiguracaoBomReaberturaPeloDetro().getValor()
				+ " dias para realizar os ajustes solicitados.";

			UtilitiesMail.sendMail( from, to, subject, body );
		}
		catch ( final MailException e )
		{
			throw new BusinessException( "Ocorreu um erro ao informar ao usuário que o BOM foi reaberto.", e );
		}

	}

	/**
	 * Reabrir bom.
	 *
	 * @param bom
	 *            the bom
	 * @param linhas
	 *            the linhas
	 * @throws BusinessException
	 *             the business exception
	 */
	public void reabrirBomPelaEmpresa( Bom bom, final List<BomLinha> linhas )
		throws BusinessException
	{
		final List<Justificativa> justificativas = createJustificativas( linhas );
		try
		{
			this.bomDAO.reabertura( bom, justificativas, linhas );
			salvarbomReabertura( bom );

		}
		catch ( final DaoException e )
		{
			throw new BusinessException(
				"Ocorreu um erro ao reabrir o BOM. Entre em contato com o administrador do sistema.",
				e );
		}

		bom = get( bom.getId() );
		if ( Check.isBlank( bom.getEmpresa().getEmailContato() ) )
		{
			throw new BusinessException( "A empresa não possui um email de contato cadastrado." );
		}
		String destinatarios = bom.getEmpresa().getEmailContato();

		final List<Usuario> usuarios = this.usuarioPerfilDAO.getUsuariosAtivosDaEmpresa( bom.getEmpresa() );
		for ( final Usuario usuario : usuarios )
		{
			destinatarios += ", " + usuario.getEmail();
		}
		destinatarios += ", " + this.configuracaoBusiness.buscarEmailDetro();
		try
		{
			final String from = this.configuracaoBusiness.buscarEmailDetro();
			final String to = destinatarios;
			final String subject = "Reabertura do BOM pela empresa";
			final String body = "O BOM da empresa "
				+ bom.getEmpresa().getNome()
				+ " referente ao mês "
				+ bom.getMesReferencia()
				+ " foi reaberto pela própria empresa."
				+ "\n\nA empresa tem o prazo de "
				+ this.configuracaoBusiness.buscarConfiguracaoBomReaberturaPelaEmpresa().getValor()
				+ " dias para realizar os ajustes solicitados.";

			UtilitiesMail.sendMail( from, to, subject, body );
		}
		catch ( final MailException e )
		{
			throw new BusinessException( "Ocorreu um erro ao informar ao usuário que o BOM foi reaberto.", e );
		}

	}

	/**
	 * Recriar linha.
	 *
	 * @param bom
	 *            the bom
	 * @param linhaVigente
	 *            the linha vigente
	 * @throws ValidationException
	 *             the validation exception
	 * @throws BusinessException
	 *             the business exception
	 */
	public void recriarLinha( final Bom bom, final LinhaVigencia linhaVigente )
		throws ValidationException,
			BusinessException
	{
		if ( Check.isNull( bom ) || Check.isNull( linhaVigente ) )
		{
			throw new IllegalArgumentException();
		}

		// FIXME:
		final Bom bomToUse = this.bomDAO.get( bom.getId() );
		final LinhaVigencia linhaVigenteToUse = this.bomDAO.pegaLinhaVigencia( linhaVigente.getId() );
		bomToUse.setResponsavel( linhaVigenteToUse.getEmpresa().getResponsavel() );
		// Recupera os bom linhas do bom e linha vigente passados.
		final List<BomLinha> bomLinhasAntigos = this.bomDAO.pesquisarLinhasPorBomELinhaVigencia(
			bomToUse,
			linhaVigenteToUse );

		garanteQueNaoTemBomLinhaFechado( bomLinhasAntigos );

		// Cria novos bom linhas com bom seções para a linha vigente que foi
		// passada.
		final List<BomLinha> bomLinhasNovos = criaESalvaBomLinhasComBomSecoes( bomToUse, linhaVigenteToUse );

		// Move as justificativas dos bom linhas antigos para os bom linhas novos,
		// associando-as de antigo para novo através do tipo de veículo.
		moveJustificativasDosBomLinhasAntigosParaOsBomLinhasNovosAtravesDoTipoDeVeiculo(
			bomLinhasAntigos,
			bomLinhasNovos );

		// Deleta bom linhas e bom secões antigos.
		deletaBomLinhas( bomLinhasAntigos );
	}

	/**
	 * Recriar linhas.
	 *
	 * @param bom
	 *            the bom
	 * @param linhas
	 *            the linhas
	 * @throws ValidationException
	 *             the validation exception
	 * @throws BusinessException
	 *             the business exception
	 */
	public void recriarLinhas( final Bom bom, final List<LinhaVigencia> linhas )
		throws ValidationException,
			BusinessException
	{
		if ( ( linhas == null ) || linhas.isEmpty() )
		{
			throw new IllegalArgumentException();
		}

		for ( final LinhaVigencia linha : linhas )
		{
			recriarLinha( bom, linha );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @throws DaoException
	 */
	private void salvarbomReabertura( final Bom bom )
		throws DaoException
	{
		final BomReabertura bomReabertura = new BomReabertura();
		bomReabertura.setBom( bom );
		this.bomReaberturaDAO.save( bomReabertura );
	}

	// TODO Tive que mudar o nome e nao usar o @Override do framework por causa
	// da exception
	/**
	 * Search bom.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 * @throws BusinessException
	 *             the business exception
	 */
	public List<Bom> searchBom( final Bom bom )
		throws BusinessException
	{

		if ( Check.isNotNull( bom ) && Check.isNotBlank( bom.getMesReferencia() ) )
		{

			final Date dataInicioSistema = this.configuracaoBusiness.buscarDataInicioSistema();

			final int mesBom = Integer.valueOf( bom.getMesReferencia().split( "/" )[0] ) - 1;
			final int anoBom = Integer.valueOf( bom.getMesReferencia().split( "/" )[1] );

			final Date dataBom = date().withMonth( mesBom ).withYear( anoBom ).toDate();

			if ( dataBom.before( dataInicioSistema ) )
			{
				throw new BusinessException( "Por favor utilize datas posteriores a "
					+ date( dataInicioSistema ).format( "MM/yyyy" ) );
			}

		}

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( perfil.isEmpresa() )
		{
			final Usuario usuario = ( Usuario ) getUser();
			final List<Bom> pesquisarPor = this.bomDAO.pesquisarPor( usuario.getEmpresa() );
			return pesquisarPor;
		}
		final List<Bom> search = this.bomDAO.search( bom );
		return search;

	}

	/**
	 * Secoes inoperantes.
	 *
	 * @param bomLinha
	 *            the bom linha
	 */
	public void secoesInoperantes( final BomLinha bomLinha )
	{
		for ( final BomSecao bomSecao : bomLinha.getSecoes() )
		{
			if ( Status.INOPERANTE.equals( bomLinha.getStatus() ) )
			{
				bomSecao.setStatus( Status.INOPERANTE );
			}
			else
			{
				if ( isTudoPreenchido( bomSecao ) )
				{
					bomSecao.setStatus( Status.PREENCHIDO );
				}
				else
				{
					bomSecao.setStatus( Status.ABERTO );
				}
			}
			this.bomDAO.update( bomSecao );
		}
	}

	/**
	 * Tudo preenchido.
	 *
	 * @param bom
	 *            the bom
	 * @throws BusinessException
	 *             the business exception
	 */
	private void tudoPreenchido( final Bom bom )
		throws BusinessException
	{
		if ( isTudoPreenchido( bom ) )
		{
			bom.setStatus( Status.PREENCHIDO );
		}
		else
		{
			bom.setStatus( Status.ABERTO );
		}
		try
		{
			this.bomDAO.update( bom );
		}
		catch ( final DaoException e )
		{
			throw new BusinessException( e );
		}
	}

	/**
	 * Tudo preenchido.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @throws BusinessException
	 *             the business exception
	 */
	private void tudoPreenchido( final BomLinha bomLinha )
		throws BusinessException
	{

		if ( !Status.INOPERANTE.equals( bomLinha.getStatus() ) )
		{

			if ( isTudoPreenchido( bomLinha ) )
			{
				boolean secoesPreenchidas = Boolean.TRUE;
				for ( final BomSecao bomSecao : bomLinha.getSecoes() )
				{
					if ( Status.ABERTO.equals( bomSecao.getStatus() ) )
					{
						secoesPreenchidas = Boolean.FALSE;
						break;
					}
				}
				if ( secoesPreenchidas )
				{
					bomLinha.setStatus( Status.PREENCHIDO );
				}
				else
				{
					bomLinha.setStatus( Status.ABERTO );
				}
			}
			else
			{
				if ( !Status.INOPERANTE.equals( bomLinha.getStatus() ) )
				{
					bomLinha.setStatus( Status.ABERTO );
				}
			}

		}

		this.bomDAO.update( bomLinha );

		tudoPreenchido( bomLinha.getBom() );

	}

	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @throws BusinessException
	 *             the business exception
	 */
	public void update( final BomLinha entity )
		throws BusinessException
	{

		// Marca os inoperantes
		if ( Check.isNotNull( entity.getSecoes() ) )
		{
			for ( final BomSecao bomSecao : entity.getSecoes() )
			{
				final BomSecao bomSecaoPersistida = buscarBomSecao( bomSecao.getId() );
				if ( bomSecao.isInoperante() )
				{
					bomSecaoPersistida.setStatus( Status.INOPERANTE );
				}
				else
				{
					if ( isTudoPreenchido( bomSecaoPersistida ) )
					{
						bomSecaoPersistida.setStatus( Status.PREENCHIDO );
					}
					else
					{
						bomSecaoPersistida.setStatus( Status.ABERTO );
					}
				}
				this.bomDAO.update( bomSecaoPersistida );
			}
		}

		final BomLinha bomLinha = buscarBomLinha( entity.getId() );
		bomLinha.setFrota( entity.getFrota() );
		bomLinha.setCapacidade( entity.getCapacidade() );
		bomLinha.setViagensOrdinariasAB( entity.getViagensOrdinariasAB() );
		bomLinha.setViagensExtraordinariasAB( Check.isNull( entity.getViagensExtraordinariasAB() ) ? 0 : entity
			.getViagensExtraordinariasAB() );
		bomLinha.setViagensOrdinariasBA( entity.getViagensOrdinariasBA() );
		bomLinha.setViagensExtraordinariasBA( Check.isNull( entity.getViagensExtraordinariasBA() ) ? 0 : entity
			.getViagensExtraordinariasBA() );

		atualizarDadosPicoLinha( entity, bomLinha );

		this.bomDAO.update( bomLinha );

		tudoPreenchido( bomLinha );

	}

	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @throws BusinessException
	 *             the business exception
	 * @throws BusinessException
	 *             the business exception
	 */
	public void update( final BomSecao entity )
		throws BusinessException,
			BusinessException
	{

		beforeUpdate( entity );

		final BomSecao bomSecao = buscarBomSecao( entity.getId() );

		final BigDecimal tarifaAtual = bomSecao.getTarifaAtual();
		final BigDecimal tarifaAnterior = bomSecao.getTarifaAnterior();
		
		// Fix de ajuste de tarifa do Bom
		// https://trello.com/c/KnxuEvaS
		Secao secao = bomSecao.getSecao();
		String mesReferencia = bomSecao.getBomLinha().getBom().getMesReferencia();
		final Tarifa tarifaRealAnterior = this.tarifaBusiness.buscaPrimeiraTarifaMes(secao, mesReferencia);
		BigDecimal valorTarifaRealAnterior =  (BigDecimal) (tarifaRealAnterior != null ? tarifaRealAnterior.getValor() : 0);
				
		if (tarifaRealAnterior!=null) {
			valorTarifaRealAnterior = tarifaRealAnterior.getValor();
		}

		if ( entity.getTarifa().compareTo( tarifaAtual ) > 0 )
		{
			throw new BusinessException( "Valor informado para tarifa está acima do valor autorizado." );
		}
		bomSecao.setTarifa( entity.getTarifa() );

		if ( entity.getTarifaAnterior() != null )
		{
			if ( entity.getTarifaAnterior().compareTo( tarifaAnterior ) > 0  )
			{
				//Fix de ajuste de tarifa do Bom. A tarifa do BOM não pode ser maior que a tarifa do sistema
				// https://trello.com/c/KnxuEvaS
				if ( entity.getTarifaAnterior().compareTo( valorTarifaRealAnterior ) > 0) {
					throw new BusinessException( "Valor informado para tarifa anterior está acima do valor autorizado." );
				}
			}
			bomSecao.setTarifaAnterior( entity.getTarifaAnterior() );
		}

		bomSecao.setPassageiroAnteriorAB( entity.getPassageiroAnteriorAB() );

		bomSecao.setPassageiroAB( entity.getPassageiroAB() );

		bomSecao.setPassageiroBA( entity.getPassageiroBA() );
		bomSecao.setPassageiroAnteriorBA( entity.getPassageiroAnteriorBA() );

		bomSecao.calculaTotalPassageirosAB();
		bomSecao.calculaTotalPassageirosBA();

		bomSecao.calculaTotalReceita();

		if ( entity.isInoperante() )
		{
			bomSecao.setStatus( Status.INOPERANTE );
		}
		else
		{
			bomSecao.setStatus( Status.PREENCHIDO );
		}

		this.bomDAO.update( bomSecao );

		tudoPreenchido( bomSecao.getBomLinha() );

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param perfil
	 * @return
	 */
	public boolean usuarioLogadoNaoEEmpresa()
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		return !perfil.isEmpresa();
	}

	/**
	 * Validate exportacao bom.
	 *
	 * @param bom
	 *            the bom
	 * @throws BusinessException
	 *             the business exception
	 */
	public void validateExportacaoBOM( final Bom bom )
		throws BusinessException
	{

		final Usuario usuario = ( Usuario ) getUser();

		if ( usuarioLogadoNaoEEmpresa() || !usuario.getEmpresa().equals( bom.getEmpresa() ) )
		{
			throw new BusinessException( "O seu usuário não tem permissão para exportar o BOM solicitado." );
		}
	}

	/**
	 * Validate fechar.
	 *
	 * @param bom
	 *            the bom
	 * @throws ValidationException
	 *             the validation exception
	 * @throws BusinessException
	 *             the business exception
	 */
	private void validateFechar( final Bom bom )
		throws ValidationException,
			BusinessException
	{
		final List<String> validationErrors = new ArrayList<String>();

		// verificaDataLimiteFechamentoBom(bom);

		verificaOperacaoLinha( bom, validationErrors );

		if ( !isTudoPreenchido( bom ) )
		{
			validationErrors.add( "Você está tentando fechar um BOM com Linha(s) e/ou Seção(ões) não preenchidas." );
		}

		if ( !validationErrors.isEmpty() )
		{
			throw new ValidationException( validationErrors );
		}
	}

	/**
	 * Validate generate.
	 *
	 * @param bom
	 *            the bom
	 * @throws ValidationException
	 *             the validation exception
	 * @throws BomNotAuthorizedException
	 */
	private void validateGenerate( final Bom bom )
		throws ValidationException
	{

		final List<String> validationErrors = new ArrayList<String>();

		// Mes Futuro
		final String mesReferencia = bom.getMesReferencia();
		final String[] splitMesAno = mesReferencia.split( "/" );
		final int mesBom = Integer.valueOf( splitMesAno[0] ) - 1;
		final int anoBom = Integer.valueOf( splitMesAno[1] );

		final DateBuilder dataBom = date().withMonth( mesBom ).withYear( anoBom ).firstDayOfMonth();

		if ( dataBom.isFutureDate() || dataBom.isCurrentMonth() )
		{
			validationErrors.add( "Só é possível gerar o BOM para meses passados." );
		}

		// verificaDataLimiteFechamentoBom(bom);

		// Bom Ja Existe

		final Bom bomJaExistente = this.bomDAO.pesquisarPor( bom.getEmpresa(), mesReferencia );

		if ( Check.isNotNull( bomJaExistente ) )
		{
			final String errorMessage = "Já existe um BOM para a Empresa "
				+ bomJaExistente.getEmpresa().getNome()
				+ " no mês de referência "
				+ bomJaExistente.getMesReferencia()
				+ ".";
			validationErrors.add( errorMessage );
		}

		final List<LinhaVigencia> linhasEmpresa = this.linhaDAO.buscarLinhasVigentesDaEmpresaNoMesAno(
			bom.getEmpresa(),
			mesReferencia );

		// Sem linhas

		if ( Check.isEmpty( linhasEmpresa ) )
		{
			validationErrors.add( "Não foi encontrada nenhuma linha vigente neste mês/ano de referência." );
		}

		// Linhas s/ Tipo de Veiculo
		for ( final LinhaVigencia linhaVigencia : linhasEmpresa )
		{
			if ( linhaVigencia.getTipoDeVeiculosUtilizados().isEmpty() )
			{
				validationErrors.add( "Existe(m) linha(s) sem tipo(s) de veículo cadastrado(s). Linha: "
					+ linhaVigencia.getCodigo() );
				break;
			}
		}

		// Linha sem tarifa
		for ( final LinhaVigencia linhaVigencia : linhasEmpresa )
		{
			for ( final Secao secao : linhaVigencia.getSecoes() )
			{

				// FBW-137 | pega as seções com base em sua data de vigor., no caso, passar as
				// linhas
				// vigentes que foram criadas até a data igual mesAno
				final Calendar dataReferencia = Calendar.getInstance();
				dataReferencia.set( anoBom, mesBom, 1 );
				final int maxDayOfMonth = dataReferencia.getActualMaximum( Calendar.DAY_OF_MONTH );
				dataReferencia.set( Calendar.DAY_OF_MONTH, maxDayOfMonth );
				final Date temp = new Date( dataReferencia.getTimeInMillis() );
				if ( secao.getDataCriacao().compareTo( temp ) < 0 )
				{
					// FBW-36
					final Tarifa tarifaAtual = this.tarifaBusiness.buscaTarifaMes( secao, mesReferencia );
					if ( Check.isNull( tarifaAtual ) )
					{
						validationErrors.add( "Não existe tarifa vigente cadastrada para: "
							+ secao.getLinhaVigencia().getItinerarioIda()
							+ " / Seção: "
							+ secao.getJuncao() );
					}
				}
			}
		}

		if ( !validationErrors.isEmpty() )
		{
			throw new ValidationException( validationErrors );
		}
	}

	/**
	 * Validate import bom linha.
	 *
	 * @param linha
	 *            the linha
	 * @param validationsErrors
	 *            the validations errors
	 * @param indLinha
	 *            the ind linha
	 */
	private void validateImportBomLinha(
		final List<String> linha,
		final List<String> validationsErrors,
		final int indLinha )
	{
		for ( int j = IMPORTACAO_CELULA_FROTA; j < IMPORTACAO_CELULA_VIAGEM_EXTRAORD_BA; j++ )
		{
			try
			{
				Integer.parseInt( linha.get( j - 1 ) );
			}
			catch ( final NumberFormatException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
				validationsErrors.add( "Número inteiro inválido na coluna "
					+ j
					+ " ("
					+ linha.get( j - 1 )
					+ ") da linha "
					+ ( indLinha + 1 )
					+ "." );
			}
		}
	}

	/**
	 * Validate import bom secao.
	 *
	 * @param linha
	 *            the linha
	 * @param validationsErrors
	 *            the validations errors
	 * @param i
	 *            the i
	 */
	private void validateImportBomSecao( final List<String> linha, final List<String> validationsErrors, final int i )
	{
		if ( !"S".equals( linha.get( IMPORTACAO_CELULA_INOPERANTE - 1 ) )
			&& !"N".equals( linha.get( IMPORTACAO_CELULA_INOPERANTE - 1 ) ) )
		{
			validationsErrors.add( "Valor inválido na coluna 'Sem Passageiros' ("
				+ linha.get( IMPORTACAO_CELULA_INOPERANTE - 1 )
				+ ") da linha "
				+ ( i + 1 )
				+ ". Preencha com 'S' ou 'N'." );
		}

		for ( int j = IMPORTACAO_CELULA_QTD_DE_PASS_AB; j < IMPORTACAO_CELULA_QTD_DE_PASS_ANT_BA; j++ )
		{
			try
			{
				Integer.parseInt( linha.get( j - 1 ) );
			}
			catch ( final NumberFormatException e )
			{
				if ( LOG.isDebugEnabled() )
				{
					LOG.debug( e, e );
				}// if
				validationsErrors.add( "Número inteiro inválido na coluna "
					+ j
					+ " ("
					+ linha.get( j - 1 )
					+ ") da linha "
					+ ( i + 1 )
					+ "." );
			}
			catch ( final IndexOutOfBoundsException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if

			}
		}
	}

	/**
	 * Validate import excel.
	 *
	 * @param listImportDTOs
	 *            the list import dt os
	 * @param bom
	 *            the bom
	 * @throws ValidationException
	 *             the validation exception
	 */
	private void validateImportExcel( final List<ImportDTO> listImportDTOs, final Bom bom )
		throws ValidationException
	{

		int i = IMPORTACAO_PRIMEIRA_LINHA_DADOS;
		List<String> linha = listImportDTOs.get( i++ ).getValues();
		String id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
		BomLinha bomLinha = null;
		boolean validarBomLinha = false;

		final List<String> validationsErrors = new ArrayList<String>();
		final List<Long> idsBomSecaoImportados = new ArrayList<Long>();
		while ( ( id != null ) && !"".equals( id ) )
		{
			if ( ( bomLinha == null ) || !new Long( Long.parseLong( id ) ).equals( bomLinha.getId() ) )
			{
				validarBomLinha = true;
			}
			else
			{
				validarBomLinha = false;
			}

			bomLinha = this.bomDAO.buscarBomLinha( Long.parseLong( id ) );
			final BomSecao bomSecao = this.bomDAO.buscarBomSecao( Long.parseLong( linha
				.get( IMPORTACAO_CELULA_ID_BOM_SECAO - 1 ) ) );

			if ( ( bomLinha == null ) || !bomLinha.getBom().getId().equals( bom.getId() ) )
			{
				validationsErrors.add( "Erro! Linha "
					+ linha.get( IMPORTACAO_CELULA_NOME_LINHA - 1 )
					+ " inexistente, ou não pertencente ao Bom(Mês/Ano) que está sendo atualizado." );
				linha = listImportDTOs.get( i++ ).getValues();
				id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
				continue;
			}
			if ( ( bomSecao == null ) || !bomSecao.getBomLinha().getBom().getId().equals( bom.getId() ) )
			{
				validationsErrors.add( "Erro! Seção "
					+ linha.get( IMPORTACAO_CELULA_NOME_SECAO - 1 )
					+ " inexistente, ou não pertencente ao Bom(Mês/Ano) que está sendo atualizado." );
				linha = listImportDTOs.get( i++ ).getValues();
				id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
				continue;
			}

			idsBomSecaoImportados.add( bomSecao.getId() );
			if ( validarBomLinha )
			{
				validateImportBomLinha( linha, validationsErrors, i );
			}

			validateImportTarifa( linha, bomSecao, validationsErrors, i );

			validateImportBomSecao( linha, validationsErrors, i );

			linha = listImportDTOs.get( i++ ).getValues();
			id = linha.size() > 0 ? linha.get( IMPORTACAO_CELULA_ID_BOM_LINHA - 1 ) : null;
		}

		if ( validationsErrors.isEmpty() )
		{
			validateImportTodasSecoes( bom, idsBomSecaoImportados, validationsErrors );
		}

		if ( !validationsErrors.isEmpty() )
		{
			throw new ValidationException( validationsErrors );
		}

	}

	/**
	 * Validate import tarifa.
	 *
	 * @param linha
	 *            the linha
	 * @param bomSecao
	 *            the bom secao
	 * @param validationsErrors
	 *            the validations errors
	 * @param indLinha
	 *            the ind linha
	 */
	private void validateImportTarifa(
		final List<String> linha,
		final BomSecao bomSecao,
		final List<String> validationsErrors,
		final int indLinha )
	{
		try
		{
			final Double tarifa = Double.parseDouble( linha.get( IMPORTACAO_CELULA_TARIFA - 1 ).replace( ",", "." ) );
			if ( bomSecao != null )
			{
				final Double tarifaAtual = bomSecao.getTarifaAtual().doubleValue();

				if ( tarifa.compareTo( tarifaAtual ) > 0 )
				{
					validationsErrors.add( "Valor informado para tarifa da seção: "
						+ bomSecao.getSecao().getJuncao()
						+ " está acima do valor autorizado." );
				}
			}
		}
		catch ( final NumberFormatException e )
		{
			if ( LOG.isDebugEnabled() )
			{
				LOG.debug( e, e );
			}// if
			validationsErrors.add( "Valor inválido de tarifa informado na coluna "
				+ ( IMPORTACAO_CELULA_TARIFA )
				+ " ("
				+ linha.get( IMPORTACAO_CELULA_TARIFA - 1 )
				+ ") da linha "
				+ ( indLinha + 1 )
				+ "." );
		}

		try
		{
			final Double tarifaAnterior = Double.parseDouble( linha
				.get( IMPORTACAO_CELULA_TARIFA_ANTERIOR - 1 )
				.replace( ",", "." ) );
			if ( bomSecao != null )
			{
				final Double tarifaAtual = bomSecao.getTarifaAtual().doubleValue();

				if ( tarifaAnterior.compareTo( tarifaAtual ) > 0 )
				{
					validationsErrors.add( "Valor informado para tarifa anterior da seção: "
						+ bomSecao.getSecao().getJuncao()
						+ " está acima do valor autorizado." );
				}
			}
		}
		catch ( final NumberFormatException e )
		{
			if ( LOG.isDebugEnabled() )
			{
				LOG.debug( e, e );
			}// if
			validationsErrors.add( "Valor inválido de tarifa anterior informado na coluna "
				+ ( IMPORTACAO_CELULA_TARIFA )
				+ " ("
				+ linha.get( IMPORTACAO_CELULA_TARIFA_ANTERIOR - 1 )
				+ ") da linha "
				+ ( indLinha + 1 )
				+ "." );
		}
	}

	/**
	 * Validate import todas secoes.
	 *
	 * @param bom
	 *            the bom
	 * @param idsBomSecaoImportados
	 *            the ids bom secao importados
	 * @param validationsErrors
	 *            the validations errors
	 */
	private void validateImportTodasSecoes(
		final Bom bom,
		final List<Long> idsBomSecaoImportados,
		final List<String> validationsErrors )
	{
		final Status[] status = {	Status.ABERTO,
									Status.PREENCHIDO};
		final List<BomSecao> bomSecoes = this.bomDAO.getBomSecoesForaDaLista( bom, idsBomSecaoImportados, status );

		if ( bomSecoes.size() > 0 )
		{
			for ( final BomSecao bomSecao : bomSecoes )
			{
				validationsErrors.add( "Planilha de importação do BOM incompleta. Preencha a seção "
					+ bomSecao.getSecao().getJuncao() );
			}
		}
	}

	/**
	 * Validate justificativa.
	 *
	 * @param bomLinhas
	 *            the bom linhas
	 * @throws BusinessException
	 *             the business exception
	 */
	public void validateJustificativa( final List<BomLinha> bomLinhas )
		throws BusinessException
	{
		if ( Check.isNotNull( bomLinhas ) )
		{
			for ( final BomLinha bomLinha : bomLinhas )
			{
				if ( Check.isNotNull( bomLinha.getId() ) )
				{
					if ( Check.isNull( bomLinha.getUltimaJustificativa() )
						|| Check.isBlank( bomLinha.getUltimaJustificativa() ) )
					{
						throw new BusinessException( "Para reabrir o BOM é necessário fornecer uma justificativa." );
					}
				}
			}
		}
	}

	/**
	 * Validate reabertura.
	 *
	 * @param bomLinhas
	 *            the bom linhas
	 * @throws BusinessException
	 *             the business exception
	 */
	public void validateReabertura( final List<BomLinha> bomLinhas )
		throws BusinessException
	{
		int linhasSelecionadas = 0;
		if ( Check.isNotNull( bomLinhas ) )
		{
			for ( final BomLinha bomLinha : bomLinhas )
			{
				if ( Check.isNotNull( bomLinha.getId() ) )
				{
					linhasSelecionadas++;
				}
			}
		}
		if ( linhasSelecionadas == 0 )
		{
			throw new BusinessException( "Por favor selecione ao menos uma linha para reabrir." );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bomLinhas
	 * @throws BusinessException
	 */
	public void validateReaberturaEmpresa( final List<BomLinha> bomLinhas )
		throws BusinessException
	{
		int linhasSelecionadas = 0;
		if ( Check.isNotNull( bomLinhas ) )
		{
			for ( final BomLinha bomLinha : bomLinhas )
			{
				if ( Check.isNotNull( bomLinha.getId() ) )
				{
					linhasSelecionadas++;
				}
			}
		}
		if ( linhasSelecionadas == 0 )
		{
			throw new BusinessException( "Não foram encontradas Linhas para esse BOM." );
		}
	}

	/**
	 * Validate recriar.
	 *
	 * @param linhas
	 *            the linhas
	 * @throws BusinessException
	 *             the business exception
	 */
	public void validateRecriar( final List<LinhaVigencia> linhas )
		throws BusinessException
	{
		int linhasSelecionadas = 0;
		if ( Check.isNotNull( linhas ) )
		{
			for ( final LinhaVigencia linha : linhas )
			{
				if ( Check.isNotNull( linha.getId() ) )
				{
					linhasSelecionadas++;
				}
			}
		}
		if ( linhasSelecionadas == 0 )
		{
			throw new BusinessException( "Por favor selecione ao menos uma linha para reabrir." );
		}
	}

	/**
	 * Verifica operacao linha.
	 *
	 * @param bom
	 *            the bom
	 * @param validationErrors
	 *            the validation errors
	 */
	private void verificaOperacaoLinha( final Bom bom, final List<String> validationErrors )
	{

		final List<BomLinha> linhasBom = this.bomDAO.pesquisarLinhas( bom );
		for ( final BomLinha bomLinha : linhasBom )
		{
			if ( !Status.INOPERANTE.equals( bomLinha.getStatus() ) )
			{
				return;
			}
		}

		validationErrors.add( "Ao menos uma linha deve estar operando." );

	}

	/**
	 * The bom dao.
	 */
	private final BomDAO bomDAO;

	/**
	 * The configuracao business.
	 */
	private final ConfiguracaoBusiness configuracaoBusiness;

	/**
	 * The empresa dao.
	 */
	private final EmpresaDAO empresaDAO;

	/**
	 * The linha dao.
	 */
	private final LinhaDAO linhaDAO;

	/**
	 * The tarifa business.
	 */
	private final TarifaBusiness tarifaBusiness;

	/**
	 * The usuario perfil dao.
	 */
	private final UsuarioPerfilDAO usuarioPerfilDAO;

	/**
	 * <p>
	 * Field <code>bomReaberturaDAO</code>
	 * </p>
	 */
	private final BomReaberturaDAO bomReaberturaDAO;
}

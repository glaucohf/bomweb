package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.excel.DynamicExcel;
import br.com.decatron.framework.excel.Row;
import br.com.decatron.framework.excel.Sheet;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.exception.MailException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.dao.BomDAO;
import br.com.fetranspor.bomweb.dao.BomReaberturaDAO;
import br.com.fetranspor.bomweb.dao.EmpresaDAO;
import br.com.fetranspor.bomweb.dao.LinhaDAO;
import br.com.fetranspor.bomweb.dao.TarifaDAO;
import br.com.fetranspor.bomweb.dto.FiltroTarifaDTO;
import br.com.fetranspor.bomweb.dto.ImportDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomReabertura;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Justificativa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.MotivoCriacaoTarifa;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.util.DateProvider;
import br.com.fetranspor.bomweb.util.UtilitiesMail;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * The Class TarifaBusiness.
 */
@Component
public class TarifaBusiness
	extends VRaptorBusiness<Tarifa>
{

	/**
	 * The Constant INDICE_EXCEL_CODIGO_LINHA.
	 */
	public static final int INDICE_EXCEL_CODIGO_LINHA = 1;

	/**
	 * The Constant INDICE_EXCEL_CODIGO_SECAO.
	 */
	public static final int INDICE_EXCEL_CODIGO_SECAO = 4;

	/**
	 * The Constant INDICE_EXCEL_EMPRESA.
	 */
	public static final int INDICE_EXCEL_EMPRESA = 2;

	/**
	 * The Constant INDICE_EXCEL_FIM_VIGENCIA.
	 */
	public static final int INDICE_EXCEL_FIM_VIGENCIA = 8;

	/**
	 * The Constant INDICE_EXCEL_ID.
	 */
	public static final int INDICE_EXCEL_ID = 0;

	/**
	 * The Constant INDICE_EXCEL_INICIO_VIGENCIA.
	 */
	public static final int INDICE_EXCEL_INICIO_VIGENCIA = 7;

	/**
	 * The Constant INDICE_EXCEL_LINHA.
	 */
	public static final int INDICE_EXCEL_LINHA = 3;

	/**
	 * The Constant INDICE_EXCEL_SECAO.
	 */
	public static final int INDICE_EXCEL_SECAO = 5;

	/**
	 * The Constant INDICE_EXCEL_VALOR.
	 */
	public static final int INDICE_EXCEL_VALOR = 6;

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( TarifaBusiness.class );

	/**
	 * Instantiates a new tarifa business.
	 *
	 * @param provider
	 *            the provider
	 * @param tarifaDAO
	 *            the dao
	 * @param bomDAO
	 *            the bom dao
	 * @param linhaDAO
	 * @param empresaDAO
	 * @param configuracaoBusiness
	 *            the configuracao business
	 * @param linhaBusiness
	 *            the linha business
	 */
	public TarifaBusiness(
		final VRaptorProvider provider,
		final TarifaDAO tarifaDAO,
		final BomDAO bomDAO,
		final BomReaberturaDAO bomReaberturaDAO,
		final LinhaDAO linhaDAO,
		final EmpresaDAO empresaDAO,
		final ConfiguracaoBusiness configuracaoBusiness,
		final LinhaBusiness linhaBusiness )
	{
		super( provider );
		this.dao = tarifaDAO;
		this.tarifaDAO = tarifaDAO;
		this.bomDAO = bomDAO;
		this.bomReaberturaDAO = bomReaberturaDAO;
		this.linhaDAO = linhaDAO;
		this.empresaDAO = empresaDAO;
		this.linhaBusiness = linhaBusiness;
		this.configuracaoBusiness = configuracaoBusiness;
	}

	/**
	 * <p>
	 * Remove dias de uma data
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param date
	 *            the date
	 * @param dias
	 *            the dias
	 * @return the date
	 */
	private static Date removeDias( final Date date, final Integer dias )
	{
		final GregorianCalendar gc = new GregorianCalendar();
		gc.setTime( date );
		gc.set( Calendar.DATE, gc.get( Calendar.DATE ) - dias );
		return gc.getTime();
	}

	/**
	 * <p>
	 * Exclusão de BOMs afetados de acordo com a vigência da tarifa
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param codLinha
	 *            the cod linha
	 * @param mesAnoInicioVigencia
	 *            the mes ano inicio vigencia
	 * @throws BusinessException
	 *             the business exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public void apagaBom( final String codLinha, final String mesAnoInicioVigencia )
		throws BusinessException,
			ParseException
	{
		// pegar os idBom que tenham data >= dataInicioVigencia e idEmpresa (pegar o idEmpresa pelo
		// codLinha)
		// pegar os os bom_linha_id pelo idBom
		// apagar bom_secao de todoas as id_bom_linha
		// apagar bom_linha de todas as idBom
		// apagar bom de todas as idBom
		final List<Bom> bomIdList = this.tarifaDAO.buscarIdBom( codLinha, mesAnoInicioVigencia );
		String emailDetro = "";
		String emailEmpresa = "";
		String nomeEmpresa = "";
		String mesesRefe = "";

		for ( final Bom bomId : bomIdList )
		{
			final List<BomLinha> bomIdLinhaList = this.tarifaDAO.buscarIdBomLinha( bomId );

			for ( final BomLinha bomIdLinha : bomIdLinhaList )
			{
				// deleta justificativa
				final List<Justificativa> justificativas = this.bomDAO.buscarJustificativasPorBomLinha( bomIdLinha );
				for ( final Justificativa justificativa : justificativas )
				{
					this.bomDAO.delete( justificativa );
				}

				// delta os bom secao
				this.tarifaDAO.deletaBomSecao( bomIdLinha );
				// delta os bom linha
				this.tarifaDAO.deleteBomLinha( bomIdLinha );
			}
			final List<BomReabertura> listaBomReabertura = this.bomReaberturaDAO.buscaBomReaberturaByBom( bomId );
			this.bomReaberturaDAO.deleteListaBomReabertura( listaBomReabertura );
			// deleta os bom
			this.tarifaDAO.deleteBom( bomId );
			emailDetro = this.configuracaoBusiness.buscarEmailDetro();
			emailEmpresa = bomId.getEmpresa().getEmailContato();
			nomeEmpresa = bomId.getEmpresa().getNome();
			mesesRefe += bomId.getMesReferencia() + "\r\n";
		}

		// As partes envolvidas (empresa e Detro) devem receber um email informando que os BOMs
		// dos meses em questão foram excluídos
		if ( bomIdList.size() > 0 )
		{
			try
			{

				final String from = emailDetro;
				final String to = emailEmpresa + "," + from;
				final String subject = "BOMWEB: Exclusão de BOMs";
				final String body = "Foram excluídos os BOMs da empresa "
					+ nomeEmpresa
					+ " , referentes aos meses:\r\n"
					+ mesesRefe;

				UtilitiesMail.sendMail( from, to, subject, body );
			}
			catch ( final MailException e )
			{
				throw new BusinessException(
					"Ocorreu um erro ao informar ao usuário que o BOM foi excluído. Nada foi alterado ou excluído!",
					e );
			}
		}

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param empresa
	 * @param mesAnoInicioVigencia
	 * @param codigoLinhaVigencia
	 * @param secao
	 * @param justificativaEdicao
	 * @throws BusinessException
	 * @throws ParseException
	 */
	private void apagaBomEdicaoRetroativa(
		final Empresa empresa,
		final String mesAnoInicioVigencia,
		final String codigoLinhaVigencia,
		final String secao,
		final String justificativaEdicao )
		throws BusinessException,
			ParseException
	{
		// pegar os idBom que tenham data >= dataInicioVigencia e idEmpresa (pegar o idEmpresa pelo
		// codLinha)
		// pegar os os bom_linha_id pelo idBom
		// apagar bom_secao de todoas as id_bom_linha
		// apagar bom_linha de todas as idBom
		// apagar bom de todas as idBom
		final TarifaDAO tarifaDAO = getTarifaDAO();
		final List<Bom> bomIdList = tarifaDAO.buscarIdBomPorIdEmpresa( empresa, mesAnoInicioVigencia );

		// FBW-505
		final String bomsQueSeraoApagados = getMensagemComBomsQueSeraoApagadosPrivate( empresa, mesAnoInicioVigencia );

		for ( final Bom bomId : bomIdList )
		{
			final List<BomLinha> bomIdLinhaList = tarifaDAO.buscarIdBomLinha( bomId );

			for ( final BomLinha bomIdLinha : bomIdLinhaList )
			{
				// deleta justificativa
				final List<Justificativa> justificativas = this.bomDAO.buscarJustificativasPorBomLinha( bomIdLinha );
				if ( justificativas.size() > 0 )
				{
					for ( final Justificativa justificativa : justificativas )
					{
						this.bomDAO.delete( justificativa );
					}
				}

				// deleta os bom secao
				tarifaDAO.deletaBomSecao( bomIdLinha );
				// deleta os bom linha
				tarifaDAO.deleteBomLinha( bomIdLinha );

			}
			final List<BomReabertura> listaBomReabertura = this.bomReaberturaDAO.buscaBomReaberturaByBom( bomId );
			this.bomReaberturaDAO.deleteListaBomReabertura( listaBomReabertura );
			// deleta os bom
			tarifaDAO.deleteBom( bomId );

		}
		final Usuario usuarioLogado = ( Usuario ) getUser();
		final ConfiguracaoBusiness configuracaoBusiness = getConfiguracaoBusiness();
		final String emailDetro = configuracaoBusiness.buscarEmailDetro();
		final String from = emailDetro;
		final String emailUsuarioLogado = usuarioLogado.getEmail();
		final String emailEmpresa = empresa.getEmailContato();
		final String nomeEmpresa = empresa.getNome();

		final String hoje = new SimpleDateFormat( "dd/MM/yyyy hh:mm:ss" ).format( new Date() );
		final String to = emailEmpresa + "," + emailUsuarioLogado;

		try
		{
			final String subject = "BOMWEB: Edição retroativa de tarifa";
			final String body = "Aviso: Devido a edição retroativa de tarifa da linha "
				+ codigoLinhaVigencia
				+ " Seção: "
				+ secao
				+ " da empresa "
				+ nomeEmpresa
				+ ", os seguintes BOMs foram excluídos:\n"
				+ bomsQueSeraoApagados
				+ "Justificativa: "
				+ justificativaEdicao
				+ "\nData da edição: "
				+ hoje;

			UtilitiesMail.sendMail( from, to, subject, body );
		}
		catch ( final MailException e )
		{
			throw new BusinessException( "Ocorreu um erro ao informar ao usuário os dados da edição retroativa. ", e );
		}

	}

	/**
	 * Atualiza fim vigencia.
	 *
	 * @param entity
	 *            the entity
	 * @param novaTarifa
	 *            the nova tarifa
	 * @throws BusinessException
	 *             the business exception
	 */
	public void atualizaFimVigencia( final Tarifa entity, final Tarifa novaTarifa )
		throws BusinessException
	{
		final Calendar finalVigencia = new GregorianCalendar();
		finalVigencia.setTime( novaTarifa.getInicioVigencia() );
		finalVigencia.add( Calendar.DAY_OF_MONTH, -1 );
		entity.setFimVigencia( finalVigencia.getTime() );
		try
		{
			this.tarifaDAO.update( entity );
		}
		catch ( final DaoException e )
		{
			throw new BusinessException( e );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param tarifaFromForm
	 * @param justificativa
	 *            FBW-487
	 * @throws DaoException
	 */
	private void atualizarRetroativamente( final Tarifa tarifaFromForm, final String justificativa )
		throws DaoException
	{
		final Date inicioVigencia = tarifaFromForm.getInicioVigencia();
		final Secao secao = tarifaFromForm.getSecao();
		final TarifaDAO tarifaDAO = getTarifaDAO();
		final Tarifa tarifaAtivaEm = tarifaDAO.buscaTarifaAtivaEm( inicioVigencia, secao );
		if ( Check.isNull( tarifaAtivaEm ) )
		{
			final List<Tarifa> todasAsTarifas = tarifaDAO.buscaTodasAsTarifas( secao );
			for ( final Tarifa tarifa : todasAsTarifas )
			{
				tarifaDAO.deleteFisicamente( tarifa );
			}// for
		}// if
		else if ( tarifaAtivaEm.getInicioVigencia().compareTo( inicioVigencia ) == 0 )
		{
			tarifaDAO.deleteFisicamente( tarifaAtivaEm );
		}// else if
		else
		{
			final Date dataSubtraida = DateProvider.subtrairDia( inicioVigencia, 1 );
			tarifaAtivaEm.setFimVigencia( dataSubtraida );

			// FBW-487
			logarEdicaoRetroativa( tarifaAtivaEm, justificativa );
		}// else
		final Tarifa novaTarifa = geraNovaTarifaParaAtualizacaoTarifaria( tarifaFromForm );

		// FBW-487
		logarEdicaoRetroativa( novaTarifa, justificativa );

	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#beforeSave(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void beforeSave( final Tarifa entity )
		throws BusinessException
	{
		super.beforeSave( entity );
		validacoesComunsBeforeSaveEUpdate( entity );

		if ( jaExisteTarifa( entity.getSecao() ) )
		{
			throw new BusinessException( "Já existe tarifa cadastrada para esta Linha/Seção." );
		}

		if ( isInicioVigenciaTarifaAnteriorHoje( entity ) )
		{
			throw new BusinessException( "A Data de Vigência deve ser posterior ao dia atual." );
		}

	}

	/**
	 * Busca linhas auto complete.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasAutoComplete( final String term, final String idEmpresa )
	{
		return this.linhaBusiness.buscaLinhasAutoComplete( term, idEmpresa );
	}

	/**
	 * Busca linhas vigencia.
	 *
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasVigencia( final String idEmpresa )
	{
		return this.linhaBusiness.buscaLinhasVigentesOuFuturas( idEmpresa );
	}

	/**
	 * Busca linhas vigencia sem tarifa cadastrada.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasVigenciaSemTarifaCadastrada( final String term, final String idEmpresa )
	{
		final List<LinhaVigencia> linhas = this.linhaBusiness.buscaLinhasSemTarifaAutoComplete( term, idEmpresa );
		return linhas;
	}

	/**
	 * Buscar tarifas ainda nao vigentes.
	 *
	 * @param tarifa
	 *            the tarifa
	 * @return the list
	 */
	public List<Tarifa> buscarTarifasAindaNaoVigentes( final Tarifa tarifa )
	{
		return this.tarifaDAO.buscarTarifasAindaNaoVigentes( tarifa );
	}

	/**
	 * <p>
	 * Retorna todas as tarifas da secao de uma empresa ativa a partir de uma data de vigencia
	 * inicial
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param tarifa
	 *            the tarifa
	 * @param dataIniVig
	 *            the data ini vig
	 * @return List<Tarifa>
	 */
	public List<Tarifa> buscarTarifasPorDataVigentes( final Tarifa tarifa, final Date dataIniVig )
	{
		return this.tarifaDAO.buscarTarifasPorDataVigentes( tarifa, dataIniVig );
	}

	/**
	 * Busca tarifa anterior.
	 *
	 * @param secao
	 *            the secao
	 * @return the tarifa
	 */
	public Tarifa buscaTarifaAnterior( final Secao secao )
	{
		return this.tarifaDAO.buscaTarifaAnterior( secao );
	}

	/**
	 * Busca tarifa atual.
	 *
	 * @param secao
	 *            the secao
	 * @return the tarifa
	 */
	public Tarifa buscaTarifaAtual( final Secao secao )
	{
		return this.tarifaDAO.buscaTarifaAtual( secao );
	}

	// FBW-36
	/**
	 * Busca tarifa mes.
	 *
	 * @param secao
	 *            the secao
	 * @param mesAno
	 *            the mes ano
	 * @return the tarifa
	 */
	public Tarifa buscaTarifaMes( final Secao secao, final String mesAno )
	{
		return this.tarifaDAO.buscaTarifaMes( secao, mesAno );
	}

	// FBW-36
	/**
	 * Busca tarifa mes.
	 *
	 * @param secao
	 *            the secao
	 * @param mesAno
	 *            the mes ano
	 * @return the tarifa
	 */
	public Tarifa buscaPrimeiraTarifaMes( final Secao secao, final String mesAno )
	{
		return this.tarifaDAO.buscaPrimeiraTarifaMes( secao, mesAno );
	}

	
	// ----------------------------------------------------------------------

	/**
	 * Busca tarifas.
	 *
	 * @param secoes
	 *            the secoes
	 * @return the list
	 */
	public List<Tarifa> buscaTarifas( final List<Secao> secoes )
	{
		return this.tarifaDAO.buscaTarifas( secoes );
	}

	/**
	 * Busca tarifas futuras e atual.
	 *
	 * @param secoes
	 *            the secoes
	 * @return the list
	 */
	public List<Tarifa> buscaTarifasFuturasEAtual( final List<Secao> secoes )
	{
		return this.tarifaDAO.buscaTarifasFuturasEAtual( secoes );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataInicioVigencia
	 * @param secao
	 * @throws DaoException
	 */
	private void deletarTodasTarifasPosteriores( final Date dataInicioVigencia, final Secao secao )
		throws DaoException
	{
		final List<Tarifa> tarifaList = this.tarifaDAO.buscaTarifasPosteriores( dataInicioVigencia, secao );

		if ( Check.isNotEmpty( tarifaList ) )
		{

			for ( final Tarifa tarifa : tarifaList )
			{
				this.tarifaDAO.deleteFisicamente( tarifa );
			}// for

		}// if

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#delete(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void delete( final Tarifa entity )
		throws BusinessException
	{
		final Tarifa tarifaExcluida = get( entity.getId() );
		final List<Tarifa> tarifasFuturas = buscarTarifasAindaNaoVigentes( tarifaExcluida );

		if ( tarifaExcluida.isVigente() )
		{
			if ( Check.isNotNull( tarifasFuturas ) )
			{
				for ( final Tarifa tarifaFutura : tarifasFuturas )
				{
					if ( !tarifaFutura.getId().equals( tarifaExcluida.getId() ) )
					{
						try
						{
							this.tarifaDAO.delete( tarifaFutura );
						}
						catch ( final DaoException e )
						{
							if ( LOG.isErrorEnabled() )
							{
								LOG.error( e, e );
							}// if
						}
					}
				}
			}
		}
		// se a tarifa removida for a unica futura, remove a data de fim de vigencia da tarifa
		// atual.
		else if ( tarifasFuturas.size() == 1 )
		{
			final Tarifa tarifaAtual = buscaTarifaAtual( tarifaExcluida.getSecao() );
			if ( Check.isNotNull( tarifaAtual ) )
			{
				tarifaAtual.setFimVigencia( null );
				try
				{
					this.tarifaDAO.update( tarifaAtual );
				}
				catch ( final DaoException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
			}
		}

		// remove possiveis tarifas geradas devido a alguma alteracao de linha
		final List<Tarifa> tarifasGeradas = this.tarifaDAO.getPorIdTarifaAntiga( tarifaExcluida.getId() );

		if ( Check.isNotNull( tarifasGeradas ) )
		{
			for ( final Tarifa tarifatmp : tarifasGeradas )
			{
				try
				{
					this.tarifaDAO.delete( tarifatmp );
				}
				catch ( final DaoException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
			}
		}

		super.delete( entity );
	}

	/**
	 * Find all tarifas.
	 *
	 * @return the list
	 */
	public List<Tarifa> findAllTarifas()
	{
		return this.dao.search( null );
	}

	/**
	 * Find historico.
	 *
	 * @param tarifa
	 *            the tarifa
	 * @return the list
	 */
	public List<Tarifa> findHistorico( final Tarifa tarifa )
	{
		return this.tarifaDAO.findHistorico( tarifa );
	}

	/**
	 * Find valor tarifas.
	 *
	 * @param isListaFuturas
	 *            the is lista futuras
	 * @return the list
	 */
	public List<String> findValorTarifas( final boolean isListaFuturas )
	{
		return this.tarifaDAO.findValorTarifas( isListaFuturas );
	}

	/**
	 * <p>
	 * FBW-134 Registra no log
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param novaTarifa
	 *            the nova tarifa
	 * @param codLinha
	 *            the cod linha
	 * @param tipoLog
	 *            the tipo log
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void geraLog( final List<Tarifa> novaTarifa, String codLinha, final int tipoLog )
		throws IOException
	{

		final DynamicExcel excel = new DynamicExcel();
		final Sheet sheet = excel.addSheet();
		final Usuario usuario = ( Usuario ) getUser();
		Row row;

		// nome do arquivo
		final StringBuffer nome = new StringBuffer();
		nome.append( "tarifaRetroativa" );
		nome.append( "_" );

		// cabecalho
		row = sheet.addRow().centralize();
		row.addCell( 1 ).useBold().setText( "CodLinha" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell( 2 ).useBold().setText( "Valor" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell( 3 ).useBold().setText( "Início Vigência" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell( 4 ).useBold().setText( "Fim Vigência" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );

		for ( final Tarifa tarifa : novaTarifa )
		{
			// pula linha
			row = sheet.addRow().centralize();

			if ( tipoLog == 1 )
			{
				row.addCell( 1 ).setText( tarifa.getSecao().getLinhaVigencia().getCodigo() );
				final String sql = "update tarifas set valor=" + tarifa.getValor() + " where id=" + tarifa.getId();
				row = sheet.addRow().centralize();
				row.addCell( 1 ).useBold().setText( "SQL" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
				row = sheet.addRow().centralize();
				row.addCell( 1 ).setText( sql );
				codLinha = tarifa.getSecao().getLinhaVigencia().getCodigo();
			}
			else
			{
				row.addCell( 1 ).setText( codLinha );
			}

			row.addCell( 2 ).setText( tarifa.getValor().toString() );
			row.addCell( 3 ).setText( tarifa.getInicioVigenciaFormated() );
			row.addCell( 4 ).setText( tarifa.getFimVigenciaFormated() );
		}

		nome.append( codLinha );
		nome.append( "_" );
		nome.append( usuario.getNome() );
		nome.append( "-TIPO" + tipoLog );

		// nome do arquivo
		nome.append( "_" );
		nome.append( new SimpleDateFormat( "yyyy-MM-dd-H-m-s" ).format( new Date() ) );
		nome.append( ".xls" );

		excel.generate( this.configuracaoBusiness.buscarDiretorioXLS() + nome.toString() );
	}

	/**
	 * Gera nova tarifa para atualizacao tarifaria.
	 *
	 * @param entity
	 *            the entity
	 * @return the tarifa
	 */
	public Tarifa geraNovaTarifaParaAtualizacaoTarifaria( final Tarifa entity )
	{
		final Tarifa novaTarifa = new Tarifa();
		novaTarifa.setActive( Boolean.TRUE );
		novaTarifa.setSecao( entity.getSecao() );
		novaTarifa.setInicioVigencia( entity.getInicioVigencia() );
		novaTarifa.setFimVigencia( null );
		novaTarifa.setValor( entity.getValor() );
		novaTarifa.setIdTarifaAntiga( entity.getId() );
		novaTarifa.setMotivoCriacao( MotivoCriacaoTarifa.ATUALIZACAO_TARIFARIA );
		return novaTarifa;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	private ConfiguracaoBusiness getConfiguracaoBusiness()
	{
		return this.configuracaoBusiness;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param umaLinha
	 * @return
	 */
	public Date getDataPrimeiraLinhaVigencia( final Linha umaLinha )
	{
		final LinhaVigencia linhaVigencia = umaLinha.getLinhaVigente();
		final Empresa empresa = linhaVigencia.getEmpresa();
		final Empresa umaEmpresa = empresa;
		final String codigo = linhaVigencia.getCodigo();
		final LinhaDAO linhaDAO = getLinhaDAO();
		final Date dataPrimeiraLinhaVigencia = linhaDAO.buscarDataPrimeiraLinhaVigencia( umaEmpresa, codigo );
		return dataPrimeiraLinhaVigencia;

	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-505
	 * </p>
	 *
	 * @return
	 */
	private EmpresaDAO getEmpresaDAO()
	{
		return this.empresaDAO;
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	private LinhaDAO getLinhaDAO()
	{
		return this.linhaDAO;
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataInicioVigencia
	 * @param empresa
	 * @return
	 */
	public String getMensagemComBomsQueSeraoApagados( final String dataInicioVigencia, final Empresa empresa )
	{
		final String mesAnoInicioVigencia = dataInicioVigencia.substring( 3, 10 );

		final String bomsQueSeraoApagados = getMensagemComBomsQueSeraoApagadosPrivate( empresa, mesAnoInicioVigencia );
		return bomsQueSeraoApagados;
	}// func

	public String getMensagemComBomsQueSeraoApagados( final String dataInicio, final Long empresaId )
	{
		final EmpresaDAO empresaDAO = getEmpresaDAO();
		final Empresa empresa = empresaDAO.get( empresaId );
		final String bomsQueSeraoApagados = getMensagemComBomsQueSeraoApagados( dataInicio, empresa );
		return bomsQueSeraoApagados;
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param empresa
	 * @param mesAnoInicioVigencia
	 * @return
	 */
	private String getMensagemComBomsQueSeraoApagadosPrivate( final Empresa empresa, final String mesAnoInicioVigencia )
	{
		final TarifaDAO tarifaDAO = getTarifaDAO();
		final List<Bom> bomsLista = tarifaDAO.buscarIdBomPorIdEmpresa( empresa, mesAnoInicioVigencia );

		if ( bomsLista.isEmpty() )
		{
			return "Nenhum BOM foi encontrado a partir da data informada.\n";
		}// if

		final StringBuilder bomsQueSeraoApagados = new StringBuilder();

		for ( final Bom bom : bomsLista )
		{
			bomsQueSeraoApagados.append( bom.getMesReferencia() );
			bomsQueSeraoApagados.append( "\n" );

		}

		return bomsQueSeraoApagados.toString();

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	private TarifaDAO getTarifaDAO()
	{
		return this.tarifaDAO;
	}

	/**
	 * Import excel.
	 *
	 * @param data
	 *            the data
	 * @param arquivo
	 *            the arquivo
	 * @return the int
	 * @throws BusinessException
	 *             the business exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public int importExcel( final String data, final File arquivo )
		throws BusinessException,
			ParseException
	{
		final ImportExcel excel = new ImportExcel();
		final List<ImportDTO> listImportDTOs = excel.loadArquivo( arquivo );
		int numTarifasAtualizadas = 0;

		final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
		final Date dataVigencia = format.parse( data );

		if ( Check.isNotNull( listImportDTOs ) && Check.isNotEmpty( listImportDTOs ) )
		{
			validateImportExcel( listImportDTOs );

			for ( final ImportDTO i : listImportDTOs )
			{
				final Tarifa t = this.tarifaDAO.buscarTarifa( Long.parseLong( i.getValues().get( INDICE_EXCEL_ID ) ) );
				if ( Check.isNotNull( t ) && t.isVigente() )
				{

					final Tarifa novaTarifa = geraNovaTarifaParaAtualizacaoTarifaria( t );
					novaTarifa.setValor( new BigDecimal( i.getValues().get( INDICE_EXCEL_VALOR ).replace( ",", "." ) ) );
					novaTarifa.setInicioVigencia( dataVigencia );

					final List<Tarifa> tarifasFuturas = buscarTarifasAindaNaoVigentes( t );
					try
					{
						// remove as tarifas futuras
						if ( Check.isNotNull( tarifasFuturas ) )
						{
							for ( final Tarifa tarifaF : tarifasFuturas )
							{
								this.tarifaDAO.delete( tarifaF );
							}
						}
						this.tarifaDAO.save( novaTarifa );
						numTarifasAtualizadas++;

					}
					catch ( final DaoException e )
					{
						throw new BusinessException( e );
					}
					atualizaFimVigencia( t, novaTarifa );
				}
			}
		}
		return numTarifasAtualizadas;
	}

	/**
	 * <p>
	 * Importa as tarifas do arquivo Excel para o caso de tarifas retroativas
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param arquivo
	 *            the arquivo
	 * @return Número de tarifas atualizadas
	 * @throws BusinessException
	 *             the business exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public int importExcelTR( final File arquivo )
		throws BusinessException,
			ParseException
	{

		final ImportExcel excel = new ImportExcel();
		final List<ImportDTO> listImportDTOs = excel.loadArquivo( arquivo );
		int numTarifasAtualizadas = 0;
		final SimpleDateFormat format1 = new SimpleDateFormat( "dd/MM/yy" );
		Date dataAnt = new Date();
		long idTarifaAnterior = 0;
		final List<Tarifa> errosIniVg = new ArrayList<Tarifa>(); // Para qdo a dataIniVg é
																	// igual ao do arquivo
		final List<Tarifa> errosFimVg = new ArrayList<Tarifa>();
		Boolean flag = Boolean.TRUE;

		if ( Check.isNotNull( listImportDTOs ) && Check.isNotEmpty( listImportDTOs ) )
		{
			validateImportExcel( listImportDTOs );

			for ( final ImportDTO i : listImportDTOs )
			{
				// recebe o historico da tarifa para cada linha do arquivo
				final List<Tarifa> tarifaHist = this.tarifaDAO.buscarTarifaCodLinhaSecao(
					i.getValues().get( INDICE_EXCEL_CODIGO_LINHA ),
					i.getValues().get( INDICE_EXCEL_CODIGO_SECAO ) );

				if ( tarifaHist.size() == 0 )
				{
					flag = Boolean.FALSE;// tarifa nova, tem q cadastrar primeiro
				}
				// As tarifas devem levar em consideração a quantidade de registros da
				// linha_vigência pois podem existir N registros a a tarifa deve ser criada em todos
				// eles.
				final Map<Long, List<Tarifa>> tarifasPorLinhaVigencia = new HashMap<Long, List<Tarifa>>();
				if ( tarifaHist.size() != 0 )
				{

					for ( final Tarifa tarifa : tarifaHist )
					{
						final Long idLinhaVigencia = tarifa.getSecao().getLinhaVigencia().getId();

						final List<Tarifa> tarifas = new ArrayList<Tarifa>();
						for ( final Tarifa tarifa2 : tarifaHist )
						{
							final Long idLinhaVigencia2 = tarifa2.getSecao().getLinhaVigencia().getId();
							if ( idLinhaVigencia2 == idLinhaVigencia )
							{
								tarifas.add( tarifa2 );
							}// if
						}// for

						tarifasPorLinhaVigencia.put( idLinhaVigencia, tarifas );
					}// for
				}// if

				if ( !flag )// tarifa nova
				{
					try
					{
						errosFimVg.add( new Tarifa() );
						geraLog( errosFimVg, i.getValues().get( INDICE_EXCEL_CODIGO_LINHA ), 2 );
					}
					catch ( final IOException e )
					{
						throw new BusinessException( e );
					}

					throw new BusinessException( "A linha "
						+ i.getValues().get( INDICE_EXCEL_CODIGO_LINHA )
						+ " ainda não foi cadastrada no sistema!"
						+ " Nenhuma tarifa foi importada." );
				}

				try
				{
					if ( tarifasPorLinhaVigencia.size() != 0 )
					{
						for ( final Entry<Long, List<Tarifa>> pair : tarifasPorLinhaVigencia.entrySet() )
						{
							// monta a nova tarifa
							final Tarifa novaTarifa = new Tarifa();
							novaTarifa.setActive( Boolean.TRUE );
							novaTarifa.setMotivoCriacao( MotivoCriacaoTarifa.ATUALIZACAO_TARIFARIA );
							novaTarifa.setValor( new BigDecimal( i
								.getValues()
								.get( INDICE_EXCEL_VALOR )
								.replace( ",", "." ) ) );
							String dataExcel = i.getValues().get( INDICE_EXCEL_INICIO_VIGENCIA );
							novaTarifa.setInicioVigencia( format1.parse( dataExcel ) );

							final List<Tarifa> tarifas = pair.getValue();

							int cont = 0;// para saber se esta no final do historico
							for ( final Tarifa tarifaH : tarifas )
							{
								cont++;
								// verificar se o dado do arquivo ja existe (user importa o msm
								// arquivo)
								if ( ( tarifaH.getInicioVigencia().compareTo( novaTarifa.getInicioVigencia() ) == 0 ) )
								{
									tarifaH.setValor( novaTarifa.getValor() );
									errosIniVg.add( tarifaH );
									break;
								}

								// se a data do banco é maior que a data do arquivo
								if ( tarifaH.getInicioVigencia().compareTo( novaTarifa.getInicioVigencia() ) > 0 )
								{
									// pega a data1 menos um dia e coloca como data2.fimVig;
									// alterar a data1.fimVig anterior e coloca a dataFimVig sendo
									// data2.iniVig menos um dia;<-- eh do comentario de cima
									// atualiza IdTarifaAntiga da novaTarifa e da
									// "logo abaixo"
									// salva nova tariva e update tarifaAnt e atual;
									// break;
									if ( i.getValues().size() > 8 )// para verificar se tem data
																	// digitada no arquivo (tem
																	// vezes
																	// que ele coloca [data_ini,],
																	// entao
																	// ele pegaria um valor, que
																	// seria
																	// vazio, mas pegaria. Porem tem
																	// vezes que ele coloca
																	// [data_ini],
																	// então ao tentar comparar com
																	// if(data_final), da erro de
																	// index.
																	// Então o jeito que encontrei
																	// foi
																	// primeiro verificar o tamanho
																	// do
																	// array para depois verificar
																	// se eh
																	// vazio.)
									{
										if ( !( i.getValues().get( INDICE_EXCEL_FIM_VIGENCIA ).isEmpty() ) )
										{
											dataExcel = i.getValues().get( INDICE_EXCEL_FIM_VIGENCIA );
											novaTarifa.setFimVigencia( format1.parse( dataExcel ) );

											final Calendar finalVigencia = new GregorianCalendar();
											finalVigencia.setTime( novaTarifa.getFimVigencia() );

											final Calendar finalVigencia2 = new GregorianCalendar();
											finalVigencia2.setTime( tarifaH.getInicioVigencia() );

											int diaAtual = 0, diaNova = 0;

											diaNova = finalVigencia.get( Calendar.DAY_OF_MONTH ) + 1;
											diaAtual = finalVigencia2.get( Calendar.DAY_OF_MONTH );

											if ( ( finalVigencia.get( Calendar.MONTH ) != finalVigencia2
												.get( Calendar.MONTH ) )
												|| ( finalVigencia.get( Calendar.YEAR ) != finalVigencia2
													.get( Calendar.YEAR ) )
												|| ( diaNova != diaAtual ) )
											{
												// registra no log
												try
												{
													errosFimVg.add( novaTarifa );
													geraLog(
														errosFimVg,
														i.getValues().get( INDICE_EXCEL_CODIGO_LINHA ),
														3 );
												}
												catch ( final IOException e )
												{
													throw new BusinessException( e );
												}

												throw new BusinessException(
													"Para a linha "
														+ i.getValues().get( INDICE_EXCEL_CODIGO_LINHA )
														+ " exite uma lacuna entre a data final do arquivo e entre a inicial logo após esta data que esta cadastrado no sistema. Tipo 3" );
											}

											if ( novaTarifa.getFimVigencia().compareTo( tarifaH.getInicioVigencia() ) > 0 )
											{
												// Não faz mais sentido esse erro, pois o acima já
												// mata
												// esse.
												// uma outra versão, era possivel atualizar a data
												// de
												// inicio da tarifa, o que não é mais, fazendo esse
												// erro
												// não mais necessário
												// Tarifivas entre a tarifa importada.
												// Ex: tarifa a ser importada: 2013-06-24 |
												// 2013-06-28,
												// porem ja existe uma (ou mais) no banco com datas
												// 2013-06-25 | 2013-06-30

												// registra no log
												try
												{
													errosFimVg.add( novaTarifa );
													geraLog(
														errosFimVg,
														i.getValues().get( INDICE_EXCEL_CODIGO_LINHA ),
														3 );
												}
												catch ( final IOException e )
												{
													throw new BusinessException( e );
												}

												throw new BusinessException(
													"A linha "
														+ i.getValues().get( INDICE_EXCEL_CODIGO_LINHA )
														+ " tem data de fim de vigência posterior a inicial de outro período seguinte. Favor verificar histórico de tarífa para essa linha." );
											}
										}
										else
										{
											novaTarifa.setFimVigencia( removeDias( tarifaH.getInicioVigencia(), 1 ) );
										}
									}
									else
									{
										novaTarifa.setFimVigencia( removeDias( tarifaH.getInicioVigencia(), 1 ) );
									}

									novaTarifa.setIdTarifaAntiga( tarifaH.getIdTarifaAntiga() );

									// atualiza a data FimVigencia da tarifa anterior com dataAnt se
									// não
									// for a primeira do historico
									if ( idTarifaAnterior > 0 )
									{
										dataAnt = removeDias( novaTarifa.getInicioVigencia(), 1 );
										final Tarifa tAnt = this.tarifaDAO.buscarTarifa( idTarifaAnterior );
										tAnt.setFimVigencia( dataAnt );
										// update anterior se a tarifa do arquivo nao for a primeira
										// do
										// historico
										this.tarifaDAO.update( tAnt );
									}

									novaTarifa.setSecao( tarifaH.getSecao() );

									// salva nova
									this.tarifaDAO.save( novaTarifa );

									tarifaH.setIdTarifaAntiga( novaTarifa.getId() );
									numTarifasAtualizadas++;

									// update atual
									this.tarifaDAO.update( tarifaH );

									// apaga os BOMs
									final Date datatemp = format1.parse( dataExcel );
									final Calendar cal = Calendar.getInstance();
									cal.setTime( datatemp );
									final String mesAno = ( cal.get( Calendar.MONTH ) + 1 )
										+ "/"
										+ cal.get( Calendar.YEAR );
									apagaBom( i.getValues().get( INDICE_EXCEL_CODIGO_LINHA ), mesAno );

									idTarifaAnterior = 0;

									break;// vai para o proximo registro do arquivo
								}
								else if ( tarifas.size() == cont )// se eh o ultimo regist
								// caso a data do arquivo tenha uma data inicial vigente maior que
								// do
								// historio
								{

									if ( i.getValues().size() > 8 )// para verificar se tem data
									// digitada no arquivo (tem vezes
									// que ele coloca [data_ini,], entao
									// ele pegaria um valor, que seria
									// vazio, mas pegaria. Porem tem
									// vezes que ele coloca [data_ini],
									// então ao tentar comparar com
									// if(data_final), da erro de index.
									// Então o jeito que encontrei foi
									// primeiro verificar o tamanho do
									// array para depois verificar se eh
									// vazio.)
									{
										if ( !( i.getValues().get( INDICE_EXCEL_FIM_VIGENCIA ).isEmpty() )
											&& ( i.getValues().get( INDICE_EXCEL_FIM_VIGENCIA ).length() == 0 ) )
										{
											dataExcel = i.getValues().get( INDICE_EXCEL_FIM_VIGENCIA );
											novaTarifa.setFimVigencia( format1.parse( dataExcel ) );
										}
									}

									dataAnt = removeDias( novaTarifa.getInicioVigencia(), 1 );
									novaTarifa.setIdTarifaAntiga( tarifaH.getId() );
									tarifaH.setFimVigencia( dataAnt );
									novaTarifa.setSecao( tarifaH.getSecao() );

									// salva a tarifa nova
									this.tarifaDAO.save( novaTarifa );

									tarifaH.setIdTarifaAntiga( novaTarifa.getId() );

									// update atual
									this.tarifaDAO.update( tarifaH );

									// apaga os BOMs
									final Date datatemp = format1.parse( dataExcel );
									final Calendar cal = Calendar.getInstance();
									cal.setTime( datatemp );
									final String mesAno = ( cal.get( Calendar.MONTH ) + 1 )
										+ "/"
										+ cal.get( Calendar.YEAR );
									apagaBom( i.getValues().get( INDICE_EXCEL_CODIGO_LINHA ), mesAno );

									numTarifasAtualizadas++;

								}
								idTarifaAnterior = tarifaH.getId();
							}
						}
					}
				}
				catch ( final DaoException e )
				{
					throw new BusinessException( e );
				}
			}

			if ( errosIniVg.size() > 0 )
			{
				if ( LOG.isErrorEnabled() )
				{
					final String logMsg = String
						.format(
							"Existe uma ou mais datas iniciais de vigência iguais as já cadastradas no banco de dados. Talvez o mesmo arquivo tenha sido enviado!:\n%s",
							Arrays.toString( errosIniVg.toArray() ) );
					LOG.error( logMsg );
				}// if
			}
		}
		return numTarifasAtualizadas;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 */
	public boolean isEdicaoRetroativa( final Tarifa entity )
	{

		final boolean resultado = isInicioVigenciaTarifaAnteriorHoje( entity );
		return resultado;

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param umaTarifa
	 * @return
	 */
	public boolean isInicioVigenciaTarifaAnteriorHoje( final Tarifa umaTarifa )
	{
		if ( Check.isNotNull( umaTarifa ) )
		{
			if ( umaTarifa.getInicioVigencia().before( new Date() ) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Ja existe tarifa.
	 *
	 * @param secao
	 *            the secao
	 * @return true, if successful
	 */
	public boolean jaExisteTarifa( final Secao secao )
	{
		if ( ( this.tarifaDAO.buscaTarifaAtual( secao ) != null ) || ( this.tarifaDAO.tarifaFutura( secao ) != null ) )
		{
			return true;
		}
		return false;
	}

	/**
	 * Lista com filtro.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	public List<Tarifa> listaComFiltro( final FiltroTarifaDTO filtro )
	{
		return this.tarifaDAO.filtro( filtro );
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 *
	 * @param tarifa
	 * @param justificativa
	 * @throws DaoException
	 */
	private void logarEdicaoRetroativa( final Tarifa tarifa, final String justificativa )
		throws DaoException
	{
		// FBW-487
		// FBW-483

		if ( Check.isNotNull( tarifa ) )
		{
			tarifa.setEdicaoRetroativaJustificativa( justificativa );
		}// if

		final TarifaDAO tarifaDAO = getTarifaDAO();
		tarifaDAO.save( tarifa );
	}// func

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#save(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void save( final Tarifa entity )
		throws BusinessException
	{
		beforeSave( entity );

		entity.setActive( true );
		entity.setMotivoCriacao( MotivoCriacaoTarifa.ATUALIZACAO_TARIFARIA );
		super.save( entity );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#update(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public Tarifa update( final Tarifa entity )
		throws BusinessException
	{

		try
		{
			final Tarifa novaTarifa = geraNovaTarifaParaAtualizacaoTarifaria( entity );

			this.tarifaDAO.save( novaTarifa );
			final Tarifa tarifaDB = get( entity.getId() );
			atualizaFimVigencia( tarifaDB, novaTarifa );
			return novaTarifa;

		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		catch ( final DaoException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		return null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @param justificativa
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 */
	public Tarifa updateRetroativo( final Tarifa entity, final String justificativa )
		throws BusinessException,
			ParseException
	{
		try
		{
			final Date inicioVigencia = entity.getInicioVigencia();
			final Tarifa tarifaDB = this.tarifaDAO.get( entity.getId() );
			final Secao secao = tarifaDB.getSecao();
			final LinhaVigencia linhaVigencia = secao.getLinhaVigencia();
			final Linha linha = linhaVigencia.getLinha();
			final String codigo = linhaVigencia.getCodigo();
			final List<LinhaVigencia> linhaVigenciaList = this.linhaDAO.buscaLinhasVigenciaAPartirDe(
				inicioVigencia,
				linha,
				codigo );
			for ( final LinhaVigencia linhaVigencia2 : linhaVigenciaList )
			{
				final List<Secao> secoes = linhaVigencia2.getSecoes();
				for ( final Secao secao2 : secoes )
				{
					if ( secao2.getCodigo().equals( secao.getCodigo() ) )
					{
						entity.setSecao( secao2 );
						atualizarRetroativamente( entity, justificativa );
						deletarTodasTarifasPosteriores( inicioVigencia, secao2 );

					}// if
				}// for
			}// for
			final Empresa empresa = linhaVigencia.getEmpresa();
			final String mesAno = new SimpleDateFormat( "MM/yyyy" ).format( inicioVigencia );
			final String codigoSecao = secao.getCodigo();
			apagaBomEdicaoRetroativa( empresa, mesAno, codigo, codigoSecao, justificativa );
			return entity;

		}
		catch ( final DaoException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		return null;
	}

	/**
	 * Validacoes comuns before save e update.
	 *
	 * @param entity
	 *            the entity
	 * @throws BusinessException
	 *             the business exception
	 */
	protected void validacoesComunsBeforeSaveEUpdate( final Tarifa entity )
		throws BusinessException
	{

		if ( Check.isNull( entity.getSecao() ) || Check.isNull( entity.getSecao().getId() ) )
		{
			throw new BusinessException( "Selecione uma seção para cadastrar uma nova tarifa." );
		}

	}

	/**
	 * Validate import date.
	 *
	 * @param validator
	 *            the validator
	 * @param data
	 *            the data
	 * @throws ParseException
	 *             the parse exception
	 * @throws BusinessException
	 *             the business exception
	 */
	public void validateImportDate( final Validator validator, final String data )
		throws ParseException,
			BusinessException
	{

		final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
		final Date dataDaTela = format.parse( data );
		final Calendar x = new GregorianCalendar();
		x.setTime( new Date() );
		final String datasemhora = format.format( x.getTime() );

		if ( dataDaTela.before( format.parse( datasemhora ) ) || dataDaTela.equals( format.parse( datasemhora ) ) )
		{
			throw new BusinessException( "Data de Vigência incorreta. Deve ser posterior à data atual." );
		}
	}

	/**
	 * Validate import excel.
	 *
	 * @param imports
	 *            the imports
	 * @throws BusinessException
	 *             the business exception
	 */
	public void validateImportExcel( final List<ImportDTO> imports )
		throws BusinessException
	{
		Long numeroLinha = 1L;
		for ( final ImportDTO importDTO : imports )
		{
			try
			{
				Long.parseLong( importDTO.getValues().get( INDICE_EXCEL_ID ) );
			}
			catch ( final NumberFormatException e )
			{
				throw new BusinessException( "Número inválido na coluna "
					+ ( INDICE_EXCEL_ID + 1 )
					+ " ("
					+ importDTO.getValues().get( INDICE_EXCEL_ID )
					+ ") da linha "
					+ numeroLinha, e );
			}

			try
			{
				Double.parseDouble( importDTO.getValues().get( INDICE_EXCEL_VALOR ).replace( ",", "." ) );
			}
			catch ( final NumberFormatException e )
			{
				throw new BusinessException( "Valor inválido de tarifa informado na coluna "
					+ ( INDICE_EXCEL_VALOR + 1 )
					+ " ("
					+ importDTO.getValues().get( INDICE_EXCEL_VALOR )
					+ ") da linha "
					+ numeroLinha, e );
			}

			numeroLinha++;
		}
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
	 * <p>
	 * Field <code>empresaDAO</code>
	 * </p>
	 */
	private EmpresaDAO empresaDAO;

	/**
	 * The linha business.
	 */
	private final LinhaBusiness linhaBusiness;

	/**
	 * <p>
	 * Field <code>linhaDAO</code>
	 * </p>
	 */
	private final LinhaDAO linhaDAO;

	/**
	 * The tarifa dao.
	 */
	private final TarifaDAO tarifaDAO;

	/**
	 * The bom reabertura dao
	 */
	private final BomReaberturaDAO bomReaberturaDAO;
}

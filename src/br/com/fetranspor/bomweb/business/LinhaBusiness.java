package br.com.fetranspor.bomweb.business;

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
import br.com.fetranspor.bomweb.dao.TipoDeLinhaDAO;
import br.com.fetranspor.bomweb.dto.ImportDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomReabertura;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Justificativa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.LinhaVigenciaTipoDeLinha;
import br.com.fetranspor.bomweb.entity.MotivoCriacaoTarifa;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.StatusLinha;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.TipoDeLinha;
import br.com.fetranspor.bomweb.entity.TipoDeLinhaTipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.exception.CloneFailException;
import br.com.fetranspor.bomweb.exception.OrphanBomLinhaException;
import br.com.fetranspor.bomweb.util.DateProvider;
import br.com.fetranspor.bomweb.util.DateUtil;
import br.com.fetranspor.bomweb.util.UtilitiesMail;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.ObjectNotFoundException;

/**
 * <p>
 * The Class LinhaBusiness.
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 29/06/2015
 */
@Component
public class LinhaBusiness
	extends VRaptorBusiness<Linha>
{

	/**
	 * The Class InterventionDataLine.
	 */
	private static class InterventionDataLine
	{

		/**
		 * <p>
		 * </p>
		 * .
		 */
		public InterventionDataLine()
		{
			super();
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the dateEnd.
		 * @see #dateEnd
		 */
		public Date getDateEnd()
		{
			return this.dateEnd;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the dateCreation.
		 * @see #dateInit
		 */
		public Date getDateInit()
		{
			return this.dateInit;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the empresaNew.
		 * @see #empresaNew
		 */
		public Empresa getEmpresaNew()
		{
			return this.empresaNew;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the interventionDataSessionMap.
		 * @see #interventionDataSessionMap
		 */
		public Map<String, InterventionDataSession> getInterventionDataSessionMap()
		{
			return this.interventionDataSessionMap;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the obs.
		 * @see #obs
		 */
		public String getObs()
		{
			return this.obs;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param dateEnd
		 *            The dateEnd to set.
		 * @see #dateEnd
		 */
		public void setDateEnd( final Date dateEnd )
		{
			this.dateEnd = dateEnd;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param dateCreation
		 *            The dateCreation to set.
		 * @see #dateInit
		 */
		public void setDateInit( final Date dateCreation )
		{
			this.dateInit = dateCreation;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param empresaNew
		 *            The empresaNew to set.
		 * @see #empresaNew
		 */
		public void setEmpresaNew( final Empresa empresaNew )
		{
			this.empresaNew = empresaNew;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param interventionDataSessionMap
		 *            The interventionDataSessionMap to set.
		 * @see #interventionDataSessionMap
		 */
		public void setInterventionDataSessionMap( final Map<String, InterventionDataSession> interventionDataSessionMap )
		{
			this.interventionDataSessionMap = interventionDataSessionMap;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param lineCode
		 *            The lineCode to set.
		 * @see #lineCode
		 */
		public void setLineCode( final String lineCode )
		{
			// DOES NOTHING
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param obs
		 *            The obs to set.
		 * @see #obs
		 */
		public void setObs( final String obs )
		{
			this.obs = obs;
		}

		/**
		 * <p>
		 * Field <code>dateEnd</code>
		 * </p>
		 * .
		 */
		private Date dateEnd;

		/**
		 * <p>
		 * Field <code>dateInit</code>
		 * </p>
		 * .
		 */
		private Date dateInit;

		/**
		 * <p>
		 * Field <code>empresaNew</code>
		 * </p>
		 * .
		 */
		private Empresa empresaNew;

		/**
		 * <p>
		 * Field <code>interventionDataActivateMap</code>
		 * </p>
		 * .
		 */
		private Map<String, InterventionDataSession> interventionDataSessionMap;

		/**
		 * <p>
		 * Field <code>obs</code>
		 * </p>
		 * .
		 */
		private String obs;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-234
	 * </p>
	 * .
	 *
	 * @author daniel.azeredo
	 * @version 1.0 Created on Sep 9, 2014
	 */
	private static class InterventionDataSession
	{

		/**
		 * <p>
		 * </p>
		 * .
		 */
		public InterventionDataSession()
		{
			super();
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the dateStart.
		 * @see #dateStart
		 */
		public Date getDateStart()
		{
			return this.dateStart;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @return Returns the tarifaValue.
		 * @see #tarifaValue
		 */
		public BigDecimal getTarifaValue()
		{
			return this.tarifaValue;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param dateStart
		 *            The dateStart to set.
		 * @see #dateStart
		 */
		public void setDateStart( final Date dateStart )
		{
			this.dateStart = dateStart;
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param sessionCode
		 *            The sessionCode to set.
		 * @see #sessionCode
		 */
		public void setSessionCode( final String sessionCode )
		{
			// DOES NOTHING
		}

		/**
		 * <p>
		 * </p>
		 * .
		 *
		 * @param tarifaValue
		 *            The tarifaValue to set.
		 * @see #tarifaValue
		 */
		public void setTarifaValue( final BigDecimal tarifaValue )
		{
			this.tarifaValue = tarifaValue;
		}

		/**
		 * <p>
		 * Field <code>dateStart</code>
		 * </p>
		 * .
		 */
		private Date dateStart;

		/**
		 * <p>
		 * Field <code>tarifaValue</code>
		 * </p>
		 * .
		 */
		private BigDecimal tarifaValue;
	}

	/**
	 * The Constant INDICE_EXCEL_CARACTERISTICA.
	 */
	public static final int INDICE_EXCEL_CARACTERISTICA = 8;

	/**
	 * The Constant INDICE_EXCEL_COD_LINHA.
	 */
	public static final int INDICE_EXCEL_COD_LINHA = 2;

	/**
	 * The Constant INDICE_EXCEL_COD_SECAO.
	 */
	public static final int INDICE_EXCEL_COD_SECAO = 3;

	/**
	 * The Constant INDICE_EXCEL_DT_CRIACAO.
	 */
	public static final int INDICE_EXCEL_DT_CRIACAO = 14;

	/**
	 * The Constant INDICE_EXCEL_DT_INICIO.
	 */
	public static final int INDICE_EXCEL_DT_INICIO = 15;

	/**
	 * The Constant INDICE_EXCEL_DT_TERMINO.
	 */
	public static final int INDICE_EXCEL_DT_TERMINO = 16;

	/**
	 * The Constant INDICE_EXCEL_NOME_EMPRESA.
	 */
	public static final int INDICE_EXCEL_NOME_EMPRESA = 1;

	/**
	 * The Constant INDICE_EXCEL_NOME_SC.
	 */
	public static final int INDICE_EXCEL_NOME_SC = 6;

	// public static final int INDICE_EXCEL_OPERACAO = 0;

	/**
	 * The Constant INDICE_EXCEL_NUM_LH.
	 */
	public static final int INDICE_EXCEL_NUM_LH = 5;

	/**
	 * The Constant INDICE_EXCEL_OBS.
	 */
	public static final int INDICE_EXCEL_OBS = 18;

	/**
	 * The Constant INDICE_EXCEL_PISO_2.
	 */
	public static final int INDICE_EXCEL_PISO_2 = 13;

	/**
	 * The Constant INDICE_EXCEL_PISO_I.
	 */
	public static final int INDICE_EXCEL_PISO_I = 12;

	/**
	 * The Constant INDICE_EXCEL_REGISTRO.
	 */
	public static final int INDICE_EXCEL_REGISTRO = 0;

	/**
	 * The Constant INDICE_EXCEL_SECAO.
	 */
	public static final int INDICE_EXCEL_SECAO = 4;

	/**
	 * The Constant INDICE_EXCEL_STATUS.
	 */
	public static final int INDICE_EXCEL_STATUS = 17;

	/**
	 * The Constant INDICE_EXCEL_TARIFA_2012.
	 */
	public static final int INDICE_EXCEL_TARIFA_2012 = 10;

	/**
	 * The Constant INDICE_EXCEL_VALOR.
	 */
	public static final int INDICE_EXCEL_VALOR = 11;

	/**
	 * The Constant INDICE_EXCEL_VEICULO.
	 */
	public static final int INDICE_EXCEL_VEICULO = 9;

	/**
	 * The Constant INDICE_EXCEL_VIA.
	 */
	public static final int INDICE_EXCEL_VIA = 7;

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LinhaBusiness.class );

	/**
	 * The Constant STR_SEPARA_IDS.
	 */
	public static final String STR_SEPARA_IDS = "000101000";

	/**
	 * <p>
	 * </p>
	 *
	 * @param provider
	 * @param dao
	 * @param empresaBusiness
	 * @param tipoDeVeiculoBusiness
	 * @param tipoDeLinhaBusiness
	 * @param tarifaDAO
	 * @param bomDAO
	 * @param bomReaberturaDAO
	 * @param tipoDeLinhaDAO
	 * @param empresaDAO
	 * @param configuracaoBusiness
	 */
	public LinhaBusiness(
		final VRaptorProvider provider,
		final LinhaDAO dao,
		final EmpresaBusiness empresaBusiness,
		final TipoDeVeiculoBusiness tipoDeVeiculoBusiness,
		final TipoDeLinhaBusiness tipoDeLinhaBusiness,
		final TarifaDAO tarifaDAO,
		final BomDAO bomDAO,
		final BomReaberturaDAO bomReaberturaDAO,
		final TipoDeLinhaDAO tipoDeLinhaDAO,
		final EmpresaDAO empresaDAO,
		final ConfiguracaoBusiness configuracaoBusiness )
	{
		super( provider );
		this.dao = dao;
		this.bomReaberturaDAO = bomReaberturaDAO;
		setEmpresaBusiness( empresaBusiness );
		this.tipoDeLinhaBusiness = tipoDeLinhaBusiness;
		setTarifaDAO( tarifaDAO );
		this.tipoDeLinhaDAO = tipoDeLinhaDAO;
		setEmpresaDAO( empresaDAO );
		this.bomDao = bomDAO;
		this.configuracaoBusiness = configuracaoBusiness;

	}// func

	/**
	 * <p>
	 * </p>
	 * TODO FIXME usar utilites.jar TODO FIXME mover para classe utilitaria
	 *
	 * @param source
	 * @param target
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public static <T> void populateWithNonNullProperties( final T source, final T target )
		throws IllegalAccessException,
			InvocationTargetException
	{
		final Class< ? extends Object> beanClass = source.getClass();
		final PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors( beanClass );
		for ( final PropertyDescriptor propertyDescriptor : propertyDescriptors )
		{

			final Method writeMethod = propertyDescriptor.getWriteMethod();
			final Method readMethod = propertyDescriptor.getReadMethod();
			if ( writeMethod == null )
			{
				continue;
			}// if
			if ( readMethod == null )
			{
				continue;
			}// if

			final Object value = readMethod.invoke( source );
			if ( value == null )
			{
				continue;
			}// if
			writeMethod.invoke( target, value );
		}// for

	}// func

	// ----------------------------------------------------------------------

	/**
	 * <p>
	 * Exclusão de BOMs afetados de acordo com a vigência da tarifa
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param empresa
	 *            the empresa
	 * @param mesAnoInicioVigencia
	 *            the mes ano inicio vigencia
	 * @return void
	 * @throws BusinessException
	 *             the business exception
	 * @throws ParseException
	 *             the parse exception
	 */
	public void apagaBom( final Empresa empresa, final String mesAnoInicioVigencia )
		throws BusinessException
	{
		// pegar os idBom que tenham data >= dataInicioVigencia e idEmpresa (pegar o idEmpresa pelo
		// codLinha)
		// pegar os os bom_linha_id pelo idBom
		// apagar bom_secao de todoas as id_bom_linha
		// apagar bom_linha de todas as idBom
		// apagar bom de todas as idBom
		final List<Bom> bomIdList = getTarifaDAO().buscarIdBomPorIdEmpresa( empresa, mesAnoInicioVigencia );
		String emailDetro = "";
		String emailEmpresa = "";
		String nomeEmpresa = "";
		String mesesRefe = "";

		for ( final Bom bomId : bomIdList )
		{
			final List<BomLinha> bomIdLinhaList = getTarifaDAO().buscarIdBomLinha( bomId );

			for ( final BomLinha bomIdLinha : bomIdLinhaList )
			{
				// deleta justificativa
				final List<Justificativa> justificativas = this.bomDao.buscarJustificativasPorBomLinha( bomIdLinha );
				if ( justificativas.size() > 0 )
				{
					for ( final Justificativa justificativa : justificativas )
					{
						this.bomDao.delete( justificativa );
					}
				}

				// deleta os bom secao
				getTarifaDAO().deletaBomSecao( bomIdLinha );
				// deleta os bom linha
				getTarifaDAO().deleteBomLinha( bomIdLinha );

			}
			final List<BomReabertura> listaBomReabertura = this.bomReaberturaDAO.buscaBomReaberturaByBom( bomId );
			this.bomReaberturaDAO.deleteListaBomReabertura( listaBomReabertura );
			// deleta os bom
			getTarifaDAO().deleteBom( bomId );
			final ConfiguracaoBusiness configuracaoBusiness = getConfiguracaoBusiness();
			emailDetro = configuracaoBusiness.buscarEmailDetro();
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
	 * @param dataInicioVigencia
	 * @param codigoLinhaVigencia
	 * @param justificativaEdicao
	 * @throws BusinessException
	 * @throws ParseException
	 */
	private void apagaBomAdicaoRetroativa(
		final Empresa empresa,
		final Date dataInicioVigencia,
		final String codigoLinhaVigencia,
		final String justificativaEdicao )
		throws BusinessException,
			ParseException
	{
		final String mesAno = new SimpleDateFormat( "MM/yyyy" ).format( dataInicioVigencia );
		apagaBomEdicaoRetroativa( empresa, mesAno, codigoLinhaVigencia, justificativaEdicao );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param empresa
	 * @param mesAnoInicioVigencia
	 * @param codigoLinhaVigencia
	 * @param justificativaEdicao
	 * @throws BusinessException
	 * @throws ParseException
	 */
	public void apagaBomEdicaoRetroativa(
		final Empresa empresa,
		final String mesAnoInicioVigencia,
		final String codigoLinhaVigencia,
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
				final List<Justificativa> justificativas = this.bomDao.buscarJustificativasPorBomLinha( bomIdLinha );
				if ( justificativas.size() > 0 )
				{
					for ( final Justificativa justificativa : justificativas )
					{
						this.bomDao.delete( justificativa );
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
			final String subject = "BOMWEB: Edição retroativa de linha";
			final String body = "Aviso: Devido a edição retroativa da linha "
				+ codigoLinhaVigencia
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
	 * Associa secoes.
	 *
	 * @param secoes
	 *            the secoes
	 * @param secoesPersistidas
	 *            the secoes persistidas
	 * @return the list
	 */
	private List<Secao> associaSecoes( final List<Secao> secoes, final List<Secao> secoesPersistidas )
	{
		final List<Secao> secoesUtilizadas = new ArrayList<Secao>();

		if ( Check.isNotNull( secoes ) )
		{
			final Date dataCriacao = new Date();
			for ( final Secao secao : secoes )
			{
				boolean achou = false;
				for ( final Secao secaoPersistida : secoesPersistidas )
				{
					if ( secao.getCodigo().equals( secaoPersistida.getCodigo() ) )
					{
						secao.setIdSecaoOrigem( secaoPersistida.getId() );
						secao.setDataCriacao( dataCriacao );
						secoesUtilizadas.add( secao );
						achou = true;
					}
				}
				if ( !achou )
				{
					secoesUtilizadas.add( secao );
				}
			}
		}
		return secoesUtilizadas;
	}

	/**
	 * Atualizar veiculos utilizados.
	 *
	 * @param linhaVigenciaPersistida
	 *            the linha vigencia persistida
	 * @param tipoDeVeiculos
	 *            the tipo de veiculos
	 */
	private void atualizarVeiculosUtilizados(
		final LinhaVigencia linhaVigenciaPersistida,
		final List<TipoDeVeiculo> tipoDeVeiculos )
	{
		final Map<Long, List<Long>> idsTLinhaTVeiculo = getIdsTiposLinhaTiposVeiculo( tipoDeVeiculos );

		final List<LinhaVigenciaTipoDeLinha> lvTiposLinha = linhaVigenciaPersistida.getLinhasVigenciaTiposDeLinha();

		for ( final LinhaVigenciaTipoDeLinha linhaVigenciaTipoDeLinha : lvTiposLinha )
		{
			final long idTipoLinha = linhaVigenciaTipoDeLinha.getTipoDeLinha().getId();
			final List<TipoDeLinhaTipoDeVeiculo> tlTiposVeiculos = new ArrayList<TipoDeLinhaTipoDeVeiculo>();
			final List<Long> veiculos = idsTLinhaTVeiculo.get( idTipoLinha );
			if ( veiculos != null )
			{
				for ( final Long idTipoVeiculo : veiculos )
				{
					tlTiposVeiculos
						.add( this.tipoDeLinhaDAO.findTipoLinhaTipoVeiculoAtiva( idTipoLinha, idTipoVeiculo ) );
				}
			}
			linhaVigenciaTipoDeLinha.setTiposDeLinhaTiposDeVeiculo( tlTiposVeiculos );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @see br.com.decatron.framework.business.AbstractBusiness#beforeUpdate(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected void beforeUpdate( final Linha entity )
		throws BusinessException
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();

		if ( perfil.isEmpresa() )
		{
			validaCamposPico( entity.getLinhaVigente() );
		}
		super.beforeUpdate( entity );
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
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscaLinhasAutoComplete( term, idEmpresa );
	}

	/**
	 * Busca linhas sem tarifa auto complete.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasSemTarifaAutoComplete( final String term, final String idEmpresa )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscaLinhasSemTarifaAutoComplete( term, idEmpresa );
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
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscaLinhasVigencia( getEmpresaBusiness().get( Long.valueOf( idEmpresa ) ) );
	}

	/**
	 * Busca linhas vigentes ou futuras.
	 *
	 * @param idEmpresa
	 *            the id empresa
	 * @return the list
	 */
	public List<LinhaVigencia> buscaLinhasVigentesOuFuturas( final String idEmpresa )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscaLinhasVigentesOuFuturas( getEmpresaBusiness().get( Long.valueOf( idEmpresa ) ) );
	}

	/**
	 * Busca linha vigencia.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscaLinhaVigencia( final LinhaVigencia linhaVigencia )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscarLinhaVigencia( linhaVigencia );
	}

	/**
	 * Busca linha vigencia.
	 *
	 * @param linhaVigenciaId
	 *            the linha vigencia id
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscaLinhaVigencia( final String linhaVigenciaId )
	{
		final LinhaVigencia linhaVigencia = new LinhaVigencia();
		linhaVigencia.setId( Long.valueOf( linhaVigenciaId ) );
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscarLinhaVigencia( linhaVigencia );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataInicioVigencia
	 * @param idLinha
	 * @param codigoLinha
	 *            FBW-483
	 * @return Retorna a linhaVigencia que estava ativa na respectiva data.
	 */
	public LinhaVigencia buscaLinhaVigenciaAtivaEm(
		final Date dataInicioVigencia,
		final Long idLinha,
		final String codigoLinha )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		final LinhaVigencia linhaVigenciaAtivaEm = linhaDAO.buscaLinhaVigenciaAtivaEm(
			dataInicioVigencia,
			idLinha,
			codigoLinha );
		return linhaVigenciaAtivaEm;
	}

	/**
	 * Busca linha vigencia de uma secao.
	 *
	 * @param secao
	 *            the secao
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscaLinhaVigenciaDeUmaSecao( final Secao secao )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscarLinhaVigenciaDeUmaSecao( secao );
	}

	/**
	 * Busca lista linhas vigencia.
	 *
	 * @param idsLinhasVigencia
	 *            the ids linhas vigencia
	 * @return the list
	 */
	public List<LinhaVigencia> buscaListaLinhasVigencia( final List<Long> idsLinhasVigencia )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscarLinhasVigencia( idsLinhasVigencia );
	}

	/**
	 * Buscar linha com vigencia futura.
	 *
	 * @param entity
	 *            the entity
	 * @return the linha
	 */
	public Linha buscarLinhaComVigenciaFutura( final Linha entity )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscarLinhaComVigenciaFutura( entity );
	}

	/**
	 * Buscar linha vigencia futura.
	 *
	 * @param linha
	 *            the linha
	 * @return the linha vigencia
	 */
	public LinhaVigencia buscarLinhaVigenciaFutura( final Linha linha )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.buscarLinhaVigenciaFutura( linha );
	}

	/**
	 * Busca secoes.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	public List<Secao> buscaSecoes( final Linha linha )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.listarSecoes( linha );
	}

	/**
	 * Busca secoes sem secao00.
	 *
	 * @param linha
	 *            the linha
	 * @return the list
	 */
	public List<Secao> buscaSecoesSemSecao00( final Linha linha )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.listarSecoesSemSecao00( linha );
	}

	/**
	 * Clona linha vigencia tipos linha.
	 *
	 * @param lvAntiga
	 *            the lv antiga
	 * @param lvNova
	 *            the lv nova
	 */
	private void clonaLinhaVigenciaTiposLinha( final LinhaVigencia lvAntiga, final LinhaVigencia lvNova )
	{
		final List<LinhaVigenciaTipoDeLinha> lvTLinhaAntigas = lvAntiga.getLinhasVigenciaTiposDeLinha();

		final List<LinhaVigenciaTipoDeLinha> lvTLinhaNovas = new ArrayList<LinhaVigenciaTipoDeLinha>();

		for ( final LinhaVigenciaTipoDeLinha lvtlAntiga : lvTLinhaAntigas )
		{
			final LinhaVigenciaTipoDeLinha lvtlNova = new LinhaVigenciaTipoDeLinha();
			lvtlNova.setLinhaVigencia( lvNova );
			lvtlNova.setTipoDeLinha( lvtlAntiga.getTipoDeLinha() );
			lvtlNova.setActive( lvtlAntiga.isActive() );
			lvTLinhaNovas.add( lvtlNova );
		}

		lvNova.setLinhasVigenciaTiposDeLinha( lvTLinhaNovas );
		final LinhaDAO linhaDAO = getLinhaDAO();
		linhaDAO.salvarLinhaVigencia( lvNova );

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param lvAntiga
	 * @param lvNova
	 */
	private void clonaLinhaVigenciaTiposLinhaComTiposDeVeiculo( final LinhaVigencia lvAntiga, final LinhaVigencia lvNova )
	{
		final List<LinhaVigenciaTipoDeLinha> lvTLinhaAntigas = lvAntiga.getLinhasVigenciaTiposDeLinha();

		final List<LinhaVigenciaTipoDeLinha> lvTLinhaNovas = new ArrayList<LinhaVigenciaTipoDeLinha>();

		for ( final LinhaVigenciaTipoDeLinha lvtlAntiga : lvTLinhaAntigas )
		{
			final LinhaVigenciaTipoDeLinha lvtlNova = new LinhaVigenciaTipoDeLinha();
			lvtlNova.setLinhaVigencia( lvNova );
			final TipoDeLinha tipoDeLinha = lvtlAntiga.getTipoDeLinha();
			lvtlNova.setTipoDeLinha( tipoDeLinha );
			final List<TipoDeLinhaTipoDeVeiculo> tiposDeLinhaTiposDeVeiculo = lvtlAntiga
				.getTiposDeLinhaTiposDeVeiculo();
			if ( Check.isNotNull( tiposDeLinhaTiposDeVeiculo ) )
			{
				final List<TipoDeLinhaTipoDeVeiculo> tipoDeLinhaTipoDeVeiculoList = new ArrayList<TipoDeLinhaTipoDeVeiculo>();
				tipoDeLinhaTipoDeVeiculoList.addAll( tiposDeLinhaTiposDeVeiculo );
				lvtlNova.setTiposDeLinhaTiposDeVeiculo( tipoDeLinhaTipoDeVeiculoList );
			}// if
			final boolean active = lvtlAntiga.isActive();
			lvtlNova.setActive( active );
			lvTLinhaNovas.add( lvtlNova );
		}

		lvNova.setLinhasVigenciaTiposDeLinha( lvTLinhaNovas );
		final LinhaDAO linhaDAO = getLinhaDAO();
		linhaDAO.salvarLinhaVigencia( lvNova );

	}

	/**
	 * Clona tarifas linha vigencia.
	 *
	 * @param lvAntiga
	 *            the lv antiga
	 * @param lvNova
	 *            the lv nova
	 */
	private void clonaTarifasLinhaVigencia( final LinhaVigencia lvAntiga, final LinhaVigencia lvNova )
	{
		final List<Tarifa> tarifaslvAntiga = getTarifaDAO().buscaTarifas( lvAntiga.getSecoes() );

		for ( final Secao secaoNova : lvNova.getSecoes() )
		{
			for ( final Secao secaoAntiga : lvAntiga.getSecoes() )
			{

				if ( secaoNova.getCodigo().equals( secaoAntiga.getCodigo() ) )
				{
					for ( final Tarifa tarifa : tarifaslvAntiga )
					{
						if ( tarifa.getSecao().getCodigo().equals( secaoNova.getCodigo() ) )
						{
							try
							{
								final Tarifa novaTarifa = tarifa.clone();
								novaTarifa.setIdTarifaAntiga( tarifa.getId() );
								novaTarifa.setSecao( secaoNova );
								novaTarifa.setMotivoCriacao( MotivoCriacaoTarifa.ATUALIZACAO_LINHA );
								getTarifaDAO().save( novaTarifa );
							}
							catch ( final CloneFailException e )
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
						}
					}
				}
			}
		}
	}

	/**
	 * Copia propriedades pico duracao.
	 *
	 * @param de
	 *            the de
	 * @param para
	 *            the para
	 */
	private void copiaPropriedadesPicoDuracao( final LinhaVigencia de, final LinhaVigencia para )
	{
		final String[] camposParaCopiar = {	"PicoInicioManhaAB",
											"PicoInicioManhaBA",
											"PicoFimManhaAB",
											"PicoFimManhaBA",
											"PicoInicioTardeAB",
											"PicoInicioTardeBA",
											"PicoFimTardeAB",
											"PicoFimTardeBA",
											"DuracaoViagemPicoAB",
											"DuracaoViagemPicoBA",
											"DuracaoViagemForaPicoAB",
											"DuracaoViagemForaPicoBA"};
		try
		{
			for ( final String element : camposParaCopiar )
			{
				final Method methodDe = de.getClass().getMethod( "get" + element );
				final String ret = ( String ) methodDe.invoke( de );

				if ( Check.isNotNull( ret ) )
				{
					final Method methodPara = para.getClass().getMethod( "set" + element, String.class );
					methodPara.invoke( para, ret );
				}
			}
		}
		catch ( final SecurityException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		catch ( final NoSuchMethodException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		catch ( final InvocationTargetException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		catch ( final IllegalAccessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
	}

	/**
	 * Cria secao00 para linha.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return the secao
	 */
	private Secao criaSecao00ParaLinha( final LinhaVigencia linhaVigencia )
	{
		final Secao secao00 = new Secao();
		secao00.setCodigo( Secao.COD_SECAO_OBRIGATORIA );
		secao00.setPontoInicial( linhaVigencia.getPontoInicial() );
		secao00.setPontoFinal( linhaVigencia.getPontoFinal() );
		secao00.setActive( Boolean.TRUE );
		secao00.setLinhaVigencia( linhaVigencia );
		secao00.setDataCriacao( new Date() );

		return secao00;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param linhaVigencia
	 * @return
	 */
	private Secao criaSecao00ParaLinhaRetroativa( final LinhaVigencia linhaVigencia )
	{
		final Secao secao00 = new Secao();
		secao00.setCodigo( Secao.COD_SECAO_OBRIGATORIA );
		secao00.setPontoInicial( linhaVigencia.getPontoInicial() );
		secao00.setPontoFinal( linhaVigencia.getPontoFinal() );
		secao00.setActive( Boolean.TRUE );
		secao00.setLinhaVigencia( linhaVigencia );
		secao00.setDataCriacao( linhaVigencia.getDataInicio() );

		return secao00;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataInicioVigencia
	 * @param idLinha
	 * @param codigo
	 * @throws DaoException
	 */
	private void deletarLinhaVigencia( final LinhaVigencia umaLinhaVigencia )
		throws DaoException
	{

		if ( Check.isNotNull( umaLinhaVigencia ) )
		{
			final List<Secao> secoes = umaLinhaVigencia.getSecoes();
			final TarifaDAO tarifaDAO = getTarifaDAO();
			final List<Tarifa> tarifasDeSecoes = tarifaDAO.buscaTarifasDeSecoes( secoes );
			for ( final Tarifa tarifa : tarifasDeSecoes )
			{
				tarifaDAO.deleteFisicamente( tarifa );
			}// for

			final LinhaDAO linhaDAO = getLinhaDAO();
			linhaDAO.deleteLinhaVigencia( umaLinhaVigencia );
		}// for

	}// if

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataInicioVigencia
	 * @param idLinha
	 * @param codigo
	 * @throws DaoException
	 */
	private void deletarTodasLinhasVigenciaPosteriores(
		final Date dataInicioVigencia,
		final Long idLinha,
		final String codigo )
		throws DaoException
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		final List<LinhaVigencia> linhaVigenciaList = linhaDAO.buscaLinhasVigenciaPosteriores(
			dataInicioVigencia,
			idLinha,
			codigo );

		if ( !linhaVigenciaList.isEmpty() )
		{

			final TarifaDAO tarifaDAO = getTarifaDAO();
			for ( final LinhaVigencia linhaVigencia : linhaVigenciaList )
			{
				final List<Secao> secoes = linhaVigencia.getSecoes();
				final List<Tarifa> tarifasDeSecoes = tarifaDAO.buscaTarifasDeSecoes( secoes );
				for ( final Tarifa tarifa : tarifasDeSecoes )
				{
					tarifaDAO.deleteFisicamente( tarifa );
				}// for

				linhaDAO.deleteLinhaVigencia( linhaVigencia );
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
	public void delete( final Linha entity )
		throws BusinessException
	{
		final Linha linhaExcluida = get( entity.getId() );

		if ( Check.isNull( linhaExcluida ) )
		{
			return;
		}

		if ( Check.isNotNull( linhaExcluida.getLinhaVigente() ) )
		{

			final LinhaDAO linhaDAO = getLinhaDAO();
			final LinhaVigencia linhaFutura = linhaDAO.buscarLinhaVigenciaFutura( linhaExcluida );
			final List<Secao> secoes = linhaExcluida.getLinhaVigente().getSecoes();
			if ( Check.isNotNull( secoes )
				&& Check.isNotNull( linhaFutura )
				&& Check.isNotNull( linhaFutura.getSecoes() ) )
			{
				secoes.addAll( linhaFutura.getSecoes() );
			}

			final List<Tarifa> listTarifas = getTarifaDAO().buscaTarifasFuturasEAtual( secoes );

			if ( Check.isNotNull( listTarifas ) )
			{
				for ( final Tarifa tarifa : listTarifas )
				{
					try
					{
						getTarifaDAO().delete( tarifa );
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
		super.delete( entity );
	}

	/**
	 * Existe linha ativa usando tipo linha.
	 *
	 * @param idTipoLinha
	 *            the id tipo linha
	 * @return true, if successful
	 */
	public boolean existeLinhaAtivaUsandoTipoLinha( final Long idTipoLinha )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.existeLinhaAtivaUsandoTipoLinha( idTipoLinha );
	}

	/**
	 * Existe linha ativa usando tipo veiculo.
	 *
	 * @param idTipoVeiculo
	 *            the id tipo veiculo
	 * @return true, if successful
	 */
	public boolean existeLinhaAtivaUsandoTipoVeiculo( final Long idTipoVeiculo )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.existeLinhaAtivaUsandoTipoVeiculo( idTipoVeiculo );
	}

	/**
	 * Find secao by code.
	 *
	 * @param code
	 *            the code
	 * @return the secao
	 */
	public Secao findSecaoByCode( final String code )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.findSecaobyCode( code );
	}

	/**
	 * Gera log.
	 *
	 * @param linhaVg
	 *            the linha vg
	 * @param tipoLog
	 *            the tipo log
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void geraLog( final List<LinhaVigencia> linhaVg, final int tipoLog )
		throws IOException
	{
		geraLog( linhaVg, tipoLog, null );
	}

	/**
	 * <p>
	 * FBW-135 Registra no log
	 * </p>
	 * .
	 *
	 * @author marcio.dias
	 * @param linhaVg
	 *            the linha vg
	 * @param tipoLog
	 *            the tipo log
	 * @param linhaArquivo
	 *            the linha arquivo
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void geraLog( final List<LinhaVigencia> linhaVg, final int tipoLog, final List<String> linhaArquivo )
		throws IOException
	{

		final DynamicExcel excel = new DynamicExcel();
		final Sheet sheet = excel.addSheet();
		final Usuario usuario = ( Usuario ) getUser();
		final StringBuffer nome = new StringBuffer();
		Row row;
		String codLinha = new String();

		// nome do arquivo

		nome.append( "linhaImportada-" );
		// cabecalho
		row = sheet.addRow().centralize();
		row.addCell( 1 ).useBold().setText( "CodLinha" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell( 2 ).useBold().setText( "Data Início" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		row.addCell( 3 ).useBold().setText( "Status" ).setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );

		if ( ( tipoLog == 1 ) || ( tipoLog == 5 ) )
		{
			row
				.addCell( 4 )
				.useBold()
				.setText( "Nome da Empresa" )
				.setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		}
		if ( ( linhaArquivo != null ) && ( linhaArquivo.size() > 0 ) )
		{
			row
				.addCell( 5 )
				.useBold()
				.setText( "Linha do Arquivo" )
				.setBackgroundColor( new HSSFColor.GREY_25_PERCENT() );
		}
		int cont = 0;
		for ( final LinhaVigencia lhLinhaVg : linhaVg )
		{
			// pula linha
			row = sheet.addRow().centralize();
			codLinha = lhLinhaVg.getCodigo();

			row.addCell( 1 ).setText( codLinha );
			row.addCell( 2 ).setText( lhLinhaVg.getDataInicioFormated() );
			final StatusLinha statusLinha = lhLinhaVg.getStatus();
			row.addCell( 3 ).setText( statusLinha.getNomeFormatado() );

			if ( ( tipoLog == 1 ) || ( tipoLog == 5 ) )
			{
				final Empresa empresa = lhLinhaVg.getEmpresa();
				row.addCell( 4 ).setText( empresa.getNome() );
			}

			if ( ( linhaArquivo != null ) && ( linhaArquivo.size() > 0 ) )
			{
				row.addCell( 5 ).setText( linhaArquivo.get( cont ) );
			}
			cont++;

		}

		nome.append( codLinha );
		nome.append( "_" );
		nome.append( usuario.getNome() );

		nome.append( "-TIPO" + tipoLog );

		// nome do arquivo
		nome.append( "_" );
		nome.append( new SimpleDateFormat( "yyyy-MM-dd-H-m-s" ).format( new Date() ) );
		nome.append( ".xls" );

		final ConfiguracaoBusiness configuracaoBusiness = getConfiguracaoBusiness();
		excel.generate( configuracaoBusiness.buscarDiretorioXLS() + nome.toString() );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the configuracaoBusiness.
	 * @see #configuracaoBusiness
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
		final String codigo = linhaVigencia.getCodigo();
		final LinhaDAO linhaDAO = getLinhaDAO();
		final LinhaVigencia linhaVigenciaAtivaByCode = linhaDAO.getLinhaVigenciaAtivaByCode( codigo );
		final Date dataPrimeiraLinhaVigencia = linhaDAO.buscarDataPrimeiraLinhaVigencia(
			linhaVigenciaAtivaByCode.getEmpresa(),
			codigo );
		return dataPrimeiraLinhaVigencia;

	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the empresaBusiness.
	 * @see #empresaBusiness
	 */
	private EmpresaBusiness getEmpresaBusiness()
	{
		return this.empresaBusiness;
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
	 * Gets the ids tipos linha tipos veiculo.
	 *
	 * @param tipoDeVeiculos
	 *            the tipo de veiculos
	 * @return the ids tipos linha tipos veiculo
	 */
	public Map<Long, List<Long>> getIdsTiposLinhaTiposVeiculo( final List<TipoDeVeiculo> tipoDeVeiculos )
	{
		final Map<Long, List<Long>> mapIds = new HashMap<Long, List<Long>>();
		for ( final TipoDeVeiculo tipoDeVeiculo : tipoDeVeiculos )
		{
			// TODO Melhorar no form.jsp a associacao entre tipoDeLinha e
			// tipoDeVeiculo
			final String[] ids = tipoDeVeiculo.getId().toString().split( STR_SEPARA_IDS );
			final long idTipoVeiculo = Long.parseLong( ids[0] );
			final long idTipoLinha = Long.parseLong( ids[1] );
			if ( mapIds.containsKey( idTipoLinha ) )
			{
				mapIds.get( idTipoLinha ).add( idTipoVeiculo );
			}
			else
			{
				final List<Long> idsVeiculos = new ArrayList<Long>();
				idsVeiculos.add( idTipoVeiculo );
				mapIds.put( idTipoLinha, idsVeiculos );
			}
		}
		return mapIds;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @return Returns the linhaDAO.
	 * @see #linhaDAO
	 */
	private LinhaDAO getLinhaDAO()
	{
		return ( LinhaDAO ) this.dao;
	}// func

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-505
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

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-505
	 * </p>
	 *
	 * @param dataInicio
	 * @param empresaId
	 * @return
	 */
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
	 * <p>
	 * FBW-505
	 * </p>
	 *
	 * @param empresa
	 * @param mesAnoInicioVigencia
	 * @return Retorna uma String com todos o BOMs que serão apagados.
	 * @throws BusinessException
	 * @throws ParseException
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
	 * .
	 *
	 * @return Returns the tarifaDAO.
	 * @see #tarifaDAO
	 */
	private TarifaDAO getTarifaDAO()
	{
		return this.tarifaDAO;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the tipoDeLinhaBusiness.
	 * @see #tipoDeLinhaBusiness
	 */
	private TipoDeLinhaBusiness getTipoDeLinhaBusiness()
	{
		return this.tipoDeLinhaBusiness;
	}

	/**
	 * Historico.
	 *
	 * @param entity
	 *            the entity
	 * @return the list
	 */
	public List<LinhaVigencia> historico( final Linha entity )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.historico( entity );
	}

	/**
	 * <p>
	 * FBW-135 Importa as linhas do arquivo Excel para o caso de tarifas retroativas
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
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 * @throws CloneFailException
	 *             the clone not supported exception
	 * @throws DaoException
	 *             the dao exception
	 */
	public int importExcelLh( final File arquivo )
		throws BusinessException,
			ParseException,
			IOException,
			CloneFailException,
			DaoException
	{
		final ImportExcel excel = new ImportExcel();
		final List<ImportDTO> listImportDTOs = excel.loadArquivo( arquivo );
		int numLinhasAtualizadas = 0;
		int numLinhaArquivo = 1;
		final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yy" );
		final List<LinhaVigencia> errosLVg = new ArrayList<LinhaVigencia>();
		final List<LinhaVigencia> lVgNaoImportadas = new ArrayList<LinhaVigencia>();
		final List<String> linhaArquivo = new ArrayList<String>();

		if ( Check.isNotNull( listImportDTOs ) && Check.isNotEmpty( listImportDTOs ) )
		{
			validateImportExcel( listImportDTOs );

			// FBW-234
			final Map<String, InterventionDataLine> interventionDataMap = new HashMap<String, InterventionDataLine>();

			final LinhaDAO linhaDAO = getLinhaDAO();
			final TarifaDAO tarifaDAO = getTarifaDAO();
			for ( final ImportDTO i : listImportDTOs )
			{
				numLinhaArquivo++;
				try
				{
					/****************************
					 * INICIO monta a linhaVg para cada linha do arquivo
					 ****************************************/
					final List<String> linhasImportadas = i.getValues();
					String cod_secao = linhasImportadas.get( INDICE_EXCEL_SECAO );
					if ( cod_secao.length() < 2 )
					{
						cod_secao = "0" + cod_secao;
					}
					final String cod_linha = linhasImportadas.get( INDICE_EXCEL_COD_LINHA );
					final LinhaVigencia novaLinhaVg = new LinhaVigencia();
					novaLinhaVg.setCodigo( cod_linha );
					novaLinhaVg.setStatus( StatusLinha.ATIVA );

					final String dateCreationStr = linhasImportadas.get( INDICE_EXCEL_DT_CRIACAO );
					final Date dateCreation;
					if ( !dateCreationStr.isEmpty() )
					{
						dateCreation = format.parse( dateCreationStr );
						novaLinhaVg.setDataCriacao( dateCreation );
					}
					else
					{
						dateCreation = null;

						// registra no log
						try
						{
							errosLVg.add( novaLinhaVg );
							geraLog( errosLVg, 3 );
						}
						catch ( final IOException e )
						{
							throw new BusinessException( e );
						}
						throw new BusinessException( "A data de criação ou de início da linha "
							+ cod_linha
							+ " não pode ser em branco! Nada foi importado!" );
					}
					final String dateStartStr = linhasImportadas.get( INDICE_EXCEL_DT_INICIO );
					final Date dateStart;
					if ( !dateStartStr.isEmpty() )
					{
						dateStart = format.parse( dateStartStr );
						novaLinhaVg.setDataInicio( dateStart );
					}
					else
					{
						dateStart = null;
						// registra no log
						try
						{
							errosLVg.add( novaLinhaVg );
							geraLog( errosLVg, 3 );
						}
						catch ( final IOException e )
						{
							throw new BusinessException( e );
						}
						throw new BusinessException( "A data de início da linha "
							+ cod_linha
							+ " não pode ser em branco! Nenhum registro foi importado!" );
					}

					final String nomeEmpresa = linhasImportadas.get( INDICE_EXCEL_NOME_EMPRESA );
					final String empresaCode = linhasImportadas.get( INDICE_EXCEL_REGISTRO );
					final EmpresaBusiness empresaBusiness = getEmpresaBusiness();
					final List<Empresa> empresaList = empresaBusiness.buscaEmpresasByCodigo( empresaCode );
					if ( empresaList.size() == 0 )
					{
						// registra no log
						try
						{
							final Empresa empresaTmp = new Empresa();
							empresaTmp.setNome( nomeEmpresa );
							novaLinhaVg.setEmpresa( empresaTmp );
							errosLVg.add( novaLinhaVg );
							geraLog( errosLVg, 1 );
						}
						catch ( final IOException e )
						{
							throw new BusinessException( e );
						}

						throw new BusinessException( " Não foi possível encontrar a empresa! Verifique a linha "
							+ numLinhaArquivo
							+ " do arquivo. Nenhum registro foi importado ou alterado." );
					}
					final Empresa empresa = empresaList.get( 0 );
					novaLinhaVg.setEmpresa( empresa );
					final String lineTypeCode = novaLinhaVg
						.getCodigo()
						.substring( novaLinhaVg.getCodigo().length() - 1 );
					if ( "0".equals( lineTypeCode ) )
					{
						novaLinhaVg.setTipoLigacao( "Linha" );
					}
					else
					{
						novaLinhaVg.setTipoLigacao( "Serviço Complementar" );
					}
					novaLinhaVg.setActive( Boolean.TRUE );
					novaLinhaVg.setNumeroLinha( linhasImportadas.get( INDICE_EXCEL_NUM_LH ) );
					if ( linhasImportadas.get( INDICE_EXCEL_PISO_I ).isEmpty() )
					{
						novaLinhaVg.setPiso1AB( 0.0 );
						novaLinhaVg.setPiso1BA( 0.0 );
					}
					else
					{
						novaLinhaVg.setPiso1AB( new Double( linhasImportadas.get( INDICE_EXCEL_PISO_I ).replace(
							",",
							"." ) ) );
						novaLinhaVg.setPiso1BA( new Double( linhasImportadas.get( INDICE_EXCEL_PISO_I ).replace(
							",",
							"." ) ) );
					}
					if ( linhasImportadas.get( INDICE_EXCEL_PISO_2 ).isEmpty() )
					{
						novaLinhaVg.setPiso2AB( 0.0 );
						novaLinhaVg.setPiso2BA( 0.0 );
					}
					else
					{
						novaLinhaVg.setPiso2AB( new Double( linhasImportadas.get( INDICE_EXCEL_PISO_2 ).replace(
							",",
							"." ) ) );
						novaLinhaVg.setPiso2BA( new Double( linhasImportadas.get( INDICE_EXCEL_PISO_2 ).replace(
							",",
							"." ) ) );
					}

					/****************************
					 * FIM monta a linhaVg para cada linha do arquivo
					 ****************************************/

					// ***************************************
					// Monta a linha
					final Linha novaLinha = new Linha();
					novaLinha.setActive( true );

					// ***************************************
					// Monta a linha_vigencia_tipos_linha
					final LinhaVigenciaTipoDeLinha lvTipoLinha = new LinhaVigenciaTipoDeLinha();
					final String lineTypeValue = linhasImportadas.get( INDICE_EXCEL_CARACTERISTICA ).trim();
					final TipoDeLinha tipoDeLinha = this.tipoDeLinhaBusiness.getTipoDeLinhaBySigla( lineTypeValue );
					lvTipoLinha.setTipoDeLinha( tipoDeLinha );

					// ***************************************
					// Monta a tarifa
					final Tarifa tarifa = new Tarifa();
					final BigDecimal valor = new BigDecimal( linhasImportadas.get( INDICE_EXCEL_VALOR ).replace(
						",",
						"." ) );
					tarifa.setValor( valor );
					tarifa.setInicioVigencia( novaLinhaVg.getDataInicio() );
					tarifa.setActive( Boolean.TRUE );
					tarifa.setMotivoCriacao( MotivoCriacaoTarifa.ATUALIZACAO_LINHA );

					// se não existe, terá que cria-la!
					if ( !linhaDAO.existeLinhaPorCodigoAtiva( cod_linha ) )
					{

						if ( !( linhasImportadas.get( INDICE_EXCEL_SECAO ).equals( "0" ) )
							&& !( linhasImportadas.get( INDICE_EXCEL_SECAO ).equals( "00" ) ) )
						{
							// registra no log
							try
							{
								errosLVg.add( novaLinhaVg );
								geraLog( errosLVg, 5 );
							}
							catch ( final IOException e )
							{
								throw new BusinessException( e );
							}
							throw new BusinessException(
								" Uma nova linha deve ter obrigatoriamente a seção 00! Verifique a linha "
									+ numLinhaArquivo
									+ " do arquivo. Nenhum registro foi importado ou alterado." );
						}

						try
						{
							final String sectionName = linhasImportadas.get( INDICE_EXCEL_NOME_SC );
							final String[] initialAndFinalStops = sectionName.split( "-" );
							novaLinhaVg.setPontoInicial( initialAndFinalStops[0].trim() );
							novaLinhaVg.setPontoFinal( initialAndFinalStops[1].trim() );

							novaLinhaVg.setObservacao( linhasImportadas.get( INDICE_EXCEL_OBS ) );
						}
						catch ( final ArrayIndexOutOfBoundsException e )
						{
							if ( LOG.isErrorEnabled() )
							{
								LOG.error( e, e );
							}// if
							try
							{
								errosLVg.add( novaLinhaVg );
								geraLog( errosLVg, 4 );
							}
							catch ( final IOException e2 )
							{
								if ( LOG.isErrorEnabled() )
								{
									LOG.error( e2, e2 );
								}// if
							}
							throw new BusinessException( " Há um problema com o nome da seção! Seção \""
								+ linhasImportadas.get( INDICE_EXCEL_NOME_SC )
								+ "\". Nenhum registro foi importado ou alterado.", e );
						}

						// salva a linha
						super.save( novaLinha );

						// salva a linhaVg, linha_vigencia_tipos_linha e Secao00
						novaLinhaVg.setLinha( novaLinha );
						novaLinha.setLinhaVigente( novaLinhaVg );

						// linha_vigencia_tipos_linha
						lvTipoLinha.setLinhaVigencia( novaLinha.getLinhaVigente() );
						final List<LinhaVigenciaTipoDeLinha> lvTiposLinha = new ArrayList<LinhaVigenciaTipoDeLinha>();
						lvTiposLinha.add( lvTipoLinha );
						novaLinha.getLinhaVigente().setLinhasVigenciaTiposDeLinha( lvTiposLinha );

						// secao00
						final Secao secao00 = criaSecao00ParaLinha( novaLinha.getLinhaVigente() );
						secao00.setDataCriacao( novaLinha.getLinhaVigente().getDataInicio() );
						if ( Check.isNull( novaLinha.getLinhaVigente().getSecoes() ) )
						{
							final List<Secao> secoes = new ArrayList<Secao>();
							secoes.add( secao00 );
							novaLinha.getLinhaVigente().setSecoes( secoes );
						}
						else
						{
							novaLinha.getLinhaVigente().getSecoes().add( secao00 );
						}

						// salva
						linhaDAO.salvarLinhaVigencia( novaLinha.getLinhaVigente() );

						// salva a tarifa
						tarifa.setSecao( novaLinha.getLinhaVigente().getSecoes().get( 0 ) );
						tarifaDAO.save( tarifa );

						// apaga os BOMs
						final Date datatemp = novaLinha.getLinhaVigente().getDataInicio();
						final Calendar cal = Calendar.getInstance();
						cal.setTime( datatemp );
						final String mesAno = ( cal.get( Calendar.MONTH ) + 1 ) + "/" + cal.get( Calendar.YEAR );
						apagaBom( novaLinha.getLinhaVigente().getEmpresa(), mesAno );

						numLinhasAtualizadas++;

					}
					else
					// A linha já existe!
					{
						final String obs = linhasImportadas.get( INDICE_EXCEL_OBS ).toUpperCase();
						final int statusDesativada = linhasImportadas
							.get( INDICE_EXCEL_STATUS )
							.toUpperCase()
							.compareTo( "DESATIVADA" );
						Secao cancelarSecao = null;

						final String dateEndStr = linhasImportadas.get( INDICE_EXCEL_DT_TERMINO );
						final Date dateEnd;
						if ( dateEndStr.isEmpty() )
						{

							dateEnd = null;
						}
						else
						{
							dateEnd = format.parse( dateEndStr );
						}

						// FBW-234
						if ( obs.indexOf( "INTERVENÇÃO" ) >= 0 )
						{
							if ( statusDesativada == 0 )
							{
								final InterventionDataLine interventionDataLineCandidate = interventionDataMap
									.get( cod_linha );
								if ( interventionDataLineCandidate != null )
								{
									continue;
								}// if

								final InterventionDataLine interventionDataLine = new InterventionDataLine();

								interventionDataLine.setLineCode( cod_linha );
								interventionDataLine.setDateEnd( dateEnd );
								interventionDataLine.setObs( obs );
								interventionDataLine
									.setInterventionDataSessionMap( new HashMap<String, InterventionDataSession>() );

								interventionDataMap.put( cod_linha, interventionDataLine );
								final List<Empresa> empresaOld = this.empresaBusiness
									.buscaEmpresasByNomeExato( nomeEmpresa );
								final String mesAno = DateUtil.getMonthAndYearAsString( dateEnd );
								if ( !empresaOld.isEmpty() )
								{
									apagaBom( empresaOld.get( 0 ), mesAno );

								}// if

							}

							// FBW-234
							// troca a empresa da linha; reativa a linha; reativa a secao;
							// reativa e atualiza a tarifa;
							else
							{
								// TODO FIXME verificar que não é nulo (acontece se ativação
								// aparecer
								// antes de cancelamento)
								final InterventionDataLine interventionDataLine = interventionDataMap.get( cod_linha );

								final Map<String, InterventionDataSession> interventionDataSessionMap = interventionDataLine
									.getInterventionDataSessionMap();

								// TODO FIXME verificar se cod_secao já exites na map (acontece se a
								// sessao está repetida para a mesma linha)

								if ( interventionDataSessionMap.isEmpty() )
								{
									// primeira secao da linha

									interventionDataLine.setEmpresaNew( empresa );
									interventionDataLine.setDateInit( dateStart );
								}// if

								final InterventionDataSession interventionDataSession = new InterventionDataSession();
								interventionDataSession.setDateStart( dateStart );
								interventionDataSession.setTarifaValue( valor );
								interventionDataSession.setSessionCode( cod_secao );

								interventionDataSessionMap.put( cod_secao, interventionDataSession );

								final String mesAno = DateUtil.getMonthAndYearAsString( dateStart );
								apagaBom( empresa, mesAno );
							}// else INTERVENÇÃO

						}// if
						else
						{
							final LinhaVigencia tempLinhaVg = linhaDAO.getLinhaVigenciaByCodeTermino( cod_linha );
							Boolean achouSecao = Boolean.FALSE;

							if ( Check.isNull( tempLinhaVg ) )
							{
								lVgNaoImportadas.add( novaLinhaVg );
								linhaArquivo.add( numLinhaArquivo + "" );
								continue;// nao faz nada, significa que já esta cancelada!
							}

							for ( final Secao secao : tempLinhaVg.getSecoes() )
							{
								if ( ( secao.getCodigo().compareTo( cod_secao ) == 0 ) )
								{
									achouSecao = Boolean.TRUE;// ja existe a secao
									cancelarSecao = secao;
									break;
								}
							}

							if ( !achouSecao && ( statusDesativada == 0 ) )
							{
								this.naoEncontrouSecaoList.add( "Seção "
									+ cod_secao
									+ " da linha "
									+ tempLinhaVg.getCodigo() );
								continue; // Não encontrou a seção da linha corrente, não faz nada.
							}// if

							if ( achouSecao )
							{

								// cancela a secao
								if ( ( statusDesativada == 0 )
									&& ( tempLinhaVg.getStatus().compareTo( StatusLinha.ATIVA ) == 0 ) )
								{

									if ( ( dateEndStr == null ) || dateEndStr.isEmpty() )
									{
										// registra no log
										try
										{
											errosLVg.add( novaLinhaVg );
											geraLog( errosLVg, 2 );
										}
										catch ( final IOException e )
										{
											throw new BusinessException( e );
										}
										throw new BusinessException(
											" Não há data de término para uma linha DESATIVADA! Verifique a linha "
												+ numLinhaArquivo
												+ " do arquivo. ERRO TIPO 2! Nenhum registro foi importado ou alterado." );
									}

									assert cancelarSecao != null;

									// atualiza linhaVg
									// this.linhaDAO.atualizarLinhaVigencia( tempLinhaVg );
									// FBW-153 - comentado acima para implementar o abaixo.
									// a seção que será desativada e nao mais toda uma linha!
									final Date tempDateTermino = dateEnd;
									cancelarSecao.setDataTermino( tempDateTermino );

									// FBW-190 -- foi comentado, motivo no comment da issue.
									// cancelarSecao.setActive( false );
									linhaDAO.cancelaSecao( cancelarSecao );

									// atualiza tarifa
									Tarifa tarifatemp = new Tarifa();
									tarifatemp = tarifaDAO.buscarTarifaCodLinhaSecao( cod_linha, cod_secao ).get( 0 );
									tarifatemp.setFimVigencia( tempDateTermino );
									tarifaDAO.update( tarifatemp );

									// FBW-190
									// Se uma linha não tem seçoes, cancela a linha.
									final LinhaVigencia linhaVigenciaByCode = linhaDAO
										.getLinhaVigenciaByCodeTermino( cod_linha );

									temSecaoAtiva( linhaVigenciaByCode, tempDateTermino, obs );
								}
								else
								{
									lVgNaoImportadas.add( novaLinhaVg );
									linhaArquivo.add( numLinhaArquivo + "" );
									continue;// nao faz nada, segnifica que é o msm arquivo, nada
												// novo.
								}
							}
							else
							{
								final String sectionName = linhasImportadas.get( INDICE_EXCEL_NOME_SC );
								final String[] initialAndFinalStops = sectionName.split( "-" );
								final Secao secaoNova = criaSecao00ParaLinha( tempLinhaVg );
								secaoNova.setDataCriacao( dateStart );
								secaoNova.setCodigo( cod_secao );
								secaoNova.setPontoInicial( initialAndFinalStops[0] );
								secaoNova.setPontoFinal( initialAndFinalStops[1] );
								tempLinhaVg.getSecoes().add( secaoNova );

								// atualiza a linha e secao
								linhaDAO.atualizarLinhaVigencia( tempLinhaVg );

								// salva a tarifa
								tarifa.setSecao( secaoNova );
								tarifaDAO.save( tarifa );

							}

							numLinhasAtualizadas++;
							Date datatemp = null;
							// apaga os BOMs
							if ( achouSecao )// se eh para cancelar uma seção
							{
								datatemp = dateEnd;
							}
							else
							// foi adicionada novas seções
							{
								datatemp = novaLinhaVg.getDataInicio();
							}

							final Calendar cal = Calendar.getInstance();
							cal.setTime( datatemp );
							final String mesAno = ( cal.get( Calendar.MONTH ) + 1 ) + "/" + cal.get( Calendar.YEAR );
							apagaBom( tempLinhaVg.getEmpresa(), mesAno );

						}
					}

				}
				catch ( final DaoException e )
				{
					throw new BusinessException( e );
				}
			}

			final int interventionDataMapSize = interventionDataMap.size();

			// FBW-234
			if ( interventionDataMapSize > 0 )
			{
				final Set<Entry<String, InterventionDataLine>> interventionDataMapEntrySet = interventionDataMap
					.entrySet();

				final List<LinhaVigencia> linhaVigenciaOldToSave = new ArrayList<LinhaVigencia>();
				final List<LinhaVigencia> linhaVigenciaNewToSave = new ArrayList<LinhaVigencia>();
				final List<Tarifa> tarifasOldToSave = new ArrayList<Tarifa>();
				final List<Tarifa> tarifasNewToSave = new ArrayList<Tarifa>();

				for ( final Entry<String, InterventionDataLine> entry : interventionDataMapEntrySet )
				{
					final String lineCode = entry.getKey();
					final InterventionDataLine interventionDataLine = entry.getValue();

					final LinhaVigencia linhaVigenciaOld = linhaDAO.getLinhaVigenciaByCodeTermino( lineCode );
					final LinhaVigencia linhaVigenciaNew = linhaVigenciaOld.clone();
					clonaLinhaVigenciaTiposLinha( linhaVigenciaOld, linhaVigenciaNew );
					// TODO FIXME verificar DataTermino da linhaVigenciaOld tem que ser nula.

					linhaVigenciaOldToSave.add( linhaVigenciaOld );
					linhaVigenciaNewToSave.add( linhaVigenciaNew );

					final Date dateEnd = interventionDataLine.getDateEnd();
					final String obs = interventionDataLine.getObs();
					final Empresa empresaNew = interventionDataLine.getEmpresaNew();
					final Date dateInit = interventionDataLine.getDateInit();
					final Map<String, InterventionDataSession> interventionDataSessionMap = interventionDataLine
						.getInterventionDataSessionMap();

					linhaVigenciaOld.setDataTermino( dateEnd );
					linhaVigenciaOld.setObservacao( obs );

					linhaVigenciaNew.setEmpresa( empresaNew );
					linhaVigenciaNew.setDataInicio( dateInit );

					final List<Secao> secoesOld = linhaVigenciaOld.getSecoes();
					final List<Secao> secoesNew = linhaVigenciaNew.getSecoes();

					int ix = -1;
					for ( final Secao secaoOld : secoesOld )
					{
						ix++;

						final Secao secaoNew = secoesNew.get( ix );

						secaoOld.setDataTermino( dateEnd );
						secaoNew.setDataCriacao( dateInit );
						final String sessionCode = secaoOld.getCodigo();

						final InterventionDataSession interventionDataSession = interventionDataSessionMap
							.get( sessionCode );
						if ( interventionDataSession == null )
						{
							final String msg = String
								.format(
									"Não existe nenhuma linha no arquivo referente a ativação da seção '%s' da linha '%s'.",
									sessionCode,
									lineCode );
							throw new IllegalArgumentException( msg );
						}// if

						final Date dateStart = interventionDataSession.getDateStart();
						final BigDecimal tarifaValue = interventionDataSession.getTarifaValue();

						final Tarifa taricaAtualOld = tarifaDAO.buscaTarifaAtual( secaoOld );
						final Tarifa tarifaAtualNew = taricaAtualOld.clone();

						tarifaAtualNew.setSecao( secaoNew );
						tarifaAtualNew.setValor( tarifaValue );
						tarifaAtualNew.setIdTarifaAntiga( null );
						tarifaAtualNew.setInicioVigencia( dateStart );

						taricaAtualOld.setFimVigencia( dateEnd );

						tarifasOldToSave.add( taricaAtualOld );
						tarifasNewToSave.add( tarifaAtualNew );

					}// for
				}// for

				numLinhasAtualizadas += interventionDataMapSize;

				for ( final LinhaVigencia linhaVigencia : linhaVigenciaOldToSave )
				{
					linhaDAO.salvarLinhaVigencia( linhaVigencia );
				}// for
				for ( final Tarifa tarifa : tarifasOldToSave )
				{
					tarifaDAO.save( tarifa );
				}// for
				for ( final LinhaVigencia linhaVigencia : linhaVigenciaNewToSave )
				{
					linhaDAO.salvarLinhaVigencia( linhaVigencia );
				}// for
				for ( final Tarifa tarifa : tarifasNewToSave )
				{
					tarifaDAO.save( tarifa );
				}// for

			}// if

		}

		if ( lVgNaoImportadas.size() > 0 )
		{
			if ( linhaArquivo.size() > 0 )
			{
				geraLog( lVgNaoImportadas, 5, linhaArquivo );
			}
			else
			{
				geraLog( lVgNaoImportadas, 5 );
			}
		}
		return numLinhasAtualizadas;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param linhaVigencia
	 * @return
	 */
	public boolean isDataInicioLinhaAnteriorHoje( final LinhaVigencia linhaVigencia )
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( Check.isNotNull( linhaVigencia ) )
		{
			if ( linhaVigencia.getDataInicio().before( new Date() ) && !perfil.isEmpresa() )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if is data inicio linha posterior hoje.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return true, if is data inicio linha posterior hoje
	 */
	public boolean isDataInicioLinhaPosteriorHoje( final LinhaVigencia linhaVigencia )
	{
		if ( Check.isNotNull( linhaVigencia ) )
		{
			if ( linhaVigencia.getDataInicio().after( new Date() ) )
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param umaLinha
	 * @return Retorna true se a data de início da edição for anterior a data de início do primeiro
	 *         registro da linhaVigencia ou retorna false se não for.
	 */
	public boolean isDataRetroativaInvalida( final Linha umaLinha )
	{
		final LinhaVigencia linhaVigencia = umaLinha.getLinhaVigente();
		final Date dataPrimeiraLinhaVigencia = getDataPrimeiraLinhaVigencia( umaLinha );
		final Date dataInicio = linhaVigencia.getDataInicio();
		final boolean result = dataPrimeiraLinhaVigencia.after( dataInicio );
		return result;

	}

	/**
	 * Ja existe tarifa.
	 *
	 * @param secao
	 *            the secao
	 * @return true, if successful
	 */
	private boolean jaExisteTarifa( final Secao secao )
	{
		if ( ( getTarifaDAO().buscaTarifaAtual( secao ) != null ) || ( getTarifaDAO().tarifaFutura( secao ) != null ) )
		{
			return true;
		}
		return false;
	}

	/**
	 * Linha ja existe.
	 *
	 * @param linha
	 *            the linha
	 * @return true, if successful
	 */
	public boolean linhaJaExiste( final Linha linha )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		final LinhaVigencia linhaVigente = linha.getLinhaVigente();
		return linhaDAO.linhaJaExiste( linhaVigente.getCodigo(), linhaVigente.getEmpresa().getId() );
	}

	/**
	 * Linha ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param id
	 *            the id
	 * @param idEmpresa
	 *            the id empresa
	 * @return true, if successful
	 */
	public boolean linhaJaExiste( final String codigo, final Long id, final Long idEmpresa )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.linhaJaExite( codigo, id, idEmpresa );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param linhaVigencia
	 * @param justificativa
	 * @throws DaoException
	 */
	private void logarEdicaoRetroativa( final LinhaVigencia linhaVigencia, final String justificativa )
		throws DaoException
	{
		// FBW-487
		// FBW-483

		if ( Check.isNotNull( linhaVigencia ) )
		{
			linhaVigencia.setEdicaoRetroativaJustificativa( justificativa );
		}// if

		final LinhaDAO linhaDAO = getLinhaDAO();
		linhaDAO.salvarLinhaVigencia( linhaVigencia );
	}// func

	/**
	 * Pesquisar linhas.
	 *
	 * @param empresa
	 *            the empresa
	 * @return the list
	 */
	public List<Linha> pesquisarLinhas( final Empresa empresa )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.pesquisarLinhas( empresa );
	}

	/**
	 * Pesquisar secoes por linha vigente secoes.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return the list
	 */
	public List<Secao> pesquisarSecoesPorLinhaVigenteSecoes( final LinhaVigencia linhaVigencia )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.pesquisarSecoesPorLinhaVigenteSecoes( linhaVigencia );
	}

	/**
	 * Pesquisar secoes por linha vigente secoes.
	 *
	 * @param linhaVigenciaId
	 *            the linha vigencia id
	 * @return the list
	 */
	public List<Secao> pesquisarSecoesPorLinhaVigenteSecoes( final String linhaVigenciaId )
	{
		final LinhaVigencia linhaVigencia = new LinhaVigencia();
		linhaVigencia.setId( Long.valueOf( linhaVigenciaId ) );
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.pesquisarSecoesPorLinhaVigenteSecoes( linhaVigencia );
	}

	/**
	 * Pesquisar secoes sem tarifa por linha vigente secoes.
	 *
	 * @param linhaVigenciaId
	 *            the linha vigencia id
	 * @return the list
	 */
	public List<Secao> pesquisarSecoesSemTarifaPorLinhaVigenteSecoes( final String linhaVigenciaId )
	{
		final List<Secao> secoes = pesquisarSecoesPorLinhaVigenteSecoes( linhaVigenciaId );
		final List<Secao> secoesSemTarifa = new LinkedList<Secao>();

		for ( final Secao sec : secoes )
		{
			if ( !jaExisteTarifa( sec ) )
			{
				secoesSemTarifa.add( sec );
			}
		}

		return secoesSemTarifa;
	}

	/**
	 * Pesquisar tipos veiculo.
	 *
	 * @param linhaVigenciaId
	 *            the linha vigencia id
	 * @return the list
	 */
	public List<TipoDeVeiculo> pesquisarTiposVeiculo( final String linhaVigenciaId )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.pesquisarTiposVeiculo( linhaVigenciaId );
	}

	/**
	 * Removes the secao.
	 *
	 * @param s
	 *            the s
	 * @return true, if successful
	 */
	public boolean removeSecao( final Secao s )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.removeSecao( s );
	}

	/**
	 * Save.
	 *
	 * @param entity
	 *            the entity
	 * @param tiposLinha
	 *            the tipos linha
	 * @throws BusinessException
	 *             the business exception
	 */
	public void save( final Linha entity, final List<TipoDeLinha> tiposLinha )
		throws BusinessException
	{

		entity.setActive( Boolean.TRUE );
		super.save( entity );

		entity.getLinhaVigente().setLinha( entity );
		entity.getLinhaVigente().setActive( Boolean.TRUE );
		entity.getLinhaVigente().setDataCriacao( new Date() );

		if ( Check.isNotNull( tiposLinha ) )
		{
			final List<LinhaVigenciaTipoDeLinha> lvTiposLinha = new ArrayList<LinhaVigenciaTipoDeLinha>();
			final TipoDeLinhaBusiness tipoDeLinhaBusiness = getTipoDeLinhaBusiness();
			for ( final TipoDeLinha tipoDeLinha : tiposLinha )
			{
				final LinhaVigenciaTipoDeLinha lvTipoLinha = new LinhaVigenciaTipoDeLinha();
				lvTipoLinha.setLinhaVigencia( entity.getLinhaVigente() );
				lvTipoLinha.setTipoDeLinha( tipoDeLinhaBusiness.find( tipoDeLinha.getId() ) );
				lvTipoLinha.setActive( Boolean.TRUE );
				lvTiposLinha.add( lvTipoLinha );
			}
			entity.getLinhaVigente().setLinhasVigenciaTiposDeLinha( lvTiposLinha );
		}

		if ( Check.isNotNull( entity.getLinhaVigente().getSecoes() ) )
		{
			final Date dataCriacao = new Date();
			for ( final Secao secao : entity.getLinhaVigente().getSecoes() )
			{
				secao.setLinhaVigencia( entity.getLinhaVigente() );
				secao.setActive( Boolean.TRUE );
				secao.setDataCriacao( dataCriacao );
			}
		}

		final Secao secao00 = criaSecao00ParaLinha( entity.getLinhaVigente() );

		if ( Check.isNull( entity.getLinhaVigente().getSecoes() ) )
		{
			final List<Secao> secoes = new ArrayList<Secao>();
			secoes.add( secao00 );
			entity.getLinhaVigente().setSecoes( secoes );
		}
		else
		{
			entity.getLinhaVigente().getSecoes().add( secao00 );
		}

		final LinhaDAO linhaDAO = getLinhaDAO();
		linhaDAO.salvarLinhaVigencia( entity.getLinhaVigente() );
	}

	/**
	 * Secao ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param idLinha
	 *            the id linha
	 * @param idSesao
	 *            the id sesao
	 * @return true, if successful
	 */
	public boolean secaoJaExiste( final String codigo, final Long idLinha, final Long idSesao )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.secaoJaExiste( codigo, idLinha, idSesao );
	}

	/**
	 * Secao ja existe.
	 *
	 * @param codigo
	 *            the codigo
	 * @param codLinha
	 *            the cod linha
	 * @param codEmp
	 *            the cod emp
	 * @return true, if successful
	 */
	public boolean secaoJaExiste( final String codigo, final String codLinha, final String codEmp )
	{
		final LinhaDAO linhaDAO = getLinhaDAO();
		return linhaDAO.secaoJaExiste( codigo, codLinha, codEmp );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataInicioVigencia
	 * @param linhaVigenciaNova
	 */
	private void setDataCriacaoDeSecao( final Date dataInicioVigencia, final LinhaVigencia linhaVigenciaNova )
	{
		final List<Secao> secoes = linhaVigenciaNova.getSecoes();
		for ( final Secao secao : secoes )
		{
			secao.setDataCriacao( dataInicioVigencia );
		}// for
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param empresaBusiness
	 *            The empresaBusiness to set.
	 * @see #empresaBusiness
	 */
	private void setEmpresaBusiness( final EmpresaBusiness empresaBusiness )
	{
		this.empresaBusiness = empresaBusiness;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-505
	 * </p>
	 *
	 * @param empresaDAO
	 *            The empresaDAO to set.
	 * @see #empresaDAO
	 */
	public void setEmpresaDAO( final EmpresaDAO empresaDAO )
	{
		this.empresaDAO = empresaDAO;
	}

	/**
	 * <p>
	 * </p>
	 * .
	 *
	 * @param tarifaDAO
	 *            The tarifaDAO to set.
	 * @see #tarifaDAO
	 */
	private void setTarifaDAO( final TarifaDAO tarifaDAO )
	{
		this.tarifaDAO = tarifaDAO;
	}

	/**
	 * Tem secao00.
	 *
	 * @param linha
	 *            the linha
	 * @return true, if successful
	 */
	public boolean temSecao00( final LinhaVigencia linha )
	{
		final List<Secao> secoes = linha.getSecoes();

		if ( Check.isNotNull( secoes ) )
		{
			for ( final Secao secao : secoes )
			{
				if ( secao.getCodigo().equals( Secao.COD_SECAO_OBRIGATORIA ) )
				{
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * <p>
	 * Se a linha não possui seções ativas ela deve ser cancelada. FBW-190
	 * </p>
	 *
	 * @author marcio.dias
	 * @param tempLinhaVg
	 *            the temp linha vg
	 * @param tempDateTermino
	 *            the temp date termino
	 * @param observacao
	 *            the observacao
	 */
	private void temSecaoAtiva( final LinhaVigencia tempLinhaVg, final Date tempDateTermino, final String observacao )
	{
		boolean exiteSecaoAtiva = false;

		for ( final Secao secao : tempLinhaVg.getSecoes() )
		{
			if ( Check.isNull( ( secao.getDataTermino() ) ) )
			{
				exiteSecaoAtiva = true;// existe uma secao sem data de termino
				break;
			}
		}

		if ( !exiteSecaoAtiva )
		{
			tempLinhaVg.setStatus( StatusLinha.CANCELADA );
			tempLinhaVg.setObservacao( observacao );
			tempLinhaVg.setDataTermino( tempDateTermino );
			final LinhaDAO linhaDAO = getLinhaDAO();
			linhaDAO.atualizarLinhaVigencia( tempLinhaVg );
		}
	}

	/**
	 * Tem secao repetida.
	 *
	 * @param linha
	 *            the linha
	 * @return true, if successful
	 */
	public boolean temSecaoRepetida( final LinhaVigencia linha )
	{
		final List<Secao> secoes = linha.getSecoes();
		final Set<String> codigosSecoesInseridas = new HashSet<String>();

		if ( Check.isNotNull( secoes ) )
		{
			for ( final Secao secao : secoes )
			{
				if ( codigosSecoesInseridas.contains( secao.getCodigo() ) )
				{
					return true;
				}
				codigosSecoesInseridas.add( secao.getCodigo() );
			}
		}
		return false;
	}

	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @param tiposLinha
	 *            the tipos linha
	 * @param tipoDeVeiculos
	 *            the tipo de veiculos
	 * @return the linha
	 * @throws BusinessException
	 *             the business exception
	 */
	public Linha update(
		final Linha entity,
		final List<TipoDeLinha> tiposLinha,
		final List<TipoDeVeiculo> tipoDeVeiculos )
		throws BusinessException
	{
		beforeUpdate( entity );
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();

		final Linha linhaPersistida = get( entity.getId() );

		final LinhaDAO linhaDAO = getLinhaDAO();
		if ( perfil.isEmpresa() )
		{

			final LinhaVigencia linhaVigenciaPersistida = linhaPersistida.getLinhaVigente();
			atualizarVeiculosUtilizados( linhaVigenciaPersistida, tipoDeVeiculos );

			// Atualiza Pisos
			linhaVigenciaPersistida.setPiso1AB( entity.getLinhaVigente().getPiso1AB() );
			linhaVigenciaPersistida.setPiso2AB( entity.getLinhaVigente().getPiso2AB() );
			linhaVigenciaPersistida.setPiso1BA( entity.getLinhaVigente().getPiso1BA() );
			linhaVigenciaPersistida.setPiso2BA( entity.getLinhaVigente().getPiso2BA() );

			// Atualiza AB
			linhaVigenciaPersistida.setDuracaoViagemPicoAB( entity.getLinhaVigente().getDuracaoViagemPicoAB() );
			linhaVigenciaPersistida.setDuracaoViagemForaPicoAB( entity.getLinhaVigente().getDuracaoViagemForaPicoAB() );
			linhaVigenciaPersistida.setPicoFimManhaAB( entity.getLinhaVigente().getPicoFimManhaAB() );
			linhaVigenciaPersistida.setPicoFimTardeAB( entity.getLinhaVigente().getPicoFimTardeAB() );
			linhaVigenciaPersistida.setPicoInicioManhaAB( entity.getLinhaVigente().getPicoInicioManhaAB() );
			linhaVigenciaPersistida.setPicoInicioTardeAB( entity.getLinhaVigente().getPicoInicioTardeAB() );

			// Atualiza BA
			linhaVigenciaPersistida.setDuracaoViagemPicoBA( entity.getLinhaVigente().getDuracaoViagemPicoBA() );
			linhaVigenciaPersistida.setDuracaoViagemForaPicoBA( entity.getLinhaVigente().getDuracaoViagemForaPicoBA() );
			linhaVigenciaPersistida.setPicoInicioManhaBA( entity.getLinhaVigente().getPicoInicioManhaBA() );
			linhaVigenciaPersistida.setPicoFimManhaBA( entity.getLinhaVigente().getPicoFimManhaBA() );
			linhaVigenciaPersistida.setPicoInicioTardeBA( entity.getLinhaVigente().getPicoInicioTardeBA() );
			linhaVigenciaPersistida.setPicoFimTardeBA( entity.getLinhaVigente().getPicoFimTardeBA() );

			// veiculos permitidos
			// linhaVigenciaPersistida.setTipoDeVeiculosUtilizados(entity.getLinhaVigente().getTipoDeVeiculosUtilizados());

			linhaDAO.atualizarLinhaVigencia( linhaVigenciaPersistida );

		}
		else
		{

			final LinhaVigencia linhaVigenciaFutura = buscarLinhaVigenciaFutura( entity );
			List<Secao> secoesPersistidas = null;
			if ( Check.isNotNull( linhaPersistida ) )
			{
				secoesPersistidas = linhaPersistida.getLinhaVigente().getSecoes();
			}

			final TipoDeLinhaBusiness tipoDeLinhaBusiness = getTipoDeLinhaBusiness();
			if ( entity.isFuturo() )
			{

				try
				{
					linhaVigenciaFutura.setActive( false );

					// Gera uma nova linha vigencia (historico)
					final LinhaVigencia linhaVigencia = entity.getLinhaVigente().clone();
					copiaPropriedadesPicoDuracao( linhaPersistida.getLinhaVigente(), linhaVigencia );
					linhaVigencia.setActive( Boolean.TRUE );
					linhaVigencia.setLinha( entity );
					linhaVigencia.setObservacao( null );

					final List<LinhaVigenciaTipoDeLinha> lvTiposLinha = new ArrayList<LinhaVigenciaTipoDeLinha>();
					for ( final TipoDeLinha tipoDeLinha : tiposLinha )
					{
						final LinhaVigenciaTipoDeLinha lvTipoLinha = new LinhaVigenciaTipoDeLinha();
						lvTipoLinha.setLinhaVigencia( linhaVigencia );
						lvTipoLinha.setTipoDeLinha( tipoDeLinhaBusiness.find( tipoDeLinha.getId() ) );
						lvTipoLinha.setActive( Boolean.TRUE );
						lvTiposLinha.add( lvTipoLinha );
					}
					linhaVigencia.setLinhasVigenciaTiposDeLinha( lvTiposLinha );

					final Secao secao00 = criaSecao00ParaLinha( linhaVigencia );
					if ( Check.isNull( linhaVigencia.getSecoes() ) )
					{
						final List<Secao> secoes = new ArrayList<Secao>();
						secoes.add( secao00 );
						linhaVigencia.setSecoes( secoes );
					}
					else
					{
						linhaVigencia.getSecoes().add( secao00 );
					}
					final List<Secao> novasSecoes = associaSecoes( linhaVigencia.getSecoes(), secoesPersistidas );
					linhaVigencia.setSecoes( novasSecoes );

					linhaDAO.atualizarLinhaVigencia( linhaVigenciaFutura );

					final Calendar c = Calendar.getInstance();
					c.setTime( linhaVigencia.getDataInicio() );
					c.add( Calendar.DAY_OF_MONTH, -1 );
					linhaPersistida.getLinhaVigente().setDataTermino( c.getTime() );
					linhaDAO.atualizarLinhaVigencia( linhaPersistida.getLinhaVigente() );

					linhaVigencia.setDataCriacao( new Date() );
					linhaVigencia.setIdLinhaVigenciaAntiga( entity.getId() );

					linhaDAO.salvarLinhaVigencia( linhaVigencia );

					clonaTarifasLinhaVigencia( linhaPersistida.getLinhaVigente(), linhaVigencia );

				}
				catch ( final CloneFailException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}

			}
			else
			{

				try
				{
					// Mata a linha futura
					if ( linhaPersistida.isTemLinhaFutura() )
					{
						linhaVigenciaFutura.setActive( false );
						linhaDAO.atualizarLinhaVigencia( linhaVigenciaFutura );
					}

					// Gera uma nova linha vigencia (historico)
					final LinhaVigencia linhaVigencia = entity.getLinhaVigente().clone();
					copiaPropriedadesPicoDuracao( linhaPersistida.getLinhaVigente(), linhaVigencia );
					linhaVigencia.setActive( Boolean.TRUE );
					linhaVigencia.setLinha( entity );
					linhaVigencia.setObservacao( null );

					final Secao secao00 = criaSecao00ParaLinha( linhaVigencia );
					if ( Check.isNull( entity.getLinhaVigente().getSecoes() ) )
					{
						final List<Secao> secoes = new ArrayList<Secao>();
						secoes.add( secao00 );
						linhaVigencia.setSecoes( secoes );
					}
					else
					{
						linhaVigencia.getSecoes().add( secao00 );
					}
					final List<Secao> novasSecoes = associaSecoes( linhaVigencia.getSecoes(), secoesPersistidas );
					linhaVigencia.setSecoes( novasSecoes );

					linhaVigencia.setDataCriacao( new Date() );
					linhaVigencia.setIdLinhaVigenciaAntiga( entity.getId() );
					clonaLinhaVigenciaTiposLinha( linhaPersistida.getLinhaVigente(), linhaVigencia );

					final List<LinhaVigenciaTipoDeLinha> linhasVigenciaTiposDeLinha = linhaVigencia
						.getLinhasVigenciaTiposDeLinha();
					for ( final TipoDeLinha tipoDeLinha : tiposLinha )
					{
						final LinhaVigenciaTipoDeLinha lvTipoLinha = new LinhaVigenciaTipoDeLinha();
						lvTipoLinha.setLinhaVigencia( linhaVigencia );
						lvTipoLinha.setTipoDeLinha( tipoDeLinhaBusiness.find( tipoDeLinha.getId() ) );
						if ( !linhasVigenciaTiposDeLinha.contains( lvTipoLinha ) )
						{
							linhasVigenciaTiposDeLinha.add( lvTipoLinha );
						}
					}

					for ( final LinhaVigenciaTipoDeLinha lvTLinha : linhasVigenciaTiposDeLinha )
					{
						if ( !tiposLinha.contains( lvTLinha.getTipoDeLinha() ) )
						{
							lvTLinha.setActive( Boolean.FALSE );
						}
						else
						{
							lvTLinha.setActive( Boolean.TRUE );
						}
					}

					linhaDAO.salvarLinhaVigencia( linhaVigencia );

					// Antiga
					final Calendar c = Calendar.getInstance();
					c.setTime( linhaVigencia.getDataInicio() );
					c.add( Calendar.DAY_OF_MONTH, -1 );
					linhaPersistida.getLinhaVigente().setDataTermino( c.getTime() );
					linhaPersistida.getLinhaVigente().setObservacao( entity.getLinhaVigente().getObservacao() );

					if ( !linhaPersistida.getLinhaVigente().isVigente() )
					{
						linhaPersistida.getLinhaVigente().setActive( Boolean.FALSE );
					}
					linhaDAO.atualizarLinhaVigencia( linhaPersistida.getLinhaVigente() );

					clonaTarifasLinhaVigencia( linhaPersistida.getLinhaVigente(), linhaVigencia );

				}
				catch ( final CloneFailException e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}
			}
		}
		return get( entity.getId() );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @param justificativa
	 * @param tiposLinha
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 * @throws DaoException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	public Linha updateRetroativo( final Linha entity, final String justificativa, final List<TipoDeLinha> tiposLinha )
		throws BusinessException,
			ParseException,
			DaoException,
			IllegalAccessException,
			InvocationTargetException,
			NoSuchMethodException
	{

		final LinhaVigencia linhaVigente = entity.getLinhaVigente();

		final Long idLinha = entity.getId();
		final LinhaDAO linhaDAO = getLinhaDAO();
		final Linha linhaDb = linhaDAO.get( idLinha );
		final LinhaVigencia linhaVigenteDb = linhaDb.getLinhaVigente();
		final LinhaVigencia linhaVigenteAux = linhaVigenteDb.clone();
		final Secao secao00 = criaSecao00ParaLinhaRetroativa( linhaVigente );
		final List<Secao> secoesList = linhaVigente.getSecoes();
		if ( Check.isNull( secoesList ) )
		{
			final List<Secao> secoes = new ArrayList<Secao>();
			secoes.add( secao00 );
			linhaVigente.setSecoes( secoes );
		}
		else
		{

			secoesList.add( 0, secao00 );
		}
		populateWithNonNullProperties( linhaVigente, linhaVigenteAux );
		final Date dataInicioVigencia = linhaVigenteAux.getDataInicio();
		final StatusLinha statusLinhaVigencia = linhaVigenteAux.getStatus();
		final boolean linhaVigenciaCorrentAtiva = StatusLinha.ATIVA.equals( statusLinhaVigencia );
		final String codigoLinha = linhaVigente.getCodigo();
		final LinhaVigencia linhaVigenciaAtivaEm = buscaLinhaVigenciaAtivaEm( dataInicioVigencia, idLinha, codigoLinha );

		final Empresa empresa = linhaVigenciaAtivaEm.getEmpresa();

		final Date dataTermino;
		if ( linhaVigenciaCorrentAtiva )
		{
			dataTermino = DateProvider.subtrairDia( dataInicioVigencia, 1 );

		}// if
		else
		{
			dataTermino = dataInicioVigencia;

			linhaVigenciaAtivaEm.setStatus( statusLinhaVigencia );
		}// else

		linhaVigenciaAtivaEm.setDataTermino( dataTermino );
		linhaDAO.atualizarLinhaVigencia( linhaVigenciaAtivaEm );

		if ( linhaVigenciaCorrentAtiva )
		{
			final LinhaVigencia linhaVigenciaNova = linhaVigenteAux.clone();
			setDataCriacaoDeSecao( dataInicioVigencia, linhaVigenciaNova );
			clonaTarifasLinhaVigencia( linhaVigenteDb, linhaVigenciaNova );
			final List<LinhaVigenciaTipoDeLinha> lvTiposLinha = new ArrayList<LinhaVigenciaTipoDeLinha>();
			for ( final TipoDeLinha tipoDeLinha : tiposLinha )
			{
				final LinhaVigenciaTipoDeLinha lvTipoLinha = new LinhaVigenciaTipoDeLinha();
				lvTipoLinha.setLinhaVigencia( linhaVigenciaNova );
				lvTipoLinha.setTipoDeLinha( this.tipoDeLinhaBusiness.find( tipoDeLinha.getId() ) );
				lvTipoLinha.setActive( Boolean.TRUE );
				lvTiposLinha.add( lvTipoLinha );
			}
			linhaVigenciaNova.setLinhasVigenciaTiposDeLinha( lvTiposLinha );
			linhaVigenciaNova.setDataInicio( dataInicioVigencia );
			linhaVigenciaNova.setDataTermino( null );

			// FBW-483
			logarEdicaoRetroativa( linhaVigenciaNova, justificativa );
		}// if

		final String codigoLinhavigencia = linhaVigenciaAtivaEm.getCodigo();
		apagaBomAdicaoRetroativa( empresa, dataInicioVigencia, codigoLinhavigencia, justificativa );
		final List<Secao> secoes = linhaVigenciaAtivaEm.getSecoes();
		try
		{
			verificarIntegridadeEntreBomESecao( secoes );
		}
		catch ( final ObjectNotFoundException e )
		{
			// FBW-506

			// ocorreu a inconsistência reportada na FBW-506 (onde tem um BomSecao que
			// referencia um BomLinha que não tem pai)
			throw new OrphanBomLinhaException( e );

		}
		deletarTodasLinhasVigenciaPosteriores( dataInicioVigencia, idLinha, codigoLinhavigencia );
		final Date dataInicio = linhaVigenciaAtivaEm.getDataInicio();
		if ( dataInicio.compareTo( dataInicioVigencia ) == 0 )
		{
			deletarLinhaVigencia( linhaVigenciaAtivaEm );

		}// if
		else
		{
			// FBW-483
			logarEdicaoRetroativa( linhaVigenciaAtivaEm, justificativa );
		}// else

		final Linha result = get( idLinha );
		return result;

	}

	/**
	 * Valida campos pico.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @throws BusinessException
	 *             the business exception
	 */
	private void validaCamposPico( final LinhaVigencia linhaVigencia )
		throws BusinessException
	{
		final SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
		sdf.setLenient( false );

		if ( Check.isNotNull( linhaVigencia ) )
		{
			boolean erro = false;
			try
			{
				Date inicio = sdf.parse( linhaVigencia.getPicoInicioManhaAB() );
				Date fim = sdf.parse( linhaVigencia.getPicoFimManhaAB() );
				if ( fim.before( inicio ) )
				{
					erro = true;
				}

				inicio = sdf.parse( linhaVigencia.getPicoInicioManhaBA() );
				fim = sdf.parse( linhaVigencia.getPicoFimManhaBA() );
				if ( fim.before( inicio ) )
				{
					erro = true;
				}

				inicio = sdf.parse( linhaVigencia.getPicoInicioTardeAB() );
				fim = sdf.parse( linhaVigencia.getPicoFimTardeAB() );
				if ( fim.before( inicio ) )
				{
					erro = true;
				}

				inicio = sdf.parse( linhaVigencia.getPicoInicioTardeBA() );
				fim = sdf.parse( linhaVigencia.getPicoFimTardeBA() );
				if ( fim.before( inicio ) )
				{
					erro = true;
				}

			}
			catch ( final ParseException e )
			{
				throw new BusinessException( "O valor inserido no intervalo de pico não é uma hora válida.", e );
			}

			if ( erro )
			{
				throw new BusinessException(
					"O horário inicial do intervalo de pico deve ser anterior ao horário final." );
			}
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
				Double.parseDouble( importDTO.getValues().get( INDICE_EXCEL_VALOR ).replace( ",", "." ) );
			}
			catch ( final NumberFormatException e )
			{
				throw new BusinessException( "Valor inválido ou em branco de tarifa informado na coluna "
					+ ( INDICE_EXCEL_VALOR + 1 )
					+ " ("
					+ importDTO.getValues().get( INDICE_EXCEL_VALOR )
					+ ") da linha "
					+ numeroLinha, e );
			}

			try
			{
				if ( !( importDTO.getValues().get( INDICE_EXCEL_TARIFA_2012 ).isEmpty() ) )
				{
					Double.parseDouble( importDTO.getValues().get( INDICE_EXCEL_TARIFA_2012 ).replace( ",", "." ) );
				}
			}
			catch ( final NumberFormatException e )
			{
				throw new BusinessException( "Valor inválido de tarifa informado na coluna "
					+ ( INDICE_EXCEL_TARIFA_2012 + 1 )
					+ " ("
					+ importDTO.getValues().get( INDICE_EXCEL_TARIFA_2012 )
					+ ") da linha "
					+ numeroLinha, e );
			}

			try
			{
				if ( !( importDTO.getValues().get( INDICE_EXCEL_PISO_I ).isEmpty() ) )
				{
					Double.parseDouble( importDTO.getValues().get( INDICE_EXCEL_PISO_I ).replace( ",", "." ) );
				}
			}
			catch ( final NumberFormatException e )
			{
				throw new BusinessException( "Valor inválido do piso I informado na coluna "
					+ ( INDICE_EXCEL_PISO_I + 1 )
					+ " ("
					+ importDTO.getValues().get( INDICE_EXCEL_PISO_I )
					+ ") da linha "
					+ numeroLinha, e );
			}

			try
			{
				if ( !( importDTO.getValues().get( INDICE_EXCEL_PISO_2 ).isEmpty() ) )
				{
					Double.parseDouble( importDTO.getValues().get( INDICE_EXCEL_PISO_2 ).replace( ",", "." ) );
				}
			}
			catch ( final NumberFormatException e )
			{
				throw new BusinessException( "Valor inválido do Piso II informado na coluna "
					+ ( INDICE_EXCEL_PISO_2 + 1 )
					+ " ("
					+ importDTO.getValues().get( INDICE_EXCEL_PISO_2 )
					+ ") da linha "
					+ numeroLinha, e );
			}

			numeroLinha++;
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param secoes
	 */
	private void verificarIntegridadeEntreBomESecao( final List<Secao> secoes )
	{

		this.bomDao.buscarBomSecaoPorSecoes( secoes );

	}

	/**
	 * The bom dao.
	 */
	private final BomDAO bomDao;

	/**
	 * The configuracao business.
	 */
	private final ConfiguracaoBusiness configuracaoBusiness;

	/**
	 * The empresa business.
	 */
	private EmpresaBusiness empresaBusiness;

	/**
	 * <p>
	 * Field <code>empresaDAO</code>
	 * </p>
	 */
	private EmpresaDAO empresaDAO;

	/**
	 * The nao encontrou secao list.
	 */
	public List<String> naoEncontrouSecaoList = new ArrayList<String>();

	/**
	 * The tarifa dao.
	 */
	private TarifaDAO tarifaDAO;

	/**
	 * The bom reabertura dao.
	 */
	private BomReaberturaDAO bomReaberturaDAO;

	/**
	 * The tipo de linha business.
	 */
	private final TipoDeLinhaBusiness tipoDeLinhaBusiness;

	/**
	 * The tipo de linha dao.
	 */
	private final TipoDeLinhaDAO tipoDeLinhaDAO;

}// func

package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.excel.Excel;
import br.com.decatron.framework.exception.AuthorizationException;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.BomBusiness;
import br.com.fetranspor.bomweb.business.ConfiguracaoBusiness;
import br.com.fetranspor.bomweb.business.EmpresaBusiness;
import br.com.fetranspor.bomweb.dto.FiltroBomPendenteDTO;
import br.com.fetranspor.bomweb.entity.Bom;
import br.com.fetranspor.bomweb.entity.BomLinha;
import br.com.fetranspor.bomweb.entity.BomReabertura;
import br.com.fetranspor.bomweb.entity.BomSecao;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Status;
import br.com.fetranspor.bomweb.entity.StatusFiltroBomPendente;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.exception.ValidationException;
import br.com.fetranspor.bomweb.jquery.datatable.BomRowFactory;
import br.com.fetranspor.bomweb.strategy.AbstractReabrirBomStrategy;
import br.com.fetranspor.bomweb.util.UtilitiesIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class BomController.
 */
@Resource
public class BomController
	extends VRaptorController
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( BomController.class );

	/**
	 * Instantiates a new bom controller.
	 *
	 * @param provider
	 *            the provider
	 * @param bomBusiness
	 *            the bom business
	 * @param empresaBusiness
	 *            the empresa business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param context
	 *            the context
	 * @param configuracaoBusiness
	 *            the configuracao business
	 */
	public BomController(
		final VRaptorProvider provider,
		final BomBusiness bomBusiness,
		final EmpresaBusiness empresaBusiness,
		final Result result,
		final Validator validator,
		final ServletContext context,
		final ConfiguracaoBusiness configuracaoBusiness )
	{
		super( provider, result, validator );
		this.bomBusiness = bomBusiness;
		this.empresaBusiness = empresaBusiness;
		this.configuracaoBusiness = configuracaoBusiness;
		this.context = context;
	}

	// FBW-41
	/**
	 * Adds the j query data table result as json to result.
	 *
	 * @param bom
	 *            the bom
	 */
	@Post
	@Get
	@Path( "/bom/dataTableResult.json" )
	public void addJQueryDataTableResultAsJSONToResult( final Bom bom )
	{
		final BomRowFactory rowFactory = new BomRowFactory( this.provider.getAuthorization(), this );
		super.addJQueryDataTableResultAsJSONToResult( rowFactory, list( bom ) );
	}

	// FBW-41
	/**
	 * Adds the j query data table result as json to result.
	 *
	 * @param filtro
	 *            the filtro
	 */
	@Post
	@Get
	@Path( "/bom/pendentes/dataTableResult.json" )
	public void addJQueryDataTableResultAsJSONToResult( final FiltroBomPendenteDTO filtro )
	{
		final BomRowFactory rowFactory = new BomRowFactory( this.provider.getAuthorization() );
		super.addJQueryDataTableResultAsJSONToResult( rowFactory, pendentes( filtro ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	private boolean bomEstaDentroDoPrazoDeReabertura( final Bom bom )
	{
		try
		{
			final Perfil perfil = ( Perfil ) getRoles().iterator().next();
			if ( !perfil.isEmpresa() )
			{
				return true;
			}// if

			return this.bomBusiness.estaDentroDoPrazoDeReabertura( bom );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}
		return false;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	private boolean bomReabertoMenosDeDuasVezes( final Bom bom )
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( !perfil.isEmpresa() )
		{
			return true;
		}// if

		final int qtdBomsReabertos = getBomReopenLength( bom );
		if ( qtdBomsReabertos >= 2 )
		{
			return false;
		}// if

		return true;

	}

	// -- BOM

	/**
	 * Confirm cancelar preenchimento linha.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @return the bom linha
	 */
	public BomLinha confirmCancelarPreenchimentoLinha( final BomLinha bomLinha )
	{
		return this.bomBusiness.buscarBomLinha( bomLinha.getId() );
	}

	/**
	 * Confirm fechar.
	 *
	 * @param bom
	 *            the bom
	 * @return the bom
	 */
	public Bom confirmFechar( final Bom bom )
	{
		return this.bomBusiness.get( bom.getId() );
	}

	/**
	 * Excel attributes bom pendente.
	 *
	 * @return the string[]
	 */
	private String[] excelAttributesBomPendente()
	{
		return new String[]{"empresa.nome",
							"mesReferencia",
							"statusPendencia",
							"dataLimiteFechamento"};
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelAttributesName()
	 */
	@Override
	public String[] excelAttributesName()
	{
		return new String[]{};
	}

	/**
	 * Excel file name.
	 *
	 * @param bom
	 *            the bom
	 * @return the string
	 */
	public String excelFileName( final Bom bom )
	{
		return "BOM_emp" + bom.getEmpresa().getCodigo() + "_" + bom.getMesReferencia().replaceAll( "/", "_" ) + ".xls";
	}

	/**
	 * Excel file name bom pendente.
	 *
	 * @return the string
	 */
	private String excelFileNameBomPendente()
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();
		final SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy" );
		String excelName;
		if ( perfil.isEmpresa() )
		{
			excelName = "Bom_Pendencias_"
				+ usuario.getEmpresa().getNome().replaceAll( " ", "_" )
				+ "_"
				+ df.format( Calendar.getInstance().getTime() )
				+ ".xls";
		}
		else
		{
			excelName = "Bom_Pendencias_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
		}
		excelName = excelName.replaceAll( "/|\\\\|\\*|:|\\?|\"|\\>|\\<|\\|", "" );
		return excelName;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelHeader()
	 */
	@Override
	public String[] excelHeader()
	{
		return new String[]{"ID",
							"Número da Linha",
							"Nome da Linha",
							"Tipo de Veículo",
							"Frota",
							"Capacidade do Veículo",
							"Tarifa (R$)",
							"Viag. Ordinárias",
							"Viag. Extraord.",
							"Viag. Ordinárias",
							"Viag. Extraord.",
							"ID Seção",
							"Seção",
							"Pass. Espécie",
							"Pass. Espécie Ant.",
							"Pass. Vale",
							"Pass. Vale Ant.",
							"Pass. Espécie",
							"Pass. Espécie Ant.",
							"Pass. Vale",
							"Pass. Vale Ant."};
	}

	// -- LINHA

	/**
	 * Excel header bom pendente.
	 *
	 * @return the string[]
	 */
	private String[] excelHeaderBomPendente()
	{
		return new String[]{"Empresa",
							"Mês/Ano de Referência",
							"Status",
							"Limite de Entrega"};
	}

	/**
	 * Exportar.
	 *
	 * @param bom
	 *            the bom
	 * @return the download
	 */
	public Download exportar( final Bom bom )
	{

		final Long idBomLong = bom.getId();
		final Bom bomPersistido = this.bomBusiness.get( idBomLong );

		try
		{
			this.bomBusiness.validateExportacaoBOM( bomPersistido );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( HomeController.class ).home();
			return null;
		}

		File excelFile = null;
		try
		{
			excelFile = this.bomBusiness.exportarBOM( bomPersistido );
		}
		catch ( final Exception e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		final String contentType = "application/vnd.ms-excel";
		final FileDownload fileDownload = new FileDownload( excelFile, contentType, excelFile.getName() );
		UtilitiesIO.deleteFile( excelFile.getPath() );
		return fileDownload;
	}

	/**
	 * Exportar pendentes.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the download
	 */
	public Download exportarPendentes( FiltroBomPendenteDTO filtro )
	{

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();

		if ( perfil.isEmpresa() )
		{
			if ( filtro == null )
			{
				filtro = new FiltroBomPendenteDTO();
			}
			filtro.setNomeEmpresa( usuario.getEmpresa().getNome() );
			filtro.setEmpresa( usuario.getEmpresa().getId().toString() );
		}

		List<Bom> bomsPendentes;
		try
		{
			bomsPendentes = this.bomBusiness.listPendentesComFiltro( filtro );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).pendentes( null );
			return null;
		}
		final String nomeArquivo = excelFileNameBomPendente();

		File excelFile = null;
		try
		{
			excelFile = new Excel()
				.withHeader( excelHeaderBomPendente() )
				.setHeightHeader( 10 )
				.useCenterHeader( Boolean.TRUE )
				.useBoldHeader( Boolean.TRUE )
				.useNumericCellType( Boolean.TRUE )
				.from( bomsPendentes )
				.withAtributes( excelAttributesBomPendente() )
				.setHeightAttribute( 10 )
				.generate( nomeArquivo );
		}
		catch ( final Exception e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		final String contentType = "application/vnd.ms-excel";
		final FileDownload fileDownload = new FileDownload( excelFile, contentType, excelFile.getName() );
		UtilitiesIO.deleteFile( excelFile.getPath() );
		return fileDownload;
	}

	/**
	 * Fechar.
	 *
	 * @param bom
	 *            the bom
	 */
	public void fechar( final Bom bom )
	{
		try
		{
			this.bomBusiness.fechar( bom );
			addInfoMessage( "BOM fechado com sucesso." );
			this.result.redirectTo( this ).list( bom );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( bom );
		}
		catch ( final ValidationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			final List<String> messages = e.getValidationsErrors();
			for ( final String message : messages )
			{
				addErrorMessage( message );
			}
			this.result.redirectTo( BomController.class ).list( bom );
		}
	}

	// -- SECAO

	/**
	 * Form upload.
	 *
	 * @param bom
	 *            the bom
	 */
	public void formUpload( final Bom bom )
	{
		this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );
	}

	/**
	 * Generate.
	 *
	 * @param bom
	 *            the bom
	 */
	public void generate( final Bom bom )
	{

		if ( bom.getMesReferencia().isEmpty() )
		{
			addErrorMessage( "Por favor preencha o campo Mês de Referência." );
			this.result.redirectTo( this ).insert();
		}

		try
		{
			this.bomBusiness.generate( bom );
			this.result.redirectTo( this ).linhas( bom );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).insert();
		}
		catch ( final ValidationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			final List<String> messages = e.getValidationsErrors();
			for ( final String message : messages )
			{
				addErrorMessage( message );
			}
			this.result.redirectTo( this ).insert();
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	public int getBomReopenLength( final Bom bom )
	{
		final List<BomReabertura> bomReaberturaList = this.bomBusiness.getBomReabertura( bom );
		return bomReaberturaList.size();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @throws BusinessException
	 * @throws ParseException
	 */
	public Date getPrazoFinalDeReabertura( final Bom bom )
		throws BusinessException

	{
		final Bom bomBase = this.bomBusiness.find( bom.getId() );
		return this.bomBusiness.getPrazoFinalDeReabertura( bomBase.getDataFechamento(), bomBase.getMesReferencia() );

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param perfil
	 * @return
	 */
	public AbstractReabrirBomStrategy getReabrirBomImpl( final Perfil perfil )
	{
		final Map<Integer, AbstractReabrirBomStrategy> perfilImplementacao = AbstractReabrirBomStrategy
			.getPerfilImplementacao();
		final AbstractReabrirBomStrategy abstractReabrirBomStrategy = perfilImplementacao.get( perfil.getId() );
		return abstractReabrirBomStrategy;
	}

	/**
	 * Historico.
	 *
	 * @param bomLinha
	 *            the bom linha
	 */
	public void historico( final BomLinha bomLinha )
	{
		final Bom bom = this.bomBusiness.getBomPorBomLinhaId( bomLinha.getId() );
		this.result.include( "bom", bom );
		this.result.include( "justList", this.bomBusiness.historicoJustificativas( bomLinha ) );
	}

	/**
	 * Insert.
	 */
	public void insert()
	{
		final Usuario usuario = ( Usuario ) getUser();
		if ( Check.isBlank( usuario.getEmpresa().getEmailContato() )
			|| Check.isBlank( usuario.getEmpresa().getResponsavel() ) )
		{
			// FBW-21
			addErrorMessage( "É necessário cadastrar os campos Email de Contato e Responsável da Empresa, deslogar e logar novamente no sistema antes de criar um BOM." );
			this.result.redirectTo( this ).list( null );
		}
	}

	/**
	 * Justificativas.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	public List<BomLinha> justificativas( final Bom bom )
	{
		try
		{
			this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );
			return this.bomBusiness.pesquisarLinhasEditaveis( bom );
		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		return null;
	}

	/**
	 * Linha.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @return the bom linha
	 */
	public BomLinha linha( BomLinha bomLinha )
	{
		try
		{
			bomLinha = this.bomBusiness.buscarBomLinha( bomLinha.getId() );

			if ( Status.INOPERANTE.equals( bomLinha.getStatus() ) )
			{
				addInfoMessage( "Essa linha está marcada como Inoperante." );
				this.result.redirectTo( this ).linhas( bomLinha.getBom() );
			}

			return bomLinha;

		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).linhas( bomLinha.getBom() );
		}
		return null;
	}

	/**
	 * Linhas.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	public List<BomLinha> linhas( final Bom bom )
	{

		final Usuario usuario = ( Usuario ) getUser();

		if ( Check.isBlank( usuario.getEmpresa().getEmailContato() )
			|| Check.isBlank( usuario.getEmpresa().getResponsavel() ) )
		{
			// FBW-21
			addErrorMessage( "É necessário cadastrar os campos Email de Contato e Responsável da Empresa, deslogar e logar novamente no sistema antes de preencher um BOM." );
			this.result.redirectTo( this ).list( null );
		}

		try
		{
			this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );
			return this.bomBusiness.pesquisarLinhasEditaveis( bom );
		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		return null;
	}

	/**
	 * Linhas inoperantes.
	 *
	 * @param bom
	 *            the bom
	 * @param linhas
	 *            the linhas
	 */
	public void linhasInoperantes( final Bom bom, final List<BomLinha> linhas )
	{
		try
		{
			this.bomBusiness.linhasInoperantes( linhas );
			addInfoMessage( "Salvo com sucesso." );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
		}
		this.result.redirectTo( this ).linhas( bom );
	}

	/**
	 * List.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	public List<Bom> list( final Bom bom )
	{
		this.result.include( "bom", bom );
		this.result.include( "empresas", this.empresaBusiness.search( null ) );

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		this.result.include( "perfil", perfil );
		try
		{
			final List<Bom> searchBom = this.bomBusiness.searchBom( bom );
			return searchBom;
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			return new ArrayList<Bom>();
		}
	}

	/**
	 * List linhas recriar.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	public List<LinhaVigencia> listLinhasRecriar( Bom bom )
	{
		bom = this.bomBusiness.get( bom.getId() );
		this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );

		return this.bomBusiness.buscarLinhasRecriar( bom );
	}

	/**
	 * Pendentes.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	public List<Bom> pendentes( FiltroBomPendenteDTO filtro )
	{
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		final StatusFiltroBomPendente[] status = {	StatusFiltroBomPendente.Todos,
													StatusFiltroBomPendente.Aberto,
													StatusFiltroBomPendente.Reaberto,
													StatusFiltroBomPendente.Vencido};
		this.result.include( "status", status );

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();

		if ( filtro == null )
		{
			filtro = new FiltroBomPendenteDTO();
		}

		if ( perfil.isEmpresa() )
		{
			filtro.setNomeEmpresa( usuario.getEmpresa().getNome() );
			filtro.setEmpresa( usuario.getEmpresa().getId().toString() );
		}

		this.result.include( "filtro", filtro );

		try
		{
			return this.bomBusiness.listPendentesComFiltro( filtro );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).pendentes( null );
		}

		return null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @throws BusinessException
	 */
	public List<BomLinha> pesquisarBomLinhaByBom( final Bom bom )
		throws BusinessException
	{
		final List<BomLinha> pesquisarBomLinhaByBom = this.bomBusiness.buscarBomLinhaList( bom );
		return pesquisarBomLinhaByBom;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 */
	public boolean podeReabrirBom( final Bom bom )
	{
		return bomEstaDentroDoPrazoDeReabertura( bom ) && bomReabertoMenosDeDuasVezes( bom );
	}

	/**
	 * Reabertura.
	 *
	 * @param bom
	 *            the bom
	 * @param linhas
	 *            the linhas
	 * @throws BusinessException
	 */

	public void reabertura( final Bom bom, final List<BomLinha> linhas )
		throws BusinessException
	{
		final boolean redirectOnValidationError = true;
		reaberturaAdapter( bom, linhas, redirectOnValidationError );

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @param linhas
	 * @param redirectOnValidationError
	 * @throws BusinessException
	 */
	public void reaberturaAdapter( final Bom bom, final List<BomLinha> linhas, final boolean redirectOnValidationError )
		throws BusinessException
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		try
		{
			if ( perfil.isEmpresa() )
			{
				this.bomBusiness.validateReaberturaEmpresa( linhas );
			}// if
			else
			{
				this.bomBusiness.validateReabertura( linhas );

			}
		}
		catch ( final BusinessException e )
		{
			if ( !redirectOnValidationError )
			{
				throw e;
			}

			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).reabrir( bom );
			return;
		}

		try
		{

			this.bomBusiness.validateJustificativa( linhas );
			if ( perfil.isEmpresa() )
			{
				this.bomBusiness.reabrirBomPelaEmpresa( bom, linhas );

			}// if
			else
			{
				this.bomBusiness.reabrirBom( bom, linhas );
			}
			this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );
			addInfoMessage( "Bom reaberto com sucesso." );
		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
		}
		finally
		{
			this.result.redirectTo( this ).list( null );
		}
	}

	/**
	 * Reabrir.
	 *
	 * @param bom
	 *            the bom
	 * @return the list
	 */
	public List<BomLinha> reabrir( final Bom bom )
	{
		try
		{
			final Perfil perfil = ( Perfil ) getRoles().iterator().next();
			final AbstractReabrirBomStrategy abstractReabrirBomStrategy = getReabrirBomImpl( perfil );
			final List<BomLinha> reabrir = abstractReabrirBomStrategy.reabrir( bom, this.result, this );
			return reabrir;
		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		return null;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 * @return
	 * @throws BusinessException
	 */
	public List<BomLinha> reabrirBomPorDetroNivel1( final Bom bom )
		throws BusinessException
	{
		this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );
		return pesquisarBomLinhaByBom( bom );
	}

	/**
	 * Recriar linhas.
	 *
	 * @param bom
	 *            the bom
	 * @param linhas
	 *            the linhas
	 */
	public void recriarLinhas( final Bom bom, final List<LinhaVigencia> linhas )
	{

		try
		{
			this.bomBusiness.validateRecriar( linhas );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).listLinhasRecriar( bom );
			return;
		}

		try
		{
			this.bomBusiness.recriarLinhas( bom, linhas );
			this.result.include( "bom", this.bomBusiness.get( bom.getId() ) );
			addInfoMessage( "Linhas recriadas com sucesso." );
		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
		}
		catch ( final ValidationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
		}
		finally
		{
			this.result.redirectTo( this ).linhas( bom );
		}

	}

	/**
	 * Salvar linha.
	 *
	 * @param entity
	 *            the entity
	 * @param actionPreencherSecoes
	 *            the action preencher secoes
	 */
	public void salvarLinha( final BomLinha entity, final Boolean actionPreencherSecoes )
	{
		boolean actionPreencherSecoesToUse = actionPreencherSecoes;
		try
		{
			this.bomBusiness.update( entity );
			if ( !actionPreencherSecoesToUse )
			{
				addInfoMessage( "Salvo com sucesso." );
			}
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			actionPreencherSecoesToUse = false;
			addErrorMessage( e.getMessage() );
		}

		if ( actionPreencherSecoesToUse )
		{
			this.result.redirectTo( this ).secoes( entity );
		}
		else
		{
			this.result.redirectTo( this ).linhas( entity.getBom() );
		}
	}

	/**
	 * Salvar secao.
	 *
	 * @param entity
	 *            the entity
	 */
	public void salvarSecao( final BomSecao entity )
	{
		try
		{
			this.bomBusiness.update( entity );
			addInfoMessage( "Salvo com sucesso." );
			this.result.redirectTo( this ).secoes( entity.getBomLinha() );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			if ( !entity.isInoperante() )
			{
				entity.setStatus( Status.PREENCHIDO );
			}
			this.result.include( "bomSecao", entity );
			this.result.redirectTo( this ).secao( entity );
		}
	}

	/**
	 * Secao.
	 *
	 * @param bomSecao
	 *            the bom secao
	 * @return the bom secao
	 */
	public BomSecao secao( BomSecao bomSecao )
	{

		bomSecao = this.bomBusiness.buscarBomSecao( bomSecao.getId() );

		if ( Status.INOPERANTE.equals( bomSecao.getBomLinha().getStatus() ) )
		{
			addErrorMessage( "Essa seção é de uma linha que se encontra marcada como Inoperante." );
			this.result.redirectTo( this ).linha( bomSecao.getBomLinha() );
		}

		/*
		 * if( Status.INOPERANTE.equals(bomSecao.getStatus()) ) {
		 * addErrorMessage("Essa seção está marcada como Sem Passageiro.");
		 * result.redirectTo(this).linha(bomSecao.getBomLinha()); }
		 */

		final BomSecao bomSecaoTela = ( BomSecao ) this.result.included().get( "bomSecao" );

		if ( Check.isNotNull( bomSecaoTela ) )
		{
			bomSecao.setPassageiroAB( bomSecaoTela.getPassageiroAB() );
			bomSecao.setPassageiroAnteriorAB( bomSecaoTela.getPassageiroAnteriorAB() );
			bomSecao.setPassageiroBA( bomSecaoTela.getPassageiroBA() );
			bomSecao.setPassageiroAnteriorBA( bomSecaoTela.getPassageiroAnteriorBA() );
			bomSecao.setTarifa( bomSecaoTela.getTarifa() );
			bomSecao.setStatus( bomSecaoTela.getStatus() );
		}

		return bomSecao;
	}

	/**
	 * Secoes.
	 *
	 * @param bomLinha
	 *            the bom linha
	 * @return the bom linha
	 */
	public BomLinha secoes( BomLinha bomLinha )
	{
		try
		{
			bomLinha = this.bomBusiness.buscarBomLinha( bomLinha.getId() );

			if ( Status.INOPERANTE.equals( bomLinha.getStatus() ) )
			{
				addInfoMessage( "Essa linha está marcada como Inoperante." );
				this.result.redirectTo( this ).linhas( bomLinha.getBom() );
			}

			return bomLinha;

		}
		catch ( final AuthorizationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).linhas( bomLinha.getBom() );
		}
		return null;
	}

	/**
	 * Upload.
	 *
	 * @param bom
	 *            the bom
	 * @param file
	 *            the file
	 */
	public void upload( Bom bom, final UploadedFile file )
	{
		File importFile = null;

		bom = this.bomBusiness.get( bom.getId() );

		try
		{
			// tarifaBusiness.validateImportDate(validator, data);

			final String uploadFolder = this.configuracaoBusiness.buscarDiretorioUpload();
			importFile = new File( uploadFolder, file.getFileName() );

			IOUtils.copyLarge( file.getFile(), new FileOutputStream( importFile ) );
			final int arquivo = this.bomBusiness.importExcel( bom, importFile );
			if ( arquivo > 0 )
			{
				addInfoMessage( "BOM atualizado com sucesso." );
				this.result.redirectTo( getClass() ).list( bom );
			}
			else
			{
				addErrorMessage( "A importação não foi realizada. Favor verificar arquivo." );
				this.result.redirectTo( BomController.class ).formUpload( bom );
			}
		}
		catch ( final FileNotFoundException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Arquivo não encontrado." );
			this.result.redirectTo( BomController.class ).formUpload( bom );
		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao abrir arquivo." );
			this.result.redirectTo( BomController.class ).formUpload( bom );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( BomController.class ).formUpload( bom );
		}
		catch ( final ValidationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			final List<String> messages = e.getValidationsErrors();
			for ( final String message : messages )
			{
				addErrorMessage( message );
			}
			this.result.redirectTo( BomController.class ).formUpload( bom );
		}
		finally
		{
			if ( Check.isNotNull( importFile ) && importFile.exists() )
			{
				importFile.delete();
			}
		}
	}

	/**
	 * Visualizar.
	 *
	 * @param bom
	 *            the bom
	 * @return the download
	 */
	public Download visualizar( final Bom bom )
	{
		final Bom bomPersistido = this.bomBusiness.get( bom.getId() );
		File file = null;
		try
		{
			file = this.bomBusiness.pegaPdf( bomPersistido );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( this ).list( null );
		}
		final String contentType = "application/pdf";
		final FileDownload fileDownload = new FileDownload( file, contentType, file.getName() );
		UtilitiesIO.deleteFile( file.getPath() );
		return fileDownload;
	}

	/**
	 * The bom business.
	 */
	private final BomBusiness bomBusiness;

	/**
	 * The configuracao business.
	 */
	private final ConfiguracaoBusiness configuracaoBusiness;

	/**
	 * The context.
	 */
	final ServletContext context;

	/**
	 * The empresa business.
	 */
	private final EmpresaBusiness empresaBusiness;
}

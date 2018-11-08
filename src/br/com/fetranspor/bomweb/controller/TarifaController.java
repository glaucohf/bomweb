package br.com.fetranspor.bomweb.controller;

import static br.com.caelum.vraptor.view.Results.json;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.spring.VRaptorRequestHolder;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.NoRootSerialization;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.view.Results;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.ConfiguracaoBusiness;
import br.com.fetranspor.bomweb.business.EmpresaBusiness;
import br.com.fetranspor.bomweb.business.LinhaBusiness;
import br.com.fetranspor.bomweb.business.TarifaBusiness;
import br.com.fetranspor.bomweb.controller.LinhaController.ValidateDataVigenciaResultData;
import br.com.fetranspor.bomweb.dto.FiltroTarifaDTO;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.Tarifa;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.exception.ValidationException;
import br.com.fetranspor.bomweb.jquery.datatable.TarifaRowFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class TarifaController.
 */
@Resource
public class TarifaController
	extends BomWebController<Tarifa>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( TarifaController.class );

	/**
	 * Instantiates a new tarifa controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param linhaBusiness
	 *            the linha business
	 * @param empresaBusiness
	 *            the empresa business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param configuracaoBusiness
	 *            the configuracao business
	 */
	public TarifaController(
		final VRaptorProvider provider,
		final TarifaBusiness business,
		final LinhaBusiness linhaBusiness,
		final EmpresaBusiness empresaBusiness,
		final Result result,
		final Validator validator,
		final ConfiguracaoBusiness configuracaoBusiness )
	{
		super( provider, business, result, validator );
		this.autoList = Boolean.TRUE;
		this.tarifaBusiness = business;
		this.linhaBusiness = linhaBusiness;
		this.empresaBusiness = empresaBusiness;
		this.configuracaoBusiness = configuracaoBusiness;
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
	@Path( "/tarifa/dataTableResult.json" )
	public void addJQueryDataTableResultAsJSONToResult( final FiltroTarifaDTO filtro )
	{
		final TarifaRowFactory rowFactory = new TarifaRowFactory( this.provider.getAuthorization() );
		super.addJQueryDataTableResultAsJSONToResult( rowFactory, list( filtro ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#afterEdit()
	 */
	@Override
	protected void afterEdit()
	{
		super.afterEdit();
		final List<Tarifa> tarifasFuturas = getTarifaBusiness().buscarTarifasAindaNaoVigentes( this.entity );
		if ( Check.isNotNull( tarifasFuturas ) && ( tarifasFuturas.size() >= 1 ) )
		{

			if ( this.entity.isVigente() )
			{
				addErrorMessage( "Existe uma tarifa futura para esta linha/seção. Para poder editá-la, exclua a tarifa futura." );
				this.result.redirectTo( TarifaController.class ).list();
			}
			else
			{
				final Tarifa tarifaAtual = getTarifaBusiness().buscaTarifaAtual( this.entity.getSecao() );
				if ( Check.isNotNull( tarifaAtual ) )
				{
					addErrorMessage( "Esta tarifa ainda não está vigente e por isso não pode ser editada. Exclua-a e edite a tarifa vigente da linha correspondente." );
					this.result.redirectTo( TarifaController.class ).list();
				}
				else
				{
					addErrorMessage( "Esta tarifa ainda não está vigente e por isso não pode ser editada. Exclua-a e crie outra tarifa para a linha correspondente." );
					this.result.redirectTo( TarifaController.class ).list();
				}
			}
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeEdit()
	 */
	@Override
	protected void beforeEdit()
	{
		super.beforeEdit();
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.CrudController#beforeInsert()
	 */
	@Override
	protected void beforeInsert()
	{
		super.beforeInsert();
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @throws BusinessException
	 * @throws ValidationException
	 * @throws ParseException
	 * @throws Exception
	 */
	protected void beforeUpdate( final Tarifa entity )
		throws BusinessException,
			ValidationException,
			ParseException

	{
		super.beforeUpdate();
		validacoesComunsBeforeSaveEUpdate( entity );

	}

	/**
	 * Busca linhas.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 */
	@Get
	@Path( "/tarifa/buscaLinhas.json" )
	public void buscaLinhas( final String term, String idEmpresa )
	{

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();
		if ( perfil.isEmpresa() )
		{
			idEmpresa = usuario.getEmpresa().getId().toString();
		}
		final List<LinhaVigencia> linhas = getTarifaBusiness().buscaLinhasAutoComplete( term, idEmpresa );

		// FBW-319
		for ( final LinhaVigencia linhaVigencia : linhas )
		{
			linhaVigencia.populateForJson();
		}// for

		this.result.use( Results.json() ).from( linhas, "linhas" ).serialize();
	}

	/**
	 * Busca linhas sem tarifa.
	 *
	 * @param term
	 *            the term
	 * @param idEmpresa
	 *            the id empresa
	 */
	@Get
	@Path( "/tarifa/buscaLinhasSemTarifa.json" )
	public void buscaLinhasSemTarifa( final String term, String idEmpresa )
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();
		if ( perfil.isEmpresa() )
		{
			idEmpresa = usuario.getEmpresa().getId().toString();
		}
		final List<LinhaVigencia> linhas = getTarifaBusiness().buscaLinhasVigenciaSemTarifaCadastrada( term, idEmpresa );
		this.result.use( Results.json() ).from( linhas, "linhas" ).serialize();
	}

	/**
	 * Busca secoes.
	 *
	 * @param parentId
	 *            the parent id
	 */
	@Get
	@Path( "/tarifa/buscaSecoes.json" )
	public void buscaSecoes( final String parentId )
	{
		final List<Secao> secoes = this.linhaBusiness.pesquisarSecoesPorLinhaVigenteSecoes( parentId );
		for ( final Secao s : secoes )
		{
			s.setJuncao( s.getPontoInicial() + " - " + s.getPontoFinal() );
		}
		this.result.use( Results.json() ).withoutRoot().from( secoes ).serialize();
	}

	/**
	 * Busca secoes sem tarifa.
	 *
	 * @param parentId
	 *            the parent id
	 */
	@Get
	@Path( "/tarifa/buscaSecoesSemTarifa.json" )
	public void buscaSecoesSemTarifa( final String parentId )
	{
		final List<Secao> secoes = this.linhaBusiness.pesquisarSecoesSemTarifaPorLinhaVigenteSecoes( parentId );
		for ( final Secao s : secoes )
		{
			s.setJuncao( s.getPontoInicial() + " - " + s.getPontoFinal() );
		}
		this.result.use( Results.json() ).withoutRoot().from( secoes ).serialize();
	}

	/**
	 * Confirm import.
	 */
	public void confirmImport()
	{
	}

	/**
	 * Erro edit futura.
	 *
	 * @param entity
	 *            the entity
	 * @return the tarifa
	 */
	public Tarifa erroEditFutura( final Tarifa entity )
	{
		final Tarifa tarifa = this.business.get( entity.getId() );
		if ( !tarifa.isFutura() && !tarifa.isTemTarifaFutura() )
		{
			this.result.redirectTo( this ).edit( entity );
		}
		return tarifa;
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
		return new String[]{"id",
							"secao.linhaVigencia.codigo",
							"secao.linhaVigencia.empresa.nome",
							"secao.linhaVigencia.itinerarioIda",
							"secao.codigo",
							"secao.juncao",
							"valor",
							"inicioVigencia",
							"fimVigencia"};
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.Controller#excelFileName()
	 */
	@Override
	public String excelFileName()
	{
		final SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy" );
		return "Tarifas_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
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
		return new String[]{"Id",
							"Código da Linha",
							"Empresa",
							"Linha",
							"Código da Seção",
							"Seção",
							"Valor",
							"Início Vigência",
							"Fim Vigência"};
	}

	/**
	 * Exportar.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the file download
	 */
	@Post
	@Path( "/tarifa/exportar" )
	public FileDownload exportar( final FiltroTarifaDTO filtro )
	{
		return super.exportToExcel( list( filtro ) );
	}

	/**
	 * Form upload.
	 */
	public void formUpload()
	{
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
		final Date dataPrimeiraLinhaVigencia = getTarifaBusiness().getDataPrimeiraLinhaVigencia( umaLinha );
		return dataPrimeiraLinhaVigencia;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param empresaId
	 * @param dataInicio
	 * @return
	 */
	private String getMensagemComBomsQueSeraoApagados( final Long empresaId, final String dataInicio )
	{
		final TarifaBusiness tarifaBusiness = ( TarifaBusiness ) this.business;
		final String bomsQueSeraoApagados = tarifaBusiness.getMensagemComBomsQueSeraoApagados( dataInicio, empresaId );
		return bomsQueSeraoApagados;
		// TODO Incluir a string retornada no JSON.

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	private TarifaBusiness getTarifaBusiness()
	{
		return this.tarifaBusiness;
	}

	/**
	 * Historico.
	 *
	 * @param entity
	 *            the entity
	 */
	public void historico( final Tarifa entity )
	{
		final Tarifa tarifa = getTarifaBusiness().get( entity.getId() );
		this.result.include( "tarifaList", getTarifaBusiness().findHistorico( tarifa ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @return false se a <code>data</code> não representa uma data no formato
	 *         <code>"dd/MM/yyyy"</code>, ou se a data representada é anterior ou igual a 'hoje'.
	 * @throws ParseException
	 */
	private boolean isDataFutura( final String data )
		throws ParseException
	{
		// FBW-503 refactor + fix

		final SimpleDateFormat dateFormat = new SimpleDateFormat( "dd/MM/yyyy" );

		final Date umaData = dateFormat.parse( data );
		final Date hoje = new Date();

		final Date umaDataZerada = zerarHoraMinutoSegundoMilissegundo( umaData );

		final Date hojeZerado = zerarHoraMinutoSegundoMilissegundo( hoje );

		final boolean result = hojeZerado.before( umaDataZerada );
		return result;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 */
	private boolean isEdicaoRetroativa( final Tarifa entity )
	{
		return getTarifaBusiness().isEdicaoRetroativa( entity );
	}

	/**
	 * List.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	@Post
	@Get
	@Path( "/tarifa/list" )
	public List<Tarifa> list( FiltroTarifaDTO filtro )
	{
		this.result.include( "empresas", this.empresaBusiness.search( null ) );

		// Inclui tarifas vigentes e futuras para serem utilizadas no filtro
		this.result.include( "tarifasVigentes", getTarifaBusiness().findValorTarifas( false ) );
		this.result.include( "tarifasFuturas", getTarifaBusiness().findValorTarifas( true ) );

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		final Usuario usuario = ( Usuario ) getUser();

		if ( Check.isNotNull( filtro ) )
		{
			if ( Check.isNotBlank( filtro.getEmpresa() ) )
			{
				this.result.include( "linhas", getTarifaBusiness().buscaLinhasVigencia( filtro.getEmpresa() ) );
			}
			if ( Check.isNotBlank( filtro.getLinha() ) )
			{
				final List<Secao> secoes = this.linhaBusiness.pesquisarSecoesPorLinhaVigenteSecoes( filtro.getLinha() );
				for ( final Secao s : secoes )
				{
					s.setJuncao( s.getPontoInicial() + " - " + s.getPontoFinal() );
				}
				this.result.include( "secoes", secoes );
			}
		}

		if ( perfil.isEmpresa() )
		{
			if ( filtro == null )
			{
				filtro = new FiltroTarifaDTO();
			}
			filtro.setNomeEmpresa( usuario.getEmpresa().getNome() );
			filtro.setEmpresa( usuario.getEmpresa().getId().toString() );
			// result.include("linhas",
			// tarifaBusiness.buscaLinhasVigencia(usuario.getEmpresa().getId().toString()));
		}

		this.result.include( "filtro", filtro );
		return getTarifaBusiness().listaComFiltro( filtro );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.controller.vraptor.VRaptorCrudController#onErrorSave()
	 */
	@Override
	protected String onErrorSave()
	{
		if ( Check.isNotNull( this.entity ) && Check.isNotNull( this.entity.getSecao().getId() ) )
		{
			this.result.include(
				"linhas",
				getTarifaBusiness().buscaLinhasVigenciaSemTarifaCadastrada(
					null,
					this.entity.getSecao().getLinhaVigencia().getEmpresa().getId().toString() ) );
			final List<Secao> secoes = this.linhaBusiness.pesquisarSecoesSemTarifaPorLinhaVigenteSecoes( this.entity
				.getSecao()
				.getLinhaVigencia()
				.getId()
				.toString() );
			for ( final Secao s : secoes )
			{
				s.setJuncao( s.getPontoInicial() + " - " + s.getPontoFinal() );
			}
			this.result.include( "secoes", secoes );
			final LinhaVigencia linha = this.linhaBusiness.buscaLinhaVigencia( this.entity
				.getSecao()
				.getLinhaVigencia() );
			this.entity.getSecao().getLinhaVigencia().setPontoInicial( linha.getPontoInicial() );
			this.entity.getSecao().getLinhaVigencia().setPontoFinal( linha.getPontoFinal() );
		}
		return super.onErrorSave();
	}

	private boolean perfilLogadoIsDetroAdmin()
	{
		final List listRoles = getRoles();
		for ( final Object object : listRoles )
		{
			final Perfil perfilLogado = ( Perfil ) object;
			if ( perfilLogado.isDetroAdmin() )
			{
				return true;
			}// if
		}// for
		return false;
	}

	/**
	 * <p>
	 * Exibe a view tarifaRetroativa.jsp de
	 * /bomweb/src/main/webapp/WEB-INF/jsp/tarifa/tarifaRetroativa.jsp Que esta no arquivo
	 * /bomweb/src/main/java/br/com/fetranspor/bomweb/components/CustomRoutes.java
	 * </p>
	 */
	@Post
	@Get
	@Path( "/tarifaRetroativa/formUpload" )
	public void tarifaRetroativa()
	{

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @see br.com.decatron.framework.controller.CrudController#update(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public void update( final Tarifa entity )
	{
		final Result result = this.result;
		final MutableRequest request = VRaptorRequestHolder.currentRequest().getRequest();
		final String justificativa = request.getParameter( "justificaivaHidden" );

		try
		{
			beforeUpdate( entity );

			final TarifaBusiness tarifaBusiness = ( TarifaBusiness ) this.business;
			if ( isEdicaoRetroativa( entity ) )
			{
				tarifaBusiness.updateRetroativo( entity, justificativa );
				addInfoMessage( updateMessage() );

			}// if
			else
			{
				tarifaBusiness.update( entity );
				addInfoMessage( updateMessage() );
			}// else
			result.redirectTo( TarifaController.class ).list();
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			result.redirectTo( TarifaController.class ).edit( entity );
		}
		catch ( final ValidationException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
			final List<String> messages = e.getValidationsErrors();
			for ( final String message : messages )
			{
				addErrorMessage( message );
			}
			result.redirectTo( TarifaController.class ).edit( entity );

		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
			addErrorMessage( e.getMessage() );
			result.redirectTo( TarifaController.class ).list( entity );

		}

	}

	/**
	 * Upload.
	 *
	 * @param data
	 *            the data
	 * @param file
	 *            the file
	 */
	public void upload( final String data, final UploadedFile file )
	{
		File importFile = null;

		try
		{
			getTarifaBusiness().validateImportDate( this.validator, data );

			final String uploadFolder = this.configuracaoBusiness.buscarDiretorioUpload();

			importFile = new File( uploadFolder, file.getFileName() );

			IOUtils.copyLarge( file.getFile(), new FileOutputStream( importFile ) );
			final int arquivo = getTarifaBusiness().importExcel( data, importFile );
			if ( arquivo > 0 )
			{
				addInfoMessage( arquivo + " tarifa(s) atualizada(s) com sucesso." );
				this.result.redirectTo( getClass() ).list();
			}
			else
			{
				addErrorMessage( "Nenhuma Tarifa importada. Favor verificar arquivo." );
				this.result.redirectTo( TarifaController.class ).formUpload();
			}
		}
		catch ( final FileNotFoundException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Arquivo não encontrado." );
			this.result.redirectTo( TarifaController.class ).formUpload();
		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao abrir arquivo." );
			this.result.redirectTo( TarifaController.class ).formUpload();
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( TarifaController.class ).formUpload();
		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao importar os dados." );
			this.result.redirectTo( TarifaController.class ).formUpload();
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
	 * <p>
	 * Recebe uma planilha em padrão previamente definido e faz as alterações de tarifa indicadas na
	 * planilha, inclusive as que tiverem datas retroativas! E faz a exclusão de BOMs afetados de
	 * acordo com a vigência da tarifa.
	 *
	 * @author marcio.dias
	 * @param file
	 *            the file
	 * @return Retorna uma msg de sucesso ou erro.
	 *         </p>
	 * @see - Faz o upLoda do aquivo<br>
	 *      - Alterar as tarifas que tem o valor diferente<br>
	 *      - Alterar a data de vigência inial que tem o valor diferente<br>
	 *      - Alterar a data de vigência final que tem o valor diferente<br>
	 *      - Exclusão de BOMs afetados de acordo com a vigência da tarifa (Apaga os BOM das
	 *      empresas que tiveram suas tarifas modificadas, apaga os BOM da data inicial até o
	 *      presente).<br>
	 *      - Registro de log via banco de dados de todas as ações efetuadas viabilizando a
	 *      auditoria no sistema. Serão registrados: (1) A alteração feita, (2) Usuário que fez, (3)
	 *      Data e hora, (4) IP da máquina onde foi solicitada a alteração.<br>
	 *      - As partes envolvidas (empresa e Detro) devem receber um email informando que os BOMs
	 *      dos meses em questão foram excluídos<br>
	 */
	public void uploadTarifaRetro( final UploadedFile file )
	{
		// addInfoMessage( " teste." );
		// this.result.redirectTo( getClass() ).tarifaRetroativa();
		File importFile = null;

		try
		{
			final String uploadFolder = this.configuracaoBusiness.buscarDiretorioUpload();

			importFile = new File( uploadFolder, file.getFileName() );

			IOUtils.copyLarge( file.getFile(), new FileOutputStream( importFile ) );
			final int arquivo = getTarifaBusiness().importExcelTR( importFile );
			if ( arquivo > 0 )
			{
				addInfoMessage( arquivo + " tarifa(s) atualizada(s) com sucesso." );
				this.result.redirectTo( getClass() ).list();
			}
			else
			{
				addErrorMessage( "Nenhuma Tarifa importada. Favor verificar arquivo." );
				this.result.redirectTo( TarifaController.class ).tarifaRetroativa();
			}
		}
		catch ( final FileNotFoundException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Arquivo não encontrado." );
			this.result.redirectTo( TarifaController.class ).tarifaRetroativa();
		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao abrir arquivo." );
			this.result.redirectTo( TarifaController.class ).tarifaRetroativa();
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( TarifaController.class ).tarifaRetroativa();
		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao importar os dados." );
			this.result.redirectTo( TarifaController.class ).tarifaRetroativa();
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
	 * Validacoes comuns before save e update.
	 *
	 * @param entity
	 *            the entity
	 * @throws BusinessException
	 *             the business exception
	 * @throws ValidationException
	 * @throws ParseException
	 */
	protected void validacoesComunsBeforeSaveEUpdate( final Tarifa entity )
		throws BusinessException,
			ValidationException,
			ParseException
	{

		final List<String> validationErrors = new ArrayList<String>();

		final Secao secao = entity.getSecao();
		if ( Check.isNull( secao ) || Check.isNull( secao.getId() ) )
		{
			validationErrors.add( "Selecione uma seção para cadastrar uma nova tarifa." );
		}

		final boolean isDetroAdmin = perfilLogadoIsDetroAdmin();
		final String dataInicio = entity.getInicioVigenciaFormated();
		final boolean canExecute = isDataFutura( dataInicio );

		if ( !canExecute && !isDetroAdmin )
		{
			validationErrors.add( "Somente é possível fazer edições futuras." );
		}// if

		if ( isDetroAdmin )

		{
			final Tarifa tarifaDB = getTarifaBusiness().get( entity.getId() );
			final Secao secaoDB = tarifaDB.getSecao();
			final LinhaVigencia linhaVigenciaDB = secaoDB.getLinhaVigencia();
			final Linha linhaDB = linhaVigenciaDB.getLinha();
			linhaDB.setLinhaVigente( linhaVigenciaDB );
			final Date dataPrimeiraLinhaVigencia = getDataPrimeiraLinhaVigencia( linhaDB );
			final Date inicioVigencia = entity.getInicioVigencia();
			if ( inicioVigencia.before( dataPrimeiraLinhaVigencia ) )
			{

				validationErrors
					.add( "A data de início da vigência não pode ser anterior à data de início do primeiro registro da linha que é "
						+ new SimpleDateFormat( "dd/MM/yyyy" ).format( dataPrimeiraLinhaVigencia )
						+ "." );
			}
		}

		if ( !validationErrors.isEmpty() )
		{
			throw new ValidationException( validationErrors );
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataVigencia
	 * @param empresaId
	 * @throws ParseException
	 */
	@Get
	@Path( "/tarifa/valida.json" )
	public void validateDataVigencia( final String dataVigencia, final Long empresaId )
		throws ParseException
	{

		final boolean isDetroAdmin = perfilLogadoIsDetroAdmin();
		final boolean canExecute = isDataFutura( dataVigencia );
		String buscaBomsQueSeraoApagados = "";
		if ( !canExecute && isDetroAdmin )
		{
			buscaBomsQueSeraoApagados = getMensagemComBomsQueSeraoApagados( empresaId, dataVigencia );
		}// if
		final ValidateDataVigenciaResultData data = new ValidateDataVigenciaResultData();
		data.setDetroAdmin( isDetroAdmin );
		data.setCanExecute( canExecute );
		data.setWarnMessage( buscaBomsQueSeraoApagados );

		final Result result = this.result;

		final Class<JSONSerialization> json = json();
		final JSONSerialization use = result.use( json );
		final NoRootSerialization withoutRoot = use.withoutRoot();
		final Serializer from = withoutRoot.from( data );
		from.serialize();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param data
	 * @return
	 */
	private Date zerarHoraMinutoSegundoMilissegundo( final Date data )
	{
		final Calendar umaData = Calendar.getInstance();
		umaData.setTime( data );
		umaData.set( Calendar.HOUR_OF_DAY, 0 );
		umaData.set( Calendar.MINUTE, 0 );
		umaData.set( Calendar.SECOND, 0 );
		umaData.set( Calendar.MILLISECOND, 0 );
		final Date time = umaData.getTime();
		return time;
	}

	/**
	 * The configuracao business.
	 */
	private final ConfiguracaoBusiness configuracaoBusiness;

	/**
	 * The empresa business.
	 */
	private final EmpresaBusiness empresaBusiness;

	/**
	 * The linha business.
	 */
	private final LinhaBusiness linhaBusiness;

	/**
	 * The tarifa business.
	 */
	private final TarifaBusiness tarifaBusiness;
}

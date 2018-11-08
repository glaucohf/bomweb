package br.com.fetranspor.bomweb.controller;

import static br.com.caelum.vraptor.view.Results.json;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;
import br.com.caelum.vraptor.ioc.spring.VRaptorRequestHolder;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.NoRootSerialization;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.decatron.framework.excel.Excel;
import br.com.decatron.framework.exception.BusinessException;
import br.com.decatron.framework.exception.DaoException;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.ConfiguracaoBusiness;
import br.com.fetranspor.bomweb.business.EmpresaBusiness;
import br.com.fetranspor.bomweb.business.LinhaBusiness;
import br.com.fetranspor.bomweb.business.TipoDeLinhaBusiness;
import br.com.fetranspor.bomweb.dto.LinhaDTO;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.LinhaVigenciaTipoDeLinha;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Secao;
import br.com.fetranspor.bomweb.entity.StatusLinha;
import br.com.fetranspor.bomweb.entity.TipoDeLinha;
import br.com.fetranspor.bomweb.entity.TipoDeLinhaTipoDeVeiculo;
import br.com.fetranspor.bomweb.entity.TipoDeVeiculo;
import br.com.fetranspor.bomweb.exception.OrphanBomLinhaException;
import br.com.fetranspor.bomweb.exception.ValidationException;
import br.com.fetranspor.bomweb.jquery.datatable.LinhaRowFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class LinhaController.
 */
/**
 * <p>
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 24/06/2015
 */
@Resource
public class LinhaController
	extends BomWebController<Linha>
{

	/**
	 * <p>
	 * </p>
	 *
	 * @author fred
	 * @version 1.0 Created on Jun 25, 2015
	 */
	public static class ValidateDataVigenciaResultData
	{

		/**
		 * <p>
		 * </p>
		 */
		public ValidateDataVigenciaResultData()
		{
			super();
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @return Returns the warnMessage.
		 * @see #warnMessage
		 */
		public String getWarnMessage()
		{
			return this.warnMessage;
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @return Returns the canExecute.
		 * @see #canExecute
		 */
		public boolean isCanExecute()
		{
			return this.canExecute;
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @return Returns the isDetroAdmin.
		 * @see #isDetroAdmin
		 */
		public boolean isDetroAdmin()
		{
			return this.isDetroAdmin;
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @param canExecute
		 *            The canExecute to set.
		 * @see #canExecute
		 */
		public void setCanExecute( final boolean canExecute )
		{
			this.canExecute = canExecute;
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @param isDetroAdmin
		 *            The isDetroAdmin to set.
		 * @see #isDetroAdmin
		 */
		public void setDetroAdmin( final boolean isDetroAdmin )
		{
			this.isDetroAdmin = isDetroAdmin;
		}

		/**
		 * <p>
		 * </p>
		 *
		 * @param warnMessage
		 *            The warnMessage to set.
		 * @see #warnMessage
		 */
		public void setWarnMessage( final String warnMessage )
		{
			this.warnMessage = warnMessage;
		}

		/**
		 * <p>
		 * Field <code>canExecute</code>
		 * </p>
		 */
		private boolean canExecute;

		/**
		 * <p>
		 * Field <code>isDetroAdmin</code>
		 * </p>
		 */
		private boolean isDetroAdmin;

		/**
		 * <p>
		 * Field <code>warnMessage</code>
		 * </p>
		 */
		private String warnMessage;

	}

	/**
	 * Logger for this class.
	 */
	private static final Log LOG = LogFactory.getLog( LinhaController.class );

	/**
	 * Instantiates a new linha controller.
	 *
	 * @param provider
	 *            the provider
	 * @param business
	 *            the business
	 * @param empresaBusiness
	 *            the empresa business
	 * @param tipoLinhaBusiness
	 *            the tipo linha business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param configuracaoBusiness
	 *            the configuracao business
	 */
	public LinhaController(
		final VRaptorProvider provider,
		final LinhaBusiness business,
		final EmpresaBusiness empresaBusiness,
		final TipoDeLinhaBusiness tipoLinhaBusiness,
		final Result result,
		final Validator validator,
		final ConfiguracaoBusiness configuracaoBusiness )
	{
		super( provider, business, result, validator );
		this.autoList = Boolean.TRUE;
		this.empresaBusiness = empresaBusiness;
		this.linhaBusiness = business;
		this.tipoLinhaBusiness = tipoLinhaBusiness;
		this.configuracaoBusiness = configuracaoBusiness;
	}

	// FBW-41
	/**
	 * Adds the j query data table result as json to result.
	 *
	 * @param entityForSearch
	 *            the entity for search
	 */
	@Post
	@Get
	@Path( "/linha/dataTableResult.json" )
	public void addJQueryDataTableResultAsJSONToResult( final Linha entityForSearch )
	{
		final LinhaRowFactory rowFactory = new LinhaRowFactory( this.provider.getAuthorization() );
		super.addJQueryDataTableResultAsJSONToResult( rowFactory, entityForSearch );
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
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();

		this.result.include( "statusLinha", StatusLinha.getListaStatusLinha() );
		this.result.include( "isStatusAtiva", this.entity.getLinhaVigente().isStatusAtiva() );
		if ( !hasErrorMessages() )
		{
			this.result.include( "secoes", ( ( LinhaBusiness ) this.business ).buscaSecoesSemSecao00( this.entity ) );
		}

		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		if ( perfil.isEmpresa() )
		{
			this.result.include( "STR_SEPARA_IDS", LinhaBusiness.STR_SEPARA_IDS );
			if ( !hasErrorMessages() )
			{
				this.result.include( "tipoLinhas", this.tipoLinhaBusiness.selecionaUtilizados( this.entity ) );
			}
		}
		else
		{
			if ( !hasErrorMessages() )
			{
				this.result.include( "tipoLinhas", this.tipoLinhaBusiness.recuperaTiposLinhaSelecionados( this.entity
					.getLinhaVigente()
					.getTiposDeLinha() ) );
			}
		}
		this.result.include( "vigente", this.entity.getLinhaVigente().isVigente() ? "1" : "0" );
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
		this.result.include( "operation", "edit" );
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
		incluiNoResultTodosOsTiposDeLinhaSelecionados();
		this.result.include( "operation", "insert" );
		this.result.include( "statusLinha", StatusLinha.getListaStatusLinha() );

		final List<TipoDeLinha> tipoDeLinhas = this.tipoLinhaBusiness.search( null );
		if ( Check.isNull( tipoDeLinhas ) || Check.isEmpty( tipoDeLinhas ) )
		{
			addErrorMessage( "Antes de cadastrar uma linha, é necessário cadastrar os tipos de linha." );
			this.result.redirectTo( this ).list();
		}
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @see br.com.decatron.framework.controller.ListController#beforeList()
	 */
	@Override
	protected void beforeList()
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		if ( !perfil.isEmpresa() )
		{
			this.result.include( "empresas", this.empresaBusiness.search( null ) );
		}
	}

	/**
	 * Before save.
	 *
	 * @param entity
	 *            the entity
	 * @param tipoLinhas
	 *            the tipo linhas
	 */
	protected void beforeSave( final Linha entity, final List<TipoDeLinha> tipoLinhas )
	{
		this.result.include( "linha", entity );
		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		this.result.include( "tipoLinhas", this.tipoLinhaBusiness.recuperaTiposLinhaSelecionados( tipoLinhas ) );
		this.result.include( "secoes", entity.getLinhaVigente().getSecoes() );

		final Perfil perfil = ( Perfil ) getRoles().iterator().next();

		// FBW-24
		if ( Check.isNull( entity.getLinhaVigente().getPiso1AB() ) )
		{
			entity.getLinhaVigente().setPiso1AB( 0.0 );
		}

		if ( Check.isNull( entity.getLinhaVigente().getPiso1BA() ) )
		{
			entity.getLinhaVigente().setPiso1BA( 0.0 );
		}
		if ( Check.isNull( entity.getLinhaVigente().getPiso2AB() ) )
		{
			entity.getLinhaVigente().setPiso2AB( 0.0 );
		}

		if ( Check.isNull( entity.getLinhaVigente().getPiso2BA() ) )
		{
			entity.getLinhaVigente().setPiso2BA( 0.0 );
		}

		if ( entity.getLinhaVigente().getCodigo().length() != LinhaVigencia.DIGITOS_CODIGO )
		{
			this.validator.add( new ValidationMessage(
				"O código da linha deve possuir 9 digitos.",
				"Favor verificar o código da linha." ) );
			this.validator.onErrorRedirectTo( LinhaController.class ).insert();
		}

		if ( getLinhaBusiness().linhaJaExiste( entity ) )
		{
			this.validator.add( new ValidationMessage( "Linha já existente.", "Favor verificar o código da linha." ) );
			this.validator.onErrorRedirectTo( LinhaController.class ).insert();
		}

		if ( Check.isNotEmpty( entity.getLinhaVigente().getSecoes() ) )
		{

			final List<Secao> secaos = entity.getLinhaVigente().getSecoes();

			if ( getLinhaBusiness().temSecaoRepetida( entity.getLinhaVigente() ) )
			{
				this.validator.add( new ValidationMessage( "Existem Seções com código repetido.", "Erro" ) );
				this.validator.onErrorRedirectTo( LinhaController.class ).insert();
			}

			for ( final Secao secao : secaos )
			{
				if ( getLinhaBusiness().secaoJaExiste(
					secao.getCodigo(),
					entity.getLinhaVigente().getCodigo(),
					entity.getLinhaVigente().getEmpresa().getCodigo() ) )
				{
					this.validator.add( new ValidationMessage(
						"Seção já existente.",
						"Favor verificar o código da Seção" ) );
					this.validator.onErrorRedirectTo( LinhaController.class ).insert();
				}
			}

			if ( getLinhaBusiness().temSecao00( entity.getLinhaVigente() ) )
			{
				this.validator.add( new ValidationMessage(
					"A seção de código "
						+ Secao.COD_SECAO_OBRIGATORIA
						+ " é gerada pelo sistema e não pode ser cadastrada manualmente.",
					"Favor verificar o código da Seção" ) );
				this.validator.onErrorRedirectTo( LinhaController.class ).insert();
			}
		}

		if ( !getLinhaBusiness().isDataInicioLinhaPosteriorHoje( entity.getLinhaVigente() ) )
		{
			if ( !perfil.isEmpresa() )
			{
				this.validator.add( new ValidationMessage(
					"A Data de Vigência deve ser posterior ao dia atual.",
					"Erro" ) );
				this.validator.onErrorRedirectTo( LinhaController.class ).insert();
			}
		}

		final Empresa empresa = entity.getLinhaVigente().getEmpresa().getId() == null ? null : this.empresaBusiness
			.find( entity.getLinhaVigente().getEmpresa().getId() );
		if ( Check.isNull( empresa ) || !empresa.getNome().equals( entity.getLinhaVigente().getEmpresa().getNome() ) )
		{
			final List<Empresa> empresas = this.empresaBusiness.buscaEmpresasByNomeExato( entity
				.getLinhaVigente()
				.getEmpresa()
				.getNome() );
			if ( Check.isEmpty( empresas ) )
			{
				this.validator.add( new ValidationMessage(
					"A empresa preenchida não existe. Favor selecionar uma empresa existente.",
					"Erro" ) );
				this.validator.onErrorRedirectTo( LinhaController.class ).insert();
			}
			else if ( empresas.size() > 1 )
			{
				this.validator
					.add( new ValidationMessage(
						"Existem empresas homônimas. Favor selecionar uma empresa na lista suspensa ao invés de digitar o nome da mesma.",
						"Erro" ) );
				this.validator.onErrorRedirectTo( LinhaController.class ).insert();
			}
			else
			{
				entity.getLinhaVigente().setEmpresa( empresas.get( 0 ) );
			}
		}

		super.beforeSave();
	}

	/**
	 * Before update.
	 *
	 * @param entity
	 *            the entity
	 * @param tiposLinha
	 *            the tipos linha
	 * @param tiposVeiculo
	 *            the tipos veiculo
	 * @throws ValidationException
	 *             the validation exception
	 * @throws ParseException
	 */
	protected void beforeUpdate(
		final Linha entity,
		final List<TipoDeLinha> tiposLinha,
		final List<TipoDeVeiculo> tiposVeiculo )
		throws ValidationException,
			ParseException
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();

		this.result.include( "empresas", this.empresaBusiness.search( null ) );
		this.result.include( "secoes", entity.getLinhaVigente().getSecoesSemSecao00() );
		if ( perfil.isEmpresa() )
		{
			this.result
				.include(
					"tipoLinhas",
					selecionaVeiculosUtilizadosInterface(
						this.tipoLinhaBusiness.listarTiposDeLinha( entity ),
						tiposVeiculo ) );
		}
		else
		{
			this.result.include( "tipoLinhas", this.tipoLinhaBusiness.recuperaTiposLinhaSelecionados( tiposLinha ) );
		}

		final List<String> validationErrors = new ArrayList<String>();

		if ( entity.getLinhaVigente().getCodigo().length() != LinhaVigencia.DIGITOS_CODIGO )
		{
			validationErrors.add( "O código da linha deve possuir 9 digitos." );
		}

		if ( getLinhaBusiness().linhaJaExiste(
			entity.getLinhaVigente().getCodigo(),
			entity.getId(),
			entity.getLinhaVigente().getEmpresa().getId() ) )
		{
			validationErrors.add( "Linha já existente." );
		}

		if ( Check.isNotEmpty( entity.getLinhaVigente().getSecoes() ) )
		{

			final List<Secao> secaos = entity.getLinhaVigente().getSecoes();

			if ( getLinhaBusiness().temSecaoRepetida( entity.getLinhaVigente() ) )
			{
				validationErrors.add( "Existem Seções com código repetido." );
			}

			for ( final Secao secao : secaos )
			{

				if ( getLinhaBusiness().secaoJaExiste(
					secao.getCodigo(),
					entity.getLinhaVigente().getId(),
					secao.getId() ) )
				{
					validationErrors.add( "Seção já existente." );
				}
			}

			if ( getLinhaBusiness().temSecao00( entity.getLinhaVigente() ) )
			{
				this.result.include( "secoes", getLinhaBusiness().buscaSecoesSemSecao00( entity ) );
				validationErrors.add( "A seção de código "
					+ Secao.COD_SECAO_OBRIGATORIA
					+ " é gerada pelo sistema e não pode ser cadastrada manualmente." );
			}
		}

		final Empresa empresaPersistida = entity.getLinhaVigente().getEmpresa().getId() == null
			? null
			: this.empresaBusiness.find( entity.getLinhaVigente().getEmpresa().getId() );

		if ( !perfil.isEmpresa()
			&& ( Check.isNull( empresaPersistida ) || !empresaPersistida.getNome().equals(
				entity.getLinhaVigente().getEmpresa().getNome() ) ) )
		{
			final List<Empresa> empresas = this.empresaBusiness.buscaEmpresasByNomeExato( entity
				.getLinhaVigente()
				.getEmpresa()
				.getNome() );
			if ( Check.isEmpty( empresas ) )
			{
				validationErrors.add( "A empresa preenchida não existe. Favor selecionar uma empresa existente." );
			}
			else if ( empresas.size() > 1 )
			{
				validationErrors
					.add( "Existem empresas homônimas. Favor selecionar uma empresa na lista suspensa ao invés de digitar o nome da mesma." );
			}
			else
			{
				entity.getLinhaVigente().setEmpresa( empresas.get( 0 ) );
			}
		}

		if ( perfil.isDetro() )
		{
			final LinhaVigencia linhaVigenciaPersistida = entity.getLinhaVigente().getId() == null
				? null
				: getLinhaBusiness().buscaLinhaVigencia( entity.getLinhaVigente() );
			// Nao permite que uma linha ativa sem tipos de veiculos associados tenha seu status
			// alterado
			if ( Check.isNotNull( linhaVigenciaPersistida )
				&& linhaVigenciaPersistida.getTipoDeVeiculosUtilizados().isEmpty() )
			{
				if ( linhaVigenciaPersistida.getStatus().equals( StatusLinha.ATIVA )
					&& !linhaVigenciaPersistida.getStatus().equals( entity.getLinhaVigente().getStatus() ) )
				{
					validationErrors
						.add( "Não é possível alterar o status de uma linha que não possui tipos de veículos associados." );
				}
			}
		}
		if ( perfil.isDetroAdmin() )
		{
			if ( isDataRetroativaInvalida( entity ) )
			{
				final Date dataPrimeiraLinhaVigencia = getDataPrimeiraLinhaVigencia( entity );

				validationErrors
					.add( "A data de início da vigência não pode ser anterior à data de início do primeiro registro da linha que é "
						+ new SimpleDateFormat( "dd/MM/yyyy" ).format( dataPrimeiraLinhaVigencia )
						+ "." );
			}
		}

		final boolean isDetroAdmin = perfilLogadoIsDetroAdmin();
		final String dataInicio = entity.getLinhaVigente().getDataInicioFormated();
		final boolean canExecute = canExecute( dataInicio );
		if ( !canExecute && !isDetroAdmin )
		{
			validationErrors.add( "Somente é possível fazer edições futuras." );
		}// if

		if ( !validationErrors.isEmpty() )
		{
			throw new ValidationException( validationErrors );
		}

		super.beforeUpdate();
	}

	/**
	 * Busca json.
	 *
	 * @param term
	 *            the term
	 */
	@Get
	@Path( "/linha/busca.json" )
	public void buscaJson( final String term )
	{
		this.result
			.use( json() )
			.from( this.empresaBusiness.buscaEmpresasByNome( term == null ? "" : term ), "empresa" )
			.exclude( "uuid", "active", "excludeProperties", "codigo", "emailContato", "responsavel", "dataCriacao" )
			.serialize();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataVigencia
	 * @return
	 * @throws ParseException
	 */
	private boolean canExecute( final String dataVigencia )
		throws ParseException
	{
		return isDataFutura( dataVigencia ) || perfilLogadoIsEmpresa();
	}

	/**
	 * Confirm edit.
	 *
	 * @param entity
	 *            the entity
	 * @return the linha
	 */
	public Linha confirmEdit( final Linha entity )
	{
		final Linha linha = this.business.get( entity.getId() );
		return linha;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @see br.com.decatron.framework.controller.vraptor.VRaptorCrudController#edit(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	public String edit( final Linha entity )
	{
		beforeEdit();
		this.result.include( "futuro", "0" );
		final Linha linhaPersistida = this.business.get( entity.getId() );

		final Linha linha;

		if ( hasErrorMessages() )
		{
			final LinhaVigencia linhaVigenteFromForm = entity.getLinhaVigente();
			final LinhaVigencia linhaVigenteFromDb = linhaPersistida.getLinhaVigente();
			final List<LinhaVigenciaTipoDeLinha> linhasVigenciaTiposDeLinha = linhaVigenteFromDb
				.getLinhasVigenciaTiposDeLinha();
			linhaVigenteFromForm.setLinhasVigenciaTiposDeLinha( linhasVigenciaTiposDeLinha );
			linha = entity;
			if ( linha.isFuturo() )
			{
				this.result.include( "futuro", "1" );
			}
		}
		else
		{
			linha = linhaPersistida;
		}
		this.entity = linha;
		afterEdit();
		this.result.include( "linha", this.entity ); // Retornando linha ao inves de T
														// pois antes o metodo retornar
														// Entity ao inves de String
														// (outcome)
		return null;
	}

	/**
	 * Edits the futuro.
	 *
	 * @param entity
	 *            the entity
	 * @return the string
	 */
	public String editFuturo( final Linha entity )
	{
		beforeEdit();
		this.result.include( "futuro", "1" );
		this.editFuturo = Boolean.TRUE;
		final Linha linhaPersistida = this.business.get( entity.getId() );
		Linha linha = null;
		if ( hasErrorMessages() )
		{
			entity.getLinhaVigente().setLinhasVigenciaTiposDeLinha(
				linhaPersistida.getLinhaVigente().getLinhasVigenciaTiposDeLinha() );
			linha = entity;
		}
		else
		{
			linha = linhaPersistida;
		}
		this.entity = linha;
		super.edit( entity );
		afterEdit();
		this.result.include( "linha", this.entity ); // Retornando linha ao inves de T
														// pois antes o metodo retornar
														// Entity ao inves de String
														// (outcome)
		return null;
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
		return new String[]{"linhaVigente.empresa.nome",
							"linhaVigente.codigo",
							"linhaVigente.listagemSecoes",
							"linhaVigente.itinerarioIda",
							"linhaVigente.status.nomeFormatado",
							"linhaVigente.listagemTipoDeVeiculosUtilizados",
							"linhaVigente.extensaoAB",
							"linhaVigente.extensaoBA",
							"linhaVigente.dataInicio",
							"linhaVigente.dataTermino"};
	}

	/**
	 * Excel attributes name linha com secoes.
	 *
	 * @return the string[]
	 */
	public String[] excelAttributesNameLinhaComSecoes()
	{
		return new String[]{"empresa",
							"codigoLinha",
							"codigoSecao",
							"nomeLinha",
							"pontoInicialSecao",
							"pontoFinalSecao",
							"numeroLinha",
							"statusLinha",
							"tiposVeiculosPermitidos",
							"piso1AB",
							"piso2AB",
							"piso1BA",
							"piso2BA",
							"via",
							"tipoLinha",
							"tipoLigacao",
							"hierarquizacao",
							"dtInicio",
							"picoInicioManhaAB",
							"picoFimManhaAB",
							"picoInicioTardeAB",
							"picoFimTardeAB",
							"duracaoViagemPicoAB",
							"duracaoViagemForaPicoAB",
							"picoInicioManhaAB",
							"picoFimManhaAB",
							"picoInicioTardeBA",
							"picoFimTardeBA",
							"duracaoViagemPicoBA",
							"duracaoViagemForaPicoBA"};
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
		return "Linhas_" + df.format( Calendar.getInstance().getTime() ) + ".xls";
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
		return new String[]{"Empresa",
							"Linha",
							"Seções",
							"Nome da Linha",
							"Status",
							"Tipos de Veículos Utilizados",
							"Extensão A-B",
							"Extensão B-A",
							"Data de Início",
							"Data de Término"};
	}

	/**
	 * Excel header linhas com secoes.
	 *
	 * @return the string[]
	 */
	public String[] excelHeaderLinhasComSecoes()
	{
		return new String[]{"Empresa",
							"Cód. Linha",
							"Cód. Seção",
							"Nome da Linha",
							"Ponto Inicial Seção",
							"Ponto Final Seção",
							"Número Linha",
							"Status",
							"Tipos de Veículos Permitidos",
							"Piso I A-B",
							"Piso II A-B",
							"Piso I B-A",
							"Piso II B-A",
							"Via",
							"Tipo de Linha",
							"Tipo da Ligação",
							"Hierarquização",
							"Data de Início",
							"Início Horário de Pico Manhã A-B",
							"Fim Horário de Pico Manhã A-B",
							"Início Horário de Pico Tarde A-B",
							"Fim Horário de Pico Tarde A-B",
							"Duração Viagem Horário de Pico A-B",
							"Duração Viagem Fora Horário de Pico A-B",
							"Início Horário de Pico Manhã B-A",
							"Fim Horário de Pico Manhã B-A",
							"Início Horário de Pico Tarde B-A",
							"Fim Horário de Pico Tarde B-A",
							"Duração Viagem Horário de Pico B-A",
							"Duração Viagem Fora Horário de Pico B-A"};
	}

	/**
	 * Exportar com secoes.
	 *
	 * @param entityForSearch
	 *            the entity for search
	 * @return the download
	 */
	public Download exportarComSecoes( final Linha entityForSearch )
	{
		final List<Linha> linhas = list( entityForSearch );

		final List<LinhaDTO> data = geraDTOLinhasComSecoes( linhas );
		final SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy" );
		final String nomeArquivo = "Linhas_com_Seções_" + df.format( Calendar.getInstance().getTime() ) + ".xls";

		File excelFile = null;
		try
		{
			excelFile = new Excel()
				.withHeader( excelHeaderLinhasComSecoes() )
				.setHeightHeader( 10 )
				.useCenterHeader( Boolean.TRUE )
				.useBoldHeader( Boolean.TRUE )
				.useNumericCellType( Boolean.TRUE )
				.from( data )
				.withAtributes( excelAttributesNameLinhaComSecoes() )
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
		final FileDownload fileDownload = new FileDownload( excelFile, contentType, nomeArquivo );
		if ( excelFile.exists() )
		{
			excelFile.delete();
		}
		return fileDownload;
	}

	/**
	 * Gera dto linhas com secoes.
	 *
	 * @param linhas
	 *            the linhas
	 * @return the list
	 */
	private List<LinhaDTO> geraDTOLinhasComSecoes( final List<Linha> linhas )
	{
		final List<LinhaDTO> listDto = new ArrayList<LinhaDTO>();

		if ( Check.isNotNull( linhas ) )
		{
			for ( final Linha linha : linhas )
			{
				final LinhaVigencia lv = linha.getLinhaVigente();
				for ( final Secao secao : lv.getSecoes() )
				{
					final LinhaDTO dto = new LinhaDTO();
					dto.setEmpresa( lv.getEmpresa().getNome() );
					dto.setCodigoLinha( lv.getCodigo() );
					dto.setNomeLinha( lv.getItinerarioIda() );
					dto.setNumeroLinha( lv.getNumeroLinha() );
					dto.setStatusLinha( lv.getStatus().getNomeFormatado() );
					dto.setTiposVeiculosPermitidos( lv.getListagemTipoDeVeiculosUtilizados() );
					dto.setPiso1AB( lv.getPiso1AB() );
					dto.setPiso2AB( lv.getPiso2AB() );
					dto.setPiso1BA( lv.getPiso1BA() );
					dto.setPiso2BA( lv.getPiso2BA() );
					dto.setVia( lv.getVia() );
					dto.setTipoLinha( lv.getListagemTiposDeLinha() );
					dto.setTipoLigacao( lv.getTipoLigacao() );
					dto.setHierarquizacao( lv.getHierarquizacao() );
					dto.setDtInicio( lv.getDataInicio() );
					dto.setPicoInicioManhaAB( lv.getPicoInicioManhaAB() );
					dto.setPicoInicioManhaBA( lv.getPicoInicioManhaBA() );
					dto.setPicoFimManhaAB( lv.getPicoFimManhaAB() );
					dto.setPicoFimManhaBA( lv.getPicoFimManhaBA() );
					dto.setPicoInicioTardeAB( lv.getPicoInicioTardeAB() );
					dto.setPicoInicioTardeBA( lv.getPicoInicioTardeBA() );
					dto.setPicoFimTardeAB( lv.getPicoFimTardeAB() );
					dto.setPicoFimTardeBA( lv.getPicoFimTardeBA() );
					dto.setDuracaoViagemPicoAB( lv.getDuracaoViagemPicoAB() == null ? null : Integer.parseInt( lv
						.getDuracaoViagemPicoAB() ) );
					dto.setDuracaoViagemPicoBA( lv.getDuracaoViagemPicoBA() == null ? null : Integer.parseInt( lv
						.getDuracaoViagemPicoBA() ) );
					dto.setDuracaoViagemForaPicoAB( lv.getDuracaoViagemForaPicoAB() == null ? null : Integer
						.parseInt( lv.getDuracaoViagemForaPicoAB() ) );
					dto.setDuracaoViagemForaPicoBA( lv.getDuracaoViagemForaPicoBA() == null ? null : Integer
						.parseInt( lv.getDuracaoViagemForaPicoBA() ) );

					dto.setCodigoSecao( secao.getCodigo() );
					dto.setPontoInicialSecao( secao.getPontoInicial() );
					dto.setPontoFinalSecao( secao.getPontoFinal() );

					listDto.add( dto );
				}
			}
		}

		return listDto;
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
		final Date dataPrimeiraLinhaVigencia = getLinhaBusiness().getDataPrimeiraLinhaVigencia( umaLinha );
		return dataPrimeiraLinhaVigencia;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
	private LinhaBusiness getLinhaBusiness()
	{
		return this.linhaBusiness;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param empresaId
	 *            FBW-505
	 * @param dataInicio
	 * @return
	 */
	private String getMensagemComBomsQueSeraoApagados( final Long empresaId, final String dataInicio )
	{
		final LinhaBusiness linhaBusiness = ( LinhaBusiness ) this.business;
		final String bomsQueSeraoApagados = linhaBusiness.getMensagemComBomsQueSeraoApagados( dataInicio, empresaId );
		return bomsQueSeraoApagados;
		// TODO Incluir a string retornada no JSON.

	}

	/**
	 * Historico.
	 *
	 * @param entity
	 *            the entity
	 */
	public void historico( final Linha entity )
	{
		final Linha linha = getLinhaBusiness().get( entity.getId() );
		this.result.include( "linhaVigenciaList", getLinhaBusiness().historico( linha ) );
	}

	/**
	 * <p>
	 * FBW-135 Exibe a view importaLinha.jsp de
	 * /bomweb/src/main/webapp/WEB-INF/jsp/linha/importaLinha.jsp Que esta no arquivo
	 * /bomweb/src/main/java/br/com/fetranspor/bomweb/components/CustomRoutes.java
	 * </p>
	 */
	@Post
	@Get
	@Path( "/importaLinha/formUpload" )
	public void importaLinha()
	{
		// DOES NOTHING
	}

	/**
	 * Inclui no result todos os tipos de linha selecionados.
	 */
	@SuppressWarnings( "unchecked" )
	protected void incluiNoResultTodosOsTiposDeLinhaSelecionados()
	{
		final List<TipoDeLinha> tipoLinhasSelecionados = ( List<TipoDeLinha> ) this.result
			.included()
			.get( "tipoLinhas" );
		this.result.include(
			"tipoLinhas",
			this.tipoLinhaBusiness.recuperaTiposLinhaSelecionados( tipoLinhasSelecionados ) );
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
	 * @param umaLinha
	 * @return
	 */
	public boolean isDataRetroativaInvalida( final Linha umaLinha )
	{
		final boolean dataRetroativaInvalida = getLinhaBusiness().isDataRetroativaInvalida( umaLinha );
		return dataRetroativaInvalida;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 */
	private boolean isEdicaoRetroativa( final Linha entity )
	{

		final boolean resultado = getLinhaBusiness().isDataInicioLinhaAnteriorHoje( entity.getLinhaVigente() );
		return resultado;

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param entity
	 * @return
	 * @see br.com.decatron.framework.controller.CrudController#loadEntityForEdit(br.com.decatron.framework.entity.EntityBase)
	 */
	@Override
	protected Linha loadEntityForEdit( Linha entity )
	{
		if ( this.editFuturo )
		{
			entity = getLinhaBusiness().buscarLinhaComVigenciaFutura( entity );
			return entity;
		}
		return super.loadEntityForEdit( entity );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 */
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
	 * </p>
	 *
	 * @return
	 */
	private boolean perfilLogadoIsEmpresa()
	{
		final List listRoles = getRoles();
		for ( final Object object : listRoles )
		{
			final Perfil perfilLogado = ( Perfil ) object;
			if ( perfilLogado.isEmpresa() )
			{
				return true;
			}// if
		}// for
		return false;
	}

	/**
	 * Save.
	 *
	 * @param entity
	 *            the entity
	 * @param tipoLinhas
	 *            the tipo linhas
	 * @param secoes
	 *            FBW-320
	 */
	public void save( final Linha entity, final List<TipoDeLinha> tipoLinhas, final List<Secao> secoes )
	{
		this.beforeSave( entity, tipoLinhas );
		try
		{
			// FBW-320
			if ( ( secoes != null ) && ( secoes.size() > 0 ) )
			{
				final LinhaVigencia linhaVigente = entity.getLinhaVigente();
				linhaVigente.setSecoes( secoes );
			}// if

			final LinhaBusiness linhaBusiness = ( LinhaBusiness ) this.business;
			linhaBusiness.save( entity, tipoLinhas );
			final String saveMessage = saveMessage();
			addInfoMessage( saveMessage );
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e );
		}
		this.result.redirectTo( LinhaController.class ).list();
	}

	/**
	 * Secoes.
	 *
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @return the list
	 */
	public List<Secao> secoes( final LinhaVigencia linhaVigencia )
	{
		return getLinhaBusiness().pesquisarSecoesPorLinhaVigenteSecoes( linhaVigencia );
	}

	/**
	 * Seleciona veiculos utilizados interface.
	 *
	 * @param tiposLinha
	 *            the tipos linha
	 * @param tiposVeiculo
	 *            the tipos veiculo
	 * @return the list
	 */
	private List<TipoDeLinha> selecionaVeiculosUtilizadosInterface(
		final List<TipoDeLinha> tiposLinha,
		final List<TipoDeVeiculo> tiposVeiculo )
	{
		final Map<Long, List<Long>> idsSelecionados = getLinhaBusiness().getIdsTiposLinhaTiposVeiculo( tiposVeiculo );

		for ( final TipoDeLinha tipoDeLinha : tiposLinha )
		{
			final List<TipoDeLinhaTipoDeVeiculo> tltipoVeiculo = tipoDeLinha.getTiposDeLinhaTiposVeiculo();
			for ( final TipoDeLinhaTipoDeVeiculo tipoDeLinhaTipoDeVeiculo : tltipoVeiculo )
			{
				final TipoDeVeiculo veiculo = tipoDeLinhaTipoDeVeiculo.getTipoDeVeiculo();
				final boolean selecionado = idsSelecionados.get( tipoDeLinha.getId() ) == null
					? false
					: idsSelecionados.get( tipoDeLinha.getId() ).contains( veiculo.getId() );
				tipoDeLinhaTipoDeVeiculo.setTipoDeVeiculo( new TipoDeVeiculo(
					veiculo.getId(),
					veiculo.getDescricao(),
					selecionado ) );
			}
		}
		return tiposLinha;
	}

	/**
	 * Update.
	 *
	 * @param entity
	 *            the entity
	 * @param tipoLinhas
	 *            the tipo linhas
	 * @param tipoDeVeiculos
	 *            the tipo de veiculos
	 * @param secoes
	 *            the secoes
	 */

	public void update(
		final Linha entity,
		final List<TipoDeLinha> tipoLinhas,
		final List<TipoDeVeiculo> tipoDeVeiculos,
		final List<Secao> secoes )
	{
		final Result result = this.result;
		final MutableRequest request = VRaptorRequestHolder.currentRequest().getRequest();
		final String justificativa = request.getParameter( "justificaivaHidden" );
		try
		{
			final LinhaVigencia linhaVigente = entity.getLinhaVigente();
			linhaVigente.setSecoes( secoes );
			beforeUpdate( entity, tipoLinhas, tipoDeVeiculos );

			final LinhaBusiness linhaBusiness = ( LinhaBusiness ) this.business;
			if ( isEdicaoRetroativa( entity ) )
			{
				linhaBusiness.updateRetroativo( entity, justificativa, tipoLinhas );
				addInfoMessage( updateMessage() );

			}// if
			else
			{
				linhaBusiness.update( entity, tipoLinhas, tipoDeVeiculos );
				addInfoMessage( updateMessage() );

			}

			result.redirectTo( LinhaController.class ).list();
		}
		catch ( final OrphanBomLinhaException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if

			addErrorMessage( "Ocorreu um erro interno ao executar a edição retroativa desta linha, favor entrar em contato com o Administrador informando o erro abaixo.<br /><br /> ERRO:"
				+ e );

			result.redirectTo( LinhaController.class ).edit( entity );
		}// catch
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			result.redirectTo( LinhaController.class ).edit( entity );
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
			result.redirectTo( LinhaController.class ).edit( entity );
		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
		}
		catch ( final DaoException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
		}
		catch ( final IllegalAccessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
		}
		catch ( final InvocationTargetException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
		}
		catch ( final NoSuchMethodException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}
		}
	}

	/**
	 * <p>
	 * FWB-135 Recebe uma planilha em padrão previamente definido e faz as alterações de linhas
	 * indicadas na planilha! E faz a exclusão de BOMs afetados de acordo com a vigência da tarifa.
	 *
	 * @author marcio.dias
	 * @param file
	 *            the file
	 * @return Retorna uma msg de sucesso ou erro.
	 *         </p>
	 * @see - Faz o upLoda do aquivo<br>
	 *      - Alterar as linhas<br>
	 *      - Exclusão de BOMs.<br>
	 *      - Registro de log via banco de dados de todas as ações efetuadas viabilizando a
	 *      auditoria no sistema. Serão registrados: (1) A alteração feita, (2) Usuário que fez, (3)
	 *      Data e hora, (4) IP da máquina onde foi solicitada a alteração.<br>
	 *      - As partes envolvidas (empresa e Detro) devem receber um email informando que os BOMs
	 *      dos meses em questão foram excluídos<br>
	 */
	public void uploadLinha( final UploadedFile file )
	{
		File importFile = null;

		try
		{
			final String uploadFolder = this.configuracaoBusiness.buscarDiretorioUpload();

			importFile = new File( uploadFolder, file.getFileName() );

			IOUtils.copyLarge( file.getFile(), new FileOutputStream( importFile ) );
			final int arquivo = getLinhaBusiness().importExcelLh( importFile );
			if ( arquivo > 0 )
			{

				addInfoMessage( arquivo + " linha(s) importada(s) com sucesso." );

				if ( !getLinhaBusiness().naoEncontrouSecaoList.isEmpty() )
				{
					final int qtdNaoImportado = getLinhaBusiness().naoEncontrouSecaoList.size();
					String msg = "";
					for ( final String registro : getLinhaBusiness().naoEncontrouSecaoList )
					{
						msg = msg + registro + "; ";
					}// for
					addInfoMessage( qtdNaoImportado
						+ " linha(s) não importada(s), registro(s) não encontrado(s) no sistema: "
						+ msg );
					getLinhaBusiness().naoEncontrouSecaoList.clear();
				}// if
				this.result.redirectTo( getClass() ).list();
			}
			else
			{
				if ( !getLinhaBusiness().naoEncontrouSecaoList.isEmpty() )
				{
					final int qtdNaoImportado = getLinhaBusiness().naoEncontrouSecaoList.size();
					String msg = "";
					for ( final String registro : getLinhaBusiness().naoEncontrouSecaoList )
					{
						msg = msg + registro + "; ";
					}// for
					addInfoMessage( qtdNaoImportado
						+ " linha(s) não importada(s), registro(s) não encontrado(s) no sistema: "
						+ msg );
					getLinhaBusiness().naoEncontrouSecaoList.clear();
				}// if

				addErrorMessage( "Nenhuma Linha importada. Favor verificar arquivo." );
				this.result.redirectTo( LinhaController.class ).importaLinha();
			}
		}
		catch ( final FileNotFoundException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Arquivo não encontrado." );
			this.result.redirectTo( LinhaController.class ).importaLinha();
		}
		catch ( final IOException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao abrir arquivo." );
			this.result.redirectTo( LinhaController.class ).importaLinha();
		}
		catch ( final BusinessException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( e.getMessage() );
			this.result.redirectTo( LinhaController.class ).importaLinha();
		}
		catch ( final Exception e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			addErrorMessage( "Erro ao importar os dados.\n\r" + e );
			this.result.redirectTo( LinhaController.class ).importaLinha();
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
	 * </p>
	 *
	 * @param dataVigencia
	 * @param empresaId
	 * @throws ParseException
	 */
	@Get
	@Path( "/linha/valida.json" )
	public void validateDataVigencia( final String dataVigencia, final Long empresaId )
		throws ParseException
	{

		final boolean isDetroAdmin = perfilLogadoIsDetroAdmin();
		final boolean canExecute = canExecute( dataVigencia );
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
	 * <p>
	 * Field <code>editPassado</code>
	 * </p>
	 */
	private Boolean edicaoRetroativa = Boolean.FALSE;

	/**
	 * The edit futuro.
	 */
	private Boolean editFuturo = Boolean.FALSE;

	/**
	 * The empresa business.
	 */
	private final EmpresaBusiness empresaBusiness;

	/**
	 * The linha business.
	 */
	private final LinhaBusiness linhaBusiness;

	/**
	 * The tipo linha business.
	 */
	private final TipoDeLinhaBusiness tipoLinhaBusiness;
}

package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.com.caelum.vraptor.view.Results;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.excel.Excel;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.business.BomBusiness;
import br.com.fetranspor.bomweb.business.LinhaBusiness;
import br.com.fetranspor.bomweb.components.Utils;
import br.com.fetranspor.bomweb.dto.FiltroRelatorioDTO;
import br.com.fetranspor.bomweb.dto.FiltroRelatorioExcelDTO;
import br.com.fetranspor.bomweb.dto.RelatorioDTO;
import br.com.fetranspor.bomweb.entity.Empresa;
import br.com.fetranspor.bomweb.entity.Linha;
import br.com.fetranspor.bomweb.entity.LinhaVigencia;
import br.com.fetranspor.bomweb.entity.Perfil;
import br.com.fetranspor.bomweb.entity.Usuario;
import br.com.fetranspor.bomweb.jquery.datatable.RelatorioRowFactory;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mchange.util.AssertException;

/**
 * The Class RelatorioController.
 */
@Resource
public class RelatorioController
	extends VRaptorController
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( RelatorioController.class );

	/**
	 * Instantiates a new relatorio controller.
	 *
	 * @param provider
	 *            the provider
	 * @param bomBusiness
	 *            the bom business
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 * @param linhaBusiness
	 *            the linha business
	 */
	public RelatorioController(
		final VRaptorProvider provider,
		final BomBusiness bomBusiness,
		final Result result,
		final Validator validator,
		final LinhaBusiness linhaBusiness )
	{
		super( provider, result, validator );
		this.bomBusiness = bomBusiness;
	}

	/**
	 * Adds the j query data table result as json to result.
	 *
	 * @param filtro
	 *            the filtro
	 */
	@Get
	@Post
	@Path( "/relatorio/dataTableResult.json" )
	public void addJQueryDataTableResultAsJSONToResult( final FiltroRelatorioDTO filtro )
	{
		final RelatorioRowFactory rowFactory = new RelatorioRowFactory( this.provider.getAuthorization() );
		final List<RelatorioDTO> data = list( filtro );

		super.addJQueryDataTableResultAsJSONToResult( rowFactory, data );
	}

	/**
	 * Busca linhas.
	 *
	 * @param parentId
	 *            the parent id
	 */
	@Get
	@Path( "/relatorio/buscaLinhas.json" )
	public void buscaLinhas( final String[] parentId )
	{
		List<LinhaVigencia> linhasVigencia = new ArrayList<LinhaVigencia>();

		if ( Check.isNotNull( parentId ) )
		{
			final List<Long> idsEmpresa = new ArrayList<Long>();
			for ( final String element : parentId )
			{
				final Long idPai = Long.valueOf( element );
				idsEmpresa.add( idPai );
			}

			final List<Linha> linhas = this.bomBusiness.getLinhasDoBomPelosIdsEmpresas( idsEmpresa );
			final List<Long> idsLinhas = new ArrayList<Long>();
			if ( Check.isNotNull( linhas ) )
			{
				for ( final Linha linha : linhas )
				{
					idsLinhas.add( linha.getId() );
				}

				linhasVigencia = this.bomBusiness.getLinhasVigenciaDoBomPelosIdsLinhas( idsLinhas );
			}
		}

		// FBW-319
		for ( final LinhaVigencia linhaVigencia : linhasVigencia )
		{
			linhaVigencia.populateForJson();
		}// for

		this.result.use( Results.json() ).withoutRoot().from( linhasVigencia ).serialize();
	}

	/**
	 * Exportar.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the download
	 */
	public Download exportar( final FiltroRelatorioExcelDTO filtro )
	{
		final FiltroRelatorioDTO filtroRelatorioDTO = new FiltroRelatorioDTO(
			filtro.getEmpresasComoString(),
			filtro.getLinhasComoString(),
			filtro.getTiposVeiculoComoString(),
			filtro.getCamposRelatorioComoString(),
			filtro.getDataInicial(),
			filtro.getDataFinal() );
		final List<RelatorioDTO> data = list( filtroRelatorioDTO );

		final SimpleDateFormat df = new SimpleDateFormat( "ddMMyyyy" );
		final String nomeArquivo = "Relatorio_do_BOM_" + df.format( Calendar.getInstance().getTime() ) + ".xls";

		final List<String> camposRelatorio = new ArrayList<String>( Arrays.asList(
			"empresa",
			"codigoLinha",
			"nomeLinha",
			"mesReferencia",
			"descricaoTipoVeiculo" ) );
		camposRelatorio.addAll( filtroRelatorioDTO.getCamposRelatorio() );

		final List<String> header = new ArrayList<String>();
		final HashMap<String, String> mapCamposRelatorio = this.bomBusiness.getMapCamposRelatorio();
		for ( final String campo : camposRelatorio )
		{
			String cabecalhoCampo = mapCamposRelatorio.get( campo );

			if ( Check.isNull( cabecalhoCampo ) )
			{
				cabecalhoCampo = Utils.splitCamelCase( campo );
			}

			header.add( cabecalhoCampo );
		}

		File excelFile = null;
		try
		{
			excelFile = new Excel()
				.withHeader( header.toArray( new String[0] ) )
				.setHeightHeader( 10 )
				.useCenterHeader( Boolean.TRUE )
				.useBoldHeader( Boolean.TRUE )
				.useNumericCellType( Boolean.TRUE )
				.from( data )
				.withAtributes( camposRelatorio.toArray( new String[0] ) )
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
	 * List.
	 *
	 * @param filtro
	 *            the filtro
	 * @return the list
	 */
	@Get
	@Post
	@Path( "/relatorio/list" )
	public List<RelatorioDTO> list( FiltroRelatorioDTO filtro )
	{
		if ( Check.isNull( filtro ) )
		{
			filtro = new FiltroRelatorioDTO();
		}

		this.result.include( "filtro", filtro );

		final boolean temPerfilEmpresa = temPerfilEmpresa();

		this.result.include( "perfilEmpresa", temPerfilEmpresa );
		if ( temPerfilEmpresa )
		{
			final Empresa empresa = ( ( Usuario ) getUser() ).getEmpresa();

			// Nao ocorre na pratica.
			if ( Check.isNull( empresa ) || Check.isNull( empresa.getId() ) )
			{
				throw new AssertException();
			}

			filtro.addEmpresa( empresa );

			this.result.include( "empresa", empresa );
			this.result.include( "empresas", this.bomBusiness.getEmpresasComBomFechadoExcluindoEmpresa( empresa ) );
			this.result.include(
				"linhas",
				this.bomBusiness.getLinhasVigenciaDoBomPelosIdsEmpresas( Arrays.asList( empresa.getId() ) ) );
		}
		else
		{
			this.result.include( "empresas", this.bomBusiness.getEmpresasComBomFechado() );
		}

		this.result.include( "tipoVeiculos", this.bomBusiness.getTiposVeiculoDeBomLinha() );

		validaFiltro( filtro );

		return this.bomBusiness.listaRelatorioComFiltro( filtro );
	}

	/**
	 * Tem perfil empresa.
	 *
	 * @return true, if successful
	 */
	protected boolean temPerfilEmpresa()
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		return perfil.isEmpresa();
	}

	/**
	 * Valida filtro.
	 *
	 * @param filtro
	 *            the filtro
	 */
	protected void validaFiltro( final FiltroRelatorioDTO filtro )
	{
		if ( Check.isNull( filtro ) )
		{
			return;
		}

		if ( Check.isNotBlank( filtro.getDataInicial() ) && Check.isNotBlank( filtro.getDataFinal() ) )
		{
			final SimpleDateFormat dateFormatter = new SimpleDateFormat( "MM/yyyy" );
			dateFormatter.setLenient( false );
			try
			{
				final Date dataInicial = dateFormatter.parse( filtro.getDataInicial() );
				final Date dataFinal = dateFormatter.parse( filtro.getDataFinal() );

				if ( dataInicial.after( dataFinal ) )
				{
					this.validator.add( new ValidationMessage(
						"A data inicial deve ser anterior a final.",
						"Favor verificar as datas" ) );
					this.validator.onErrorUsePageOf( RelatorioController.class ).list( null );
				}
			}
			catch ( final ParseException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
				this.validator.add( new ValidationMessage( "A data informada inválida.", "Favor verificar as datas" ) );
				this.validator.onErrorUsePageOf( RelatorioController.class ).list( null );
			}
		}
	}

	/**
	 * The bom business.
	 */
	private final BomBusiness bomBusiness;
}

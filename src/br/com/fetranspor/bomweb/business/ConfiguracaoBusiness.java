package br.com.fetranspor.bomweb.business;

import br.com.caelum.vraptor.ioc.Component;
import br.com.decatron.framework.business.VRaptorBusiness;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.decatron.framework.util.Check;
import br.com.decatron.framework.util.DateUtil;
import br.com.fetranspor.bomweb.dao.ConfiguracaoDAO;
import br.com.fetranspor.bomweb.entity.Configuracao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class ConfiguracaoBusiness.
 */
@Component
public class ConfiguracaoBusiness
	extends VRaptorBusiness<Configuracao>
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( ConfiguracaoBusiness.class );

	/**
	 * Instantiates a new configuracao business.
	 *
	 * @param provider
	 *            the provider
	 * @param dao
	 *            the dao
	 */
	public ConfiguracaoBusiness( final VRaptorProvider provider, final ConfiguracaoDAO dao )
	{
		super( provider );
		this.dao = dao;
		this.configuracaoDAO = dao;
	}

	/**
	 * Atualizar configuracao.
	 *
	 * @param configuracao
	 *            the configuracao
	 */
	public void atualizarConfiguracao( final Configuracao configuracao )
	{
		this.configuracaoDAO.atualizarConfiguracao( configuracao );
	}

	/**
	 * Buscar configuracao bom.
	 *
	 * @return the configuracao
	 */
	public Configuracao buscarConfiguracaoBom()
	{
		if ( Check.isNull( this.configuracaoBom ) )
		{
			this.configuracaoBom = this.configuracaoDAO.buscarConfiguracaoBom();
		}
		return this.configuracaoBom;
	}

	/**
	 * <p>
	 * </p>
	 * 
	 * @return
	 */
	public Configuracao buscarConfiguracaoBomReaberturaPelaEmpresa()
	{
		if ( Check.isNull( this.configuracaoBomReaberturaPelaEmpresa ) )
		{
			this.configuracaoBomReaberturaPelaEmpresa = this.configuracaoDAO
				.buscarConfiguracaoBomReaberturaPelaEmpresa();
		}
		return this.configuracaoBomReaberturaPelaEmpresa;
	}

	/**
	 * Buscar configuracao bom reabertura.
	 *
	 * @return the configuracao
	 */
	public Configuracao buscarConfiguracaoBomReaberturaPeloDetro()
	{
		if ( Check.isNull( this.configuracaoBomReaberturaPeloDetro ) )
		{
			this.configuracaoBomReaberturaPeloDetro = this.configuracaoDAO.buscarConfiguracaoBomReaberturaPeloDetro();
		}
		return this.configuracaoBomReaberturaPeloDetro;
	}

	/**
	 * Buscar configuracoes bom.
	 *
	 * @return the list
	 */
	public List<Configuracao> buscarConfiguracoesBom()
	{
		return this.configuracaoDAO.buscarConfiguracoesBom();
	}

	/**
	 * Buscar data inicio sistema.
	 *
	 * @return the date
	 */
	public Date buscarDataInicioSistema()
	{
		if ( Check.isNull( this.dataInicioSistema ) )
		{
			final String valor = this.configuracaoDAO.buscarConfiguracaoDataInicioSistema().getValor();
			try
			{
				this.dataInicioSistema = DateUtil.convertToDate( "dd/MM/yyyy", valor );
			}
			catch ( final ParseException e )
			{
				if ( LOG.isErrorEnabled() )
				{
					LOG.error( e, e );
				}// if
				return null;
			}
		}
		return this.dataInicioSistema;
	}

	/**
	 * Buscar diretorio pdf.
	 *
	 * @return the string
	 */
	public String buscarDiretorioPDF()
	{
		return System.getProperty( "catalina.base" )
			+ System.getProperty( "file.separator" )
			+ "webapps"
			+ System.getProperty( "file.separator" );
	}

	/**
	 * Buscar diretorio upload.
	 *
	 * @return the string
	 */
	public String buscarDiretorioUpload()
	{
		return System.getProperty( "catalina.base" )
			+ System.getProperty( "file.separator" )
			+ "webapps"
			+ System.getProperty( "file.separator" );
	}

	/**
	 * Buscar diretorio xls.
	 *
	 * @return the string
	 */
	public String buscarDiretorioXLS()
	{
		return System.getProperty( "catalina.base" )
			+ System.getProperty( "file.separator" )
			+ "webapps"
			+ System.getProperty( "file.separator" );
	}

	/**
	 * Buscar email detro.
	 *
	 * @return the string
	 */
	public String buscarEmailDetro()
	{
		if ( Check.isNull( this.configuracaoEmailDetro ) )
		{
			this.configuracaoEmailDetro = this.configuracaoDAO.buscarConfiguracaoEmailDetro();
		}
		return this.configuracaoEmailDetro.getValor();
	}

	/**
	 * The configuracao bom.
	 */
	private Configuracao configuracaoBom;

	/**
	 * The configuracao bom reabertura.
	 */
	private Configuracao configuracaoBomReaberturaPeloDetro;

	/**
	 * <p>
	 * Field <code>configuracaoBomReaberturaPelaEmpresa</code>
	 * </p>
	 */
	private Configuracao configuracaoBomReaberturaPelaEmpresa;

	/**
	 * The configuracao dao.
	 */
	private final ConfiguracaoDAO configuracaoDAO;

	/**
	 * The configuracao email detro.
	 */
	private Configuracao configuracaoEmailDetro;

	/**
	 * The data inicio sistema.
	 */
	private Date dataInicioSistema;

}

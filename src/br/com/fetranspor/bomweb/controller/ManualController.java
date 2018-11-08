package br.com.fetranspor.bomweb.controller;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.interceptor.download.Download;
import br.com.caelum.vraptor.interceptor.download.FileDownload;
import br.com.decatron.framework.controller.vraptor.VRaptorController;
import br.com.decatron.framework.provider.VRaptorProvider;
import br.com.fetranspor.bomweb.entity.Perfil;

import java.io.File;

/**
 * The Class ManualController.
 */
@Resource
public class ManualController
	extends VRaptorController
{

	/**
	 * The Constant NOME_ARQUIVO_MANUAL_DETRO.
	 */
	public static final String NOME_ARQUIVO_MANUAL_DETRO = "Manual_Perfil_DETRO.pdf";

	/**
	 * The Constant NOME_ARQUIVO_MANUAL_EMPRESA.
	 */
	public static final String NOME_ARQUIVO_MANUAL_EMPRESA = "Manual_Perfil_Empresa.pdf";

	/**
	 * Instantiates a new manual controller.
	 *
	 * @param provider
	 *            the provider
	 * @param result
	 *            the result
	 * @param validator
	 *            the validator
	 */
	public ManualController( final VRaptorProvider provider, final Result result, final Validator validator )
	{
		super( provider, result, validator );
	}

	/**
	 * Download.
	 *
	 * @return the download
	 */
	public Download download()
	{
		final Perfil perfil = ( Perfil ) getRoles().iterator().next();
		String fileName = "";
		if ( perfil.isEmpresa() )
		{
			fileName = NOME_ARQUIVO_MANUAL_EMPRESA;
		}
		else
		{
			fileName = NOME_ARQUIVO_MANUAL_DETRO;
		}

		final File file = new File( System.getProperty( "catalina.base" )
			+ System.getProperty( "file.separator" )
			+ "webapps"
			+ System.getProperty( "file.separator" )
			+ fileName );
		final String contentType = "application/pdf";
		final FileDownload fileDownload = new FileDownload( file, contentType, file.getName() );
		return fileDownload;
	}
}

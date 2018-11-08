package br.com.fetranspor.bomweb.components;

import br.com.caelum.vraptor.http.FormatResolver;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.com.caelum.vraptor.view.DefaultPathResolver;

/**
 * The Class CustomPathResolver.
 */
@Component
public class CustomPathResolver
	extends DefaultPathResolver
{

	/**
	 * Instantiates a new custom path resolver.
	 *
	 * @param resolver
	 *            the resolver
	 */
	public CustomPathResolver( final FormatResolver resolver )
	{
		super( resolver );
		this.resolver = resolver;
	}

	/**
	 * Extract file name.
	 *
	 * @param method
	 *            the method
	 * @return the string
	 */
	protected String extractFileName( final ResourceMethod method )
	{
		final String fileName = method.getMethod().getName();
		if ( fileName.equals( "insert" )
			|| fileName.equals( "edit" )
			|| fileName.equals( "view" )
			|| fileName.equals( "editUser" )
			|| fileName.equals( "editFuturo" ) )
		{
			return "form";
		}
		return fileName;
	}

	/**
	 * Lower first character.
	 *
	 * @param baseName
	 *            the base name
	 * @return the string
	 */
	protected String lowerFirstCharacter( final String baseName )
	{
		return baseName.toLowerCase().substring( 0, 1 ) + baseName.substring( 1, baseName.length() );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param method
	 * @return
	 * @see br.com.caelum.vraptor.view.DefaultPathResolver#pathFor(br.com.caelum.vraptor.resource.ResourceMethod)
	 */
	@Override
	public String pathFor( final ResourceMethod method )
	{

		final String format = this.resolver.getAcceptFormat();

		String suffix = "";
		if ( ( format != null ) && !format.equals( "html" ) )
		{
			suffix = "." + format;
		}
		final String name = method.getResource().getType().getSimpleName();
		final String folderName = extractControllerFromName( name );
		return getPrefix() + folderName + "/" + extractFileName( method ) + suffix + "." + getExtension();
	}

	/**
	 * The resolver.
	 */
	private final FormatResolver resolver;

}

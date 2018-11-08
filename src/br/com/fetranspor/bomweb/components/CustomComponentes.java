package br.com.fetranspor.bomweb.components;

import br.com.caelum.vraptor.ComponentRegistry;
import br.com.decatron.framework.vraptor.VraptorCustomComponentes;
import br.com.decatron.framework.vraptor.converter.CustomBigDecimalConverter;
import br.com.decatron.framework.vraptor.converter.CustomBooleanConverter;
import br.com.decatron.framework.vraptor.converter.CustomDoubleConverter;
import br.com.decatron.framework.vraptor.converter.CustomIntegerConverter;

/**
 * The Class CustomComponentes.
 */
public class CustomComponentes
	extends VraptorCustomComponentes
{

	/**
	 * <p>
	 * </p>
	 *
	 * @param registry
	 * @see br.com.decatron.framework.vraptor.VraptorCustomComponentes#registerCustomComponents(br.com.caelum.vraptor.ComponentRegistry)
	 */
	@Override
	protected void registerCustomComponents( final ComponentRegistry registry )
	{
		super.registerCustomComponents( registry );

		registry.register( CustomBigDecimalConverter.class, CustomBigDecimalConverter.class );
		registry.register( CustomDoubleConverter.class, CustomDoubleConverter.class );
		registry.register( CustomIntegerConverter.class, CustomIntegerConverter.class );
		registry.register( CustomBooleanConverter.class, CustomBooleanConverter.class );
	}

}

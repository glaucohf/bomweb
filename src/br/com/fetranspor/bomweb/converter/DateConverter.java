package br.com.fetranspor.bomweb.converter;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.converter.ConversionError;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Convert( Date.class )
public class DateConverter
	implements Converter<Date>
{

	@Override
	public Date convert( final String arg0, final Class< ? extends Date> arg1, final ResourceBundle arg2 )
	{
		if ( ( arg0 == null ) || arg0.equals( "" ) )
		{
			return null;
		}
		try
		{
			final DateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
			format.setLenient( false );
			return format.parse( arg0 );
		}
		catch ( final ParseException e )
		{
			throw new ConversionError( "Data Inválida." );
		}
	}

}

package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.util.Check;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class InformacoesLog.
 */
public abstract class InformacoesLog
	extends BomWebEntityBase
{

	/**
	 * The Constant SEPARADOR.
	 */
	public static final String SEPARADOR = "<BR>";

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 4421927850302906374L;

	static
	{
		// TODO FIXME mover para uma classe de configurações iniciais
		// FBW-234
		final BeanUtilsBean beanUtilsBean = BeanUtilsBean.getInstance();
		final ConvertUtilsBean convertUtils = beanUtilsBean.getConvertUtils();
		convertUtils.register( false, false, 0 );
	}

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( InformacoesLog.class );

	/**
	 * <p>
	 * </p>
	 */
	public InformacoesLog()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bean
	 * @return
	 */
	protected int getBeanHash( final InformacoesLog bean )
	{
		final int hash1;
		if ( bean == null )
		{
			hash1 = 0;
		}// if
		else
		{
			final int beanHash = bean.hashCode();
			hash1 = beanHash;
		}// else
		return hash1;
	}

	/**
	 * Tripao.
	 *
	 * @return the string
	 */
	public String tripao()
	{

		// TODO Migrar para o framework

		this.excludeProperties.add( "excludeProperties" );
		this.excludeProperties.add( "SEPARADOR" );
		this.excludeProperties.add( "serialVersionUID" );
		this.excludeProperties.add( "uuid" );
		this.excludeProperties.add( "active" );
		this.excludeProperties.add( "ativo" );
		this.excludeProperties.add( "trocarSenha" );
		this.excludeProperties.add( "temBomLinhaFechado" );
		this.excludeProperties.add( "temBomLinhaAberto" );
		this.excludeProperties.add( "temLinhaExcluida" );

		final StringBuffer buffer = new StringBuffer();

		final List<Field> fields = new Mirror().on( this.getClass() ).reflectAll().fields();
		for ( final Field field : fields )
		{
			if ( !this.excludeProperties.contains( field.getName() ) )
			{
				final Object value = new Mirror().on( this ).get().field( field );
				try
				{
					if ( Check.isNotNull( value ) && !Check.isCollection( value ) )
					{
						buffer.append( field.getName().toUpperCase() + ":" + value + SEPARADOR );
					}
				}
				catch ( final Exception e )
				{
					if ( LOG.isErrorEnabled() )
					{
						LOG.error( e, e );
					}// if
				}

			}
		}

		return buffer.toString();
	}

	/**
	 * The exclude properties.
	 */
	protected List<String> excludeProperties = new ArrayList<String>();
}

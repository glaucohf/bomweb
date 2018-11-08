package br.com.fetranspor.bomweb.entity;

import br.com.fetranspor.bomweb.exception.CloneFailException;

import java.lang.reflect.InvocationTargetException;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class TipoDeLinhaTipoDeVeiculo.
 */
@Entity
@Audited
@Table( name = "tipos_linha_tipos_veiculo", uniqueConstraints = {@UniqueConstraint( columnNames = {	"tipoDeLinha_Id",
																									"tipoDeVeiculo_Id"} )} )
@Inheritance( strategy = InheritanceType.JOINED )
public class TipoDeLinhaTipoDeVeiculo
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 9018715000971785346L;

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( LinhaVigencia.class );

	/**
	 * Instantiates a new tipo de linha tipo de veiculo.
	 */
	public TipoDeLinhaTipoDeVeiculo()
	{
		super();
	}

	/**
	 * Instantiates a new tipo de linha tipo de veiculo.
	 *
	 * @param tipoDeVeiculo
	 *            the tipo de veiculo
	 * @param tipoDeLinha
	 *            the tipo de linha
	 */
	public TipoDeLinhaTipoDeVeiculo( final TipoDeVeiculo tipoDeVeiculo, final TipoDeLinha tipoDeLinha )
	{
		super();
		this.tipoDeVeiculo = tipoDeVeiculo;
		this.tipoDeLinha = tipoDeLinha;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TipoDeLinhaTipoDeVeiculo clone()
	{

		final TipoDeLinhaTipoDeVeiculo tipoDeLinhaTipoDeVeiculo = new TipoDeLinhaTipoDeVeiculo();

		// TODO vir da tela
		try
		{
			PropertyUtils.copyProperties( tipoDeLinhaTipoDeVeiculo, this );

		}
		catch ( final IllegalAccessException e )
		{
			throw new CloneFailException( e );
		}
		catch ( final InvocationTargetException e )
		{
			throw new CloneFailException( e );
		}
		catch ( final NoSuchMethodException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
		}

		tipoDeLinhaTipoDeVeiculo.setId( null );
		return tipoDeLinhaTipoDeVeiculo;

	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param obj
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( final Object obj )
	{
		if ( this == obj )
		{
			return true;
		}
		if ( !super.equals( obj ) )
		{
			return false;
		}
		if ( getClass() != obj.getClass() )
		{
			return false;
		}
		final TipoDeLinhaTipoDeVeiculo other = ( TipoDeLinhaTipoDeVeiculo ) obj;
		if ( this.tipoDeLinha == null )
		{
			if ( other.tipoDeLinha != null )
			{
				return false;
			}
		}
		else if ( !this.tipoDeLinha.equals( other.tipoDeLinha ) )
		{
			return false;
		}
		if ( this.tipoDeVeiculo == null )
		{
			if ( other.tipoDeVeiculo != null )
			{
				return false;
			}
		}
		else if ( !this.tipoDeVeiculo.equals( other.tipoDeVeiculo ) )
		{
			return false;
		}
		return true;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#getId()
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}

	/**
	 * Gets the tipo de linha.
	 *
	 * @return the tipo de linha
	 */
	public TipoDeLinha getTipoDeLinha()
	{
		return this.tipoDeLinha;
	}

	/**
	 * Gets the tipo de veiculo.
	 *
	 * @return the tipo de veiculo
	 */
	public TipoDeVeiculo getTipoDeVeiculo()
	{
		return this.tipoDeVeiculo;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see br.com.decatron.framework.entity.EntityBase#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = super.hashCode();
		result = ( prime * result ) + ( ( this.tipoDeLinha == null ) ? 0 : this.tipoDeLinha.hashCode() );
		result = ( prime * result ) + ( ( this.tipoDeVeiculo == null ) ? 0 : this.tipoDeVeiculo.hashCode() );
		return result;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId( final Long id )
	{
		this.id = id;
	}

	/**
	 * Sets the tipo de linha.
	 *
	 * @param tipoDeLinha
	 *            the new tipo de linha
	 */
	public void setTipoDeLinha( final TipoDeLinha tipoDeLinha )
	{
		this.tipoDeLinha = tipoDeLinha;
	}

	/**
	 * Sets the tipo de veiculo.
	 *
	 * @param tipoDeVeiculo
	 *            the new tipo de veiculo
	 */
	public void setTipoDeVeiculo( final TipoDeVeiculo tipoDeVeiculo )
	{
		this.tipoDeVeiculo = tipoDeVeiculo;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The tipo de veiculo.
	 */
	@NotNull
	@NotAudited
	@ManyToOne
	private TipoDeVeiculo tipoDeVeiculo;

	/**
	 * The tipo de linha.
	 */
	@NotNull
	@ManyToOne( fetch = FetchType.LAZY )
	@NotAudited
	private TipoDeLinha tipoDeLinha;
}

package br.com.fetranspor.bomweb.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.envers.Audited;

/**
 * The Class Linha.
 */
@Entity
@Audited
@Table( name = "linhas" )
@Inheritance( strategy = InheritanceType.JOINED )
public class Linha
	extends InformacoesLog
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 8344786872066534984L;

	/**
	 * Instantiates a new linha.
	 */
	public Linha()
	{
		super();
	}

	/**
	 * Instantiates a new linha.
	 *
	 * @param id
	 *            the id
	 * @param active
	 *            the active
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @param temLinhaFutura
	 *            the tem linha futura
	 */
	public Linha( final Long id, final boolean active, final LinhaVigencia linhaVigencia, final boolean temLinhaFutura )
	{
		super();

		this.id = id;
		this.active = active;
		this.linhaVigente = linhaVigencia;
		this.temLinhaFutura = temLinhaFutura;
	}

	/**
	 * Instantiates a new linha.
	 *
	 * @param id
	 *            the id
	 * @param active
	 *            the active
	 * @param linhaVigencia
	 *            the linha vigencia
	 * @param quantidadeLinhasVigencia
	 *            the quantidade linhas vigencia
	 */
	public Linha(
		final Long id,
		final boolean active,
		final LinhaVigencia linhaVigencia,
		final long quantidadeLinhasVigencia )
	{
		super();

		this.id = id;
		this.active = active;
		this.linhaVigente = linhaVigencia;
		setQuantidadeLinhasVigencia( quantidadeLinhasVigencia );
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
		final Linha other = ( Linha ) obj;
		if ( this.id == null )
		{
			if ( other.id != null )
			{
				return false;
			}
		}
		else if ( !this.id.equals( other.id ) )
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

	// ----------------------------------------------------------------------

	/**
	 * Gets the linha vigente.
	 *
	 * @return the linha vigente
	 */
	public LinhaVigencia getLinhaVigente()
	{
		return this.linhaVigente;
	}

	/**
	 * Gets the quantidade linhas vigencia.
	 *
	 * @return the quantidade linhas vigencia
	 */
	public long getQuantidadeLinhasVigencia()
	{
		return this.quantidadeLinhasVigencia;
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
		result = ( prime * result ) + ( ( this.id == null ) ? 0 : this.id.hashCode() );
		return result;
	}

	// ----------------------------------------------------------------------

	/**
	 * Checks if is futuro.
	 *
	 * @return true, if is futuro
	 */
	public boolean isFuturo()
	{
		return this.futuro;
	}

	/**
	 * Checks if is tem linha futura.
	 *
	 * @return true, if is tem linha futura
	 */
	public boolean isTemLinhaFutura()
	{
		return this.temLinhaFutura;
	}

	/**
	 * Sets the futuro.
	 *
	 * @param futuro
	 *            the new futuro
	 */
	public void setFuturo( final boolean futuro )
	{
		this.futuro = futuro;
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
	 * Sets the linha vigente.
	 *
	 * @param linhaVigente
	 *            the new linha vigente
	 */
	public void setLinhaVigente( final LinhaVigencia linhaVigente )
	{
		this.linhaVigente = linhaVigente;
	}

	/**
	 * Sets the quantidade linhas vigencia.
	 *
	 * @param quantidadeLinhasVigencia
	 *            the new quantidade linhas vigencia
	 */
	public void setQuantidadeLinhasVigencia( final long quantidadeLinhasVigencia )
	{
		this.quantidadeLinhasVigencia = quantidadeLinhasVigencia;
		if ( quantidadeLinhasVigencia > 1 )
		{
			setTemLinhaFutura( Boolean.TRUE );
		}
		else
		{
			setTemLinhaFutura( Boolean.FALSE );
		}
	}

	/**
	 * Sets the tem linha futura.
	 *
	 * @param temLinhaFutura
	 *            the new tem linha futura
	 */
	private void setTemLinhaFutura( final boolean temLinhaFutura )
	{
		this.temLinhaFutura = temLinhaFutura;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The linha vigente.
	 */
	@Transient
	private transient LinhaVigencia linhaVigente = new LinhaVigencia();

	/**
	 * The quantidade linhas vigencia.
	 */
	@Transient
	private long quantidadeLinhasVigencia;

	/**
	 * The tem linha futura.
	 */
	@Transient
	private boolean temLinhaFutura;

	/**
	 * The futuro.
	 */
	@Transient
	private boolean futuro;
}

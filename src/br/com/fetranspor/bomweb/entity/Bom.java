package br.com.fetranspor.bomweb.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;

/**
 * The Class Bom.
 */
@Entity
@Audited
@Table( name = "bom" )
public class Bom
	extends InformacoesLog
	implements Comparable<Bom>
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 6725220104123394241L;

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( Bom.class );

	/**
	 * <p>
	 * </p>
	 *
	 * @param o
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( final Bom o )
	{
		if ( !this.empresa.getNome().equals( o.getEmpresa().getNome() ) )
		{
			return this.empresa.getNome().compareTo( this.empresa.getNome() );
		}
		try
		{
			final SimpleDateFormat sdf = new SimpleDateFormat( "MM/yyyy" );
			return sdf.parse( this.mesReferencia ).compareTo( sdf.parse( o.mesReferencia ) );
		}
		catch ( final ParseException e )
		{
			if ( LOG.isErrorEnabled() )
			{
				LOG.error( e, e );
			}// if
			return 1;
		}
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
		if ( obj instanceof Bom )
		{
			if ( ( this.empresa != null )
				&& this.empresa.equals( ( ( Bom ) obj ).empresa )
				&& ( this.mesReferencia != null )
				&& this.mesReferencia.equals( ( ( Bom ) obj ).mesReferencia ) )
			{
				return true;
			}
			return false;
		}
		return false;
	}

	/**
	 * Gets the data fechamento.
	 *
	 * @return the data fechamento
	 */
	public Date getDataFechamento()
	{
		return this.dataFechamento;
	}

	/**
	 * Gets the data limite fechamento.
	 *
	 * @return the data limite fechamento
	 */
	public Date getDataLimiteFechamento()
	{
		return this.dataLimiteFechamento;
	}

	/**
	 * Gets the data reabertura.
	 *
	 * @return the data reabertura
	 */
	public Date getDataReabertura()
	{
		return this.dataReabertura;
	}

	/**
	 * Gets the empresa.
	 *
	 * @return the empresa
	 */
	public Empresa getEmpresa()
	{
		return this.empresa;
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
	 * Gets the mes referencia.
	 *
	 * @return the mes referencia
	 */
	public String getMesReferencia()
	{
		return this.mesReferencia;
	}

	/**
	 * Gets the responsavel.
	 *
	 * @return the responsavel
	 */
	public String getResponsavel()
	{
		return this.responsavel;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus()
	{
		return this.status;
	}

	/**
	 * Gets the status pendencia.
	 *
	 * @return the status pendencia
	 */
	public String getStatusPendencia()
	{
		if ( this.status == null )
		{
			return "Vencido";
		}
		else if ( this.dataReabertura == null )
		{
			return "Aberto";
		}
		else
		{
			return "Reaberto";
		}
	}

	/**
	 * Checks if is tem bom linha aberto.
	 *
	 * @return true, if is tem bom linha aberto
	 */
	public boolean isTemBomLinhaAberto()
	{
		return this.temBomLinhaAberto;
	}

	/**
	 * Checks if is tem bom linha fechado.
	 *
	 * @return true, if is tem bom linha fechado
	 */
	public boolean isTemBomLinhaFechado()
	{
		return this.temBomLinhaFechado;
	}

	/**
	 * Checks if is tem linha excluida.
	 *
	 * @return true, if is tem linha excluida
	 */
	public boolean isTemLinhaExcluida()
	{
		return this.temLinhaExcluida;
	}

	/**
	 * Sets the data fechamento.
	 *
	 * @param dataFechamento
	 *            the new data fechamento
	 */
	public void setDataFechamento( final Date dataFechamento )
	{
		this.dataFechamento = dataFechamento;
	}

	/**
	 * Sets the data limite fechamento.
	 *
	 * @param dataLimiteFechamento
	 *            the new data limite fechamento
	 */
	public void setDataLimiteFechamento( final Date dataLimiteFechamento )
	{
		this.dataLimiteFechamento = dataLimiteFechamento;
	}

	/**
	 * Sets the data reabertura.
	 *
	 * @param dataReabertura
	 *            the new data reabertura
	 */
	public void setDataReabertura( final Date dataReabertura )
	{
		this.dataReabertura = dataReabertura;
	}

	/**
	 * Sets the empresa.
	 *
	 * @param empresa
	 *            the new empresa
	 */
	public void setEmpresa( final Empresa empresa )
	{
		this.empresa = empresa;
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
	 * Sets the mes referencia.
	 *
	 * @param mesReferencia
	 *            the new mes referencia
	 */
	public void setMesReferencia( final String mesReferencia )
	{
		this.mesReferencia = mesReferencia;
	}

	/**
	 * Sets the responsavel.
	 *
	 * @param responsavel
	 *            the new responsavel
	 */
	public void setResponsavel( final String responsavel )
	{
		this.responsavel = responsavel;
	}

	/**
	 * Sets the status.
	 *
	 * @param status
	 *            the new status
	 */
	public void setStatus( final Status status )
	{
		this.status = status;
	}

	/**
	 * Sets the tem bom linha aberto.
	 *
	 * @param temBomLinhaAberto
	 *            the new tem bom linha aberto
	 */
	public void setTemBomLinhaAberto( final boolean temBomLinhaAberto )
	{
		this.temBomLinhaAberto = temBomLinhaAberto;
	}

	/**
	 * Sets the tem bom linha fechado.
	 *
	 * @param temBomLinhaFechado
	 *            the new tem bom linha fechado
	 */
	public void setTemBomLinhaFechado( final boolean temBomLinhaFechado )
	{
		this.temBomLinhaFechado = temBomLinhaFechado;
	}

	/**
	 * Sets the tem linha excluida.
	 *
	 * @param temLinhaExcluida
	 *            the new tem linha excluida
	 */
	public void setTemLinhaExcluida( final boolean temLinhaExcluida )
	{
		this.temLinhaExcluida = temLinhaExcluida;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The mes referencia.
	 */
	@Column( name = "mes_referencia" )
	@Index( name = "idx_bom_mesreferencia" )
	private String mesReferencia;

	/**
	 * The empresa.
	 */
	@ManyToOne
	private Empresa empresa;

	/**
	 * The responsavel.
	 */
	private String responsavel;

	/**
	 * The status.
	 */
	private Status status;

	/**
	 * The data reabertura.
	 */
	@Temporal( TemporalType.DATE )
	@Column( name = "data_reabertura" )
	private Date dataReabertura;

	/**
	 * The data fechamento.
	 */
	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "data_fechamento" )
	private Date dataFechamento;

	/**
	 * The data limite fechamento.
	 */
	@Transient
	private transient Date dataLimiteFechamento;

	/**
	 * The tem bom linha fechado.
	 */
	@Transient
	private transient boolean temBomLinhaFechado;

	/**
	 * The tem bom linha aberto.
	 */
	@Transient
	private transient boolean temBomLinhaAberto;

	/**
	 * The tem linha excluida.
	 */
	@Transient
	private transient boolean temLinhaExcluida;

}

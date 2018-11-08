package br.com.fetranspor.bomweb.entity;

import static br.com.decatron.framework.dsl.date.DateDsl.date;

import br.com.decatron.framework.util.Check;
import br.com.fetranspor.bomweb.components.Utils;
import br.com.fetranspor.bomweb.exception.CloneFailException;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;

/**
 * The Class Tarifa.
 */
@Entity
@Audited
@Table( name = "tarifas" )
@Inheritance( strategy = InheritanceType.JOINED )
public class Tarifa
	extends InformacoesLog
{

	/**
	 * Logger for this class
	 */
	private static final Log LOG = LogFactory.getLog( Tarifa.class );

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 8723931880782787995L;

	/**
	 * Instantiates a new tarifa.
	 */
	public Tarifa()
	{
		super();
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @throws CloneFailException
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Tarifa clone()
		throws CloneFailException
	{

		final Tarifa tarifa = new Tarifa();

		try
		{
			PropertyUtils.copyProperties( tarifa, this );

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

		tarifa.setId( null );
		return tarifa;
	}

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 *
	 * @return Returns the edicaoRetroativaJistificativa.
	 * @see #edicaoRetroativaJustificativa
	 */
	public String getEdicaoRetroativaJustificativa()
	{
		return this.edicaoRetroativaJustificativa;
	}

	/**
	 * Gets the fim vigencia.
	 *
	 * @return the fim vigencia
	 */
	public Date getFimVigencia()
	{
		return this.fimVigencia;
	}

	/**
	 * Gets the fim vigencia formated.
	 *
	 * @return the fim vigencia formated
	 */
	public String getFimVigenciaFormated()
	{
		String dataFormadata = "";
		if ( Check.isNotNull( this.fimVigencia ) )
		{
			dataFormadata = date( this.fimVigencia ).format( "dd/MM/yyyy" );
		}
		return dataFormadata;
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
	 * Gets the id tarifa antiga.
	 *
	 * @return the id tarifa antiga
	 */
	public Long getIdTarifaAntiga()
	{
		return this.idTarifaAntiga;
	}

	/**
	 * Gets the inicio vigencia.
	 *
	 * @return the inicio vigencia
	 */
	public Date getInicioVigencia()
	{
		return this.inicioVigencia;
	}

	/**
	 * Gets the inicio vigencia formated.
	 *
	 * @return the inicio vigencia formated
	 */
	public String getInicioVigenciaFormated()
	{
		String dataFormadata = "";
		if ( Check.isNotNull( this.inicioVigencia ) )
		{
			dataFormadata = date( this.inicioVigencia ).format( "dd/MM/yyyy" );
		}
		return dataFormadata;
	}

	// ----------------------------------------------------------------

	/**
	 * Gets the motivo criacao.
	 *
	 * @return the motivo criacao
	 */
	public MotivoCriacaoTarifa getMotivoCriacao()
	{
		return this.motivoCriacao;
	}

	/**
	 * Gets the secao.
	 *
	 * @return the secao
	 */
	public Secao getSecao()
	{
		return this.secao;
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public BigDecimal getValor()
	{
		return this.valor;
	}

	/**
	 * Checks if is futura.
	 *
	 * @return true, if is futura
	 */
	public boolean isFutura()
	{
		if ( Calendar.getInstance().getTime().before( this.inicioVigencia ) )
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks if is tem tarifa anterior.
	 *
	 * @return true, if is tem tarifa anterior
	 */
	public boolean isTemTarifaAnterior()
	{
		if ( this.idTarifaAntiga != null )
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks if is tem tarifa futura.
	 *
	 * @return true, if is tem tarifa futura
	 */
	public boolean isTemTarifaFutura()
	{
		if ( this.fimVigencia != null )
		{
			return true;
		}
		return false;
	}

	/**
	 * Checks if is vigente.
	 *
	 * @return true, if is vigente
	 */
	public boolean isVigente()
	{
		boolean retorno = false;
		final Date hoje = new Date();

		if ( this.inicioVigencia != null )
		{
			// inicioVigencia eh igual ou anterior a hoje.
			if ( Utils.compareToSemHora( this.inicioVigencia, hoje ) <= 0 )
			{
				if ( Check.isNull( this.fimVigencia ) || ( Utils.compareToSemHora( this.fimVigencia, hoje ) >= 0 ) )
				{
					retorno = true;
				}
			}
		}
		return retorno;
	}

	// public TipoDeVeiculo getTipoDeVeiculo() {
	// return tipoDeVeiculo;
	// }
	//
	// public void setTipoDeVeiculo(TipoDeVeiculo tipoDeVeiculo) {
	// this.tipoDeVeiculo = tipoDeVeiculo;
	// }

	/**
	 * <p>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 *
	 * @param edicaoRetroativaJustificativa
	 *            The edicaoRetroativaJustificativa to set.
	 * @see #edicaoRetroativaJustificativa
	 */
	public void setEdicaoRetroativaJustificativa( final String edicaoRetroativaJustificativa )
	{
		this.edicaoRetroativaJustificativa = edicaoRetroativaJustificativa;
	}

	/**
	 * Sets the fim vigencia.
	 *
	 * @param fimVigencia
	 *            the new fim vigencia
	 */
	public void setFimVigencia( final Date fimVigencia )
	{
		this.fimVigencia = fimVigencia;
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
	 * Sets the id tarifa antiga.
	 *
	 * @param idTarifaAntiga
	 *            the new id tarifa antiga
	 */
	public void setIdTarifaAntiga( final Long idTarifaAntiga )
	{
		this.idTarifaAntiga = idTarifaAntiga;
	}

	/**
	 * Sets the inicio vigencia.
	 *
	 * @param inicioVigencia
	 *            the new inicio vigencia
	 */
	public void setInicioVigencia( final Date inicioVigencia )
	{
		this.inicioVigencia = inicioVigencia;
	}

	/**
	 * Sets the motivo criacao.
	 *
	 * @param motivoCriacao
	 *            the new motivo criacao
	 */
	public void setMotivoCriacao( final MotivoCriacaoTarifa motivoCriacao )
	{
		this.motivoCriacao = motivoCriacao;
	}

	/**
	 * Sets the secao.
	 *
	 * @param secao
	 *            the new secao
	 */
	public void setSecao( final Secao secao )
	{
		this.secao = secao;
	}

	/**
	 * Sets the valor.
	 *
	 * @param valor
	 *            the new valor
	 */
	public void setValor( final BigDecimal valor )
	{
		this.valor = valor;
	}

	/**
	 * <p>
	 * Field <code>edicaoRetroativaJistificativa</code>
	 * </p>
	 * <p>
	 * FBW-487 FBW-483
	 * </p>
	 */
	@Column( name = "edicao_retroativa_justificativa", length = 5000 )
	private String edicaoRetroativaJustificativa;

	/**
	 * The fim vigencia.
	 */
	@Temporal( TemporalType.DATE )
	@Index( name = "idx_tarifa_fim" )
	private Date fimVigencia;

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The id tarifa antiga.
	 */
	private Long idTarifaAntiga;

	/**
	 * The inicio vigencia.
	 */
	@Temporal( TemporalType.DATE )
	@Index( name = "idx_tarifa_inicio" )
	private Date inicioVigencia;

	/**
	 * The motivo criacao.
	 */
	private MotivoCriacaoTarifa motivoCriacao;

	/**
	 * The secao.
	 */
	@ManyToOne
	private Secao secao;

	/**
	 * The valor.
	 */
	private BigDecimal valor;
}

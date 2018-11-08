package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.entity.EntityBase;
import br.com.decatron.framework.util.Check;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

/**
 * The Class BomSecao.
 */
@Entity
@Table( name = "bom_secao" )
public class BomSecao
	extends EntityBase
	implements Comparable<BomSecao>
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 7876616269366906478L;

	/**
	 * Aumentou tarifa.
	 *
	 * @return true, if successful
	 */
	public boolean aumentouTarifa()
	{
		// Se é inoperante, não aumentou.
		if ( ehInoperante() )
		{
			return false;
		}

		if ( Check.isNull( getPassageiroAnteriorAB() ) || Check.isNull( getPassageiroAnteriorBA() ) )
		{
			return false;
		}

		// Se não tem todos os passageiros anteriores, não aumentou.
		if ( getPassageiroAnteriorAB().equals( 0 ) || getPassageiroAnteriorBA().equals( 0 ) )
		{
			return false;
		}

		return true;
	}

	/**
	 * Calcula total passageiros ab.
	 */
	public void calculaTotalPassageirosAB()
	{
		Integer total = Integer.valueOf( 0 );
		total += calculaTotalPassageirosTarifaAtualAB();
		total += calculaTotalPassageirosTarifaAnteriorAB();
		this.totalPassageirosAB = total;
	}

	/**
	 * Calcula total passageiros ba.
	 */
	public void calculaTotalPassageirosBA()
	{
		Integer total = Integer.valueOf( 0 );
		total += calculaTotalPassageirosTarifaAtualBA();
		total += calculaTotalPassageirosTarifaAnteriorBA();
		this.totalPassageirosBA = total;
	}

	/**
	 * Calcula total passageiros tarifa anterior ab.
	 *
	 * @return the integer
	 */
	private Integer calculaTotalPassageirosTarifaAnteriorAB()
	{
		Integer total = Integer.valueOf( 0 );
		if ( Check.isNotNull( this.passageiroAnteriorAB ) )
		{
			total += this.passageiroAnteriorAB;
		}
		return total;
	}

	/**
	 * Calcula total passageiros tarifa anterior ba.
	 *
	 * @return the integer
	 */
	private Integer calculaTotalPassageirosTarifaAnteriorBA()
	{
		Integer total = Integer.valueOf( 0 );
		if ( Check.isNotNull( this.passageiroAnteriorBA ) )
		{
			total += this.passageiroAnteriorBA;
		}
		return total;
	}

	/**
	 * Calcula total passageiros tarifa atual ab.
	 *
	 * @return the integer
	 */
	private Integer calculaTotalPassageirosTarifaAtualAB()
	{
		Integer total = Integer.valueOf( 0 );
		if ( Check.isNotNull( this.passageiroAB ) )
		{
			total += this.passageiroAB;
		}
		return total;
	}

	/**
	 * Calcula total passageiros tarifa atual ba.
	 *
	 * @return the integer
	 */
	private Integer calculaTotalPassageirosTarifaAtualBA()
	{
		Integer total = Integer.valueOf( 0 );
		if ( Check.isNotNull( this.passageiroBA ) )
		{
			total += this.passageiroBA;
		}
		return total;
	}

	/**
	 * Calcula total receita.
	 */
	public void calculaTotalReceita()
	{
		this.totalReceita = BigDecimal.ZERO;
		if ( Check.isNotNull( this.tarifaAnterior ) )
		{
			this.totalReceita = this.totalReceita.add( this.tarifaAnterior.multiply( new BigDecimal(
				calculaTotalPassageirosTarifaAnteriorAB() ) ) );
			this.totalReceita = this.totalReceita.add( this.tarifaAnterior.multiply( new BigDecimal(
				calculaTotalPassageirosTarifaAnteriorBA() ) ) );
		}
		this.totalReceita = this.totalReceita.add( this.tarifa.multiply( new BigDecimal(
			calculaTotalPassageirosTarifaAtualAB() ) ) );
		this.totalReceita = this.totalReceita.add( this.tarifa.multiply( new BigDecimal(
			calculaTotalPassageirosTarifaAtualBA() ) ) );
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param outraSecao
	 * @return
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo( final BomSecao outraSecao )
	{
		if ( this.secao.getCodigo().compareTo( outraSecao.secao.getCodigo() ) == 0 )
		{
			return 0;
		}
		else if ( this.secao.getCodigo().compareTo( outraSecao.secao.getCodigo() ) == -1 )
		{
			return -1;
		}
		return 1;
	}

	/**
	 * Eh inoperante.
	 *
	 * @return true, if successful
	 */
	public boolean ehInoperante()
	{
		final Status status = getStatus();

		// FIXME: Será que isso é verdade? Será que tem como uma seção não ter status no BD?
		if ( Check.isNull( status ) )
		{
			return false;
		}

		return status.equals( Status.INOPERANTE );
	}

	/**
	 * Eh secao obrigatoria.
	 *
	 * @return true, if successful
	 */
	public boolean ehSecaoObrigatoria()
	{
		return this.secao.getCodigo().equals( Secao.COD_SECAO_OBRIGATORIA );
	}

	/**
	 * Gets the bom linha.
	 *
	 * @return the bom linha
	 */
	public BomLinha getBomLinha()
	{
		return this.bomLinha;
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
	 * Gets the passageiro ab.
	 *
	 * @return the passageiro ab
	 */
	public Integer getPassageiroAB()
	{
		return this.passageiroAB;
	}

	/**
	 * Gets the passageiro anterior ab.
	 *
	 * @return the passageiro anterior ab
	 */
	public Integer getPassageiroAnteriorAB()
	{
		return this.passageiroAnteriorAB;
	}

	/**
	 * Gets the passageiro anterior ba.
	 *
	 * @return the passageiro anterior ba
	 */
	public Integer getPassageiroAnteriorBA()
	{
		return this.passageiroAnteriorBA;
	}

	/**
	 * Gets the passageiro ba.
	 *
	 * @return the passageiro ba
	 */
	public Integer getPassageiroBA()
	{
		return this.passageiroBA;
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
	 * Gets the status.
	 *
	 * @return the status
	 */
	public Status getStatus()
	{
		return this.status;
	}

	/**
	 * Gets the tarifa.
	 *
	 * @return the tarifa
	 */
	public BigDecimal getTarifa()
	{
		return this.tarifa;
	}

	/**
	 * Gets the tarifa anterior.
	 *
	 * @return the tarifa anterior
	 */
	public BigDecimal getTarifaAnterior()
	{
		return this.tarifaAnterior;
	}

	/**
	 * Gets the tarifa atual.
	 *
	 * @return the tarifa atual
	 */
	public BigDecimal getTarifaAtual()
	{
		return this.tarifaAtual;
	}

	/**
	 * Gets the total passageiros.
	 *
	 * @return the total passageiros
	 */
	public long getTotalPassageiros()
	{
		Integer totalPassageirosAB = this.totalPassageirosAB;

		if ( Check.isNull( totalPassageirosAB ) )
		{
			totalPassageirosAB = 0;
		}

		Integer totalPassageirosBA = this.totalPassageirosBA;

		if ( Check.isNull( totalPassageirosBA ) )
		{
			totalPassageirosBA = 0;
		}

		return totalPassageirosAB + totalPassageirosBA;
	}

	/**
	 * Gets the total passageiros ab.
	 *
	 * @return the total passageiros ab
	 */
	public Integer getTotalPassageirosAB()
	{
		return this.totalPassageirosAB;
	}

	/**
	 * Gets the total passageiros ba.
	 *
	 * @return the total passageiros ba
	 */
	public Integer getTotalPassageirosBA()
	{
		return this.totalPassageirosBA;
	}

	/**
	 * Gets the total receita.
	 *
	 * @return the total receita
	 */
	public BigDecimal getTotalReceita()
	{
		return this.totalReceita;
	}

	/**
	 * Checks if is inoperante.
	 *
	 * @return true, if is inoperante
	 */
	public boolean isInoperante()
	{
		return this.inoperante;
	}

	/**
	 * Checks if is primeiro bom secao.
	 *
	 * @return true, if is primeiro bom secao
	 */
	public boolean isPrimeiroBomSecao()
	{
		return this.primeiroBomSecao;
	}

	/**
	 * Sets the bom linha.
	 *
	 * @param bomLinha
	 *            the new bom linha
	 */
	public void setBomLinha( final BomLinha bomLinha )
	{
		this.bomLinha = bomLinha;
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
	 * Sets the inoperante.
	 *
	 * @param inoperante
	 *            the new inoperante
	 */
	public void setInoperante( final boolean inoperante )
	{
		this.inoperante = inoperante;
	}

	/**
	 * Sets the passageiro ab.
	 *
	 * @param passageiroAB
	 *            the new passageiro ab
	 */
	public void setPassageiroAB( final Integer passageiroAB )
	{
		this.passageiroAB = passageiroAB;
		calculaTotalPassageirosAB();
	}

	/**
	 * Sets the passageiro anterior ab.
	 *
	 * @param passageiroEspecieAnteriorAB
	 *            the new passageiro anterior ab
	 */
	public void setPassageiroAnteriorAB( final Integer passageiroEspecieAnteriorAB )
	{
		this.passageiroAnteriorAB = passageiroEspecieAnteriorAB;
		calculaTotalPassageirosAB();
	}

	/**
	 * Sets the passageiro anterior ba.
	 *
	 * @param passageiroAnteriorBA
	 *            the new passageiro anterior ba
	 */
	public void setPassageiroAnteriorBA( final Integer passageiroAnteriorBA )
	{
		this.passageiroAnteriorBA = passageiroAnteriorBA;
		calculaTotalPassageirosBA();
	}

	/**
	 * Sets the passageiro ba.
	 *
	 * @param passageiroBA
	 *            the new passageiro ba
	 */
	public void setPassageiroBA( final Integer passageiroBA )
	{
		this.passageiroBA = passageiroBA;
		calculaTotalPassageirosBA();
	}

	/**
	 * Sets the primeiro bom secao.
	 *
	 * @param primeiroBomSecao
	 *            the new primeiro bom secao
	 */
	public void setPrimeiroBomSecao( final boolean primeiroBomSecao )
	{
		this.primeiroBomSecao = primeiroBomSecao;
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
	 * Sets the tarifa.
	 *
	 * @param tarifa
	 *            the new tarifa
	 */
	public void setTarifa( final BigDecimal tarifa )
	{
		this.tarifa = tarifa;
	}

	/**
	 * Sets the tarifa anterior.
	 *
	 * @param tarifaAnterior
	 *            the new tarifa anterior
	 */
	public void setTarifaAnterior( final BigDecimal tarifaAnterior )
	{
		this.tarifaAnterior = tarifaAnterior;
	}

	/**
	 * Sets the tarifa atual.
	 *
	 * @param tarifaAtual
	 *            the new tarifa atual
	 */
	public void setTarifaAtual( final BigDecimal tarifaAtual )
	{
		this.tarifaAtual = tarifaAtual;
	}

	/**
	 * Sets the total passageiros ab.
	 *
	 * @param totalPassageirosAB
	 *            the new total passageiros ab
	 */
	public void setTotalPassageirosAB( final Integer totalPassageirosAB )
	{
		this.totalPassageirosAB = totalPassageirosAB;
	}

	/**
	 * Sets the total passageiros ba.
	 *
	 * @param totalPassageirosBA
	 *            the new total passageiros ba
	 */
	public void setTotalPassageirosBA( final Integer totalPassageirosBA )
	{
		this.totalPassageirosBA = totalPassageirosBA;
	}

	/**
	 * Sets the total receita.
	 *
	 * @param totalReceita
	 *            the new total receita
	 */
	public void setTotalReceita( final BigDecimal totalReceita )
	{
		this.totalReceita = totalReceita;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The bom linha.
	 */
	@ManyToOne
	@NotNull
	@JoinColumn( name = "bom_linha_id" )
	private BomLinha bomLinha;

	/**
	 * The secao.
	 */
	@ManyToOne
	@NotNull
	private Secao secao;

	/**
	 * The tarifa.
	 */
	@NotNull
	private BigDecimal tarifa;

	/**
	 * The tarifa anterior.
	 */
	@NotNull
	private BigDecimal tarifaAnterior;

	/**
	 * The tarifa atual.
	 */
	@NotNull
	private BigDecimal tarifaAtual;

	/**
	 * The passageiro ab.
	 */
	@Column( name = "passageiro_ab" )
	private Integer passageiroAB;

	/**
	 * The passageiro anterior ab.
	 */
	@Column( name = "passageiro_anterior_ab" )
	private Integer passageiroAnteriorAB;

	/**
	 * The passageiro ba.
	 */
	@Column( name = "passageiro_ba" )
	private Integer passageiroBA;

	/**
	 * The passageiro anterior ba.
	 */
	@Column( name = "passageiro_anterior_ba" )
	private Integer passageiroAnteriorBA;

	/**
	 * The total passageiros ab.
	 */
	@Column( name = "total_passageiros_ab" )
	private Integer totalPassageirosAB;

	/**
	 * The total passageiros ba.
	 */
	@Column( name = "total_passageiros_ba" )
	private Integer totalPassageirosBA;

	/**
	 * The total receita.
	 */
	@Column( name = "total_receita" )
	private BigDecimal totalReceita;

	/**
	 * The status.
	 */
	private Status status;

	/**
	 * The inoperante.
	 */
	@Transient
	private transient boolean inoperante;

	/**
	 * The primeiro bom secao.
	 */
	@Transient
	private transient boolean primeiroBomSecao;
}

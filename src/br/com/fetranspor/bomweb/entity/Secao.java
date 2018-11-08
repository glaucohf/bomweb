package br.com.fetranspor.bomweb.entity;

import br.com.fetranspor.bomweb.exception.CloneFailException;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class Secao.
 */
@Entity
@Audited
@Table( name = "secoes" )
@Inheritance( strategy = InheritanceType.JOINED )
public class Secao
	extends InformacoesLog
	implements Cloneable, Comparable<Secao>
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = 9189683654709716197L;

	/**
	 * The Constant COD_SECAO_OBRIGATORIA.
	 */
	public static final String COD_SECAO_OBRIGATORIA = "00";

	/**
	 * Instantiates a new secao.
	 */
	public Secao()
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
	public Secao clone()
		throws CloneFailException
	{

		final Secao secao = new Secao();
		// TODO vir da tela
		try
		{
			BeanUtils.copyProperties( secao, this );
		}
		catch ( final IllegalAccessException e )
		{
			throw new CloneFailException( e );
		}
		catch ( final InvocationTargetException e )
		{
			throw new CloneFailException( e );
		}
		secao.setDataCriacao( new Date() );
		secao.setId( null );

		return secao;
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
	public int compareTo( final Secao outraSecao )
	{
		if ( ( this == null )
			|| ( outraSecao == null )
			|| ( getCodigo() == null )
			|| ( outraSecao.getCodigo() == null )
			|| ( getCodigo().compareTo( outraSecao.getCodigo() ) == 0 ) )
		{
			return 0;
		}
		else if ( getCodigo().compareTo( outraSecao.getCodigo() ) == -1 )
		{
			return -1;
		}
		return 1;
	}

	/**
	 * Gets the codigo.
	 *
	 * @return the codigo
	 */
	public String getCodigo()
	{
		return this.codigo;
	}

	/**
	 * Gets the data criacao.
	 *
	 * @return the data criacao
	 */
	public Date getDataCriacao()
	{
		return this.dataCriacao;
	}

	/**
	 * Gets the data termino.
	 *
	 * @return the data termino
	 */
	public Date getDataTermino()
	{
		return this.dataTermino;
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
	 * Gets the id secao origem.
	 *
	 * @return the id secao origem
	 */
	public Long getIdSecaoOrigem()
	{
		return this.idSecaoOrigem;
	}

	/**
	 * Gets the juncao.
	 *
	 * @return the juncao
	 */
	public String getJuncao()
	{
		this.juncao = this.pontoInicial + "  - " + this.pontoFinal;
		return this.juncao;
	}

	/**
	 * Gets the linha vigencia.
	 *
	 * @return the linha vigencia
	 */
	public LinhaVigencia getLinhaVigencia()
	{
		return this.linhaVigencia;
	}

	/**
	 * Gets the ponto final.
	 *
	 * @return the ponto final
	 */
	public String getPontoFinal()
	{
		return this.pontoFinal;
	}

	/**
	 * Gets the ponto inicial.
	 *
	 * @return the ponto inicial
	 */
	public String getPontoInicial()
	{
		return this.pontoInicial;
	}

	// ----------------------------------------------------------------

	/**
	 * Sets the codigo.
	 *
	 * @param codigo
	 *            the new codigo
	 */
	public void setCodigo( final String codigo )
	{
		this.codigo = codigo;
	}

	/**
	 * Sets the data criacao.
	 *
	 * @param dataCriacao
	 *            the new data criacao
	 */
	public void setDataCriacao( final Date dataCriacao )
	{
		this.dataCriacao = dataCriacao;
	}

	/**
	 * Sets the data termino.
	 *
	 * @param dataTermino
	 *            the new data termino
	 */
	public void setDataTermino( final Date dataTermino )
	{
		this.dataTermino = dataTermino;
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
	 * Sets the id secao origem.
	 *
	 * @param idSecaoOrigem
	 *            the new id secao origem
	 */
	public void setIdSecaoOrigem( final Long idSecaoOrigem )
	{
		this.idSecaoOrigem = idSecaoOrigem;
	}

	/**
	 * Sets the juncao.
	 *
	 * @param juncao
	 *            the new juncao
	 */
	public void setJuncao( final String juncao )
	{
		this.juncao = juncao;
	}

	/**
	 * Sets the linha vigencia.
	 *
	 * @param linhaVigencia
	 *            the new linha vigencia
	 */
	public void setLinhaVigencia( final LinhaVigencia linhaVigencia )
	{
		this.linhaVigencia = linhaVigencia;
	}

	/**
	 * Sets the ponto final.
	 *
	 * @param pontoFinal
	 *            the new ponto final
	 */
	public void setPontoFinal( final String pontoFinal )
	{
		this.pontoFinal = pontoFinal;
	}

	/**
	 * Sets the ponto inicial.
	 *
	 * @param pontoInicial
	 *            the new ponto inicial
	 */
	public void setPontoInicial( final String pontoInicial )
	{
		this.pontoInicial = pontoInicial;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getCodigo();
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The codigo.
	 */
	@NotNull
	@Index( name = "idx_secao_codigo" )
	private String codigo;

	/**
	 * The ponto inicial.
	 */
	@NotNull
	@Index( name = "idx_secao_pi" )
	private String pontoInicial;

	/**
	 * The linha vigencia.
	 */
	@NotNull
	@ManyToOne( fetch = FetchType.LAZY )
	@NotAudited
	private LinhaVigencia linhaVigencia;

	/**
	 * The ponto final.
	 */
	@NotNull
	@Index( name = "idx_secao_pf" )
	private String pontoFinal;

	/**
	 * The juncao.
	 */
	@Transient
	private String juncao;

	/**
	 * The data criacao.
	 */
	@Column( name = "data_criacao" )
	private Date dataCriacao;

	/**
	 * The data termino.
	 */
	@Column( name = "data_termino" )
	private Date dataTermino;

	/**
	 * The id secao origem.
	 */
	private Long idSecaoOrigem;
}

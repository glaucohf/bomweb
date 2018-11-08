package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.util.Check;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Index;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

/**
 * The Class Empresa.
 */
@Entity
@Audited
@Table( name = "empresas" )
@Inheritance( strategy = InheritanceType.JOINED )
public class Empresa
	extends InformacoesLog
{

	/**
	 * <p>
	 * Field <code>serialVersionUID</code>
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new empresa.
	 */
	public Empresa()
	{
		super();
		this.excludeProperties.add( "linhas" );
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
		final Empresa other = ( Empresa ) obj;
		if ( this.codigo == null )
		{
			if ( other.codigo != null )
			{
				return false;
			}
		}
		else if ( !this.codigo.equals( other.codigo ) )
		{
			return false;
		}
		return true;
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
	 * @throws ParseException
	 *             the parse exception
	 */
	public String getDataCriacao()
		throws ParseException
	{
		final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
		String source = "";
		if ( Check.isNotNull( this.dataCriacao ) )
		{
			source = format.format( this.dataCriacao );
		}
		return source;
	}

	/**
	 * Gets the data criacao date.
	 *
	 * @return the data criacao date
	 */
	public Date getDataCriacaoDate()
	{
		return this.dataCriacao;
	}

	/**
	 * Gets the email contato.
	 *
	 * @return the email contato
	 */
	public String getEmailContato()
	{
		return this.emailContato;
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
	 * Gets the inicio vigencia bom.
	 *
	 * @return the inicio vigencia bom
	 */
	public Date getInicioVigenciaBom()
	{
		return this.inicioVigenciaBom;
	}

	// ----------------------------------------------------------------------

	/**
	 * Gets the linhas vigencias.
	 *
	 * @return the linhas vigencias
	 */
	public List<LinhaVigencia> getLinhasVigencias()
	{
		return this.linhasVigencias;
	}

	// ----------------------------------------------------------------------

	/**
	 * Gets the nome.
	 *
	 * @return the nome
	 */
	public String getNome()
	{
		return this.nome;
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
		result = ( prime * result ) + ( ( this.codigo == null ) ? 0 : this.codigo.hashCode() );
		return result;
	}

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
	 * Sets the data criacao.
	 *
	 * @param dataCriacao
	 *            the new data criacao
	 * @throws ParseException
	 *             the parse exception
	 */
	public void setDataCriacao( final String dataCriacao )
		throws ParseException
	{
		Date dataAux;
		final SimpleDateFormat format = new SimpleDateFormat( "dd/MM/yyyy" );
		if ( Check.isBlank( dataCriacao ) )
		{
			dataAux = format.parse( format.format( Calendar.getInstance().getTime() ) );
		}
		else
		{
			dataAux = format.parse( dataCriacao );
		}
		this.dataCriacao = dataAux;

	}

	/**
	 * Sets the email contato.
	 *
	 * @param emailContato
	 *            the new email contato
	 */
	public void setEmailContato( final String emailContato )
	{
		this.emailContato = emailContato;
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
	 * Sets the inicio vigencia bom.
	 *
	 * @param inicioVigenciaBom
	 *            the new inicio vigencia bom
	 */
	public void setInicioVigenciaBom( final Date inicioVigenciaBom )
	{
		this.inicioVigenciaBom = inicioVigenciaBom;
	}

	/**
	 * Sets the linhas vigencias.
	 *
	 * @param linhasVigencias
	 *            the new linhas vigencias
	 */
	public void setLinhasVigencias( final List<LinhaVigencia> linhasVigencias )
	{
		this.linhasVigencias = linhasVigencias;
	}

	/**
	 * Sets the nome.
	 *
	 * @param nome
	 *            the new nome
	 */
	public void setNome( final String nome )
	{
		this.nome = nome;
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
	 * <p>
	 * </p>
	 *
	 * @return
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return getNome();
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
	@Index( name = "idx_empresa_codigo" )
	private String codigo;

	/**
	 * The nome.
	 */
	@NotNull
	@Index( name = "idx_empresa_nome" )
	private String nome;

	/**
	 * The responsavel.
	 */
	private String responsavel;

	/**
	 * The email contato.
	 */
	@Column( name = "email_contato" )
	private String emailContato;

	/**
	 * The data criacao.
	 */
	@Column( name = "data_criacao" )
	private Date dataCriacao;

	/**
	 * The linhas vigencias.
	 */
	@NotAudited
	@OneToMany( mappedBy = "empresa" )
	private List<LinhaVigencia> linhasVigencias;

	/**
	 * The inicio vigencia bom.
	 */
	@NotNull
	@Column( name = "inicio_vigencia_bom" )
	@Temporal( TemporalType.DATE )
	private Date inicioVigenciaBom;
}

package br.com.fetranspor.bomweb.entity;

import br.com.decatron.framework.entity.EntityBase;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * The Class Justificativa.
 */
@Entity
@Table( name = "justificativas" )
public class Justificativa
	extends EntityBase
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -4577103366725767359L;

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
	 * Gets the data criacao.
	 *
	 * @return the data criacao
	 */
	public Date getDataCriacao()
	{
		return this.dataCriacao;
	}

	/**
	 * Gets the descricao.
	 *
	 * @return the descricao
	 */
	public String getDescricao()
	{
		return this.descricao;
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
	 * Gets the previa justificativa.
	 *
	 * @return the previa justificativa
	 */
	public String getPreviaJustificativa()
	{
		if ( this.descricao.length() < 21 )
		{
			return this.descricao;
		}

		return this.descricao.substring( 0, 20 ) + "...";
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public Usuario getUsuario()
	{
		return this.usuario;
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
	 * Sets the descricao.
	 *
	 * @param descricao
	 *            the new descricao
	 */
	public void setDescricao( final String descricao )
	{
		this.descricao = descricao;
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
	 * Sets the usuario.
	 *
	 * @param usuario
	 *            the new usuario
	 */
	public void setUsuario( final Usuario usuario )
	{
		this.usuario = usuario;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * The descricao.
	 */
	@NotNull
	@Column( name = "descricao" )
	private String descricao;

	/**
	 * The bom linha.
	 */
	@NotNull
	@ManyToOne
	@JoinColumn( name = "bom_linha_id" )
	private BomLinha bomLinha;

	/**
	 * The usuario.
	 */
	@NotNull
	@ManyToOne
	@JoinColumn( name = "usuario_id" )
	private Usuario usuario;

	/**
	 * The data criacao.
	 */
	@NotNull
	@Temporal( TemporalType.DATE )
	@Column( name = "data_criacao" )
	private Date dataCriacao;
}

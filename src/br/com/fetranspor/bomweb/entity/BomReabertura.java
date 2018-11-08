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
 * <p>
 * </p>
 *
 * @author daniel.azeredo
 * @version 1.0 Created on 06/04/2016
 */
@Entity
@Table( name = "bom_reabertura" )
public class BomReabertura
	extends EntityBase
{

	/**
	 * <p>
	 * Field <code>serialVersionUID</code>
	 * </p>
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the bom.
	 * @see #bom
	 */
	public Bom getBom()
	{
		return this.bom;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the dataReabertura.
	 * @see #dataCriacao
	 */
	public Date getDataCriacao()
	{
		return this.dataCriacao;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @return Returns the id.
	 * @see #id
	 */
	@Override
	public Long getId()
	{
		return this.id;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param bom
	 *            The bom to set.
	 * @see #bom
	 */
	public void setBom( final Bom bom )
	{
		this.bom = bom;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param dataCriacao
	 *            The dataReabertura to set.
	 * @see #dataCriacao
	 */
	public void setDataCriacao( final Date dataCriacao )
	{
		this.dataCriacao = dataCriacao;
	}

	/**
	 * <p>
	 * </p>
	 *
	 * @param id
	 *            The id to set.
	 * @see #id
	 */
	public void setId( final Long id )
	{
		this.id = id;
	}

	/**
	 * <p>
	 * Field <code>id</code>
	 * </p>
	 */
	@Id
	@GeneratedValue
	private Long id;

	/**
	 * <p>
	 * Field <code>bom</code>
	 * </p>
	 */
	@NotNull
	@ManyToOne
	@JoinColumn( name = "bom_id" )
	private Bom bom;

	/**
	 * <p>
	 * Field <code>dataCriacao</code>
	 * </p>
	 */
	@Temporal( TemporalType.DATE )
	@Column( name = "data_criacao" )
	private Date dataCriacao;

}

package br.com.fetranspor.bomweb.entity;

import br.com.fetranspor.bomweb.components.BomWebRevisionListener;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

/**
 * The Class BomWebRevisionEntity.
 */
@Entity
@RevisionEntity( BomWebRevisionListener.class )
@Table( name = "bomweb_revison_entity" )
public class BomWebRevisionEntity
	extends BomWebEntityBase
{

	/**
	 * The Constant serialVersionUID.
	 */
	private static final long serialVersionUID = -955693366815302687L;

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public Date getData()
	{
		return this.data;
	}

	/**
	 * Gets the endereco ip.
	 *
	 * @return the endereco ip
	 */
	public String getEnderecoIP()
	{
		return this.enderecoIP;
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
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	public long getTimestamp()
	{
		return this.timestamp;
	}

	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	public Integer getUserid()
	{
		return this.userid;
	}

	/**
	 * Sets the data.
	 *
	 * @param data
	 *            the new data
	 */
	public void setData( final Date data )
	{
		this.data = data;
	}

	/**
	 * Sets the endereco ip.
	 *
	 * @param enderecoIP
	 *            the new endereco ip
	 */
	public void setEnderecoIP( final String enderecoIP )
	{
		this.enderecoIP = enderecoIP;
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
	 * Sets the timestamp.
	 *
	 * @param timestamp
	 *            the new timestamp
	 */
	public void setTimestamp( final long timestamp )
	{
		this.timestamp = timestamp;
	}

	/**
	 * Sets the userid.
	 *
	 * @param userid
	 *            the new userid
	 */
	public void setUserid( final Integer userid )
	{
		this.userid = userid;
	}

	/**
	 * The id.
	 */
	@Id
	@GeneratedValue
	@RevisionNumber
	private Long id;

	/**
	 * The timestamp.
	 */
	@RevisionTimestamp
	private long timestamp;

	/**
	 * The data.
	 */
	private Date data;

	/**
	 * The userid.
	 */
	private Integer userid;

	/**
	 * The endereco ip.
	 */
	private String enderecoIP;
}
